package com.partiufast.euetilicoapp.ui.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Miguel on 12/12/2016.
 */

public class MultiSelectionSpinner extends Spinner implements
        DialogInterface.OnMultiChoiceClickListener
{
    protected String[] mItems = null;
    protected boolean[] mSelection = null;
    private String mTitle = "";


    ArrayAdapter<String> simple_adapter;

    public MultiSelectionSpinner(Context context)
    {
        super(context);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {
            mSelection[which] = isChecked;

            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }
    public void setTitle(String title) {
        mTitle = title;
    }
    @Override
    public boolean performClick() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(mTitle);
        builder.setMultiChoiceItems(mItems, mSelection, this);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                Log.d("TAG", "debug Log");
            }
        });


        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(String[] items) {
        mItems = items;
        mSelection = new boolean[mItems.length];
        simple_adapter.clear();
        simple_adapter.add(mItems[0]);
        Arrays.fill(mSelection, false);
    }

    public void setItems(List<String> items) {
        if (items.size() == 0) {
            mItems = null;
            mSelection = null;
        }
        if (items.size() > 0) {
            mItems = items.toArray(new String[items.size()]);
            mSelection = new boolean[mItems.length];
            simple_adapter.clear();
            simple_adapter.add(mItems[0]);
            Arrays.fill(mSelection, false);
        }
    }

    public void setSelection(String[] selection) {
        for (String cell : selection) {
            for (int j = 0; j < mItems.length; ++j) {
                if (mItems[j].equals(cell)) {
                    mSelection[j] = true;
                }
            }
        }
    }

    public void setSelection(List<String> selection) {
        if (selection.size() > 0) {
            for (int i = 0; i < mSelection.length; i++) {
                mSelection[i] = false;
            }
            for (String sel : selection) {
                for (int j = 0; j < mItems.length; ++j) {
                    if (mItems[j].equals(sel)) {
                        mSelection[j] = true;
                    }
                }
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());

    }

    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int[] selectedIndicies) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        for (int index : selectedIndicies) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<String>();
        for (int i = 0; i < mItems.length; ++i) {
            if (mSelection[i]) {
                selection.add(mItems[i]);
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<Integer>();
        for (int i = 0; i < mItems.length; ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;
        if (mItems != null) {
            for (int i = 0; i < mItems.length; ++i) {
                if (mSelection[i]) {
                    if (foundOne) {
                        sb.append(", ");
                    }
                    foundOne = true;

                    sb.append(mItems[i]);
                }
            }
        }

        if (areAllFalse(mSelection) || mItems == null ){
            sb.append("Consumidores");
        }

        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < mItems.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(mItems[i]);
            }
        }
        return sb.toString();
    }

    private boolean areAllFalse(boolean[] array)
    {
        if (array == null)
            return true;
        for(boolean b : array) if(b) return false;
        return true;
    }

}