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

    static ArrayList<PrivateChatItem> privateChatItemArrayList = new ArrayList<PrivateChatItem>();
    static String lastMessage="";
    static String senderId;

    public static void privateChatsJoin(final PrivateChatActivity instance)
    {

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference privateChatsRef = databaseReference.child("PrivateChats");
        privateChatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (final DataSnapshot dsPrivateChatId : dataSnapshot.getChildren()) {
                    final String uiddb1 = (String) dsPrivateChatId.child("idUser1").getValue();
                    final String uiddb2 = (String) dsPrivateChatId.child("idUser2").getValue();
                    if (userId.equals(uiddb1)) {
                        DatabaseReference messagesRef = databaseReference.child("ChannelMessages");
                        messagesRef./*equalTo(dsPrivateChatId.getKey()).*/addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot dsSenderIdLastMessage : dataSnapshot.getChildren()) {

                                    if (dsSenderIdLastMessage.getKey().equals(dsPrivateChatId.getKey())) {
                                        for (DataSnapshot dsSenderIdLastMessageInner : dsSenderIdLastMessage.getChildren())
                                        {
                                            Query lastQuery = databaseReference.child("ChannelMessages").child(dsSenderIdLastMessage.getKey()).child(dsSenderIdLastMessageInner.getKey()).orderByKey().limitToLast(3);
                                            lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                System.out.println("lastMessagesnapshot:" + dataSnapshot.toString());
                                                lastMessage = (String) dataSnapshot.child("content").getValue();
                                                senderId = (String) dataSnapshot.child("senderId").getValue();
                                                DatabaseReference usersRef = databaseReference.child("Users");
                                                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        String chatName = "";
                                                        String senderName = "";
                                                        ArrayList<PrivateChatItem> privateChatItemList = new ArrayList<>();
                                                        for (DataSnapshot dsUserId : dataSnapshot.getChildren()) {
                                                            System.out.println("get usernames");
                                                            if (uiddb2.equals(dsUserId.getKey()))
                                                            {
                                                                chatName = (String) dsUserId.child("username").getValue();
                                                                System.out.println(chatName);
                                                            }

                                                            if (senderId.equals(dsUserId.getKey()))
                                                            {
                                                                senderName = (String) dsUserId.child("username").getValue();
                                                                System.out.println(senderName);
                                                            }

                                                            if (!chatName.equals("") && !senderName.equals("")) {
                                                                PrivateChatItem privateChatItem = new PrivateChatItem(chatName, senderName, lastMessage, dsPrivateChatId.getKey());
                                                                privateChatItemArrayList.add(privateChatItem);
                                                                privateChatItemList.add(privateChatItem);
                                                                chatName="";
                                                                senderName="";
                                                            }
                                                        }
                                                         instance.updatePrivateChats(privateChatItemList);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                //Handle possible errors.
                                            }
                                        });

                                    } }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });

                    }
                    if (userId.equals(uiddb2))
                    {
                        DatabaseReference messagesRef = databaseReference.child("ChannelMessages");
                        messagesRef.equalTo(dsPrivateChatId.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot dsSenderIdLastMessage : dataSnapshot.getChildren())
                                {
                                    final String senderId = (String) dsSenderIdLastMessage.child("senderId").getValue();
                                    final String lastMessage = (String) dsSenderIdLastMessage.child("content").getValue();
                                    if (dsSenderIdLastMessage.getKey().equals(dsPrivateChatId.getKey())) {
                                        DatabaseReference usersRef = databaseReference.child("Users");
                                        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String chatName = "";
                                                String senderName = "";
                                                ArrayList<PrivateChatItem> privateChatItemList = new ArrayList<>();
                                                for (DataSnapshot dsUserId : dataSnapshot.getChildren()) {
                                                    if (uiddb1.equals(dsUserId.getKey()))
                                                        chatName = (String) dsUserId.child("username").getValue();
                                                    if (senderId.equals(dsUserId.getKey()))
                                                        senderName = (String) dsUserId.child("username").getValue();
                                                    if (chatName != null && senderName != null) {
                                                        PrivateChatItem privateChatItem = new PrivateChatItem(chatName, senderName, lastMessage, dsPrivateChatId.getKey());
                                                        privateChatItemArrayList.add(privateChatItem);
                                                    }
                                                }
                                                // instance.updatePrivateChats(privateChatItemList);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }


                }
               // instance.updatePrivateChats(privateChatItemArrayList);
                privateChatItemArrayList.clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
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
        while (iter1.hasNext() && iter2.hasNext()) {
            Map.Entry<String, String> e1 = iter1.next();
            Map.Entry<String, String> e2 = iter2.next();

            int a=0;

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
            }


        }

        ArrayList<PrivateChatItem> privateChatItemList = new ArrayList<>();

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

