package com.partiufast.euetilicoapp.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.partiufast.euetilicoapp.R;

/**
 * Created by Miguel on 12/12/2016.
 */

public class PersonListFragment extends Fragment{

    public static ProductListFragment newInstance(int sectionNumber) {
        ProductListFragment fragment = new ProductListFragment();
        /*Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, "Product List Fragment"));*/
        return rootView;
    }
}
