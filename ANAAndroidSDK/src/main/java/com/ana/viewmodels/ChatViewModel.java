package com.ana.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ana.datarepository.ChatDataSource;
import com.ana.datarepository.ChatRepository;
import com.ana.handlers.ButtonHandler;
import com.ana.handlers.NodeHandler;
import com.ana.handlers.SectionHandler;
import com.ana.models.Button;
import com.ana.models.Node;
import com.ana.models.Section;
import com.ana.utils.ChatPrefUtils;
import com.ana.utils.CommonMethods;
import com.ana.utils.Constants;

/**
 * Created by admin on 7/26/2017.
 */

public class ChatViewModel extends BaseObservable {


    public final ObservableField<Section> sections = new ObservableField<Section>();

    public ObservableField<Button> mMediaButton = new ObservableField<Button>();

    public final ObservableField<Boolean> showKeyBoard = new ObservableField<Boolean>(false);

    public final ObservableField<String> chatInput = new ObservableField<String>("");

    private Context mContext; // To avoid leaks, this must be an Application Context.

    private final ChatRepository mChatRepository;

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public Map<String, String> mDataMap = new HashMap<>();

    public ObservableArrayList<Button> mVisibleButtons = new ObservableArrayList<>();

    private Button mCurrButton;

    private String mCurrNodeId;

    private LinkedHashMap<String, Node> hmNodeList;

    public final String KEY_NEXT_NODE_ID = "NextNodeId";

    public ChatViewModel(ChatRepository repository, Context context) {
        mContext = context.getApplicationContext();
        mChatRepository = repository;
    }


    public void start() {
        loadNodes();
    }


    private void loadNodes() {

        dataLoading.set(true);

        String chatURL = ChatPrefUtils.getStringPreference(mContext, ChatPrefUtils.KEY_URL);

        mChatRepository.getNodes(chatURL, new ChatDataSource.LoadNodesCallback() {

            @Override
            public void onNodesLoaded(LinkedHashMap<String, Node> hmList) {
                dataLoading.set(false);
                hmNodeList = hmList;

                String mFirstNodeId = hmNodeList.keySet().iterator().next();
                processNode(mFirstNodeId);
            }

            @Override
            public void onDataNotAvailable() {
                dataLoading.set(false);
            }
        });

    }

    public void processNode(String nodeId) {

        mCurrNodeId = nodeId;
        chatInput.set("");
        showKeyBoard.set(false);
        mVisibleButtons.clear();
        mCurrButton = null;

        NodeHandler.getInstance(ChatViewModel.this).handleMessage(hmNodeList.get(nodeId));
    }

    public void onNodeProcessCompletion() {
        Node mNode = hmNodeList.get(mCurrNodeId);
        ArrayList<Section> arrSections = (ArrayList<Section>) mNode.getSections();

        if (arrSections != null && arrSections.size() > 0) {
            SectionHandler.getInstance(this).handleMessage(arrSections);
        } else {
            onSectionRenderCompletion();
        }
    }

    public void onSectionAdded(Section mSection) {
        sections.set(mSection);
    }

    public void onSectionRenderCompletion() {

        Node mNode = hmNodeList.get(mCurrNodeId);
        ArrayList<Button> arrButtons = (ArrayList<Button>) mNode.getButtons();

        if (arrButtons != null && arrButtons.size() > 0) {
            ButtonHandler.getInstance(this).handleMessage(arrButtons);
        }

    }

    public void onButtonClick(Button mButton) {
        this.mCurrButton = mButton;
        ButtonHandler.getInstance(this).handleMessage(mButton);
    }

    public void onButtonRenderCompletion(ArrayList<Button> arrInputButtons) {
        this.mVisibleButtons.addAll(arrInputButtons);
    }

    public void onButtonRenderCompletion(String nodeId) {

        if (TextUtils.isEmpty(nodeId)) {
            nodeId = hmNodeList.get(mCurrNodeId).getNextNodeId();
        }

        processNode(nodeId);
    }

    public void showInputText(Button mButton) {
        this.mCurrButton = mButton;
        showKeyBoard.set(true);
    }

    public Node getCurrNode() {
        return hmNodeList.get(mCurrNodeId);
    }

    public Button getCurrButton() {
        return mCurrButton;
    }

    public String getParsedPrefixPostfixText(String text) {
        if (text == null)
            return null;
        Matcher m = Pattern.compile("\\[~(.*?)\\]").matcher(text);
        while (m.find()) {
            if (mDataMap.get(m.group()) != null) {
                text = text.replace(m.group(), mDataMap.get(m.group()));
            }
        }
        return text;
    }

    public void onSendMessageClick(View view) {
        if (!chatInput.get().trim().equals("")) {

            setChatVariable(getCurrNode().getVariableName(), chatInput.get().trim());
            if (!mCurrButton.isPostToChat()) {
                processNode(mCurrButton.getNextNodeId());
            } else {
                replyToRia(Constants.SectionType.TYPE_TEXT, chatInput.get());
                processNode(mCurrButton.getNextNodeId());
            }

        }
    }

    public void setChatVariable(String key, String value) {
        if (key != null) {
            mDataMap.put("[~" + key + "]", value);
        }
    }

    public Map<String, String> getChatVariables() {
        return mDataMap;
    }

    public void replyToRia(String type, String... msg) {

        Section section = new Section();
        section.setDateTime(CommonMethods.getFormattedDate(new Date()));

        switch (type) {
            case Constants.SectionType.TYPE_TEXT:
                section.setFromRia(false);
                section.setSectionType(Constants.SectionType.TYPE_TEXT);
                section.setText(getParsedPrefixPostfixText(msg[0]));
                onSectionAdded(section);
                break;

        }
        section.setShowDate(true);
    }


    public Section getSection() {
        return sections.get();
    }


    public void handleMedia(Button button) {
        mMediaButton.set(button);
    }
}
