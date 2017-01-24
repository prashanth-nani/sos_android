package com.example.android.sos;

/**
 * Created by prashantagrawal on 10/24/2016.
 */

        import android.app.Service;
        import android.content.Context;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.media.MediaPlayer;
        import android.os.Binder;
        import android.os.IBinder;
        import android.content.Intent;
        import android.util.Log;

public class sensorService extends Service implements SensorEventListener {
    SensorManager mSensorManager;
    Sensor mSensor;
    float gravity[];
    float x[];
    boolean flag = false;
    MediaPlayer sir1 = null;

    /**
     * indicates how to behave if the service is killed
     */
    int mStartMode;
    private String TAG = "SensorService";
    /**
     * interface for clients that bind
     */
    private final IBinder mBinder= new LocalService();
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public class LocalService extends Binder
    {
        sensorService getService()
        {
            return sensorService.this;
        }
    }

    public void stopAudio()
    {
        if(sir1.isPlaying()) {
            sir1.stop();
            sir1.release();
            sir1=MediaPlayer.create(sensorService.this, R.raw.siren1);
        }
        // sirenS =MediaPlayer.create(sensor.this, R.raw.siren1);

    }
    public String sendMessage()
    {
        return "Hello test";
    }
        /**
     * indicates whether onRebind should be used
     */
    boolean mAllowRebind;

    /**
     * Called when the service is being created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
//        sharedPref = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


    }

    /**
     * The service is starting, due to a call to startService()
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "started " + " ");
        gravity = new float[4];
        //ImageButton btn= (ImageButton)findViewById(R.id.safe);
        x = new float[4];
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sir1 = MediaPlayer.create(sensorService.this, R.raw.siren1);
        //Intent in=new Intent(this,sensor.class);
        //startActivity(in);
        return mStartMode;
    }




    /**
     * Called when all clients have unbound with unbindService()
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }


    public void onSensorChanged(SensorEvent event) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.
        //  sir1 =MediaPlayer.create(this, R.raw.siren1);
        final float alpha = 0.8f;
        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = 9.81f;
        gravity[1] = 9.81f;
        gravity[2] = 9.81f;
        // Remove the gravity contribution with the high-pass filter.
        x[0] = event.values[0] - gravity[0];
        x[1] = event.values[1] - gravity[1];
        x[2] = event.values[2] - gravity[2];
        int sum = (int) Math.sqrt(x[2] * x[2]);
      //  Toast.makeText(sensorService.this, "" + sum + "$$" + x[0] + "$$" + x[1] + "$$" + x[2], Toast.LENGTH_SHORT).show();
        if (x[1]>20 || x[0]>20 || x[2]>20) {
            //      if(flag==true) {
            //sir.prepareAsync();

            sir1.setLooping(true);

            sir1.start();



        } else {
            //Toast.makeText(sensor.this,"landed",Toast.LENGTH_SHORT).show();
            // if(sir1.isPlaying())
            //sir1.stop();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}