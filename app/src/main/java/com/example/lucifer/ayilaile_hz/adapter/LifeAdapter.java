package com.example.lucifer.ayilaile_hz.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lucifer.ayilaile_hz.Life_Detail_Activity;
import com.example.lucifer.ayilaile_hz.R;
import com.example.lucifer.ayilaile_hz.bean.LifeItem;


import java.util.List;

public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.ViewHolder>{

    private static final String TAG = "LifeAdapter";

    private Context mContext;

    private List<LifeItem> mlifeList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView lifeImage;
        TextView lifeName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            lifeImage = (ImageView) view.findViewById(R.id.life_image);
            lifeName = (TextView) view.findViewById(R.id.life_name);
        }
    }

    public LifeAdapter(List<LifeItem> lifeList) {
        mlifeList = lifeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.life_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                LifeItem fruit = mlifeList.get(position);
                Intent intent = new Intent(mContext, Life_Detail_Activity.class);
                intent.putExtra(Life_Detail_Activity.LIFE_NAME, fruit.getName());
                intent.putExtra(Life_Detail_Activity.LIFE_IMAGE_ID, fruit.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LifeItem lifeItem = mlifeList.get(position);
        holder.lifeName.setText(lifeItem.getName());
        Glide.with(mContext).load(lifeItem.getImageId()).into(holder.lifeImage);
    }

    @Override
    public int getItemCount() {
        return mlifeList.size();
    }

}
