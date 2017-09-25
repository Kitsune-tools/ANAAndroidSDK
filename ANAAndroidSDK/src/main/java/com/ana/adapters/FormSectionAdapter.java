package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ana.R;

import com.ana.models.Section;

/**
 * Created by Admin on 13-07-2017.
 */

public class FormSectionAdapter extends SectionAdapter<Section> {

    public FormSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_submit_form_layout, parent, false);
        return new FormViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {

    }

    private class FormViewHolder extends RecyclerView.ViewHolder {

        TextView tvBusinessName, tvPhoneNumber, tvCategory, tvAddress, tvEmailAddress, tvWebsiteURL, tvtermAndPolicy;
        View itemView;
        Button btnCreateWebsite;

        private FormViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            tvBusinessName = (TextView) itemView.findViewById(R.id.tvBusinessName);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvEmailAddress = (TextView) itemView.findViewById(R.id.tvEmailAddress);
            tvWebsiteURL = (TextView) itemView.findViewById(R.id.tvWebsiteURL);
            tvtermAndPolicy = (TextView) itemView.findViewById(R.id.tvtermAndPolicy);
            btnCreateWebsite = (Button) itemView.findViewById(R.id.btnCreateWebsite);

        }
    }
}
