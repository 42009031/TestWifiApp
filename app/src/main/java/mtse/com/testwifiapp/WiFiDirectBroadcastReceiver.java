package mtse.com.testwifiapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Dawoud on 1/16/2017.
 */

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager ;
    private WifiP2pManager.Channel mChannel ;
    private MainActivity activity ;
    private List<WifiP2pDevice> mpeers ;
    private List<WifiP2pConfig> mConfigs;


    public WiFiDirectBroadcastReceiver(WifiP2pManager mManager,  WifiP2pManager.Channel mChannel, MainActivity activity ){
        this.mManager  =  mManager;
        this.mChannel  =  mChannel;
        this.activity  =  activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE , -1);
            if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                activity.mtextView.setText("WiFi Direct : Enabled");
            }else{
                activity.mtextView.setText("WiFi Direct : Disabled");
            }
        } else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){
            mpeers = new ArrayList<WifiP2pDevice>();
            mConfigs = new ArrayList<WifiP2pConfig>();

            if(mManager != null){
                WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                        mpeers.clear();
                        mpeers.addAll(wifiP2pDeviceList.getDeviceList());

                        activity.dispayPeers(wifiP2pDeviceList);

//                        mpeers.addAll(wifiP2pDeviceList.getDeviceList());

                        for (int i= 0 ; i<wifiP2pDeviceList.getDeviceList().size() ; i++){
                            WifiP2pConfig config = new WifiP2pConfig() ;
                            config.deviceAddress = mpeers.get(i).deviceAddress;
                            mConfigs.add(config);
                        }



                    }
                };

                mManager.requestPeers(mChannel, peerListListener);
            }
        }else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){

        }else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){

        }
    }
}
