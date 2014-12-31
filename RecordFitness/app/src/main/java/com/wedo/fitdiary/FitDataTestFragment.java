package com.wedo.fitdiary;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wedo.fitdiary.data.FitActivity;
import com.wedo.fitdiary.data.FitActivityType;
import com.wedo.fitdiary.data.FitDistMeasure;
import com.wedo.fitdiary.data.FitMeasure;
import com.wedo.fitdiary.data.FitSetsMeasure;
import com.wedo.fitdiary.data.OnManageFitActivityListener;
import com.wedo.fitdiary.data.OnManageFitTypeListener;

import java.sql.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by hanter on 2014. 12. 1..
 */
public class FitDataTestFragment extends Fragment {
    OnManageFitActivityListener mFitActivityListener;
    OnManageFitTypeListener mFitTypeListener;

    private ListView lvTestData;
    private Button btnTest;

    private List<FitActivity> fitActivityList;
    private ArrayAdapter<FitActivity> arrayAdapter;

    public FitDataTestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data_test, container, false);

        //fitActivityList = mFitActivityListener.onGetFitAll();

        lvTestData = (ListView)rootView.findViewById(R.id.frag_test_list);
        btnTest = (Button)rootView.findViewById(R.id.frag_test_button);

        //arrayAdapter = new ArrayAdapter<FitActivity>(getActivity(),
        //        android.R.layout.simple_list_item_1, fitActivityList);
        //lvTestData.setAdapter(arrayAdapter);
        loadAllData();

        btnTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Random random = new Random();
                FitActivity fit = null;
                FitMeasure measure = null;

                int actType = FitActivityType.getTypeList().get(
                        Math.abs(random.nextInt()) % FitActivityType.getTypeList().size()).getType();
                Date date = new Date(2014-1900, random.nextInt()%2+11, random.nextInt()%30+1);

                int measureType = FitActivityType.findMeasureType(actType);

                if (measureType == FitMeasure.MEASURE_TYPE_DISTANCE) {
                    measure = new FitDistMeasure((int)((random.nextFloat()*16 + 4.0f)*10.f)/10.f);
                } else if (measureType == FitMeasure.MEASURE_TYPE_SETS_N_REPS) {
                    measure = new FitSetsMeasure(random.nextInt()%5+1, random.nextInt()%40+11);
                } else {
                    Log.d("FitDataTest", "Fuck. act type = " + actType);
                }

                fit = new FitActivity(actType, date, date, measure);
                mFitActivityListener.onFitInsert(fit);

                loadAllData();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mFitActivityListener = (OnManageFitActivityListener) activity;
            mFitTypeListener = (OnManageFitTypeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement OnManageFitActivityListener/OnManageFitTypeListener");
        }
    }

    private void loadAllData() {
        fitActivityList = mFitActivityListener.onGetFitAll();

        arrayAdapter = new ArrayAdapter<FitActivity>(getActivity(),
                android.R.layout.simple_list_item_1, fitActivityList);
        lvTestData.setAdapter(arrayAdapter);

        lvTestData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FitActivity fitAct = (FitActivity)parent.getAdapter().getItem(position);
                //loadAllData();
            }
        });
    }
}
