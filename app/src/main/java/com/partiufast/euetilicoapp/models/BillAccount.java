package com.partiufast.euetilicoapp.models;

import android.location.Location;

import java.math.BigDecimal;
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
    private String mPlaceName;
    private Location mLocation;
    private GregorianCalendar mGregorianCalendar;
    private long mStartTime;
    private long mEndTime;
    private static BigDecimal TENPERCENT = new BigDecimal(1.1);

    public BillAccount(ArrayList<ProductItem> productItemList, ArrayList<CustomerItem> customerItemList,
                       boolean is10PercentOn, BigDecimal totalPrice, String placeName, Location location,
                       GregorianCalendar gregorianCalendar, long startTime, long endTime) {
        mProductItemList = productItemList;
        mCustomerItemList = customerItemList;
        this.is10PercentOn = is10PercentOn;
        mTotalPrice = totalPrice;
        mPlaceName = placeName;
        mLocation = location;
        mGregorianCalendar = gregorianCalendar;
        mStartTime = startTime;
        mEndTime = endTime;
    }

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

    public void setTotalPrice(BigDecimal totalPrice) {
        mTotalPrice = totalPrice;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public void setPlaceName(String placeName) {
        mPlaceName = placeName;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public GregorianCalendar getGregorianCalendar() {
        return mGregorianCalendar;
    }

    public void setGregorianCalendar(GregorianCalendar gregorianCalendar) {
        mGregorianCalendar = gregorianCalendar;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(long endTime) {
        mEndTime = endTime;
    }

    public void updateBill() {
        updateTotalPrice();
        setPersonItems();
        updatePersonListPrices();
    }

    public void updateTotalPrice() {
        mTotalPrice = BigDecimal.ZERO;
        BigDecimal tipValue = new BigDecimal(1.0);
        if (is10PercentOn)
            tipValue = TENPERCENT;
        for (int index = 0; index < mProductItemList.size(); index++) {
            mTotalPrice = mTotalPrice.add(mProductItemList.get(index).getProductPrice()
                    .multiply(tipValue)
                    .multiply(new BigDecimal(mProductItemList.get(index).getProductCount())));
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
            mCustomerItemList.get(index).updatePrice();
        }
    }


    public void clearLists() {
        mProductItemList.clear();
        mCustomerItemList.clear();
    }
}