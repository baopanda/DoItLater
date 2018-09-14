package dunght.example.com.doitlater.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import dunght.example.com.doitlater.dao.DatabaseHelper;
import dunght.example.com.doitlater.model.Remind;

public class RemindReceiver extends BroadcastReceiver {

    DatabaseHelper db;
    ArrayList<Remind> listRemind;

    @Override
    public void onReceive(Context context, Intent intent) {
        String contentRemind = intent.getExtras().getString("contentRemind");
        int position = intent.getExtras().getInt("positionRemind");
        Intent intentRemind = new Intent(context, RemindNote.class);
        intentRemind.putExtra("titleRemind", "Nhắc nhở");
        intentRemind.putExtra("contentRemind", contentRemind);
        intentRemind.putExtra("positionRemind", position);
        context.startService(intentRemind);
    }
}
