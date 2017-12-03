package com.example.kai.mParrot;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;


public class helpActivity extends Activity  {
	WebView myBrowser;
    // TTS Stuff
    TTSManager ttsManager = null;
    public String cleanPub = " i am empty ";
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        myBrowser = (WebView)findViewById(R.id.helpView);
        myBrowser.getSettings().setJavaScriptEnabled(true);
        myBrowser.setWebChromeClient(new WebChromeClient());
        myBrowser.getSettings().setSupportZoom(true);
        myBrowser.getSettings().setBuiltInZoomControls(true);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.allowFileSchemeCookies();
        CookieManager.setAcceptFileSchemeCookies(true);
// tts
        ttsManager = new TTSManager();
        ttsManager.init(this);
/* An instance of this class will be registered as a JavaScript interface */
        class MyJavaScriptInterface
        {
            @SuppressWarnings("unused")
            @JavascriptInterface

            public void processHTML(String html) {
                Document doc = Jsoup.parse(html);
               // Element content = doc.getElementById("tw-passagedata");
               // Log.d("html", "is content" + content);
               // String clean = Jsoup.clean(html, "", Whitelist.simpleText(), new Document.OutputSettings().prettyPrint(false));
                String clean = Jsoup.clean(html, "", Whitelist.simpleText(), new Document.OutputSettings().prettyPrint(false));

                cleanPub = clean;
                Log.d("html", "from javaScript cleanPub value" + cleanPub);
              ttsManager.initQueue(cleanPub);

            }
        }


        final WebView myBrowser = (WebView)findViewById(R.id.helpView);
  /* JavaScript must be enabled if you want it to work, obviously */
        myBrowser.getSettings().setJavaScriptEnabled(true);

  /* Register a new JavaScript interface called HTMLOUT */
        myBrowser.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

  /* WebViewClient must be set BEFORE calling loadUrl! */
        myBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

        /* This call inject JavaScript into the page which just finished loading. */
       myBrowser.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });

/* load a web page */
        myBrowser.loadUrl("file:///android_asset/readme.html");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      ttsManager.shutDown();
    }

}
