package com.put.Chatterbox.Controller;


import android.net.Uri;
import android.net.Uri.Builder;
/**
 * Created by Piotr on 2018-04-18.
 */

public class ChatController {




    public static void messageToWebserv(String message){
        message = message.replaceAll("[^a-zA-Z-_ ]", "");
        String[] words = message.split(" ");

        Uri.Builder urib = new Uri.Builder();
        urib.scheme("https")
                .authority("adkeywordsresolver.azurewebsites.net");

        for (String s:
             words) {
            urib.appendQueryParameter("color",s);
        }
        System.out.println(urib.toString());
    }

}
