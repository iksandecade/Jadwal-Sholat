package com.example.iksandecade.jadwalsholat.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iksandecade.jadwalsholat.model.WaktuModel;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.List;

/**
 * Created by iksandecade on 03/03/17.
 */

public class ListDelegatesAdapter extends RecyclerView.Adapter {
    private AdapterDelegatesManager<List<WaktuModel>> delegatesManager;
    private List<WaktuModel> waktuModels;
    private OnLoadmoreListener onLoadmoreListener;

    public ListDelegatesAdapter(Activity activity, List<WaktuModel> waktuModels, RecyclerView recyclerView) {
        this.waktuModels = waktuModels;
        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new MainRecyclerAdapter(activity));
        delegatesManager.addDelegate(new SeparatorAdapter(activity));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(waktuModels, position, holder);
    }

    @Override
    public int getItemCount() {
        return waktuModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(waktuModels, position);
    }

    public void setOnLoadMoreListener(OnLoadmoreListener onLoadMoreListener) {
        this.onLoadmoreListener = onLoadMoreListener;
    }
}
