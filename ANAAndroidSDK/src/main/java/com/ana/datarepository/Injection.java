package com.ana.datarepository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ana.datarepository.local.ChatLocalDataSource;
import com.ana.datarepository.remote.ChatRemoteDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by admin on 7/26/2017.
 */

public class Injection {

    public static ChatRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return ChatRepository.getInstance(ChatRemoteDataSource.getInstance(),
                ChatLocalDataSource.getInstance(context));
    }

}
