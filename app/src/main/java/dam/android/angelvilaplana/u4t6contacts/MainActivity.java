package dam.android.angelvilaplana.u4t6contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    private MyContacts myContacts;

    private RecyclerView recyclerView;

    // Activity 10.2 - Display contact data
    private TextView tvInfoContact;

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
        // Activity 10.2 - Display contact data
        tvInfoContact = findViewById(R.id.tvInfoContact);
        tvInfoContact.setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setHasFixedSize(true);

        // Activity 10.2 - Hide tvInfoContact if RecycleView is moving
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                tvInfoContact.setVisibility(View.INVISIBLE);
            }
        });

        // Activity 10.1 - Add a separator in the RecyclerView
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_recycler_view)));
        recyclerView.addItemDecoration(itemDecorator);

        // Set recyclerView with a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void setListAdapter() {
        // MyContacts class gets data from ContactsProvider
        myContacts = new MyContacts(this);
        // Set adapter to recyclerView
        recyclerView.setAdapter(new MyAdapter(myContacts, this));
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

    // Activity 10.2 - Listener item click
    @Override
    public void onItemClick(ContactItem item, boolean isLongClick) {
        if (!isLongClick) {
            tvInfoContact.setText(item.toString());
            tvInfoContact.setVisibility(View.VISIBLE);
        } else {
            tvInfoContact.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(item.getContactID()));
            intent.setData(uri);
            startActivityForResult(intent, REQUEST_CONTACTS);
        }
    }

    // Activity 10.2 - Check if contact update & restart contacts
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACTS) {
            myContacts = new MyContacts(this);
            recyclerView.setAdapter(new MyAdapter(myContacts, this));
        }
    }

}