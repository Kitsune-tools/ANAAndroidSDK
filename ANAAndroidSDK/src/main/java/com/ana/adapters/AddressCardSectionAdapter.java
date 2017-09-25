package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ana.databinding.ChatAddressCardRowLayoutBinding;
import com.bumptech.glide.Glide;

import com.ana.BR;
import com.ana.R;

import com.ana.models.Section;
import com.ana.utils.CommonMethods;
import com.ana.viewmodels.ChatViewModel;

/**
 * Created by Admin on 13-07-2017.
 */

public class AddressCardSectionAdapter extends SectionAdapter<Section> {

    public AddressCardSectionAdapter(Context context, ChatViewModel model) {
        super(context);
        chatViewModel = model;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ChatAddressCardRowLayoutBinding binding = ChatAddressCardRowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AddressCardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {
        AddressCardViewHolder cardViewHolder = (AddressCardViewHolder) holder;
        cardViewHolder.getBinding().tvConfirmationText.setText(CommonMethods.fromHtml(chatViewModel.getParsedPrefixPostfixText(section.getCardModel().getSections().get(1).getText())));
        cardViewHolder.getBinding().tvAddrFooter.setText(CommonMethods.fromHtml(chatViewModel.getParsedPrefixPostfixText(section.getCardModel().getCardFooter())));
        Glide.with(mContext)
                .load(chatViewModel.getParsedPrefixPostfixText(section.getCardModel().getSections().get(0).getUrl()))
                .placeholder(R.drawable.default_product_image)
                .error(R.drawable.default_product_image)
                .into(cardViewHolder.getBinding().ivMapData);
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

    private class AddressCardViewHolder extends RecyclerView.ViewHolder {

        private ChatAddressCardRowLayoutBinding binding;

        private AddressCardViewHolder(ChatAddressCardRowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ChatAddressCardRowLayoutBinding getBinding() {
            return binding;
        }

        public void bind(Section section) {
            binding.setVariable(BR.section, section);
            binding.executePendingBindings();
        }
    }
}
