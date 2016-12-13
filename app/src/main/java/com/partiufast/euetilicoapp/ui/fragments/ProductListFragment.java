package com.partiufast.euetilicoapp.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.adapters.ProductItemAdapter;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {
    public static final String ARG_PRODUCT_LIST = "ARG_PRODUCT_LIST";

    private ArrayList<ProductItem> mProductItems;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProductItemAdapter mProductItemAdapter;

    public static ProductListFragment newInstance(ArrayList<ProductItem> productItems) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PRODUCT_LIST, productItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductItems = getArguments().getParcelableArrayList(ARG_PRODUCT_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mProductItemAdapter = new ProductItemAdapter(mProductItems);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mProductItemAdapter);

        return rootView;
    }

    public void notifyAdapter(){
        mProductItemAdapter.notifyDataSetChanged();
    }
}