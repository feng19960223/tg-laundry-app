package com.turingoal.laundry.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.common.app.TgApplication;
import com.turingoal.common.app.TgSystemHelper;
import com.turingoal.common.base.TgBaseActivity;
import com.turingoal.common.base.TgBaseFragment;
import com.turingoal.common.utils.BluetoothUtils;
import com.turingoal.laundry.R;
import com.turingoal.laundry.ReaderHolder;
import com.turingoal.laundry.constants.ConstantActivityPath;
import com.turingoal.laundry.ui.fragment.FragmentFactory;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ConstantActivityPath.MAIN)
public class MainActivity extends TgBaseActivity {

    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer; // fragment容器
    @BindView(R.id.tvReceive)
    TextView tvReceive; // 收污布草
    @BindView(R.id.tvSend)
    TextView tvSend; // 发干布草
    @BindView(R.id.fragmentFun)
    FrameLayout fragmentFun; // 收发控制器
    @BindView(R.id.ivHome)
    ImageView ivHome; // 首页图片
    @BindView(R.id.tvHome)
    TextView tvHome; // 首页
    @BindView(R.id.ivScan)
    ImageView ivScan; // 扫描
    @BindView(R.id.ivUser)
    ImageView ivUser; // 我图片
    @BindView(R.id.tvUser)
    TextView tvUser; // 我
    private TgBaseFragment mFragment; // 当前Fragment
    private ReaderHolder readerHolder;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialized() {
        readerHolder = ReaderHolder.getInstance();
        mFragment = FragmentFactory.createFragment(FragmentFactory.FRAGMENT_HOME); // 默认选中项
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mFragment).commit();
    }

    /**
     * OnClick
     */
    @OnClick({R.id.tvReceive, R.id.tvSend, R.id.fragmentFun, R.id.llHome, R.id.llScan, R.id.llUser})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.tvReceive:
                fragmentFun.setVisibility(View.GONE);
                ivScan.setImageResource(R.drawable.ic_scan);
                TgApplication.type = TgApplication.TYPE_RECEIVE;
                TgSystemHelper.handleIntent(ConstantActivityPath.RFID);
                break;
            case R.id.tvSend:
                fragmentFun.setVisibility(View.GONE);
                ivScan.setImageResource(R.drawable.ic_scan);
                TgApplication.type = TgApplication.TYPE_SEND;
                TgSystemHelper.handleIntent(ConstantActivityPath.RFID);
                break;
            case R.id.fragmentFun: // 点击非发送或收污区域，不显示
                fragmentFun.setVisibility(View.GONE);
                ivScan.setImageResource(R.drawable.ic_scan);
                break;
            case R.id.llHome:
                if (fragmentFun.getVisibility() == View.VISIBLE) { // 如果收发布草视图可见
                    fragmentFun.setVisibility(View.GONE);
                    ivScan.setImageResource(R.drawable.ic_scan);
                }
                ivUser.setImageResource(R.drawable.ic_user);
                tvUser.setTextColor(Color.parseColor("#FF9E9E9E"));
                ivHome.setImageResource(R.drawable.ic_home_current);
                tvHome.setTextColor(Color.parseColor("#FFE57373"));
                mFragment = FragmentFactory.createFragment(FragmentFactory.FRAGMENT_HOME); // 我
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mFragment).commit();
                break;
            case R.id.llScan:
                if (BluetoothUtils.isOpenBluetooth()) {
                    if (readerHolder.isConnected()) {
                        fragmentFun.setVisibility(View.VISIBLE);
                        ivScan.setImageResource(R.drawable.ic_scan_cancel);
                    } else {
                        Toast.makeText(this, getString(R.string.main_connected_hint), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.llUser:
                if (fragmentFun.getVisibility() == View.VISIBLE) { // 如果收发布草视图可见
                    fragmentFun.setVisibility(View.GONE);
                    ivScan.setImageResource(R.drawable.ic_scan);
                }
                ivHome.setImageResource(R.drawable.ic_home);
                tvHome.setTextColor(Color.parseColor("#FF9E9E9E"));
                ivUser.setImageResource(R.drawable.ic_user_current);
                tvUser.setTextColor(Color.parseColor("#FFE57373"));
                mFragment = FragmentFactory.createFragment(FragmentFactory.FRAGMENT_USER); // 我
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mFragment).commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (FragmentFactory.isHomeFragment()) {
            TgSystemHelper.dbClickExit(this); // 双击退出
        } else {
            mFragment = FragmentFactory.createFragment(FragmentFactory.FRAGMENT_HOME); // 默认选中项
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mFragment).commit();
        }
    }
}
