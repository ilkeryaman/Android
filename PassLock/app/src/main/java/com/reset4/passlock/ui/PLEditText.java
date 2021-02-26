package com.reset4.passlock.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

import com.reset4.passlock.R;

/**
 * Created by ilkery on 31.12.2016.
 */
public class PLEditText extends EditText {
    AttributeSet attrs;

    public PLEditText(Context context) {
        super(context);
        initialize();
    }

    public PLEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        initialize();
    }

    public PLEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.attrs = attrs;
        initialize();
    }

    public void initialize()
    {
        setTextViewTypeface();
        setTextColor(UIHelper.getColor(getContext(), (R.color.darkGray)));
    }

    public void setTextViewTypeface(){
        String font = getFont();
        if(font.equals(Font.RalewayMedium)) {
            this.setTypeface(Font.RalewayMediumFont);
        }else if(font.equals(Font.RalewaySemiBold)) {
            this.setTypeface(Font.RalewaySemiBoldFont);
        }
    }

    public String getFont(){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PLTextViewAttr);
        String font = typedArray.getString(R.styleable.PLTextViewAttr_fonts);
        if(font == null) {
            font = Font.RalewayMedium;
        }
        typedArray.recycle();
        return font;
    }
}
