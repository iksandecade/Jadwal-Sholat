package com.example.iksandecade.jadwalsholat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.iksandecade.jadwalsholat.adapter.CalendarPagerAdapter;
import com.example.iksandecade.jadwalsholat.eventbus.Events;
import com.example.iksandecade.jadwalsholat.eventbus.GlobalBus;
import com.example.iksandecade.jadwalsholat.greendao.DaoHandler;
import com.example.iksandecade.jadwalsholat.greendao.DaoSession;
import com.example.iksandecade.jadwalsholat.greendao.Jadwal;
import com.example.iksandecade.jadwalsholat.greendao.JadwalDao;
import com.example.iksandecade.jadwalsholat.service.NotificationService;
import com.example.iksandecade.jadwalsholat.utils.SPJadwalSholat;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by iksandecade on 24/02/17.
 */

public class CalendarV2Activity extends AppCompatActivity {

    TabLayout tabLayout;
    TextView tvShalat;
    TextView tvRemaining;
    ViewPager vpCalendar;
    CalendarPagerAdapter calendarPagerAdapter;
    DaoSession daoSession;
    List<Jadwal> jadwalList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_ver2);
        vpCalendar = (ViewPager) findViewById(R.id.vpCalendar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tvRemaining = (TextView) findViewById(R.id.tvRemaining);
        tvShalat = (TextView) findViewById(R.id.tvShalat);
        daoSession = DaoHandler.getInstance(this);

        jadwalList = daoSession.getJadwalDao().queryBuilder().where(JadwalDao.Properties.Filter.between(getNow(), getNow() + ((24 * 60 * 60 * 1000))*7), JadwalDao.Properties.Region.eq(SPJadwalSholat.getKota(this))).list();
        tabLayout.addTab(tabLayout.newTab().setText("today"));
        tabLayout.addTab(tabLayout.newTab().setText("today"));

        calendarPagerAdapter = new CalendarPagerAdapter(this, jadwalList);
        vpCalendar.setAdapter(calendarPagerAdapter);
        addNotification();
        startService(new Intent(getBaseContext(), NotificationService.class));

    }

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBus.getEventBus().register(this);
        vpCalendar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setScrollPosition(position, positionOffset, true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpCalendar.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void gotoSetting(View view) {
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
        finish();
    }

    private long getNow() {
        long result = 0;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MM dd");
        String dateTime = simpleDateFormat.format(date);
        try {
            Date parsedDate = simpleDateFormat.parse(dateTime);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            result = timestamp.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getMessage(Events.ActivityActivityMessage activityActivityMessage) {
        tvRemaining.setText(activityActivityMessage.getMessage());
        tvShalat.setText(activityActivityMessage.getSecondMessage());

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getMessage(Events.DayMessage dayMessage) {
        tabLayout.getTabAt(0).setText(dayMessage.getDay());
    }

    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getEventBus().unregister(this);
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Dzuhur")
                        .setContentText("10 minute remaining");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
