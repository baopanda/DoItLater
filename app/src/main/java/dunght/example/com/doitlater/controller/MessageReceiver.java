package dunght.example.com.doitlater.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import dunght.example.com.doitlater.dao.DatabaseHelper;
import dunght.example.com.doitlater.model.Remind;

public class MessageReceiver extends BroadcastReceiver {

    DatabaseHelper db;
    ArrayList<Remind> listRemind;

    @Override
    public void onReceive(Context context, Intent intent) {
        String smsRemind = intent.getExtras().getString("smsRemind");
        String phoneNumber = intent.getExtras().getString("phoneRemind");
        int position = intent.getExtras().getInt("positionRemind");
        Intent intentRemind = new Intent(context, MessageNote.class);
        intentRemind.putExtra("smsRemind", smsRemind);
        intentRemind.putExtra("positionRemind", position);
        intentRemind.putExtra("phoneRemind", phoneNumber);
        context.startService(intentRemind);
    }
}
