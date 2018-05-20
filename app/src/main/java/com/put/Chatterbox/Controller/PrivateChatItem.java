package com.put.Chatterbox.Controller;

/**
 * Created by A on 21.05.2018.
 */

public class PrivateChatItem {


    private boolean hasAvatar;
    private String name;
    private String sender;
    private String lastMessage;


    private String chatId;

    public PrivateChatItem(String name, String sender, String lastMessage, String chatId) {
        this.name = name;
        this.sender = sender;
        this.lastMessage = lastMessage;
        this.chatId=chatId;
        this.hasAvatar = false;
    }

    public boolean HasAvatar() {
        return hasAvatar;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return sender + ": " + lastMessage;
    }

    public String getChatId() {
        return chatId;
    }


}
