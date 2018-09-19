package com.mango.bc.homepage.bookdetail.jsonutil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.homepage.bookdetail.bean.BookDetailBean;
import com.mango.bc.homepage.bookdetail.bean.CommentBean;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;

import java.util.List;

/**
 * Description :
 * Date   : 15/12/19
 */
public class JsonBookDetailUtils {

    public static List<CommentBean> readCommentBean(String res) {
        Gson gson = new Gson();
        List<CommentBean> commentBeans = gson.fromJson(res, new TypeToken<List<CommentBean>>() {
        }.getType());
        return commentBeans;
    }
    public static BookDetailBean readBookDetailBean(String res) {
        Gson gson = new Gson();
        BookDetailBean bookDetailBean = gson.fromJson(res, new TypeToken<BookDetailBean>() {
        }.getType());
        return bookDetailBean;
    }
}
