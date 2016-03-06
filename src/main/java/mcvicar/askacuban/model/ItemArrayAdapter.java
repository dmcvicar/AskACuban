package mcvicar.askacuban.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import mcvicar.askacuban.R;
import mcvicar.askacuban.activities.ListCommentsActivity;

/**
 * Created by davidmcvicar on 3/5/16.
 */
public class ItemArrayAdapter extends ArrayAdapter<Item> {

    private final Context context;
    private final List<Item> values;

    public ItemArrayAdapter(Context context, List<Item> values ) {
        super(context,-1,values);
        this.context = context;
        this.values = values;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.item_list_element_layout, parent, false);

        RelativeLayout layout = (RelativeLayout) rowView.findViewById(R.id.item_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCommentsIntent = new Intent(rowView.getContext(), ListCommentsActivity.class);
                viewCommentsIntent.putExtra("item_id",values.get(position).getId());
                rowView.getContext().startActivity(viewCommentsIntent);
            }
        });

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        titleText.setText(values.get(position).getTitle());

        TextView likesText = (TextView) rowView.findViewById(R.id.likes);
        likesText.setText(String.format(context.getString(R.string.like_label), values.get(position).getUpvotes_count()));

        //TextView submittedText = (TextView) rowView.findViewById(R.id.submitted);
        //submittedText.setText(String.format(context.getString(R.string.submitted_label),values.get(position).getUpvotes_count()));

        TextView authorText = (TextView) rowView.findViewById(R.id.author);
        authorText.setText(String.format(context.getString(R.string.author_label),values.get(position).getUsername()));

        return rowView;
    }
}
