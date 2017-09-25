package com.ana.handlers;

import android.os.Handler;
import android.os.Looper;

import com.ana.models.Section;
import com.ana.utils.CommonMethods;
import com.ana.utils.Constants;
import com.ana.viewmodels.ChatViewModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 24-07-2017.
 */

public class SectionHandler {

    private static SectionHandler sectionHandler;

    private ChatViewModel chatViewModel;

    private int mCurSectionPos = 0;

    private ArrayList<Section> arrSection;

    private Handler mHandler;

    private final int DEFAULT_TYPING_DELAY = 1000;
    private final int MIN_DELAY = 500;

    private SectionHandler(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static SectionHandler getInstance(ChatViewModel chatViewModel) {
        if (sectionHandler == null) {
            synchronized (SectionHandler.class) {
                sectionHandler = new SectionHandler(chatViewModel);
            }
        }
        return sectionHandler;
    }

    public void handleMessage(ArrayList<Section> arrSection) {

        mCurSectionPos = 0;
        this.arrSection = arrSection;
        processSection();

    }

    private void processSection() {

        if (mCurSectionPos > arrSection.size() - 1) {
            chatViewModel.onSectionRenderCompletion();
        } else {

            Section mSection = new Section();
            mSection.setReplace(false);
            mSection.setSectionType(Constants.SectionType.TYPE_TYPING);
            mHandler.postDelayed(getRunnable(mSection), DEFAULT_TYPING_DELAY);

            Section actualSection = arrSection.get(mCurSectionPos);

            if (mCurSectionPos == 0) {
                actualSection.setFollowUp(false);
            } else if (mCurSectionPos == arrSection.size() - 1) {
                actualSection.setShowDate(true);
            }

            mHandler.postDelayed(getRunnable(arrSection.get(mCurSectionPos++)), DEFAULT_TYPING_DELAY +
                    (actualSection.getDelayInMs() > MIN_DELAY ? actualSection.getDelayInMs() : MIN_DELAY));
        }

    }


    private Runnable getRunnable(final Section actualSection) {
        return new Runnable() {

            @Override
            public void run() {
                chatViewModel.onSectionAdded(actualSection);
                if (!actualSection.getSectionType()
                        .equalsIgnoreCase(Constants.SectionType.TYPE_TYPING)) {

                    String str = null;
                    if (actualSection.getText() != null) {
                        str = actualSection.getText();
                        str = chatViewModel.getParsedPrefixPostfixText(str);
                    }
                    if (null != str) {
                        actualSection.setText(str);
                    }
                    actualSection.setFromRia(true);
                    actualSection.setDateTime(CommonMethods.getFormattedDate(new Date()));

                    processSection();
                }
            }
        };
    }
}
