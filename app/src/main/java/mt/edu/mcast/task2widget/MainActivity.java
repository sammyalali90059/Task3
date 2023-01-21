package mt.edu.mcast.task2widget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mt.edu.mcast.task2widget.weather.OpenWeather;
import mt.edu.mcast.task2widget.weather.WeatherService;

public class MainActivity extends AppCompatActivity {

    final int ALL_PERMISSIONS_REQUEST_CODE= 10;
    //Register and get your APPID https://openweathermap.org/
    final static String APP_ID = "db3f55ab2f498f2ff70493904c9c6d47";

    TextView txtvExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtvExample = findViewById(R.id.txtvExample);

        //check and Request Permissions
        checkAndRequestPermission();

        //Loading Weather DEMO
        //within the listener you implement what you want to do with the data that you receive from the API
        WeatherService.WeatherListener weatherListener = new WeatherService.WeatherListener() {
            @Override
            public void weatherResponse(OpenWeather openWeather) {
               txtvExample.setText(openWeather.getName() + ":" + openWeather.getMain().getTemp());
            }
        };
        //creating an instance of the weatherService with the defined listener
        WeatherService weatherService = new WeatherService(weatherListener);
        //loading weather for a fixed location
        weatherService.loadWeatherFixed("Rome", APP_ID);

        //This method returns weather info for a gps location
        //weatherService.loadWeatherDynamic("35.917973", "14.409943", APP_ID);
    }


    public boolean checkAndRequestPermission(){

        //Checking if permissions are granted
        int receiveSms = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int fineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermissionNeeded = new ArrayList<>();

        //Check each permission if granted otherwise add to list
        if(receiveSms != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(Manifest.permission.RECEIVE_SMS);
        }

        if(readSMS != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(Manifest.permission.READ_SMS);
        }


        if(fineLocation != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(coarseLocation != PackageManager.PERMISSION_GRANTED){
            listPermissionNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if(!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,
                    listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),
                    ALL_PERMISSIONS_REQUEST_CODE);
            return false;
        }

        return true;
    }

    //Callback method, once permission are allowed or denied, this method is executed
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == ALL_PERMISSIONS_REQUEST_CODE){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) !=
                    PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission to receive SMSs required for task 1", Toast.LENGTH_SHORT).show();
            }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) !=
                    PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission to read SMSs required for task 1", Toast.LENGTH_SHORT).show();
            }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Location Permission required for task 2", Toast.LENGTH_SHORT).show();
            }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Location Permission required for task 2", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show();
            }
        }



    }
}