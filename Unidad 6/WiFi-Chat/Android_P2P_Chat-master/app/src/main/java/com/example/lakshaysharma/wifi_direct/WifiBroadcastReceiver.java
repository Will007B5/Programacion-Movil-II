package com.example.lakshaysharma.wifi_direct;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Button;
import android.widget.Toast;

public class WifiBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private MainActivity mainActivity;
    //private Button btn;
    public boolean isWifiActive;

    public boolean isWifiActive() {
        return isWifiActive;
    }

    public void setWifiActive(boolean wifiActive) {
        isWifiActive = wifiActive;
    }

    public WifiBroadcastReceiver(WifiP2pManager wifiP2pManager, WifiP2pManager.Channel channel, MainActivity mainActivity) {
        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action.equals(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)){

            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

            if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                Toast.makeText(context, "WiFi Encendido", Toast.LENGTH_SHORT).show();
                setWifiActive(true);
                //btn.setText("Apagar WiFi");
                //mainActivity.setBtnOnOff(btn);
            }
            else {
                Toast.makeText(context, "WiFi Apagado", Toast.LENGTH_SHORT).show();
                setWifiActive(false);
                //btn.setText("Encender WiFi");
                //mainActivity.setBtnOnOff(btn);
            }
        }
        else if (action.equals(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)){

            if (wifiP2pManager != null){
                wifiP2pManager.requestPeers(channel, mainActivity.peerListListener);
            }
        }
        else if (action.equals(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)){

            if(wifiP2pManager == null){
                return;
            }

            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()){
                wifiP2pManager.requestConnectionInfo(channel, mainActivity.connectionInfoListener);
            }
            else {
                mainActivity.connectionStatus.setText("Dispositivo desconectado");
            }
        }
        else if (action.equals(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)){

        }
    }
}
