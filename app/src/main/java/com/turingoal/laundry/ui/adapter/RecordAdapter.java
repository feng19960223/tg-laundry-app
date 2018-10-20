package com.turingoal.laundry.ui.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.request.PostRequest;
import com.turingoal.common.app.TgSystemHelper;
import com.turingoal.common.base.TgBaseViewHolder;
import com.turingoal.common.bean.TgResponseBean;
import com.turingoal.common.utils.TgDateUtil;
import com.turingoal.common.utils.TgDialogUtil;
import com.turingoal.common.utils.TgHttpCallback;
import com.turingoal.common.utils.TgHttpUtil;
import com.turingoal.laundry.R;
import com.turingoal.laundry.bean.Record;
import com.turingoal.laundry.constants.ConstantActivityPath;
import com.turingoal.laundry.constants.ConstantUrls;
import com.turingoal.laundry.ui.activity.MainActivity;

/**
 * 流水单adapter
 */

public class RecordAdapter extends BaseQuickAdapter<Record, TgBaseViewHolder> {
    public RecordAdapter() {
        super(R.layout.item_record);
    }

    @Override
    protected void convert(final TgBaseViewHolder helper, final Record record) {
        helper.setText(R.id.tvTime, TgDateUtil.date2String(record.getCreateTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH))
                .setText(R.id.tvSendUnit, record.getSendUnit()) // 医院地址
                .setText(R.id.tvNameAndPhone, record.getUserName() + "：" + record.getUserPhone()) // 名字和电话
                .setText(R.id.tvStatus, getStatusStr(record.getStatus())) // 状态
                .setGone(R.id.tvCancel, record.getStatus().equals("1")); // 是否显示取消按钮
        // item点击事件
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                TgSystemHelper.handleIntentWithObj(ConstantActivityPath.RECORD, "record", record);
            }
        });
        // 取消点击事件
        helper.getView(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                cancel(record);
            }
        });
    }

    /**
     * 根据不同状态显示不同的状态字符串
     */
    private String getStatusStr(final String status) {
        if (status.equals("1")) {
            return mContext.getString(R.string.record_adapter_status1);
        } else if (status.equals("2")) {
            return mContext.getString(R.string.record_adapter_status2);
        } else {
            return mContext.getString(R.string.record_adapter_status3);
        }
    }

    /**
     * 取消一条流水
     */
    private void cancel(final Record record) {
        new MaterialDialog.Builder(mContext)
                .title(R.string.cancel)
                .content(R.string.record_cancel_hint)
                .positiveText(R.string.yes)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull final MaterialDialog dialog, @NonNull final DialogAction which) {
                        PostRequest request = TgHttpUtil.requestPost(ConstantUrls.URL_RECORD_DELETE, ((MainActivity) mContext).getHttpTaskKey());
                        request.params("id", record.getId());
                        request.execute(new TgHttpCallback(mContext) {
                            @Override
                            public void successHandler(final TgResponseBean result) {
                                if (result.isSuccess()) {
                                    mData.remove(record);
                                    notifyDataSetChanged();
                                } else {
                                    TgDialogUtil.showToast(result.getMsg()); // 弹出错误信息
                                }
                            }
                        });
                    }
                }).show();
    }
}
