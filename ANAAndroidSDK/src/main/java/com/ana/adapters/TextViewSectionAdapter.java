package com.ana.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.ana.R;
import com.ana.BR;
import com.ana.databinding.ChatTextRowLayoutBinding;

import com.ana.models.Section;
import com.ana.utils.CommonMethods;
import com.ana.viewmodels.ChatViewModel;


/**
 * Created by Admin on 13-07-2017.
 */

public class TextViewSectionAdapter extends SectionAdapter<Section> {


    private ChatViewModel chatViewModel;

    public TextViewSectionAdapter(Context context, ChatViewModel chatViewModel) {
        super(context);
        this.chatViewModel = chatViewModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ChatTextRowLayoutBinding binding = ChatTextRowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TextViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final Section section) {

        final TextViewHolder textViewHolder = (TextViewHolder) holder;

        if (section != null && textViewHolder != null) {

            final CharSequence charSequence = CommonMethods.fromHtml(section.getText());

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
            CommonMethods.linkClickableText(mContext, spannableStringBuilder, charSequence);
            textViewHolder.getBinding().tvMessageText.setMovementMethod(LinkMovementMethod.getInstance());
            textViewHolder.getBinding().tvMessageText.setText(spannableStringBuilder);


            if (section.isShowDate()) {
                textViewHolder.getBinding().tvDateTime.setVisibility(View.VISIBLE);
                textViewHolder.getBinding().tvDateTime.setText(section.getDateTime());
            } else {
                textViewHolder.getBinding().tvDateTime.setVisibility(View.GONE);
            }

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) textViewHolder.getBinding().llBubbleContainer.getLayoutParams();
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = mContext.getTheme();
            theme.resolveAttribute(R.attr.ChatTextStyle, typedValue, true);
            TypedArray a = theme.obtainStyledAttributes(typedValue.data, new int[] {R.attr.receiveChatBg,R.attr.receiveTextColor,R.attr.sendChatBg,R.attr.sendTextColor});
            int receiveColor = a.getColor(1,-1);
            int sendColor = a.getColor(3,-1);
            Drawable sendBg= a.getDrawable(2);
            Drawable receiveBg= a.getDrawable(0);

            a.recycle();
            if (!section.isFromRia()) {

                textViewHolder.getBinding().llBubbleContainer.setVisibility(View.VISIBLE);
                textViewHolder.getBinding().tvMessageText.setTextColor(sendColor == -1 ? ContextCompat.getColor(mContext,R.color.white) : sendColor);
                ((LinearLayout) textViewHolder.itemView).setGravity(Gravity.END);

                textViewHolder.getBinding().llBubbleContainer.setBackgroundResource(R.drawable.reply_main_bubble);
                lp.setMargins(CommonMethods.dpToPx(mContext, 60), 0, CommonMethods.dpToPx(mContext, 5), 0);


            } else {

                ((LinearLayout) textViewHolder.itemView).setGravity(Gravity.START);
                textViewHolder.getBinding().tvMessageText.setTextColor(receiveColor == -1 ? ContextCompat.getColor(mContext,R.color.black) : receiveColor);

                if (section.isFollowUp()) {
                    textViewHolder.getBinding().llBubbleContainer.setBackgroundResource(R.drawable.ria_followup_bubble);
                    lp.setMargins(CommonMethods.dpToPx(mContext, 15), 0, CommonMethods.dpToPx(mContext, 60), 0);
                } else {
                    textViewHolder.getBinding().llBubbleContainer.setBackgroundResource(R.drawable.ria_main_bubble);
                    lp.setMargins(CommonMethods.dpToPx(mContext, 5), 0, CommonMethods.dpToPx(mContext, 60), 0);
                }

            }

            textViewHolder.getBinding().llBubbleContainer.setLayoutParams(lp);
            textViewHolder.bind(section);
//            applyAnimation(textViewHolder, section);

        }
    }

    private void applyAnimation(TextViewHolder textViewHolder, Section section) {

        if (!section.isAnimApplied()) {
            Animation a = new TranslateAnimation(
                    Animation.ABSOLUTE, //from xType
                    -200,
                    Animation.ABSOLUTE, //to xType
                    0,
                    Animation.ABSOLUTE, //from yType
                    200,
                    Animation.ABSOLUTE, //to yType
                    0
            );
            a.setDuration(200);
            textViewHolder.itemView.setAnimation(a);
            a.start();
            //Anim End
            section.setIsAnimApplied(true);
        }

    }

    private class TextViewHolder extends RecyclerView.ViewHolder {


        private ChatTextRowLayoutBinding binding;

        private TextViewHolder(ChatTextRowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ChatTextRowLayoutBinding getBinding() {
            return binding;
        }

        public void bind(Section section) {
            binding.setVariable(BR.section, section);
            binding.executePendingBindings();
        }
    }
}
