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
import android.widget.NumberPicker;

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
    private static final String KEY_IS_TIP_ON = "KEY_IS_TIP_ON";
    private static final String KEY_PRODUCTS_FRAG_TAG = "KEY_PRODUCTS_FRAG_TAG";
    private static final String KEY_TIP_VALUE = "KEY_TIP_VALUE";
    private static final String KEY_BILL_CODE = "KEY_BILL_CODE";
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
        mBillAccount.setTipValue(mSharedPreferences.getInt(KEY_TIP_VALUE, 10));
        mCheckBox.setText(mBillAccount.getTipValueAsInt()+"%");
        mBillAccount.setIsTipOn(mSharedPreferences.getBoolean(KEY_IS_TIP_ON, false));
        mBillAccount.setBillCode(mSharedPreferences.getString(KEY_BILL_CODE, ""));
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
        //must set view after adapters are set
        mCheckBox.setChecked(mSharedPreferences.getBoolean(KEY_IS_TIP_ON, false));




        FloatingActionMenu parentFAB = (FloatingActionMenu) findViewById(R.id.fab_menu);
        parentFAB.setClosedOnTouchOutside(true);
        FloatingActionButton addProductFab = (FloatingActionButton) findViewById(R.id.add_product_fab);
        addProductFab.setOnClickListener(new FabAddProductClickLisntener(mBillAccount.getProductItemList(), parentFAB, mViewPager));
        FloatingActionButton addCustomerFab = (FloatingActionButton) findViewById(R.id.add_customer_fab);
        addCustomerFab.setOnClickListener(new FabAddCustomerClickListener(mBillAccount.getCustomerItemList(), parentFAB));
        /*getBillReference().child("active_users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mBillAccount.setActiveUsers( Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

  /*  private DatabaseReference getBillReference(){
        return mDatabase.getReference().child("bills").child(mBillAccount.getBillCode());
    }

    private DatabaseReference getBillActiveUsersReference(){
        return mDatabase.getReference().child("bills").child(mBillAccount.getBillCode()).child("active_users");
    }*/

 /*   private void addBillToFirebaseDB() {
        getBillActiveUsersReference().setValue(1);
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onUpdateBill() {
        Log.d("UPDATE BILL:", " yay!");
        mBillAccount.updateBill();
        mTotalPriceTextView.setText(mBillAccount.getTotalPriceCurrencyFormat());
        if (mCustomerListFragment!=null)
            mCustomerListFragment.notifyAdapter();
    }

    @Override
    public void onDeleteCustomer(String name) {
        mBillAccount.onDeleteCustomer(name);
        mProductListFragment.notifyAdapter();
    }


    @Override
    public void onCreateBuilder(Context context, AlertDialog.Builder builder, EditText input) {
        setCustomerBuilder( context,  builder, input);
    }

    @Override
    public void onClickProductCard(int position) {

    }


    @Override
    public boolean onOkSelectingCustomers(EditText input, int position) {
        return setLists(input, position);
    }

    private boolean setLists(EditText input, int pos){

        if(!input.getText().toString().equals("")  && !mBillAccount.getAllCustomersNames().contains(input.toString())) {
            mBillAccount.getCustomerItemList().add(new CustomerItem(input.getText().toString(), new BigDecimal(0)));
            mBillAccount.getProductItemList().get(pos).addCustomerToProduct(input.toString());
            mCustomerListFragment.notifyAdapter();
            return true;
        }
        return false;
    }

    private class FabAddProductClickLisntener implements View.OnClickListener {
        private final ViewPager mVPager;
        FloatingActionMenu mFam;
        ArrayList<ProductItem> mList;

        FabAddProductClickLisntener(ArrayList<ProductItem> list, FloatingActionMenu fam, ViewPager viewPager) {
            mList = list;
            mFam = fam;
            mVPager = viewPager;
        }

        @Override
        public void onClick(View view) {
            mList.add(new ProductItem("", new BigDecimal(0), 1, new ArrayList<String>()));
            mProductListFragment.notifyAdapter();
            mFam.close(false);
            try {
                if (mVPager.getCurrentItem() != 0) {
                    mVPager.setCurrentItem(0, true);
                }
            } catch (Exception e){
                Log.e("ERROR: ", e.getLocalizedMessage());
            }
            mProductListFragment.jumpToLastWhenCallButton();
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
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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
        builder.setNegativeButton(R.string.cancel, null);
        input.setTextColor(ContextCompat.getColor(context, R.color.white));
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
    }

    @Override
    protected void onPause() {
        Gson gson = new Gson();

        mJSONProducts = gson.toJson(mBillAccount.getProductItemList());
        mJSONCustomers = gson.toJson(mBillAccount.getCustomerItemList());

        mEditor.putString(KEY_PRODUCT_LIST, mJSONProducts);
        mEditor.putString(KEY_CUSTOMER_LIST, mJSONCustomers);
        mEditor.putInt(KEY_TIP_VALUE, mBillAccount.getTipValueAsInt());
        mEditor.putBoolean(KEY_IS_TIP_ON, mCheckBox.isChecked());
        mEditor.putString(KEY_BILL_CODE, mBillAccount.getBillCode());
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
            if (mBillAccount.getActiveUsers() == 1)
      ///          getBillReference().removeValue();
            mBillAccount.generateBillCode();
            mBillAccount.setActiveUsers(1);
     //       addBillToFirebaseDB();

            mTotalPriceTextView.setText("");
            mProductListFragment.clearAdapter(productCount);
            mCustomerListFragment.clearAdapter(customerCount);
            mBillAccount.updateBill();
        }
        if (id == R.id.action_tip){
            final NumberPicker picker = new NumberPicker(this);
            picker.setMaxValue(25);
            picker.setMinValue(1);
            String [] values = new String[25];
            for (int i = 0; i < 25; i++)
                values[i] = (i+1) + "%";
            picker.setDisplayedValues( values );
            picker.setWrapSelectorWheel(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.CustomDialog));
           builder.setTitle("Defina o valor da taxa de conveniência:");
            picker.setValue(mBillAccount.getTipValueAsInt());
            builder.setView(picker);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mBillAccount.setTipValue(picker.getValue());
                    onUpdateBill();
                    mCheckBox.setText(picker.getValue()+"%");
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    /*    if (id == R.id.action_share){
            final EditText editText = new EditText(this);

            editText.setText(mBillAccount.getBillCode());
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.CustomDialog));
            builder.setTitle("Compartilhar Comanda");
            builder.setMessage("Insira o código fornecido por seus amigos, ou compartilhe com eles o código abaixo:");
            builder.setView(editText);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     if (!editText.getText().toString().equals(mBillAccount.getBillCode())){
                         mDatabase.getReference().child("bills").addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(DataSnapshot dataSnapshot) {
                                 mBillAccount.setBillCode(editText.getText().toString());
                                if (dataSnapshot.hasChild(editText.getText().toString())){
                                    int active_users = Integer.parseInt(dataSnapshot.child(editText.getText().toString()).child("active_users").getValue().toString()) + 1;
                                 mDatabase.getReference().child("bills").child(editText.getText().toString()).child("active_users")
                                         .setValue(active_users);
                                }
                             }

                             @Override
                             public void onCancelled(DatabaseError databaseError) {

                             }
                         });
                    }
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }*/

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
