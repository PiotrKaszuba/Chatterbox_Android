package com.put.Chatterbox.Controller;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.put.Chatterbox.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class chatsView extends AppCompatActivity {

    final static int columns = 2;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ConstraintLayout constraintLayout = new ConstraintLayout(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.TOP;
        layoutParams.weight = 4f;

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,100);
        layoutParams1.gravity = Gravity.BOTTOM;
        layoutParams1.weight = 1f;


        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setLayoutParams(layoutParams);
        // ConstraintLayout.LayoutParams scrollParams = new ConstraintLayout.LayoutParams();
        //scrollParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
        //  scrollParams.width= LinearLayout.LayoutParams.MATCH_PARENT;
        //   scrollView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        //ScrollView scrollView1 = (ScrollView) findViewById(R.id.scrollView1);


       /* ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.constrainHeight(scrollView.getId(),ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainWidth(scrollView.getId(),ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.applyTo(constraintLayout);
        constraintLayout.addView(scrollView);*/

        TextView test = new TextView(this);
        test.setText("testTesttest");
        test.setGravity(Gravity.CENTER);
        test.setLayoutParams(layoutParams1);

        TextView chatText = new TextView(this);
        chatText.setGravity(Gravity.CENTER);
        chatText.setBackgroundResource(R.drawable.chat);


        ListView listView = new ListView(this);
        listView.setDividerHeight(0);

        List<String> messages = new ArrayList<String>();
        for(int i=0;i<20;i++)
        {
            messages.add("sampleText"+i);
        }
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this,,messages);


        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(2);
        grid.setBackgroundColor(Color.GRAY);


        linearLayout.addView(scrollView);
        linearLayout.addView(test);
        scrollView.addView(grid);
        prepareView(makeList(),grid);
        setContentView(linearLayout);//R.layout.activity_main);
    }

    private List<String> makeList() {

        List<String> labels = new ArrayList<String>();
        for(int i=0;i<20;i++)
        {
            labels.add("sampleText"+i);
        }
        /*labels.add("First");
        labels.add("Second");
        labels.add("Third");
        labels.add("Fourth");
        labels.add("Fifth");*/

        return labels;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void prepareView(List<String> labels, GridLayout grid) {


       /* grid.setColumnCount(2);
        grid.setBackgroundColor(Color.GRAY);*/
        for (int i = 0; i < labels.size(); i++) {
            TextView text;
            text = new TextView(this);
            text.setText(labels.get(i));
            text.setTypeface(null, Typeface.BOLD);
            text.setBackgroundColor(Color.WHITE);
            text.setGravity(Gravity.CENTER);
            text.setBackgroundResource(R.drawable.pobrane);


            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setGravity(Gravity.CENTER);
            params.columnSpec = GridLayout.spec(i % columns, 1f);
            params.rowSpec = GridLayout.spec(i / columns);
            params.setMargins(5, 5, 5, 5);
            text.setLayoutParams(params);
            grid.addView(text, i);
        }


    }
}