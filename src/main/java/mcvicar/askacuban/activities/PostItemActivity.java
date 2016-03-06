package mcvicar.askacuban.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import mcvicar.askacuban.R;

public class PostItemActivity extends ActionBarActivity {

    private final String DEBUG_TAG = "Debug: PostItemActivity";

    TextView usernameView;
    EditText questionView;
    EditText titleView;
    String question;
    String username;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        username = getIntent().getExtras().getString("username");

        usernameView = (TextView) findViewById(R.id.username);
        titleView = (EditText) findViewById(R.id.title);
        questionView = (EditText) findViewById(R.id.question);
        usernameView.setText(getString(R.string.usernamelabel) + username);
    }

    public void postItem(View view) {
        title = titleView.getText().toString();
        question = questionView.getText().toString();
        new PostItemToServer().execute();
    }

    public class PostItemToServer extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            URL apiURL;
            HttpURLConnection conn = null;
            JsonWriter writer = null;
            boolean retVal = false;
            try {
                apiURL = new URL(String.format(getString(R.string.items_new_url),username.replace(" ","%20")));
                conn = (HttpURLConnection) apiURL.openConnection();
                conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                writer = new JsonWriter(new OutputStreamWriter(new BufferedOutputStream(conn.getOutputStream())));
                writer.beginObject();
                writer.name("item").beginObject();
                writer.name("title").value(title);
                writer.name("content").value(question);
                writer.endObject();
                writer.endObject();
                writer.close();
                Log.d(DEBUG_TAG,"URL: " + apiURL.toString());
                Log.d(DEBUG_TAG,"Error Code: " + conn.getResponseCode());
                if(conn.getResponseCode() == 200) {
                    conn.disconnect();
                    retVal = true;
                } else {
                    conn.disconnect();
                }
            } catch(Exception e) {
                return retVal;
            } finally {
                if(conn != null) conn.disconnect();
                try { if(writer != null) writer.close(); } catch (IOException ioe){} //fail quietly
                return retVal;
            }
        }

        protected void onPostExecute(Boolean result) {
            Toast postToast;
            if(result) {
                postToast = Toast.makeText(getBaseContext(),"Post Success!",Toast.LENGTH_SHORT);
            } else {
                postToast = Toast.makeText(getBaseContext(),"Post Failed",Toast.LENGTH_SHORT);
            }
            postToast.show();
            finish();
        }
    }
}
