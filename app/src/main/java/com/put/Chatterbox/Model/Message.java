package com.put.Chatterbox.Model;

public class Message {
    private String content;
    private String senderId;
    private long timestamp;

    public Message(String content, String userId, long timestamp) {
        this.content = content;
        this.senderId = userId;
        this.timestamp = timestamp;
    }

    public Message() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String userId) {
        this.senderId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
