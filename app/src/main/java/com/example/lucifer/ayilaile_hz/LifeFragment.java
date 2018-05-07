package com.example.lucifer.ayilaile_hz;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lucifer.ayilaile_hz.adapter.LifeAdapter;
import com.example.lucifer.ayilaile_hz.bean.LifeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lucifer on 2018/4/21.
 */

public class LifeFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;

    private LifeItem[] fruits = {
            new LifeItem("孩子成长过程中应该注意的事", R.drawable.child2),
            new LifeItem("对人体有益的水果精选", R.drawable.banana),
            new LifeItem("父母如何与孩子建立友好的关系", R.drawable.child3),
            new LifeItem("适合度假的风景区精选", R.drawable.fengjing2),
            new LifeItem("美好的人生该是什么样子", R.drawable.car2),
            new LifeItem("萌宠带来的乐趣", R.drawable.animal2),
            new LifeItem("汽车之家精选", R.drawable.car3),
            new LifeItem("精致生活", R.drawable.car1),
            new LifeItem("周末大优惠", R.drawable.youhui1),
            new LifeItem("足球人生", R.drawable.zuqiu1)};

    private List<LifeItem> fruitList = new ArrayList<>();

    private LifeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View life = inflater.inflate(R.layout.fragment_life, null);

        initFruits();
        RecyclerView recyclerView = (RecyclerView) life.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout)life.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshLife();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LifeAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        return life;
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    private void refreshLife() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Activity activity = (Activity)getContext();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
