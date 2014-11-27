package com.wedo.fitdiary.data;

import java.util.List;

/**
 * Created by hanter on 2014. 11. 25..
 */
public class FitActivityType {

    private int activityTypeNum;       // 운동 번호
    private String activityName;    // 운동 이름
    private int measureType;        // 측정 방법

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
