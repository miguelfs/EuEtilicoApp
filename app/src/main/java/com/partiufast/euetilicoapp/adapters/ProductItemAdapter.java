package com.partiufast.euetilicoapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.models.ProductItem;
import com.partiufast.euetilicoapp.ui.customviews.MultiSelectionSpinner;

import java.util.Arrays;
import java.util.List;

public class ProductItemAdapter  extends  RecyclerView.Adapter<ProductItemAdapter.productItemViewHolder>{
    private static List<ProductItem> mProductItemList;
    private Resources mResources;
    private Context mContext;
    private List<String> mCustomersList = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");

    public ProductItemAdapter(List<ProductItem> productItemList){
        mProductItemList = productItemList;
    }

    public class productItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mProductNameTextView;
        public CurrencyEditText mProductPriceTextView;
        public Spinner mProductCountSpinner;
        public MultiSelectionSpinner mProductPersonListSpinner;

        public productItemViewHolder(View itemView) {
            super(itemView);
            mProductNameTextView = (TextView) itemView.findViewById(R.id.productNameTextView);
            mProductPriceTextView = (CurrencyEditText) itemView.findViewById(R.id.productPriceTextView);
            mProductCountSpinner = (Spinner) itemView.findViewById(R.id.productCountSpinner);
            mProductPersonListSpinner = (MultiSelectionSpinner) itemView.findViewById(R.id.productPersonListSpinner);
        }
    }

    @Override
    public productItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        mResources = parent.getResources();
        mContext = parent.getContext();
        return new productItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(productItemViewHolder holder, int position) {

        holder.mProductNameTextView.setText(mProductItemList.get(position).getProductName());
        holder.mProductPriceTextView.setText(mProductItemList.get(position).getProductPriceString());
        holder.mProductCountSpinner.setSelection(mProductItemList.get(position).getProductCount() - 1);
        holder.mProductPersonListSpinner.setItems(mCustomersList);
        holder.mProductPersonListSpinner.setSelection(mProductItemList.get(position).getProductCustomerList());
    }

    @Override
    public int getItemCount() {
        return mProductItemList.size();
    }



}
