package com.akash.cp.vtu.vtupartb_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Base {
    TextView mTextView;
    ;
    private static final int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener();
    }

    @Override
    public void init() {
        checkAndRequestPermissions();
            mTextView = findViewById(R.id.msg);
        }

    @Override
    public void listener() {

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("msg")) {
                String message = intent.getStringExtra("message");
                Log.i("onReceive: ", message);
                String sender = intent.getStringExtra("Sender");
                mTextView.setText(sender+"\n"+message);


            }
        }
    };


    private boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            int receiveSMS = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS);
            int readSMS = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
            }
            if (readSMS != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.READ_SMS);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), request_code);
                return false;
            }
            return true;
        }
        return true;

    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("msg"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       // LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}