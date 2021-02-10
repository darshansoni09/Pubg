package com.example.pubgtournament.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pubgtournament.Adapters.myContestsAdapter;
import com.example.pubgtournament.Adapters.pagerAdapter;
import com.example.pubgtournament.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Joined_Contests extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined__contests);

        toolbar=findViewById(R.id.toolbar_joined_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Contests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout=findViewById(R.id.tab_layout_joined_user);
        viewPager=findViewById(R.id.viewpager_joined_user);

        myContestsAdapter pagerAdapter = new myContestsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);



    }
}
