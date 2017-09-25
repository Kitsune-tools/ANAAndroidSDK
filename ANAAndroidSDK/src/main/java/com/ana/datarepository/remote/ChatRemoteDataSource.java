/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ana.datarepository.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ana.datarepository.ChatDataSource;
import com.ana.models.Node;
import com.ana.network.NetworkAdapter;
import com.ana.network.NetworkRequestModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static com.ana.network.NetworkUtils.Method.GET;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class ChatRemoteDataSource implements ChatDataSource {

    private static ChatRemoteDataSource INSTANCE;


    public static ChatRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatRemoteDataSource();
        }
        return INSTANCE;
    }

    private ChatRemoteDataSource() {
    }

    @Override
    public void getNodes(@NonNull String url, @NonNull final LoadNodesCallback callback) {

        NetworkAdapter adapter = NetworkAdapter.getInstance();
        NetworkRequestModel model = new NetworkRequestModel();
        model.setMethod(GET);

        if (!url.contains("http://") && !url.contains("https://"))
            url = "http://" + url;

        model.setUrl(url);

        Request request = adapter.getRequest(model);
        if (request != null) {
            adapter.addRequestInQueue(request, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.v("ggg", e.getMessage());
                    callback.onDataNotAvailable();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String s = response.body().string();
                        Log.v("ggg", s);

                        List<Node> modelList;
                        modelList = Arrays.asList(new Gson().fromJson(s, Node[].class));

                        LinkedHashMap<String, Node> hmNodelIst = new LinkedHashMap<>();

                        for (Node node : modelList) {
                            hmNodelIst.put(node.getId(), node);
                        }
                        callback.onNodesLoaded(hmNodelIst);

                    } else {
                        throw new IOException("error found");
                    }
                }
            });
        }
        /*try {
            InputStream is = new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/json.json"));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            List<Node> modelList = new ArrayList<Node>();
            Gson mGson = new Gson();
            modelList = Arrays.asList(mGson.fromJson(json, Node[].class));

            LinkedHashMap<String, Node> hmNodelIst = new LinkedHashMap<>();

            if (modelList != null) {
                for (Node node : modelList) {
                    hmNodelIst.put(node.getId(), node);
                }
            }

            callback.onNodesLoaded(hmNodelIst);

        } catch (IOException ex) {
            callback.onDataNotAvailable();
            ex.printStackTrace();
        }*/

    }

    @Override
    public void getSections(@NonNull String nodeId, @NonNull GetSectionsCallback callback) {

    }
}
