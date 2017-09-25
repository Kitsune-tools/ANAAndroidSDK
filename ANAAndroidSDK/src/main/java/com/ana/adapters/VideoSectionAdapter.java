package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.ana.R;

import com.ana.models.Section;

/**
 * Created by Admin on 13-07-2017.
 */

public class VideoSectionAdapter extends SectionAdapter<Section> {

    public VideoSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_video_row_layout, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {

    }

    private class VideoViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        VideoView vvMainVideo;
        ProgressBar pbVideoLoading;

        private VideoViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            vvMainVideo = (VideoView) itemView.findViewById(R.id.vv_main_video);
            pbVideoLoading = (ProgressBar) itemView.findViewById(R.id.pb_video_loading);
        }
    }

}
