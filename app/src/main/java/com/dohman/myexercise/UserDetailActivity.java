package com.dohman.myexercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserDetailActivity extends AppCompatActivity {


    private ImageView image;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Intent intent = getIntent();


        image = findViewById(R.id.imageMain);
        title = findViewById(R.id.textLoginName);


        User user = (User) getIntent().getExtras().getSerializable("USER_DETAILS");


        if (user != null) {
            Glide.with(this).load(user.getAvatar_url()).into(image);
            title.setText(user.getLogin());
        }
    }
}
