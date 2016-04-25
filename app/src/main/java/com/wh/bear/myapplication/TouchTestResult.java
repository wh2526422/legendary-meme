package com.wh.bear.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/25.
 */
public class TouchTestResult extends Activity implements View.OnClickListener{
    TextView tv_test1,tv_test2,tv_test3,tv_test4,tv_test5;
    Button btn_success,btn_failed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_result);
        Intent intent = getIntent();
        initUi(intent);
    }

    private void initUi(Intent intent) {
        ArrayList<String> result = intent.getStringArrayListExtra("result");

        tv_test1 = (TextView) findViewById(R.id.tv_test1);
        tv_test2 = (TextView) findViewById(R.id.tv_test2);
        tv_test3 = (TextView) findViewById(R.id.tv_test3);
        tv_test4 = (TextView) findViewById(R.id.tv_test4);
        tv_test5 = (TextView) findViewById(R.id.tv_test5);
        btn_failed = (Button) findViewById(R.id.btn_failed);
        btn_success = (Button) findViewById(R.id.btn_success);

        tv_test1.setText(result.get(1));
        tv_test2.setText(result.get(2));
        tv_test3.setText(result.get(3));
        tv_test4.setText(result.get(4));
        tv_test5.setText(result.get(5));

        btn_failed.setOnClickListener(this);
        btn_success.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_success:

                break;
            case R.id.btn_failed:

                break;
        }

        finish();
    }
}


