package com.microsoft.advertising.samples.videointerstitial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.microsoft.advertising.AdControl;

public class MainActivity extends AppCompatActivity {

    private AdControl control;
    private String adObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        RelativeLayout adContainer =     (RelativeLayout)this.findViewById(R.id.videoAdsContainer);
        control = new AdControl(this, "test", "test", "videoInterstitial", adContainer);

        control.registerListener(new com.microsoft.advertising.AdControlListener() {
            @Override
            public void onAdReady(String returnedAdObject) {

                adObject = returnedAdObject;

                // The ad is ready. Play it whenever you want.
                control.showAd(returnedAdObject);
            }

            @Override
            public void onAdError(String errorObject) {
                // There was an error fetching/rendering the ad.

                control.destroy();
                control = null;
            }

            @Override
            public void onAdEnd(String adObject) {
                // The video has ended.

                control.destroy();
                control = null;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (this.adObject != null) {
            control.pauseAd(this.adObject);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.adObject != null) {
            control.resumeAd(this.adObject);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        control.destroy();
        control = null;
    }
}