package com.partiufast.euetilicoapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.callbacks.CustomerDialogCallback;
import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.listeners.ProductNameListener;
import com.partiufast.euetilicoapp.listeners.ProductPriceListener;
import com.partiufast.euetilicoapp.models.CustomerItem;
import com.partiufast.euetilicoapp.models.ProductItem;
import com.partiufast.euetilicoapp.ui.customviews.MultiSelectionSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder> {
    private static List<ProductItem> mProductItemList;

    private Resources mResources;
    private Context mContext;
    private UpdatePricesCallback mPricesCallback;
    private List<CustomerItem> mCustomersList = new ArrayList<>();

    public ProductItemAdapter(List<ProductItem> productItemList, List<CustomerItem> customerItemList) {
        mProductItemList = productItemList;
        mCustomersList = customerItemList;
    }

    private List<String> getCustomerNames(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mCustomersList.size(); i++)
            list.add(mCustomersList.get(i).getCustomerName());
        return list;
    }

    public class ProductItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mProductNameTextView;
        public CurrencyEditText mProductPriceTextView;
        public Spinner mProductCountSpinner;
        public MultiSelectionSpinner mProductPersonListSpinner;

        public ProductNameListener mProductNameListener;
        public ProductPriceListener mProductPriceListener;
        public ProductCountListener mProductCountListener;

        public ProductItemViewHolder(View itemView, ProductNameListener productNameListener, ProductPriceListener productPriceListener, ProductCountListener productCountListener) {
            super(itemView);
            mProductNameTextView = (TextView) itemView.findViewById(R.id.productNameTextView);
            mProductPriceTextView = (CurrencyEditText) itemView.findViewById(R.id.productPriceTextView);
            mProductCountSpinner = (Spinner) itemView.findViewById(R.id.productCountSpinner);
            mProductPersonListSpinner = (MultiSelectionSpinner) itemView.findViewById(R.id.productPersonListSpinner);

            mProductNameListener = productNameListener;
            mProductPriceListener = productPriceListener;
            mProductCountListener = productCountListener;

            mProductNameTextView.addTextChangedListener(mProductNameListener);
            mProductPriceTextView.addTextChangedListener(mProductPriceListener);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mContext.getResources().getStringArray(R.array.units_array));
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mProductCountSpinner.setAdapter(spinnerArrayAdapter);
            mProductCountSpinner.setOnItemSelectedListener(mProductCountListener);

        }
    }

    @Override
    public ProductItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        mResources = parent.getResources();
        mContext = parent.getContext();
        mPricesCallback = (UpdatePricesCallback) mContext;
        return new ProductItemViewHolder(itemView, new ProductNameListener(mProductItemList, mPricesCallback), new ProductPriceListener(mProductItemList, mPricesCallback),
                new ProductCountListener(mProductItemList, mPricesCallback));
    }

    @Override
    public void onBindViewHolder(ProductItemViewHolder holder, int position) {
        holder.mProductNameListener.updatePosition(position);
        holder.mProductPriceListener.updatePosition(position);
        holder.mProductPriceListener.updateCurrencyText(holder.mProductPriceTextView);
        holder.mProductCountListener.updatePosition(position);

        holder.mProductNameTextView.setText(mProductItemList.get(position).getProductName());
        holder.mProductPriceTextView.setText(mProductItemList.get(position).getProductPriceString());
        holder.mProductCountSpinner.setSelection(mProductItemList.get(position).getProductCount() - 1);
        holder.mProductPersonListSpinner.setItems(getCustomerNames());
        holder.mProductPersonListSpinner.setSelection(mProductItemList.get(position).getProductCustomerList());
    }

    @Override
    public int getItemCount() {
        return mProductItemList.size();
    }

    private class ProductCountListener implements AdapterView.OnItemSelectedListener {
        private final UpdatePricesCallback mCallback;
        private final List<ProductItem> mList;
        private int productPosition;


        ProductCountListener(List<ProductItem> list, UpdatePricesCallback callback){
            mCallback = callback;
            mList = list;

        }

        public void updatePosition(int position) {
            productPosition = position;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int positionSpinner, long id) {
            mList.get(productPosition).setProductCount(positionSpinner + 1);
            mCallback.onUpdateBill();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    public static class CustomerSpinner extends MultiSelectionSpinner {
        private final List<String> mCustomersListString;
        private final List<ProductItem> mProducts;
        private final CustomerDialogCallback mDialogCallback;
        private int position;
        //      String[] _items;
        //      boolean[] mSelection;


        public CustomerSpinner(Context context, List<String> customers, List<ProductItem> products){
            super(context);
            mCustomersListString = customers;
            mDialogCallback = (CustomerDialogCallback) context;
            mProducts = products;
        }
/*
        public CustomerSpinner(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
*/
        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public boolean performClick() {
            super.performClick();
            /*setItems(mCustomersListString);
            setSelection(mProducts.get(position).getProductCustomerList());
            mDialogCallback.onClickCustomersSpinner(_items, mSelection, this);*/

/*
            setItems(mCustomersListString);
            setSelection(mProducts.get(position).getProductCustomerList());
            final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.CustomDialog));
            if (_items == null){
                final EditText input = new EditText(getContext());
                TabbedActivity.PersonListFragment.setCustomerBuilder(getContext(),builder, input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TabbedActivity.PersonListFragment.setLists(input);
                        setItems(mCustomersList);
                        setSelection(mCustomersList);
                        mProductItemList.get(position).setProductPersonList(getSelectedStrings());
                        TabbedActivity.updatePrices();
                    }
                });
            } else {
                builder.setTitle("Selecione os consumidores:")
                        .setMultiChoiceItems(_items, mSelection, this);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        mProductItemList.get(position).setProductPersonList(getSelectedStrings());
                        TabbedActivity.updatePrices();
                    }
                });
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mProductItemList.get(position).setProductPersonList(getSelectedStrings());
                        TabbedActivity.updatePrices();
                    }
                });
            }
            builder.show();*/
            return true;
        }
    }

}
