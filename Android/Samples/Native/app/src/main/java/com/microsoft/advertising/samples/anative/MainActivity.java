package com.microsoft.advertising.samples.anative;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.microsoft.advertising.AdControl;
import com.microsoft.advertising.nativeAd.NativeAd;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private AdControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RelativeLayout adContainer =     (RelativeLayout)this.findViewById(R.id.adsContainer);
        control = new AdControl(this, "test", "test", "native", adContainer);

        control.registerListener(new com.microsoft.advertising.NativeAdControlListener() {
            @Override
            public void onAdReady(String adObject, final NativeAd nativeAd) {
                ((TextView)findViewById(R.id.adTitle)).setText(nativeAd.title);

                if (nativeAd.mainImages != null && nativeAd.mainImages.length > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.get().load(nativeAd.mainImages[0].source).into((ImageView) findViewById(R.id.adImage));
                        }
                    });
                }

                control.showAd(adObject);
            }

            @Override
            public void onAdError(String errorObject) {
                control.destroy();
                control = null;
            }

            @Override
            public void onAdEnd(String adObject) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        control.destroy();
        control = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
