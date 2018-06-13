package com.put.Chatterbox.Controller;

/**
 * Created by A on 21.05.2018.
 */

public class PrivateChatItem {


    private boolean hasAvatar;
    private String name; // z kim
    private String sender; // kto last wyslal
    private String lastMessage; // last message
    private String chatId; // chat id

    public PrivateChatItem(String name, String lastMessageSender, String lastMessage, String chatId) {
        this.name = name;
        this.sender = lastMessageSender;
        this.lastMessage = lastMessage;
        this.chatId=chatId;
        this.hasAvatar = false;
    }

    public PrivateChatItem() {
    }

    public void setHasAvatar(boolean hasAvatar) {
        this.hasAvatar = hasAvatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
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
