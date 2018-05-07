package com.example.lucifer.ayilaile_hz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Daily_Clean_Activity extends AppCompatActivity implements View.OnClickListener{
    @Override
    public void onClick(View view) {

    }

   /* @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }*/

    /*private String initStartDateTime = "2017年4月19日 14:44"; // 初始化开始时间

    private android.support.design.widget.AppBarLayout daily_Pic;//服务图片
    private TextView service_Name;//获得服务名称
    private TextView service_Dec;//获得服务描述
    private EditText time_Select;//服务时间
    private Button Confirm;
    private ImageButton service_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_clean);

        service_left = (ImageButton)findViewById(R.id.btn_daily_left);
        service_Name = (TextView)findViewById(R.id.service_name);
        service_Dec = (TextView)findViewById(R.id.service_dec);
        daily_Pic = (android.support.design.widget.AppBarLayout)findViewById(R.id.daily_pic);
        time_Select = (EditText)findViewById(R.id.time_select);
        Confirm = (Button)findViewById(R.id.icon_daily_confirm);
        Confirm.setOnClickListener(this);
        time_Select.setOnClickListener(this);
        service_left.setOnClickListener(this);

        Intent intent = getIntent();
        String icon = intent.getStringExtra("icon");
        switch (icon){
            case "1":
                break;
            case "2":
                daily_Pic.setBackgroundResource(R.drawable.daliy_dasaochu1);
                service_Name.setText("大扫除");
                service_Dec.setText("          大扫除工作由扫、擦、洗等各种作业组成，大扫除的三大清扫重点：玻璃、地板和油烟机。大扫除的三大清扫重点：玻璃、地板和油烟机。 ");
                break;
            case "3":
                daily_Pic.setBackgroundResource(R.drawable.daliy_shafa1);
                service_Name.setText("真皮沙发保养");
                service_Name.setTextColor(Color.WHITE);
                service_Dec.setText("          真皮沙发的比较重要的方法就是采用皮具护理液来对其经常擦拭，这样既可以起到清洁的作用，又能在真皮沙发的表面形成一层保护膜，让污渍不会很轻易地进入到真皮毛孔里面。");
                break;
            case "4":
                daily_Pic.setBackgroundResource(R.drawable.daliy_dala1);
                service_Name.setText("地板打蜡");
                service_Name.setTextColor(Color.WHITE);
                service_Dec.setText("          地板打蜡 木质地板防划伤、防干裂、防潮打蜡养护；石质地板清洗、上蜡、抛光防渗透处理。木质地板防划伤、防干裂、防潮打蜡养护；石质地板清洗、上蜡、抛光防渗透处理。");
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_daily_left:
                finish();
                break;
            case R.id.time_select:

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        this, initStartDateTime);
                dateTimePicKDialog.dateTimePicKDialog(time_Select);
                break;
            case R.id.icon_daily_confirm:
                Toast.makeText(getBaseContext(),"下单成功!", Toast.LENGTH_SHORT).show();
                finish();
            default:
                break;
        }
    }*/
}
