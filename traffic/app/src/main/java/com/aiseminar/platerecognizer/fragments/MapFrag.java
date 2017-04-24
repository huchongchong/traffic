package com.aiseminar.platerecognizer.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiseminar.platerecognizer.R;
import com.aiseminar.platerecognizer.activity.MainActivity;
import com.aiseminar.platerecognizer.application.MyApplication;
import com.aiseminar.platerecognizer.service.LocationService;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static com.aiseminar.platerecognizer.R.id.location;


/**
 * Created by Administrator on 2017/4/10.
 */

public class MapFrag extends Fragment {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor mCurrentMarker;
    private LocationService locationService;
    public MapFrag() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_map,null);
        //获取地图控件引用
        mMapView = (MapView) rootView.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        float f = mBaiduMap.getMaxZoomLevel();//19.0 最小比例尺
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(f-6);//大小按需求计算就可以
        mBaiduMap.animateMapStatus(u);
//普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
// 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mMapView.showZoomControls(false);
// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.map_pin);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        mMapView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        isFirstLoc = true;
        mMapView.setVisibility(View.INVISIBLE);
    }
    /***
     * Stop location service
     */
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((MyApplication) getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        //int type = getIntent().getIntExtra("from", 0);
//        if (type == 0) {
//            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
            locationService.start();
//        }
//        startLocation.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (startLocation.getText().toString().equals(getString(R.string.startlocation))) {
//                    locationService.start();// 定位SDK
//                    // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
//                    startLocation.setText(getString(R.string.stoplocation));
//                } else {
//                    locationService.stop();
//                    startLocation.setText(getString(R.string.startlocation));
//                }
//            }
//        });
    }
   void drawReact(LatLng ptLT,LatLng ptRT,LatLng ptLB,LatLng ptRB){
//       LatLng pt1 = new LatLng(39.93923, 116.357428);
//       LatLng pt2 = new LatLng(39.91923, 116.327428);
//       LatLng pt3 = new LatLng(39.89923, 116.347428);
//       LatLng pt4 = new LatLng(39.89923, 116.367428);
//       LatLng pt5 = new LatLng(39.91923, 116.387428);
       List<LatLng> pts = new ArrayList<LatLng>();
       pts.add(ptLT);
       pts.add(ptRT);
       pts.add(ptRB);
       pts.add(ptLB);
//构建用户绘制多边形的Option对象
//       OverlayOptions dotOption = new DotOptions().center(ptLT).color(Color.BLACK);
//       mBaiduMap.addOverlay(dotOption);
//       OverlayOptions dotOption1 = new DotOptions().center(ptRT).color(Color.RED);
//       mBaiduMap.addOverlay(dotOption1);
//       OverlayOptions dotOption2 = new DotOptions().center(ptLB).color(Color.BLUE);
//       mBaiduMap.addOverlay(dotOption2);
//       OverlayOptions dotOption3 = new DotOptions().center(ptRB).color(Color.GREEN);
//       mBaiduMap.addOverlay(dotOption3);
       OverlayOptions polygonOption = new PolygonOptions()
               .points(pts)
               .stroke(new Stroke(3, Color.BLUE)).fillColor(Color.TRANSPARENT);
//       List<Integer> colors = new ArrayList<>();
//       colors.add(Integer.valueOf(Color.BLUE));
//       colors.add(Integer.valueOf(Color.RED));
//       colors.add(Integer.valueOf(Color.YELLOW));
//       colors.add(Integer.valueOf(Color.GREEN));
//
//       OverlayOptions ooPolyline = new PolylineOptions().width(10)
//               .colorsValues(colors).points(pts);
//       Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
//在地图上添加多边形Option，用于显示
       mBaiduMap.addOverlay(polygonOption);
   }

    private boolean isFirstLoc = true;
    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                 MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
// 设置定位数据
                mBaiduMap.setMyLocationData(locData);
                if(isFirstLoc) {
                        isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
                    drawReact(new LatLng(location.getLatitude()+0.002,
                            location.getLongitude()-0.002),new LatLng(location.getLatitude()+0.002,
                            location.getLongitude()+0.002),new LatLng(location.getLatitude()-0.002,
                            location.getLongitude()-0.002),new LatLng(location.getLatitude()-0.002,
                            location.getLongitude()+0.002));
                }
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.e("baidu",sb.toString());
                ((MainActivity)MapFrag.this.getActivity()).setTitle(location.getLocationDescribe());
            }
        }

        public void onConnectHotSpotMessage(String s, int i){
        }
    };
}
