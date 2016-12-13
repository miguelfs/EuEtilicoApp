package com.partiufast.euetilicoapp.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.listeners.OnTipCheckboxChangeListener;
import com.partiufast.euetilicoapp.models.BillAccount;
import com.partiufast.euetilicoapp.models.CustomerItem;
import com.partiufast.euetilicoapp.models.ProductItem;
import com.partiufast.euetilicoapp.ui.fragments.CustomerListFragment;
import com.partiufast.euetilicoapp.ui.fragments.ProductListFragment;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_FILE = "com.partiufast.euetilicoapp.preferences";
    private static final String KEY_PRODUCT_LIST = "KEY_PRODUCT_LIST";
    private static final String KEY_CUSTOMER_LIST = "KEY_CUSTOMER_LIST";
    private static final String KEY_10_PERCENT = "KEY_10_PERCENT";
    private BillAccount mBillAccount = new BillAccount();
    private ProductListFragment mProductListFragment;
    private CustomerListFragment mCustomerListFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mJSONProducts, mJSONCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CheckBox checkBox = (CheckBox) getLayoutInflater().inflate(R.layout.custom_checkbox, null);
        checkBox.setOnCheckedChangeListener(new OnTipCheckboxChangeListener(mBillAccount));
        toolbar.addView(checkBox);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        Gson gson = new Gson();
        mJSONProducts = mSharedPreferences.getString(KEY_PRODUCT_LIST, "");
        mJSONCustomers = mSharedPreferences.getString(KEY_CUSTOMER_LIST, "");
        mBillAccount.setIs10PercentOn(mSharedPreferences.getBoolean(KEY_10_PERCENT, false));
        Type productType = new TypeToken<List<ProductItem>>(){}.getType();
        Type customerType = new TypeToken<List<CustomerItem>>(){}.getType();
        ArrayList<ProductItem> productList = gson.fromJson(mJSONProducts, productType);
        ArrayList<CustomerItem> customerList = gson.fromJson(mJSONCustomers, customerType);
        productList.add(new ProductItem("produto", new BigDecimal(20), 4, Arrays.asList("Buenos Aires", "Córdoba", "La Plata")));
        customerList.add(new CustomerItem("Miguel", new BigDecimal(30)));
        mBillAccount.setBillAccount(productList, customerList);
        mProductListFragment = ProductListFragment.newInstance(mBillAccount.getProductItemList());
        mCustomerListFragment = CustomerListFragment.newInstance(mBillAccount.getCustomerItemList());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new FabClickLisntener(mBillAccount.getProductItemList(), mProductListFragment));
    }

    private class FabClickLisntener implements View.OnClickListener{
        ArrayList<ProductItem> mList;
        ProductListFragment mFrag;
        FabClickLisntener(ArrayList<ProductItem> list, ProductListFragment frag) { mList = list; mFrag = frag;}
        @Override
        public void onClick(View view) {
            mList.add(new ProductItem("produto", new BigDecimal(20), 4, Arrays.asList("Buenos Aires", "Córdoba", "La Plata")));
            mFrag.notifyAdapter();}
    }

    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        mJSONProducts = gson.toJson(mBillAccount.getProductItemList());
        mJSONCustomers = gson.toJson(mBillAccount.getCustomerItemList());

        mEditor.putString(KEY_PRODUCT_LIST, mJSONProducts);
        mEditor.putString(KEY_CUSTOMER_LIST, mJSONCustomers);

        mEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_clear) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return mProductListFragment;
            if (position == 1)
                return mCustomerListFragment;
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Comanda";
                case 1:
                    return "Conta";
            }
            return null;
        }
    }
}
