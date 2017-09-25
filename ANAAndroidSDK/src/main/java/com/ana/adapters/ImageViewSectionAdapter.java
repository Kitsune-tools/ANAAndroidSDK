package com.ana.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ana.databinding.ChatImageRowLayoutBinding;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import com.ana.BR;
import com.ana.R;

import com.ana.models.Section;
import com.ana.viewmodels.ChatViewModel;

/**
 * Created by Admin on 13-07-2017.
 */

public class ImageViewSectionAdapter extends SectionAdapter<Section> {

    public ImageViewSectionAdapter(Context context, ChatViewModel model) {
        super(context);
        chatViewModel = model;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ChatImageRowLayoutBinding binding = ChatImageRowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final Section section) {
        final ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        if (imageViewHolder != null) {

            if (section.isLoading()) {
                imageViewHolder.getBinding().pbLoading.setVisibility(View.VISIBLE);
            } else {
                imageViewHolder.getBinding().pbLoading.setVisibility(View.INVISIBLE);
            }
            if (section.getTitle() != null && !section.getTitle().trim().equals("")) {
                imageViewHolder.getBinding().tvTitle.setText(section.getTitle());
            } else {
                imageViewHolder.getBinding().tvTitle.setVisibility(View.GONE);
            }


            String imageURL = chatViewModel.getParsedPrefixPostfixText(section.getUrl());

            if (!imageURL.contains("http")) {
//                imageURL = "file://"+section.getUrl();
                ((LinearLayout) imageViewHolder.itemView).setGravity(Gravity.START);

                imageViewHolder.getBinding().ivMainRowImage.setImageResource(R.drawable.site_sc_default);

                final android.view.ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) imageViewHolder.getBinding().ivMainRowImage.getLayoutParams();
                layoutParams.height = (int) TARGET_WIDTH;
                layoutParams.width = (int) TARGET_WIDTH;
                imageViewHolder.getBinding().ivMainRowImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(mContext)
                        .load(chatViewModel.getParsedPrefixPostfixText(imageURL))
                        .centerCrop()
                        .placeholder(R.drawable.site_sc_default)
                        .into(imageViewHolder.getBinding().ivMainRowImage);

            } else {

                imageViewHolder.getBinding().ivMainRowImage.setScaleType(ImageView.ScaleType.FIT_XY);

                ((LinearLayout) imageViewHolder.itemView).setGravity(Gravity.END);


                Picasso.with(mContext).load(imageURL).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {

                        //whatever algorithm here to compute size
                        final android.view.ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) imageViewHolder.getBinding().ivMainRowImage.getLayoutParams();

                        if (bitmap.getWidth() > MAX_WIDTH || bitmap.getHeight() > MAX_WIDTH) {
                            if (!chatViewModel.getParsedPrefixPostfixText(section.getUrl()).contains("http")) {

                                if (bitmap.getHeight() > bitmap.getWidth()) {
                                    float ratio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
                                    float widthFloat = ((float) MAX_WIDTH) * ratio;
                                    layoutParams.height = (int) MAX_WIDTH;
                                    layoutParams.width = (int) widthFloat;
                                } else {
                                    float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
                                    float heightFloat = ((float) MAX_WIDTH) * ratio;
                                    layoutParams.height = (int) heightFloat;
                                    layoutParams.width = (int) MAX_WIDTH;
                                }

                            } else {

                                float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
                                float heightFloat = ((float) MAX_WIDTH) * ratio;
                                layoutParams.height = (int) heightFloat;
                                layoutParams.width = (int) MAX_WIDTH;

                            }
                        } else {

                            layoutParams.height = (int) bitmap.getHeight();
                            layoutParams.width = (int) bitmap.getWidth();
                        }


                        imageViewHolder.getBinding().ivMainRowImage.setLayoutParams(layoutParams);
//                    if(bitmap!=null && !bitmap.isRecycled())
//                        bitmap.recycle();
                        imageViewHolder.getBinding().ivMainRowImage.setImageBitmap(bitmap);

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        imageViewHolder.getBinding().ivMainRowImage.setImageResource(R.drawable.site_sc_default);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        imageViewHolder.getBinding().ivMainRowImage.setImageResource(R.drawable.site_sc_default);
                    }

                });
            }


            if (section.getCaption() != null && !section.getCaption().trim().equals("")) {
                imageViewHolder.getBinding().tvCaption.setText(section.getCaption());
            } else {
                imageViewHolder.getBinding().tvCaption.setVisibility(View.GONE);
            }
            if (!section.isFromRia()) {
                ((LinearLayout) imageViewHolder.itemView).setGravity(Gravity.END);
            } else {
                ((LinearLayout) imageViewHolder.itemView).setGravity(Gravity.START);

            }

            imageViewHolder.bind(section);
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private ChatImageRowLayoutBinding binding;

        private ImageViewHolder(ChatImageRowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ChatImageRowLayoutBinding getBinding() {
            return binding;
        }

        public void bind(Section section) {
            binding.setVariable(BR.section, section);
            binding.executePendingBindings();
        }
    }

}
