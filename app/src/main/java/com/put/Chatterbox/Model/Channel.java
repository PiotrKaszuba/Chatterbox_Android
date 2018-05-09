package com.put.Chatterbox.Model;

/**
 * Created by Piotr on 2018-04-26.
 */

public class Channel {
    public String channelName;
    public double lastMessageTimestamp;

    public Channel(){

    }
    public Channel(String channelName, double lastMessageTimestamp)
    {
        this.channelName=channelName;
        this.lastMessageTimestamp=lastMessageTimestamp;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Channel)) return false;
        Channel o = (Channel) obj;
        return o.channelName.equals(this.channelName);
    }
}
