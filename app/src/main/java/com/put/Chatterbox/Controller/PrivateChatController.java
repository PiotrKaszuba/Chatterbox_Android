package com.put.Chatterbox.Controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PrivateChatController {

    static Map<String,String> senderIdLastMessageMap = new ConcurrentHashMap<String,String>();
    static Map<String,String> userChatIdMap = new ConcurrentHashMap<String,String>();
    //static Map<String,String> senderNameChatName = new HashMap<String,String>();

    static Map<String,String> userIdUsername = new ConcurrentHashMap<String,String>();


    static ArrayList<PrivateChatItem> privateChatItemList = new ArrayList<PrivateChatItem>();


    public static void fillPrivateChatItem(final PrivateChatActivity instance) {
        // 1. sprawdzenie listy czatow uzytkownika (chatID) //mam
        // 2. z kim pisal ok // mam id
        // 3. last wiadomosc // mam tekst
        // 4. kto wyslal last wiadomosc // mam id


        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference privateChatsRef = databaseReference.child("PrivateChats");
        privateChatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uiddb1 = (String) ds.child("idUser1").getValue();
                    String uiddb2 = (String) ds.child("idUser2").getValue();
                    // wypelnienie mapy userId z ktorym prowadziny czat i chatId
                    if (userId.equals(uiddb1)) userChatIdMap.put(uiddb2, ds.getKey());
                    if (userId.equals(uiddb2)) userChatIdMap.put(uiddb1, ds.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        // uzupelnienie w mapie id sendera i ostatniej wiadomosci
        DatabaseReference lastMessageRef = databaseReference.child("ChannelMessages");
        lastMessageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    int a=0;

                    for (Map.Entry<String, String> entry : userChatIdMap.entrySet()) {
                        if (entry.getValue().equals(ds.getKey())) {
                            int c=0;
                            Query lastQuery = databaseReference.child("ChannelMessages").child(entry.getValue()).orderByKey().limitToLast(1);
                            lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    int d=0;
                                    // wpisanie do mapy id sendera i tresc wiadomosci na tej samej pozycji co w mapie userChatIdMap
                                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                        int t=0;
                                        senderIdLastMessageMap.put((String) ds.child("senderId").getValue(), (String) ds.child("content").getValue());
                                        int b = 0;
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //Handle possible errors.
                                }
                            });
                        }
                    }
                    int g=0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // pobranie mapy userId - username
        DatabaseReference userRef = databaseReference.child("Users");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userIdUsername.put(ds.getKey(), (String) ds.child("username").getValue());
                }
                int a=0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //zamiana userId na username w mapach


        Iterator<Map.Entry<String, String>> iter1 = userChatIdMap.entrySet().iterator();
        Iterator<Map.Entry<String, String>> iter2 = senderIdLastMessageMap.entrySet().iterator();
        while (iter1.hasNext() || iter2.hasNext()) {
            Map.Entry<String, String> e1 = iter1.next();
            Map.Entry<String, String> e2 = iter2.next();

            for (Map.Entry<String, String> entry : userIdUsername.entrySet()) {
                int b=0;
                if (entry.getKey().equals(e1.getKey())) {
                    int c=0;
                    userChatIdMap.put(entry.getValue(), userChatIdMap.get(e1.getValue()));
                    userChatIdMap.remove(e1.getKey());
                    int h=0;
                }
                if (entry.getKey().equals(e2.getKey())) {
                    int d=0;
                    senderIdLastMessageMap.put(entry.getValue(), senderIdLastMessageMap.get(e2.getValue()));
                    senderIdLastMessageMap.remove(e2.getKey());
                }
                int a=0;
            }

        }
        //wypelnienie listy z privateChatItems
        Iterator<Map.Entry<String, String>> i1 = userChatIdMap.entrySet().iterator();
        Iterator<Map.Entry<String, String>> i2 = senderIdLastMessageMap.entrySet().iterator();
        while (i1.hasNext() || i2.hasNext()) {
            Map.Entry<String, String> e1 = i1.next();
            Map.Entry<String, String> e2 = i2.next();

            PrivateChatItem privateChatItem = new PrivateChatItem(e1.getKey(),e2.getKey(),e2.getValue(),e1.getValue());
            privateChatItemList.add(privateChatItem);

        }
        instance.updatePrivateChats(privateChatItemList);

    }









    public static void checkUserIdChatId(PrivateChatActivity instance)
    {
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        int a=0;
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference privateChatsRef = databaseReference.child("PrivateChats");
        privateChatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uiddb1 = (String) ds.child("idUser1").getValue();
                    String uiddb2 = (String) ds.child("idUser2").getValue();
                    // wypelnienie mapy userId z ktorym prowadziny czat i chatId
                    if (userId.equals(uiddb1)) userChatIdMap.put(uiddb2, ds.getKey());
                    if (userId.equals(uiddb2)) userChatIdMap.put(uiddb1, ds.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

    public static void checkSenderIdLastMessage(PrivateChatActivity instance)
    {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference lastMessageRef = databaseReference.child("ChannelMessages");
        lastMessageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    for (Map.Entry<String, String> entry : userChatIdMap.entrySet()) {
                        if (entry.getValue().equals(ds.getKey())) {
                            Query lastQuery = databaseReference.child("ChannelMessages").child(entry.getValue()).orderByKey().limitToLast(1);
                            lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    // wpisanie do mapy id sendera i tresc wiadomosci na tej samej pozycji co w mapie userChatIdMap
                                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                        senderIdLastMessageMap.put((String) ds.child("senderId").getValue(), (String) ds.child("content").getValue());
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //Handle possible errors.
                                }
                            });
                        }
                    }
                    int g=0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void checkUserIdUsername(PrivateChatActivity instance)
    {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = databaseReference.child("Users");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userIdUsername.put(ds.getKey(), (String) ds.child("username").getValue());
                }
                int a=0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void changeUserIdUsername(PrivateChatActivity instance) {
        Iterator<Map.Entry<String, String>> iter1 = userChatIdMap.entrySet().iterator();
        Iterator<Map.Entry<String, String>> iter2 = senderIdLastMessageMap.entrySet().iterator();
        while (iter1.hasNext() || iter2.hasNext()) {
            Map.Entry<String, String> e1 = iter1.next();
            Map.Entry<String, String> e2 = iter2.next();

            for (Map.Entry<String, String> entry : userIdUsername.entrySet()) {
                int b=0;
                if (entry.getKey().equals(e1.getKey())) {
                    int c=0;
                    userChatIdMap.put(entry.getValue(), userChatIdMap.get(e1.getKey()));
                    userChatIdMap.remove(e1.getKey());
                    int h=0;
                }
                if (entry.getKey().equals(e2.getKey())) {
                    int d=0;
                    senderIdLastMessageMap.put(entry.getValue(), senderIdLastMessageMap.get(e2.getKey()));
                    senderIdLastMessageMap.remove(e2.getKey());
                }
                int a=0;
            }


        }


        //wypelnienie listy z privateChatItems
        Iterator<Map.Entry<String, String>> i1 = userChatIdMap.entrySet().iterator();
        Iterator<Map.Entry<String, String>> i2 = senderIdLastMessageMap.entrySet().iterator();
        while (i1.hasNext() && i2.hasNext()) {
            Map.Entry<String, String> e1 = i1.next();
            Map.Entry<String, String> e2 = i2.next();

            PrivateChatItem privateChatItem = new PrivateChatItem();
            if(e1 != null && e2!= null)
            {
               // privateChatItem = new PrivateChatItem(e1.getKey(),e2.getKey(),e2.getValue(),e1.getValue());
                privateChatItem.setName(e1.getKey());
                privateChatItem.setLastMessage(e2.getKey());
                privateChatItem.setLastMessage(e2.getValue());
                privateChatItem.setChatId(e1.getValue());
            }
            int a=0;
            privateChatItemList.add(privateChatItem);

        }
        instance.updatePrivateChats(privateChatItemList);

    }
    }

