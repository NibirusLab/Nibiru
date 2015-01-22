package nibiruslab.org.nibiru;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

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
    }

    public Bitmap takeScreenshot(){
        rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = rootView.getDrawingCache();

        return bitmap;
    }

    public String saveBitmap(Bitmap bitmap){
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/Nibiru/Nibirus Images/IMG-" + nameImage() + "-Nibirus.png");
        FileOutputStream fos;
        File folder = new File(Environment.getExternalStorageDirectory() + "/Nibiru/Nibirus Images/");
        if (!folder.exists()){
            folder.mkdirs();
        }
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
        Intent share;
        if (!packageExist()){
            share = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.no_twitter)));
            startActivity(Intent.createChooser(share, getResources().getString(R.string.tweet_title)));
            return;
        }
        share = new Intent(Intent.ACTION_SEND);
        share.setPackage("com.twitter.android");
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.tweet));
        Uri screenshotUri = Uri.parse(imagePath);
        share.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(share, getResources().getString(R.string.tweet_title)));
    }

    public boolean packageExist(){
        PackageManager pm = getPackageManager();

        try{
            pm.getPackageInfo("com.twitter.android", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public String nameImage(){
        Date date = new Date();
        String nameDate = "";

        nameDate += String.valueOf(date.getDay());
        nameDate += String.valueOf(date.getMonth());
        nameDate += String.valueOf(date.getYear());
        nameDate += String.valueOf(date.getHours());
        nameDate += String.valueOf(date.getMinutes());
        nameDate += String.valueOf(date.getSeconds());

        return nameDate;
    }

    @Override
    public void onClick(View v) {
        Bitmap bitmap = takeScreenshot();
        String imagePath = saveBitmap(bitmap);
        shareOnTwitter(imagePath);
    }
}
