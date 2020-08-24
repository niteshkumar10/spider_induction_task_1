package myapplication.example.spider_induction_task1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class stopwatch extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<lap_display> lap_displays;
    Chronometer chronometer;
    Button start,reset,stop,lap;
    long pauseOffset = 0;
    Boolean running = false,has_started = false;
    int running_lap_count = 0;
    Integer hour,min,sec;
    String m_hour,m_min,m_sec,result,stopped_result;
    Boolean check_stop_pressed = false;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        chronometer = (Chronometer)findViewById(R.id.chronometer);
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        reset = (Button)findViewById(R.id.reset);
        lap = (Button)findViewById(R.id.lap);
        stop.setVisibility(View.INVISIBLE);
        lap.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.INVISIBLE);
        lap_displays = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new lap_display_adapter(lap_displays);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                start.setVisibility(View.INVISIBLE);
                lap.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
                reset.setVisibility(View.VISIBLE);
                startChronometer();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.INVISIBLE);
                lap.setVisibility(View.INVISIBLE);
                pauseChronometer();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                stop.setVisibility(View.INVISIBLE);
                lap.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                resetChronometer();
            }
        });
        lap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick( View v ) {
                running_lap_count++;
                Long time;
                if(!has_started){
                    lap_displays.add((new lap_display("00:00:00",running_lap_count)));
                }
                else if(!check_stop_pressed) {
                    time = SystemClock.elapsedRealtime() - chronometer.getBase();
                    result = form_time(time);
                    lap_displays.add(new lap_display(result, running_lap_count));
                }
                else{
                    lap_displays.add(new lap_display(stopped_result, running_lap_count));
                }
                adapter.notifyItemInserted(running_lap_count - 1);
            }
        });
    }
    public void startChronometer() {
        if (!running) {
            check_stop_pressed = false;
            has_started = true;
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
    public void pauseChronometer() {
        if (running) {
            check_stop_pressed = true;
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            stopped_result = form_time(pauseOffset - 1000);
            running = false;
        }
    }
    public void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
    public void insertItem(int position) {
        lap_displays.add(new lap_display(chronometer.getBase()+"", running_lap_count));
        adapter.notifyItemInserted(position);
    }
    public String checking_single_digit(Integer n,String s){
        if(n < 10)s="0"+n;
        else s=n+"";
        return s;
    }

    public String form_time(Long time){
        String answer;
        hour = (int) (time / 3600000);
        m_hour = checking_single_digit(hour, m_hour);
        min = (int) ((time - (hour * 3600000)) / 60000);
        m_min = checking_single_digit(min, m_min);
        sec = (int) ((time - (hour * 3600000) - (min * 60000)) / 1000);
        m_sec = checking_single_digit(sec, m_sec);
        answer = m_hour.concat(":").concat(m_min).concat(":").concat(m_sec);
        return answer;
    }
}