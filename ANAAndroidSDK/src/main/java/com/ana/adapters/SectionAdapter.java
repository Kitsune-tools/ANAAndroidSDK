package com.ana.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ana.viewmodels.ChatViewModel;

/**
 * Created by Admin on 13-07-2017.
 */

public abstract class SectionAdapter<T> {

    Context mContext;
    ChatViewModel chatViewModel;
    public float MAX_WIDTH,TARGET_WIDTH,TARGET_HEIGHT;
    public SectionAdapter(Context context){
        this.mContext = context;
        Resources r = context.getResources();
        MAX_WIDTH =  r.getDisplayMetrics().widthPixels;
        TARGET_WIDTH = (int) (300 * r.getDisplayMetrics().density);
        TARGET_HEIGHT = (int) (200 * r.getDisplayMetrics().density);
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, T section);

   /* public String getParsedPrefixPostfixText(String text) {
        if (text == null)
            return null;
        Matcher m = Pattern.compile("\\[~(.*?)\\]").matcher(text);
        while (m.find()) {
           *//* if (mDataMap.get(m.group()) != null) {
                text = text.replace(m.group(), mDataMap.get(m.group()));
            }*//*
        }
        return text;
    }*/

}
