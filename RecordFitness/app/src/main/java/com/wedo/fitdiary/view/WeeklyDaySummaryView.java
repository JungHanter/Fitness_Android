package com.wedo.fitdiary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.wedo.fitdiary.R;

/**
 * Created by hanter on 2014. 11. 25..
 */
public class WeeklyDaySummaryView extends LinearLayout {

    public WeeklyDaySummaryView(Context context) {
        super(context);
    }

    public WeeklyDaySummaryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeeklyDaySummaryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_weekly_day_summary, this, true);

        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WeeklyDaySummaryView, 0, 0);
        try {
            arr.getString(R.styleable.WeeklyDaySummaryView_textDayOfWeek);
            arr.getString(R.styleable.WeeklyDaySummaryView_textDay);
        } finally {
            arr.recycle();
        }
    }


}
