package com.example.temp1.time;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by One on i/i/i.
 */

public class TimeFragment extends Fragment{

    private Time mTime;



    TextView txtTimer;
    Button btnStart;
    Button btnStop;


    long StartTime,timeInMilliseconds,TimeBuff,UpdateTime =0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds;

    ListView listView;
    String[] ListElements = new String[]{  };
    List<String> ListElementsArrayList;
    ArrayAdapter<String> adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTime = new Time();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_time, container, false);





        txtTimer = (TextView) v.findViewById(R.id.Timer_Value);
        btnStart = (Button)v.findViewById(R.id.btnStart);
        btnStop = (Button)v.findViewById(R.id.btnStop);

        listView = (ListView) v.findViewById(R.id.time_container);


        handler = new Handler();
        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );
        listView.setAdapter(adapter);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnStart.setVisibility( View.GONE );
                btnStop.setVisibility( View.VISIBLE );
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimerThread, 0);


                }
        });

                btnStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnStop.setVisibility( View.GONE );
                        btnStart.setVisibility( View.VISIBLE );

                        TimeBuff += timeInMilliseconds;
                        setListViewHeightBasedOnChildren(listView);

                        handler.removeCallbacks(updateTimerThread);
                        ListElementsArrayList.add(txtTimer.getText().toString());

                            adapter.notifyDataSetChanged();






                    }
                });






        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Time");
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public  Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis()-StartTime;
            UpdateTime = TimeBuff+timeInMilliseconds;
            Seconds=(int)(UpdateTime/1000);
            Minutes=Seconds/60;
            Seconds%=60;
            MilliSeconds=(int)(UpdateTime%1000);
            txtTimer.setText(""+Minutes+":"
                    +String.format("%02d",Seconds)+":"
                    +String.format("%03d",MilliSeconds));
            handler.postDelayed(this,0);
        }
    };
}