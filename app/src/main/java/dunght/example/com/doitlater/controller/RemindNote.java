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

import java.util.ArrayList;

import dunght.example.com.doitlater.R;
import dunght.example.com.doitlater.dao.DatabaseHelper;
import dunght.example.com.doitlater.model.Remind;
import dunght.example.com.doitlater.view.MainActivity;
import dunght.example.com.doitlater.view.ToDoFragment;

public class RemindNote extends Service {

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

        String titleRemind = intent.getExtras().getString("titleRemind");
        String contentRemind = intent.getExtras().getString("contentRemind");
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.remind);
        builder.setContentTitle(titleRemind);
        builder.setContentText(contentRemind);
        builder.setAutoCancel(true);

        mediaPlayer = MediaPlayer.create(this, R.raw.nguoiamphu);
        mediaPlayer.start();

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, builder.build());

//        intent = new Intent(this, ToDoFragment.class);
//        startActivity(intent);

        return  START_NOT_STICKY;
    }
}
