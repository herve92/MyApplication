package is.unige.ch.myapplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/*

*Classe qui va gérer les données des listViews


    Your code might call findViewById() frequently during the scrolling of ListView,
    which can slow down performance. Even when the Adapter returns an inflated view for recycling,
    you still need to look up the elements and update them.
    A way around repeated use of findViewById() is to use the "view holder" design pattern.
    A ViewHolder object stores each of the component views inside the tag field of the Layout,
    so you can immediately access them without the need to look them up repeatedly.
    First, you need to create a class to hold your exact set of views.

 */
public class ListAdapter extends ArrayAdapter<GarbagePlace> {

    private Context context;
    private int layoutResourceId;
    private String lastSearch;

    private ArrayList<GarbagePlace> data = new ArrayList<GarbagePlace>();

    public ListAdapter(Context context, int layoutResourceId,
                       ArrayList<GarbagePlace> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public List<GarbagePlace> getItems() {
        return data;
    }
// compare l'input et les addresses
    private Comparator<GarbagePlace> compareAddresses = new Comparator<GarbagePlace>() {

        public int compare(GarbagePlace p1, GarbagePlace p2) {

            String a1 = p1.getAddress();
            String a2 = p2.getAddress();

            if (a1.contains(lastSearch) && a2.contains(lastSearch)) {
                return +1;
            }
            if (a1.contains(lastSearch) && !a2.contains(lastSearch)) {
                return -1;
            }
            return 0;
        }
    };

    public void sortByAddress(String search) {
        this.lastSearch = search;

        Collections.sort(this.data, compareAddresses);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            //Then populate the ViewHolder and store it inside the layout.
            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.item_title);
            holder.txtAddress = (TextView) row.findViewById(R.id.item_address);
            holder.txtTypes = (TextView) row.findViewById(R.id.item_types);

            row.setTag(holder);

        } else {
            holder = (RecordHolder) row.getTag();
        }

        GarbagePlace item = data.get(position);
        holder.txtTitle.setText(item.getNumero());
        holder.txtAddress.setText(item.getAddress());

        holder.txtTypes.setText("");
        for(GarbagePlace.GARBAGE_TYPE type : item.getGarbageSupported()) {
            holder.txtTypes.setText(holder.txtTypes.getText() + " " + type.toString());
        }

        return row;
    }


    static class RecordHolder {
        TextView txtTitle;
        TextView txtAddress;
        TextView txtTypes;
    }
}