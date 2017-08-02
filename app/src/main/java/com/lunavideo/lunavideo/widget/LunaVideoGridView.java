package com.lunavideo.lunavideo.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.etsy.android.grid.StaggeredGridView;

/**
 * Created by gaowei on 22/06/2017.
 */

public class LunaVideoGridView extends StaggeredGridView {

    private int mPreviousPos = -1;

    public LunaVideoGridView(Context context) {
        super(context);
    }

    public LunaVideoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LunaVideoGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPreviousPosition(int pos) {
        mPreviousPos = pos;
    }

    public int getPreviousPos() {
        return mPreviousPos;
    }
}
