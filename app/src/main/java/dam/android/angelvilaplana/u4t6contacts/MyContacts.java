package dam.android.angelvilaplana.u4t6contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class MyContacts {

    // Activity 10.1 Modify dataSet String by ContactItem
    private ArrayList<ContactItem> myDataSet;

    private Context context;

    public MyContacts(Context context) {
        this.context = context;
        this.myDataSet = getContacts();
    }

    /**
     * Activity 10.1 Modify dataSet String by ContactItem
     * Get contacts list from ContactsProvider
     * @return Contacts array
     */
    private ArrayList<ContactItem> getContacts() {
        ArrayList<ContactItem> contactsList = new ArrayList<>();

        // Access to ContentProviders
        ContentResolver contentResolver = context.getContentResolver();

        // Aux variables
        String[] projection = new String[] {
                ContactsContract.Data._ID,
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                // Act 10.1 Modify query new values
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Data.LOOKUP_KEY,
                ContactsContract.Data.RAW_CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
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
            // Act 10.1 Get the new columns that I've modified in the query
            int idColumn = contactsCursor.getColumnIndex(ContactsContract.Data._ID);
            int contactIDColumn = contactsCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID);
            int lookKey = contactsCursor.getColumnIndex(ContactsContract.Data.LOOKUP_KEY);
            int rawContactIDColumn = contactsCursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID);
            int phoneTypeColumn = contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
            int photoThumbnailURI = contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);

            // Read data and add to ArrayList
            while (contactsCursor.moveToNext()) {
                int id = contactsCursor.getInt(idColumn);
                String name = contactsCursor.getString(nameIndex);
                String numberPhone = contactsCursor.getString(numberIndex);
                int phoneType = contactsCursor.getInt(phoneTypeColumn);
                int contactID = contactsCursor.getInt(contactIDColumn);
                int rawContactID = contactsCursor.getInt(rawContactIDColumn);
                String lookUpKey = contactsCursor.getString(lookKey);
                String photo = contactsCursor.getString(photoThumbnailURI);

                ContactItem contactItem = new ContactItem(id, name, numberPhone, phoneType, contactID, rawContactID, lookUpKey, photo);
                contactsList.add(contactItem);
            }
            contactsCursor.close();
        }

        return contactsList;
    }

    public ContactItem getContactData(int position) {
        return myDataSet.get(position);
    }

    public int getCount() {
        return myDataSet.size();
    }

}
