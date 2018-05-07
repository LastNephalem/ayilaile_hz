package com.example.lucifer.ayilaile_hz;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.lucifer.ayilaile_hz.adapter.ViewPagerAdapter;
import com.example.lucifer.ayilaile_hz.region_DB.DBCopyUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener,ViewPager.OnPageChangeListener {

    private static final int REGION_REQUEST_CODE = 001;
    private final int SDK_PERMISSION_REQUEST = 1;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明

    private TextView tv_result;                 //保存得到的地区
    private LinearLayout linearLayout;          //存放小白点
    private ViewPager viewPager;                //获取viewPager
    private TextView textView;                  //获取文本描述的TextView
    private int[] imageResIds;                  //保存图片资源ID数组
    private String[] imageText;                 //图片描述数组
    private Intent dailyintent;                 //服务
    //private Intent ayinfo;                      //阿姨信息

    ViewPagerAdapter viewpagerAdapter = new ViewPagerAdapter();
    private boolean isRunning = true;

    //事物处理代码
    private LinearLayout icon_1;
    private LinearLayout icon_2;
    private LinearLayout icon_3;
    private LinearLayout icon_4;
    private LinearLayout icon_5;

    private LinearLayout pic_1;
    private LinearLayout pic_2;
    private LinearLayout pic_3;
    private LinearLayout pic_4;

    private RelativeLayout rl_city,rl_nearby;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home, null);
        findView(home);
        setAllOnClickListener();
        //初始化数据
        initData();
        //初始化适配器
        initAdapter();

        locate();//定位

        /**
         * 自动循环： 1.定时器：Timer 2.开子线程：while true循环 3.ClockManger
         * 4.用Handler发送延时信息，实现循环，最简单最方便
         */
        mhandler.sendEmptyMessageDelayed(0, 3000);
        return home;
    }


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {
                mhandler.sendEmptyMessageDelayed(0, 3000);
            }
        }
    };

    private void setAllOnClickListener() {
        icon_1.setOnClickListener(this);
        icon_2.setOnClickListener(this);
        icon_3.setOnClickListener(this);
        icon_4.setOnClickListener(this);
        icon_5.setOnClickListener(this);
        pic_1.setOnClickListener(this);
        pic_2.setOnClickListener(this);
        pic_3.setOnClickListener(this);
        pic_4.setOnClickListener(this);

        rl_city.setOnClickListener(this);
        rl_nearby.setOnClickListener(this);
    }

    private void findView(View view) {
        //初始化视图
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        textView = (TextView) view.findViewById(R.id.home_image_desc);
        viewPager.setOnPageChangeListener(this);
        linearLayout = (LinearLayout) view.findViewById(R.id.home_point_container);
        //初始化地图选择器,city_tv
        rl_city = (RelativeLayout) view.findViewById(R.id.rl_home_location);
        tv_result = (TextView) view.findViewById(R.id.tv_home_current_city);
        //搜索附近阿姨
        rl_nearby = (RelativeLayout) view.findViewById(R.id.rl_ayi_nearby);

        icon_1 = (LinearLayout) view.findViewById(R.id.icon1);
        icon_2 = (LinearLayout) view.findViewById(R.id.icon2);
        icon_3 = (LinearLayout) view.findViewById(R.id.icon3);
        icon_4 = (LinearLayout) view.findViewById(R.id.icon4);
        icon_5 = (LinearLayout) view.findViewById(R.id.icon5);
        pic_1 = (LinearLayout) view.findViewById(R.id.show_text_1);
        pic_2 = (LinearLayout) view.findViewById(R.id.show_text_2);
        pic_3 = (LinearLayout) view.findViewById(R.id.show_text_3);
        pic_4 = (LinearLayout) view.findViewById(R.id.show_text_4);
    }


    private void initData() {

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        setBDMapAttributes();
        //设置地图定位属性

        // 图片资源id数组
        imageResIds = new int[]{R.drawable.home_viewpager_image1, R.drawable.home_viewpager_image2,
                R.drawable.home_viewpager_image3, R.drawable.home_viewpager_image4, R.drawable.home_viewpager_image5};
        // 文本描述
        imageText = new String[]{
                "专业级厨房卫生打扫",
                "打造完美家居",
                "创造温馨室内环境",
                "给您一个舒适安逸的家",
                "热爱生活"
        };

        // 初始化要展示的5个ImageView
        viewpagerAdapter.imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(getContext());
            imageView.setBackgroundResource(imageResIds[i]);
            viewpagerAdapter.imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(getContext());
            pointView.setBackgroundResource(R.drawable.pointer_selector);
            layoutParams = new LinearLayout.LayoutParams(15, 15);
            if(i != 0)
                layoutParams.leftMargin = 20;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            linearLayout.addView(pointView,layoutParams);
        }

    }

    private void initAdapter() {
        //将第一张图片的状态设置为true
        linearLayout.getChildAt(0).setEnabled(true);
        textView.setText(imageText[0]);
        // 设置适配器
        viewPager.setAdapter(viewpagerAdapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        //新条目被选中时调用
        int newPosition = position % viewpagerAdapter.imageViewList.size();
        textView.setText(imageText[newPosition]);
        for(int i=0;i<5;i++){
            if(i==newPosition){
                linearLayout.getChildAt(i).setEnabled(true);
            }else{
                linearLayout.getChildAt(i).setEnabled(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //滚动状态变化时调用
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_home_location:
                DBCopyUtil.copyDataBaseFromAssets(getContext(), "region.db");
                startActivityForResult(new Intent(getContext(), RegionSelectActivity.class), REGION_REQUEST_CODE);
                break;
            case R.id.rl_ayi_nearby:
                startActivity(new Intent(getContext(),LocationActivity.class));
                break;
            /*case R.id.icon1:
                dailyintent = new Intent(getContext(),Daily_Clean_Activity.class);
                dailyintent.putExtra("icon","1");
                startActivity(dailyintent);
                break;
            case R.id.icon2:
                dailyintent = new Intent(getContext(),Daily_Clean_Activity.class);
                dailyintent.putExtra("icon","2");
                startActivity(dailyintent);
                break;
            case R.id.icon3:
                dailyintent = new Intent(getContext(),Daily_Clean_Activity.class);
                dailyintent.putExtra("icon","3");
                startActivity(dailyintent);
                break;
            case R.id.icon4:
                dailyintent = new Intent(getContext(),Daily_Clean_Activity.class);
                dailyintent.putExtra("icon","4");
                startActivity(dailyintent);
                break;*/
            case R.id.show_text_1:
                dailyintent = new Intent(getContext(),search_Activity.class);
                dailyintent.putExtra("item","1");
                startActivity(dailyintent);
                break;
            case R.id.show_text_2:
                dailyintent = new Intent(getContext(),search_Activity.class);
                dailyintent.putExtra("item","2");
                startActivity(dailyintent);
                break;
            case R.id.show_text_3:
                dailyintent = new Intent(getContext(),search_Activity.class);
                dailyintent.putExtra("item","3");
                startActivity(dailyintent);
                break;
            case R.id.show_text_4:
                dailyintent = new Intent(getContext(),search_Activity.class);
                dailyintent.putExtra("item","4");
                startActivity(dailyintent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REGION_REQUEST_CODE && resultCode == 200) {
            String province = data.getStringExtra(RegionSelectActivity.REGION_PROVINCE);
            String city = data.getStringExtra(RegionSelectActivity.REGION_CITY);
            String area = data.getStringExtra(RegionSelectActivity.REGION_AREA);

            tv_result.setText(city);
        }
    }

    @Override
    public void onDestroyView() {
        isRunning = false;
        super.onDestroyView();
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            Log.w("定位描述", bdLocation.getLocTypeDescription() + "");
            String addr = bdLocation.getAddrStr();    //获取详细地址信息
            String country = bdLocation.getCountry();    //获取国家
            String province = bdLocation.getProvince();    //获取省份
            String city = bdLocation.getCity();    //获取城市
            String district = bdLocation.getDistrict();    //获取区县
            String street = bdLocation.getStreet();    //获取街道信息
            //Toast.makeText(getContext(), addr, Toast.LENGTH_SHORT).show();
            tv_result.setText(city);
        }
    }

    //设置百度SDK配置参数
    private void setBDMapAttributes() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setScanSpan(0);
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            // requestCode即所声明的权限获取码
            case SDK_PERMISSION_REQUEST:{
                mLocationClient.stop();
                mLocationClient.start();
            }break;
        }
    }

    private void locate() {

        //判断权限
        List<String> permission = getPermissionList(getActivity());
        if(permission.size()>0){
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            requestPermissions(permission.toArray(new String[permission.size()]),SDK_PERMISSION_REQUEST);
            return;
        }
        mLocationClient.start();
        //mLocationClient为第二步初始化过的LocationClient对象
        //调用LocationClient的start()方法，便可发起定位请求
    }

    //Android6.0之后需要动态获取权限
    public List<String> getPermissionList(Activity activity) {
        List<String> permission = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.READ_PHONE_STATE);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.ACCESS_WIFI_STATE);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.ACCESS_NETWORK_STATE);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.INTERNET);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
            permission.add(Manifest.permission.CHANGE_WIFI_STATE);
        return permission;
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.stop();
    }
}
