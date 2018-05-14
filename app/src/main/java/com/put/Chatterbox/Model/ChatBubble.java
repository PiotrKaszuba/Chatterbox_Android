package com.put.Chatterbox.Model;

/**
 * Created by A on 15.05.2018.
 */

public class ChatBubble {

    private String content;
    private long senderId;

    public ChatBubble(String content, long senderId) {
        this.content = content;
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public long getSenderId() {
        return senderId;
    }
}
