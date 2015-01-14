package nibiruslab.org.nibiru;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import nibiruslab.org.nibiru.Database.Database;


public class MainActivity extends Activity {

    private AutoCompleteTextView editText;
    private Database db;
    private ArrayList<String> hashtags;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        db = new Database(this);
        hashtags = new ArrayList<>();
//        getActionBar().setDisplayShowTitleEnabled(false);
//        getActionBar().setDisplayShowHomeEnabled(true);

        editText = (AutoCompleteTextView) findViewById(R.id.editText);
        editText.setHintTextColor(getResources().getColor(R.color.grey));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, hashtags);
        editText.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        db.newHashtag(String.valueOf(editText.getText()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshArrayHashtags();
        adapter.notifyDataSetChanged();
    }

    private void refreshArrayHashtags() {
        hashtags.clear();
        Cursor cursor = db.getAll();
        while (cursor.moveToNext()) {
            hashtags.add(cursor.getString(1));
        }
    }
}
