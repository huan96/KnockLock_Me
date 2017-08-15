package com.zergitas.teamb.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zergitas.teamb.myapplication.R;
import com.zergitas.teamb.myapplication.until.FileManager;

public class LockScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView btnA, btnB, btnC, btnD;
    private String password;
    private String temp;
    private int lengthPass, limitPass;
    private ImageView imgBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        initViews();
        getpass();
        initvar();
        Toast.makeText(this, password, Toast.LENGTH_LONG).show();
    }

    private void initvar() {
        temp = "";
        limitPass = 0;
    }

    private void getpass() {
        try {
            password = FileManager.getPassword(this);
            lengthPass = password.length();
        } catch (Exception e) {
            Toast.makeText(this, "Sảy ra lỗi!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        btnA = (TextView) findViewById(R.id.btn_a);
        btnB = (TextView) findViewById(R.id.btn_b);
        btnC = (TextView) findViewById(R.id.btn_c);
        btnD = (TextView) findViewById(R.id.btn_d);
        imgBackground = (ImageView) findViewById(R.id.image_background);
        //setOnClickListener
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        initBackground();
    }

    private void initBackground() {
        try {
            Intent intent = getIntent();
            String s = intent.getStringExtra(FileManager.BACKGROUND_LOCK_SCREEN);
            if (s.equals(""))
                s = FileManager.getUriImageBackground(this);
            Glide.with(this).load(s).into(imgBackground);
        } catch (Exception e) {
            Glide.with(this).load(getResources().getDrawable(R.drawable.hide_off)).into(imgBackground);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_a:
                addPass("A");
                break;
            case R.id.btn_b:
                addPass("B");
                break;
            case R.id.btn_c:
                addPass("C");
                break;
            case R.id.btn_d:
                addPass("D");
                break;
            default:
                break;
        }
    }

    private void addPass(String a) {
        if (limitPass >= 5) {
            Toast.makeText(this, "Sai quá nhiều lần!\nChờ để được tiếp tục nhập", Toast.LENGTH_SHORT).show();
        } else {
            if (temp.length() < lengthPass) {
                temp += a;
            }
            if (password.equals(temp)) {
                finish();
            } else {
                if (temp.length() >= lengthPass) {
                    temp = "";
                    limitPass++;
                    Toast.makeText(this, "Bạn nhập sai mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
