package com.aazen.apidemo;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.aazen.android.common.ConnectionResult;
import com.aazen.android.common.api.AazenApiClient;
import com.aazen.android.common.data.FreezableUtils;
import com.aazen.android.wearable.DataEvent;
import com.aazen.android.wearable.DataEventBuffer;
import com.aazen.android.wearable.MessageEvent;
import com.aazen.android.wearable.Node;
import com.aazen.android.wearable.Wearable;
import com.aazen.android.wearable.WearableListenerService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Listens to DataItems and Messages from the local node.
 */
public class DataLayerListenerService extends WearableListenerService {

    public static final String COUNT_PATH = "/count";
    public static final String IMAGE_PATH = "/image";
    public static final String IMAGE_KEY = "photo";
    private static final String TAG = "DataLayer";
    private static final String START_ACTIVITY_PATH = "/start-activity";
    private static final String START_SENSOR_PATH = "/start-sensor";
    private static final String START_WEATHER_PATH = "/start-weather";
    private static final String START_STEP_PATH = "/start-step";
    private static final String START_CARD = "/start-card";
    private static final String START_UI = "/start-ui";
    private static final String DATA_ITEM_RECEIVED_PATH = "/data-item-received";

    private AazenApiClient mAazenApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mAazenApiClient = new AazenApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mAazenApiClient.connect();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged: " + dataEvents);
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        dataEvents.close();
        ConnectionResult connectionResult = mAazenApiClient
                .blockingConnect(30, TimeUnit.SECONDS);
        if (!connectionResult.isSuccess()) {
            return;
        }

        // Loop through the events and send a message back to the node that created the data item.
        for (DataEvent event : events) {
            Uri uri = event.getDataItem().getUri();
            String path = uri.getPath();
            if (COUNT_PATH.equals(path)) {
                // Get the node id of the node that created the data item from the host portion of
                // the uri.
                String nodeId = uri.getHost();
                // Set the data of the message to be the bytes of the Uri.
                byte[] payload = uri.toString().getBytes();
                // Send the rpc
                Wearable.MessageApi.sendMessage(mAazenApiClient, nodeId, DATA_ITEM_RECEIVED_PATH,
                        payload);
            }
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "onMessageReceived: " + messageEvent);
        // Check to see if the message is to start an activity
        switch (messageEvent.getPath()) {
            case START_ACTIVITY_PATH: {
                Intent startIntent = new Intent(this, MainActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startIntent);
                Log.d(TAG, "MessageReceived! Open MainActivity");
                break;
            }
            case START_SENSOR_PATH: {
                Intent sensorIntent = new Intent(this, SensorActivity.class);
                sensorIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sensorIntent);
                Log.d(TAG, "MessageReceived! Open Sensors!");
                break;
            }
            case START_STEP_PATH: {
                Intent stepIntent = new Intent(this, StepActivity.class);
                stepIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(stepIntent);
                Log.d(TAG, "MessageReceived! Open StepDemo!");
                break;
            }
            case START_WEATHER_PATH: {
                Intent weatherIntent = new Intent(this, WeatherActivity.class);
                weatherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(weatherIntent);
                Log.d(TAG, "MessageReceived! Open WeatherDemo!");
                break;
            }
            case START_CARD: {
                Intent cardIntent = new Intent(this, CardActivity.class);
                cardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cardIntent);
                Log.d(TAG, "MessageReceived! Open CardDemo!");
                break;
            }
            case START_UI: {
                Intent uiIntent = new Intent(this, UiListActivity.class);
                uiIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uiIntent);
                Log.d(TAG, "MessageReceived! Open UIList!");
                break;
            }
            default: {
                Intent startIntent = new Intent(this, MainActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startIntent);
                Log.d(TAG, "MessageReceived! Open MainActivity");
                break;
            }
        }
    }

    @Override
    public void onPeerConnected(Node peer) {
        Log.d(TAG, "onPeerConnected: " + peer);
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        Log.d(TAG, "onPeerDisconnected: " + peer);
    }

}
