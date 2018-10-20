package com.turingoal.laundry.ui.activity;

import android.Manifest;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzy.okgo.request.PostRequest;
import com.turingoal.common.app.TgApplication;
import com.turingoal.common.app.TgSystemHelper;
import com.turingoal.common.base.TgBaseActivity;
import com.turingoal.common.bean.TgResponseBean;
import com.turingoal.common.utils.TgDialogUtil;
import com.turingoal.common.utils.TgHttpCallback;
import com.turingoal.common.utils.TgHttpUtil;
import com.turingoal.common.utils.permission.PermissionListener;
import com.turingoal.common.utils.permission.PermissionManager;
import com.turingoal.common.widget.ClearEditText;
import com.turingoal.laundry.R;
import com.turingoal.laundry.constants.ConstantActivityPath;
import com.turingoal.laundry.constants.ConstantUrls;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 */
@Route(path = ConstantActivityPath.LOGIN)
public class LoginActivity extends TgBaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle; // 标题
    @BindView(R.id.etUsername)
    ClearEditText etUsername;
    @BindView(R.id.tilUsername)
    TextInputLayout tilUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private PermissionManager helper; // 权限
    private static final int MAX_STRING_LEN = 24; // 用户名和密码最大长度
    private static final int MIN_USERNAME_LEN = 5; // 用户名最小长度
    private static final int MIN_PASSWORD_LEN = 6; // 密码最小长度
    private boolean isUsernameEnable = false; // 用户名是否正确
    private boolean isPasswordEnable = false; // 密码是否正确
    private static final int REQUEST_CODE = 1001; // 权限请求返回码

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initialized() {
        tvTitle.setText(R.string.activity_login);
        // 权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE};
            requestPermissions(mPermissionList, REQUEST_CODE);
        }
        etUsername.setText(TgSystemHelper.getUsername()); // 用户名
        if (!TextUtils.isEmpty(etUsername.getText().toString().trim())) { // 用户名没有内容
            isUsernameEnable = true;
        }
        // 监听
        etUsername.addTextChangedListener(userNameTextWatcher); // 用户名内容监
        etPassword.addTextChangedListener(passwordTextWatcher); // 密码内容监听
        tilPassword.setPasswordVisibilityToggleEnabled(true); // 点击显示密码
        if (isUsernameEnable && isPasswordEnable) { // 用户名和密码同时正确才可以点
            btnLogin.setEnabled(true); // 启用提交按钮
        } else {
            btnLogin.setEnabled(false); // 按钮不可点击
        }
        etUsername.setSelection(etUsername.getText().toString().trim().length()); // 光标移动到文本框末尾
    }

    /**
     * OnClick
     */
    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        login();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPermission();
    }

    /**
     * 检查权限
     */
    private void initPermission() {
        helper = PermissionManager.with(this)
                // 添加权限请求码
                .addRequestCode(REQUEST_CODE)
                // 设置权限，可以添加多个权限
                .permissions(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE
                )
                // 设置权限监听器
                .setPermissionsListener(new PermissionListener() {
                    @Override
                    public void onGranted() {
                        // 当权限被授予时调用
                    }

                    @Override
                    public void onDenied() {
                        // 用户拒绝该权限时调用
                        initPermission();
                    }

                    @Override
                    public void onShowRationale(String[] permissions) {
                        helper.setIsPositive(true);
                        helper.request();
                    }
                }).request(); // 请求权限
    }

    /**
     * 6.0权限申请
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (helper != null) {
                    helper.onPermissionResult(permissions, grantResults);
                }
                break;
        }
    }


    /**
     * 用户名EditText监听
     */
    private TextWatcher userNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
        }

        @Override
        public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
        }

        @Override
        public void afterTextChanged(final Editable editable) {
            if (editable.toString().trim().length() != 0 && editable.toString().trim().length() < MAX_STRING_LEN) { // 3-24个字
                isUsernameEnable = true;
            } else {
                isUsernameEnable = false;
            }
            if (editable.toString().trim().length() > MAX_STRING_LEN) { // 字数大于最大限制，提示错误
                tilUsername.setError(getString(R.string.string_username_max_hint));
            } else {
                tilUsername.setErrorEnabled(false);
            }
            if (isUsernameEnable && isPasswordEnable) { // 用户名和密码同时正确才可以点
                btnLogin.setEnabled(true); // 启用提交按钮
            } else {
                btnLogin.setEnabled(false); // 按钮不可点击
            }
        }
    };

    /**
     * 密码EditText监听
     */
    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
        }

        @Override
        public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
        }

        @Override
        public void afterTextChanged(final Editable editable) {
            if (editable.toString().trim().length() != 0 && editable.toString().trim().length() < MAX_STRING_LEN) { // 3-24个字
                isPasswordEnable = true;
            } else {
                isPasswordEnable = false;
            }
            if (editable.toString().trim().length() > MAX_STRING_LEN) { // 字数大于最大限制，提示错误
                tilPassword.setError(getString(R.string.string_password_max_hint));
            } else {
                tilPassword.setErrorEnabled(false);
            }
            if (isUsernameEnable && isPasswordEnable) { // 用户名和密码同时正确才可以点
                btnLogin.setEnabled(true); // 启用提交按钮
            } else {
                btnLogin.setEnabled(false); // 按钮不可点击
            }
        }
    };

    /**
     * 登录
     */
    private void login() {
        if (etUsername.getText().toString().trim().length() < MIN_USERNAME_LEN) {
            tilUsername.setError(getString(R.string.string_username_min_hint));
            isUsernameEnable = false;
            btnLogin.setEnabled(false); // 按钮不可点击
            return;
        }
        if (etPassword.getText().toString().trim().length() < MIN_PASSWORD_LEN) {
            tilPassword.setError(getString(R.string.string_password_min_hint));
            isPasswordEnable = false;
            btnLogin.setEnabled(false); // 按钮不可点击
            return;
        }
        loginRequest(); // 登录网络请求
    }

    /**
     * 登录网络请求
     */
    private void loginRequest() {
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        PostRequest request = TgHttpUtil.requestPost(ConstantUrls.URL_LOGIN, getHttpTaskKey());
        request.params("username", username);
        request.params("password", password);
        request.execute(new TgHttpCallback(this) {
            @Override
            public void successHandler(final TgResponseBean result) {
                if (result.isSuccess()) { // 成功
                    TgSystemHelper.setUserInfo(result);
                    TgApplication.getTgUserPreferences().setLoginTime(Calendar.getInstance().getTimeInMillis()); // 本次登陆时间
                    TgSystemHelper.handleIntentAndFinish(ConstantActivityPath.MAIN, LoginActivity.this); // 跳转到主页面,关闭当前页面
                } else {
                    TgDialogUtil.showToast(result.getMsg()); // 登录失败，弹出错误信息
                }
            }
        });
    }
}
