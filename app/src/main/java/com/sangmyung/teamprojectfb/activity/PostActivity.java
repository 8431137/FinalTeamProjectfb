package com.sangmyung.teamprojectfb.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sangmyung.teamprojectfb.R;
import com.sangmyung.teamprojectfb.view.Postinfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Postinfo postinfo = (Postinfo) getIntent().getSerializableExtra("postinfo");
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(postinfo.getTitle());

        TextView depositTextView =findViewById(R.id.depositTextView);
        depositTextView.setText(postinfo.getDeposit());

        TextView createdAtTextView = findViewById(R.id.createdAtTextView);
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(postinfo.getCreatedAt()));

        LinearLayout contentsLayout = findViewById(R.id.contentsLayout);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<String> contentsList =postinfo.getContents();

        if (contentsLayout.getTag() == null || !contentsLayout.getTag().equals(contentsList)) {
            contentsLayout.setTag(contentsList);
            contentsLayout.removeAllViews();
            for (int i = 0; i < contentsList.size(); i++) {
                String contents = contentsList.get(i);
                if (Patterns.WEB_URL.matcher(contents).matches() && contents.contains("https://firebasestorage.googleapis.com/v0/b/teamprojectfb-b1a8c.appspot.com/o/posts")) {
                    ImageView imageView = new ImageView(this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    contentsLayout.addView(imageView);
                    Glide.with(this).load(contents).override(1000).thumbnail(0.1f).into(imageView);
                } else {
                    TextView textView = new TextView(this);
                    textView.setLayoutParams(layoutParams);
                    textView.setText(contents);
                    textView.setTextColor(Color.rgb(0, 0, 0));
                    contentsLayout.addView(textView);
                }
            }
        }
    }

}

