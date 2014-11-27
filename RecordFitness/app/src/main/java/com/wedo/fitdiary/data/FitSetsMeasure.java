package com.wedo.fitdiary.data;

import java.text.ParseException;

/**
 * Created by hanter on 2014. 11. 26..
 */
public class FitSetsMeasure extends FitMeasure {
    private int times;
    private int sets;

    public FitSetsMeasure(int sets, int times) {
        measureType = MEASURE_TYPE_SETS_N_REPS;
        setSets(sets);
        setTimes(times);
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        if(times < 1) times = 1;
        this.times = times;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        if(sets < 1 ) sets = 1;
        this.sets = sets;
    }


    static public FitSetsMeasure parseValueString(String valueString) throws ParseException {
        try {
            String[] strings = valueString.split("/");
            return new FitSetsMeasure(Integer.parseInt(strings[0]),
                    Integer.parseInt(strings[1]));
        } catch (Exception e) {
            throw new ParseException("FitSetsMeasure times/sets string parsing error - "
                    + valueString, 0);
        }
    }

    @Override
    public String toValueString() {
        return ("" + times + '/' + sets);
    }
}
