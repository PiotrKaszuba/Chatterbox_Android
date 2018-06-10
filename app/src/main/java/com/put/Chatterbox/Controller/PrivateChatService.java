package com.put.Chatterbox.Controller;

import android.content.Context;
import android.content.Intent;
import android.media.AsyncPlayer;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.put.Chatterbox.Model.PrivateChannel;

public class PrivateChatService extends AsyncTask <Void,Void,Void> {

    String uid1;
    String uid2;
    DatabaseReference databaseReference;
    Context context;
    boolean chatInDb = false;
    String keyChatInDb;

    public PrivateChatService(String u1, String u2, DatabaseReference databaseReference,Context context) {
        this.uid1 = u1;
        this.uid2 = u2;
        this.databaseReference = databaseReference;
        this.context = context;

        final Context myContext = context;

        DatabaseReference chatsRef = databaseReference.child("PrivateChats");
        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println( ds.toString());

                    String uiddb1 = (String) ds.child("idUser1").getValue();
                    String uiddb2 = (String) ds.child("idUser2").getValue();

                    System.out.println("User1: " + uiddb1 + "     User2: " + uiddb2);

                    if(uid1.equals(uiddb1) || uid1.equals(uiddb2) && uid2.equals(uiddb1) || uid2.equals(uiddb2)) {

                        chatInDb = true;
                        keyChatInDb = ds.getKey();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });



    }

    @Override
    protected void onPreExecute() {
        /*System.out.println("Sprawdzanie czatow dla zalogowanego uzytkownika z bazy danych onPreExecute");
        DatabaseReference chatsRef = databaseReference.child("PrivateChats");
        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println( ds.toString());

                    String uiddb1 = (String) ds.child("idUser1").getValue();
                    String uiddb2 = (String) ds.child("idUser2").getValue();

                    System.out.println("User1: " + uiddb1 + "     User2: " + uiddb2);

                    if(uid1.equals(uiddb1) || uid1.equals(uiddb2) && uid2.equals(uiddb1) || uid2.equals(uiddb2)) {

                        chatInDb = true;
                        String key = ds.getKey();
                        int a=0;
                        Intent i = new Intent(context, ChatActivity.class);
                        i.putExtra("chatId", key);
                        i.putExtra("chatType","ChannelMessages");
                        System.out.println("Start ChatActivity doInBackground");
                        context.startActivity(i);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });*/
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        System.out.println("Skonczenie sprawdzania czatow jak nie ma tworzy nowy");
        if(!chatInDb) {
            PrivateChannel privateChannel = new PrivateChannel(uid1, uid2, System.currentTimeMillis());


            String key = databaseReference.child("PrivateChats").push().getKey();
            databaseReference.child("PrivateChats").child(key).setValue(privateChannel);


            int a = 0;

            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("chatId", key);
            i.putExtra("chatType", "ChannelMessages");
            System.out.println("Start ChatActivity  onPostExecute");
            context.startActivity(i);
        }
        else
        {
            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("chatId", keyChatInDb);
            i.putExtra("chatType", "ChannelMessages");
            System.out.println("Start ChatActivity  onPostExecute");
            context.startActivity(i);
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
       /* System.out.println("Sprawdzanie czatow dla zalogowanego uzytkownika z bazy danych onProgresUpdate");
        DatabaseReference chatsRef = databaseReference.child("PrivateChats");
        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println( ds.toString());

                    String uiddb1 = (String) ds.child("idUser1").getValue();
                    String uiddb2 = (String) ds.child("idUser2").getValue();

                    System.out.println("User1: " + uiddb1 + "     User2: " + uiddb2);

                    if(uid1.equals(uiddb1) || uid1.equals(uiddb2) && uid2.equals(uiddb1) || uid2.equals(uiddb2)) {

                        chatInDb = true;
                        String key = ds.getKey();
                        int a=0;
                        Intent i = new Intent(context, ChatActivity.class);
                        i.putExtra("chatId", key);
                        i.putExtra("chatType","ChannelMessages");
                        System.out.println("Start ChatActivity onProgressUpdate");
                        context.startActivity(i);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });*/
    }

    @Override
    protected void onCancelled(Void aVoid) {
        /*System.out.println("Skonczenie sprawdzania czatow jak nie ma tworzy nowy");
        if(!chatInDb) {
            PrivateChannel privateChannel = new PrivateChannel(uid1, uid2, System.currentTimeMillis());


            String key = databaseReference.child("PrivateChats").push().getKey();
            databaseReference.child("PrivateChats").child(key).setValue(privateChannel);


            int a = 0;

            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("chatId", key);
            i.putExtra("chatType", "ChannelMessages");
            System.out.println("Start ChatActivity  onPostExecute");
            context.startActivity(i);
        }
        else
        {
            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("chatId", keyChatInDb);
            i.putExtra("chatType", "ChannelMessages");
            System.out.println("Start ChatActivity  onPostExecute");
            context.startActivity(i);
        }*/
    }

    @Override
    protected void onCancelled() {
        /*System.out.println("Skonczenie sprawdzania czatow jak nie ma tworzy nowy");
        if(!chatInDb) {
            PrivateChannel privateChannel = new PrivateChannel(uid1, uid2, System.currentTimeMillis());


            String key = databaseReference.child("PrivateChats").push().getKey();
            databaseReference.child("PrivateChats").child(key).setValue(privateChannel);


            int a = 0;

            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("chatId", key);
            i.putExtra("chatType", "ChannelMessages");
            System.out.println("Start ChatActivity  onPostExecute");
            context.startActivity(i);
        }
        else
        {
            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("chatId", keyChatInDb);
            i.putExtra("chatType", "ChannelMessages");
            System.out.println("Start ChatActivity  onPostExecute");
            context.startActivity(i);
        }*/
    }

    @Override
    protected Void doInBackground(Void... voids) {
        /*System.out.println("Sprawdzanie czatow dla zalogowanego uzytkownika z bazy danych doInBackground");
        DatabaseReference chatsRef = databaseReference.child("PrivateChats");
        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println( ds.toString());

                    String uiddb1 = (String) ds.child("idUser1").getValue();
                    String uiddb2 = (String) ds.child("idUser2").getValue();

                    System.out.println("User1: " + uiddb1 + "     User2: " + uiddb2);

                    if(uid1.equals(uiddb1) || uid1.equals(uiddb2) && uid2.equals(uiddb1) || uid2.equals(uiddb2)) {

                        chatInDb = true;
                        if(chatInDb)
                        {
                            String keyChatInDb = ds.getKey();
                        }
                    }

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }


        });*/
        return null;
    }
}
