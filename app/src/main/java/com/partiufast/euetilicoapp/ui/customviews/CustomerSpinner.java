package com.partiufast.euetilicoapp.ui.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.Toast;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.callbacks.CreateBuilderCallback;
import com.partiufast.euetilicoapp.callbacks.OkBuilderCallback;
import com.partiufast.euetilicoapp.callbacks.UpdatePricesCallback;
import com.partiufast.euetilicoapp.models.CustomerItem;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 14/12/2016.
 */
public class CustomerSpinner extends MultiSelectionSpinner {
    private  List<ProductItem> mProductItems;
    private List<CustomerItem> mCustomerItems;
    private  List<String> mCustomerStringList;
    private  CreateBuilderCallback mCreateBuilderCallback;
    private  OkBuilderCallback mOkBuilderCallback;
    private int position;
    private UpdatePricesCallback mUpdateCallback;

    public CustomerSpinner(Context context){
        super(context);
    }
    public CustomerSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*public void setParameters( List<String> customerStringList, List<ProductItem> productItems, CreateBuilderCallback createBuilderCallback, OkBuilderCallback okBuilderCallback) {
        mCustomerStringList = customerStringList;
        mProductItems = productItems;
        mCreateBuilderCallback = createBuilderCallback;
        mOkBuilderCallback = okBuilderCallback;
    }*/

    public void setParameters(List<CustomerItem> customers, List<ProductItem> productItems, CreateBuilderCallback createBuilderCallback, OkBuilderCallback okBuilderCallback, UpdatePricesCallback update) {
        mCustomerItems = customers;
        mProductItems = productItems;
        mCreateBuilderCallback = createBuilderCallback;
        mOkBuilderCallback = okBuilderCallback;
        mUpdateCallback = update;
    }

    private  List<String>  getStringAllCustomers(){
        List<String> names = new ArrayList<>();
        for (int i = 0; i < mCustomerItems.size(); i++)
            names.add(mCustomerItems.get(i).getCustomerName());
        return names;
    }

    public void updatePosition(int position) {
        this.position = position;
    }

    @Override
    public boolean performClick() {

        setItems(getStringAllCustomers());
        try {
            setSelection(mProductItems.get(position).getProductCustomerList());
        } catch (IndexOutOfBoundsException e){
            Log.e("ERROR INDEX:", "SET SELECTION, position = " + position);
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.CustomDialog));
        if (mItems == null) {
            final EditText input = new EditText(getContext());
            mCreateBuilderCallback.onCreateBuilder(getContext(), builder, input);
            builder.setNegativeButton(R.string.cancel, null);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   if (mOkBuilderCallback.onOkSelectingCustomers(input, position)) {
                       setItems(getStringAllCustomers());
                       setSelection(getStringAllCustomers());
                       mProductItems.get(position).setProductCustomerList(getSelectedStrings());
                       mUpdateCallback.onUpdateBill();
                   }
                }
            });
        } else {
            builder.setTitle("Selecione os consumidores:")
                    .setMultiChoiceItems(mItems, mSelection, this);
            //TODO fix method publicSelectedItemString in MultiSelectionSpinner
            //não está eficiente
         final String display = publicBuildSelectedItemString();

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    mProductItems.get(position).setProductCustomerList(getSelectedStrings());
                    mUpdateCallback.onUpdateBill();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   updateDisplay(display);
                }
            });
           /* builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                   *//* mProductItemList.get(position).setProductPersonList(getSelectedStrings());
                    TabbedActivity.updatePrices();*//*
                }
            });*/
        }
        builder.show();
        return true;
    }

}
