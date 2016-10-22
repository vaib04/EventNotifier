package com.example.vbelwal94.eventnotifier;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button mButton,mCreateEvent;
    TextView mTextView;
    private int CAMERA_PERMISSION_CODE=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton= (Button) findViewById(R.id.button);
        mCreateEvent= (Button) findViewById(R.id.event);
        mTextView= (TextView) findViewById(R.id.data);
        mCreateEvent.setEnabled(false);
        Bundle extras=getIntent().getExtras();
        if(extras != null) {
            final String input=extras.getString("data","null");
            mTextView.setText(input);
            if(!input.equals("null")) {
                mCreateEvent.setEnabled(true);
            }
            mCreateEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createEvent(input);
                    //mTextView.setText("");
                }
            });
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if(isCameraAllowed()) {
                        Toast.makeText(MainActivity.this, "Camera Allowed", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,ScanUsingCamera.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        MainActivity.this.startActivity(intent);
                    }
                    requestCameraPermission();
                }
                else{
                    Intent intent=new Intent(MainActivity.this,ScanUsingCamera.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    MainActivity.this.startActivity(intent);
                }
            }

        });
    }
    private boolean isCameraAllowed() {
        int result=ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA);
        if(result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    private void requestCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA)) {
            Toast.makeText(MainActivity.this, "Camera permission is necessary", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);

    }
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if(requestCode==CAMERA_PERMISSION_CODE) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Camera permission now granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "App wont work without camera permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void createEvent(String input) {

        try {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType("vnd.android.cursor.item/event");
            String[] output=input.split(",");
            final int sYear=Integer.parseInt(output[2]);
            final int sMonth=Integer.parseInt(output[3]);
            final int sDay=Integer.parseInt(output[4]);
            final int sHrs=Integer.parseInt(output[5]);
            final int sMin=Integer.parseInt(output[6]);
            final int eYear=Integer.parseInt(output[7]);
            final int eMonth=Integer.parseInt(output[8]);
            final int eDay=Integer.parseInt(output[9]);
            final int eHrs=Integer.parseInt(output[10]);
            final int eMin=Integer.parseInt(output[11]);
            Calendar beginCal = Calendar.getInstance();
            beginCal.set(sYear,sMonth,sDay,sHrs,sMin);
            long startTime = beginCal.getTimeInMillis();
            Calendar endCal = Calendar.getInstance();
            endCal.set(eYear,eMonth,eDay,eHrs,eMin);
            long endTime = endCal.getTimeInMillis();

            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);
            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

            intent.putExtra(CalendarContract.Events.TITLE, output[0]);
            intent.putExtra(CalendarContract.Events.DESCRIPTION, output[12]);
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, output[1]);

            startActivity(intent);
        }catch(Exception e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("ScanUsingCamera","CalendarEvent: "+e.toString());
        }
    }

}
