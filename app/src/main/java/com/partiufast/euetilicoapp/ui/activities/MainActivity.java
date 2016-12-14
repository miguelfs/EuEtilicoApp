package com.partiufast.euetilicoapp.ui.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.callbacks.CustomerDialogCallback;
import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.listeners.OnTipCheckboxChangeListener;
import com.partiufast.euetilicoapp.models.BillAccount;
import com.partiufast.euetilicoapp.models.CustomerItem;
import com.partiufast.euetilicoapp.models.ProductItem;
import com.partiufast.euetilicoapp.ui.fragments.CustomerListFragment;
import com.partiufast.euetilicoapp.ui.fragments.ProductListFragment;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements UpdatePricesCallback {
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
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCheckBox = (CheckBox) getLayoutInflater().inflate(R.layout.custom_checkbox, null);
        mCheckBox.setOnCheckedChangeListener(new OnTipCheckboxChangeListener(mBillAccount));
        toolbar.addView(mCheckBox);
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
        Type productType = new TypeToken<List<ProductItem>>() {
        }.getType();
        Type customerType = new TypeToken<List<CustomerItem>>() {
        }.getType();
        ArrayList<ProductItem> productList = gson.fromJson(mJSONProducts, productType);
        ArrayList<CustomerItem> customerList = gson.fromJson(mJSONCustomers, customerType);
        if (productList == null)
            productList = new ArrayList<>();
        if (customerList == null)
            customerList = new ArrayList<>();
        customerList.add(new CustomerItem("Miguel", new BigDecimal(30), productList));
        mBillAccount.setBillAccount(productList, customerList);
        mProductListFragment = ProductListFragment.newInstance(mBillAccount.getProductItemList(), mBillAccount.getCustomerItemList());
        mCustomerListFragment = CustomerListFragment.newInstance(mBillAccount.getCustomerItemList());

        FloatingActionMenu parentFAB = (FloatingActionMenu) findViewById(R.id.fab_menu);
        parentFAB.setClosedOnTouchOutside(true);
        FloatingActionButton addProductFab = (FloatingActionButton) findViewById(R.id.add_product_fab);
        addProductFab.setOnClickListener(new FabAddProductClickLisntener(mBillAccount.getProductItemList(), mProductListFragment, parentFAB));
        FloatingActionButton addCustomerFab = (FloatingActionButton) findViewById(R.id.add_customer_fab);
        addCustomerFab.setOnClickListener(new FabAddCustomerClickListener(mBillAccount.getCustomerItemList(), mCustomerListFragment, parentFAB));
        /*
        fab.setOnClickListener(new FabAddProductClickLisntener(mBillAccount.getProductItemList(), mProductListFragment));*/
    }

    @Override
    public void onUpdateBill() {
        Log.d("UPDATE BILL:", " yay!");
        /*mBillAccount.updateBill();
        mTotalPriceTextView.setText(mBillAccount.getTotalPrice().setScale(2, RoundingMode.HALF_UP).toString());
        FormatStringAndText.setPriceTextViewSize(mBillAccount.getTotalPrice(), mTotalPriceTextView, mViewPager, mViewPager.getContext());
        mPersonItemAdapter.notifyDataSetChanged();*/
    }


   /* @Override
    public void onClickCustomersSpinner(String[] _items, boolean[] mSelection) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getApplicationContext(), R.style.CustomDialog));
        if (_items == null){
            final EditText input = new EditText(getApplicationContext());
            builder.setTitle("Defina o nome do novo consumidor:");
            input.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    *//*addCustomer(input);
                    *//**//*setItems(mCustomersList);
                    setSelection(mCustomersList);*//**//*
                    mBillAccount.getProductItemList().get(position).setProductCustomerList(getSelectedStrings());*//*
                    onUpdateBill();
                }
            });
        } else {
            builder.setTitle("Selecione os consumidores:")
                    .setMultiChoiceItems(_items, mSelection, this);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    mProductItemList.get(position).setProductPersonList(getSelectedStrings());
                    TabbedActivity.updatePrices();
                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mProductItemList.get(position).setProductPersonList(getSelectedStrings());
                    TabbedActivity.updatePrices();
                }
            });
        }
        builder.show();
    }*/

    private void addCustomer(EditText input) {

    }

    private class FabAddProductClickLisntener implements View.OnClickListener {
        FloatingActionMenu mFam;
        ArrayList<ProductItem> mList;
        ProductListFragment mFrag;

        FabAddProductClickLisntener(ArrayList<ProductItem> list, ProductListFragment frag, FloatingActionMenu fam) {
            mList = list;
            mFrag = frag;
            mFam = fam;
        }

        @Override
        public void onClick(View view) {
            mList.add(new ProductItem("", new BigDecimal(0), 1, Arrays.asList("Buenos Aires", "Córdoba", "La Plata")));
            mFrag.notifyAdapter();
            mFam.close(false);
        }
    }

    private class FabAddCustomerClickListener implements View.OnClickListener {
        FloatingActionMenu mFam;
        ArrayList<CustomerItem> mList;
        CustomerListFragment mFrag;

        FabAddCustomerClickListener(ArrayList<CustomerItem> list, CustomerListFragment frag, FloatingActionMenu fam) {
            mList = list;
            mFrag = frag;
            mFam = fam;
        }

        @Override
        public void onClick(View view) {
            mFam.close(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.CustomDialog));
            setAndDisplayCustomerAddBuilder(MainActivity.this, builder, mList, mFrag);
            builder.show();
        }
    }

    public static void setAndDisplayCustomerAddBuilder(Context context, AlertDialog.Builder builder, final ArrayList<CustomerItem> list, final CustomerListFragment frag) {
        final EditText input = new EditText(context);
        setCustomerBuilder(context,builder, input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!input.getText().toString().equals("")) {
                    list.add(new CustomerItem(input.getText().toString(), new BigDecimal(0)));
                    frag.notifyAdapter();
                }
            }
        });
    }

    public static void setCustomerBuilder(Context context, AlertDialog.Builder builder, EditText input){
        builder.setTitle("Defina o nome do novo consumidor:");
        input.setTextColor(ContextCompat.getColor(context, R.color.white));
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        mJSONProducts = gson.toJson(mBillAccount.getProductItemList());
        mJSONCustomers = gson.toJson(mBillAccount.getCustomerItemList());

        mEditor.putString(KEY_PRODUCT_LIST, mJSONProducts);
        mEditor.putString(KEY_CUSTOMER_LIST, mJSONCustomers);
        mEditor.putBoolean(KEY_10_PERCENT, mCheckBox.isChecked());

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
            mEditor.clear();
            mEditor.apply();
            int productCount = mBillAccount.getProductItemList().size();
            int customerCount = mBillAccount.getCustomerItemList().size();
            mBillAccount.clearLists();
            mProductListFragment.clearAdapter(productCount);
            mCustomerListFragment.clearAdapter(customerCount);
        }

        return true;
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
