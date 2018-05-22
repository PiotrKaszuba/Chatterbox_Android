package com.put.Chatterbox.Controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.put.Chatterbox.R;

import java.util.List;

/**
 * Created by A on 22.05.2018.
 */

public class PrivateChatLobbyAdapter extends ArrayAdapter<PrivateChatItem>{
    private Activity activity;

    public PrivateChatLobbyAdapter(Activity context, int resource, List<PrivateChatItem> objects) {
        super(context, resource, objects);
        this.activity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        PrivateChatItem item = getItem(position);

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.private_chat_lobby_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

//                if(!item.HasAvatar())
//                {}
        holder.avatar.setImageResource(R.mipmap.ic_launcher_round);
        holder.name.setText(item.getName());
        holder.lastMessage.setText(item.getLastMessage());

        return convertView;
    }


    private class ViewHolder {
        private ImageView avatar;
        private TextView name;
        private TextView lastMessage;

        public ViewHolder(View v) {
            avatar = (ImageView) v.findViewById(R.id.avatar);
            name = (TextView) v.findViewById(R.id.chat_name);
            lastMessage = (TextView) v.findViewById(R.id.last_message);
        }
    }
}
