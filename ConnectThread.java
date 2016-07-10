//package com.example.android.flora;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.util.Log;
//
//import java.io.IOException;
//import java.util.UUID;
//
///**
// * Created by Owner on 7/9/2016.
// */
//public class ConnectThread extends Thread {
//
//    private final BluetoothSocket mmSocket;
//    private final BluetoothDevice mmDevice;
//    MainActivity device = new MainActivity();
//    private final UUID MY_UUID = device.getUUID();
//    private final BluetoothAdapter BA = device.getBA();
//
//    //in the ConnectThread class, we connect phone as a client
//    //A final bluetoothDevice and a final bluetoothSocket are members of this class
//    //in the constructor, make a bluetooth device the argument
//    // and create  a temporary BluetoothSocket as a placeholder, set it to NULL
//    //then try getting BSocket to connect with phone
//
//
//    public ConnectThread(BluetoothDevice device) {
//        // Use a temporary object that is later assigned to mmSocket,
//        // because mmSocket is final
//        BluetoothSocket tmp = null;
//        mmDevice = device;
//
//        // Get a BluetoothSocket to connect with the given BluetoothDevice
//        try {
//            // MY_UUID is the app's UUID string, also used by the server code
//            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
//        } catch (IOException e) { }
//        mmSocket = tmp;
//    }
//
//
//    public void run() {
//        // Cancel discovery because it will slow down the connection
//        BA.cancelDiscovery();
//
//        try {
//            // Connect the device through the socket. This will block
//            // until it succeeds or throws an exception
//            mmSocket.connect();
//            Log.i("connected","device connected");
//        } catch (IOException connectException) {
//            // Unable to connect; close the socket and get out
//            try {
//                mmSocket.close();
//            } catch (IOException closeException) { }
//            return;
//        }
//
//        // // Do work to manage the connection (in a separate thread)
//        // manageConnectedSocket(mmSocket);
//    }
//
//    /** Will cancel an in-progress connection, and close the socket */
//    public void cancel() {
//        try {
//            mmSocket.close();
//        } catch (IOException e) { }
//    }
//}
