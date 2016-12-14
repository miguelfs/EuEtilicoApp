package com.partiufast.euetilicoapp.listeners;

import android.text.Editable;
import android.text.TextWatcher;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Miguel on 14/12/2016.
 */
public class ProductPriceListener implements TextWatcher {
    private List<ProductItem> mProducts;
    private UpdatePricesCallback mCallback;
    private CurrencyEditText mCurrencyText;
    private int position;
    boolean mToggle = false;

    public ProductPriceListener(List<ProductItem> list, UpdatePricesCallback callback) {
        mProducts = list;
        mCallback = callback;

    }

    public void updatePosition(int position) {
        this.position = position;
    }

    public void updateCurrencyText(CurrencyEditText editText) {
        mCurrencyText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        // no op
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
       /* if (charSequence.toString().equals("") || charSequence.toString().equals(".")) {
            mProducts.get(position).setProductPrice(new BigDecimal(0));
        }
        else {
            mProductItemList.get(position).setProductPrice(new BigDecimal(charSequence.toString()));
        }
        mPricesCallback.onUpdateBill();*/
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mToggle) {
            mProducts.get(position).setProductPrice(new BigDecimal((double) mCurrencyText.getRawValue() / 100));
            mCallback.onUpdateBill();
        }
        mToggle = !mToggle;

    }
}
