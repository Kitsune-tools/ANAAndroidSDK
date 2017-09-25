package com.ana.handlers;

import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;

import com.ana.models.Button;
import com.ana.utils.Constants;
import com.ana.viewmodels.ChatViewModel;
import com.jayway.jsonpath.DocumentContext;

import java.util.ArrayList;

/**
 * Created by Admin on 25-07-2017.
 */

public class ButtonHandler {

    private static ButtonHandler buttonHandler;

    private ChatViewModel chatViewModel;

    private int mCurButtonPos = 0;

    private ArrayList<Button> arrInputButtons = new ArrayList<>();

    private ArrayList<Button> arrButtons;

    private Button mDefaultButton;

    private Handler mHandler;


    private ButtonHandler(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static ButtonHandler getInstance(ChatViewModel chatViewModel) {
        if (buttonHandler == null) {
            synchronized (ButtonHandler.class) {
                buttonHandler = new ButtonHandler(chatViewModel);
            }
        }
        return buttonHandler;
    }

    public void handleMessage(ArrayList<Button> arrButtons) {

        mDefaultButton = null;
        mCurButtonPos = 0;
        arrInputButtons = new ArrayList<>();
        this.arrButtons = arrButtons;

        if (chatViewModel.getCurrNode().getDocumentContext() != null) {
            handleApiResponse();
        } else {
            processButtons();
        }

    }

    private void processButtons() {

        if (mCurButtonPos > arrButtons.size() - 1) {

            chatViewModel.onButtonRenderCompletion(arrInputButtons);
            if (mDefaultButton != null) {
                mHandler.postDelayed(getAutoRunnable(mDefaultButton),
                        chatViewModel.getCurrNode().getTimeoutInMs());
            }

        } else {

            Button btn = arrButtons.get(mCurButtonPos);

            if (btn.isDefaultButton()) {
                mDefaultButton = btn;
            }

            //    if (btn.getPrefixText() != null) {
//                    chatViewModel.setPrefix(btn);
//                }
//
//                if (btn.getPostfixText() != null) {
//                    chatViewModel.setPostfix(btn);
//                }

            if (!btn.isHidden()) {


                if (btn.getButtonType().equals(Constants.ButtonType.TYPE_GET_TEXT)) {
                    btn.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                } else if (btn.getButtonType().equals(Constants.ButtonType.TYPE_GET_NUMBER)) {
                    btn.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else if (btn.getButtonType().equals(Constants.ButtonType.TYPE_GET_EMAIL)) {
                    btn.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                } else if (btn.getButtonType().equals(Constants.ButtonType.TYPE_GET_PHONE_NUMBER)) {
                    btn.setInputType(InputType.TYPE_CLASS_PHONE);
                }
            }

            if (btn.getInputType() == Button.DEFAULT_BUTTON_TYPE) {
                arrInputButtons.add(btn);
            } else {
                chatViewModel.showInputText(btn);
            }
            mCurButtonPos++;
            processButtons();

        }
    }

    private void handleApiResponse() {

        DocumentContext context = chatViewModel.getCurrNode().getDocumentContext();
        Button btnNextNode = null;

        for (Button btn : arrButtons) {
            if (TextUtils.isEmpty(btn.getApiResponseMatchKey()))
                continue;
            String keyPath = "$." + btn.getApiResponseMatchKey();
            String val = context.read(keyPath, String.class);
            if (val != null && val.equalsIgnoreCase(btn.getApiResponseMatchValue())) {
                btnNextNode = btn;
                break;
            }
        }

        if (btnNextNode != null) {
            handleMessage(btnNextNode);
        } else {
            chatViewModel.onButtonRenderCompletion("");
        }
    }

    public void handleMessage(Button button) {

        switch (button.getButtonType()) {
            case Constants.ButtonType.TYPE_NEXT_NODE:
                if (chatViewModel.getCurrNode().getVariableName() != null &&
                        button.getVariableValue() != null && !button.getVariableValue().isEmpty()) {

                    String str = chatViewModel.getParsedPrefixPostfixText(button.getVariableValue());
                    chatViewModel.setChatVariable(chatViewModel.getCurrNode().getVariableName(), str);
                }
                chatViewModel.onButtonRenderCompletion(button.getNextNodeId());
                // set place holder for input
                break;
            case Constants.ButtonType.TYPE_GET_AUDIO:
            case Constants.ButtonType.TYPE_GET_IMAGE:
            case Constants.ButtonType.TYPE_GET_VIDEO:
                chatViewModel.handleMedia(button);
                break;
        }

        if (!button.isHidden() && button.getButtonType() != null &&
                !button.getButtonType().equals(Constants.ButtonType.TYPE_GET_ITEM_FROM_SOURCE) && button.isPostToChat()) {
            chatViewModel.replyToRia(Constants.SectionType.TYPE_TEXT, button.getButtonText());
        }

    }

    private Runnable getAutoRunnable(final Button mDefaultButton) {
        return new Runnable() {

            @Override
            public void run() {
                chatViewModel.onButtonClick(mDefaultButton);

            }
        };
    }


}
