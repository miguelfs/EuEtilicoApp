package com.partiufast.euetilicoapp.listeners;

import android.widget.CompoundButton;

import com.partiufast.euetilicoapp.models.BillAccount;

/**
 * Created by Miguel on 12/12/2016.
 */
public class OnTipCheckboxChangeListener implements CompoundButton.OnCheckedChangeListener {
    BillAccount mBillAccount;

    public OnTipCheckboxChangeListener(BillAccount billAccount) {
        mBillAccount = billAccount;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mBillAccount.setIs10PercentOn(compoundButton.isChecked());
    }
}
