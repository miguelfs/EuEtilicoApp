package com.partiufast.euetilicoapp.models;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 04/06/2016.
 */
public class PersonItem {
    private String mPersonName;
    private BigDecimal mPrice;
    private List<ProductItem> mProductItemList = new ArrayList<>();
    private static BigDecimal TENPERCENT = new BigDecimal(1.1);
    boolean mIs10PercentOn;


    public PersonItem(String personName, BigDecimal price) {
        mPersonName = personName;
        mPrice = price;
    }

    public PersonItem(String personName, BigDecimal price, boolean is10PercentOn) {
        mPersonName = personName;
        mPrice = price;
        mIs10PercentOn = is10PercentOn;
    }

    public String getPersonName() {
        return mPersonName;
    }

    public void setPersonName(String personName) {
        mPersonName = personName;
    }

    public BigDecimal getPrice() {
        return mPrice;
    }

    public void setPrice(BigDecimal price) {
        mPrice = price;
    }

    public void setProductItemList(List<ProductItem> productItemList) {
        mProductItemList = productItemList;
    }

    public boolean is10PercentOn() {
        return mIs10PercentOn;
    }

    public void setIs10PercentOn(boolean is10PercentOn) {
        this.mIs10PercentOn = is10PercentOn;
    }

    public void updatePrice(){
        mPrice = new BigDecimal(0);
        BigDecimal tipValue = new BigDecimal(1.0);
        if (mIs10PercentOn) {
            tipValue = TENPERCENT;
        }
        for (int index = 0; index < mProductItemList.size(); index++) {
            mPrice = mPrice.add(mProductItemList.get(index).getProductPrice()
                    .multiply(tipValue)
                    .multiply(new BigDecimal(mProductItemList.get(index).getProductCount()))
                    .divide(new BigDecimal(mProductItemList.get(index).getProductPersonList().size()), 2, BigDecimal.ROUND_HALF_UP));
                    //.setScale(2, RoundingMode.CEILING);
            Log.d("TAG", "oi");
        }
    }

    }
