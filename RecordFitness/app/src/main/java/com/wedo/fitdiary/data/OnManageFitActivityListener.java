package com.wedo.fitdiary.data;

import java.util.List;

/**
 * Created by hanter on 2014. 12. 2..
 */
public interface OnManageFitActivityListener {

    public long onFitTypeInsert(FitActivityType type);
    public boolean onFitTypeDelete(FitActivityType type);
    public boolean onFitTypeUpdate(FitActivityType type);
    public List<FitActivityType> onGetFitTypes();

}
