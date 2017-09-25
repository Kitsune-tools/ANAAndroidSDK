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

package com.ana.datarepository;

import android.support.annotation.NonNull;

import java.util.LinkedHashMap;

import com.ana.models.Node;
import com.ana.models.Section;

/**
 * Main entry point for accessing tasks data.
 */
public interface ChatDataSource {

    interface LoadNodesCallback {

        void onNodesLoaded(LinkedHashMap<String, Node> hmNodeList);

        void onDataNotAvailable();
    }

    interface GetSectionsCallback {

        void onSectionLoaded(Section section);

        void onDataNotAvailable();
    }

    void getNodes(@NonNull String url, @NonNull LoadNodesCallback callback);

    void getSections(@NonNull String nodeId, @NonNull GetSectionsCallback callback);

}
