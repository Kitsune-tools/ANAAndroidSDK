package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ana.R;

import com.ana.models.Section;

/**
 * Created by Admin on 13-07-2017.
 */

public class HeaderSectionAdapter extends SectionAdapter<Section> {

    public HeaderSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_head, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {

    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tvDateTime;

        private HeaderViewHolder(View itemView) {
            super(itemView);
            tvDateTime = (TextView) itemView.findViewById(R.id.tv_date_time);
        }
    }

}
