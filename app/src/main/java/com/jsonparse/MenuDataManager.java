package com.jsonparse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.util.ILog;

/**
 * Created by duke on 16-8-20.
 */
public class MenuDataManager {
    public MenuData getMenuData() {
        String json = "{\"mCHTitle\":\"热点\",\"mEngTitle\":\"Hot\",\"_id\":\"HOT_TAB\"}";
        Gson gson = new Gson();
        TabData tabData = gson.fromJson(json, TabData.class);
        ILog.d(tabData.toString());

        MenuData menuData = new MenuData();
        String menuJson = gson.toJson(menuData);
        ILog.d(menuJson);

        return menuData;
    }

    /* GSON 输出结果如下，是按照字符顺序的。对 FastJson 输出结果是无序的。
    {"JACKIE_ZHANG":"张学友"} {"ANDY_LAU":"刘德华"}{"LIMING":"黎明"}{"Aaron_Kwok":"郭富城"}
    {"JACKIE_ZHANG":"张学友"} {"ANDY_LAU":"刘德华"}{"LIMING":"黎明"}{"Aaron_Kwok":"郭富城"}
    {"JACKIE_ZHANG":"张学友"} {"ANDY_LAU":"刘德华"}{"LIMING":"黎明"}{"Aaron_Kwok":"郭富城"}
    {"JACKIE_ZHANG":"张学友"} {"ANDY_LAU":"刘德华"}{"LIMING":"黎明"}{"Aaron_Kwok":"郭富城"}
    {"JACKIE_ZHANG":"张学友"} {"ANDY_LAU":"刘德华"}{"LIMING":"黎明"}{"Aaron_Kwok":"郭富城"}
    * */
    public void test1(){
        String jsonStr = "{\"JACKIE_ZHANG\":\"张学友\",\"ANDY_LAU\":\"刘德华\",\"LIMING\":\"黎明\",\"Aaron_Kwok\":\"郭富城\"}" ;
        // 做5次测试
        for(int i=0,j=5;i<j;i++)  {
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(jsonStr);
            for(java.util.Map.Entry<String,JsonElement> entry:jsonObject.entrySet()){
                System.out.print(entry.getKey()+"-"+entry.getValue()+"\t");
            }
            System.out.println();  // 用来换行
        }
    }

}
