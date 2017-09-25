package com.ana.animators;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Miroslaw Stanek on 06.01.2016.
 */
public class ChatItemAnimator extends DefaultItemAnimator {

    private Context mContext;

    public ChatItemAnimator(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return false;
    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull final RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo,
                                 @NonNull ItemHolderInfo postInfo) {

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {

        return super.animateAdd(holder);
    }
}
