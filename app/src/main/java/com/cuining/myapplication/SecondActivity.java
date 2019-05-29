package com.cuining.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.meb.tracker.aop.ActivityTrace;

@ActivityTrace(pageId = "222")
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void jumpPage(View view) {
        startActivity(new Intent(this, ThirdActivity.class));
    }
}
