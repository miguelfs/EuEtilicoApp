package com.partiufast.euetilicoapp.listeners;

import android.widget.CompoundButton;

import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.models.BillAccount;

/**
 * Created by Miguel on 12/12/2016.
 */
public class OnTipCheckboxChangeListener implements CompoundButton.OnCheckedChangeListener {
    private final UpdatePricesCallback mUpdateCallback;
    BillAccount mBillAccount;

    public OnTipCheckboxChangeListener(BillAccount billAccount, UpdatePricesCallback updateCallback) {
        mBillAccount = billAccount;
        mUpdateCallback = updateCallback;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mBillAccount.setIsTipOn(compoundButton.isChecked());
        mUpdateCallback.onUpdateBill();
    }
}
