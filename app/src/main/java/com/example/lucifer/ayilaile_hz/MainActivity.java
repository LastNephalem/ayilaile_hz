package com.example.lucifer.ayilaile_hz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int HOME_FRAGMENT = 0;
    private static final int ORDER_FRAGMENT = 1;
    private static final int LIFE_FRAGMENT = 2;
    private static final int MINE_FRAGMENT = 3;

    private Fragment[] fragments;        //Fragment数组
    private ImageView[] imagebuttons;    //tag图标
    private TextView[] textviews;        //tag文字
    private int currentselected;        //当前fragment
    private int select;                  //即将选择的fragment

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private ImageView homeIv, orderIv, lifeIv, mineIv;
    private TextView homeTv, orderTv, lifeTv, mineTv;
    private LinearLayout homell, lifell;
    private RelativeLayout orderll, minell;

    //定义Fragment
    private HomeFragment homefragment; //首页
    private OrderFragment orderFragment;//订单
    private LifeFragment lifeFragment;//生活
    private MineFragment mineFragment;    //我的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findById();
        //初始化
        initView();
    }

    @Override
    public void onClick(View view) {
        onTabClicked(view);
    }

    private void findById() {
        homeIv = (ImageView) findViewById(R.id.act_main_v_home);
        orderIv = (ImageView) findViewById(R.id.act_main_v_order);
        lifeIv = (ImageView) findViewById(R.id.act_main_v_life);
        mineIv = (ImageView) findViewById(R.id.act_main_v_mine);
        homeTv = (TextView) findViewById(R.id.act_main_txt_home);
        orderTv = (TextView) findViewById(R.id.act_main_txt_order);
        lifeTv = (TextView) findViewById(R.id.act_main_txt_life);
        mineTv = (TextView) findViewById(R.id.act_main_txt_mine);
        homell = (LinearLayout) findViewById(R.id.act_main_llayout_home);
        lifell = (LinearLayout) findViewById(R.id.act_main_llayout_life);
        minell = (RelativeLayout) findViewById(R.id.act_main_llayout_mine);
        orderll = (RelativeLayout) findViewById(R.id.act_main_llayout_order);
    }

    private void initView() {
        homefragment = new HomeFragment();
        orderFragment = new OrderFragment();
        lifeFragment = new LifeFragment();
        mineFragment = new MineFragment();
        fragments = new Fragment[]{homefragment, orderFragment, lifeFragment, mineFragment};
        imagebuttons = new ImageView[]{homeIv, orderIv, lifeIv, mineIv};
        textviews = new TextView[]{homeTv, orderTv, lifeTv, mineTv};
        homeIv.setSelected(true);
        homeTv.setTextColor(0xFF45C01A);
        currentselected = HOME_FRAGMENT;
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        //加载各个fragment,并将Home页显示在前面
        transaction.add(R.id.act_main_flayout_content, homefragment)
                .add(R.id.act_main_flayout_content, orderFragment)
                .add(R.id.act_main_flayout_content, lifeFragment)
                .add(R.id.act_main_flayout_content, mineFragment)
                .hide(mineFragment).hide(lifeFragment)
                .hide(orderFragment).show(homefragment).commit();
        homell.setOnClickListener(this);
        orderll.setOnClickListener(this);
        lifell.setOnClickListener(this);
        minell.setOnClickListener(this);
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.act_main_llayout_home:
                select = HOME_FRAGMENT;
                break;
            case R.id.act_main_llayout_order:
                select = ORDER_FRAGMENT;
                break;
            case R.id.act_main_llayout_life:
                select = LIFE_FRAGMENT;
                break;
            case R.id.act_main_llayout_mine:
                select = MINE_FRAGMENT;
            default:
                break;
        }
        if (select != currentselected) {
            imagebuttons[currentselected].setSelected(false);  //图标改为未选中
            textviews[currentselected].setTextColor(0xFF999999);//文字颜色改为灰色
            transaction = manager.beginTransaction();            //开启事务
            transaction.hide(fragments[currentselected]);      //隐藏之前
            if (!fragments[select].isAdded()) {
                transaction.add(R.id.act_main_flayout_content, fragments[select]);
            }
            transaction.show(fragments[select]);
            transaction.commit();
            imagebuttons[select].setSelected(true);             //将图标设置为选中
            textviews[select].setTextColor(0xFF45C01A);          //设置文字颜色为绿色
            currentselected = select;
        }
    }

}
