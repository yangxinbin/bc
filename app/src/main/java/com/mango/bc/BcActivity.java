package com.mango.bc;

import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.bookcase.BookcaseFragment;
import com.mango.bc.homepage.HomePageFragment;
import com.mango.bc.mine.MineFragment;
import com.mango.bc.view.BottomBar;
import com.mango.bc.wallet.WalletFragment;

public class BcActivity extends BaseActivity {

    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.bottom_bar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc);
        ButterKnife.bind(this);
        bottomBar.setContainer(R.id.container)
                .setTitleBeforeAndAfterColor("#333333", "#ffac00")
                .addItem(HomePageFragment.class,
                        "主页",
                        R.drawable.home,
                        R.drawable.home_select)
                .addItem(BookcaseFragment.class,
                        "书架",
                        R.drawable.bookcase,
                        R.drawable.bookcase_select)
                .addItem(WalletFragment.class,
                        "钱包",
                        R.drawable.wallet,
                        R.drawable.wallet_select)
                .addItem(MineFragment.class,
                        "我的",
                        R.drawable.my,
                        R.drawable.my_select)
                .build();
    }
}
