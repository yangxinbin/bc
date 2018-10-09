package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.login.adapter.DuoXuanLikeAdapter;
import com.mango.bc.login.adapter.NoScrollGridView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LikeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.gv)
    NoScrollGridView gv;
    private DuoXuanLikeAdapter adapter;
    private Map<Integer, Boolean> gvChooseMap = new HashMap<Integer, Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        ButterKnife.bind(this);
        initGird();
    }
    private void initGird() {
        adapter = new DuoXuanLikeAdapter(this);
        gv.setAdapter(adapter);
        adapter.setCheckItem(gvChooseMap);
        gv.setOnItemClickListener(this);
    }
    @OnClick({R.id.imageView_back, R.id.button_next})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_back:
                intent = new Intent(this, PositionActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_next:
                intent = new Intent(this, UserDetailActivity.class);
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
}
