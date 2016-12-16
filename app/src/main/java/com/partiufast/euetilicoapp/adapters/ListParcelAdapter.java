package com.partiufast.euetilicoapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.partiufast.euetilicoapp.R;
import com.partiufast.euetilicoapp.models.ProductItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitLayout;
import me.grantland.widget.AutofitTextView;

/**
 * Created by Miguel on 13/12/2016.
 */

public class ListParcelAdapter extends RecyclerView.Adapter<ListParcelAdapter.ParcelViewHolder> {

    private List<ProductItem> mList;
    private Context mContext;

    ListParcelAdapter(List<ProductItem> list, Context context) {
        mList = list;
        mContext = context;
    }

    ListParcelAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setList(List<ProductItem> list) {
        mList.clear();
        mList.addAll(list);
    }

    public class ParcelViewHolder extends RecyclerView.ViewHolder {
        AutofitTextView productLabel;
        TextView priceLabel, mathLabel, resultLabel;

        public ParcelViewHolder(View itemView, Context context) {
            super(itemView);
            productLabel = (AutofitTextView) itemView.findViewById(R.id.product_label);
            priceLabel = (TextView) itemView.findViewById(R.id.product_price_label);
            mathLabel = (TextView) itemView.findViewById(R.id.math_label);
            resultLabel = (TextView) itemView.findViewById(R.id.result_label);
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/cour.ttf");
            productLabel.setTypeface(custom_font);
            priceLabel.setTypeface(custom_font);
            mathLabel.setTypeface(custom_font);
            resultLabel.setTypeface(custom_font);
        }
    }

    @Override
    public ParcelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_parcel_item_layout, parent, false);
        return new ParcelViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(ParcelViewHolder viewHolder, int i) {
        viewHolder.productLabel.setText(mList.get(i).getProductName() + ":");
        viewHolder.priceLabel.setText(mList.get(i).getProductelPriceCurrencyFormat());
        viewHolder.mathLabel.setText("×" + mList.get(i).getProductCount() + "Un.÷" + mList.get(i).getProductCustomerList().size() + "=");
        viewHolder.resultLabel.setText(mList.get(i).getParcelPriceCurrencyFormat(new BigDecimal(1)));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



}
