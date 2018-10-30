package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.mango.bc.BcActivity;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.login.adapter.GirdDownAdapter;
import com.mango.bc.login.adapter.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_old)
    EditText etOld;
    @Bind(R.id.gv)
    NoScrollGridView gv;
    @Bind(R.id.et_zoom)
    EditText etZoom;
    @Bind(R.id.et_position)
    EditText etPosition;
    private List<String> list;
    private GirdDownAdapter adapter;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        initDate();
        initGird();
    }

    private void initDate() {
        list = new ArrayList<>();
        list.add("先生");
        list.add("女士");
    }

    private void initGird() {
        adapter = new GirdDownAdapter(this, list);
        gv.setAdapter(adapter);
        adapter.setCheckItem(currentPosition);
        gv.setOnItemClickListener(this);
    }

    @OnClick({R.id.imageView_back, R.id.tv_jump, R.id.button_next})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                if (getIntent().getBooleanExtra("perfect", false)) {
                    finish();
                } else {
                    intent = new Intent(this, LikeActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.tv_jump:
                intent = new Intent(this, BcActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_next:
                intent = new Intent(this, BcActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String date = list.get(position);
        currentPosition = position;
        adapter.setCheckItem(position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            intent = new Intent(this, LikeActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
