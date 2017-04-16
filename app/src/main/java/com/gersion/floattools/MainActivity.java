package com.gersion.floattools;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn;
    private Subscription mSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (Build.VERSION.SDK_INT >= 23) {

            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
                Toast.makeText(this, "请先允许FloatTools出现在顶部", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {

        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
    }

    boolean isOpen = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Log.d("aa",isOpen+"");
                if (!isOpen){
                    Intent intent = new Intent(MainActivity.this, FloatBallService.class);
                    Bundle data = new Bundle();
                    data.putInt("type", FloatBallService.TYPE_ADD);
                    intent.putExtras(data);
                    startService(intent);
                    isOpen = true;
                    mBtn.setText("开启");
                }else{
                    Intent intent = new Intent(MainActivity.this, FloatBallService.class);
                    Bundle data = new Bundle();
                    data.putInt("type", FloatBallService.TYPE_DEL);
                    intent.putExtras(data);
                    startService(intent);
                    isOpen = false;
                    mBtn.setText("关闭");
                }
                break;
        }
    }

    int count = 2;
    @Override
    public void onBackPressed() {
        count--;

        mSubscribe = Observable.just("")
                .timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        count++;
                    }
                });

        if (count==0){
            MainActivity.super.onBackPressed();
        }else{
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe!= null&&!mSubscribe.isUnsubscribed()){
            mSubscribe.unsubscribe();
            mSubscribe = null;
        }
    }
}
