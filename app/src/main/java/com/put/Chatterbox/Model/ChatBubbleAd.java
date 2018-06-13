package com.put.Chatterbox.Model;

import com.google.android.gms.ads.formats.NativeContentAdView;

/**
 * Created by Piotr on 2018-06-12.
 */

public class ChatBubbleAd extends ChatBubble {
    public ChatBubbleAd(String content, String senderId, NativeContentAdView adView ) {
        super(content, senderId);
        this.adView = adView;


    }
    public NativeContentAdView getAdView() {
        return adView;
    }

    public void setAdView(NativeContentAdView adView) {
        this.adView = adView;
    }

    private NativeContentAdView adView;

}
