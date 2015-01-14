package nibiruslab.org.nibiru;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class Map extends Activity {

    private WebView map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = (WebView) findViewById(R.id.webView);
        map.loadUrl("https://www.google.es");
    }

}
