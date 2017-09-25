package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ana.managers.SectionAdapterManager;
import com.ana.models.Section;
import com.ana.viewmodels.ChatViewModel;

import java.util.ArrayList;

/**
 * Created by Admin on 13-07-2017.
 */

public class ChatAdapter extends RecyclerView.Adapter {

    private SectionAdapterManager adapterManager;
    ArrayList<Section> mSectionList;

    public ChatAdapter(Context context, ArrayList<Section> list, ChatViewModel model) {
        mSectionList = list;
        adapterManager = new SectionAdapterManager(context,model);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapterManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapterManager.onBindViewHolder(holder, mSectionList.get(position), getItemViewType(position));
    }

    public void addSection(Section mSection) {
        mSectionList.add(mSection);
        notifyItemInserted(mSectionList.size() - 1);
    }

    public void replaceSection(Section mSection) {
        mSectionList.set(mSectionList.size() - 1, mSection);
        notifyItemChanged(mSectionList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return mSectionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return SectionAdapterManager.SectionTypeEnum.valueOf(mSectionList.get(position).getSectionType()).getValue();
    }
}
