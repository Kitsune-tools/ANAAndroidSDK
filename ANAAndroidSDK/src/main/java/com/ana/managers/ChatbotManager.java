package com.ana.managers;

import android.app.Activity;
import android.content.Intent;

import com.ana.R;
import com.ana.ui.ChatbotActivity;
import com.ana.utils.ChatConfig;
import com.ana.utils.ChatPrefUtils;

/**
 * To create instance, call static method <b>getInstance</b>
 * Use this class to customize library
 *
 * @see ChatConfig
 */

public class ChatbotManager {

    private ChatConfig mChatConfig = new ChatConfig();

    private static ChatbotManager sChatManager;

    private Activity mContext;


    /**
     * To get instance
     *
     * @return ChatbotManager
     */
    public synchronized static ChatbotManager getInstance(Activity mContext) {
        if (sChatManager == null) {
            sChatManager = new ChatbotManager(mContext);
        }
        return sChatManager;
    }

    private ChatbotManager(Activity context) {
        mContext = context;
    }

    /**
     * Get customized ChatConfig class to customize chat
     *
     * @return ChatConfig
     */
    public ChatConfig getChatConfig() {
        return mChatConfig;
    }

    /**
     * Used to start {@link ChatbotActivity}
     * passing mChatConfig instance of ChatConfig class through intent
     */
    public void startChat(String webURL) {
        ChatPrefUtils.setStringPreference(mContext, ChatPrefUtils.KEY_URL, webURL);
        Intent chatIntent = new Intent(mContext, ChatbotActivity.class);
        mContext.overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);
        mContext.startActivity(chatIntent);
    }

    /**
     * Use when stop chat use
     */
    public void endChat() {
        mContext = null;
        sChatManager = null;
    }
}

