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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by One on i/i/i.
 */

public class TimeFragment extends Fragment{

    private Time mTime;


    TextView txtTimer;
    Button btnSingle;

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
        btnSingle = (Button)v.findViewById(R.id.btnSingle);
        listView = (ListView) v.findViewById(R.id.time_container); //this is new
        handler = new Handler();
        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );
        listView.setAdapter(adapter);
        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//change button color, text, and start/stop timer.
                if (btnSingle.getText()=="Pause")  {
                    btnSingle.setText("Start");
                    TimeBuff+=timeInMilliseconds;

                    ListElementsArrayList.add(txtTimer.getText().toString());
                    adapter.notifyDataSetChanged();

                    handler.removeCallbacks(updateTimerThread);
                    btnSingle.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_style_green));
                } else {
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimerThread, 0);
                    btnSingle.setText("Pause");
                    adapter.notifyDataSetChanged();
                    btnSingle.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_style_red));
                }
            }
        });



        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Time");
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