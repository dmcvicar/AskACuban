package mcvicar.askacuban.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import mcvicar.askacuban.R;

public class ListCommentsActivity extends ActionBarActivity {

    private ListAdapter commentsAdapter;
    private ListView commentsView;
    private String selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comments);

        //selectedItemId = getIntent().getExtras().getString("");

        commentsAdapter = new ArrayAdapter<>(this,R.layout.list_item_layout,
                R.id.list_item,getResources().getStringArray(R.array.test_comments));
        commentsView = (ListView)findViewById(R.id.comments_list);
        commentsView.setAdapter(commentsAdapter);
    }

}
