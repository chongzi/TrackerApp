package com.cuining.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cuining.myapplication.aop.TestAnnoTrace;
import com.meb.tracker.TrackerSDK;
import com.meb.tracker.aop.ActivityTrace;
import com.meb.tracker.aop.ClickTrace;
import com.meb.tracker.aop.NavigationTrace;

import java.util.ArrayList;

@ActivityTrace(pageId = "111")
public class FirstActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("------------_>");

        ViewPager viewPager = findViewById(R.id.viewPager);

        mFragments.add(new FirstFragment());
        mFragments.add(new SecondFragment());
        mFragments.add(new ThirdFragment());

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                            jumpPage(null);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        ).start();
    }


    @NavigationTrace(fromPageId = "123", toPageId = "234")
    public void jumpPage(View view) {
        startActivity(new Intent(this, SecondActivity.class));
//        xixi();
    }

    @ClickTrace(eventId = "234")
    public void xixi() {
        Toast.makeText(this, "xixi", Toast.LENGTH_SHORT).show();
    }

    public void nol(View view) {
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
