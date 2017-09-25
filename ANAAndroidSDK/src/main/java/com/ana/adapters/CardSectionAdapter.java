package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ana.BR;
import com.ana.R;
import com.ana.databinding.ChatCardRowLayoutBinding;

import com.ana.models.Section;
import com.ana.utils.CommonMethods;
import com.ana.viewmodels.ChatViewModel;

/**
 * Created by Admin on 13-07-2017.
 */

public class CardSectionAdapter extends SectionAdapter<Section> {


    public CardSectionAdapter(Context context, ChatViewModel model) {
        super(context);
        chatViewModel = model;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ChatCardRowLayoutBinding binding = ChatCardRowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {
        CardViewHolder cardViewHolder = (CardViewHolder) holder;
        if (cardViewHolder != null) {
            String anaText = chatViewModel.getParsedPrefixPostfixText(section.getCardModel().getSections().get(0).getText());

            if (anaText.contains(".ana.com")) {
                anaText = anaText.split(".ana.com")[0].replace("<br>", "");
                cardViewHolder.getBinding().tvConfirmedBusinessText.setVisibility(View.VISIBLE);
            } else {
                cardViewHolder.getBinding().tvConfirmedBusinessText.setVisibility(View.GONE);
            }

            cardViewHolder.getBinding().tvConfirmedBusinessText.setText(CommonMethods.fromHtml(anaText));
            cardViewHolder.getBinding().tvConfirmedHintText.setText(CommonMethods.fromHtml(chatViewModel.getParsedPrefixPostfixText(section.getCardModel().getCardFooter())));


            if (section.isShowDate()) {
                cardViewHolder.getBinding().tvDateTime.setVisibility(View.VISIBLE);
                cardViewHolder.getBinding().tvDateTime.setText(section.getDateTime());
            } else {
                cardViewHolder.getBinding().tvDateTime.setVisibility(View.GONE);
            }

            if (!section.isAnimApplied()) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.flip_in_anim);
                cardViewHolder.getBinding().llBubbleContainer.setAnimation(animation);
                animation.start();
                section.setIsAnimApplied(true);
            }

        }
    }

    private class CardViewHolder extends RecyclerView.ViewHolder {

        private ChatCardRowLayoutBinding binding;

        private CardViewHolder(ChatCardRowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ChatCardRowLayoutBinding getBinding() {
            return binding;
        }

        public void bind(Section section) {
            binding.setVariable(BR.section, section);
            binding.executePendingBindings();
        }

    }

}
