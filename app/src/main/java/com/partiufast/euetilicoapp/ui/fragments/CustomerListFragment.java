package com.partiufast.euetilicoapp.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.models.CustomerItem;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.util.ArrayList;

public class CustomerListFragment extends Fragment{


    private ArrayList<CustomerItem> mCustomerItems;
    public static final String ARG_CUSTOMER_LIST = "ARG_CUSTOMER_LIST";

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
        } else {
            Log.e("EU ETILICO ERROR - ", "mCustomerItems == null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d("productlist instance", "yay");
        if (mCustomerItems!=null)
        Log.d("customerlist size = ", ""+ mCustomerItems.size());
        return rootView;
    }
}
