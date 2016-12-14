package com.partiufast.euetilicoapp.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.util.List;


public class ProductNameListener implements TextWatcher {
    private final List<ProductItem> mProducts;
    private final UpdatePricesCallback mCallback;
    private int position;

    public ProductNameListener(List<ProductItem> list, UpdatePricesCallback callback) {
        mProducts = list;
        mCallback = callback;

    }

    public void updatePosition(int position) {
        this.position = position;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        // no op
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        mProducts.get(position).setProductName(charSequence.toString());
        mCallback.onUpdateBill();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // no op
    }
}
