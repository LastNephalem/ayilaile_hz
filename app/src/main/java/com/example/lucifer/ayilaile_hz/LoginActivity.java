package com.example.lucifer.ayilaile_hz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lucifer on 2018/4/24.
 */

public class LoginActivity extends AppCompatActivity implements AdapterView.OnClickListener {
    //10.0.2.2
    private String originAddress = "http://192.168.191.1:8080/AyilaileServerTest";
    private EditText edtPhone, edtpsd;
    private TextView tvGetCode, tvChange, tvLogin, tvHotLine;
    private String phoneNumber = "", password = "";
    private boolean psdOrcode = true; //默认密码登录

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        setAllOnClickListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    private void setAllOnClickListener() {
        tvLogin.setOnClickListener(this);
        tvGetCode.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvHotLine.setOnClickListener(this);
    }

    private void findView() {
        edtPhone = (EditText) findViewById(R.id.act_login_et_number);
        edtpsd = (EditText) findViewById(R.id.act_login_et_code);
        tvGetCode = (TextView) findViewById(R.id.act_login_txt_get_code);
        tvChange = (TextView) findViewById(R.id.chang_to_psd);
        tvLogin = (TextView) findViewById(R.id.act_login_txt_login);
        tvHotLine = (TextView) findViewById(R.id.act_loginservice_phone_tv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_login_txt_login:
                try {
                    if (psdOrcode)
                        loginBypsd();
                    else
                        loginBycode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.chang_to_psd:
                if (psdOrcode) {
                    psdOrcode = false;
                    tvChange.setText("密码登录");
                    tvGetCode.setVisibility(View.VISIBLE);
                } else {
                    psdOrcode = true;
                    tvChange.setText("验证码登录");
                    tvGetCode.setVisibility(View.GONE);
                }
                break;
            case R.id.act_login_txt_get_code:
                phoneNumber = edtPhone.getText().toString();
                sendCode("86",phoneNumber);
                break;
            default:
                break;
        }
    }

    //密码登录
    private void loginBypsd() throws IOException {
        phoneNumber = edtPhone.getText().toString();
        password = edtpsd.getText().toString();
        String userUrl = originAddress + "/login?phoneNumber=" + phoneNumber
                + "&password=" + password;
        if (isInputValid()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().get().url(userUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Error:", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String strResponse = response.body().string();
                    Log.e("Success", strResponse);
                    if ("failure".equals(strResponse)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                                edtpsd.setText("");
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent result = new Intent();
                                Bundle b = new Bundle();
                                b.putString("phoneNumber", phoneNumber);
                                b.putString("password", password);
                                result.putExtras(b);
                                setResult(RESULT_OK, result);
                                finish();
                            }
                        });
                    }
                }
            });
        } else {
            Toast.makeText(this, "输入有误，请重新输入", Toast.LENGTH_SHORT).show();
            edtpsd.setText("");
        }
    }


    //验证码登录
    private void loginBycode() {
        phoneNumber = edtPhone.getText().toString();
        password = edtpsd.getText().toString();
        submitCode("86", phoneNumber,password);
    }

    private boolean isInputValid() {
        //检查用户输入的合法性，这里暂且默认用户输入合法
        return true;
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Log.d("sendcode", "success");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,
                                    "短信已发送，请稍后",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d("sendcode", "failure");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,
                                    "获取验证码失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Log.d("submit code", "success");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,
                                    "登录成功",Toast.LENGTH_SHORT).show();
                            Intent result = new Intent();
                            Bundle b = new Bundle();
                            b.putString("phoneNumber", phoneNumber);
                            b.putString("password", password);
                            result.putExtras(b);
                            setResult(RESULT_OK, result);
                            finish();
                        }
                    });
                } else {
                    Log.d("submit code", "failure");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,
                                    "验证码输入有误，请重新输入",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }


}
