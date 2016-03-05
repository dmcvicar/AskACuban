package mcvicar.askacuban.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import mcvicar.askacuban.R;
import mcvicar.askacuban.model.Item;
import mcvicar.askacuban.util.ApiJsonParser;

public class ListItemsActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListAdapter itemsAdapter;
    private ListView itemsView;
    private String username;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        username = getIntent().getExtras().getString("username");
        userId = getIntent().getExtras().getInt("userId");

        itemsView = (ListView)findViewById(R.id.questions_list);
        itemsView.setOnItemClickListener(this);
        new GetItems().execute(username);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //selectedItem = itemsAdapter.getItem(position);
        System.out.println(view.toString());
        Intent viewCommentsIntent = new Intent(this, ListCommentsActivity.class);
        //viewCommentsIntent.putExtra("itemId",selectedItem.id());
        startActivity(viewCommentsIntent);
    }

    public void postQuestionClick(View view) {
        Intent postQuestionIntent = new Intent(this, PostItemActivity.class);
        postQuestionIntent.putExtra("username",username);
        postQuestionIntent.putExtra("userId",userId);
        startActivity(postQuestionIntent);
    }

    public class GetItems extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            URL apiURL;
            HttpURLConnection conn = null;
            List<Item> items;
            String[] itemTitles;
            try {
                apiURL = new URL(getString(R.string.items_view_url));
                conn = (HttpURLConnection) apiURL.openConnection();
                BufferedInputStream content = new BufferedInputStream(conn.getInputStream());
                items = ApiJsonParser.readItemArray(content);
                itemTitles = new String[items.size()];
                for(int i=0;i<items.size();i++) {
                    itemTitles[i] = items.get(i).getTitle();
                }
                return itemTitles;
            } catch(IOException ioe) {
                String[] exceptionThrown = {"Exception thrown", ioe.getMessage()};
                return exceptionThrown;
            } finally {
                if(conn != null) conn.disconnect();
            }
        }

        protected void onPostExecute(String[] result) {
            itemsAdapter = new ArrayAdapter<>(getBaseContext(),R.layout.list_item_layout,
                    R.id.list_item,result);
            itemsView.setAdapter(itemsAdapter);
        }
    }
}
