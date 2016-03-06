package mcvicar.askacuban.util;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import mcvicar.askacuban.model.Comment;
import mcvicar.askacuban.model.Item;
import mcvicar.askacuban.model.User;

/**
 * Created by davidmcvicar on 3/5/16.
 */
public class ApiJsonParser {

    private static final String DEBUG_TAG = "ApiCaller Debug: ";

    public static List<Item> readItemArray(InputStream content) {
        JsonReader reader = null;
        ArrayList<Item> items = new ArrayList<Item>();
        try {
            reader = new JsonReader(new InputStreamReader(content));
            reader.beginArray();
            while(reader.hasNext()) {
                items.add(readItem(reader));
            }
            reader.endArray();
            return items;
        } catch( IOException ioe){
            return null;
        } finally {
            try{ if(reader != null) reader.close(); } catch (IOException ioe) {} //fail quietly
        }
    }

    public static Item readItem(JsonReader reader) {
        Item newItem = new Item();
        try {
            reader.beginObject();
            while(reader.hasNext()) {
                String name = reader.nextName();
                if("id".equals(name)) {
                    newItem.setId(reader.nextInt());
                } else if("user_id".equals(name)) {
                    newItem.setUser_id(reader.nextInt());
                } else if("title".equals(name)) {
                    newItem.setTitle(reader.nextString());
                } else if("content".equals(name)) {
                    try {
                        newItem.setContent(reader.nextString());
                    } catch (IllegalStateException ise) {
                        reader.nextNull();
                        newItem.setContent("");
                    }
                } else if("disabled".equals(name)) {
                    newItem.setDisabled(reader.nextBoolean());
                } else if("comments_count".equals(name)) {
                    newItem.setComments_count(reader.nextInt());
                } else if("upvotes_count".equals(name)) {
                    newItem.setUpvotes_count(reader.nextInt());
                } else if("score".equals(name)) {
                    newItem.setScore(reader.nextInt());
                } else if("rank".equals(name)) {
                    newItem.setRank(reader.nextInt());
                } else if("created_at".equals(name)) {
                    newItem.setCreated_at(reader.nextString());
                } else if("updated_at".equals(name)) {
                    newItem.setUpdated_at(reader.nextString());
                } else if("user".equals(name)) {
                    reader.beginObject();
                    reader.nextName();
                    newItem.setUsername(reader.nextString());
                    reader.endObject();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return newItem;
        } catch( IOException ioe){
            return null;
        }
    }

    public static User readUser(InputStream content) {
        JsonReader reader = null;
        reader = new JsonReader(new InputStreamReader(content));
        User newUser = new User();
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if("id".equals(name)) {
                    newUser.setId(reader.nextInt());
                } else if("username".equals(name)) {
                    newUser.setUsername(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return newUser;
        } catch (IOException ioe) {
            return null;
        }
    }

    public static List<Comment> readCommentArray(InputStream content) {
        Log.d(DEBUG_TAG, "Entering ReadCommentArray");
        JsonReader reader = null;
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {
            reader = new JsonReader(new InputStreamReader(content));
            reader.beginObject();
            while(reader.hasNext()) {
                String name = reader.nextName();
                Log.d(DEBUG_TAG,name);
                if ("comments".equals(name)) {
                    reader.beginArray();
                    while(reader.hasNext())
                        comments.add(readComment(reader));
                    reader.endArray();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return comments;
        } catch( IOException ioe) {
            Log.d(DEBUG_TAG, "Exception Throwm");
            return null;
        } finally {
            try{ if(reader != null) reader.close(); } catch (IOException ioe) {} //fail quietly
        }
    }

    public static Comment readComment(JsonReader reader) {
        Log.d(DEBUG_TAG, "Entering ReadComment");
        Comment newComment = new Comment();
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                Log.d(DEBUG_TAG,name);
                if("content".equals(name))
                    newComment.setContent(reader.nextString());
                else
                    reader.skipValue();
            }
            reader.endObject();
            return newComment;
        } catch (IOException ioe) {
            Log.d(DEBUG_TAG, "Exception Throwm");
            return null;
        }
    }
}
