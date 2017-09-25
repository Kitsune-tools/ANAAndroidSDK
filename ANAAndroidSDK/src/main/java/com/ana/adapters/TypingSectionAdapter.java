package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ana.R;

import com.ana.customviews.AVLoadingIndicatorView;
import com.ana.models.Section;

/**
 * Created by Admin on 13-07-2017.
 */

public class TypingSectionAdapter extends SectionAdapter<Section> {


    public TypingSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_typing_row_layout, parent, false);
        return new TypingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {
        TypingViewHolder typingViewHolder = (TypingViewHolder) holder;
    }

    private class TypingViewHolder extends RecyclerView.ViewHolder {
        AVLoadingIndicatorView ldiDots;
        RelativeLayout rlLoadingDots;

        private TypingViewHolder(View itemView) {
            super(itemView);
            ldiDots = (AVLoadingIndicatorView) itemView.findViewById(R.id.ldi_dots);
            rlLoadingDots = (RelativeLayout) itemView.findViewById(R.id.rl_typing_container);
        }
    }
}
