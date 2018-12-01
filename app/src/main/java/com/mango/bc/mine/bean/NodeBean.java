package com.mango.bc.mine.bean;

/**
 * Created by admin on 2018/12/1.
 */

public class NodeBean {
    private boolean ifRefesh;

    public boolean isIfRefesh() {
        return ifRefesh;
    }

    public void setIfRefesh(boolean ifRefesh) {
        this.ifRefesh = ifRefesh;
    }

    public NodeBean(boolean ifRefesh) {
        this.ifRefesh = ifRefesh;
    }
}
