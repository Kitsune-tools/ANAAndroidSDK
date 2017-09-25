package com.ana.utils;


import com.ana.models.Node;

import java.util.HashMap;
import java.util.List;

public class ChatConfig {
    public String botImage;
    public String botName = "Ria";
    public String chatBgColor = "#ffffff";
    public String botBubbleColor = "#ffb900";
    public String userBubbleColor = "#FAFAFA";
    public String chatTextColor = "#808080";
    private List<Node> mChatDatamodel;

    private HashMap<String,String> mUserData = new HashMap<>();
    public void setChatData(List<Node> chatData){
        mChatDatamodel = chatData;
    }
    public List<Node> getChatData(){
        return mChatDatamodel;
    }

    public HashMap<String,String> getUserData(){
        return mUserData;
    }

}
