package com.aazen.apidemo;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.aazen.android.common.ConnectionResult;
import com.aazen.android.common.api.AazenApiClient;
import com.aazen.android.common.api.AazenApiClient.ConnectionCallbacks;
import com.aazen.android.common.api.AazenApiClient.OnConnectionFailedListener;
import com.aazen.android.common.api.ResultCallback;
import com.aazen.android.wearable.DataApi;
import com.aazen.android.wearable.DataEventBuffer;
import com.aazen.android.wearable.MessageApi;
import com.aazen.android.wearable.MessageEvent;
import com.aazen.android.wearable.Node;
import com.aazen.android.wearable.NodeApi;
import com.aazen.android.wearable.Wearable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Shows Sensor Messages from the Wearable APIs.
 */
public class SensorActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener, DataApi.DataListener, MessageApi.MessageListener,
        NodeApi.NodeListener, SensorEventListener {

    private static final String TAG = "SensorActivity";

    private AazenApiClient mAazenApiClient;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private String mConnectedNode;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_sensor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mAazenApiClient = new AazenApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAazenApiClient.connect();
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.DataApi.removeListener(mAazenApiClient, this);
        Wearable.MessageApi.removeListener(mAazenApiClient, this);
        Wearable.NodeApi.removeListener(mAazenApiClient, this);
        mAazenApiClient.disconnect();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Wearable.DataApi.addListener(mAazenApiClient, this);
        Wearable.MessageApi.addListener(mAazenApiClient, this);
        Wearable.NodeApi.addListener(mAazenApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int cause) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.e(TAG, "onConnectionFailed(): Failed to connect, with result: " + result);
    }


    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged(): " + dataEvents);

    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        Log.d(TAG, "onMessageReceived: " + event);
    }

    @Override
    public void onPeerConnected(Node node) {
    }

    @Override
    public void onPeerDisconnected(Node node) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        new StartCheckTask().execute();
        String message = x + "," + y + "," + z;
        byte[] data = message.getBytes();
        if (mConnectedNode != null && mAazenApiClient != null && mAazenApiClient.isConnected()) {
            Wearable.MessageApi.sendMessage(
                    mAazenApiClient, mConnectedNode, "Accelerometer", data)
                    .setResultCallback(
                            new ResultCallback<MessageApi.SendMessageResult>() {
                                @Override
                                public void onResult(MessageApi.SendMessageResult sendMessageResult) {

                                    if (!sendMessageResult.getStatus().isSuccess()) {
                                        Log.d(TAG, "Failed to send message with status code: "
                                                + sendMessageResult.getStatus().getStatusCode());
                                    } else {
                                        Log.d(TAG, "send data successfully ");
                                    }
                                }
                            }
                    );
        } else {
            Log.e(TAG, "node null or client null or not connected. ");
        }
    }

    private Collection<String> getNodes() {
        Set<String> results = new HashSet<String>();
        NodeApi.GetConnectedNodesResult nodes =
                Wearable.NodeApi.getConnectedNodes(mAazenApiClient).await();

        for (Node node : nodes.getNodes()) {
            results.add(node.getId());
        }

        return results;
    }

    private class StartCheckTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... args) {
            Collection<String> nodes = getNodes();
            for (String node : nodes) {
                if (node != null) {
                    mConnectedNode = node;
                    //Log.d(TAG, "The connected node is " + node);
                }

            }
            return true;
        }
    }

}
