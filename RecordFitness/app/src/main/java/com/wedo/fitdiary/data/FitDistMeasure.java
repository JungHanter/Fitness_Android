package com.wedo.fitdiary.data;

import java.text.ParseException;

/**
 * Created by hanter on 2014. 11. 26..
 */
public class FitDistMeasure extends FitMeasure {
    private float killometer;

    public FitDistMeasure(float killometer) {
        measureType = MEASURE_TYPE_DISTANCE;
        setKillometer(killometer);
    }

    public float getKillometer() {
        return killometer;
    }

    public void setKillometer(float killometer) {
        if(killometer < 0f) killometer = 0f;
        this.killometer = killometer;
    }


    static public FitDistMeasure parseValueString(String valueString) throws ParseException {
        try {
            return new FitDistMeasure(Float.parseFloat(valueString));
        } catch (Exception e) {
            throw new ParseException("FitDistMeasure float string parsing error - "
                    + valueString, 0);
        }
    }

    @Override
    public String toValueString() {
        return String.format("%.1f", killometer);
    }
}