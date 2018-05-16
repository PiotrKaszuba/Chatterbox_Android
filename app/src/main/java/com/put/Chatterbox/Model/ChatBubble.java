package com.put.Chatterbox.Model;

/**
 * Created by A on 15.05.2018.
 */

public class ChatBubble {

    private String content;
    private String senderId;
    private Long timestamp;

    public ChatBubble() {}

    public ChatBubble(String content, String senderId ) {
        this.content = content;
        this.senderId = senderId;

    }

    public String getContent() {
        return content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
