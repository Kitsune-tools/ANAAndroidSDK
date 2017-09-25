package com.ana.handlers;

import android.text.TextUtils;
import android.util.Log;

import com.ana.models.Node;
import com.ana.network.NetworkAdapter;
import com.ana.network.NetworkRequestModel;
import com.ana.utils.Constants;
import com.ana.viewmodels.ChatViewModel;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Admin on 24-07-2017.
 */

public class NodeHandler {


    private static NodeHandler nodeHandler;

    private ChatViewModel chatViewModel;


    public static NodeHandler getInstance(ChatViewModel chatViewModel) {

        if (nodeHandler == null) {
            synchronized (NodeHandler.class) {
                nodeHandler = new NodeHandler(chatViewModel);
            }
        }
        return nodeHandler;
    }

    private NodeHandler(ChatViewModel chatViewModel) {
        this.chatViewModel = chatViewModel;
    }

    public void handleMessage(Node mNode) {
        switch (mNode.getNodeType()) {

            case Constants.NodeType.TYPE_API_CALL:
                handleRequest(mNode);
                break;
            case Constants.NodeType.TYPE_CARD:
            case Constants.NodeType.TYPE_COMBINATION:
                chatViewModel.onNodeProcessCompletion();
                break;
        }

    }

    private void handleRequest(final Node mNode) {

        NetworkRequestModel model = new NetworkRequestModel();
        model.setMethod(mNode.getApiMethod());
        model.setUrl(mNode.getApiUrl());

        // process required variables
        List<String> requiredParams = mNode.getRequiredVariables();
        if (requiredParams != null && requiredParams.size() > 0) {
            List<NetworkRequestModel.QueryParam> paramList = new ArrayList<>();
            for (String key : requiredParams) {
                paramList.add(model.new QueryParam(key,
                        chatViewModel.getChatVariables().get("[~" + key + "]")));
            }
            model.setParams(paramList);
        }

        NetworkAdapter adapter = NetworkAdapter.getInstance();
        Request request = adapter.getRequest(model);
        if(request == null){
            chatViewModel.onNodeProcessCompletion();
            return;
        }
        adapter.addRequestInQueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("ggg", e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response queryResponse) throws IOException {
                if (queryResponse.isSuccessful()) {
                    try {
                        JSONObject response = new JSONObject(queryResponse.body().string());
                        Iterator<?> keys = response.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();

                            if (!key.equals(chatViewModel.KEY_NEXT_NODE_ID)) {
                                chatViewModel.setChatVariable(key, response.getString(key));
                            }
                        }


                        if (!TextUtils.isEmpty(response.optString(chatViewModel.KEY_NEXT_NODE_ID))) {
                            mNode.setNextNodeId(response.getString(chatViewModel.KEY_NEXT_NODE_ID));
                        }

                        DocumentContext context = JsonPath.parse(response.toString());
                        if (context != null) {
                            mNode.setDocumentContext(context);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    chatViewModel.onNodeProcessCompletion();
                } else {
                    throw new IOException("error found");
                }
            }
        });


    }

}
