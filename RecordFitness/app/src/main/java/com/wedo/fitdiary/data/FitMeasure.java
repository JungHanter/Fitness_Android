package com.wedo.fitdiary.data;

import java.text.ParseException;

/**
 * Created by hanter on 2014. 11. 25..
 */
public abstract class FitMeasure {
    public static final int MEASURE_TYPE_DISTANCE = 0;
    public static final int MEASURE_TYPE_SETS_N_REPS = 1;
//    static final int MEASURE_TYPE_

    protected int measureType;

    public int getMeasureType() {
        return measureType;
    }

    public void setMeasureType(int measureType) {
        this.measureType = measureType;
    }

    @Override
    public String toString() {
        return toValueString();
    }

    abstract public String toValueString();

    /*public static FitMeasure parseValueString(String valueString) throws ParseException {
        throw new ParseException("NOT use FitMeasure, BUT use subclass", 0);
    }*/

    public static FitMeasure parseValueString(int measureType, String valueString)
            throws ParseException {
        FitMeasure fitMeasure = null;
        switch (measureType) {
            case MEASURE_TYPE_DISTANCE:
                fitMeasure = FitDistMeasure.parseValueString(valueString);
                break;
            case MEASURE_TYPE_SETS_N_REPS:
                fitMeasure = FitSetsMeasure.parseValueString(valueString);
                break;
            default:
                throw new ParseException("UNDEFINED SUBCLASS, measureType = " + measureType +
                        ", valueString = " + valueString, 0);
        }
        return fitMeasure;
    }

}
