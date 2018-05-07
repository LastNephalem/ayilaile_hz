package com.example.lucifer.ayilaile_hz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Lucifer on 2018/4/21.
 */

public class MineFragment extends Fragment implements View.OnClickListener{

    private static final int LOGINACTIVITY = 1;         //subActivity的方式启动LoginActivty获取返回值

    Button btnLogin;
    LinearLayout llLogin,llName,llMobile,llSave,llAcountRecord,llAddress,llSetting,llShare;
    TextView tvHeadName,tvHeadMobile,tvName,tvMobile,tvLogout,tvHotLine;

    Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mine = inflater.inflate(R.layout.fragment_mine, null);
        findView(mine);
        setAllOnClickListener();
        return mine;
    }

    private void setAllOnClickListener() {
        btnLogin.setOnClickListener(this);
        llName.setOnClickListener(this);
        llMobile.setOnClickListener(this);
        llSave.setOnClickListener(this);
        llAcountRecord.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        llSetting.setOnClickListener(this);
        llShare.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        tvHotLine.setOnClickListener(this);
    }

    private void findView(View view) {
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        llLogin = (LinearLayout) view.findViewById(R.id.ll_login);
        llName = (LinearLayout) view.findViewById(R.id.framine_myname_ll);
        llMobile = (LinearLayout) view.findViewById(R.id.framine_mysmobile_ll);
        llSave = (LinearLayout) view.findViewById(R.id.framine_mysave_ll);
        llAcountRecord = (LinearLayout) view.findViewById(R.id.framine_accountrecord_ll);
        llAddress = (LinearLayout) view.findViewById(R.id.framine_address_ll);
        llSetting = (LinearLayout) view.findViewById(R.id.framine_settings_ll);
        llShare = (LinearLayout) view.findViewById(R.id.framine_share_ll);
        tvHeadName = (TextView) view.findViewById(R.id.tv_userinfo_name);
        tvHeadMobile = (TextView) view.findViewById(R.id.tv_userinfo_phone);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvMobile = (TextView) view.findViewById(R.id.tv_mobile);
        tvLogout = (TextView) view.findViewById(R.id.tv_exit);
        tvHotLine = (TextView) view.findViewById(R.id.tv_hotline);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                intent = new Intent(getContext(), LoginActivity.class);
                startActivityForResult(intent,LOGINACTIVITY);
                /*llLogin.setVisibility(View.GONE);
                tvLogout.setVisibility(View.VISIBLE);
                tvHotLine.setVisibility(View.GONE);*/
                break;
            case R.id.framine_myname_ll:
                Toast.makeText(getContext(),"姓名",Toast.LENGTH_SHORT).show();
                break;
            case R.id.framine_mysmobile_ll:
                Toast.makeText(getContext(),"手机号",Toast.LENGTH_SHORT).show();
                break;
            case R.id.framine_mysave_ll:
                Toast.makeText(getContext(),"关注",Toast.LENGTH_SHORT).show();
                break;
            case R.id.framine_accountrecord_ll:
                Toast.makeText(getContext(),"付费记录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.framine_address_ll:
                Toast.makeText(getContext(),"地址",Toast.LENGTH_SHORT).show();
                break;
            case R.id.framine_settings_ll:
                Toast.makeText(getContext(),"设置",Toast.LENGTH_SHORT).show();
                break;
            case R.id.framine_share_ll:
                Toast.makeText(getContext(),"分享",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_exit:
                llLogin.setVisibility(View.VISIBLE);
                tvLogout.setVisibility(View.GONE);
                tvHotLine.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_hotline:
                Toast.makeText(getContext(),"服务热线：12580",Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(getContext(),"something is wrong!",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOGINACTIVITY:
                if(resultCode == RESULT_OK) {
                    Bundle b = data.getExtras();
                    tvMobile.setText(b.getString("phoneNumber"));
                    tvName.setText("id" + b.getString("phoneNumber"));
                    tvHeadMobile.setText(b.getString("phoneNumber"));
                    tvHeadName.setText("id" + b.getString("phoneNumber"));
                    llLogin.setVisibility(View.GONE);
                    tvLogout.setVisibility(View.VISIBLE);
                    tvHotLine.setVisibility(View.GONE);
                }
        }
    }
}
