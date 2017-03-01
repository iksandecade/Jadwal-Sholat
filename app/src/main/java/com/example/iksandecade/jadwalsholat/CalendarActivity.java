//package com.example.iksandecade.jadwalsholat;
//
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//
//import com.example.iksandecade.jadwalsholat.adapter.CalendarPagerAdapter;
//import com.example.iksandecade.jadwalsholat.greendao.DaoHandler;
//import com.example.iksandecade.jadwalsholat.greendao.DaoSession;
//import com.example.iksandecade.jadwalsholat.greendao.Jadwal;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class CalendarActivity extends AppCompatActivity {
//
//    ViewPager vpCalendar;
//    Toolbar toolbar;
//    TabLayout tabLayout;
//    List<Jadwal> jadwalList = new ArrayList<>();
//    CalendarPagerAdapter calendarPagerAdapter;
//    DaoSession daoSession;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calendar);
//        toolbar = (Toolbar) findViewById(R.id.toolBar);
//        vpCalendar = (ViewPager) findViewById(R.id.vpCalendar);
//        daoSession = DaoHandler.getInstance(this);
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//
//        jadwalList = daoSession.getJadwalDao().queryBuilder().list();
//        for (int i = 0; i < jadwalList.size(); i++) {
//            long time = jadwalList.get(i).getId();
//            Date date = new Date(time);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd");
//            String dateTime = simpleDateFormat.format(date);
//            tabLayout.addTab(tabLayout.newTab().setText(dateTime));
//        }
//        calendarPagerAdapter = new CalendarPagerAdapter(this, jadwalList);
//        setSupportActionBar(toolbar);
//        vpCalendar.setAdapter(calendarPagerAdapter);
//        vpCalendar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                tabLayout.setScrollPosition(position, positionOffset, true);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                vpCalendar.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }
//}
