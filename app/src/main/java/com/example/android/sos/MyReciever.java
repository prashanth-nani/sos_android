package com.example.android.sos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by prashantagrawal on 10/24/2016.
 */
public class MyReciever extends BroadcastReceiver {

    private String TAG="Reciever";
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, sensorService.class);
        context.startService(myIntent);
        //Toast.makeText(this,"reciever on ",Toast.LENGTH_SHORT).show();

    }
}
