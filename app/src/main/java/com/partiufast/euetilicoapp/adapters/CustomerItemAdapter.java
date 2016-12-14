package com.partiufast.euetilicoapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.models.CustomerItem;

import java.util.List;

public class CustomerItemAdapter extends RecyclerView.Adapter<CustomerItemAdapter.CustomerItemViewHolder> {

    private final List<CustomerItem> mCustomerItemList;
    private Resources mResources;
    private Context mContext;

    public CustomerItemAdapter(List<CustomerItem> customerItemList){
        mCustomerItemList = customerItemList;
    }

    public class CustomerItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mCustomerNameTextView, mCustomerPriceTextView;
        public ListView mListView;

        public CustomerItemViewHolder(View itemView) {
            super(itemView);
            mCustomerNameTextView = (TextView) itemView.findViewById(R.id.customer_name_text_view);
            mCustomerPriceTextView = (TextView) itemView.findViewById(R.id.customer_price_text_view);
            mListView = (ListView) itemView.findViewById(android.R.id.list);
        }
    }



    @Override
    public CustomerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item_layout, parent, false);
        mResources = parent.getResources();
        mContext = parent.getContext();
        return new CustomerItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerItemViewHolder holder, int position) {
        holder.mCustomerNameTextView.setText(mCustomerItemList.get(position).getCustomerName());
        holder.mCustomerPriceTextView.setText(mCustomerItemList.get(position).getPriceCurrencyFormat());
        ListParcelAdapter adapter = new ListParcelAdapter(mCustomerItemList.get(position).getProductItemList(), mContext);
        holder.mListView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return mCustomerItemList.size();
    }
}
