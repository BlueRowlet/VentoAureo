package com.figer.game;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.widget.Toast;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.figer.game.GUI.Button;
import com.figer.game.GUI.List;
import com.figer.game.GUI.Signal;
import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

import java.util.ArrayList;
import java.util.UUID;

public class GameMain extends ApplicationAdapter{
	//Toast.makeText(context, "", duration).show();
	private int duration = Toast.LENGTH_SHORT;
	private Context context;

    public GameMain(Context context) {
        this.context = context;
    }

	/**				Bluetooth Magic				*/
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothConnection mBluetoothConnection;
	private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
	private BluetoothDevice mBTDevice;
	private ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
	private String connectedDevice;
    private List deviceAddresses;

	/**				GUI Magic					*/
	// System
	private Renderer renderer;
	private Input input;

	// Main Menu
	private Button btnOn;
	private Button btnDiscover;
	private Button btnSearch;
	private Button btnConnect;
	private List deviceList;

	@Override
	public void create () {
		//Bluetooth Magic
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		context.registerReceiver(mBroadcastReceiver4, filter);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        deviceAddresses = new List(30);

		//GUI Elements
		renderer = new Renderer();
		input = new Input(renderer.getViewport());

		btnOn = new Button(25, 25, 200, 50, "On/Off");
		btnDiscover = new Button(25, 90, 200, 50, "Enable Discoverability");
		btnSearch = new Button(25, 155, 200, 50, "Search");
		btnConnect = new Button(25, 210, 200, 50, "Connect");

		deviceList = new List(300, 25, 200, 30);
	}

	private void draw() {
		// Draw
		btnOn.draw(renderer);
		btnDiscover.draw(renderer);
		btnSearch.draw(renderer);
		btnConnect.draw(renderer);
		deviceList.draw(renderer);
	}

	private void update() {
		// Update
		btnOn.update(input);
		btnDiscover.update(input);
		btnSearch.update(input);
		btnConnect.update(input);
		deviceList.update(input);

		// Consume signals
		if (btnOn.consumeSignal() != Signal.NULL) {
			enableDisableBT();
		}
		if (btnDiscover.consumeSignal() != Signal.NULL) {
			discoverAbility();
		}
		if (btnSearch.consumeSignal() != Signal.NULL) {
			discover();
		}
		if (btnConnect.consumeSignal() != Signal.NULL) {
			startConnection();
		}
		if (deviceList.consumeSignal() != Signal.NULL) {
			connectedDevice = deviceAddresses.getElementByIndex(deviceList.getSelectedIndex());
			System.out.println(connectedDevice);
		}
	}

	@Override
	public void dispose () {
//Destroy Receivers
		context.unregisterReceiver(mBroadcastReceiver1);
		context.unregisterReceiver(mBroadcastReceiver2);
		context.unregisterReceiver(mBroadcastReceiver3);
		context.unregisterReceiver(mBroadcastReceiver4);
//Destroy GUI elements
		renderer.dispose();
	}

	@Override
	public void resize(int w, int h) {
		renderer.resize(w, h);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Drawing
		renderer.prepare();
		renderer.begin();
		draw(); // The main draw method!
		renderer.end();

		// Updating
		update(); // The main update method!
		input.update();
	}

	//				BroadcastReceivers				//
	//ON/OFF
	private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// When discovery finds a device
			if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

				switch(state){
					case BluetoothAdapter.STATE_OFF:
						Toast.makeText(context, "Bluetooth is Off", duration).show();
						break;
					case BluetoothAdapter.STATE_TURNING_OFF:
						break;
					case BluetoothAdapter.STATE_ON:
						Toast.makeText(context, "Bluetooth is On", duration).show();
						break;
					case BluetoothAdapter.STATE_TURNING_ON:
						break;
				}
			}
		}
	};

	//Discover
	private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

				int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

				switch (mode) {
					//Device is in Discoverable Mode
					case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
						break;
					//Device not in discoverable mode
					case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
						break;
					case BluetoothAdapter.SCAN_MODE_NONE:
						break;
					case BluetoothAdapter.STATE_CONNECTING:
						break;
					case BluetoothAdapter.STATE_CONNECTED:
						break;
				}

			}
		}
	};

	private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			if (action.equals(BluetoothDevice.ACTION_FOUND)){
				BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
				mBTDevices.add(device);
				deviceList.addElement(device.getName());
				deviceAddresses.addElement(device.getAddress());
			}
		}
	};

	private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
				BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				//3 cases:
				//case1: bonded already
				if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
					//inside BroadcastReceiver4
					mBTDevice = mDevice;
				}
				//case2: creating a bone
				if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
				}
				//case3: breaking a bond
				if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
				}
			}
		}
	};

	//OnOff Magic
	public void enableDisableBT(){
		if(mBluetoothAdapter == null){
		    System.out.println("Can't Bluetooth");
		}
		if(!mBluetoothAdapter.isEnabled()){
            System.out.println("Enabling Bluetooth");
			Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			context.startActivity(enableBTIntent);

			IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
			context.registerReceiver(mBroadcastReceiver1, BTIntent);
		}
		if(mBluetoothAdapter.isEnabled()){
            System.out.println("Disabling Bluetooth");
			mBluetoothAdapter.disable();

			IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
			context.registerReceiver(mBroadcastReceiver1, BTIntent);
		}

	}

	//Discoverability
	public void discoverAbility() {
		Intent discoverableIntent = new Intent(mBluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(mBluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		context.startActivity(discoverableIntent);

		IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		context.registerReceiver(mBroadcastReceiver2,intentFilter);
	}

	//Discover
    public void discover() {
        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();

            checkBTPermissions();

			mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            context.registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!mBluetoothAdapter.isDiscovering()){
			mBluetoothAdapter.startDiscovery();

			checkBTPermissions();

            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            context.registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

	public void startConnection(){
		startBTConnection(mBTDevice,MY_UUID_INSECURE);
	}

	public void startBTConnection(BluetoothDevice device, UUID uuid){
		mBluetoothConnection.startClient(device,uuid);
	}

	public void checkBTPermissions() {
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
			int permissionCheck = context.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
			permissionCheck += context.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
		}else{
			System.out.println("fuck this shit im out");
		}
	}
}