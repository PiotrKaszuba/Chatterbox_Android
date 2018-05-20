package com.put.Chatterbox.Controller;

/**
 * Created by A on 21.05.2018.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.put.Chatterbox.R;

import java.util.List;

public class PrivateChatActivity extends AppCompatActivity {

    private ListView listView;
    private List<PrivateChatItem> privateChatItems;
    private ArrayAdapter<PrivateChatItem> privateChatItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);

    }
}
