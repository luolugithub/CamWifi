package com.amoszhang.camwifi;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    public ClientThread client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Button event listeners
        Button button_left = (Button) findViewById(R.id.button_left);
        Button button_back = (Button) findViewById(R.id.button_back);
        Button button_forward = (Button) findViewById(R.id.button_forward);
        Button button_right = (Button) findViewById(R.id.button_right);
        button_left.setOnTouchListener(ontouch);
        button_back.setOnTouchListener(ontouch);
        button_forward.setOnTouchListener(ontouch);
        button_right.setOnTouchListener(ontouch);

        // Set WebView
        WebView webview = (WebView) findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.loadUrl("http://192.168.1.100:8081/javascript_simple.html");

        // Start Client thread
        client = new ClientThread();
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.send("exit\n");
        client.exit = true;
    }

    // OnTouchListeners for 4 buttons
    OnTouchListener ontouch = new OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int button = v.getId();
            int action = event.getAction();
            Log.d("DEBUG",((Button)v).getText().toString()+": " + MotionEvent.actionToString(action));
            // Press down or press up
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    // Different responses for 4 buttons
                    switch(button){
                        case R.id.button_left:
                            client.send("LEFT\n");
                            break;
                        case R.id.button_back:
                            client.send("BACK\n");
                            break;
                        case R.id.button_forward:
                            client.send("FORWARD\n");
                            break;
                        case R.id.button_right:
                            client.send("RIGHT\n");
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    client.send("STOP\n");
                    break;
            }

            return false;
        }
    };

}
