package nibiruslab.org.nibiru;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;


public class Map extends Activity {

    private WebView map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map);

        map = (WebView) findViewById(R.id.webView);
        map.getSettings().setJavaScriptEnabled(true);
        map.loadUrl("http://192.168.1.15:3000");
    }

}
