package com.example.android.sos;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class About extends AppCompatActivity {

    String ListItemsName[] = new String[]{
            "Add or remove emergency contacts from Navigation Drawer",
            "Click to trigger loud alarm.Click again to stop.",
            "Click to get your current location and nearby places.",
            "Click to send SOS message to all the emergency contacts",
            "Single Click to switch on torch light.Double click to get stroboscopic flash light.Clik again to switch off",
            "Click to open Voice Recorder/Player",};
    Integer ImageName[] = {
            R.drawable.navdraw,
            R.drawable.sound_icon,
            R.drawable.gps,
            R.drawable.message,
            R.drawable.flash,
            R.drawable.ausio,
    };
    ListView listView;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about2);

        listView = (ListView)findViewById(R.id.listView1);

        listAdapter = new ListAdapter(this , ListItemsName, ImageName);

        listView.setAdapter(listAdapter);
           }
}
