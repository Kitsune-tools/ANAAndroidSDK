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

package com.ana.adapters;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ana.models.Button;
import com.ana.models.Section;

/**
 * Contains {@link BindingAdapter}s for the {@link Section} list.
 */
public class ChatListBindings {

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:additems")
    public static void setItem(RecyclerView recyclerView, ObservableArrayList<Button> mButtons) {

        ChatButtonsAdapter adapter = (ChatButtonsAdapter) recyclerView.getAdapter();

        if (mButtons != null && mButtons.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.refresh(mButtons);
        } else {
            adapter.refresh(null);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

}
