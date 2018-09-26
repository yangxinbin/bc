package com.mango.bc.homepage.bean;

/**
 * Created by Administrator on 2018/9/26 0026.
 */

public class JumpToPlayDetailBean {
    private boolean idJump;

    public JumpToPlayDetailBean(boolean idJump) {
        this.idJump = idJump;
    }

    public boolean isIdJump() {
        return idJump;
    }

    public void setIdJump(boolean idJump) {
        this.idJump = idJump;
    }
}
