package com.example.android.sos;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class selfHelping extends AppCompatActivity {
ViewPager viewPager;
    CustomSwipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_self_helping);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter= new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);
    }
}
