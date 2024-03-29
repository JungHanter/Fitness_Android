package com.wedo.fitdiary;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.wedo.fitdiary.R;
import com.wedo.fitdiary.data.FitActivity;
import com.wedo.fitdiary.data.FitActivityDataManager;
import com.wedo.fitdiary.data.FitActivityType;
import com.wedo.fitdiary.data.FitActivityTypeManager;
import com.wedo.fitdiary.data.OnManageFitActivityListener;
import com.wedo.fitdiary.data.OnManageFitTypeListener;

import java.sql.Date;
import java.util.List;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
                   OnManageFitActivityListener, OnManageFitTypeListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.act_main_navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.act_main_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        FitActivityType.setTypeList(mFitActivityTypeManager.getFitActivityTypes());
        mFitActivityDataManager.getWritableDatabase().close();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Log.d("MainActivity" , "onNavigationDrawerItemSelected(" + position + ")");

        if(position < 3) {
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.act_main_container, PlaceholderFragment.newInstance(position + 1))
                    .commit();


            /*FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.act_main_container, PlaceholderFragment.newInstance(position + 1));
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();*/
        } else {
            if(position == 3) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.act_main_container, new FitDataTestFragment())
                        .commit();
            }
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = "TEST FitData";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            Log.d("PlaceholderFragment" , "new Instance");
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.d("PlaceholderFragment" , "create fragment view");
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    private FitActivityDataManager mFitActivityDataManager = new FitActivityDataManager(this);
    private FitActivityTypeManager mFitActivityTypeManager = new FitActivityTypeManager(this);

    /*** OnManageFitTypeListener ***/
    @Override
    public long onFitTypeInsert(FitActivityType type) {
        long result =  mFitActivityTypeManager.insertFitActivityType(type);
        Log.d("OnManageFitTypeListener", "onFitTypeInsert() " + type.toString());
        return result;
    }

    @Override
    public boolean onFitTypeDelete(FitActivityType type) {
        Log.d("OnManageFitTypeListener", "onFitTypeDelete() " + type.toString());
        return mFitActivityTypeManager.deleteFitActivityType(type);
    }

    @Override
    public boolean onFitTypeUpdate(FitActivityType type) {
        Log.d("OnManageFitTypeListener", "onFitTypeUpdate() " + type.toString());
        return mFitActivityTypeManager.updateFitActivityType(type);
    }

    @Override
    public List<FitActivityType> onGetFitTypes() {
        Log.d("OnManageFitTypeListener", "onGetFitTypes");
        return mFitActivityTypeManager.getFitActivityTypes();
    }


    /*** OnManageFitActivityListeners ***/
    @Override
    public long onFitInsert(FitActivity act) {
        long result =  mFitActivityDataManager.insertFitActivity(act);
        Log.d("OnManageFitActivityListeners", "onFitInsert() " + act.toString());
        return result;
    }

    @Override
    public boolean onFitDelete(FitActivity act) {
        Log.d("OnManageFitActivityListeners", "onFitDelete() " + act.toString());
        return mFitActivityDataManager.deleteFitActivity(act);
    }

    @Override
    public boolean onFitDeleteAllByType(int fitType) {
        Log.d("OnManageFitActivityListeners", "onFitDeleteByType() " + fitType);
        return mFitActivityDataManager.deleteFitActivitiesByFitType(fitType);
    }

    @Override
    public boolean onFitUpdate(FitActivity act) {
        Log.d("OnManageFitActivityListeners", "onFitUpdate() " + act.toString());
        return mFitActivityDataManager.updateFitActivity(act);
    }

    @Override
    public List<FitActivity> onGetFitAll() {
        Log.d("OnManageFitActivityListeners", "onGetFitAll()");
        return mFitActivityDataManager.getAllFitActivities();
    }

    @Override
    public List<FitActivity> onGetFitByBeginTime(Date beginTime, Date endTime) {
        Log.d("OnManageFitActivityListeners", "onGetFitByBeginTime()");
        return mFitActivityDataManager.getFitActivitiesByBeginTime(beginTime, endTime);
    }
}
