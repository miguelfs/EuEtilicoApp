package com.partiufast.euetilicoapp.models;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Miguel on 03/06/2016.
 */
public class ProductItem {
    private String mProductName;
    private BigDecimal mProductPrice;
    private int mProductCount;
    private List<String> mProductPersonList;


    public ProductItem(String productName, BigDecimal productPrice, int productCount, List<String> productPersonList) {

            mProductName = productName;
        mProductPrice = productPrice;
        mProductCount = productCount;
        mProductPersonList = productPersonList;
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

    public List<String> getProductPersonList() {
        return mProductPersonList;
    }

    public void setProductPersonList(List<String> productPersonList) {
        mProductPersonList = productPersonList;
    }

    public boolean checkIfListHasName(String name) {
        for (int i = 0; i < mProductPersonList.size(); i++)
            if (mProductPersonList.get(i).equals(name))
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
    }