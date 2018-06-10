package com.put.Chatterbox.Model;

public class PrivateChannel {
    private String idUser1;
    private String idUser2;
    private Long lastMessageTimestamp;

    public PrivateChannel(String idUser1, String idUser2, Long lastMessageTimestamp) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public PrivateChannel() {
    }

    public String getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
    }

    public Long getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Long lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }
}
