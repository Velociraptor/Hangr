package com.example.hannah.rfduinotest;


import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private String mBluetoothDeviceAddress;
    private String mConnectionState = STATE_DISCONNECTED;
    private BluetoothManager mBluetoothManager2;
    private BluetoothAdapter mBluetoothAdapter2;
    private BluetoothGatt mBluetoothGatt2;
    private String mBluetoothDeviceAddress2;
    private String mConnectionState2 = STATE_DISCONNECTED;

    private static final String STATE_DISCONNECTED = "Disconnected";
    private static final String STATE_CONNECTING = "Connecting...";
    private static final String STATE_CONNECTED = "Connected";
    private static final String UPDATE_GUI_INTENT = "ble.test.update.GUI";

    public static final UUID RECEIVE_CHARACTERISTIC_UUID = UUID.fromString("00002221-0000-1000-8000-00805F9B34FB");
    public static final UUID SEND_CHARACTERISTIC_UUID = UUID.fromString("00002222-0000-1000-8000-00805F9B34FB");
    public static final UUID RFDUINO_SERVICE_UUID = UUID.fromString("00002220-0000-1000-8000-00805F9B34FB");
    public final static UUID CLIENT_CHARACTERISTIC_CONFIGURATION_UUID = UUID.fromString("00002902-0000-1000-8000-00805F9B34FB");

    Context context;

    private TextView textConnectionStatus;
    private TextView textConnectionStatus2;
    private Button btnConnect;
    private Button btnDisconnect;
    private Button btnOn;
    private Button btnOff;
    private Button btn2Connect;
    private Button btn2Disconnect;
    private Button btnOn2;
    private Button btnOff2;
    private Button btnBothOn;
    private Button btnSwitch;

    private String Item2MAC = "F7:22:01:7C:1C:CC";
    private String Item1MAC = "CE:FF:39:4D:20:C5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_GUI_INTENT);
        registerReceiver(receiver, filter);
        registerReceiver(receiver2, filter);

        textConnectionStatus = (TextView) findViewById(R.id.textConnectionStatus);
        textConnectionStatus2 = (TextView) findViewById(R.id.textConnectionStatus2);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnDisconnect = (Button) findViewById(R.id.btnDisconnect);
        btn2Connect = (Button) findViewById(R.id.btnConnect2);
        btn2Disconnect = (Button) findViewById(R.id.btnDisconnect2);
        btnOn = (Button) findViewById(R.id.btnOn);
        btnOff = (Button) findViewById(R.id.btnOff);
        btnOn2 = (Button) findViewById(R.id.btn2On);
        btnOff2 = (Button) findViewById(R.id.btn2Off);
        btnBothOn = (Button) findViewById(R.id.btnBothOn);
        btnSwitch = (Button) findViewById(R.id.btnSwitchScreens);

        mBluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothManager2 =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter2 = mBluetoothManager2.getAdapter();

        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connect(Item1MAC);
            }
        });
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothGatt.disconnect();
            }
        });
        btn2Connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connect2(Item2MAC);
            }
        });
        btn2Disconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBluetoothGatt2.disconnect();
            }

        });
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            // switch back to initial "empty" screen
            public void onClick(View v) {
                ImageView img = (ImageView) findViewById(R.id.backgroundImg);
                img.setImageResource(R.drawable.mainemptysmall);
            }

        });
        btnOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.w(TAG,"Turning on Blue for Item 1");
                List<BluetoothGattService> bgsList = mBluetoothGatt.getServices();
                Log.w(TAG,"List size 1: "+ bgsList.size());
                for(int i = 0; i < bgsList.size();i++)
                {
                    Log.w(TAG,"Service UUID 1: "+bgsList.get(i).getUuid().toString());
                    List<BluetoothGattCharacteristic> bgcList = bgsList.get(i).getCharacteristics();

                    for(int j = 0; j < bgcList.size();j++)
                    {
                        Log.w(TAG,"Characteristic UUID 1: "+bgcList.get(j).getUuid().toString());
                        if(bgcList.get(j).getUuid().equals(SEND_CHARACTERISTIC_UUID))
                        {
                            byte[] value = new byte[3];
                            int high = 1;
                            value[0] = (byte) (high & 0xFF);
                            Log.w(TAG,"Writing val 1 to characteristic 1");
                            writeDataToCharacteristic(bgcList.get(j), value);
                        }
                    }
                }
            }
        });
        btnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.w(TAG,"Turning off Blue for Item 1");
                List<BluetoothGattService> bgsList = mBluetoothGatt.getServices();
                Log.w(TAG,"List size 1: "+ bgsList.size());
                for(int i = 0; i < bgsList.size();i++)
                {
                    Log.w(TAG,"Service UUID 1: "+bgsList.get(i).getUuid().toString());
                    List<BluetoothGattCharacteristic> bgcList = bgsList.get(i).getCharacteristics();

                    for(int j = 0; j < bgcList.size();j++)
                    {
                        Log.w(TAG,"Characteristic UUID 1: "+bgcList.get(j).getUuid().toString());
                        if(bgcList.get(j).getUuid().equals(SEND_CHARACTERISTIC_UUID))
                        {
                            byte[] value = new byte[3];
                            int low = 0;
                            value[0] = (byte) (low & 0xFF);
                            Log.w(TAG,"Writing val 0 to characteristic 1");
                            writeDataToCharacteristic(bgcList.get(j), value);
                        }
                    }
                }
            }
        });
        btnOn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.w(TAG,"Turning on Blue for Item 2");
                List<BluetoothGattService> bgsList = mBluetoothGatt2.getServices();
                Log.w(TAG,"List size 2: "+ bgsList.size());
                for(int i = 0; i < bgsList.size();i++)
                {
                    Log.w(TAG,"Service UUID 2: "+bgsList.get(i).getUuid().toString());
                    List<BluetoothGattCharacteristic> bgcList = bgsList.get(i).getCharacteristics();

                    for(int j = 0; j < bgcList.size();j++)
                    {
                        Log.w(TAG,"Characteristic UUID 2: "+bgcList.get(j).getUuid().toString());
                        if(bgcList.get(j).getUuid().equals(SEND_CHARACTERISTIC_UUID))
                        {
                            byte[] value = new byte[3];
                            int high = 1;
                            value[0] = (byte) (high & 0xFF);
                            Log.w(TAG,"Writing val 1 to characteristic 2");
                            writeDataToCharacteristic2(bgcList.get(j), value);
                        }
                    }
                }
            }
        });
        btnOff2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.w(TAG,"Turning off Blue for Item 2");
                List<BluetoothGattService> bgsList = mBluetoothGatt2.getServices();
                Log.w(TAG,"List size 2: "+ bgsList.size());
                for(int i = 0; i < bgsList.size();i++)
                {
                    Log.w(TAG,"Service UUID 2: "+bgsList.get(i).getUuid().toString());
                    List<BluetoothGattCharacteristic> bgcList = bgsList.get(i).getCharacteristics();

                    for(int j = 0; j < bgcList.size();j++)
                    {
                        Log.w(TAG,"Characteristic UUID: "+bgcList.get(j).getUuid().toString());
                        if(bgcList.get(j).getUuid().equals(SEND_CHARACTERISTIC_UUID))
                        {
                            byte[] value = new byte[3];
                            int low = 0;
                            value[0] = (byte) (low & 0xFF);
                            Log.w(TAG,"Writing val 0 to characteristic 2");
                            writeDataToCharacteristic2(bgcList.get(j), value);
                        }
                    }
                }
            }
        });
        btnBothOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.w(TAG,"Turn on both LEDs to Red");
                if (mBluetoothGatt != null) {
                    List<BluetoothGattService> bgsList = mBluetoothGatt.getServices();
                    Log.w(TAG, "List size 1: " + bgsList.size());
                    for (int i = 0; i < bgsList.size(); i++) {
                        Log.w(TAG, "Service UUID 1: " + bgsList.get(i).getUuid().toString());
                        List<BluetoothGattCharacteristic> bgcList = bgsList.get(i).getCharacteristics();

                        for (int j = 0; j < bgcList.size(); j++) {
                            Log.w(TAG, "Characteristic UUID 1: " + bgcList.get(j).getUuid().toString());
                            if(bgcList.get(j).getUuid().equals(SEND_CHARACTERISTIC_UUID)) {
                                byte[] value = new byte[3];
                                int sendit = 4;
                                value[0] = (byte) (sendit & 0xFF);
                                Log.w(TAG, "Writing Red to Item1 LED");
                                writeDataToCharacteristic(bgcList.get(j), value);
                            }
                        }
                    }
                }
                Log.v(TAG,"Pausing then sending Item2 LED Red");
                try {
                    synchronized(this){
                        wait(100);
                    }
                }
                catch(InterruptedException ex){
                    Log.e(TAG,"Interrupted pausing before second red");
                }
                if (mBluetoothGatt2 != null) {
                    List<BluetoothGattService> bgsList2 = mBluetoothGatt2.getServices();
                    Log.w(TAG, "List size 2: " + bgsList2.size());
                    for (int i = 0; i < bgsList2.size(); i++) {
                        Log.w(TAG, "Service UUID 2: " + bgsList2.get(i).getUuid().toString());
                        List<BluetoothGattCharacteristic> bgcList2 = bgsList2.get(i).getCharacteristics();

                        for (int j = 0; j < bgcList2.size(); j++) {
                            Log.w(TAG, "Characteristic UUID 2: " + bgcList2.get(j).getUuid().toString());
                            if(bgcList2.get(j).getUuid().equals(SEND_CHARACTERISTIC_UUID)) {
                                byte[] value2 = new byte[3];
                                int sendit = 4;
                                value2[0] = (byte) (sendit & 0xFF);
                                Log.w(TAG, "Writing Red to Item2 LED");
                                writeDataToCharacteristic2(bgcList2.get(j), value2);
                            }
                        }
                    }
                }
            }
        });
    }


    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mConnectionState = STATE_CONNECTED;
                context = getApplicationContext();
                Intent i = new Intent();
                i.setAction(UPDATE_GUI_INTENT);
                context.sendBroadcast(i);
                mBluetoothGatt.discoverServices();

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mConnectionState = STATE_DISCONNECTED;
                context = getApplicationContext();
                Intent i = new Intent();
                i.setAction(UPDATE_GUI_INTENT);
                context.sendBroadcast(i);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "onServicesDiscovered 1 success");
            } else {
                Log.w(TAG, "onServicesDiscovered 1 received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.w(TAG,"Characteristic Read Item1");
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            Log.d(TAG, "Characteristic Changed 1");
        }
    };

    private final BluetoothGattCallback mGattCallback2 = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mConnectionState2 = STATE_CONNECTED;
                context = getApplicationContext();
                Intent i = new Intent();
                i.setAction(UPDATE_GUI_INTENT);
                context.sendBroadcast(i);
                mBluetoothGatt2.discoverServices();

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mConnectionState2 = STATE_DISCONNECTED;
                context = getApplicationContext();
                Intent i = new Intent();
                i.setAction(UPDATE_GUI_INTENT);
                context.sendBroadcast(i);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.w(TAG, "onServicesDiscovered 2 success");
            } else {
                Log.w(TAG, "onServicesDiscovered 2 received: " + status);
            }

            Log.d(TAG, "gatt 2 " + gatt);
            Log.d(TAG, "status 2 " + status);
            super.onServicesDiscovered(gatt, status);

            BluetoothGattService service = gatt.getService(RFDUINO_SERVICE_UUID);
            Log.d(TAG, "service 2 " + service);

            BluetoothGattCharacteristic receiveCharacteristic = service.getCharacteristic(RECEIVE_CHARACTERISTIC_UUID);

            if (receiveCharacteristic != null) {
                gatt.setCharacteristicNotification(receiveCharacteristic, true);

                BluetoothGattDescriptor receiveConfigDescriptor = receiveCharacteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIGURATION_UUID);
                if (receiveConfigDescriptor != null) {
                    receiveConfigDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    gatt.writeDescriptor(receiveConfigDescriptor);
                    Log.d(TAG, "Registering read characteristic to receive Bluetooth signals for button press.");
                } else {
                    Log.e(TAG, "Receive Characteristic 2 can not be configured.");
                }
            } else {
                Log.e(TAG, "Receive Characteristic 2 is missing.");
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            Log.d(TAG,"Characteristic Read 2");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            Log.d(TAG, "Characteristic Changed 2");
            if (characteristic.getUuid().equals(RECEIVE_CHARACTERISTIC_UUID)) {
                byte val;
                val = characteristic.getValue()[0];
                Log.d(TAG, "Callback 2 received (byte 0): " + val);
                // Turn off this item's LED (happens in Arduino code) and send other a color change
                Log.w(TAG,"Sending Green to Item 1");
                if (mBluetoothGatt != null) {
                    List<BluetoothGattService> bgsList = mBluetoothGatt.getServices();
                    Log.w(TAG, "List size (1): " + bgsList.size());
                    for (int i = 0; i < bgsList.size(); i++) {
                        Log.w(TAG, "Service UUID (1): " + bgsList.get(i).getUuid().toString());
                        List<BluetoothGattCharacteristic> bgcList = bgsList.get(i).getCharacteristics();

                        for (int j = 0; j < bgcList.size(); j++) {
                            Log.w(TAG, "Characteristic UUID (1): " + bgcList.get(j).getUuid().toString());
                            if (bgcList.get(j).getUuid().equals(SEND_CHARACTERISTIC_UUID)) {
                                byte[] value = new byte[3];
                                int high = 3;
                                value[0] = (byte) (high & 0xFF);

                                Log.w(TAG, "Writing val 3 to characteristic 1 (Set Item 1 LED to Green)");
                                writeDataToCharacteristic(bgcList.get(j), value);
                            }
                        }

                    }
                } else {
                    Log.e(TAG,"Cannot change Item 1 color - not connected");
                }
                // change screen to "full" as item 2 has been selected
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                    Log.d(TAG, "Changing bg image to full");
                    ImageView img = (ImageView) findViewById(R.id.backgroundImg);
                    img.setImageResource(R.drawable.mainfullsmall);
                    }
                });
            }
        }
    };

    public void writeDataToCharacteristic(final BluetoothGattCharacteristic ch, final byte[] dataToWrite) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null || ch == null) {
            Log.e(TAG, "Problem writing data 1, missing adapter or gatt");
            return;
        }
        Log.w(TAG,"Writing to Item 1");
        ch.setValue(dataToWrite);

        if(mBluetoothGatt.writeCharacteristic(ch))
        {
            Log.w(TAG,"Write 1 success");
        }
        else
        {
            Log.w(TAG,"Write 1 failure");
        }

    }

    public void writeDataToCharacteristic2(final BluetoothGattCharacteristic ch, final byte[] dataToWrite) {
        if (mBluetoothAdapter2 == null || mBluetoothGatt2 == null || ch == null) {
            Log.e(TAG,"Problem writing data 2, missing adapter or gatt");
            return;
        }
        Log.w(TAG,"Writing to Item 2");
        ch.setValue(dataToWrite);

        if(mBluetoothGatt2.writeCharacteristic(ch))
        {
            Log.w(TAG,"Write 2 success");
        }
        else
        {
            Log.w(TAG,"Write 2 failure");
        }

    }

    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter 1 not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection 1.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                context = getApplicationContext();
                Intent i = new Intent();
                i.setAction(UPDATE_GUI_INTENT);
                context.sendBroadcast(i);
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect 1.");
            return false;
        }

        mBluetoothGatt = device.connectGatt(this, true, mGattCallback);
        Log.d(TAG, "Trying to create a new connection 1.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        context = getApplicationContext();
        Intent i = new Intent();
        i.setAction(UPDATE_GUI_INTENT);
        context.sendBroadcast(i);
        return true;
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(UPDATE_GUI_INTENT)) {
                Log.w(TAG,"Broadcast Receiver 1 Intent");
                textConnectionStatus.setText(mConnectionState);
            }
        }
    };

    public boolean connect2(final String address) {
        if (mBluetoothAdapter2 == null || address == null) {
            Log.w(TAG, "BluetoothAdapter 2 not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress2 != null && address.equals(mBluetoothDeviceAddress2)
                && mBluetoothGatt2 != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection 2.");
            if (mBluetoothGatt2.connect()) {
                mConnectionState2 = STATE_CONNECTING;
                context = getApplicationContext();
                Intent i = new Intent();
                i.setAction(UPDATE_GUI_INTENT);
                context.sendBroadcast(i);
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter2.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect 2.");
            return false;
        }
        mBluetoothGatt2 = device.connectGatt(this, true, mGattCallback2);
        Log.d(TAG, "Trying to create a new connection 2.");
        mBluetoothDeviceAddress2 = address;
        mConnectionState2 = STATE_CONNECTING;
        context = getApplicationContext();
        Intent i = new Intent();
        i.setAction(UPDATE_GUI_INTENT);
        context.sendBroadcast(i);
        return true;
    }
    private BroadcastReceiver receiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(UPDATE_GUI_INTENT)) {
                Log.w(TAG,"Broadcast Receiver 2 Intent");
                textConnectionStatus2.setText(mConnectionState2);
            }
        }
    };

}
