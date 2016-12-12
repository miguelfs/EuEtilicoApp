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
    private List<ProductItem> mProductItemList;
    private List<PersonItem> mPersonItemList;
    boolean is10PercentOn;
    private BigDecimal mTotalPrice;
    private String mPlaceName;
    private Location mLocation;
    private GregorianCalendar mGregorianCalendar;
    private long mStartTime;
    private long mEndTime;
    private static BigDecimal TENPERCENT = new BigDecimal(1.1);

    public BillAccount(List<ProductItem> productItemList, List<PersonItem> personItemList,
                       boolean is10PercentOn, BigDecimal totalPrice, String placeName, Location location,
                       GregorianCalendar gregorianCalendar, long startTime, long endTime) {
        mProductItemList = productItemList;
        mPersonItemList = personItemList;
        this.is10PercentOn = is10PercentOn;
        mTotalPrice = totalPrice;
        mPlaceName = placeName;
        mLocation = location;
        mGregorianCalendar = gregorianCalendar;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    public BillAccount(){
        mPersonItemList = new ArrayList<PersonItem>();
        mProductItemList = new ArrayList<ProductItem>();
    }


    public List<ProductItem> getProductItemList() {
        return mProductItemList;
    }

    public void setProductItemList(List<ProductItem> productItemList) {
        mProductItemList = productItemList;

    }

    public List<PersonItem> getPersonItemList() {
        return mPersonItemList;
    }

    public void setPersonItemList(List<PersonItem> personItemList) {
        mPersonItemList = personItemList;
    }

    public boolean is10PercentOn() {
        return is10PercentOn;
    }

    public void setIs10PercentOn(boolean is10PercentOn) {
        this.is10PercentOn = is10PercentOn;
        for (int personIterator = 0; personIterator < mPersonItemList.size(); personIterator ++ )
            mPersonItemList.get(personIterator).setIs10PercentOn(is10PercentOn);
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

    public void updateBill(){
        updateTotalPrice();
        setPersonItems();
        updatePersonListPrices();
    }

    public void updateTotalPrice() {
        mTotalPrice = new BigDecimal(0);
        BigDecimal tipValue = new BigDecimal(1.0);
        if (is10PercentOn)
            tipValue = TENPERCENT;
        for (int index = 0; index < mProductItemList.size(); index++) {
            mTotalPrice = mTotalPrice.add(mProductItemList.get(index).getProductPrice()
                    .multiply(tipValue)
                    .multiply(new BigDecimal(mProductItemList.get(index).getProductCount())));
        }
    }

    public void setPersonItems(){
        for (int personIterator = 0; personIterator < mPersonItemList.size(); personIterator ++ ){
            List<ProductItem> productBufferList = new ArrayList<>();
            for (int productIterator = 0; productIterator < mProductItemList.size(); productIterator ++){
                if (mProductItemList.get(productIterator).checkIfListHasName(mPersonItemList.get(personIterator).getPersonName())){
                   productBufferList.add(mProductItemList.get(productIterator));
                }
            }
            mPersonItemList.get(personIterator).setProductItemList(productBufferList);
        }
    }

    public void updatePersonListPrices(){
        for (int index = 0; index < mPersonItemList.size(); index++){
            mPersonItemList.get(index).updatePrice();
        }
    }


    }