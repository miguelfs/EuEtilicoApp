package com.partiufast.euetilicoapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Miguel on 03/06/2016.
 */
public class ProductItem implements Parcelable {
    private String mProductName;
    private BigDecimal mProductPrice;
    private String mEditableProductPrice;
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

    public void addCustomerToProduct(String customer){
        mProductCustomerList.add(customer);
    }

    public BigDecimal getItemTotalPrice(){
        return mProductPrice.multiply( new BigDecimal(mProductCount));
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

    public String getProductelPriceCurrencyFormat(){
        return NumberFormat.getCurrencyInstance().format(mProductPrice);
    }

    public String getProductPriceRawValueString(){
        if(mProductPrice != null ) {
            if (mProductPrice.equals(new BigDecimal(0)))
                return "";
            return (mProductPrice.multiply(new BigDecimal(100))).toString();
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

    public void setEditableProductPrice(Editable editableProductPrice) {
        mEditableProductPrice = editableProductPrice.toString();
    }

    public String getEditableProductPrice() {
        if (mEditableProductPrice == null)
            return "0";
        return mEditableProductPrice;
    }
}