package dam.android.angelvilaplana.u4t6contacts;

import android.provider.ContactsContract;
import androidx.annotation.NonNull;

public class ContactItem {

    private int id;

    private String name;

    private String numberPhone;

    private int phoneType;

    private int contactID;

    private int rawContactID;

    private String lookUpKey;

    private String image;

    public ContactItem(int id, String name, String numberPhone, int phoneType, int contactID, int rawContactID, String lookUpKey, String image) {
        this.id = id;
        this.name = name;
        this.numberPhone = numberPhone;
        this.phoneType = phoneType;
        this.contactID = contactID;
        this.rawContactID = rawContactID;
        this.lookUpKey = lookUpKey;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getContactID() {
        return contactID;
    }

    public String getName() {
        return name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getImage() {
        return image;
    }

    public String getPhoneType() {
        if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_HOME) {
            return "HOME";
        } else if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_WORK) {
            return "WORK";
        } else if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
            return "MOBILE";
        }
        return "OTHER";
    }

    @Override
    public String toString() {
        return name + " " + numberPhone + " (" + getPhoneType() + ") _ID:" + id + " CONTACT_ID: " + contactID +
                " RAW_CONTACT_ID: " + rawContactID + " LOOKUP_KEY: " + lookUpKey;
    }
}
