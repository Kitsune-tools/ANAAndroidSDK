package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ana.R;

import com.ana.models.Section;

/**
 * Created by Admin on 13-07-2017.
 */

public class OTPSectionAdapter extends SectionAdapter<Section> {

    public OTPSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_print_otp_layout, parent, false);
        return new OTPViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {

    }

    private class OTPViewHolder extends RecyclerView.ViewHolder {

        TextView tvConfirmationTitle;
        EditText etOTPConfirmation;
        TextView tvConfirm, tvEdit, tvSubmit;
        View itemView;
        LinearLayout llConfirm;

        private OTPViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            this.etOTPConfirmation = (EditText) itemView.findViewById(R.id.et_otp_confirm_text);
            this.tvConfirmationTitle = (TextView) itemView.findViewById(R.id.tv_confirmation_title);
            this.tvConfirm = (TextView) itemView.findViewById(R.id.tv_confirm);
            this.tvEdit = (TextView) itemView.findViewById(R.id.tv_edit);
            this.llConfirm = (LinearLayout) itemView.findViewById(R.id.llConfirm);
            this.tvSubmit = (TextView) itemView.findViewById(R.id.tvSubmit);

        }
    }
}
