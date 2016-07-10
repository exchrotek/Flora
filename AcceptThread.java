//package com.example.android.flora;
//
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//
//import java.io.IOException;
//import java.util.UUID;
//
///**
// * Created by Owner on 7/9/2016.
// */
//public class AcceptThread {
//    private final BluetoothServerSocket mmServerSocket;
//
//    private final String NAME = "SGH-M919";
//    MainActivity device = new MainActivity();
//
//    private final UUID MY_UUID = device.getUUID();
//    private final BluetoothAdapter BA = device.getBA();
//
//
//    public AcceptThread() {
//        // Use a temporary object that is later assigned to mmServerSocket,
//        // because mmServerSocket is final
//        BluetoothServerSocket tmp = null;
//        try {
//            // MY_UUID is the app's UUID string, also used by the client code
//            // Get a BluetoothServerSocket
//            tmp = BA.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
//        } catch (IOException e) { }
//        mmServerSocket = tmp;
//    }
//
//    public void run() {
//        BluetoothSocket socket = null;
//        // Keep listening until exception occurs or a socket is returned
//        while (true) {
//            try {
//                socket = mmServerSocket.accept();
//            } catch (IOException e) {
//                break;
//            }
//            // If a connection was accepted
//            if (socket != null) {
//                // Do work to manage the connection (in a separate thread)
//                //manageConnectedSocket(socket);
//                try {
//                    mmServerSocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//        }
//    }
//
//    /** Will cancel the listening socket, and cause the thread to finish */
//    public void cancel() {
//        try {
//            mmServerSocket.close();
//        } catch (IOException e) { }
//    }
//
//
//}
