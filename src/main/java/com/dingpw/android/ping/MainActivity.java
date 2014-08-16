package com.dingpw.android.ping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import com.dingpw.android.setting.about.AboutView;


public class MainActivity extends Activity {

    private AlertDialog alertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(new PingView(this));
        this.alertDialog = new AlertDialog.Builder(this).create();

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);

        this.setTitle(this.getString(R.string.string_dping_local_address) + ip);

    }
    private String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(this.alertDialog.isShowing()){
            this.alertDialog.dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU){
            this.alertDialog.show();
            Window window = this.alertDialog.getWindow();
            window.setContentView(new AboutView(this));
        }
        return super.onKeyUp(keyCode, event);
    }
}
