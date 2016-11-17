package com.aazen.apidemo;

import android.app.Activity;
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

public class WeatherActivity extends Activity implements AazenApiClient.ConnectionCallbacks,
        AazenApiClient.OnConnectionFailedListener, NodeApi.NodeListener, MessageApi.MessageListener {

    private static final String START_ACTIVITY_PATH = "/start-weather";
    private static final String DEFAULT_NODE = "default_node";
    private final String TAG = "WeatherActivity";
    private Button mStartWeatherBtn;
    private TextView mWeatherTv;
    private AazenApiClient mAazenApiClient;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mWeatherTv = (TextView) findViewById(R.id.weather_text);
        mStartWeatherBtn = (Button) findViewById(R.id.start_weather);
        mStartWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button Click");
                sendStartActivityMessage();
            }
        });
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
        Log.d(TAG, "onResume...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.MessageApi.removeListener(mAazenApiClient, this);
        Wearable.NodeApi.removeListener(mAazenApiClient, this);
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
    public void onMessageReceived(MessageEvent event) {
        if (event.getPath().equals("Weathers")) {
            Log.d(TAG, "onMessageReceived: " + event);
            byte[] data = event.getData();
            final String datas = new String(data);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mWeatherTv.setText(datas);
                }
            });
        }
    }

    @Override
    public void onPeerConnected(Node node) {
        Log.d(TAG, "onPeerConncted:");
    }

    @Override
    public void onPeerDisconnected(Node node) {
        Log.d(TAG, "onPeerDisconnected:");
    }

    private void sendStartActivityMessage() {
        Wearable.MessageApi.sendMessage(
                mAazenApiClient, DEFAULT_NODE, START_ACTIVITY_PATH, new byte[0]).setResultCallback(
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
}
