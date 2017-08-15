package com.zergitas.teamb.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zergitas.teamb.myapplication.R;
import com.zergitas.teamb.myapplication.until.FileManager;

public class CheckOldPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView btnA, btnB, btnC, btnD, tvPass, btnBack, btnClear;
    private String oldPass;
    private int dem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_old_password);
        intiViews();
    }

    private void intiViews() {
        btnA = (TextView) findViewById(R.id.btn_a);
        btnB = (TextView) findViewById(R.id.btn_b);
        btnC = (TextView) findViewById(R.id.btn_c);
        btnD = (TextView) findViewById(R.id.btn_d);
        tvPass = (TextView) findViewById(R.id.tv_pass);
        btnBack = (TextView) findViewById(R.id.btn_back);
        btnClear = (TextView) findViewById(R.id.btn_clear);
        //setOnClickListener
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        oldPass = FileManager.getPassword(this);
        dem = 0;
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
            case R.id.btn_clear:
                clearText();
                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void addtext(String a) {
        if (dem >= 5) {
            Toast.makeText(this, "Bạn đã nhập sai quá nhiều!", Toast.LENGTH_SHORT).show();
            finish();
        }
        String pass = tvPass.getText().toString();

        if (pass.length() > oldPass.length()) {
            Toast.makeText(this, "Password sai!", Toast.LENGTH_SHORT).show();
            tvPass.setText("");
            dem++;
        } else {
            tvPass.setText(pass + a);
            pass += a;
        }
        if (pass.equals(oldPass)) {
            toNewPassWord();
        }
    }

    private void toNewPassWord() {
        Intent itent = new Intent(this, NewPasswordActivity.class);
        startActivity(itent);
        finish();
    }

    private void clearText() {
        tvPass.setText("");
    }
}
