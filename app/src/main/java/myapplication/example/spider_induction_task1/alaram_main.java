package myapplication.example.spider_induction_task1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

public class alaram_main extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button pick_time,set_alarm,delete_alarm,additional_setting,ringtone_selection,confirm_1,confirm_2;
    CheckBox once,monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    RadioGroup ringtone_number_selection;
    RadioButton ringtone_1,ringtone_2,ringtone_3;
    Integer ringtone_number = 0,nxt_alarm,alarm_hour,alarm_minute,rintone_selected_no;
    TextView alarm_display;
    MediaPlayer player_1;
    ArrayList<Integer> days = new ArrayList<Integer>();
    String days_tostring;
    String time,day_selected = " - ";
    Calendar c;
    Boolean recursive = false,test_recursive;
    AlarmManager alarmManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        confirm_1 = (Button)findViewById(R.id.confirm_1);
        confirm_2 = (Button)findViewById(R.id.confirm_2);
        confirm_1.setVisibility(View.INVISIBLE);
        confirm_2.setVisibility(View.INVISIBLE);
        load();
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
                confirm_2.setVisibility(View.VISIBLE);
                once.setVisibility(View.VISIBLE);
                monday.setVisibility(View.VISIBLE);
                tuesday.setVisibility(View.VISIBLE);
                wednesday.setVisibility(View.VISIBLE);
                thursday.setVisibility(View.VISIBLE);
                friday.setVisibility(View.VISIBLE);
                saturday.setVisibility(View.VISIBLE);
                sunday.setVisibility(View.VISIBLE);
                set_alarm.setVisibility(View.INVISIBLE);
                ringtone_selection.setVisibility(View.INVISIBLE);
            }
        });
        confirm_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                find_days();
                save();
                confirm_2.setVisibility(View.INVISIBLE);
                set_alarm.setVisibility(View.VISIBLE);
                once.setVisibility(View.INVISIBLE);
                monday.setVisibility(View.INVISIBLE);
                tuesday.setVisibility(View.INVISIBLE);
                wednesday.setVisibility(View.INVISIBLE);
                thursday.setVisibility(View.INVISIBLE);
                friday.setVisibility(View.INVISIBLE);
                saturday.setVisibility(View.INVISIBLE);
                sunday.setVisibility(View.INVISIBLE);
                ringtone_selection.setVisibility(View.VISIBLE);

            }
        });

        ringtone_number_selection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( RadioGroup group, int checkedId ) {
                stop_player();
                RadioButton option_select = findViewById(ringtone_number_selection.getCheckedRadioButtonId());
                ringtone_number = ringtone_number_selection.indexOfChild(option_select);
                set_ringtone();
                start_player_withoutloop();
            }
        });
        confirm_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                stop_player();
                if(ringtone_1.isChecked() || ringtone_2.isChecked() || ringtone_3.isChecked()){
                    ringtone_number_selection.setVisibility(View.INVISIBLE);
                    RadioButton option_select = findViewById(ringtone_number_selection.getCheckedRadioButtonId());
                    ringtone_number = ringtone_number_selection.indexOfChild(option_select);
                }
                save();
                confirm_1.setVisibility(View.INVISIBLE);
                additional_setting.setVisibility(View.VISIBLE);
                set_alarm.setVisibility(View.VISIBLE);
            }
        });

        ringtone_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                confirm_1.setVisibility(View.VISIBLE);
                once.setVisibility(View.INVISIBLE);
                monday.setVisibility(View.INVISIBLE);
                tuesday.setVisibility(View.INVISIBLE);
                wednesday.setVisibility(View.INVISIBLE);
                thursday.setVisibility(View.INVISIBLE);
                friday.setVisibility(View.INVISIBLE);
                saturday.setVisibility(View.INVISIBLE);
                sunday.setVisibility(View.INVISIBLE);
                ringtone_number_selection.setVisibility(View.VISIBLE);
                set_alarm.setVisibility(View.INVISIBLE);
                additional_setting.setVisibility(View.INVISIBLE);
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
                additional_setting.setVisibility(View.INVISIBLE);
                ringtone_selection.setVisibility(View.INVISIBLE);
                cancelAlarm();
                alarm_display.setText("00:00 - Monday");
            }
        });
    }


    @Override
    public void onTimeSet( TimePicker view, int hourOfDay, int minute ) {
       alarm_hour = hourOfDay;
       alarm_minute = minute;
        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        time = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        alarm_display.setText(time);
        additional_setting.setVisibility(View.VISIBLE);
        ringtone_selection.setVisibility(View.VISIBLE);
        set_alarm.setVisibility(View.VISIBLE);
    }

    public void start_player_withoutloop(){
        if(player_1 == null){
            player_1 = MediaPlayer.create(getApplicationContext(),rintone_selected_no);
            player_1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion( MediaPlayer mp ) {
                    stop_player();
                }
            });
        }
        player_1.start();
    }
    public void stop_player(){
        if(player_1 != null){
            player_1.release();
            player_1 = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop_player();
    }

    public void set_ringtone(){
        switch(ringtone_number){
            case 0:
                rintone_selected_no = R.raw.nice_alarm_sound;
                break;

            case 1:
                rintone_selected_no = R.raw.morning_alarm;
                break;

            case 2:
               rintone_selected_no =  R.raw.morning_mood;
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
        if(days.size() > 0)recursive = true;
        if(monday.isChecked() || tuesday.isChecked() || wednesday.isChecked() || thursday.isChecked() || friday.isChecked() || saturday.isChecked() || sunday.isChecked()){
            setting_closerdate();
            c.set(Calendar.DAY_OF_WEEK,nxt_alarm);
        }
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, alertreciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    public void cancelAlarm() {
        recursive = false;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, alertreciever.class);
        intent.putExtra("ringtone_number",ringtone_number);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public void setting_closerdate(){
        Calendar c = Calendar.getInstance();
        Integer current = c.get(Calendar.DAY_OF_WEEK);
        if(monday.isChecked() || tuesday.isChecked() || wednesday.isChecked() || thursday.isChecked() || friday.isChecked() || saturday.isChecked() || sunday.isChecked()){
            Integer small = days.get(0);
            for(int i = 1;i < days.size(); i++){
              if(Math.abs(current - days.get(i)) < small){
                  small = days.get(i);
              }
            }
            nxt_alarm = small;
        }
    }

    public void save(){
        SharedPreferences s1 = getSharedPreferences("task1",MODE_PRIVATE);
        SharedPreferences.Editor ed = s1.edit();
        ed.putBoolean("recursive",recursive);
        ed.putInt("ringtone_number",ringtone_number);
        if(recursive){
            StringBuilder str = new StringBuilder();
            for(int i = 0; i < days.size(); i++){
                str.append(days.get(i)).append(",");
            }
            ed.putString("days",str.toString());
            ed.putInt("number_of_days",days.size());
            ed.putInt("hour",alarm_hour);
            ed.putInt("minute",alarm_minute);
        }
        ed.apply();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void load(){
        SharedPreferences s2 = getSharedPreferences("task1",MODE_PRIVATE);
        test_recursive = s2.getBoolean("recursive",false);
        if(test_recursive){
            ringtone_number = s2.getInt("ringtone_number",ringtone_number);
            days_tostring = s2.getString("days",",");
            StringTokenizer st = new StringTokenizer(days_tostring,",");
            Integer days_size = s2.getInt("number_of_days",0);
            for(int i = 0; i < days_size; i++){
                days.set(i,Integer.parseInt(st.nextToken()));
            }
            pick_time.setVisibility(View.INVISIBLE);
            delete_alarm.setVisibility(View.VISIBLE);
            recursive = test_recursive;
            alarm_hour = s2.getInt("hour",0);
            alarm_minute = s2.getInt("minute",0);
            Calendar d = Calendar.getInstance();
            d.set(Calendar.HOUR_OF_DAY, alarm_hour);
            d.set(Calendar.MINUTE, alarm_minute);
            d.set(Calendar.SECOND, 0);
            setting_closerdate();
            d.set(Calendar.DAY_OF_WEEK,nxt_alarm);
            time = DateFormat.getTimeInstance(DateFormat.SHORT).format(d.getTime());
            alarm_display.setText(time);
            startAlarm(d);
        }
    }
}