package com.example.android.flora;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends Activity  {

    private static final int REQUEST_ENABLE_BT = 1001;

    Button b1,b2,b3,b4,b5;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices; //list of bluetooth devices that cannot have duplicates
    private Set<BluetoothDevice> discoveredDevices;
    protected ArrayAdapter mArrayAdapter;
    ListView lv;

    protected ArrayAdapter list_discoveries;

     BroadcastReceiver mReceiver;

    public TextView discovery_txt;// (TextView) findViewById(R.id.textView2);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        discovery_txt = (TextView) findViewById(R.id.textView2);

        b1 = (Button) findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5); //discoveries


        BA = BluetoothAdapter.getDefaultAdapter();
        lv = (ListView)findViewById(R.id.listView);

        if (BA == null) {
            // Device does not support Bluetooth
            Toast.makeText(MainActivity.this,
                    "Bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();
            finish();
        }

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                        Toast.LENGTH_SHORT).show();

            }

        });


    }




    public void on(View v){ //when the turn on button is clicked, enable bluetooth
        b3.setVisibility(View.VISIBLE);
        b5.setVisibility(View.VISIBLE);
        lv.setVisibility(View.VISIBLE);
        if (!BA.isEnabled()) {

            //Intent needed so can use action_request_enable constant asks the user for permission
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0); //need this to use the intent
            Toast.makeText(getApplicationContext(),"Turned on",Toast.LENGTH_LONG).show();
        }
        else //if it's already on
        {
            Toast.makeText(getApplicationContext(),"Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void off(View v){
        b3.setVisibility(View.INVISIBLE);
        b5.setVisibility(View.INVISIBLE);
        lv.setVisibility(View.INVISIBLE);


        if(BA.isEnabled()) {
            BA.disable();
            Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
            lv.setAdapter(null);
            BA.cancelDiscovery();


        }

        else //if it's already off
        {
            Toast.makeText(getApplicationContext(),"Already off",Toast.LENGTH_LONG).show();
        }


    }

    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }

    public void list(View v){ //corresponds to the b3, "List devices", button

        //Before performing device discovery, its worth querying the set of paired devices
        // to see if the desired device is already known.
        // To do so, call getBondedDevices().
        // This will return a Set of BluetoothDevices representing paired devices. For example,
        // you can query all paired devices and then show the name of each device to the user, using an ArrayAdapter:
       //Remember there is a difference between being paired and being connected.
        //To be paired means that two devices are aware of each other's existence, have a shared link-key that can be used for authentication,
        // and are capable of establishing an encrypted connection with each other.
        // To be connected means that the devices currently share an RFCOMM channel and are able to transmit data with each other

        pairedDevices = BA.getBondedDevices(); //get set of devices

        mArrayAdapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1);



        //TextView discovery_txt = (TextView) findViewById(R.id.textView2);
        discovery_txt.setText("Paired Devices: ");


        for(BluetoothDevice x : pairedDevices) //goes through the entire set of paired devices
            mArrayAdapter.add(x.getName() + "\n" + x.getAddress()); //BluetoothDevice method to get the name of the paired device
        Toast.makeText(getApplicationContext(),"Showing Paired Devices",Toast.LENGTH_SHORT).show();

        lv.setAdapter(mArrayAdapter);


        //keep in mind there's a difference between list of discoverables vs list of paired





    }


    public void scan (View view){

        BA.startDiscovery();

        list_discoveries = new ArrayAdapter(this,android.R.layout.simple_list_item_1);



        Toast.makeText(getApplicationContext(), "List of Available Devices", Toast.LENGTH_SHORT).show();

        // Create a BroadcastReceiver for ACTION_FOUND
        final BroadcastReceiver
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Toast.makeText(MainActivity.this, "Entered broadcast", Toast.LENGTH_SHORT).show();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    Toast.makeText(MainActivity.this, "Action Found", Toast.LENGTH_SHORT).show();
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    list_discoveries.add(device.getName() + "\n" + device.getAddress());
                    Toast.makeText(MainActivity.this, device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "What is going on?", Toast.LENGTH_SHORT).show();
                }


            }
        };

        Toast.makeText(MainActivity.this, "after broacastreceiver", Toast.LENGTH_SHORT).show();
// Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);


        Toast.makeText(MainActivity.this, "register", Toast.LENGTH_SHORT).show();


        discovery_txt.setText("Available Devices: ");

       // final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list_discoveries);
        lv.setAdapter(list_discoveries);






    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        BA.cancelDiscovery();
        unregisterReceiver(mReceiver);
        Toast.makeText(MainActivity.this, "Broadcast Receiver temporarily unregistered!", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onPause()
    {
        super.onPause();

        //unregisterReceiver(mReceiver);
        Toast.makeText(MainActivity.this, "Broadcast Receiver temporarily unregistered!", Toast.LENGTH_LONG).show();
    }
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//
//
//    }
}