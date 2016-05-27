package com.example.android.flora;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Owner on 5/22/2016.
 */
public class receiver extends BroadcastReceiver {
        public receiver(){}
        @Override
        public void onReceive(Context context, Intent intent) {
            // This method is called when this BroadcastReceiver receives an Intent broadcast.
            Toast.makeText(context, "Action: " + intent.getAction(), Toast.LENGTH_SHORT).show();
        }
    }

