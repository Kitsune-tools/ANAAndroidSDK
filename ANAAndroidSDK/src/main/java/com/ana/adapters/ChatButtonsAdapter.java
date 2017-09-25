package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ana.BR;
import com.ana.databinding.ButtonItemLayoutBinding;

import java.util.List;

import com.ana.models.Button;
import com.ana.viewmodels.ChatViewModel;


/**
 * Created by ANA on 16-03-2017.
 */

public class ChatButtonsAdapter extends RecyclerView.Adapter<ChatButtonsAdapter.ButtonViewHolder> {

    private List<Button> mButtonList;

    private ChatViewModel mChatViewModel;
    private Context mContext;

    public ChatButtonsAdapter(Context context, List<Button> mButtonList, ChatViewModel mChatViewModel) {
        this.mButtonList = mButtonList;
        this.mChatViewModel = mChatViewModel;
        mContext = context;
    }

    @Override
    public ButtonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ButtonItemLayoutBinding binding = ButtonItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ButtonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ButtonViewHolder holder, final int position) {
        holder.bind(mButtonList.get(position));
        holder.getBinding().setViewmodel(mChatViewModel);
    }

    @Override
    public int getItemCount() {

        if (mButtonList != null && mButtonList.size() > 0)
            return mButtonList.size();
        return 0;
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder {

        private ButtonItemLayoutBinding binding;

        private ButtonViewHolder(ButtonItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ButtonItemLayoutBinding getBinding() {
            return binding;
        }

        public void bind(Button button) {
            binding.setVariable(BR.button, button);
            binding.executePendingBindings();
        }
    }

    public void refresh(List<Button> mButtonList) {
        this.mButtonList = mButtonList;
        this.notifyDataSetChanged();
    }

}
