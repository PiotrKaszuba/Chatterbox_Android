package com.put.Chatterbox.Model;

/**
 * Created by A on 15.05.2018.
 */

public class ChatBubble {

    private String content;
    private String senderId;

    public ChatBubble(String content, String senderId) {
        this.content = content;
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public String getSenderId() {
        return senderId;
    }
}
