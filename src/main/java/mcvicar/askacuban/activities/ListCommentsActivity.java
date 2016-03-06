package mcvicar.askacuban.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mcvicar.askacuban.R;
import mcvicar.askacuban.model.Comment;
import mcvicar.askacuban.model.CommentArrayAdapter;
import mcvicar.askacuban.model.Item;
import mcvicar.askacuban.model.ItemArrayAdapter;
import mcvicar.askacuban.util.ApiJsonParser;

public class ListCommentsActivity extends ActionBarActivity {

    private final static String DEBUG_TAG = "ListCommentsActivity";

    private ListAdapter commentsAdapter;
    private ListView commentsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comments);

        //selectedItemId = getIntent().getExtras().getString("");

        commentsAdapter = new ArrayAdapter<>(this,R.layout.comment_list_element_layout,
                R.id.list_item,getResources().getStringArray(R.array.test_comments));
        commentsView = (ListView)findViewById(R.id.comments_list);
        new GetComments().execute(getIntent().getExtras().getInt("item_id"));
    }

    private class GetComments extends AsyncTask<Integer, Void, List<Comment>> {

        @Override
        protected List<Comment> doInBackground(Integer... params) {
            URL apiURL;
            HttpURLConnection conn = null;
            try {
                apiURL = new URL(String.format(getString(R.string.comments_view_url),params[0]));
                conn = (HttpURLConnection) apiURL.openConnection();
                BufferedInputStream content = new BufferedInputStream(conn.getInputStream());
                return ApiJsonParser.readCommentArray(content);
            } catch(IOException ioe) {
                Log.d(DEBUG_TAG,"Exception Thrown");
                return null;
            } finally {
                if(conn != null) conn.disconnect();
            }
        }

        protected void onPostExecute(List<Comment> result) {
            ArrayList<String> comments = new ArrayList<>();
            for(Comment comment: result) {
                comments.add(comment.getContent());
                Log.d(DEBUG_TAG,"Comment: " + comment.getContent());
            }
            commentsAdapter = new ArrayAdapter<>(getBaseContext(),R.layout.comment_list_element_layout, comments);
            commentsView.setAdapter(commentsAdapter);
        }
    }

}
