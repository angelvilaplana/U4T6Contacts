package dam.android.angelvilaplana.u4t6contacts;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private MyContacts myContacts;

    // Class for each item: contains only a TextView
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder (TextView view) {
            super(view);
            this.textView = view;
        }

        // Sets viewHolder views with data
        public void bind(String contactData) {
            this.textView.setText(contactData);
        }
    }

    // Constructor: myContacts contains Contacts data
    MyAdapter(MyContacts myContacts) {
        this.myContacts = myContacts;
    }

    // Creates new View item: Layout Manager calls this method
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create item View:
        // Use a simple TextView predefined layout (sdk/platforms/android-xx/data/res/layout)
        // that contains only TextView
        TextView tv = (TextView) LayoutInflater.from(parent.getContext()).
                inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MyViewHolder(tv);
    }

    // Replaces the data content of a viewholder (recycles old viewholder): Layout Manager calls this method
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        // Bind viewHolder with data at: position
        viewHolder.bind(myContacts.getContactData(position));
    }

    // Returns the size of dataSet : Layout Manager calls this method
    @Override
    public int getItemCount() {
        return myContacts.getCount();
    }

}
