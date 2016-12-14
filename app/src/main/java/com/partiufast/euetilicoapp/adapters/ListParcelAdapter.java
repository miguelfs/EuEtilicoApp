package com.partiufast.euetilicoapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.math.BigDecimal;
import java.util.List;

import me.grantland.widget.AutofitLayout;
import me.grantland.widget.AutofitTextView;

/**
 * Created by Miguel on 13/12/2016.
 */

public class ListParcelAdapter extends BaseAdapter {

    private final List<ProductItem> mList;
    private final Context mContext;

    ListParcelAdapter(List<ProductItem> list, Context context){
        mList = list;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0; //não implementado
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.product_parcel_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.productLabel = (TextView) view.findViewById(R.id.product_label);
            viewHolder.priceLabel = (TextView) view.findViewById(R.id.product_price_label);
            viewHolder.mathLabel = (TextView) view.findViewById(R.id.math_label);
            viewHolder.resultLabel = (TextView) view.findViewById(R.id.result_label);
            view.setTag(viewHolder);
        }
            else {
                viewHolder = (ViewHolder) view.getTag();
            }
        Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),  "fonts/cour.ttf");
        viewHolder.productLabel.setTypeface(custom_font);
        viewHolder.priceLabel.setTypeface(custom_font);
        viewHolder.mathLabel.setTypeface(custom_font);
        viewHolder.resultLabel.setTypeface(custom_font);

        /*viewHolder.productLabel.setText(mList.get(i).getProductName());
        viewHolder.priceLabel.setText(mList.get(i).getProductPriceString());
        viewHolder.mathLabel.setText("×"+mList.get(i).getProductCount()+"Un.÷"+mList.get(i).getProductCustomerList().size()+"=");
        viewHolder.productLabel.setText(mList.get(i).getParcelPriceCurrencyFormat(new BigDecimal(1)));*/
        /*viewHolder.productLabel.setText("Cerveja Antártica" + ":");
        viewHolder.priceLabel.setText(mList.get(i).getProductPriceCurrencyFormat());
        viewHolder.mathLabel.setText("×"+mList.get(i).getProductCount()+"Un.÷"+mList.get(i).getProductCustomerList().size()+"=");
        viewHolder.productLabel.setText("R$13,33");*/
        return view;
    }

    private static class ViewHolder {
        private TextView productLabel, priceLabel, mathLabel, resultLabel;
    }

}
