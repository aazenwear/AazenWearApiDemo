package com.aazen.apidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;

public class FirstActivity extends Activity implements WearableListView.ClickListener {
    private static final String TAG = "FirstActivity";
    // Sample dataset for the list
    private final String[] mElements = {"数据传输", "传感器", "计步数据", "天气", "快捷卡片", "UI库Demo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // Get the list component from the layout of the activity
        WearableListView listView =
                (WearableListView) findViewById(R.id.wearable_list);

        // Assign an adapter to the list
        listView.setAdapter(new ListAdapter(this, mElements));

        // Set a click listener
        listView.setClickListener(this);
    }

    // WearableListView click listener
    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Integer tag = (Integer) v.itemView.getTag();
        switch (tag) {
            case 0: {
                Intent startIntent = new Intent(this, MainActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startIntent);
                Log.d(TAG, "MessageReceived! Open MainActivity");
                break;
            }
            case 1: {
                Intent sensorIntent = new Intent(this, SensorActivity.class);
                sensorIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sensorIntent);
                Log.d(TAG, "MessageReceived! Open Sensors!");
                break;
            }
            case 2: {
                Intent stepIntent = new Intent(this, StepActivity.class);
                stepIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(stepIntent);
                Log.d(TAG, "MessageReceived! Open StepDemo!");
                break;
            }
            case 3: {
                Intent weatherIntent = new Intent(this, WeatherActivity.class);
                weatherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(weatherIntent);
                Log.d(TAG, "MessageReceived! Open WeatherDemo!");
                break;
            }
            case 4: {
                Intent cardIntent = new Intent(this, CardActivity.class);
                cardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cardIntent);
                Log.d(TAG, "MessageReceived! Open CardDemo!");
                break;
            }
            case 5: {
                Intent uiIntent = new Intent(this, UiListActivity.class);
                uiIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uiIntent);
                Log.d(TAG, "MessageReceived! Open UiListActivity!");
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
    public void onTopEmptyRegionClick() {
    }
}