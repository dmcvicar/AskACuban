package mcvicar.askacuban;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by davidmcvicar on 3/5/16.
 */
public class CallAPI extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        URL apiURL;
        HttpURLConnection conn = null;
        try {
            apiURL = new URL(params[0]);
            conn = (HttpURLConnection) apiURL.openConnection();
            BufferedInputStream content = new BufferedInputStream(conn.getInputStream());
            String result = parseResultStream(content);
            return result;
        } catch(Exception e) {
            return "API call failed\n" + e.getMessage();
        } finally {
            if(conn != null) conn.disconnect();
        }
    }

    protected void onPostExecute(String result) {
        //put result somewhere
    }

    private String parseResultStream(BufferedInputStream content) {
        byte[] contents = new byte[1024];

        int bytesRead;
        String result = "";
        try {
            while ((bytesRead = content.read(contents)) != -1) {
                result = new String(contents, 0, bytesRead);
            }
        } catch(IOException ioe) {
            return "Parsing results failed";
        }
        return result;
    }
}