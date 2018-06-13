package com.put.Chatterbox.Controller;

/**
 * Created by A on 15.05.2018.
 */

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.google.android.gms.ads.internal.gmsg.HttpClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.put.Chatterbox.Model.ChatBubble;
import com.put.Chatterbox.Model.ChatBubbleAd;
import com.put.Chatterbox.Model.Message;
import com.put.Chatterbox.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ChatActivity extends AppCompatActivity {

    private ListView listView;
    private View btnSend;
    private EditText editText;
    private  String userId;
    private String chatId;
    private String chatType;
    private List<ChatBubble> chatBubbles;
    private ArrayAdapter<ChatBubble> adapter;
    private AdLoader adLoader;
    private int counter;
    private String adCat="";
    private StringBuilder tempWords;
    private final Context instance;

    public ChatActivity(){
        instance = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();

        counter=0;
         adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
        .forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
            @Override
            public void onContentAdLoaded(NativeContentAd contentAd) {

                NativeContentAdView adView = (NativeContentAdView) getLayoutInflater()
                        .inflate(R.layout.native_ad_left, null);

                TextView headlineView = adView.findViewById(R.id.txt_msg);
                headlineView.setText(contentAd.getHeadline());
                adView.setHeadlineView(headlineView);

                if(contentAd.getImages().size() >0) {
                    ImageView image = adView.findViewById(R.id.img);
                    image.setImageDrawable(contentAd.getImages().get(0).getDrawable());
                    //image.setImageLevel(10);
                    adView.setImageView(image);
                }


                TextView contentad_advertiser = adView.findViewById(R.id.contentad_advertiser);
                contentad_advertiser.setText(contentAd.getAdvertiser());
                adView.setAdvertiserView(contentad_advertiser);


                TextView body = adView.findViewById(R.id.contentad_body);
                body.setText(contentAd.getBody());
                adView.setBodyView(body);

                TextView call = adView.findViewById(R.id.contentad_call_to_action);
                call.setText(contentAd.getCallToAction());
                adView.setCallToActionView(call);
                if(contentAd.getLogo() !=null) {
                    ImageView logo = adView.findViewById(R.id.contentad_logo);
                    logo.setImageDrawable(contentAd.getLogo().getDrawable());
                    adView.setLogoView(logo);
                }
                ChatBubbleAd cba = new ChatBubbleAd(null, userId, adView);
                Bundle g = contentAd.getExtras();

                cba.setTimestamp(System.currentTimeMillis());
                TextView timestamp = adView.findViewById(R.id.time);
                String time = cba.getTimestamp();
                timestamp.setText(time);

                adView.setNativeAd(contentAd);
                adapter.add(cba);
                adapter.notifyDataSetChanged();

            }
        }).withNativeAdOptions((new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build()
                )).build();

        //

        //
        chatId = intent.getStringExtra("chatId");
        chatType = intent.getStringExtra("chatType");

        chatBubbles = new ArrayList<>();



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference channelReference = databaseReference.child(chatType).child(chatId);

       channelReference.orderByChild("timestamp").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               ChatBubble chatBubble = dataSnapshot.getValue(ChatBubble.class);
               chatBubbles.add(chatBubble);
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });


        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println(ds.toString());
                    System.out.println(ds.child("content").getValue() + " " + (String) ds.child("senderId").getValue());

                    Message message = ds.getValue(Message.class);


                   // chatBubbles.add(new ChatBubble((String) ds.child("content").getValue(), (String) ds.child("senderId").getValue()));

                    

                    adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String uid = user.getUid();
            userId = uid; // brak danych = crash
        }

        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = findViewById(R.id.btn_chat_send);
        editText = (EditText) findViewById(R.id.msg_type);

        //set ListView adapter first

        adapter = new ChatAdapter(this, R.layout.chat_layout_left, chatBubbles, userId);
        listView.setAdapter(adapter);

        //event for button SEND
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatActivity.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list

                    //chatBubbles.add(new ChatBubble(editText.getText().toString(),userId));
                    //adapter.notifyDataSetChanged();
                    // [??] Wysylanie wiadomosci do bazy
                    String temp = editText.getText().toString();
                    writeNewMessage(userId,temp , System.currentTimeMillis(),
                           FirebaseDatabase.getInstance().getReference());


                    editText.setText("");
                    counter++;
                    tempWords.append(temp+" ");
                    if(counter >=9) getCategory(tempWords.toString());
                    if(counter >=10){


                        counter=0;
                        adLoader.loadAds(new AdRequest.Builder()
                                .addKeyword(adCat)
                                .addTestDevice("52CFC76193B00F793EAD31B14D7EED9E").build(), 1);
                    }
                    Toast.makeText(instance, "Ad request with keyword: " +adCat, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getCategory(String words){
        StringBuilder sb = new StringBuilder("localhost:8080/AdKeywordsResolver/get?message=");
        sb.append(words);
        Request rq = new Request.Builder().url(sb.toString()).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(rq).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                adCat=response.body().string();
            }
        });
    }
    private void writeNewMessage(String senderId, String content, Long timestamp, DatabaseReference mDatabase) {

        Message message = new Message(content, senderId, timestamp);


        mDatabase.child(chatType).child(chatId).push().setValue(message);
    }
}

