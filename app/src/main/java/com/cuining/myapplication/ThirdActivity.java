package com.cuining.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.meb.tracker.aop.ActivityTrace;

@ActivityTrace(pageId = "333")
public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }
}
