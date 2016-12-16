package com.partiufast.euetilicoapp.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.adapters.ProductItemAdapter;
import com.partiufast.euetilicoapp.models.CustomerItem;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {
    public static final String ARG_PRODUCT_LIST = "ARG_PRODUCT_LIST";
    private static final String ARG_PRODUCT_LIST_CUSTOMERS = "ARG_PRODUCT_LIST_CUSTOMERS";

    private ArrayList<ProductItem> mProductItems;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProductItemAdapter mProductItemAdapter;
    private List<CustomerItem> mCustomerItems;

    public static ProductListFragment newInstance(ArrayList<ProductItem> productItems, ArrayList<CustomerItem> customerItems) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PRODUCT_LIST, productItems);
        args.putParcelableArrayList(ARG_PRODUCT_LIST_CUSTOMERS, customerItems);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductItems = getArguments().getParcelableArrayList(ARG_PRODUCT_LIST);
            mCustomerItems = getArguments().getParcelableArrayList(ARG_PRODUCT_LIST_CUSTOMERS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mProductItemAdapter = new ProductItemAdapter(mProductItems, mCustomerItems);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mProductItemAdapter);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT ) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getContext(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //mProductItemAdapter.onProductRemove(viewHolder, mRecyclerView, (CoordinatorLayout) getActivity().findViewById(R.id.main_content));
                mProductItemAdapter.swipeRemove(viewHolder.getAdapterPosition());

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        /*SwipeableRecyclerViewTouchListener swipeTouchListener = new SwipeableRecyclerViewTouchListener(mRecyclerView, new ProductSwipeListener(mProductItems, mProductItemAdapter));
        mRecyclerView.addOnItemTouchListener(swipeTouchListener);*/
        return rootView;
    }
    public void setCustomerStringList(List<String> customerStringList) {
        mProductItemAdapter.setCustomersList(customerStringList);
    }


    private class SwipeRightCallback extends ItemTouchHelper.SimpleCallback {


        public SwipeRightCallback(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mUpdatePricesCallback = (UpdatePricesCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }*/

    public void notifyAdapter(){
        if (mProductItemAdapter == null)
            mProductItemAdapter = new ProductItemAdapter(mProductItems, mCustomerItems);
        mProductItemAdapter.notifyDataSetChanged();

    }

    public void clearAdapter(int count){
        if (mProductItemAdapter == null) {
            mProductItemAdapter = new ProductItemAdapter(mProductItems, mCustomerItems);
        }
        mProductItemAdapter.notifyItemRangeRemoved(0, count);
    }
}