package myapplication.example.spider_induction_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;

public class ringing extends AppCompatActivity {

    MediaPlayer player;
    Uri notification;
    Integer ringtone_number;
    Vibrator vibrator;
    Boolean finished;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);
        vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(20000);
        ringtone_number = getIntent().getIntExtra("ringtone_number",0);
        set_notification();
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
            player = MediaPlayer.create(this,notification);
            player.setLooping(true);
            finished = true;
        }
        player.start();
    }

    public void set_notification(){
        switch(ringtone_number){
            case 0:
                notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                start_player_loop();
                break;

            case 1:
                notification = RingtoneManager.getDefaultUri(R.raw.nice_alarm_sound);
                start_player_loop();
                break;

            case 2:
                notification = RingtoneManager.getDefaultUri(R.raw.morning_alarm);
                start_player_loop();
                break;

            case 3:
                notification = RingtoneManager.getDefaultUri(R.raw.morning_mood);
                start_player_loop();
                break;
        }
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
}