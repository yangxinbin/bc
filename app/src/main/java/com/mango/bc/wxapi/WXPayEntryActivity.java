package com.mango.bc.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.mango.bc.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);
        sharedPreferences = getSharedPreferences("CIFIT", MODE_PRIVATE);
        api = WXAPIFactory.createWXAPI(this, "wxdb0ddd21496fccc3");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("pay", "onPayFinish, errCode = " + resp.errCode);
        if (resp.errCode == 0) {
            /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_tip);
			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
			builder.show();*/
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.icon)//设置标题的图片
                    .setTitle("报名成功")//设置对话框的标题
                    .setMessage("恭喜您报名成功！")//设置对话框的内容
                    //设置对话框的按钮
/*                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })*/
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //EventBus.getDefault().postSticky(bean1);
                            //Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
                            //intent.putExtra("id", sharedPreferences.getString("eventId", ""));
                            //startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        } else {
            //AppUtils.showToast(this, "支付失败，请您重新报名！");
            //Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
            //intent.putExtra("id", sharedPreferences.getString("eventId", ""));
            //startActivity(intent);
            finish();
        }

    }
}