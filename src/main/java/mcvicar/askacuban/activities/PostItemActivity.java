package mcvicar.askacuban.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mcvicar.askacuban.R;

public class PostItemActivity extends ActionBarActivity {

    TextView usernameView;
    EditText questionView;
    EditText titleView;
    String question;
    String username;
    String title;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        username = getIntent().getExtras().getString("username");
        userId = getIntent().getExtras().getInt("userId");

        usernameView = (TextView) findViewById(R.id.username);
        titleView = (EditText) findViewById(R.id.title);
        questionView = (EditText) findViewById(R.id.question);
        usernameView.setText(getString(R.string.usernamelabel) + username);
    }

    public void postItem(View view) {
        title = titleView.getText().toString();
        question = questionView.getText().toString();
        String jsonData = String.format(getString(R.string.new_item_body),
                userId,
                title,
                question
        );
        new PostItemToServer().execute(jsonData);
    }

    public class PostItemToServer extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            URL apiURL;
            HttpURLConnection conn = null;
            BufferedOutputStream output;
            String jsonData = params[0];
            byte[] messageBody = jsonData.getBytes();
            try {
                apiURL = new URL("@string/items_new_url");
                conn = (HttpURLConnection) apiURL.openConnection();
                conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0);
                output = new BufferedOutputStream(conn.getOutputStream());
                output.write(messageBody);
                if(conn.getResponseCode() == 200)
                    return true;
                else
                    return false;
            } catch(Exception e) {
                return false;
            } finally {
                if(conn != null) conn.disconnect();
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
            Intent listItemsIntent = new Intent(getBaseContext(), ListItemsActivity.class);
            listItemsIntent.putExtra("username",username);
            listItemsIntent.putExtra("userId", userId);
            startActivity(listItemsIntent);
        }
    }
}
