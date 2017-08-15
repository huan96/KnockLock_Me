package com.zergitas.teamb.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zergitas.teamb.myapplication.R;
import com.zergitas.teamb.myapplication.until.FileManager;

public class NewPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView btnA, btnB, btnC, btnD, btnShow, tvPass, btnSave, btnClear;
    private boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        intiViews();
    }

    private void intiViews() {
        btnA = (TextView) findViewById(R.id.btn_a);
        btnB = (TextView) findViewById(R.id.btn_b);
        btnC = (TextView) findViewById(R.id.btn_c);
        btnD = (TextView) findViewById(R.id.btn_d);
        btnShow = (TextView) findViewById(R.id.btn_show);
        tvPass = (TextView) findViewById(R.id.tv_pass);
        btnSave = (TextView) findViewById(R.id.btn_save);
        btnClear = (TextView) findViewById(R.id.btn_clear);
        //setOnClickListener
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_a:
                addtext("A");
                break;
            case R.id.btn_b:
                addtext("B");
                break;
            case R.id.btn_c:
                addtext("C");
                break;
            case R.id.btn_d:
                addtext("D");
                break;
            case R.id.btn_show:
                changeShow();
                break;
            case R.id.btn_clear:
                clearText();
                break;
            case R.id.btn_save:
                savePassword();
                nextActivity();
                break;
            default:
                break;
        }
    }

    private void clearText() {
        tvPass.setText("");
    }

    private void nextActivity() {
        Intent intent = new Intent();
        intent.putExtra(FileManager.BACKGROUND_LOCK_SCREEN, FileManager.getUriImageBackground(this));
        intent.setClass(this, LockScreenActivity.class);
        startActivity(intent);
        finish();
    }

    private void savePassword() {
        try {
            String pass = tvPass.getText().toString();
            if (pass.equals("")) {
                Toast.makeText(this, "Cận chạm để nhập Password!", Toast.LENGTH_SHORT).show();
            } else {
                if (pass.length() < 3) {
                    Toast.makeText(this, "Password quá ngắn!", Toast.LENGTH_SHORT).show();
                } else {
                    FileManager.savePassword(pass, this);
                }
            }

        } catch (Exception e) {
            Toast.makeText(this, "Không thể lưu mật khẩu!", Toast.LENGTH_SHORT).show();
        }

    }


    private void changeShow() {
        if (isShow) {
            isShow = false;
            btnShow.setBackground(getResources().getDrawable(R.drawable.hide_off));
            tvPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            isShow = true;
            btnShow.setBackground(getResources().getDrawable(R.drawable.hide));
            tvPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    private void addtext(String a) {
        String pass = tvPass.getText().toString();
        if (pass.length() > 9) {
            Toast.makeText(this, "Password quá dài!", Toast.LENGTH_SHORT).show();
        } else {
            tvPass.setText(pass + a);
        }
    }
}
