package com.aazen.apidemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aazen.android.common.ConnectionResult;
import com.aazen.android.common.api.AazenApiClient;
import com.aazen.android.common.api.ResultCallback;
import com.aazen.android.wearable.MessageApi;
import com.aazen.android.wearable.MessageEvent;
import com.aazen.android.wearable.Wearable;

public class UiActivity extends Activity implements AazenApiClient.OnConnectionFailedListener,
        AazenApiClient.ConnectionCallbacks, MessageApi.MessageListener {

    private static final String START_ACTIVITY_PATH = "/start-ui";
    private static final String DEFAULT_NODE = "default_node";

    private Button mStartUiBtn;
    private AazenApiClient mAazenApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        mStartUiBtn = (Button) findViewById(R.id.start_ui);
        mStartUiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wearable.MessageApi.sendMessage(
                        mAazenApiClient, DEFAULT_NODE, START_ACTIVITY_PATH, new byte[0])
                        .setResultCallback(
                                new ResultCallback<MessageApi.SendMessageResult>() {
                                    @Override
                                    public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                    }
                                }
                        );
            }
        });

        mAazenApiClient = new AazenApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
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
    }

    @Override
    public void onConnectionSuspended(int cause) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

    }

    @Override
    public void onMessageReceived(MessageEvent event) {

    }
}
