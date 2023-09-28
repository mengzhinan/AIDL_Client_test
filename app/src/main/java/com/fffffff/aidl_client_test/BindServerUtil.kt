package com.fffffff.aidl_client_test

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection

/**
 * @Author: duke
 * @DateTime: 2023-09-27 16:28:04
 * @Description: 功能描述：
 *
 */
object BindServerUtil {

    // 数据提供方(服务端)的 Service 包名 + 类名
    private const val SERVER_SERVICE_CLASS_NAME = "com.fffffff.aidlserver.MyService"

    // 数据提供方(服务端)的 Service 所在的可知行 Module 的包名，比如常见的 App Module
    // 不是 Service 所在的子 Module 的包名
    private const val SERVER_SERVICE_PACKAGE_NAME = "com.fffffff.aidl_server_test"

    // 数据提供方(服务端)的 Service 中定义的 Action 值
    private const val SERVER_SERVICE_ACTION = "com.fffffff.aidlserver.action.MY_SERVICE_CENTER"

    @JvmStatic
    fun buildIntent1(): Intent {
        val intent = Intent()
        // setComponent() 方式
        intent.component = ComponentName(SERVER_SERVICE_PACKAGE_NAME, SERVER_SERVICE_CLASS_NAME)
        return intent;
    }

    @JvmStatic
    fun buildIntent2(): Intent {
        val intent = Intent()
        // setClassName() 方式
        intent.setClassName(SERVER_SERVICE_PACKAGE_NAME, SERVER_SERVICE_CLASS_NAME)
        return intent
    }

    @JvmStatic
    fun buildIntent3(): Intent {
        val intent = Intent()
        // setAction 方式
        intent.action = SERVER_SERVICE_ACTION
        intent.setPackage(SERVER_SERVICE_PACKAGE_NAME)
        return intent
    }

    @JvmStatic
    fun bindService(activity: Activity?, intent: Intent, serviceConnection: ServiceConnection) {
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    @JvmStatic
    fun startService(activity: Activity?, intent: Intent) {
        activity?.startService(intent)
    }

}
