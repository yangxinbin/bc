package com.mango.bc.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.bc.R;
import com.mango.bc.mine.activity.FaqActivity;
import com.mango.bc.wallet.activity.CurrencyActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2018/9/10.
 */

public class MineFragment extends Fragment {
    @Bind(R.id.imageVie_pic)
    CircleImageView imageViePic;
    @Bind(R.id.tv_class)
    TextView tvClass;
    @Bind(R.id.l_class)
    LinearLayout lClass;
    @Bind(R.id.tv_get)
    TextView tvGet;
    @Bind(R.id.l_get)
    LinearLayout lGet;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.l_time)
    LinearLayout lTime;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.l_code)
    LinearLayout lCode;
    @Bind(R.id.l_to_vip)
    LinearLayout lToVip;
    @Bind(R.id.l_to_agent)
    LinearLayout lToAgent;
    @Bind(R.id.l_to_talent)
    LinearLayout lToTalent;
    @Bind(R.id.l_faq)
    LinearLayout lFaq;
    @Bind(R.id.l_service)
    LinearLayout lService;
    @Bind(R.id.l_setting)
    LinearLayout lSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.imageVie_pic, R.id.l_class, R.id.l_get, R.id.l_time, R.id.l_code, R.id.l_to_vip, R.id.l_to_agent, R.id.l_to_talent, R.id.l_faq, R.id.l_service, R.id.l_setting})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageVie_pic:
                break;
            case R.id.l_class:
                break;
            case R.id.l_get:
                break;
            case R.id.l_time:
                break;
            case R.id.l_code:
                break;
            case R.id.l_to_vip:
                break;
            case R.id.l_to_agent:
                break;
            case R.id.l_to_talent:
                break;
            case R.id.l_faq:
                intent = new Intent(getActivity(), FaqActivity.class);
                startActivity(intent);
                break;
            case R.id.l_service:
                break;
            case R.id.l_setting:
                break;
        }
    }
}
