package com.example.robotcontroller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

import com.example.robotcontroller.Bluetooth.BluetoothController;
import com.example.robotcontroller.Bluetooth.BtState;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;

public class MainViewModel extends ViewModel implements BluetoothController.Listner{
    public MainViewModel(){
    }
    BtState btState;
    BluetoothAdapter bluetoothAdapter;
    BluetoothController bluetoothController;
    int REQUEST_CODE_PERMISSION_BLUETOOTH_CONNECT;

    protected void InitBtComponents(MainActivity activity){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothController = new BluetoothController(bluetoothAdapter);
        PermissionCheck(activity);
        CheckBtState();
    }
    protected BtState getBtState(){
        if(btState!=null) {
            return btState;
        }
        return null;
    }
    protected void CheckBtState(){
        if (bluetoothAdapter == null) {
            //Устройство не поддерживает блютуз???
            btState = btState.BT_NoFound;
        } else if (!bluetoothAdapter.isEnabled()) {
            btState = btState.BT_Disable;
        } else {
            //блютуз включен
            btState = btState.BT_Enable;
        }
        if(getPairedDevice()!=null){
            ConnectPairedModule(getPairedDevice());
        }
    }
    protected void ConnectPairedModule(BluetoothDevice device){
        //Создание потока соединения с уже сопряженным устройством
        bluetoothController = new BluetoothController(bluetoothAdapter);
        bluetoothController.Connect(device.getAddress(),this);
    }
    @SuppressLint("MissingPermission")
    protected BluetoothDevice getPairedDevice(){
        @SuppressLint("MissingPermission") Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        //Проверяем среди сопряженных устройств, коленные модули steplife и выводим их отдельным списком
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals("HC-06")) {
                return device;
            }
        }
        return null;
    }

    public void SendCommand(String string){
        if(bluetoothController.connectThread!=null) {
            bluetoothController.SendCommand(string.getBytes(StandardCharsets.UTF_8));
        }
    }

    private boolean PermissionCheck(MainActivity activity) {
        boolean BLUETOOTH_CONNECT = false;
        int permissionStatus = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.BLUETOOTH_CONNECT);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            //Доступ есть к устройства поблизости
            BLUETOOTH_CONNECT = true;
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_CODE_PERMISSION_BLUETOOTH_CONNECT);
        }

        return BLUETOOTH_CONNECT;
    }

    @Override
    public void onReceive(byte[] message) {
        Log.d("BTBoard", Arrays.toString(message));
    }
}
