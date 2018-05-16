package com.put.Chatterbox.Controller;

/**
 * Created by A on 15.05.2018.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.put.Chatterbox.Model.ChatBubble;
import com.put.Chatterbox.R;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatBubble> {

    private Activity activity;
    private List<ChatBubble> messages;
    private String userId;

    public ChatAdapter(Activity context, int resource, List<ChatBubble> objects, String userId) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
        this.userId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        ChatBubble chatBubble = getItem(position);

        int viewType = getItemViewType(position);


        if (!userId.equals(chatBubble.getSenderId())) {
            layoutResource = R.layout.chat_layout_left;
        } else {
            layoutResource = R.layout.chat_layout_right;
        }

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //set message content
        holder.messageTextView.setText(chatBubble.getContent());

        return convertView;
    }

    @Override
    public ChatBubble getItem(int position){
        ChatBubble chatBubble;
        chatBubble = messages.get(position);
        return chatBubble;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime. Value 2 is returned because of left and right views.
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        //return position % 2;
        return ArrayAdapter.IGNORE_ITEM_VIEW_TYPE;
    }



    private class ViewHolder {
        private TextView messageTextView;

        public ViewHolder(View v) {
            messageTextView = (TextView) v.findViewById(R.id.txt_msg);
        }
    }
}
