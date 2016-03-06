package mcvicar.askacuban.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import mcvicar.askacuban.R;
import mcvicar.askacuban.model.Item;
import mcvicar.askacuban.model.ItemArrayAdapter;
import mcvicar.askacuban.util.ApiJsonParser;

public class ListItemsActivity extends ActionBarActivity {

    private final String DEBUG_TAG = "Debug: ListItemActivity";

    private ListAdapter itemsAdapter;
    private ListView itemsView;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        username = getIntent().getExtras().getString("username");

        itemsView = (ListView)findViewById(R.id.questions_list);
        new GetItems().execute();
    }

    public void postQuestionClick(View view) {
        Intent postQuestionIntent = new Intent(this, PostItemActivity.class);
        postQuestionIntent.putExtra("username",username);
        startActivityForResult(postQuestionIntent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        new GetItems().execute();
    }

    public class GetItems extends AsyncTask<Void, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(Void... params) {
            URL apiURL;
            HttpURLConnection conn = null;
            try {
                apiURL = new URL(getString(R.string.items_view_url));
                conn = (HttpURLConnection) apiURL.openConnection();
                BufferedInputStream content = new BufferedInputStream(conn.getInputStream());
                return ApiJsonParser.readItemArray(content);
            } catch(IOException ioe) {
                Log.d(DEBUG_TAG,"Exception Thrown");
                return null;
            } finally {
                if(conn != null) conn.disconnect();
            }
        }

        protected void onPostExecute(List<Item> result) {
            itemsAdapter = new ItemArrayAdapter(getBaseContext(),result);
            itemsView.setAdapter(itemsAdapter);
        }
    }
}
