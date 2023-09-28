package com.fffffff.aidl_client_test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fffffff.aidllib.IMyTestAidlInterface;
import com.fffffff.aidllib.IMyTestCallback;

/**
 * @Author: duke
 * @DateTime: 2023-09-28 11:08:01
 * @Description: 功能描述：
 */
public class JavaActivity extends AppCompatActivity {

    private TextView textView;
    private IMyTestAidlInterface iMyTestAidlInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        textView = findViewById(R.id.tv_show_java);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击 TextView 绑定服务
                Intent intent = BindServerUtil.buildIntent1();
                BindServerUtil.bindService(JavaActivity.this, intent, connection);
            }
        });
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            textView.setText("连接成功");

            iMyTestAidlInterface = IMyTestAidlInterface.Stub.asInterface(service);
            try {
                iMyTestAidlInterface.searchKeyWord(400, "test_bj", new IMyTestCallback() {
                    @Override
                    public void onResult(String s) throws RemoteException {
                        textView.setText("获取成功 = " + s);
                    }

                    @Override
                    public void onFailure(String s) throws RemoteException {
                        textView.setText("获取失败 = " + s);
                    }

                    @Override
                    public IBinder asBinder() {
                        return null;
                    }
                });
            } catch (RemoteException e) {
                textView.setText("连接失败 = " + e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            textView.setText("连接断开");
        }
    };
}
