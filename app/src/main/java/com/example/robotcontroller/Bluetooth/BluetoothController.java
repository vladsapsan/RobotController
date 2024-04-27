package com.example.robotcontroller.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class BluetoothController {
    BluetoothAdapter adapter;
    BluetoothDevice device;
    public ConnectThread connectThread = null;
    public BluetoothController(BluetoothAdapter adapter){
        this.adapter = adapter;
    }
    public static final String BLUETOOTH_CONNECTED = "Блютуз подключен";
    public static final String BLUETOOTH_NO_CONNECTED = "Блютуз не подключен";
    public void Connect(String MacAdress,Listner listner){
        if(adapter.isEnabled()&&!MacAdress.isEmpty()) {
            device = adapter.getRemoteDevice(MacAdress);
            connectThread = new ConnectThread(device,listner);
            connectThread.start();
        }
    }


    public void SendCommand(byte[] message){
        connectThread.SendCommand(message);
    }
    public void CloseConnection(){
        connectThread.CloseConnect();
    }

    public interface Listner{
        void onReceive(byte[] message);
    }
}
