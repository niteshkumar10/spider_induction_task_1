package myapplication.example.spider_induction_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button alarm,timer,stopwatch;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarm = (Button)findViewById(R.id.alarm);
        timer = (Button)findViewById(R.id.timer);
        stopwatch = (Button)findViewById(R.id.stopwatch);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(getApplicationContext(),alaram_main.class);
                startActivity(intent);
            }
        });
        stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(getApplicationContext(),stopwatch.class);
                startActivity(intent);
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent(getApplicationContext(),timer.class);
                startActivity(intent);
            }
        });
    }
}