package com.mango.bc.homepage.net.jsonutils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mango.bc.homepage.net.bean.BookBean;
import com.mango.bc.homepage.net.bean.CompetitiveFieldBean;
import com.mango.bc.homepage.net.bean.FreeBookBean;

import java.util.List;

/**
 * Description :
 * Date   : 15/12/19
 */
public class JsonUtils {

    private final static String TAG = "NewsJsonUtils";

    /**
     * 将获取到的json转换为列表对象
     *
     * @param res
     * @param
     * @return
     */
/*    public static List<ListEventBean> readJsonEventBeans(String res, String va) {
        List<ListEventBean> beans = new ArrayList<ListEventBean>();
        JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
        //JsonObject ob = jsonObject.getAsJsonObject("responseObject");
        JsonArray jsonArray = jsonObject.getAsJsonArray(va);
        for (int i = 0; i < jsonArray.size(); i++) {
            ListEventBean news = JsonUtils.deserialize(jsonObject, ListEventBean.class);
            beans.add(news);//这里会将所有的json对象转换为bean对象
        }
        return beans;
    }*/

/*    public static List<ConfigBean> readJsonConfigBean(String res,String va) {
        List<ConfigBean> beans = new ArrayList<ConfigBean>();
        JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
        //JsonObject ob = jsonObject.getAsJsonObject("responseObject");
        JsonArray jsonArray = jsonObject.getAsJsonArray(va);
        for (int i = 0; i < jsonArray.size(); i++) {
            ConfigBean bean = JsonUtils.deserialize(jsonObject, ConfigBean.class);
            beans.add(bean);
        }
        return beans;
    }*/

/*    public static EventDetailBean readDetailBean(String res) {
        JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
        EventDetailBean eventDetailBean = JsonUtils.deserialize(jsonObject, EventDetailBean.class);
        return eventDetailBean;
    }*/
    public static List<CompetitiveFieldBean> readCompetitiveFieldBean(String res) {
        Gson gson = new Gson();
        List<CompetitiveFieldBean> competitiveFieldBeans = gson.fromJson(res, new TypeToken<List<CompetitiveFieldBean>>(){}.getType());
        return competitiveFieldBeans;
    }
    public static List<BookBean> readBookBean(String res) {
        Gson gson = new Gson();
        List<BookBean> bookBeans = gson.fromJson(res, new TypeToken<List<BookBean>>(){}.getType());
        return bookBeans;
    }
}
