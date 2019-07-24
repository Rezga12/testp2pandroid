package assignment.first.rezga.testingwifip2p;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Date;

import assignment.first.rezga.testingwifip2p.model.Message;
import assignment.first.rezga.testingwifip2p.model.MessageRepository;

public class MainActivity extends AppCompatActivity {

    private final IntentFilter intentFilter = new IntentFilter();
    public static Context context;


    private WifiP2pManager.Channel channel;
    private WifiP2pManager manager;

    public static Context getContext(){
        return context;
    }


    public void printf(String test){
        Log.i("AAAA",test);
    }

    public static void showDebugDBAddressLogToast(Context context) {
        if (BuildConfig.DEBUG) {
            try {
                Class<?> debugDB = Class.forName("com.amitshekhar.DebugDB");
                Method getAddressLog = debugDB.getMethod("getAddressLog");
                Object value = getAddressLog.invoke(null);
                Toast.makeText(context, (String) value, Toast.LENGTH_LONG).show();
            } catch (Exception ignore) {

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showDebugDBAddressLogToast(this);

        context = getApplicationContext();
        MessageRepository repo = new MessageRepository();

        Message message = new Message();
        message.message = "123";
        message.sendTime = new Date(System.currentTimeMillis());
        message.peerAddress = "soyboy";

        //repo.insertMessage(message);

        repo.loadAllMessages("soyboy");

        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);



        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);

        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank. Code for peer discovery goes in the
                // onReceive method, detailed below.
            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
            }
        });

    }

    MyBroadcastReciever reciever;

    @Override
    protected void onResume() {

        reciever = new MyBroadcastReciever(manager, channel, this);
        registerReceiver(reciever, intentFilter);

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
