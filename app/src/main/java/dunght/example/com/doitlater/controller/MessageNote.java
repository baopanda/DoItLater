package dunght.example.com.doitlater.controller;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;

import java.util.ArrayList;

import dunght.example.com.doitlater.R;
import dunght.example.com.doitlater.dao.DatabaseHelper;
import dunght.example.com.doitlater.model.Remind;

public class MessageNote extends Service {

    MediaPlayer mediaPlayer;
    int id;
    private NotificationCompat.Builder builder;
    DatabaseHelper db;
    ArrayList<Remind> listRemind;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = new DatabaseHelper(this);
        listRemind = db.getAllRemind();
        int position = intent.getExtras().getInt("positionRemind");
        Remind remind = listRemind.get(position);
        remind.setState("Hoàn thành");
        db.updateRemind(remind);

        mediaPlayer = MediaPlayer.create(this, R.raw.nguoiamphu);
        mediaPlayer.start();

        String smsRemind = intent.getExtras().getString("smsRemind");
        String phoneNumber = intent.getExtras().getString("phoneRemind");
        SmsManager MySmsManager = SmsManager.getDefault();
        MySmsManager.sendTextMessage(phoneNumber, null, smsRemind, null, null);

        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.message);
        builder.setContentTitle(phoneNumber);
        builder.setContentText(smsRemind);
        builder.setAutoCancel(true);

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, builder.build());

        return  START_NOT_STICKY;
    }
}
