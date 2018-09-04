package com.shiyuji;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "LocationActivity";
    public LocationClient mLocationClient;
    private TextView Lbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate:LocationActivity ");
        Lbs = (TextView)findViewById(R.id.LBS);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[]permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(LocationActivity.this,permissions,1);
        }else{
            requestLocation();
        }

    }

    /**
     * 开始定位
     */
    private void requestLocation(){
        initLocation();
        mLocationClient.start();

    }
private void initLocation(){
    LocationClientOption option = new LocationClientOption();
    option.setScanSpan(5000);
    option.setIsNeedAddress(true);
    mLocationClient.setLocOption(option);
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:if (grantResults.length>0){
                for (int result:grantResults){
                    if (result != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"同意权限才可以使用本程序",Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                requestLocation();
            }else{
                Toast.makeText(this,"发生错误",Toast.LENGTH_SHORT).show();
                finish();
            }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Lbs.setText(bdLocation.getCity());
            Log.d(TAG, "getLocationID: "+bdLocation.getLocationID());
            Log.d(TAG, "getCityCode: "+bdLocation.getCityCode());
            Log.d(TAG, "getAdCode: "+bdLocation.getAdCode());
        }
    }
}
