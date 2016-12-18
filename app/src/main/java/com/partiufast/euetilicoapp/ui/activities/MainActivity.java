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
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.callbacks.CreateBuilderCallback;
import com.partiufast.euetilicoapp.callbacks.OkBuilderCallback;
import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.listeners.OnTipCheckboxChangeListener;
import com.partiufast.euetilicoapp.models.BillAccount;
import com.partiufast.euetilicoapp.models.CustomerItem;
import com.partiufast.euetilicoapp.models.ProductItem;
import com.partiufast.euetilicoapp.ui.fragments.CustomerListFragment;
import com.partiufast.euetilicoapp.ui.fragments.ProductListFragment;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class MainActivity extends AppCompatActivity implements UpdatePricesCallback, CreateBuilderCallback, OkBuilderCallback {
    private static final String PREFS_FILE = "com.partiufast.euetilicoapp.preferences";
    private static final String KEY_PRODUCT_LIST = "KEY_PRODUCT_LIST";
    private static final String KEY_CUSTOMER_LIST = "KEY_CUSTOMER_LIST";
    private static final String KEY_10_PERCENT = "KEY_10_PERCENT";
    private static final String KEY_PRODUCTS_FRAG_TAG = "KEY_PRODUCTS_FRAG_TAG";
    private BillAccount mBillAccount = new BillAccount();
    private ProductListFragment mProductListFragment;
    private CustomerListFragment mCustomerListFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mJSONProducts, mJSONCustomers;
    private CheckBox mCheckBox;
    private AutofitTextView mTotalPriceTextView;
    private String mProductsFragmentTag, mCustomersFragmentTag;
    private LinearLayout mBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCheckBox = (CheckBox) getLayoutInflater().inflate(R.layout.custom_checkbox, null);
        mCheckBox.setOnCheckedChangeListener(new OnTipCheckboxChangeListener(mBillAccount, this));
        toolbar.addView(mCheckBox);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mBottomSheet = (LinearLayout) findViewById(R.id.bottomSheet);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mTotalPriceTextView = (AutofitTextView) findViewById(R.id.totalPriceValueTextView);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mJSONProducts = mSharedPreferences.getString(KEY_PRODUCT_LIST, "");
        mJSONCustomers = mSharedPreferences.getString(KEY_CUSTOMER_LIST, "");
        mProductsFragmentTag = mSharedPreferences.getString(KEY_PRODUCTS_FRAG_TAG, "");

        mBillAccount.setIs10PercentOn(mSharedPreferences.getBoolean(KEY_10_PERCENT, false));
        Type productType = new TypeToken<List<ProductItem>>() {
        }.getType();
        Type customerType = new TypeToken<List<CustomerItem>>() {
        }.getType();
        Gson gson = new Gson();
        ArrayList<ProductItem> productList = gson.fromJson(mJSONProducts, productType);
        ArrayList<CustomerItem> customerList = gson.fromJson(mJSONCustomers, customerType);
        if (productList == null)
            productList = new ArrayList<>();
        if (customerList == null)
            customerList = new ArrayList<>();
        mBillAccount.setBillAccount(productList, customerList);
        if (savedInstanceState != null) {
            Log.d("OLAR", " OLAR");
        }

       /* if (savedInstanceState != null) {
            mProductListFragment = (ProductListFragment) getSupportFragmentManager().findFragmentByTag(mProductsFragmentTag);
        }*/

        /*mProductListFragment = ProductListFragment.newInstance(mBillAccount.getProductItemList(), mBillAccount.getCustomerItemList());
        mCustomerListFragment = CustomerListFragment.newInstance(mBillAccount.getCustomerItemList());*/

        //must set view after adapters are set
        mCheckBox.setChecked(mSharedPreferences.getBoolean(KEY_10_PERCENT, false));




        FloatingActionMenu parentFAB = (FloatingActionMenu) findViewById(R.id.fab_menu);
        parentFAB.setClosedOnTouchOutside(true);
        FloatingActionButton addProductFab = (FloatingActionButton) findViewById(R.id.add_product_fab);
        addProductFab.setOnClickListener(new FabAddProductClickLisntener(mBillAccount.getProductItemList(), parentFAB));
        FloatingActionButton addCustomerFab = (FloatingActionButton) findViewById(R.id.add_customer_fab);
        addCustomerFab.setOnClickListener(new FabAddCustomerClickListener(mBillAccount.getCustomerItemList(), parentFAB));
        /*
        fab.setOnClickListener(new FabAddProductClickLisntener(mBillAccount.getProductItemList(), mProductListFragment));*/
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onUpdateBill() {
        Log.d("UPDATE BILL:", " yay!");
        mBillAccount.updateBill();
        //mTotalPriceTextView.setText(mBillAccount.getTotalPrice().setScale(2, RoundingMode.HALF_UP).toString());
        mTotalPriceTextView.setText(mBillAccount.getTotalPriceCurrencyFormat());
        mCustomerListFragment.notifyAdapter();

        /*
        mTotalPriceTextView.setText(mBillAccount.getTotalPrice().setScale(2, RoundingMode.HALF_UP).toString());
        FormatStringAndText.setPriceTextViewSize(mBillAccount.getTotalPrice(), mTotalPriceTextView, mViewPager, mViewPager.getContext());
        mPersonItemAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void onDeleteCustomer(String name) {
       // mCustomerListFragment.notifyAdapter();
        mBillAccount.onDeleteCustomer(name);
        mProductListFragment.notifyAdapter();
     //   Log.d("DELETE COSTUMER", "");

    }

    private void addCustomer(EditText input) {

    }

    @Override
    public void onCreateBuilder(Context context, AlertDialog.Builder builder, EditText input) {
        setCustomerBuilder( context,  builder, input);
    }



    @Override
    public boolean onOkSelectingCustomers(EditText input, int position) {
        return setLists(input, position);
    }

    private boolean setLists(EditText input, int pos){

        //mBillAccount.getCustomerItemList().contains(input.toString())
        if(!input.getText().toString().equals("")  && !mBillAccount.getAllCustomersNames().contains(input.toString())) {
            mBillAccount.getCustomerItemList().add(new CustomerItem(input.getText().toString(), new BigDecimal(0)));
            mBillAccount.getProductItemList().get(pos).addCustomerToProduct(input.toString());
            mCustomerListFragment.notifyAdapter();
          //  mProductListFragment.setCustomerStringList(mBillAccount.getAllCustomersNames());
            return true;
        }
        return false;
    }

    private class FabAddProductClickLisntener implements View.OnClickListener {
        FloatingActionMenu mFam;
        ArrayList<ProductItem> mList;

        FabAddProductClickLisntener(ArrayList<ProductItem> list,  FloatingActionMenu fam) {
            mList = list;
            mFam = fam;
        }

        @Override
        public void onClick(View view) {
            mList.add(new ProductItem("", new BigDecimal(0), 1, new ArrayList<String>()));
            mProductListFragment.notifyAdapter();
            mFam.close(false);
        }
    }

    private class FabAddCustomerClickListener implements View.OnClickListener {
        FloatingActionMenu mFam;
        ArrayList<CustomerItem> mList;

        FabAddCustomerClickListener(ArrayList<CustomerItem> list, FloatingActionMenu fam) {
            mList = list;
            mFam = fam;
        }

        @Override
        public void onClick(View view) {
            mFam.close(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.CustomDialog));
            setAndDisplayCustomerAddBuilder(MainActivity.this, builder, mList, mCustomerListFragment);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.show();
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
        /*final GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();*/
        Gson gson = new Gson();

        mJSONProducts = gson.toJson(mBillAccount.getProductItemList());
        mJSONCustomers = gson.toJson(mBillAccount.getCustomerItemList());

        mEditor.putString(KEY_PRODUCT_LIST, mJSONProducts);
        mEditor.putString(KEY_CUSTOMER_LIST, mJSONCustomers);
        mEditor.putBoolean(KEY_10_PERCENT, mCheckBox.isChecked());
        mEditor.putString(KEY_PRODUCTS_FRAG_TAG, mProductsFragmentTag);

        mEditor.apply();
        super.onPause();
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
            mTotalPriceTextView.setText("");
            mProductListFragment.clearAdapter(productCount);
            mCustomerListFragment.clearAdapter(customerCount);
            mBillAccount.updateBill();
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
               return ProductListFragment.newInstance(mBillAccount.getProductItemList(), mBillAccount.getCustomerItemList());
            if (position == 1)
              return CustomerListFragment.newInstance(mBillAccount.getCustomerItemList());
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            if (position == 0) {
                mProductListFragment = (ProductListFragment) createdFragment;
                if (mProductListFragment.getProductItemList() != null){
                    mProductListFragment.setParameters(mBillAccount.getProductItemList(), mBillAccount.getCustomerItemList());
                }
            }
            if (position == 1) {
                mCustomerListFragment = (CustomerListFragment) createdFragment;
                if (mCustomerListFragment.getCustomerItemList() != null) {
                    mCustomerListFragment.setParameters(mBillAccount.getCustomerItemList());
                }
            }
            return createdFragment;
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
