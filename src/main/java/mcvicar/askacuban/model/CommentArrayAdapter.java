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
 * Created by davidmcvicar on 3/6/16.
 */
public class CommentArrayAdapter extends ArrayAdapter<Comment> {
        private final Context context;
        private final List<Comment> values;

        public CommentArrayAdapter(Context context, List<Comment> values ) {
            super(context,-1,values);
            this.context = context;
            this.values = values;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View rowView = inflater.inflate(R.layout.comment_list_element_layout, parent, false);

            TextView titleText = (TextView) rowView.findViewById(R.id.title);
            titleText.setText(values.get(position).getContent());

            return rowView;
        }
}
