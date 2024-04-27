package com.example.robotcontroller.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectThread extends Thread{

    private String UUID = "00001101-0000-1000-8000-00805F9B34FB";
    BluetoothController.Listner listner;
    private BluetoothSocket mSocket = null;
    public static Boolean IsConnected =false;
    private InputStream mInputStream;
    private OutputStream mOutputStream;

    ConnectThread(BluetoothDevice device, BluetoothController.Listner listner){
        try {
            //Создание канал передачи данных
            mSocket = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
        }catch (SecurityException e){} catch (IOException e) {
        }
        this.listner = listner;
    }

    @Override
    public void run() {
        super.run();
        try {
            if(mSocket!=null) {
                Log.d("BTConnect", "Connected...");
                //Запуск канал передачи данных
                mSocket.connect();

                //Определяем потоки входящий и выходящий
                mInputStream = mSocket.getInputStream();
                mOutputStream = mSocket.getOutputStream();
                IsConnected=true;

            }
        }catch (SecurityException e){
            Log.d("BTConnect", e.toString());
        } catch (IOException e) {
            Log.d("BTConnect", e.toString());
        }
    }

    public void SendCommand(byte[] message){
        try {
            mSocket.getOutputStream().write(message);
        }catch (IOException e){
            Log.d("BTConnect", e.toString());
        }
    }

    public void CloseConnect(){
        try {
            if(mSocket!=null) {
                mSocket.close();
            }
        }catch (SecurityException e){} catch (IOException e) {
            Log.e("BtConnection", "Ошибка при закрытии BluetoothSocket: " + e.getMessage());
        }
    }
}
