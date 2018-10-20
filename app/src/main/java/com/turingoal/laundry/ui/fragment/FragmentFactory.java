package com.turingoal.laundry.ui.fragment;

import com.turingoal.common.base.TgBaseFragment;

import java.util.HashMap;

/**
 * Fragment工厂
 */

public class FragmentFactory {
    private static int currentFragment = 0; // 当前fragment
    public static final int FRAGMENT_HOME = 0; // 首页
    public static final int FRAGMENT_USER = 1; // 我
    private static HashMap<Integer, TgBaseFragment> mFragments = null; //Fragment缓存

    private FragmentFactory() {
        throw new Error("Fragment工厂类不能实例化！");
    }

    /**
     * 根据类型创建Fragment
     */
    public static TgBaseFragment createFragment(final int type) {
        currentFragment = type;
        if (mFragments == null) {
            mFragments = new HashMap<>();
        }
        TgBaseFragment fragment = mFragments.get(type);
        if (fragment == null) {
            switch (type) {
                case FRAGMENT_HOME:
                    fragment = new HomeFragment(); // 主页
                    break;
                case FRAGMENT_USER:
                    fragment = new UserFragment();  // 我
                    break;
                default:
                    break;
            }
            if (fragment != null) {
                mFragments.put(type, fragment); // 如果新new了Fragment，加到缓存中
            }
        }
        return fragment;
    }

    /**
     * 清理数据缓存，退出的时候要清理重新加载
     */
    public static void fragmetnFactoryClearData() {
        mFragments = null;
    }

    /**
     * 判断当前页面是否是首页
     */
    public static boolean isHomeFragment(){
        return currentFragment == FRAGMENT_HOME;
    }
}
