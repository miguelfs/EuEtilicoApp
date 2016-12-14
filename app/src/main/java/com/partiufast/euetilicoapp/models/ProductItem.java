package com.partiufast.euetilicoapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by Miguel on 03/06/2016.
 */
public class ProductItem implements Parcelable {
    private String mProductName;
    private BigDecimal mProductPrice;
    private int mProductCount;
    private List<String> mProductCustomerList;


    public ProductItem(String productName, BigDecimal productPrice, int productCount, List<String> productCustomerList) {

            mProductName = productName;
        mProductPrice = productPrice;
        mProductCount = productCount;
        mProductCustomerList = productCustomerList;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public BigDecimal getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        mProductPrice = productPrice;
    }

    public int getProductCount() {
        return mProductCount;
    }

    public void setProductCount(int productCount) {
        mProductCount = productCount;
    }

    public List<String> getProductCustomerList() {
        return mProductCustomerList;
    }

    public void setProductCustomerList(List<String> productCustomerList) {
        mProductCustomerList = productCustomerList;
    }

    public BigDecimal getParcelPrice(BigDecimal tipValue){
        return  mProductPrice
                .multiply(tipValue)
                .multiply(new BigDecimal(mProductCount))
                .divide(new BigDecimal(mProductCustomerList.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    public String getParcelPriceCurrencyFormat(BigDecimal tipValue){
        return NumberFormat.getCurrencyInstance().format(mProductPrice
                .multiply(tipValue)
                .multiply(new BigDecimal(mProductCount))
                .divide(new BigDecimal(mProductCustomerList.size()), 2, BigDecimal.ROUND_HALF_UP));
    }

    public boolean checkIfListHasName(String name) {
        for (int i = 0; i < mProductCustomerList.size(); i++)
            if (mProductCustomerList.get(i).equals(name))
                return true;
        return false;
    }

    public String getProductPriceString(){
        if(mProductPrice != null ) {
            if (mProductPrice.equals(new BigDecimal(0)))
                return "";
            return mProductPrice.toString();
        }
        return "";
        }

    public String getProductPriceCurrencyFormat() {
        return  NumberFormat.getCurrencyInstance().format(mProductPrice);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mProductName);
        dest.writeSerializable(this.mProductPrice);
        dest.writeInt(this.mProductCount);
        dest.writeStringList(this.mProductCustomerList);
    }

    protected ProductItem(Parcel in) {
        this.mProductName = in.readString();
        this.mProductPrice = (BigDecimal) in.readSerializable();
        this.mProductCount = in.readInt();
        this.mProductCustomerList = in.createStringArrayList();
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel source) {
            return new ProductItem(source);
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };
}