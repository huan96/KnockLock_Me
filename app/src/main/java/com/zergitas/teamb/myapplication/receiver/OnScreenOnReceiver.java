package com.zergitas.teamb.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zergitas.teamb.myapplication.service.LockScreenService;

/**
 * Created by huand on 8/11/2017.
 */

public class OnScreenOnReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, LockScreenService.class);
        context.startService(intent);
    }
}
