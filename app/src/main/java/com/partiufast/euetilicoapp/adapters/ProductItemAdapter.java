package com.partiufast.euetilicoapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.callbacks.CreateBuilderCallback;
import com.partiufast.euetilicoapp.callbacks.OkBuilderCallback;
import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.listeners.ProductCountListener;
import com.partiufast.euetilicoapp.listeners.ProductNameListener;
import com.partiufast.euetilicoapp.listeners.ProductPriceListener;
import com.partiufast.euetilicoapp.models.CustomerItem;
import com.partiufast.euetilicoapp.models.ProductItem;
import com.partiufast.euetilicoapp.ui.customviews.CustomCurrencyText;
import com.partiufast.euetilicoapp.ui.customviews.CustomerSpinner;

import java.util.ArrayList;
import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder> {
    private  CreateBuilderCallback mCreateBuilderCallback;
    private OkBuilderCallback mOkBuilderCallback;
    private UpdatePricesCallback mUpdatePricesCallback;

    private List<ProductItem> mProductItemList;
    private Context mContext;
    private UpdatePricesCallback mPricesCallback;
    private List<CustomerItem> mCustomersList;

    public ProductItemAdapter(List<ProductItem> productItemList, List<CustomerItem> customerItemList) {
        mProductItemList = productItemList;
        mCustomersList = customerItemList;
    }




    public void swipeRemove(int adapterPosition) {
        mProductItemList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        mPricesCallback.onUpdateBill();
    }

    public void setCustomersList(List<String> customersList) {
       /* mCustomersList.clear();
        mCustomersList.addAll(customersList);*/
    }

    public class ProductItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mProductNameTextView;
        public CustomCurrencyText mProductPriceTextView;
        public Spinner mProductCountSpinner;
        public CustomerSpinner mProductCustomerListSpinner;


        public ProductNameListener mProductNameListener;
        public ProductPriceListener mProductPriceListener;
        public ProductCountListener mProductCountListener;

        public ProductItemViewHolder(View itemView, ProductNameListener productNameListener, ProductPriceListener productPriceListener, ProductCountListener productCountListener) {
            super(itemView);
            mProductNameTextView = (TextView) itemView.findViewById(R.id.productNameTextView);
            mProductPriceTextView = (CustomCurrencyText) itemView.findViewById(R.id.productPriceTextView);
            mProductCountSpinner = (Spinner) itemView.findViewById(R.id.productCountSpinner);
            mProductCustomerListSpinner = (CustomerSpinner) itemView.findViewById(R.id.productPersonListSpinner);

            mProductNameListener = productNameListener;
            mProductNameTextView.addTextChangedListener(mProductNameListener);

            mProductPriceTextView.setDefaultHintEnabled(false);
            mProductPriceListener = productPriceListener;
            mProductPriceTextView.addTextChangedListener(mProductPriceListener);


            mProductCountListener = productCountListener;
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mContext.getResources().getStringArray(R.array.units_array));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mProductCountSpinner.setAdapter(spinnerArrayAdapter);
            mProductCountSpinner.setOnItemSelectedListener(mProductCountListener);

            mProductCustomerListSpinner.setParameters(mCustomersList, mProductItemList, mCreateBuilderCallback, mOkBuilderCallback, mUpdatePricesCallback);

        }
    }

    @Override
    public ProductItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        mContext = parent.getContext();
        mPricesCallback = (UpdatePricesCallback) mContext;
        mCreateBuilderCallback = (CreateBuilderCallback) mContext;
        mOkBuilderCallback = (OkBuilderCallback) mContext;
        mUpdatePricesCallback = (UpdatePricesCallback) mContext;
        return new ProductItemViewHolder(itemView, new ProductNameListener(mProductItemList, mPricesCallback), new ProductPriceListener(mProductItemList, mPricesCallback),
                new ProductCountListener(mProductItemList, mPricesCallback));
    }

    @Override
    public void onBindViewHolder(ProductItemViewHolder holder, int position) {
        holder.mProductNameListener.updatePosition(position);
        holder.mProductNameTextView.setText(mProductItemList.get(position).getProductName());


        holder.mProductPriceListener.updatePosition(position);
        holder.mProductPriceListener.updateCurrencyText(holder.mProductPriceTextView);
        // if (mProductItemList.size()!=1 && position!=0)
        holder.mProductPriceTextView.setText(mProductItemList.get(position).getEditableProductPrice());

        holder.mProductCountListener.updatePosition(position);
        holder.mProductCountSpinner.setSelection(mProductItemList.get(position).getProductCount() - 1);
        /*holder.mProductCustomerListSpinner.setItems(getCustomerNames());
        holder.mProductCustomerListSpinner.setSelection(mProductItemList.get(position).getProductCustomerList());*/
        holder.mProductCustomerListSpinner.updatePosition(position);
        holder.mProductCustomerListSpinner.setItems(getAllCustomersNames());
        holder.mProductCustomerListSpinner.setSelection(mProductItemList.get(position).getProductCustomerList());


    }

    public   List<String>  getAllCustomersNames(){
        List<String> names = new ArrayList<>();
        for (int i = 0; i < mCustomersList.size(); i++)
            names.add(mCustomersList.get(i).getCustomerName());
        return names;
    }

    @Override
    public int getItemCount() {
        return mProductItemList.size();
    }


}
