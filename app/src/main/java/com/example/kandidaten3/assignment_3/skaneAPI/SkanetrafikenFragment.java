package com.example.kandidaten3.assignment_3.skaneAPI;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.example.kandidaten3.assignment_3.R;
import com.example.kandidaten3.assignment_3.skaneAPI.control.Constants;
import com.example.kandidaten3.assignment_3.skaneAPI.control.Journey;
import com.example.kandidaten3.assignment_3.skaneAPI.control.Journeys;
import com.example.kandidaten3.assignment_3.skaneAPI.control.Parser;

import java.util.ArrayList;


/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class SkanetrafikenFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private String searchURL;

    private ArrayList<Journey> myItems = new ArrayList<Journey>();
    private Adapter myAdapter;

    public SkanetrafikenFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                // String searchURL = Constants.getURL("80000", "93070", 14); //Malmö C = 80000,  Malmö Gatorg 80100, Hässleholm C 93070


                spinnerFrom.setOnItemSelectedListener(this);
                spinnerTo.setOnItemSelectedListener(this);

                int fromStation = spinnerFrom.getSelectedItemPosition();
                int toSTation = spinnerTo.getSelectedItemPosition();
                //String searchURL = Constants.getURL("80000", "93070", 10); //Malmö C = 80000,  Malmö GAtorg 80100, Hässleholm C 93070
                String[] stationNo = getResources().getStringArray(R.array.Numbers);
                searchURL = Constants.getURL( stationNo[fromStation], stationNo[toSTation], 14);
                new MyAsyncTask().execute(searchURL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reseplaneraren,
                container, false);

        spinnerFrom = (Spinner) view.findViewById(R.id.spinner);
        spinnerTo = (Spinner) view.findViewById(R.id.spinner2);

        spinnerFrom.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);

        spinnerTo.setSelection(1);


        ExpandableListView ev = (ExpandableListView) view.findViewById(R.id.expandableListView);
        myAdapter = new Adapter(getActivity(), myItems);
        ev.setAdapter(myAdapter);

        return view;
    }

    public void onClick(View v) {

    }

    private void searchFinished() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        int fromStation = spinnerFrom.getSelectedItemPosition();
        int toSTation = spinnerTo.getSelectedItemPosition();
        //String searchURL = Constants.getURL("80000", "93070", 10); //Malmö C = 80000,  Malmö GAtorg 80100, Hässleholm C 93070
        String[] stationNo = getResources().getStringArray(R.array.Numbers);
        String searchURL = Constants.getURL(stationNo[fromStation], stationNo[toSTation], 14);
        new MyAsyncTask().execute(searchURL);
        // FROM ONCREATE!

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private class MyAsyncTask extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... params) {
            Journeys journeys = Parser.getJourneys(params[0]); //There can be many in the params Array
            String param1 = params[0];
            myItems.clear();
            myItems.addAll(journeys.getJourneys());
            return null;
        }


        protected void onPostExecute(Long result) { //Called when the AsyncTask is all done
            //myAdapter.notifyDataSetInvalidated();
            myAdapter.notifyDataSetChanged();
            // searchFinished();
            Log.i("List", "List size: " + String.valueOf(myItems.size()));
        }
    }
}