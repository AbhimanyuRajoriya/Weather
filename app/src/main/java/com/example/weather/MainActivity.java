package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edt;
    private Button btn;
    private TextView tem1, tem2, hum1, hum2, win1, win2;
    private RetApi api;

    private static final String API_KEY = "37ac9112fcca0157cac22555a96c08a1";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindMainViews();
        api = RetClient.getClient().create(RetApi.class);
    }

    private void bindMainViews() {
        edt = findViewById(R.id.edtT1);
        btn = findViewById(R.id.save);

        btn.setOnClickListener(v -> {
            String city = edt.getText().toString().trim();
            if (city.isEmpty()) {
                edt.setError("Enter city name");
                return;
            }
            fetchWeather(city);
        });
    }

    private void fetchWeather(String city) {
        api.getWeather(city, API_KEY, "metric").enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model weather = response.body();
                if (!response.isSuccessful() || weather == null) {
                    showToast("Error: " + response.code());
                    return;
                }

                boolean isRainy = (weather.getRain() != null) &&
                        (weather.getRain().getOneHour() != null || weather.getRain().getThreeHour() != null);

                if (isRainy) {
                    setContentView(R.layout.weth1);
                    bindCloudyViews();
                    updateUI(weather, true);
                } else {
                    setContentView(R.layout.weth2);
                    bindSunnyViews();
                    updateUI(weather, false);
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                showToast("Failure: " + t.getMessage());
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void bindSunnyViews() {
        tem2 = findViewById(R.id.temp_2);
        hum2 = findViewById(R.id.hum_2);
        win2 = findViewById(R.id.wind_2);
    }

    private void bindCloudyViews() {
        tem1 = findViewById(R.id.temp_1);
        hum1 = findViewById(R.id.hum_1);
        win1 = findViewById(R.id.wind_1);
    }

    private void updateUI(Model weather, boolean isCloudy) {
        if (isCloudy) {
            if (tem1 != null) tem1.setText(weather.getMain().getTemp() + " °C");
            if (hum1 != null) hum1.setText("Humidity: " + weather.getMain().getHumidity() + " %");
            if (win1 != null) win1.setText("Wind: " + weather.getWind().getSpeed() + " m/s");
        } else {
            if (tem2 != null) tem2.setText(weather.getMain().getTemp() + " °C");
            if (hum2 != null) hum2.setText("Humidity: " + weather.getMain().getHumidity() + " %");
            if (win2 != null) win2.setText("Wind: " + weather.getWind().getSpeed() + " m/s");
        }
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        if (findViewById(R.id.temp_1) != null || findViewById(R.id.temp_2) != null) {
            setContentView(R.layout.activity_main);
            bindMainViews();
        } else {
            super.onBackPressed();
        }
    }
}