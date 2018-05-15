package com.put.Chatterbox.Model;

public class Message {
    private String content;
    private String userId;
    private long timestamp;

    public Message(String content, String userId, long timestamp) {
        this.content = content;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
