package dam.android.angelvilaplana.u4t6contacts;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // Activity 10.2 Listener click item
    public interface OnItemClickListener {
        void onItemClick(ContactItem item, boolean isLongClick);
    }

    private MyContacts myContacts;

    // Activity 10.2 Listener click item
    private OnItemClickListener listener;

    //  Activity 10.1 Modify MyView
    // Class for each item: contains only a TextView
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layoutContact;
        TextView tvId;
        TextView tvName;
        TextView tvMobilePhone;
        ImageView ivProfile;

        // Activity 10.2 Check if is a long click in the listener
        boolean isLongClick;

        // Activity 10.1 Change text for a layout
        public MyViewHolder (View itemView) {
            super(itemView);
            this.layoutContact = itemView.findViewById(R.id.layoutContact);
            this.tvId = itemView.findViewById(R.id.tvId);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvMobilePhone = itemView.findViewById(R.id.tvMobilePhone);
            this.ivProfile = itemView.findViewById(R.id.ivProfile);

            this.isLongClick = false;
        }

        // Sets viewHolder views with data
        public void bind(ContactItem item, OnItemClickListener listener) {
            // Activity 10.2 Set listeners
            setListener(item, listener);

            tvId.setText(String.valueOf(item.getId()));
            tvName.setText(item.getName());
            tvMobilePhone.setText(item.getNumberPhone());

            String image = item.getImage();
            if (image != null) {
                ivProfile.setImageURI(Uri.parse(image));
            }
        }

        // Activity 10.2 Set listeners
        private void setListener(final ContactItem item, final OnItemClickListener listener) {
            layoutContact.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemClick(item, true);
                    isLongClick = true;
                    return false;
                }
            });
            layoutContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isLongClick) {
                        listener.onItemClick(item, false);
                    } else {
                        isLongClick = false;
                    }
                }
            });
        }
    }

    // Constructor: myContacts contains Contacts data
    MyAdapter(MyContacts myContacts, OnItemClickListener listener) {
        this.myContacts = myContacts;

        // Activity 10.2 Set listener for onItemClick
        this.listener = listener;
    }

    // Creates new View item: Layout Manager calls this method
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Activity 10.1 Change text for a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    // Replaces the data content of a viewholder (recycles old viewholder): Layout Manager calls this method
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        // Bind viewHolder with data at: position
        viewHolder.bind(myContacts.getContactData(position), listener);
    }

    // Returns the size of dataSet : Layout Manager calls this method
    @Override
    public int getItemCount() {
        return myContacts.getCount();
    }

}
