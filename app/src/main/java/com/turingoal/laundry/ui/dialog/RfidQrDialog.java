package com.turingoal.laundry.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.turingoal.common.utils.QrUtils;
import com.turingoal.common.utils.TgDateUtil;
import com.turingoal.laundry.R;

import java.util.Date;

/**
 * 扫描前扫描结果生成带二维码的dialog
 */

public class RfidQrDialog extends Dialog {

    public RfidQrDialog(@NonNull Context context) {
        super(context);
    }

    public static class Builder {
        int totalNumber = 0; // 总数
        String recordNum = ""; // 单号
        String sendUnit = ""; // 发送单位
        String receiveUnit = ""; // 接收单位
        String workUser = ""; // 操作员
        String workUserPhone = ""; // 操作员电话
        String time = TgDateUtil.date2String(new Date(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH); // 扫描时间
        private Context context;
        private RfidQrDialog dialog;
        private View view; // 根布局
        private OnCancelClickListener onCancelClickListener; // 点击取消监听

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTotalNumber(int totalNumber) {
            this.totalNumber = totalNumber;
            return this;
        }

        public Builder setRecordNum(String recordNum) {
            this.recordNum = recordNum;
            return this;
        }

        public Builder setSendUnit(String sendUnit) {
            this.sendUnit = sendUnit;
            return this;
        }

        public Builder setReceiveUnit(String receiveUnit) {
            this.receiveUnit = receiveUnit;
            return this;
        }

        public Builder setWorkUser(String workUser) {
            this.workUser = workUser;
            return this;
        }

        public Builder setWorkUserPhone(String workUserPhone) {
            this.workUserPhone = workUserPhone;
            return this;
        }

        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public Builder addCancelListener(OnCancelClickListener onCancelClickListener) {
            this.onCancelClickListener = onCancelClickListener;
            return this;
        }

        public RfidQrDialog build() {
            dialog = new RfidQrDialog(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dialog_rfid_qr, null);
            TextView tvTotalNumber = view.findViewById(R.id.tvTotalNumber);
            TextView tvRecordNum = view.findViewById(R.id.tvRecordNum);
            TextView tvSendUnit = view.findViewById(R.id.tvSendUnit);
            TextView tvReceiveUnit = view.findViewById(R.id.tvReceiveUnit);
            TextView tvWorkUser = view.findViewById(R.id.tvWorkUser);
            TextView tvTime = view.findViewById(R.id.tvTime);
            ImageView ivQr = view.findViewById(R.id.ivQr);
            if (onCancelClickListener != null) {
                view.findViewById(R.id.ivCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        onCancelClickListener.onCancelClick(dialog);
                    }
                });
            }
            tvTotalNumber.setText(String.format(context.getString(R.string.rfid_total_number), totalNumber));
            tvRecordNum.setText(context.getText(R.string.rfid_record_num) + recordNum);
            tvSendUnit.setText(context.getText(R.string.rfid_sendUnit) + sendUnit);
            tvReceiveUnit.setText(context.getText(R.string.rfid_receiveUnit) + receiveUnit);
            tvWorkUser.setText(context.getText(R.string.rfid_workUser) + workUser + " " + workUserPhone);
            tvTime.setText(context.getText(R.string.rfid_time) + time);
            ivQr.setImageBitmap(QrUtils.createQRBitmap(context, 0, recordNum));
            dialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setCancelable(false);
            // 设置dialog宽度和屏幕一样
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
            dialog.getWindow().setAttributes(params);
            return dialog;
        }

        public interface OnCancelClickListener {
            void onCancelClick(RfidQrDialog dialog);
        }
    }
}
