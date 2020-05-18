package com.bignerdranch.android.workout;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment implements View.OnClickListener {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds" );
            running = savedInstanceState.getBoolean( "running" );
            wasRunning = savedInstanceState.getBoolean( "wasRunning" );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate( R.layout.fragment_stopwatch, container, false );
        runTimer(layout);

        Button startBtn = layout.findViewById( R.id.start_button );
        startBtn.setOnClickListener( this );

        Button stopBtn = layout.findViewById( R.id.stop_button );
        stopBtn.setOnClickListener( this );

        Button resettBtn = layout.findViewById( R.id.reset_button );
        resettBtn.setOnClickListener( this );





        return  layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt( "seconds", seconds );
        savedInstanceState.putBoolean( "running", running );
        savedInstanceState.putBoolean( "wasRunning", wasRunning );
    }

    public void onClickStart(View view){
        running = true;
    }

    public void onClickStop(View view){
        running = false;
    }

    public void onClickReset (View view){
        running = false;
        seconds = 0;
    }

    private void runTimer(View view){
        final TextView timeView =  view.findViewById( R.id.time_view );
        final Handler handler = new Handler(  );

        handler.post( new Runnable() {
            @Override
            public void run() {
                int hours =  seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds % 60;

                String time = String.format( Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs );
                timeView.setText( time );

                if(running){
                    seconds++;
                }
                handler.postDelayed( this, 1000 );
            }
        } );
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
                case R.id.start_button:
                onClickStart();
                break;

                case R.id.stop_button:
                onClickStop();
                break;

                case R.id.reset_button:
                onClickReset();
                break;
        }
    }

    private void onClickStart(){
        running = true;
    }

    private void onClickStop(){
        running = false;
    }

    private void onClickReset(){
        running = false;
        seconds = 0;
    }



}
