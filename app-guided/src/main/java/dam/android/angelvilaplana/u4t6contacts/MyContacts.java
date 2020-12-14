package dam.android.angelvilaplana.u4t6contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class MyContacts {

    private ArrayList<String> myDataSet;

    private Context context;

    public MyContacts(Context context) {
        this.context = context;
        this.myDataSet = getContacts();
    }

    /**
     * Get contacts list from ContactsProvider
     * @return Contacts array
     */
    private ArrayList<String> getContacts() {
        ArrayList<String> contactsList = new ArrayList<>();

        // Access to ContentProviders
        ContentResolver contentResolver = context.getContentResolver();

        // Aux variables
        String[] projection = new String[] {
                ContactsContract.Data._ID,
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String selectionFilter = ContactsContract.Data.MIMETYPE + "='" +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND " +
                ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";

        // Query required data
        Cursor contactsCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,    // URI to Contacts: content://com.android.contacts/data
                projection,                                                                 // Projection
                selectionFilter,                                                            // Selection Filter
                null,                                                           // No selectionArgs
                ContactsContract.Data.DISPLAY_NAME + " ASC");                      // sortOrder;

        if (contactsCursor != null) {
            // Get the column indexes for desired Name and Number columns
            int nameIndex = contactsCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);
            int numberIndex = contactsCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);

            // Read data and add to ArrayList
            while (contactsCursor.moveToNext()) {
                String name = contactsCursor.getString(nameIndex);
                String number = contactsCursor.getString(numberIndex);
                contactsList.add(name + ": " + number);
            }
            contactsCursor.close();
        }

        return contactsList;
    }

    public String getContactData(int position) {
        return myDataSet.get(position);
    }

    public int getCount() {
        return myDataSet.size();
    }

}
