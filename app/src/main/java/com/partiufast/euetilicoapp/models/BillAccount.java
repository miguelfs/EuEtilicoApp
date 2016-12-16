package com.partiufast.euetilicoapp.models;

import android.location.Location;
import android.util.Log;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Miguel on 19/06/2016.
 */
public class BillAccount {
    private ArrayList<ProductItem> mProductItemList = new ArrayList<ProductItem>();
    private ArrayList<CustomerItem> mCustomerItemList = new ArrayList<CustomerItem>();
    boolean is10PercentOn;
    private BigDecimal mTotalPrice;
    private final static BigDecimal TENPERCENT = new BigDecimal(1.1);



    public BillAccount() {
        mCustomerItemList = new ArrayList<CustomerItem>();
        mProductItemList = new ArrayList<ProductItem>();
    }

    public BillAccount(ArrayList<ProductItem> productItemList, ArrayList<CustomerItem> customerItemList) {
        mProductItemList.clear();
        mProductItemList.addAll(productItemList);
        mCustomerItemList.clear();
        mCustomerItemList.addAll(customerItemList);
    }

    public void setBillAccount(ArrayList<ProductItem> productItemList, ArrayList<CustomerItem> customerItemList) {
        mProductItemList.clear();
        mProductItemList.addAll(productItemList);
        mCustomerItemList.clear();
        mCustomerItemList.addAll(customerItemList);
    }


    public ArrayList<ProductItem> getProductItemList() {
        return mProductItemList;
    }

    public void setProductItemList(ArrayList<ProductItem> productItemList) {
        mProductItemList = productItemList;
    }

    public ArrayList<CustomerItem> getCustomerItemList() {
        return mCustomerItemList;
    }

    public void setCustomerItemList(ArrayList<CustomerItem> customerItemList) {
        mCustomerItemList = customerItemList;
    }

    public boolean is10PercentOn() {
        return is10PercentOn;
    }

    public void setIs10PercentOn(boolean is10PercentOn) {
        this.is10PercentOn = is10PercentOn;
        for (int personIterator = 0; personIterator < mCustomerItemList.size(); personIterator++)
            mCustomerItemList.get(personIterator).setIs10PercentOn(is10PercentOn);
    }

    public BigDecimal getTotalPrice() {
        return mTotalPrice;
    }

    public String getTotalPriceCurrencyFormat(){
        return NumberFormat.getCurrencyInstance().format(mTotalPrice);
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        mTotalPrice = totalPrice;
    }


    public void updateBill() {
        updateTotalPrice();
        setPersonItems();
        updatePersonListPrices();
    }

    private BigDecimal tip(){
        BigDecimal tipValue = new BigDecimal(1.0);
        if (is10PercentOn)
            tipValue = TENPERCENT;
        return tipValue;
    }

    public void updateTotalPrice() {
        mTotalPrice = BigDecimal.ZERO;
        for (int index = 0; index < mProductItemList.size(); index++) {
            mTotalPrice = mTotalPrice.add(mProductItemList.get(index).getItemTotalPrice().multiply(tip()));
        }
    }

    public void setPersonItems() {
        for (int personIterator = 0; personIterator < mCustomerItemList.size(); personIterator++) {
            List<ProductItem> productBufferList = new ArrayList<>();
            for (int productIterator = 0; productIterator < mProductItemList.size(); productIterator++) {
                if (mProductItemList.get(productIterator).checkIfListHasName(mCustomerItemList.get(personIterator).getCustomerName())) {
                    productBufferList.add(mProductItemList.get(productIterator));
                }
            }
            mCustomerItemList.get(personIterator).setProductItemList(productBufferList);
        }
    }

    public void updatePersonListPrices() {
        for (int index = 0; index < mCustomerItemList.size(); index++) {
            mCustomerItemList.get(index).updatePrice(tip());
        }
    }

    public   List<String>  getAllCustomersNames(){
        List<String> names = new ArrayList<>();
        for (int i = 0; i < mCustomerItemList.size(); i++)
            names.add(mCustomerItemList.get(i).getCustomerName());
        return names;
    }


    public void clearLists() {
        mProductItemList.clear();
        mCustomerItemList.clear();
        getAllCustomersNames().clear();
    }
}