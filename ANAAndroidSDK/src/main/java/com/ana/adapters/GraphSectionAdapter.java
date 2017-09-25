package com.ana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ana.R;

import com.ana.models.Section;

/**
 * Created by Admin on 13-07-2017.
 */

public class GraphSectionAdapter extends SectionAdapter<Section> {

    public GraphSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_graph_row_layout, parent, false);
        return new GraphViewholder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section section) {

    }

    private class GraphViewholder extends RecyclerView.ViewHolder {

        private GraphViewholder(View itemView) {
            super(itemView);
        }
    }


}
