package mt.edu.mcast.task2widget.weather;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherService {

        public WeatherListener weatherListener;

        public WeatherService(WeatherListener weatherListener) {
            this.weatherListener = weatherListener;
        }

        public void loadWeatherFixed(String location, String appID) {
            //Retrofit Builder
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            //instance for interface
            WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

            Call<OpenWeather> call = weatherAPI.getWeatherFixedLocation(location, appID);


            call.enqueue(new Callback<OpenWeather>() {
                @Override
                public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {

                    if (response.code() == 200) {

                        weatherListener.weatherResponse(response.body());

                    }
                }

                @Override
                public void onFailure(Call<OpenWeather> call, Throwable t) {

                    Log.e("retro error" , "error");
                }
            });
        }

    public void loadWeatherDynamic(double lat, double lon, String appID) {
        //Retrofit Builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        //instance for interface
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        Call<OpenWeather> call = weatherAPI.getWeatherDynamicLocation(String.valueOf(lat), String.valueOf(lon), appID);


        call.enqueue(new Callback<OpenWeather>() {
            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {

                if (response.code() == 200) {

                    weatherListener.weatherResponse(response.body());

                }
            }

            @Override
            public void onFailure(Call<OpenWeather> call, Throwable t) {

                Log.e("retro error" , "error");
            }
        });
    }

        public void setWeatherListener(WeatherListener listener) {
            weatherListener = listener;
        }


        public interface WeatherListener {
            public void weatherResponse(OpenWeather openWeather);
        }
}
