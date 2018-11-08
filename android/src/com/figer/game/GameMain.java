package com.figer.game;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.UUID;

public class GameMain extends ApplicationAdapter {
	Toast toast;
	int duration = Toast.LENGTH_SHORT;
	Context context;

	/**				Bluetooth Magic				*/
	BluetoothAdapter mBluetoothAdapter;
	private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
	BluetoothDevice mBTDevice;
	private ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();

	//				BroadcastReceivers				//
	//ON/OFF
	private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// When discovery finds a device
			if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
				final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

				switch(state){
					case BluetoothAdapter.STATE_OFF:
						toast.makeText(context, "STATE OFF", duration).show();
						break;
					case BluetoothAdapter.STATE_TURNING_OFF:
						toast.makeText(context, "STATE TURNING OFF", duration).show();
						break;
					case BluetoothAdapter.STATE_ON:
						toast.makeText(context, "STATE ON", duration).show();
						break;
					case BluetoothAdapter.STATE_TURNING_ON:
						toast.makeText(context, "STATE TURNING ON", duration).show();
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
						toast.makeText(context, "Discoverability Enabled", duration).show();
						break;
					//Device not in discoverable mode
					case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
						toast.makeText(context, "Discoverability Disabled. Able to receive connections", duration).show();
						break;
					case BluetoothAdapter.SCAN_MODE_NONE:
						toast.makeText(context, "Discoverability Disabled. Not able to receive connections", duration).show();
						break;
					case BluetoothAdapter.STATE_CONNECTING:
						toast.makeText(context, "Connectiong...", duration).show();
						break;
					case BluetoothAdapter.STATE_CONNECTED:
						toast.makeText(context, "Connected", duration).show();
						break;
				}

			}
		}
	};

	private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			toast.makeText(context, "onReceive: ACTION FOUND", duration).show();

			if (action.equals(BluetoothDevice.ACTION_FOUND)){
				BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
				mBTDevices.add(device);
				toast.makeText(context, "Device: " + device.getName() + "" + device.getAddress(), duration).show();
				//mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
				//lvNewDevices.setAdapter(mDeviceListAdapter);
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
					toast.makeText(context, "BOND_BONDED", duration).show();
					//inside BroadcastReceiver4
					mBTDevice = mDevice;
				}
				//case2: creating a bone
				if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
					toast.makeText(context, "BOND_BONDING", duration).show();
				}
				//case3: breaking a bond
				if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
					toast.makeText(context, "BOND_DONE", duration).show();
				}
			}
		}
	};

	/**					Damn Graphics				*/
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
//Destroy Receivers
		toast.makeText(context, "onDestroy: called", duration).show();
		context.unregisterReceiver(mBroadcastReceiver1);
		context.unregisterReceiver(mBroadcastReceiver2);
		context.unregisterReceiver(mBroadcastReceiver3);
		context.unregisterReceiver(mBroadcastReceiver4);

//Destroy Batches and images
		batch.dispose();
		img.dispose();
	}
}
