package com.ana.ui;

import android.databinding.Observable;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.ana.databinding.ChatBotFragBinding;

import java.util.ArrayList;

import com.ana.adapters.ChatAdapter;
import com.ana.adapters.ChatButtonsAdapter;
import com.ana.models.Button;
import com.ana.models.Section;
import com.ana.viewmodels.ChatViewModel;

/**
 * Created by admin on 7/26/2017.
 */

public class ChatbotFragment extends Fragment {

    private ChatBotFragBinding mChatViewFragBinding;

    private ChatViewModel mChatViewModel;

    private ChatAdapter mChatAdapter;

    private ChatButtonsAdapter mChatButtonAdapter;

    private Observable.OnPropertyChangedCallback mSectionCallback;

    private Observable.OnPropertyChangedCallback mChatInput;

    public ChatbotFragment() {
        // Requires empty public constructor
    }

    public static ChatbotFragment newInstance() {
        return new ChatbotFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mChatViewFragBinding = ChatBotFragBinding.inflate(inflater, container, false);

        mChatViewFragBinding.setView(this);

        mChatViewFragBinding.setViewmodel(mChatViewModel);

        View root = mChatViewFragBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChatViewModel.start();
    }

    public void setViewModel(ChatViewModel viewModel) {
        mChatViewModel = viewModel;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter();

        setButtonListAdapter();

        listenSectionCallback();

        listenKeyBoardCallback();

        listenTextChangeCallback();
    }

    private void setListAdapter() {

        RecyclerView recyclerView = mChatViewFragBinding.rvChatData;

        mChatAdapter = new ChatAdapter(getActivity(), new ArrayList<Section>(), mChatViewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        AnimationSet set = new AnimationSet(true);

        // Fade in animation
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(400);
        fadeIn.setFillAfter(true);
        set.addAnimation(fadeIn);

        // Slide up animation from bottom of screen
        Animation slideUp = new TranslateAnimation(-100, 0, 0, 0);
        slideUp.setInterpolator(new DecelerateInterpolator(4.f));
        slideUp.setDuration(400);
        set.addAnimation(slideUp);

        // Set up the animation controller              (second parameter is the delay)
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.2f);
        recyclerView.setLayoutAnimation(controller);

//        recyclerView.setItemAnimator(new ChatItemAnimator(getActivity()));
        recyclerView.setAdapter(mChatAdapter);
    }

    private void setButtonListAdapter() {

        RecyclerView recyclerView = mChatViewFragBinding.rvReplyButtonContainer;

        mChatButtonAdapter = new ChatButtonsAdapter(getContext(),new ArrayList<Button>(), mChatViewModel);

        LinearLayoutManager buttonsLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(buttonsLayoutManager);
        recyclerView.setAdapter(mChatButtonAdapter);
    }

    private void listenSectionCallback() {
        mSectionCallback = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {

                if (getActivity() != null && !getActivity().isFinishing()) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            final RecyclerView rvChatData = mChatViewFragBinding.rvChatData;

                            Section mSection = mChatViewModel.getSection();

                            if (!mSection.isReplace() || !mSection.isFromRia())
                                mChatAdapter.addSection(mChatViewModel.getSection());
                            else
                                mChatAdapter.replaceSection(mChatViewModel.getSection());

                            rvChatData.scrollToPosition(mChatAdapter.getItemCount() - 1);
                        }
                    });
                }


            }
        };
        mChatViewModel.sections.addOnPropertyChangedCallback(mSectionCallback);
    }

    private void listenTextChangeCallback() {

        mChatInput = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        final ImageView ivSendMessage = mChatViewFragBinding.ivSendMsg;
                        String inputText = mChatViewModel.chatInput.get();

                        if (inputText.trim().length() > 0) {
                            ivSendMessage.getBackground().setColorFilter(Color.parseColor("#157EFB"), PorterDuff.Mode.DARKEN);
                        } else {
                            ivSendMessage.getBackground().setColorFilter(Color.parseColor("#40157EFB"), PorterDuff.Mode.DARKEN);
                        }

                    }
                });


            }
        };
        mChatViewModel.chatInput.addOnPropertyChangedCallback(mChatInput);
    }


    private void listenKeyBoardCallback() {

        Observable.OnPropertyChangedCallback mShowInputCallBack = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {

                final boolean showKeyboard = mChatViewModel.showKeyBoard.get();

                final Button mButton = mChatViewModel.getCurrButton();
                final AutoCompleteTextView etChatInput = mChatViewFragBinding.etChatInput;

                if (showKeyboard && mButton != null) {
                    etChatInput.requestFocus();
                    etChatInput.setInputType(mButton.getInputType());
                    etChatInput.setHint(mButton.getPlaceholderText());
                }

                if (getActivity() != null) {

                    ((ChatbotActivity) getActivity()).mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            final RecyclerView rvChatData = mChatViewFragBinding.rvChatData;

                            rvChatData.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                                @Override
                                public void onLayoutChange(View v,
                                                           int left, int top, int right, int bottom,
                                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {
                                    if (bottom < oldBottom) {
                                        rvChatData.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                rvChatData.smoothScrollToPosition(
                                                        rvChatData.getAdapter().getItemCount() - 1);
                                            }
                                        }, 50);
                                    }
                                }
                            });
                        }
                    }, 300);

                }

            }
        };
        mChatViewModel.showKeyBoard.addOnPropertyChangedCallback(mShowInputCallBack);
    }

}
