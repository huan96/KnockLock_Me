package com.zergitas.teamb.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.zergitas.teamb.myapplication.R;
import com.zergitas.teamb.myapplication.service.LockScreenService;
import com.zergitas.teamb.myapplication.until.FileManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView btnChangeWallpage, btnSetPass, btnRate, btnMoreApps;
    ImageView btnNew, btnShare;
    ToggleButton sw24h, swShowGrid, swLock;
    Boolean lockState, is24hFormat, isShowGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();
        initViews();
        setStateViews();
        setOnClick();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                myIntent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(myIntent, 101);
            }
        }
    }

    private void setOnClick() {
        btnChangeWallpage.setOnClickListener(this);
        btnSetPass.setOnClickListener(this);
        btnRate.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        btnMoreApps.setOnClickListener(this);
        swLock.setOnClickListener(this);
        sw24h.setOnClickListener(this);
        swShowGrid.setOnClickListener(this);
    }

    private void initViews() {
        btnChangeWallpage = (TextView) findViewById(R.id.btn_change_wallpaper);
        btnSetPass = (TextView) findViewById(R.id.btn_change_pass);
        btnRate = (TextView) findViewById(R.id.btn_rate);
        btnMoreApps = (TextView) findViewById(R.id.btn_more_app);
        btnShare = (ImageView) findViewById(R.id.btn_share);
        btnNew = (ImageView) findViewById(R.id.btn_new);
        swLock = (ToggleButton) findViewById(R.id.sw_on_of);
        sw24h = (ToggleButton) findViewById(R.id.sw_24h);
        swShowGrid = (ToggleButton) findViewById(R.id.sw_knock_grid);
    }

    private void setStateViews() {
        lockState = FileManager.getLockState(this);
        is24hFormat = FileManager.get24hFormatTimeState(this);
        isShowGrid = FileManager.getShowGrid(this);

        if (lockState) {
            swLock.setChecked(true);
        } else {
            swLock.setChecked(false);
        }

        if (is24hFormat) {
            sw24h.setChecked(true);
        } else {
            sw24h.setChecked(false);
        }

        if (isShowGrid) {
            swShowGrid.setChecked(true);
        } else {
            swShowGrid.setChecked(false);
        }


    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_change_wallpaper:
                    gotoChangeWapperActivity();
                    break;
                case R.id.btn_change_pass:
                    checkPass();
                    break;
                case R.id.btn_share:
                    shareApp();
                    break;
                case R.id.sw_on_of:
                    changeStateLock();
                    break;
                case R.id.sw_24h:
                    changeState24hFormat();
                    break;
                case R.id.sw_knock_grid:
                    changeShowGridKnock();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.have_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void changeShowGridKnock() {

        if (swShowGrid.isChecked()) {
            FileManager.saveShowGridState(true, this);
        } else {
            FileManager.saveShowGridState(false, this);
        }

    }

    private void changeState24hFormat() {
        if (sw24h.isChecked()) {
            FileManager.save24hFormatTimeState(true, this);
        } else {
            FileManager.save24hFormatTimeState(false, this);
        }
    }

    private void changeStateLock() {
        if (swLock.isChecked()) {
            startService();
            FileManager.saveLockState(true, this);
        } else {
            stopService();
            FileManager.saveLockState(false, this);
        }
    }

    private void startService() {
        Intent intent = new Intent(this, LockScreenService.class);
        startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(this, LockScreenService.class);
        stopService(intent);
    }

    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app!");
        startActivity(shareIntent);
    }

    private void gotoChangeWapperActivity() {
        Intent intent = new Intent(this, ChangeWallpaperActivity.class);
        startActivity(intent);
    }

    private void checkPass() {
        String pass = FileManager.getPassword(this);
        if (pass.equals("")) {
            Intent intent = new Intent(this, NewPasswordActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CheckOldPasswordActivity.class);
            startActivity(intent);
        }
    }
}
