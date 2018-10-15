package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.util.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class WechatLoginActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.imageVie_pic)
    CircleImageView imageViePic;
    @Bind(R.id.button_wechat_ok)
    Button buttonWechatOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_login);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("img") != null) {
            Glide.with(this).load(getIntent().getStringExtra("img")).into(imageViePic);
        } else {
            imageViePic.setImageDrawable(getResources().getDrawable(R.drawable.head_pic2));
        }
    }

    @OnClick({R.id.imageView_back, R.id.button_wechat_ok})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(WechatLoginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_wechat_ok:
                intent = new Intent(WechatLoginActivity.this, BcActivity.class);
                intent.putExtra("wechat", true);
                startActivity(intent);
                finish();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            intent = new Intent(WechatLoginActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
