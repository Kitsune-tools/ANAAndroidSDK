package com.ana.managers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.ana.adapters.AddressCardSectionAdapter;
import com.ana.adapters.AudioSectionAdapter;
import com.ana.adapters.CardSectionAdapter;
import com.ana.adapters.EmbeddedHtmlSectionAdapter;
import com.ana.adapters.FormSectionAdapter;
import com.ana.adapters.GifSectionAdapter;
import com.ana.adapters.GraphSectionAdapter;
import com.ana.adapters.HeaderSectionAdapter;
import com.ana.adapters.ImageViewSectionAdapter;
import com.ana.adapters.OTPSectionAdapter;
import com.ana.adapters.SectionAdapter;
import com.ana.adapters.TextViewSectionAdapter;
import com.ana.adapters.TypingSectionAdapter;
import com.ana.adapters.UnConfirmedCardAdapter;
import com.ana.adapters.UnconfirmedAddressCardSectionAdapter;
import com.ana.adapters.VideoSectionAdapter;
import com.ana.models.Section;
import com.ana.viewmodels.ChatViewModel;

/**
 * Created by Admin on 13-07-2017.
 */

public class SectionAdapterManager {

    public enum SectionTypeEnum {

        Image(0), Text(1), Graph(2), Gif(3), Audio(4), Video(5), Header(6), EmbeddedHtml(7),
        SubmitForm(8), Typing(9), Card(10), AddressCard(11), UnConfirmedCard(12), UnConfirmedAddressCard(13), PrintOTP(14);

        private final int val;

        private SectionTypeEnum(int val) {
            this.val = val;
        }

        public int getValue() {
            return val;
        }
    }

    ArrayList<SectionAdapter<Section>> adapterList;
    Context mContext;
    public SectionAdapterManager(Context context, ChatViewModel model) {
        mContext = context;
        adapterList = new ArrayList<>();
        makeSectionAdapterList(model);
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        // may be we add adapter in hashmap here if position is not present
        return adapterList.get(position).onCreateViewHolder(parent);
    }


    public void onBindViewHolder(RecyclerView.ViewHolder holder, Section node, int position) {
        adapterList.get(position).onBindViewHolder(holder, node);
    }

    private void makeSectionAdapterList( ChatViewModel model){

        adapterList.add(new ImageViewSectionAdapter(mContext,model));
        adapterList.add(new TextViewSectionAdapter(mContext,model));
        adapterList.add(new GraphSectionAdapter(mContext));
        adapterList.add(new GifSectionAdapter(mContext));
        adapterList.add(new AudioSectionAdapter(mContext));
        adapterList.add(new VideoSectionAdapter(mContext));
        adapterList.add(new HeaderSectionAdapter(mContext));
        adapterList.add(new EmbeddedHtmlSectionAdapter(mContext));
        adapterList.add(new FormSectionAdapter(mContext));
        adapterList.add(new TypingSectionAdapter(mContext));
        adapterList.add(new CardSectionAdapter(mContext,model));
        adapterList.add(new AddressCardSectionAdapter(mContext,model));
        adapterList.add(new UnConfirmedCardAdapter(mContext));
        adapterList.add(new UnconfirmedAddressCardSectionAdapter(mContext));
        adapterList.add(new OTPSectionAdapter(mContext));
    }

}
