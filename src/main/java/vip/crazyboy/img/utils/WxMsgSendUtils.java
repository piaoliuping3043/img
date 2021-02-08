package vip.crazyboy.img.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @description 微信消息发送工具类
 * 9097050026@chatroom
 * @author liupeng 
 * @updateTime 2021/1/29 2:27 下午 
 */
public class WxMsgSendUtils {
    private static String url = "http://49.232.149.57:8073/send";
    private static String robotWxId = "wxid_fjuu0pj2pon412";
    private static String toWxId = "20540595016@chatroom";


    public static void main(String[] args) throws Exception{
//        System.out.println(modify_group_notice(robotWxId, "20503795584@chatroom","佳辉沙雕"));
//        System.out.println(URLDecoder.decode(get_group_list(robotWxId),"utf-8"));

        // 封装返回数据结构
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "100");// Api数值（可以参考 - api列表demo）
        map.put("msg", URLEncoder.encode("中文测试", "UTF-8"));// 发送内容
        map.put("to_wxid", toWxId);// 对方id
        map.put("robot_wxid", robotWxId);// 账户id，用哪个账号去发送这条消息
        HttpRequest.post(url).timeout(3000).body(JSONObject.toJSONString(map),"application/x-www-form-urlencoded").execute().body();
//        return HttpUtils.postJson(url, JSONObject.toJSONString(map));
    }

    /**
     * 发送群消息并艾特某人
     *
     * @access public
     * @param  $robwxid 账户id，用哪个账号去发送这条消息
     * @param  $to_wxid 群id
     * @param  $at_wxid 艾特的id，群成员的id
     * @param  $at_name 艾特的昵称，群成员的昵称
     * @param  $msg     消息内容
     * @return string json_string
     */
    public static String send_group_at_msg(String robotWxId, String toWxGroupId, String atWxId, String atWxName, String msg){
        // 封装返回数据结构
        HashMap<String, String> map = Maps.newHashMap();
        map.put("type","102");
        map.put("msg",msg);
        map.put("to_wxid",toWxGroupId);
        map.put("at_wxid",atWxId);
        map.put("at_name",atWxName);
        map.put("robot_wxid",robotWxId);
        return HttpUtil.post(url, JSONObject.toJSONString(map));
    }


    /**
     * 修改群公告
     *
     * @access public
     * @param  string $robwxid     账户id
     * @param  string $group_wxid  群id
     * @param  string $notice      新公告
     * @return string json_string
     */
    public static String modify_group_notice(String robotWxId, String toWxGroupId, String notice){
        // 封装返回数据结构
        HashMap<String, String> map = Maps.newHashMap();
        map.put("type","308");
        map.put("notice",notice);
        map.put("group_wxid",toWxGroupId);
        map.put("robot_wxid",robotWxId);
        return JSONObject.toJSONString(map);
//        return HttpUtil.post(url, JSONObject.toJSONString(map));
    }


    /**
     * 取群聊列表
     *
     * @access public
     * @param  string $robwxid    账户id
     * @param  string $is_refresh 是否刷新
     * @return string 当前框架已登录的账号信息列表
     */
    public static String get_group_list(String robotWxId) throws Exception{
        // 封装返回数据结构
        HashMap<String, String> map = Maps.newHashMap();
        map.put("type","205");
        map.put("is_refresh","1");
        map.put("robot_wxid",robotWxId);
        return URLDecoder.decode(HttpUtil.post(url, JSONObject.toJSONString(map)),"utf-8");
    }
}
