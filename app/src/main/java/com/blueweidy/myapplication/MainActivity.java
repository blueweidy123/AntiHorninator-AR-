package com.blueweidy.myapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        //checkBatteryPermission();

        //Database_sqlite database_sqlite = new Database_sqlite(this);
        //set statusBar color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.color_BG_white));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

//region MAIN MENU ITEM

        sNavigationDrawer = findViewById(R.id.navDrawer);
        //menu add
        List<com.shrikanthravi.customnavigationdrawer2.data.MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new com.shrikanthravi.customnavigationdrawer2.data.MenuItem("Thời khóa biểu",R.drawable.m3));
        menuItems.add(new com.shrikanthravi.customnavigationdrawer2.data.MenuItem("Thời gian biểu",R.drawable.m6));
        menuItems.add(new com.shrikanthravi.customnavigationdrawer2.data.MenuItem("Đặt lời nhắc",R.drawable.m2));
        menuItems.add(new com.shrikanthravi.customnavigationdrawer2.data.MenuItem("Chế độ tập trung",R.drawable.adv));
        sNavigationDrawer.setMenuItemList(menuItems);

        fragmentClass = Fragment1.class;
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                    android.R.animator.fade_out).replace(R.id.framelayout, fragment).commit();
        }
        sNavigationDrawer.setOnMenuItemClickListener(position -> {
            switch (position){
                case 0:{
                    fragmentClass = Fragment1.class;
                    break;
                }
                case 1:{
                    fragmentClass = Frag3_notes.class;
                    break;
                }
                case 2:{
                    fragmentClass = Frag2_RepeatMode.class;
                    break;
                }
                case 3:{
                    fragmentClass = Fragment4.class;
                    break;
                }
            }
        });

        sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {
            @Override
            public void onDrawerOpening() {
            }
            @Override
            public void onDrawerClosing() {
                new Thread(() -> {
            try {
                fragment = (Fragment)fragmentClass.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            if(fragment!=null){
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out).replace(R.id.framelayout, fragment).commit();
            }
                }).start();
            }
            @Override
            public void onDrawerOpened() {
            }
            @Override
            public void onDrawerClosed() {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
//endregion

        //checkPMS();
    }

    //region Press back again to exit function
    private boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit){
            finish();
        }else {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
            exit=true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3*1000);
        }
    }
//endregion

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }

    }
    @SuppressLint("BatteryLife")
    public void checkBatteryPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)){
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package" + packageName));
                startActivity(intent);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // You don't have permission
                checkPermission();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
