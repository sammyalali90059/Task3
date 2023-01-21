package mt.edu.mcast.task2widget.weather;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {


        @GET("/data/2.5/weather?units=metric")
        Call<OpenWeather> getWeatherFixedLocation(@Query("q") String location, @Query("appid") String appId);

        @GET("/data/2.5/weather?units=metric")
        Call<OpenWeather> getWeatherDynamicLocation(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appId);

}
