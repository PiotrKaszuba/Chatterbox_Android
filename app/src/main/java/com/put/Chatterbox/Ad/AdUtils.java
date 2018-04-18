package com.put.Chatterbox.Ad;


import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.put.Chatterbox.R;

/**
 * Created by Piotr on 2018-04-18.
 */

public class AdUtils {

    public static void InitializeAds(Context context){
        MobileAds.initialize(context, "ca-app-pub-1924213908299315~6546641683");

    }

    public static void addTestBanner(Context context){
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setId(Integer.parseInt("adView"));
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111"); // test app id
        // TODO: Add adView to your view hierarchy.
    }

    public static void loadTestBanner(Activity context){
        AdView mAdView = context.findViewById(Integer.parseInt("adView"));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
