package myapplication.example.spider_induction_task1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class alaram_main extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button pick_time,set_alarm,delete_alarm,additional_setting,ringtone_selection;
    CheckBox once,monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    RadioGroup ringtone_number_selection;
    RadioButton default_ringtone,ringtone_1,ringtone_2,ringtone_3;
    Integer alarm_hour,alarm_minute,ringtone_number = 0,nxt_alarm;
    TextView alarm_display;
    MediaPlayer player_1;
    Uri notification;
    ArrayList<Integer> days = new ArrayList<Integer>();
    String time,day_selected = " - ";
    Calendar c;
    Boolean recursive;
    alertreciever alert = new alertreciever();
    AlarmManager alarmManager;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaram_main);

        alarm_display = (TextView)findViewById(R.id.alarm_time);
        pick_time = (Button)findViewById(R.id.time_picker);
        set_alarm = (Button)findViewById(R.id.set_alarm);
        delete_alarm = (Button)findViewById(R.id.cancel_alarm);
        additional_setting = (Button)findViewById(R.id.select_type);
        ringtone_selection = (Button)findViewById(R.id.select_ringtone);
        once = (CheckBox)findViewById(R.id.once);
        monday = (CheckBox)findViewById(R.id.monday);
        tuesday = (CheckBox)findViewById(R.id.tuesday);
        wednesday = (CheckBox)findViewById(R.id.wednesday);
        thursday = (CheckBox)findViewById(R.id.thursday);
        friday = (CheckBox)findViewById(R.id.friday);
        saturday = (CheckBox)findViewById(R.id.saturday);
        sunday = (CheckBox)findViewById(R.id.sunday);
        ringtone_number_selection = (RadioGroup)findViewById(R.id.ringtone_options);
        default_ringtone = (RadioButton)findViewById(R.id.default_ringtone);
        ringtone_1 = (RadioButton)findViewById(R.id.ringtone_1);
        ringtone_2 = (RadioButton)findViewById(R.id.ringtone_2);
        ringtone_3 = (RadioButton)findViewById(R.id.ringtone_3);
        ringtone_selection.setVisibility(View.INVISIBLE);
        additional_setting.setVisibility(View.INVISIBLE);
        ringtone_number_selection.setVisibility(View.INVISIBLE);
        once.setVisibility(View.INVISIBLE);
        monday.setVisibility(View.INVISIBLE);
        tuesday.setVisibility(View.INVISIBLE);
        wednesday.setVisibility(View.INVISIBLE);
        thursday.setVisibility(View.INVISIBLE);
        friday.setVisibility(View.INVISIBLE);
        saturday.setVisibility(View.INVISIBLE);
        sunday.setVisibility(View.INVISIBLE);
        delete_alarm.setVisibility(View.INVISIBLE);
        set_alarm.setVisibility(View.INVISIBLE);
        pick_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                DialogFragment timePicker = new timepicker_fragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                pick_time.setVisibility(View.INVISIBLE);
            }
        });
        additional_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                once.setVisibility(View.VISIBLE);
                monday.setVisibility(View.VISIBLE);
                tuesday.setVisibility(View.VISIBLE);
                wednesday.setVisibility(View.VISIBLE);
                thursday.setVisibility(View.VISIBLE);
                friday.setVisibility(View.VISIBLE);
                saturday.setVisibility(View.VISIBLE);
                sunday.setVisibility(View.VISIBLE);
                find_days();
            }
        });

        ringtone_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                once.setVisibility(View.INVISIBLE);
                monday.setVisibility(View.INVISIBLE);
                tuesday.setVisibility(View.INVISIBLE);
                wednesday.setVisibility(View.INVISIBLE);
                thursday.setVisibility(View.INVISIBLE);
                friday.setVisibility(View.INVISIBLE);
                saturday.setVisibility(View.INVISIBLE);
                sunday.setVisibility(View.INVISIBLE);
                ringtone_number_selection.setVisibility(View.VISIBLE);
                if(default_ringtone.isChecked() || ringtone_1.isChecked() || ringtone_2.isChecked() || ringtone_3.isChecked()){
                    RadioButton option_select = findViewById(ringtone_number_selection.getCheckedRadioButtonId());
                    ringtone_number = ringtone_number_selection.indexOfChild(option_select);
                    set_ringtone();
                }
            }
        });

        set_alarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick( View v ) {
                ringtone_number_selection.setVisibility(View.INVISIBLE);
                additional_setting.setVisibility(View.INVISIBLE);
                ringtone_selection.setVisibility(View.INVISIBLE);
                set_alarm.setVisibility(View.INVISIBLE);
                delete_alarm.setVisibility(View.VISIBLE);
                startAlarm(c);
            }
        });

        delete_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                pick_time.setVisibility(View.VISIBLE);
                delete_alarm.setVisibility(View.INVISIBLE);
                additional_setting.setVisibility(View.VISIBLE);
                ringtone_selection.setVisibility(View.VISIBLE);
                cancelAlarm();
            }
        });
    }


    @Override
    public void onTimeSet( TimePicker view, int hourOfDay, int minute ) {
        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        if(monday.isChecked() || tuesday.isChecked() || wednesday.isChecked() || thursday.isChecked() || friday.isChecked() || saturday.isChecked() || sunday.isChecked()){
            setting_closerdate();
            c.set(Calendar.DAY_OF_WEEK,nxt_alarm);
            recursive = true;
        }
        alarm_hour = hourOfDay;
        alarm_minute = minute;
        time = alarmtime(hourOfDay,minute);
        alarm_display.setText(time);
        additional_setting.setVisibility(View.VISIBLE);
        ringtone_selection.setVisibility(View.VISIBLE);
        set_alarm.setVisibility(View.VISIBLE);
    }
    public String alarmtime(int hours,int min){
        String alarm,a_min;
        if(min > 9)a_min = min+"";
        else {
            a_min = "0";
            a_min = a_min.concat(min+"");
        }
        if(hours > 12){
            hours = hours - 12;
            alarm = hours+"";
            alarm = alarm.concat(":").concat(a_min).concat(" PM");
        }
        else{
            alarm = hours+"";
            alarm = alarm.concat(":").concat(a_min).concat(" AM");
        }
        return alarm;
    }

    public void start_player_withoutloop(){
        if(player_1 == null){
            player_1 = MediaPlayer.create(getApplicationContext(),notification);
            player_1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion( MediaPlayer mp ) {
                    stop_player(player_1);
                }
            });
        }
        player_1.start();

    }
    public void stop_player(MediaPlayer pl){
        if(pl != null){
            pl.release();
            pl = null;
        }
    }
    public void set_ringtone(){
        switch(ringtone_number){
            case 0:
                notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                start_player_withoutloop();
                break;

            case 1:
                notification = RingtoneManager.getDefaultUri(R.raw.nice_alarm_sound);
                start_player_withoutloop();
                break;

            case 2:
                notification = RingtoneManager.getDefaultUri(R.raw.morning_alarm);
                start_player_withoutloop();
                break;

            case 3:
                notification = RingtoneManager.getDefaultUri(R.raw.morning_mood);
                start_player_withoutloop();
                break;
        }
    }

    public void find_days(){
        if(monday.isChecked()) {
            days.add(Calendar.MONDAY);
            day_selected = day_selected.concat(" Monday");
        }
        if(tuesday.isChecked()){
            days.add(Calendar.TUESDAY);
            day_selected = day_selected.concat(" Tuesday");
        }
        if(wednesday.isChecked()){
            days.add(Calendar.WEDNESDAY);

            day_selected = day_selected.concat(" Wednesday");
        }
        if(thursday.isChecked()){
            days.add(Calendar.THURSDAY);
            day_selected = day_selected.concat(" Thursday");
        }
        if(friday.isChecked()){
            days.add(Calendar.FRIDAY);
            day_selected = day_selected.concat(" Friday");
        }
        if(saturday.isChecked()){
            days.add(Calendar.SATURDAY);
            day_selected = day_selected.concat(" Saturday");
        }
        if(sunday.isChecked()){
            days.add(Calendar.SUNDAY);
            day_selected =day_selected.concat(" Sunday");
        }
        alarm_display.setText(time.concat(day_selected));
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm( Calendar c) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, alertreciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ringtone_number, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    public void cancelAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, alertreciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public void setting_closerdate(){
        Calendar c = Calendar.getInstance();
        Integer current = c.get(Calendar.DAY_OF_WEEK);
        if(monday.isChecked() || tuesday.isChecked() || wednesday.isChecked() || thursday.isChecked() || friday.isChecked() || saturday.isChecked() || sunday.isChecked()){
            Integer small = days.get(0);
            for(int i = 1;i < days.size(); i++){
              if(Math.abs(current - days.get(i)) < small)small = days.get(i);
            }
            nxt_alarm = small;
        }
    }
}