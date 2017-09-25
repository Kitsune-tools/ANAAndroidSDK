package com.ana.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.ana.models.Node;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 30-06-2017.
 */

public class NetworkFactory {

    private NetworkCallResponse mCallback;
    private static RequestQueue mRequestQueue;
    private NetworkFactory(NetworkCallResponse mCallback){
        this.mCallback = mCallback;
    }

    public synchronized RequestQueue getRequestQueue(File file){
        if(mRequestQueue == null){
            Cache cache = new DiskBasedCache(file, 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public void networkCall(final Node node, final HashMap<String,String> mHashMap){
        int method = Constants.ApiType.TYPE_GET.equals(node.getApiMethod())? Request.Method.GET:Request.Method.POST;
        StringRequest stringRequest = new StringRequest(method, node.getApiUrl(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        mCallback.onResponse(result);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mCallback.onResponse(null);
                //TODO:Add unable to process in the sectionList
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                List<String> urlParam = node.getRequiredVariables();
                for (String key : urlParam) {
                    param.put(key, mHashMap.get("[~" + key + "]"));
                }
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }
    public interface NetworkCallResponse{
        void onResponse(String response);
    }
}
