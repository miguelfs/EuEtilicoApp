package com.partiufast.euetilicoapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.models.CustomerItem;

import java.util.List;

public class CustomerItemAdapter extends RecyclerView.Adapter<CustomerItemAdapter.CustomerItemViewHolder> {

    private final List<CustomerItem> mCustomerItemList;
    private Resources mResources;
    private Context mContext;
    private UpdatePricesCallback mPricesCallback;

    public CustomerItemAdapter(List<CustomerItem> customerItemList){
        mCustomerItemList = customerItemList;
    }

    public void swipeRemove(int adapterPosition) {
        mCustomerItemList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        mPricesCallback.onUpdateBill();
    }

    public class CustomerItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mCustomerNameTextView, mCustomerPriceTextView;
        public RecyclerView mParcelRecyclerView;
        public ListParcelAdapter mAdapter = new ListParcelAdapter(mContext);



        public CustomerItemViewHolder(View itemView) {
            super(itemView);
            mCustomerNameTextView = (TextView) itemView.findViewById(R.id.customer_name_text_view);
            mCustomerPriceTextView = (TextView) itemView.findViewById(R.id.customer_price_text_view);
            mParcelRecyclerView = (RecyclerView) itemView.findViewById(R.id.parcel_recycler_view);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            mParcelRecyclerView.setLayoutManager(layoutManager);
            mParcelRecyclerView.setAdapter(mAdapter);
            mParcelRecyclerView.setNestedScrollingEnabled(false);
        }
    }



    @Override
    public CustomerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item_layout, parent, false);
        mResources = parent.getResources();
        mContext = parent.getContext();
        mPricesCallback = (UpdatePricesCallback) mContext;
        return new CustomerItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerItemViewHolder holder, int position) {
        holder.mCustomerNameTextView.setText(mCustomerItemList.get(position).getCustomerName());
        holder.mCustomerPriceTextView.setText(mCustomerItemList.get(position).getPriceCurrencyFormat());
        holder.mAdapter.setList(mCustomerItemList.get(position).getProductItemList());
        holder.mAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mCustomerItemList.size();
    }
}
