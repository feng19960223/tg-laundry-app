package com.turingoal.common.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;

/**
 * SD卡相关工具类
 */
public class TgSDCardUtil {
    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return TgSDCardUtil.isSDCardEnable();
    }

    /**
     * 获取SD卡路径
     * <p>先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        return TgSDCardUtil.getSDCardPath();
    }

    /**
     * 获取SD卡data路径
     *
     * @return SD卡data路径
     */
    public static String getDataPath() {
        return TgSDCardUtil.getDataPath();
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    public static String getFreeSpace() {
        return TgSDCardUtil.getFreeSpace();
    }

    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo
     */
    public static String getSDCardInfo() {
        return TgSDCardUtil.getSDCardInfo();
    }

    /**
     * 将bitmap保存到本地
     */
    public static void saveImageToGallery(final Context context, final Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "mengbao");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            TgDialogUtil.showToast("保存成功");
            // 其次把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
        } catch (Exception e) {
            e.printStackTrace();
            TgDialogUtil.showToast("保存失败");
        }
    }
}
