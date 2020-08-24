package myapplication.example.spider_induction_task1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class timer extends AppCompatActivity {

    EditText hour,minute,second;
    TextView timer,swiping_text;
    Button start,reset,stop,set,set_again;
    Integer entered_hour,entered_minute,entered_second;
    CountDownTimer countdowntimer;
    Long time_left = Long.valueOf(0),time_enter = Long.valueOf(0),leaving_time;
    Boolean timer_running = false,finish = false;
    MediaPlayer player;
    ConstraintLayout timer_layout;
    Animation anim;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timer_layout = (ConstraintLayout)findViewById(R.id.timer_layout);
        hour = (EditText)findViewById(R.id.hour);
        minute = (EditText)findViewById(R.id.minute);
        second = (EditText)findViewById(R.id.second);
        timer = (TextView)findViewById(R.id.timer);
        start = (Button)findViewById(R.id.start);
        reset = (Button)findViewById(R.id.reset);
        stop = (Button)findViewById(R.id.stop);
        set = (Button)findViewById(R.id.set);
        set_again = (Button)findViewById(R.id.set_again);
        set_again.setVisibility(View.INVISIBLE);
        start.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.INVISIBLE);
        swiping_text = (TextView)findViewById(R.id.swiping_text);
        swiping_text.setVisibility(View.INVISIBLE);
        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                hour.setEnabled(false);
                minute.setEnabled(false);
                second.setEnabled(false);
                finish = false;
                if((hour.getText().length()) == 0)hour.setText("00");
                if(minute.getText().length() == 0)minute.setText("00");
                if(second.getText().length() == 0)second.setText("00");
                if((Integer.parseInt(hour.getText().toString()) == 24)){
                    if((Integer.parseInt(minute.getText().toString()) != 0))minute.setError("Invalid time entered");
                    else if((Integer.parseInt(second.getText().toString()) != 0))second.setError("Invalid time entered");
                    else{
                        time_left = time_enter = Long.valueOf((24 * 3600000));
                    }
                }
                else if((Integer.parseInt(hour.getText().toString()) < 24)){
                    if((Integer.parseInt(minute.getText().toString()) < 60)){
                        if((Integer.parseInt(second.getText().toString()) < 60)){
                            entered_hour = Integer.parseInt(hour.getText().toString());
                            entered_minute = Integer.parseInt(minute.getText().toString());
                            entered_second = Integer.parseInt(second.getText().toString());
                            time_left = time_enter = Long.valueOf((entered_hour * 3600000) + (entered_minute * 60000) + (entered_second * 1000));
                        }
                        else second.setError("Invalid time entered");
                    }
                    else minute.setError("Invalid time entered");
                }
                else hour.setError("Invalid time entered");
                set.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                set_again.setVisibility(View.VISIBLE);
                update_timer();
            }
        });
        set_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                start.setVisibility(View.INVISIBLE);
                set_again.setVisibility(View.INVISIBLE);
                set.setVisibility(View.VISIBLE);
                hour.setEnabled(true);
                second.setEnabled(true);
                minute.setEnabled(true);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                start.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
                set.setVisibility(View.INVISIBLE);
                start_timer();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                stop.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                set.setVisibility(View.VISIBLE);
                reset_timer();

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                stop.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                stop_timer();
            }
        });
        update_timer();
    }
    private void start_timer() {
        leaving_time = System.currentTimeMillis() - time_left;
        countdowntimer = new CountDownTimer(time_left, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left = millisUntilFinished;
                if(time_left < 5000){
                    timer_layout.startAnimation(anim);
                    timer_layout.setBackgroundResource(R.color.red);
                }
                if(time_left < 2000)start_player();
                update_timer();
            }
            @Override
            public void onFinish() {
                timer_running = false;
                finish = true;
            }
        }.start();
        timer_running = true;
    }
    private void stop_timer() {
        countdowntimer.cancel();
        timer_running = false;
    }
    private void reset_timer() {
        time_left = time_enter;
        update_timer();
    }
    private void update_timer() {
        int hours = (int)(time_left/3600000);
        int minutes = (int) ((time_left / 1000) / 60);
        int seconds = (int) ((time_left / 1000) % 60);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours,minutes, seconds);
        timer.setText(timeLeftFormatted);
    }

    public void start_player(){
        if(player == null){
            swiping_text.setVisibility(View.VISIBLE);
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            player = MediaPlayer.create(getApplicationContext(),notification);
            player.setLooping(true);
        }
        player.start();
    }
    public void stop_player() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                if(finish){
                    player.stop();
                    swiping_text.setVisibility(View.INVISIBLE);
                    timer_layout.setBackgroundResource(R.color.white);
                    anim.cancel();
                    timer_layout.setAnimation(anim);
                    set.setVisibility(View.VISIBLE);
                    reset.setVisibility(View.INVISIBLE);
                    stop.setVisibility(View.INVISIBLE);
                    start.setVisibility(View.VISIBLE);
                    hour.setEnabled(true);
                    minute.setEnabled(true);
                    second.setEnabled(true);
                }
        }
        return false;
    }
}