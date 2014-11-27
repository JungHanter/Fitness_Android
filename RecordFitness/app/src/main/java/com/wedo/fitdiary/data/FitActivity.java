package com.wedo.fitdiary.data;

import android.provider.BaseColumns;

import java.sql.Date;
import java.text.ParseException;

/**
 * Created by hanter on 2014. 11. 25..
 */
public class FitActivity {
    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "fit_activity";
        public static final String COLUMN_NAME_INDEX = _ID;
        public static final String COLUMN_NAME_ACTIVITY_TYPE_NUM = "activity_no";
        public static final String COLUMN_NAME_BEGIN_TIME = "begin_time";
        public static final String COLUMN_NAME_END_TIME = "end_time";
        public static final String COLUMN_NAME_MEASURE_VALUE = "measure_value";
    }

    private long index = -1;
    private int activityTypeNum = -1;
    private Date beginTime = null;
    private Date endTime = null;
    private String measureValue = null;
    private FitMeasure measure = null;     // measureValue Parsing 결과

    public FitActivity(int activityTypeNum, Date beginTime, Date endTime, FitMeasure measure) {
        this.activityTypeNum = activityTypeNum;
        this.beginTime = beginTime;
        this.endTime = endTime;
        setMeasure(measure);
    }

    public FitActivity(int activityTypeNum, Date beginTime, Date endTime, String measureValue)
            throws ParseException {
        this.activityTypeNum = activityTypeNum;
        this.beginTime = beginTime;
        this.endTime = endTime;
        setMeasureValue(measureValue);
    }

    public long getIndex() {
        return index;
    }

    protected void setIndex(long index) {
        this.index = index;
    }

    public int getActivityTypeNum() {
        return activityTypeNum;
    }

    public void setActivityTypeNum(int activityTypeNum) {
        this.activityTypeNum = activityTypeNum;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    protected String getMeasureValue() {
        return measureValue;
    }

    public void setMeasureValue(String measureValue) throws ParseException {
        this.measureValue = measureValue;
        this.measure = FitMeasure.parseValueString(FitActivityType.findMeasureType(activityTypeNum),
                measureValue);
    }

    public FitMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(FitMeasure measure) {
        this.measure = measure;
        this.measureValue = measure.toValueString();
    }
}
