package com.yellowbee.gesturetools.activity

import android.Manifest
import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.RequestCallback
import com.yellowbee.gesturetools.R
import com.yellowbee.gesturetools.databinding.ActivityWelcomeBinding
import com.yellowbee.gesturetools.databinding.DialogPermissionButtonsBinding
import com.yellowbee.gesturetools.model.WelcomeViewModel
import com.yellowbee.gesturetools.service.GestureAccessibilityService
import com.yellowbee.gesturetools.utils.MyLog
import com.yellowbee.gesturetools.utils.ToastUtils

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding, WelcomeViewModel>() {


    /**
     * ActivityForResult推荐写法
     */
    private lateinit var mSomeActivityResultLauncher: ActivityResultLauncher<Intent>



    /**
     * 控制permissionX只调用一次
     */
    private var hasPermissionXGet = false

    /**
     * 检测无障碍服务
     */
    override fun onResume() {
        super.onResume()

        val enable =
            isAccessibilityServiceEnabled(
                this,
                GestureAccessibilityService::class.java
            )

        MyLog.d(TAG, "无障碍状态:$enable")
    }

    override fun init() {
        mSomeActivityResultLauncher = registerForActivityResult(
            StartActivityForResult(),
            ActivityResultCallback { result: ActivityResult ->

                MyLog.d(TAG, "result: ${result.resultCode}")

            }
        )
        mBinding.btRequestPermission.setOnClickListener {
            PermissionX.init(this).permissions(
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).request(object : RequestCallback {
                override fun onResult(
                    allGranted: Boolean,
                    grantedList: MutableList<String?>,
                    deniedList: MutableList<String?>
                ) {
                    if (allGranted && !hasPermissionXGet) {
                        hasPermissionXGet = true
                        requestManagerPermission()
                    } else if (!allGranted) {
                        deniedList.forEach {
                            val permissionName = it
                            // Permission is denied
                            ToastUtils.toast(this@WelcomeActivity, "${permissionName}被拒绝了，请在应用设置里打开权限")
                        }
                    } else {
                        requestManagerPermission()
                    }
                }
            })
        }
    }

    /**
     * 获取文件管理权限以及悬浮窗权限
     */
    private fun requestManagerPermission() {
        val inflater = LayoutInflater.from(this)
        val binding: DialogPermissionButtonsBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_permission_buttons, null, false)
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("系统权限请求")
            .setMessage("请手动获取以下权限：")
            .setView(binding.root) // 自定义四个按钮的布局
            .setCancelable(true)
            .create()
        dialog.show()
        binding.btnFilePermission.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()){
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = ("package:$packageName").toUri()
                startIntentForResult(intent)
            } else {
                ToastUtils.toast(this@WelcomeActivity, "文件管理权限已获取")
            }
        }

        binding.btnOverlayPermission.setOnClickListener {
            if(!Settings.canDrawOverlays(this@WelcomeActivity)){
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = ("package:$packageName").toUri()
                startIntentForResult(intent)
            } else {
                ToastUtils.toast(this@WelcomeActivity, "应用上层权限已获取")
            }
        }

        binding.btnAccessibilityPermission.setOnClickListener {
            if(!isAccessibilityServiceEnabled(this@WelcomeActivity, GestureAccessibilityService::class.java)){
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                ToastUtils.toast(this@WelcomeActivity, "无障碍权限已获取")
            }
        }

        binding.btnAlreadyGranted.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager() && isAccessibilityServiceEnabled(this@WelcomeActivity, GestureAccessibilityService::class.java) && Settings.canDrawOverlays(this@WelcomeActivity)){
                startMainActivity()
                dialog.cancel()
            } else {
                ToastUtils.toast(this@WelcomeActivity, "权限未全部获取")
            }
        }
    }

    /**
     * 手动重写startActivityForResult
     */
    private fun startIntentForResult(intent: Intent){
        mSomeActivityResultLauncher.launch(intent)
    }


    /**
     * 无障碍服务是否开启
     */
    fun isAccessibilityServiceEnabled(context: Context, serviceClass: Class<out AccessibilityService>): Boolean {
        val accessibilityEnabled = try {
            Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
            0
        }

        if (accessibilityEnabled != 1) {
            return false
        }

        val expectedComponentName =
            ComponentName(context, serviceClass)

        val enabledServices =
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            ) ?: return false

        val colonSplitter = TextUtils.SimpleStringSplitter(':')
        colonSplitter.setString(enabledServices )

        for (service in colonSplitter) {
            if (service.equals(expectedComponentName.flattenToString(), ignoreCase = true)) {
                return true
            }
        }

        return false
    }

    /**
     * 进入主界面
     */
    private fun startMainActivity() {
        val intent =  Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initView() {
        setStatusBarDark()
    }

    override fun release() {
    }

    companion object {
        private const val TAG = "WelcomeActivity"
    }
}