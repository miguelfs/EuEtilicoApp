package com.partiufast.euetilicoapp.ui.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.EditText;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.util.List;

/**
 * Created by Miguel on 12/12/2016.
 */

public class CustomerSpinner extends MultiSelectionSpinner {
    private int position;
    private List<String> mCustomersList;
    private List<ProductItem> mProductItemList;
    //      String[] _items;
    //      boolean[] mSelection;

    public CustomerSpinner(Context context){
        super(context);
    }
    public CustomerSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void updatePosition(int position) {
        this.position = position;
    }

    @Override
    public boolean performClick() {
        setItems(mCustomersList);
        setSelection(mProductItemList.get(position).getProductCustomerList());
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.CustomDialog));
        if (_items == null){
            Log.d("items == ", "null");
            /*final EditText input = new EditText(getContext());
            mPersonListFragment.setCustomerBuilder(getContext(),builder, input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TabbedActivity.PersonListFragment.setLists(input);
                    setItems(mCustomersList);
                    setSelection(mCustomersList);
                    mProductItemList.get(position).setProductPersonList(getSelectedStrings());
                    TabbedActivity.updatePrices();*/


        } else {
            builder.setTitle("Selecione os consumidores:")
                    .setMultiChoiceItems(_items, mSelection, this);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Log.d("positive button ", "ok");
                }
            });

        }
        builder.show();
        return true;
    }
}
