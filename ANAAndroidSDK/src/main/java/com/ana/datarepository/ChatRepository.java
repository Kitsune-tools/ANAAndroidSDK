package com.ana.datarepository;

import android.support.annotation.NonNull;

import com.ana.datarepository.local.ChatLocalDataSource;
import com.ana.datarepository.remote.ChatRemoteDataSource;
import com.ana.models.Node;

import java.util.LinkedHashMap;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Admin on 25-07-2017.
 */

public class ChatRepository implements ChatDataSource {


    private static ChatRepository INSTANCE = null;

    private final ChatDataSource mChatRemoteDataSource;

    private final ChatDataSource mChatLocalDataSource;


    // Prevent direct instantiation.
    private ChatRepository(@NonNull ChatRemoteDataSource chatRemoteDataSource,
                           @NonNull ChatLocalDataSource chatLocalDataSource) {
        mChatRemoteDataSource = checkNotNull(chatRemoteDataSource);
        mChatLocalDataSource = checkNotNull(chatLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param chatRemoteDataSource the backend data source
     * @param chatLocalDataSource  the device storage data source
     * @return the {@link ChatRepository} instance
     */
    public static ChatRepository getInstance(ChatRemoteDataSource chatRemoteDataSource,
                                             ChatLocalDataSource chatLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ChatRepository(chatRemoteDataSource, chatLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getNodes(@NonNull String url, @NonNull LoadNodesCallback callback) {
        getNodesFromRemoteDataSource(url, callback);
    }

    private void getNodesFromRemoteDataSource(String url, @NonNull final LoadNodesCallback callback) {
        mChatRemoteDataSource.getNodes(url, new LoadNodesCallback() {


            @Override
            public void onNodesLoaded(LinkedHashMap<String, Node> hmNodeList) {
                callback.onNodesLoaded(hmNodeList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }


    @Override
    public void getSections(@NonNull String nodeId, @NonNull GetSectionsCallback callback) {

    }
}
