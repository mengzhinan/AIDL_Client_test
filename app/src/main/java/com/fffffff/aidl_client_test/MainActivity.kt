package com.fffffff.aidl_client_test

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fffffff.aidllib.IMyTestAidlInterface
import com.fffffff.aidllib.IMyTestCallback
import com.fffffff.aidllib.UserData

class MainActivity : AppCompatActivity() {

    private lateinit var tvShow: TextView
    private var iMyTestAidlInterface: IMyTestAidlInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvShow = findViewById(R.id.tv_show)

        tvShow.setOnClickListener {
            tvShow.isClickable = false
            // 点击 TextView 绑定服务
            val intent = BindServerUtil.buildIntent3()
            BindServerUtil.bindService(this, intent, connection)
        }
    }

    // 服务绑定成功的回调
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            tvShow.text = "连接成功"
            try {
                iMyTestAidlInterface = IMyTestAidlInterface.Stub.asInterface(service)

                val callback = createBinderCallback()
                iMyTestAidlInterface?.searchKeyWord(404, "wuhan", callback)
            } catch (e: Exception) {
                tvShow.text = "连接失败 error = ${e.message}"
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            tvShow.text = "连接断开"
        }

    }

    private fun createBinderCallback(): IMyTestCallback.Stub {
        return object : IMyTestCallback.Stub() {
            override fun onResult(p0: String, p1: UserData?) {
                tvShow.text = "$p0：\n" + "UserData = $p1\nint = ${p1?.percentage}\nmsg = ${p1?.msg}"
            }


            override fun onFailure(p0: String?) {
                tvShow.text = "获取失败 = $p0"
            }

        }
    }
}