package com.wedo.fitdiary.data;

import android.provider.BaseColumns;

import java.util.List;

/**
 * Created by hanter on 2014. 11. 25..
 */
public class FitActivityType {
    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "fit_type";
        public static final String COLUMN_NAME_ACTIVITY_TYPE_NUM = "activity_no";
        public static final String COLUMN_NAME_ACTIVITY_NAME = "activity_name";
        public static final String COLUMN_NAME_MEASURE_TYPE = "measure_type";
    }

    private int activityTypeNum;    // 운동 번호
    private String activityName;    // 운동 이름
    private int measureType;        // 측정 방법

    public FitActivityType(int activityTypeNum, String activityName, int measureType) {
        this.activityTypeNum = activityTypeNum;
        this.activityName = activityName;
        this.measureType = measureType;
    }

    public int getType() {
        return activityTypeNum;
    }

    public void setType(int activityTypeNum) {
        this.activityTypeNum = activityTypeNum;
    }

    public String getName() {
        return activityName;
    }

    public void setName(String activityName) {
        this.activityName = activityName;
    }

    public int getMeasureType() {
        return measureType;
    }

    public void setMeasureType(int measureType) {
        this.measureType = measureType;
    }


    private static List<FitActivityType> _typeList = null;

    static void setTypeList(List<FitActivityType> typeList) {
        _typeList = typeList;
    }

    public List<FitActivityType> getTypeList() {
        return _typeList;
    }

    public static int findMeasureType(int activityTypeNum) {
        for(FitActivityType type : _typeList) {
            if(type.activityTypeNum == activityTypeNum) {
                return type.measureType;
            }
        }

        return -1;
    }
}
