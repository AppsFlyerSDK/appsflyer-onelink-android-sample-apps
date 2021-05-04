package com.appsflyer.onelink.appsflyeronelinkbasicapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Map;

public class conversionDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_data);
        displayConversionData();
    }

    public void displayConversionData(){
        SharedPreferences appSharedPreferences = this.getSharedPreferences("CONVERSIONDATA", getApplicationContext().MODE_PRIVATE);
        Map<String, Object> conversionData = (Map<String, Object>) appSharedPreferences.getAll();
        String conversionDataString = MapToFormattedString(conversionData);
        TextView conversionDataTextView = findViewById(R.id.conversionDataTextView);
        conversionDataTextView.setText(conversionDataString);
    }

    public String MapToFormattedString(Map<String, Object> map){
        String result = "";
        Object value;
        for (Map.Entry<String, Object> pair: map.entrySet()){
            value = pair.getValue();
            if (value == null) {
                value = "null";
            }
            result += String.format("%s : %s\n", pair.getKey(), value);
        }
        return result;
    }
}
