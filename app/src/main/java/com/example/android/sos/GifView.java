package com.example.android.sos;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.InputStream;

/**

 */

public class GifView extends View {
    private InputStream gifInputStream;
    private Movie gifMovie;
    private int movieWidth,movieHieght;
    private long movieDuration;
    private long movieStart;

    SharedPreferences sharedpref1;
    SharedPreferences.Editor editor;
    public GifView(Context context) {

        super(context);
        editor=sharedpref1.edit();
        init(context);
    }
    public GifView(Context context,AttributeSet attrs) {
        super(context,attrs);
        init(context);

    }
    public GifView(Context context,AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init(context);
    }
    private void init(Context context){
        setFocusable(true);

        gifInputStream=context.getResources().openRawResource(R.raw.giphy);
        gifMovie = Movie.decodeStream(gifInputStream);
        movieWidth = gifMovie.width();
        movieHieght = gifMovie.height();
        movieDuration = gifMovie.duration();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec,int hieghtMeasureSpec){
        setMeasuredDimension(movieWidth,movieHieght);
    }
    public int getMovieWidth(){
        return movieWidth;
    }
    public int getMovieHieght(){
        return movieHieght;
    }
    public long getMovieDuration(){
        return movieDuration;
    }
    @Override
    protected void onDraw(Canvas canvas){
        long now= SystemClock.uptimeMillis();
        if(gifMovie!=null){
            int dur= gifMovie.duration();
            if(movieStart==0){
                movieStart=now;
            }
          /*  if(dur==0){
                dur=0;
            }*/
            // get
          //  String s = ((MyApplication) this.getApplication()).getSomeVariable();


            //dur=1;
            int relTime = (int)((now - movieStart)% dur);
            gifMovie.setTime(relTime);
            DisplayMetrics metrics = new DisplayMetrics();
          //  getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width =getContext().getResources().getDisplayMetrics().widthPixels;


           // float scaleHeight = (float) ((height / (1f)));//add 1f does the trick
            float scaleWidth = (float) ((width / (1f*movieWidth)));
            canvas.scale(2, 1);
          //  Toast.makeText(getContext()," "+scaleWidth,Toast.LENGTH_LONG).show();
            gifMovie.draw(canvas, 0, 0);
            invalidate();
        }
    }
}
