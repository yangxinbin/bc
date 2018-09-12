package com.mango.bc.homepage.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.homepage.adapter.BookGirdAdapter;
import com.mango.bc.homepage.adapter.HistorySearchAdapter;
import com.mango.bc.homepage.net.presenter.BookPresenter;
import com.mango.bc.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @Bind(R.id.imageView_back)
    ImageView imageViewBack;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.l_delete)
    LinearLayout lDelete;
    @Bind(R.id.recycle_file)
    RecyclerView mRecyclerView;
    @Bind(R.id.img_search)
    ImageView imgSearch;
    @Bind(R.id.l_history)
    LinearLayout lHistory;
    @Bind(R.id.recycle)
    RecyclerView recycle;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.l_search_book)
    LinearLayout lSearchBook;
    private String SEARCH_HISTORY = "search_history";
    private ArrayList<String> historyList;
    private HistorySearchAdapter mHistorySearchAdapter;
    private StringBuilder sb;
    private SPUtils spUtils;
    private String longHistory;
    private BookGirdAdapter bookGirdAdapter;
    private BookPresenter bookPresenter;
    private final int TYPE = 5;//搜索课
    private int page = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
        initRecycle();
        initRecycleBook();

    }

    private void initRecycleBook() {
        bookGirdAdapter = new BookGirdAdapter(this);
        recycle.setLayoutManager(new GridLayoutManager(this, 3));
        recycle.setAdapter(bookGirdAdapter);
    }


    private void init() {
        spUtils = SPUtils.getInstance(SEARCH_HISTORY, this);
        longHistory = (String) spUtils.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longHistory.split(",");                            //split后长度为1有一个空串对象
        historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")) {          //如果没有搜索记录，split之后第0位是个空串的情况下
            historyList.clear();                                                 //清空集合，这个很关键
        }

    }

    private void initRecycle() {
        Log.v("yyyyyyyyyy", "====toList(longHistory)==" + longHistory);
        if (longHistory.equals("")) {
            mHistorySearchAdapter = new HistorySearchAdapter(new ArrayList<String>());
        } else {
            mHistorySearchAdapter = new HistorySearchAdapter(toList(longHistory));
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mHistorySearchAdapter);
        mHistorySearchAdapter.setOnItemClickLitener(new HistorySearchAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                etSearch.setText(toList(longHistory).get(position));
                lHistory.setVisibility(View.GONE);
            }

            @Override
            public void onDeleteClick(View view, int position) {
                List<String> hList = mHistorySearchAdapter.reMoveItem(position);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < hList.size(); i++) {
                    if (hList.size() - 1 == i) {
                        sb.append(hList.get(i));
                        break;
                    }
                    sb.append(hList.get(i) + ",");
                }
                spUtils.put(SEARCH_HISTORY, sb.toString());
                Log.v("yyyyyyyyyy", sb.toString() + "====onDeleteClick==" + position);
            }
        });


    }

    private List<String> toList(String value) {
        String[] arg = value.split(",");
        //List<String> list = Arrays.asList(arg);
        //List<String> list = new LinkedList(Arrays.asList(arg));//这个也行
        List<String> list = new ArrayList(Arrays.asList(arg));
/*        GsonJsonParser gson = new GsonJsonParser();
        List<String> list = (List<String>) gson.parse(value);*/
        Log.v("yyyyyyyyyy", "====list==" + list.size());
        return list;
    }

    @OnClick({R.id.imageView_back, R.id.l_delete, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.l_delete:
                historyList.clear();                            //清空集合
                spUtils.remove(SEARCH_HISTORY);                 //清空sp
                mHistorySearchAdapter.reMove();      //刷新适配器
                break;
            case R.id.img_search:
                etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {//键盘
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        //点击搜索按钮
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            String inputText = etSearch.getText().toString().trim();
                            //jumpPage(inputText);                              //获取搜索关键字跳页面
                            saveSearchHistory(inputText);                     //保存搜索历史
                            mHistorySearchAdapter.notifyDataSetChanged();        //刷新适配器
                            return true;
                        }

                        return false;
                    }
                });
                String inputText = etSearch.getText().toString().trim();
                //jumpPage(inputText);                              //获取搜索关键字跳页面
                saveSearchHistory(inputText);                     //保存搜索历史
                break;
        }
    }

    /**
     * 保存搜索记录
     *
     * @param inputText 输入的历史记录
     */
    private void saveSearchHistory(String inputText) {

        if (TextUtils.isEmpty(inputText)) {
            return;
        }
        Log.v("yyyyyyyyyy", "====sbb==" + inputText);

        String longHistory = (String) spUtils.getString(SEARCH_HISTORY, "");        //获取之前保存的历史记录

        String[] tmpHistory = longHistory.split(",");                            //逗号截取 保存在数组中

        historyList = new ArrayList<String>(Arrays.asList(tmpHistory));          //将改数组转换成ArrayList

        if (historyList.size() > 0) {
            //移除之前重复添加的元素
            for (int i = 0; i < historyList.size(); i++) {
                if (inputText.equals(historyList.get(i))) {
                    historyList.remove(i);
                    break;
                }
            }

            historyList.add(0, inputText);                           //将新输入的文字添加集合的第0位也就是最前面

            if (historyList.size() > 10) {
                historyList.remove(historyList.size() - 1);         //最多保存10条搜索记录 删除最早搜索的那一项
            }

            //逗号拼接
            sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                if (historyList.size() - 1 == i) {
                    sb.append(historyList.get(i));
                    break;
                }
                sb.append(historyList.get(i) + ",");
            }
            //保存到sp
            spUtils.put(SEARCH_HISTORY, sb.toString());
        } else {
            //之前未添加过
            spUtils.put(SEARCH_HISTORY, inputText + ",");
        }
        Log.v("yyyyyyyyyy", "======" + sb);
    }

}
