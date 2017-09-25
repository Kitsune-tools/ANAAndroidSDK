package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ana.R;

import com.ana.models.Section;

/**
 * Created by Admin on 13-07-2017.
 */

public class GifSectionAdapter extends SectionAdapter<Section> {

    public GifSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_gif_row_layout, parent, false);
        return new GifViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {

    }

    private class GifViewHolder extends RecyclerView.ViewHolder {

        TextView tvImageTitle, tvDateTime;
        ImageView ivMainImage;
        TextView tvImageCaption;

        private GifViewHolder(View itemView) {
            super(itemView);

            tvImageTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivMainImage = (ImageView) itemView.findViewById(R.id.iv_main_row_image);
            tvDateTime = (TextView) itemView.findViewById(R.id.tv_date_time);
            tvImageCaption = (TextView) itemView.findViewById(R.id.tv_caption);
        }
    }
}
