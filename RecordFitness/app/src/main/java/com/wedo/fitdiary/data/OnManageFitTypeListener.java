package com.wedo.fitdiary.data;

import java.sql.Date;
import java.util.List;

/**
 * Created by hanter on 2014. 12. 2..
 */
public interface OnManageFitTypeListener {

    public void onFitInsert(FitActivity act);
    public boolean onFitDelete(FitActivity act);
    public boolean onFitUpdate(FitActivity act);
    public List<FitActivity> onGetFitAll();
    public List<FitActivity> onGetFitByBeginTime(Date beginTime, Date endTime);

}
