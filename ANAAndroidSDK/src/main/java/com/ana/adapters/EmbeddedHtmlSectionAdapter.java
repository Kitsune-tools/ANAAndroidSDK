package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.ana.R;

import com.ana.models.Section;

/**
 * Created by Admin on 13-07-2017.
 */

public class EmbeddedHtmlSectionAdapter extends SectionAdapter<Section>  {


    public EmbeddedHtmlSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_embedded_html_row_layout, parent, false);
        return new EmbeddedHtmlViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {

    }

    private class EmbeddedHtmlViewHolder extends RecyclerView.ViewHolder {

        WebView wvEmbeddedHtml;
        TextView tvTitle, tvOpenInBrowser, tvCaption;

        private EmbeddedHtmlViewHolder(View itemView) {
            super(itemView);

            wvEmbeddedHtml = (WebView) itemView.findViewById(R.id.wv_embedded_html);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvOpenInBrowser = (TextView) itemView.findViewById(R.id.tv_open_in_browser);
            tvCaption = (TextView) itemView.findViewById(R.id.tv_caption);

        }
    }

}
