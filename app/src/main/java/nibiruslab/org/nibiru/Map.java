package nibiruslab.org.nibiru;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Map extends Activity implements View.OnClickListener {

    private WebView map;
    private ImageButton shareButton;
    private ShareActionProvider myShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map);

        shareButton = (ImageButton) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(this);

        map = (WebView) findViewById(R.id.webView);
        map.getSettings().setJavaScriptEnabled(true);
        map.setWebChromeClient(new WebChromeClient());
        map.addJavascriptInterface(new Object() {
            public void handshake() {}

            public void handshake(String json) {}
        }, "Android");
        map.loadUrl("http://diskdejorge.myds.me:3000/");
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        myShareActionProvider = (ShareActionProvider)item.getActionProvider();
        myShareActionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        myShareActionProvider.setShareIntent(createShareIntent());
        return true;
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, "https://github.com/NibirusLab/Nibiru/blob/master/app/src/main/res/drawable/ic_launcher.png");
        startActivity(shareIntent);
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
                //share();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Goodbye", Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }
    }

    public void share(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, "");
    }*/

    public void screenshot(){
        String mPath = /*Environment.getExternalStorageDirectory().toString()*/"/storage/Android" + "/" + "photography_name.jpg";

        Toast.makeText(this, Environment.getExternalStorageDirectory().toString(), Toast.LENGTH_LONG).show();

        // create bitmap screen capture
        Bitmap bitmap;
        View view = getWindow().getDecorView().getRootView();
        /*view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0,0, view.getMeasuredWidth(), view.getMeasuredHeight());*/

        view.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(view.getDrawingCache());

        view.setDrawingCacheEnabled(false);

        OutputStream fout = null;
        File imageFile = new File(mPath);

        try {
            imageFile.createNewFile();
            fout = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
            fout.flush();
            fout.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, mPath, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        screenshot();
    }
}
