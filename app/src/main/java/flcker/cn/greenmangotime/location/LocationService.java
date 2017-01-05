package flcker.cn.greenmangotime.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import flcker.cn.greenmangotime.MainApplication;

/**
 * Created by liujiankang on 2016/12/28.
 */

public class LocationService implements LocationListener {
    // TAG
    private static final String TAG = LocationService.class.getSimpleName();
    // private members
    private static LocationService mInstance;
    private MainApplication mApp;
    private LocationManager mLocationManager;
    private Location mLocation = null;
    private double mLatitude = 0.0;
    private double mLongitude = 0.0;

    private LocationService() {
        mApp = MainApplication.getApplication();
        mLocationManager = mApp.getLocationManager();
    }

    public static LocationService getInstance() {
        if (mInstance == null) {
            mInstance = new LocationService();
        }
        return mInstance;
    }

    public void registerLocationService() {
        if (ActivityCompat.checkSelfPermission(mApp, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mApp, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(mApp, "没有访问位置权限", Toast.LENGTH_LONG).show();
            return;
        }
        // 5*1000ms, 1.0 meter
        List<String> locationList = mLocationManager.getProviders(true);
        // gps
        if (locationList.contains(LocationManager.GPS_PROVIDER)) {
            mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 5 * 1000, 1.0f, this);
        }
        // network
        if (locationList.contains(LocationManager.NETWORK_PROVIDER)) {
            mLocationManager.requestLocationUpdates(mLocationManager.NETWORK_PROVIDER, 5 * 1000, 1.0f, this);
        }
//        mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 5*1000, 1.0f, this);
    }

    public void unregisterLocationService() {
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(mApp, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mApp, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Toast.makeText(mApp, "没有访问位置权限", Toast.LENGTH_LONG).show();
                return;
            }
            mLocationManager.removeUpdates(this);
        }
    }

    public void refreshCurrentLocation() {
        Log.i(TAG, "refreshCurrentLocation 1");
        MainApplication app = MainApplication.getApplication();
        Log.i(TAG, "refreshCurrentLocation 1");
        LocationManager lm = app.getLocationManager();
        Log.i(TAG, "refreshCurrentLocation 1");
        List<String> locationList = lm.getProviders(true);

        Log.i(TAG, "refreshCurrentLocation 1");
        String provider = "";
        if (locationList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (locationList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(app.getApplicationContext(), "没有可用的定位服务", Toast.LENGTH_LONG).show();
            return;
        }

        Log.i(TAG, "refreshCurrentLocation 2");
        if (app.checkManifestPerssion(Manifest.permission.ACCESS_FINE_LOCATION) && app.checkManifestPerssion(Manifest.permission.ACCESS_COARSE_LOCATION)) {

        }
        if (ActivityCompat.checkSelfPermission( app, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(app, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i(TAG, "refreshCurrentLocation 3");
            return;
        }
        Log.i(TAG, "refreshCurrentLocation 4 " + provider);
        provider = LocationManager.NETWORK_PROVIDER;
        Location location = lm.getLastKnownLocation(provider);
        Log.i(TAG, "refreshCurrentLocation 5 " + location);
        mLatitude = location.getLatitude();
        Log.i(TAG, "refreshCurrentLocation 6");
        mLongitude = location.getLongitude();
        Log.i(TAG, "refreshCurrentLocation 7");
    }

    public double getLatitude() {
        double _latitude = 0.0;
        if(mLocation != null) {
            _latitude = mLocation.getLatitude();
        }
        return _latitude;
    }

    public double getLongitude() {
        double _longitude = 0.0;
        if (mLocation != null) {
            _longitude = mLocation.getLongitude();
        }
        return _longitude;
    }

    public void refreshLocation(Location location) {
        mLocation = location;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
