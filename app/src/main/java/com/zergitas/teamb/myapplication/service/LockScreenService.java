package com.zergitas.teamb.myapplication.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zergitas.teamb.myapplication.R;
import com.zergitas.teamb.myapplication.until.FileManager;

public class LockScreenService extends Service {
    private BroadcastReceiver screenOfReceiver;
    private Context mContext;
    private View viewLockScreen;
    private WindowManager windowManager;
    private LayoutInflater inflater;
    private WindowManager.LayoutParams params;
    private String password;
    private String temp;
    private boolean isTyping;
    int lengthPass;
    private Handler handler;
    private boolean isShowGrid, is24h;

    public LockScreenService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        inflater = LayoutInflater.from(mContext);
        viewLockScreen = inflater.inflate(R.layout.activity_lock_screen, null);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        params.format = PixelFormat.TRANSLUCENT;
        params.flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        try {
            password = FileManager.getPassword(this);
            lengthPass = password.length();
        } catch (Exception e) {
            windowManager.removeView(viewLockScreen);
        }
        temp = "";
        isTyping = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filterScreenOf = new IntentFilter();
        filterScreenOf.addAction(Intent.ACTION_SCREEN_OFF);
        screenOfReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                createLockScreen();
            }
        };
        registerReceiver(screenOfReceiver, filterScreenOf);
        return START_STICKY;
    }

    private void createLockScreen() {
        final TextView btnA, btnB, btnC, btnD, tv_notification;
        final TextClock tc_time;
        isShowGrid = FileManager.getShowGrid(mContext);
        is24h = FileManager.get24hFormatTimeState(mContext);
        ImageView imgBackground;
        imgBackground = (ImageView) viewLockScreen.findViewById(R.id.image_background);
        btnA = (TextView) viewLockScreen.findViewById(R.id.btn_a);
        btnB = (TextView) viewLockScreen.findViewById(R.id.btn_b);
        btnC = (TextView) viewLockScreen.findViewById(R.id.btn_c);
        btnD = (TextView) viewLockScreen.findViewById(R.id.btn_d);
        tc_time = (TextClock) viewLockScreen.findViewById(R.id.tv_time);
        if (is24h) {
            tc_time.setFormat24Hour("HH:mm");
            tc_time.setFormat12Hour(null);
        } else {
            tc_time.setFormat24Hour(null);
            tc_time.setFormat12Hour("h:MM");
        }
        if (!isShowGrid) {
            btnA.setText("");
            btnB.setText("");
            btnC.setText("");
            btnD.setText("");
            btnA.setBackground(getResources().getDrawable(R.drawable.bg_transparent));
            btnB.setBackground(getResources().getDrawable(R.drawable.bg_transparent));
            btnC.setBackground(getResources().getDrawable(R.drawable.bg_transparent));
            btnD.setBackground(getResources().getDrawable(R.drawable.bg_transparent));
        } else {
            btnA.setBackground(getResources().getDrawable(R.drawable.bg_grid));
            btnB.setBackground(getResources().getDrawable(R.drawable.bg_grid));
            btnC.setBackground(getResources().getDrawable(R.drawable.bg_grid));
            btnD.setBackground(getResources().getDrawable(R.drawable.bg_grid));
        }
        tv_notification = (TextView) viewLockScreen.findViewById(R.id.tv_notification);
        String uriBackground = FileManager.getUriImageBackground(this);
        Glide.with(this).load(uriBackground).into(imgBackground);
        viewLockScreen.setSystemUiVisibility(
//                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        try {
            windowManager.addView(viewLockScreen, params);
        } catch (Exception e) {
        }
        btnA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    addPass("A", tv_notification);

                }
                return true;
            }
        });
        btnB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    addPass("B", tv_notification);
                }
                return true;
            }
        });
        btnC.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    addPass("C", tv_notification);
                }
                return true;
            }
        });
        btnD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    addPass("D", tv_notification);
                }
                return true;
            }
        });
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (isTyping) {
                            sleep(5000);
                            isTyping = false;
                            temp = "";
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        handler = new Handler();
    }

    private void addPass(String a, final TextView tv_noti) {
        isTyping = true;
        if (temp.length() < lengthPass && isTyping) {
            temp += a;
        }
        if (password.equals(temp)) {
            windowManager.removeView(viewLockScreen);
        } else {
            if (temp.length() >= lengthPass) {
                temp = "";
                tv_noti.setText(getResources().getString(R.string.wrong_password));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_noti.setText("");
                    }
                }, 1000);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
