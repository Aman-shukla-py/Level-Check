package com.example.levelcheck;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor accelerometer;
    private SensorManager accelerometerManager;
    private AnimateView animateView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        accelerometerManager=(SensorManager)getSystemService(SENSOR_SERVICE);

        accelerometer=accelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        accelerometerManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);

        animateView=new AnimateView(this);
        setContentView(animateView);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
               animateView.onSensorEvent(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        accelerometerManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        accelerometerManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);
    }

    public class AnimateView extends View
    {

        private static final int CIRCLE_RADIUS=25;

        private Paint paint;
        private Paint paint1;

        private int x;
        private int y;
        private int z;
        private int height;
        private int width;

        public AnimateView(Context context) {
            super(context);

            //Bubble
            paint=new Paint();
            paint.setColor(Color.GREEN);

            //Square Background
            paint1=new Paint();
            paint1.setColor(Color.RED);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            height=h;
            width=w;
        }

        public void onSensorEvent (SensorEvent event)
        {
            x=x-(int) event.values[0];
            y=y-(int) event.values[1];

            if(x <= 0+CIRCLE_RADIUS)
            {
                x=0+CIRCLE_RADIUS;
            }
            if(x >= width-CIRCLE_RADIUS)
            {
                x = width-CIRCLE_RADIUS;
            }
            if(y <= 0+CIRCLE_RADIUS)
            {
                y=0+CIRCLE_RADIUS;
            }
            if(y >= height-CIRCLE_RADIUS)
            {
                y = height-CIRCLE_RADIUS;
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(x,y,CIRCLE_RADIUS,paint);
            invalidate();
        }
    }
}