package com.partiufast.euetilicoapp.models;

import  java.text.NumberFormat;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by Miguel on 04/06/2016.
 */
public class CustomerItem implements Parcelable {
    private String mCustomerName;
    private BigDecimal mPrice;
    private List<ProductItem> mProductItemList = new ArrayList<>();



    private static BigDecimal TENPERCENT = new BigDecimal(1.1);
    boolean mIs10PercentOn;


    public CustomerItem(String customerName, BigDecimal price) {
        mCustomerName = customerName;
        mPrice = price;
    }

    public CustomerItem(String customerName, BigDecimal price, List<ProductItem> productItems) {
        mCustomerName = customerName;
        mPrice = price;
        mProductItemList = productItems;
    }

    public CustomerItem(String customerName, BigDecimal price, boolean is10PercentOn) {
        mCustomerName = customerName;
        mPrice = price;
        mIs10PercentOn = is10PercentOn;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public BigDecimal getPrice() {
        return mPrice;
    }

    public List<ProductItem> getProductItemList() {
        return mProductItemList;
    }

    public String getPriceCurrencyFormat() {
        return  NumberFormat.getCurrencyInstance().format(mPrice);
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
                    .divide(new BigDecimal(mProductItemList.get(index).getProductCustomerList().size()), 2, BigDecimal.ROUND_HALF_UP));
                    //.setScale(2, RoundingMode.CEILING);
        }
    }

    public void updatePrice(BigDecimal tipValue){
        mPrice = new BigDecimal(0);
        for (int index = 0; index < mProductItemList.size(); index++) {
            mPrice = mPrice.add(mProductItemList.get(index).getProductPrice()
                    .multiply(tipValue)
                    .multiply(new BigDecimal(mProductItemList.get(index).getProductCount()))
                    .divide(new BigDecimal(mProductItemList.get(index).getProductCustomerList().size()), 2, BigDecimal.ROUND_HALF_UP));
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCustomerName);
        dest.writeSerializable(this.mPrice);
        dest.writeTypedList(this.mProductItemList);
        dest.writeByte(this.mIs10PercentOn ? (byte) 1 : (byte) 0);
    }

    protected CustomerItem(Parcel in) {
        this.mCustomerName = in.readString();
        this.mPrice = (BigDecimal) in.readSerializable();
        this.mProductItemList = in.createTypedArrayList(ProductItem.CREATOR);
        this.mIs10PercentOn = in.readByte() != 0;
    }

    public static final Creator<CustomerItem> CREATOR = new Creator<CustomerItem>() {
        @Override
        public CustomerItem createFromParcel(Parcel source) {
            return new CustomerItem(source);
        }

        @Override
        public CustomerItem[] newArray(int size) {
            return new CustomerItem[size];
        }
    };
}
