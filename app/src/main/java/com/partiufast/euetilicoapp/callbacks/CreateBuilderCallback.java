package com.partiufast.euetilicoapp.callbacks;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

/**
 * Created by Miguel on 14/12/2016.
 */

public interface CreateBuilderCallback {
    public void onCreateBuilder(Context context, AlertDialog.Builder builder, EditText input);
    public void onClickProductCard(int position);


}
