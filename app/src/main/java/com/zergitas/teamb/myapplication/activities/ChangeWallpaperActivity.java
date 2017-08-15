package com.zergitas.teamb.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zergitas.teamb.myapplication.R;
import com.zergitas.teamb.myapplication.adapter.WallpagerAdapter;
import com.zergitas.teamb.myapplication.until.FileManager;

import java.util.ArrayList;

public class ChangeWallpaperActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView selectedImage;
    private RecyclerView recyclerView;
    private TextView btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_wallpaper);
        initViews();

    }

    private void initViews() {
        selectedImage = (ImageView) findViewById(R.id.imageView);
        btnAdd = (TextView) findViewById(R.id.btn_add_img);
        btnBack = (TextView) findViewById(R.id.btn_back);
        Glide.with(this).load(FileManager.getUriImageBackground(this)).into(selectedImage);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        WallpagerAdapter wallpagerAdapter = new WallpagerAdapter(getImgID(), this, new WallpagerAdapter.ItemClick() {
            @Override
            public void onclick(int id) {
                Glide.with(ChangeWallpaperActivity.this).load(id).into(selectedImage);
            }
        });
        recyclerView.setAdapter(wallpagerAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromAlbum();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    final ArrayList<Integer> getImgID() {
        ArrayList<Integer> imgId;
        imgId = new ArrayList<>();
        imgId.add(R.drawable.bg_1);
        imgId.add(R.drawable.bg_2);
        imgId.add(R.drawable.bg_3);
        imgId.add(R.drawable.bg_4);
        imgId.add(R.drawable.bg_5);
        imgId.add(R.drawable.bg_6);
        imgId.add(R.drawable.bg_7);
        imgId.add(R.drawable.bg_8);
        imgId.add(R.drawable.bg_9);
        imgId.add(R.drawable.bg_10);
        imgId.add(R.drawable.bg_11);
        imgId.add(R.drawable.bg_12);
        imgId.add(R.drawable.bg_13);
        imgId.add(R.drawable.bg_14);
        return imgId;
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                FileManager.saveImageBackground(imageUri.toString(), this);
                Glide.with(this).load(imageUri).into(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Bạn chưa chọn ảnh!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getImageFromAlbum() {
        try {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        } catch (Exception exp) {
            Log.i("Error", exp.toString());
        }
    }
}
