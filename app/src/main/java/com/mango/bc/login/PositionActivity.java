package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.login.adapter.DuoXuanAdapter;
import com.mango.bc.login.adapter.NoScrollGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PositionActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.gv)
    NoScrollGridView gv;
    private List<String> list;
    private DuoXuanAdapter adapter;
    private Map<Integer, Boolean> gvChooseMap = new HashMap<Integer, Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        ButterKnife.bind(this);
        initDate();
        initGird();
    }

    private void initDate() {
        list = new ArrayList<>();
        list.add("从业者");
        list.add("创业者");
        list.add("转型者");
        list.add("投资机构");
        list.add("散户");
        list.add("其它");
    }

    private void initGird() {
        adapter = new DuoXuanAdapter(this, list);
        gv.setAdapter(adapter);
        adapter.setCheckItem(gvChooseMap);
        gv.setOnItemClickListener(this);
    }

    @OnClick({R.id.imageView_back, R.id.button_next})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, BunblePhoneActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_next:
                intent = new Intent(this, LikeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (view.isActivated()) {
            view.setActivated(false);
            gvChooseMap.put(position, false);
        } else {
            view.setActivated(true);
            gvChooseMap.put(position, true);
        }
        adapter.setCheckItem(gvChooseMap);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            intent = new Intent(this, BunblePhoneActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
