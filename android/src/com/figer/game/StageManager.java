package com.figer.game;

import com.badlogic.gdx.utils.Array;

public class StageManager {
    public static final int NULL = -1;
    public static final int INITIAL = 0;
    public static final int GAME = 1;
    private int number;
    private int requestNumber;

    private String connectedDevice;

    public StageManager(){
        number = NULL;
        requestNumber = INITIAL;
    }

    public int getNumber(){
        return number;
    }

    public void requestNumber(int request){
        requestNumber = request;
    }

    public void update(Array<Stage> stages){
        if (number != requestNumber) {
            if (number != StageManager.NULL)
                stages.get(number).onDeactivating();
            number = requestNumber;
            stages.get(requestNumber).onActivating();
        }
    }

    public String getConnectedDevice() {
        return connectedDevice;
    }

    public void setConnectedDevice(String connectedDevice) {
        this.connectedDevice = connectedDevice;
    }
}
