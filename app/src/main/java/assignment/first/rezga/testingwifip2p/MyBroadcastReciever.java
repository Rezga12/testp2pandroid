package assignment.first.rezga.testingwifip2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MyBroadcastReciever extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    MainActivity a;
    public MyBroadcastReciever(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity){
        a = activity;
        this.manager = manager;
        this.channel = channel;
    }



    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            Toast.makeText(a,"State changed",Toast.LENGTH_SHORT).show();
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed! We should probably do something about
            // that.
            if (manager != null) {
                manager.requestPeers(channel, peerListListener);
            }
            Toast.makeText(a,"Peers Changed",Toast.LENGTH_SHORT).show();

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed! We should probably do something about
            // that.
            Toast.makeText(a,"Connection State changed",Toast.LENGTH_SHORT).show();

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            Toast.makeText(a,"This Device Changed",Toast.LENGTH_SHORT).show();


        }
    }

    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    boolean tr = false;
    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {

            Collection<WifiP2pDevice> refreshedPeers = peerList.getDeviceList();

           Toast.makeText(a,"we need peers " + refreshedPeers.size(),Toast.LENGTH_LONG).show();
            if(!tr)
            for(WifiP2pDevice device : refreshedPeers){


                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;

                manager.connect(channel, config, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        // WiFiDirectBroadcastReceiver notifies us. Ignore for now.
                        Toast.makeText(a, "success",
                                Toast.LENGTH_SHORT).show();
                        tr = true;
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(a, "Connect failed. Retry.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            }




        }
    };

}
