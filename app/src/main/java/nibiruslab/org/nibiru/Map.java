package nibiruslab.org.nibiru;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;


public class Map extends Activity implements View.OnClickListener {

    private WebView map;
    private ImageButton shareButton;
    private View rootView;

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

        map.setWebChromeClient(new WebChromeClient());
        map.addJavascriptInterface(new Object() {
            public void handshake() {}

            public void handshake(String json) {}
        }, "Android");
        map.loadUrl("http://diskdejorge.myds.me:3000/");

        //map.loadUrl("http://diskdejorge.myds.me:3000/");

        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://diskdejorge.myds.me:3000/"));
        //map.getContext().startActivity(intent);
        //finish();
    }

    public Bitmap takeScreenshot(){
        rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = rootView.getDrawingCache();

        return bitmap;
    }

    public String saveBitmap(Bitmap bitmap){
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        rootView.setDrawingCacheEnabled(false);
        return imagePath.toString();
    }

    public void shareOnTwitter(String imagePath){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setPackage("com.twitter.android");
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TEXT, "I´m using gi#Nibiru");
        Uri screenshotUri = Uri.parse(imagePath);
        share.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(share, "Share image using"));
    }

    @Override
    public void onClick(View v) {
        Bitmap bitmap = takeScreenshot();
        String imagePath = saveBitmap(bitmap);
        shareOnTwitter(imagePath);
    }
}
