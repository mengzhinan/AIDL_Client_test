package com.fffffff.aidl_client_test

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fffffff.aidllib.IMyTestAidlInterface
import com.fffffff.aidllib.IMyTestCallback

class MainActivity : AppCompatActivity() {

    private lateinit var tvShow: TextView
    private var iMyTestAidlInterface: IMyTestAidlInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvShow = findViewById(R.id.tv_show)

        tvShow.setOnClickListener {
            // 点击 TextView 绑定服务
            BindServerUtil.bindService1(this, connection)
        }
    }

    // 服务绑定成功的回调
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            tvShow.text = "连接成功"

            iMyTestAidlInterface = IMyTestAidlInterface.Stub.asInterface(service)

            iMyTestAidlInterface?.searchKeyWord(404, "wuhan", object : IMyTestCallback {
                override fun asBinder(): IBinder? {
                    return null
                }

                override fun onResult(p0: String?) {
                    tvShow.text = p0
                }

                override fun onFailure(p0: String?) {
                    tvShow.text = p0
                }

            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            tvShow.text = "连接失败"
        }

    }
}