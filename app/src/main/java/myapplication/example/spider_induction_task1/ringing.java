package myapplication.example.spider_induction_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class ringing extends AppCompatActivity {

    MediaPlayer player;
    Integer ringtone_number,ringtone_selected_no;
    Vibrator vibrator;
    Boolean finished;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);
        vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(20000);

        load();
        set_ringtone();
        start_player_loop();
    }
    public void stop_player( MediaPlayer pl){
        if(player != null){
            player.release();
            player = null;
        }
    }

    public void start_player_loop(){
        if(player == null){
            player = MediaPlayer.create(getApplicationContext(),ringtone_selected_no);
            player.setLooping(true);
            finished = true;
        }
        player.start();
    }


    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                if(finished){
                stop_player(player);
                vibrator.cancel();
                Intent intent = new Intent(getApplicationContext(),alaram_main.class);
                startActivity(intent);
            }
        }
        return false;
    }
    public void set_ringtone(){
        switch(ringtone_number){
            case 0:
                ringtone_selected_no = R.raw.nice_alarm_sound;
                break;

            case 1:
                ringtone_selected_no = R.raw.morning_alarm;
                break;

            case 2:
                ringtone_selected_no =  R.raw.morning_mood;
                break;
        }
    }
    public void load(){
        SharedPreferences s2 = getSharedPreferences("task1",MODE_PRIVATE);
        ringtone_number = s2.getInt("ringtone_number",0);
    }
}