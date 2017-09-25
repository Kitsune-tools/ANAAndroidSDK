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

public class UnconfirmedAddressCardSectionAdapter extends SectionAdapter<Section> {

    public UnconfirmedAddressCardSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_unconfirmed_address_card_layout, parent, false);
        return new UnconfirmedAddressCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {

    }

    private class UnconfirmedAddressCardViewHolder extends RecyclerView.ViewHolder {

        ImageView ivMap;
        TextView tvAddressText, tvConfirm, tvEdit;
        View itemView;


        private UnconfirmedAddressCardViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;

            this.ivMap = (ImageView) itemView.findViewById(R.id.iv_map_data);
            this.tvAddressText = (TextView) itemView.findViewById(R.id.tv_address_text);
            this.tvEdit = (TextView) itemView.findViewById(R.id.tv_addr_edit);
            this.tvConfirm = (TextView) itemView.findViewById(R.id.tv_addr_confirm);
        }
    }
}
