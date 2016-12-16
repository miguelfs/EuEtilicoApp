package com.partiufast.euetilicoapp.ui.customviews;

import android.content.Context;
import android.util.AttributeSet;

import com.blackcat.currencyedittext.CurrencyEditText;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by Miguel on 14/12/2016.
 */

public class CustomCurrencyText extends CurrencyEditText {
    private Locale defaultLocale = Locale.US;
    Long valueInLowestDenom;


    public CustomCurrencyText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHint(String hint){
        this.setHint(hint);
    }


    protected void setValueInLowestDenom(Long mValueInLowestDenom) {
        this.valueInLowestDenom = mValueInLowestDenom;
    }


}
