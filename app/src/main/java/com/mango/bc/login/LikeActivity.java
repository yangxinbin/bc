package com.mango.bc.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.login.adapter.DuoXuanLikeAdapter;
import com.mango.bc.login.adapter.NoScrollGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LikeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.gv)
    NoScrollGridView gv;
    private DuoXuanLikeAdapter adapter;
    private Map<Integer, Boolean> gvChooseMap = new HashMap<Integer, Boolean>();
    private List<String> lists = new ArrayList();

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
                for (Map.Entry<Integer, Boolean> entry : gvChooseMap.entrySet()) {
                    switch (entry.getKey()) {
                        case 0:
                            lists.add("小白学堂");
                            break;
                        case 1:
                            lists.add("创业创新");
                            break;
                        case 2:
                            lists.add("技术开发");
                            break;
                        case 3:
                            lists.add("产业设计");
                            break;
                        case 4:
                            lists.add("通证经济");
                            break;
                        case 5:
                            lists.add("挖矿经济");
                            break;
                        case 6:
                            lists.add("数字资产");
                            break;
                        case 7:
                            lists.add("量化交易");
                            break;
                        case 8:
                            lists.add("投资策略");
                            break;
                        case 9:
                            lists.add("书记精讲");
                            break;
                        case 10:
                            lists.add("项目精讲");
                            break;
                        default:
                            break;
                    }

                }
                intent = new Intent(this, UserDetailActivity.class);
                if (getIntent().getBooleanExtra("perfect", false)) {
                    intent.putExtra("perfect", true);
                }
                intent.putExtra("identity", getIntent().getStringExtra("position"));
                intent.putExtra("hobbies",listToString(lists));
                startActivity(intent);
                finish();
                break;
        }
    }

    private String listToString(List<String> stringList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == stringList.size() - 1) {
                stringBuffer.append(stringList.get(i));
                break;
            }
            stringBuffer.append(stringList.get(i) + ",");
        }
        Log.v("uuuuuuuu", "----" + stringBuffer.toString());
        return stringBuffer.toString();
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
            intent = new Intent(this, PositionActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
