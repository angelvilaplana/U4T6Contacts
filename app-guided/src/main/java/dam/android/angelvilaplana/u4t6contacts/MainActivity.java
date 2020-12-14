package dam.android.angelvilaplana.u4t6contacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private MyContacts myContacts;

    private RecyclerView recyclerView;

    // Permissions required to contacts provider, only needed to READ
    private static String[] PERMISSIONS_CONTACTS = {Manifest.permission.READ_CONTACTS};

    // Id to identify a contacts permissions request
    private static final  int REQUEST_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();

        if (checkPermissions()) {
            setListAdapter();
        }
    }

    private void setUI() {
        recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setHasFixedSize(true);

        // Set recyclerView with a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void setListAdapter() {
        // MyContacts class gets data from ContactsProvider
        myContacts = new MyContacts(this);
        // Set adapter to recyclerView
        recyclerView.setAdapter(new MyAdapter(myContacts));
        // Hide empty list TextView
        if (myContacts.getCount() > 0) {
            findViewById(R.id.tvEmptyList).setVisibility(View.INVISIBLE);
        }
    }

    private boolean checkPermissions() {
        // Check for permissions granted before setting listView Adapter data
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Opens Dialog: requests user to grant permission
            ActivityCompat.requestPermissions(this, MainActivity.PERMISSIONS_CONTACTS, MainActivity.REQUEST_CONTACTS);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CONTACTS) {
            // We have requested on READ permissions for contacts, so only need [0] to be checked
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setListAdapter();
            } else {
                Toast.makeText(this, getString(R.string.contacts_read_right_required), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}