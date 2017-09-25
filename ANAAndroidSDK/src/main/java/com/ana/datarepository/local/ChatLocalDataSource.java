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

package com.ana.datarepository.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ana.datarepository.ChatDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


/**
 * Concrete implementation of a data source as a db.
 */
public class ChatLocalDataSource implements ChatDataSource {

    private static ChatLocalDataSource INSTANCE;

//    private TasksDbHelper mDbHelper;

    // Prevent direct instantiation.
    private ChatLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
//        mDbHelper = new TasksDbHelper(context);
    }

    public static ChatLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ChatLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getNodes(@NonNull String url, @NonNull LoadNodesCallback callback) {

    }

    @Override
    public void getSections(@NonNull String nodeId, @NonNull GetSectionsCallback callback) {

    }
}
