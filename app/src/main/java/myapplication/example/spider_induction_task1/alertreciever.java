package myapplication.example.spider_induction_task1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

public class alertreciever extends BroadcastReceiver {



    Integer ringtone_number;

    @Override
    public void onReceive( Context context, Intent intent ) {
        notificationhelper notificationHelper = new notificationhelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());


        Intent intent_1 = new Intent(context,ringing.class);
        intent_1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent_1);
    }

}

