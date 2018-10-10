package com.mango.bc.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointDetailActivity extends BaseActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_position)
    EditText etPosition;
    @Bind(R.id.l_zoom)
    LinearLayout lZoom;
    @Bind(R.id.l_adress)
    LinearLayout lAdress;
    @Bind(R.id.l_et_all)
    LinearLayout lEtAll;
    @Bind(R.id.item_content1)
    EditText itemContent1;
    @Bind(R.id.item_content2)
    EditText itemContent2;
    @Bind(R.id.sure_apply)
    Button sureApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_detail);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imageView_back, R.id.tv_jump, R.id.l_zoom, R.id.l_adress, R.id.sure_apply})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, PointApplyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_jump:
                break;
            case R.id.l_zoom:
                intent = new Intent(this, PointZoomActivity.class);
                startActivityForResult(intent,0);
                finish();
                break;
            case R.id.l_adress:
                break;
            case R.id.sure_apply:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {

        }
    }

}
