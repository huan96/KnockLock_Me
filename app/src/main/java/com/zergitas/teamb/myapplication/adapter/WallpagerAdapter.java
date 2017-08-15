package com.zergitas.teamb.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zergitas.teamb.myapplication.R;

import java.util.List;

/**
 * Created by huand on 8/15/2017.
 */

public class WallpagerAdapter extends RecyclerView.Adapter<WallpagerAdapter.ViewHolder> {
    private ItemClick itemClick;
    private List<Integer> listImgID;
    private Context mContext;

    public WallpagerAdapter(List<Integer> listImgID, Context mContext, ItemClick itemClick) {
        this.listImgID = listImgID;
        this.mContext = mContext;
        this.itemClick = itemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext).load(listImgID.get(position)).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onclick(listImgID.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImgID.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_item);
        }
    }

    public interface ItemClick {
        void onclick(int id);
    }
}
