package com.partiufast.euetilicoapp.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.util.List;

/**
 * Created by Miguel on 14/12/2016.
 */
public class ProductCountListener implements AdapterView.OnItemSelectedListener {
    private final UpdatePricesCallback mCallback;
    private final List<ProductItem> mList;
    private int productPosition;


    public ProductCountListener(List<ProductItem> list, UpdatePricesCallback callback) {
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
