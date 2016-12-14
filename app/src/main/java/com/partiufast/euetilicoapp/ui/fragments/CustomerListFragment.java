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
import android.widget.Toast;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.adapters.CustomerItemAdapter;
import com.partiufast.euetilicoapp.adapters.ProductItemAdapter;
import com.partiufast.euetilicoapp.models.CustomerItem;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.util.ArrayList;

public class CustomerListFragment extends Fragment{


    private ArrayList<CustomerItem> mCustomerItems;
    public static final String ARG_CUSTOMER_LIST = "ARG_CUSTOMER_LIST";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CustomerItemAdapter mCustomerItemAdapter;

    public static CustomerListFragment newInstance(ArrayList<CustomerItem> customerItems) {
        CustomerListFragment fragment = new CustomerListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CUSTOMER_LIST, customerItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCustomerItems = getArguments().getParcelableArrayList(ARG_CUSTOMER_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mCustomerItemAdapter = new CustomerItemAdapter(mCustomerItems);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCustomerItemAdapter);
        return rootView;
    }

    public void notifyAdapter(){
        if (mCustomerItemAdapter == null)
            mCustomerItemAdapter = new CustomerItemAdapter(mCustomerItems);
        mCustomerItemAdapter.notifyDataSetChanged();

    }

    public void clearAdapter(int count){
        if (mCustomerItemAdapter == null) {
            mCustomerItemAdapter = new CustomerItemAdapter(mCustomerItems);
            Log.e("ERROR EU ETILICO: ", "customer adapter = null");
            Toast.makeText(getContext(),"customer adapter = null", Toast.LENGTH_SHORT ).show();
        }
        mCustomerItemAdapter.notifyItemRangeRemoved(0, count);
    }
}
