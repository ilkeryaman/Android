package com.reset4.passlock.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatButton;

import com.reset4.passlockpro.R;

/**
 * Created by ilkery on 31.12.2016.
 */
public class PLButton extends AppCompatButton {
    AttributeSet attrs;

    public PLButton(Context context) {
        super(context);
        initialize();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public PLButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        initialize();
    }

    public PLButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.attrs = attrs;
        initialize();
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

    public void initialize()
    {
        this.setTypeface(Font.RalewayMediumFont);
        if(isDecreaseTextSize()){
            this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }else {
            this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        }
        this.setTextColor(UIHelper.getColor(getContext(), R.color.white));
        this.setShadowLayer(5, 0, 0, UIHelper.getColor(getContext(), R.color.darkGray));
        this.setBackgroundResource(R.drawable.plbutton_style);

    }

    public boolean isDecreaseTextSize(){
        boolean isDecreaseTextSize = false;
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.PLButtonAttr, 0, 0);

        try {
            isDecreaseTextSize = typedArray.getBoolean(R.styleable.PLButtonAttr_decreaseTextSize, false);
        } finally {
            typedArray.recycle();
        }

        return isDecreaseTextSize;
    }
}
