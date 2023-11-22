package com.reset4.passlock.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.reset4.passlockpro.R;

/**
 * Created by ilkery on 31.12.2016.
 */
public class PLTextView extends AppCompatTextView {
    AttributeSet attrs;

    public PLTextView(Context context) {
        super(context);
        initialize();
    }

    public PLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        initialize();
    }

    public PLTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.attrs = attrs;
        initialize();
    }

    public void initialize()
    {
        setTextViewTypeface();
        setDefaultTextColor();
    }

    private void setDefaultTextColor(){
        String textColor = getTextColor();
        if(textColor == null || textColor == "") {
            setTextColor(UIHelper.getColor(getContext(), (R.color.darkGray)));
        }
    }

    private void setTextViewTypeface(){
        String font = getFont();
        if(font.equals(Font.RalewayMedium)) {
            this.setTypeface(Font.RalewayMediumFont);
        }else if(font.equals(Font.RalewaySemiBold)) {
            this.setTypeface(Font.RalewaySemiBoldFont);
        }
    }

    private String getFont(){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PLTextViewAttr);
        String font = typedArray.getString(R.styleable.PLTextViewAttr_fonts);
        if(font == null) {
            font = Font.RalewayMedium;
        }
        typedArray.recycle();
        return font;
    }

    private String getTextColor(){
        int[] attrsArray = new int[]{android.R.attr.textColor};
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, attrsArray);
        String textColor = typedArray.getString(0);
        typedArray.recycle();
        return textColor;
    }
}