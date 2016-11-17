package com.aazen.apidemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aazen.android.common.ConnectionResult;
import com.aazen.android.common.api.AazenApiClient;
import com.aazen.android.common.api.ResultCallback;
import com.aazen.android.wearable.MessageApi;
import com.aazen.android.wearable.MessageEvent;
import com.aazen.android.wearable.Node;
import com.aazen.android.wearable.NodeApi;
import com.aazen.android.wearable.Wearable;

import java.util.Collection;
import java.util.HashSet;


public class SensorActivity extends Activity implements View.OnClickListener,
        AazenApiClient.ConnectionCallbacks, AazenApiClient.OnConnectionFailedListener,
        MessageApi.MessageListener, NodeApi.NodeListener {

    private static final String TAG = "SensorActivity";
    private static final String START_ACTIVITY_PATH = "/start-sensor";

    private TextView mSensorXTv;
    private TextView mSensorYTv;
    private TextView mSensorZTv;
    private Button mStartButton;
    private AazenApiClient mAazenApiClient;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mSensorXTv = (TextView) findViewById(R.id.sensor_textx);
        mSensorYTv = (TextView) findViewById(R.id.sensor_texty);
        mSensorZTv = (TextView) findViewById(R.id.sensor_textz);
        mStartButton = (Button) findViewById(R.id.start_sensor);
        mStartButton.setOnClickListener(this);
        mAazenApiClient = new AazenApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAazenApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.MessageApi.removeListener(mAazenApiClient, this);
        mAazenApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
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
    public void onPeerConnected(Node node) {
        Log.d(TAG, "Node Connected");
    }

    @Override
    public void onPeerDisconnected(Node node) {
        Log.d(TAG, "Node Disconnected");
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        if (event.getPath().equals("Accelerometer")) {
            byte[] data = event.getData();
            final String SensorMessage = new String(data);
            final String[] xyz = SensorMessage.split(",");
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mSensorXTv.setText(getString(R.string.sensor_x) + xyz[0]);
                    mSensorYTv.setText(getString(R.string.sensor_y) + xyz[1]);
                    mSensorZTv.setText(getString(R.string.sensor_z) + xyz[2]);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_sensor: {
                Log.d(TAG, "Button Clicked");
                new StartWearableActivityTask().execute();
            }
        }
    }

    private void sendStartActivityMessage(String node) {
        Wearable.MessageApi.sendMessage(
                mAazenApiClient, node, START_ACTIVITY_PATH, new byte[0]).setResultCallback(
                new ResultCallback<MessageApi.SendMessageResult>() {
                    @Override
                    public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                        if (!sendMessageResult.getStatus().isSuccess()) {
                            Log.e(TAG, "Failed to send message with status code: "
                                    + sendMessageResult.getStatus().getStatusCode());
                        } else {
                            Log.d(TAG, "Success");
                        }
                    }
                }
        );
    }

    private Collection<String> getNodes() {
        HashSet<String> results = new HashSet<String>();
        NodeApi.GetConnectedNodesResult nodes =
                Wearable.NodeApi.getConnectedNodes(mAazenApiClient).await();

        for (Node node : nodes.getNodes()) {
            results.add(node.getId());
        }
        return results;
    }

    private class StartWearableActivityTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            Collection<String> nodes = getNodes();
            for (String node : nodes) {
                sendStartActivityMessage(node);
            }
            return null;
        }
    }
}
