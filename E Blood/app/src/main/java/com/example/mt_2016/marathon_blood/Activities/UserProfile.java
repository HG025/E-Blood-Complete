package com.example.mt_2016.marathon_blood.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mt_2016.marathon_blood.R;

public class UserProfile extends AppCompatActivity {
    ImageView imageView;
    Toolbar toolbar;
    TextView name, email, gender, bloodgroup;
    String userName,userimage,useremail,usergender,userbloodg;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        iniitializeViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataFromIntent();
        setDatatoFields();
        collapasAndExpandToolbar();
    }

    private void collapasAndExpandToolbar() {

        collapsingToolbarLayout.setTitle(userName);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.cardview_light_background));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    private void setDatatoFields() {
        Glide.with(UserProfile.this).load(userimage).into(imageView);
        name.setText(" " + userName);
        email.setText(" " + useremail);
        gender.setText(" " + usergender);
        bloodgroup.setText(" " + userbloodg);
    }

    private void getDataFromIntent() {
        userName = getIntent().getStringExtra("username");
        userimage = getIntent().getStringExtra("userimage");
        useremail = getIntent().getStringExtra("useremail");
        usergender = getIntent().getStringExtra("usergender");
        userbloodg = getIntent().getStringExtra("friend_bG");
    }

    private void iniitializeViews() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.maincollapsing);
        toolbar = (Toolbar) findViewById(R.id.maintoolbar);
        imageView = (ImageView) findViewById(R.id.mainbackdrop);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);
        bloodgroup = (TextView) findViewById(R.id.bgroups);
    }
}
