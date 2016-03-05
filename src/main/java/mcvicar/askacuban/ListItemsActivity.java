package mcvicar.askacuban;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListItemsActivity extends ActionBarActivity {

    private ListAdapter questionsAdapter;
    private ListView questionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        //questionsAdapter = new SimpleCursorAdapter(this,R.layout.activity_list_items,null,null,null,0);
        questionsAdapter = new ArrayAdapter<>(this,R.layout.activity_list_items,
                R.id.list_item,getResources().getStringArray(R.array.test_questions));
        questionsView = (ListView)findViewById(R.id.questions_list);
        questionsView.setAdapter(questionsAdapter);
    }

    public void viewComments(View view) {

    }
}
