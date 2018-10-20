package com.turingoal.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * 二维码工具类
 */

public class QrUtils {
    private QrUtils() {
        throw new Error("工具类不能实例化！");
    }

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    /**
     * 创建二维码
     *
     * @param wh  二维码宽高，如果<1,返回屏幕7/8
     * @param str 内容
     */
    public static Bitmap createQRBitmap(final Context context, final int wh, final String str) {
        // 设置二维码的宽高，start
        int smallerDimension = wh;
        if (wh < 1) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point displaySize = new Point();
            display.getSize(displaySize);
            int width = displaySize.x;
            int height = displaySize.y;
            smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 7 / 8;
        }
        // 得到二维码的宽高，end
        // 判断URL合法性
        String contents = str;
        if (TextUtils.isEmpty(str)) {
            contents = "-1";
        }
        String data = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                "?appid=wx70e08c5337ffd18d" +
                "&redirect_uri=https://66962ea5.ngrok.io/tg-laundry/wechat/handover/handover.app" +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=" + contents +
                "#wechat_redirect";
        // 生成二维码图片的格式，使用ARGB_8888
        Bitmap bitmap = Bitmap.createBitmap(smallerDimension, smallerDimension, Bitmap.Config.ARGB_8888);
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, smallerDimension, smallerDimension, hints);
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            int rqWidth = bitMatrix.getWidth();
            int rqHeight = bitMatrix.getHeight();
            int[] pixels = new int[rqWidth * rqHeight];
            for (int y = 0; y < rqHeight; y++) {
                int offset = y * rqWidth;
                for (int x = 0; x < rqWidth; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? BLACK : WHITE;
                }
            }
            bitmap.setPixels(pixels, 0, smallerDimension, 0, 0, smallerDimension, smallerDimension);
            // 显示到一个ImageView上面
            // imageView.setImageBitmap(bitmap);
            // imageView.setImageBitmap(addLogo(bitmap, BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher)));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 创建加logo的二维码
     */
    private static Bitmap createAddLogoQRBitmap(final Context context, final int wh, final String str, final Bitmap logoBitmap) {
        Bitmap qrBitmap = createQRBitmap(context, wh, str);
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, sx, qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }
}
