package vip.crazyboy.img.monitor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vip.crazyboy.img.entity.Items;
import vip.crazyboy.img.entity.ItemsDTO;
import vip.crazyboy.img.entity.JingFengResultDTO;
import vip.crazyboy.img.utils.DateUtils;
import vip.crazyboy.img.utils.GsonUtils;
import vip.crazyboy.img.utils.StandardThreadExecutor;

import javax.annotation.PostConstruct;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JingFengMonitor {
    //线程池
    public static final ExecutorService MONITOR_THREAD_EXECUTOR = new StandardThreadExecutor(5,5,10,TimeUnit.SECONDS,new LinkedBlockingQueue<>(),"monitor");
    private static Logger logSmart = LoggerFactory.getLogger("LOG_SMART");
    private static Logger logError = LoggerFactory.getLogger("LOG_ERROR");
    private static Set<String> wxGroupIds = Sets.newHashSet();
    private static String url = "http://49.232.149.57:8073/send";
    private static String robotWxId = "wxid_fjuu0pj2pon412";
    //是否打开测试
    private static boolean openTest = false;


    static {
//        wxGroupIds.add("9097050026@chatroom");//吃鸡小分队
//        wxGroupIds.add("2750449706@chatroom");//敬君神通,伏地呼兄
        wxGroupIds.add("20503795584@chatroom");//对外监控群
//        wxGroupIds.add("5082200292@chatroom");//七人行
//        wxGroupIds.add("18903736578@chatroom");//监控测试
//        openTest = true;
    }
    @PostConstruct
    public void monitor(){
//        MONITOR_THREAD_EXECUTOR.execute(() -> begainMonitor());
    }

    private void begainMonitor() {
        String url = "http://100000552840.yuyue.n.weimob.com/api3/interactive/advance/microbook/mobile/getAvailableCalendar";
        String cookies = "rprm_cuid=3124274306btvhslvm38; saas.express.session=s%3A476XqGxLYN0uUw42PyCtn1HAtZs4WLG_.nrGD9ynJJzyoSgzICk4%2FPu35qry8l%2Bd9jPus8f8r6%2Fs";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sno", "12556");
        hashMap.put("pid", "100000552840");
        long lastNotifyTime = System.currentTimeMillis() - 3 * 60 * 1000;
        long lastSendTime = System.currentTimeMillis() -  30 * 1000;
        while (true){
            if (DateUtils.isBelong(new Date(),"23","6",true)) {
                try {
                    logSmart.info("免打扰时间.");
                    Thread.sleep(1000 * 60 * 30);
                    continue;
                } catch (Exception e) {
                    logError.error("计算是否在免打扰时间出错",e);
                }
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(3000,9000));
                HttpRequest httpRequest = HttpRequest.post(url);
                httpRequest.cookie(cookies);
                httpRequest.header(Header.REFERER, "http://100000552840.yuyue.n.weimob.com/saas/yuyue/100000552840/12556/calendar?pno=", false);
                httpRequest.header(Header.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) MicroMessenger/6.8.0(0x16080000) MacWechat/2.4.2(0x12040210) NetType/WIFI WindowsWechat", false);
                httpRequest.header(Header.ACCEPT, "application/json, text/plain, */*");
                httpRequest.header(Header.CONNECTION, "keep-alive");
                httpRequest.header(Header.ACCEPT_ENCODING, "gzip, deflate, br");
                httpRequest.header(Header.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9");
                httpRequest.header(Header.CONTENT_TYPE, "application/json;charset=UTF-8");
                hashMap.put("calendarDate", System.currentTimeMillis()+(2 * 24 * 60 * 60 * 1000));
                String res = httpRequest.body(JSONUtil.toJsonPrettyStr(hashMap)).timeout(6000).execute().body();
                log.info(res);
                JingFengResultDTO<Items> resultDTO = GsonUtils.jsonToBean(res, new TypeToken<JingFengResultDTO<Items>>() {
                });
                for (ItemsDTO itemsDTO : resultDTO.getData().getItems()) {
                    openTest(itemsDTO);
                    if (!Integer.valueOf(0).equals(itemsDTO.getStockNum()) && itemsDTO.getStockNum() > 20 ) {
                        String msg = "京丰放货: \r  日期[ "+itemsDTO.getBookDivideTimes()+" ]\r  剩余存库[ "+itemsDTO.getStockNum()+" ]\r  当前时间[ "+ DateUtil.format(new Date(),"HH:mm:ss"+" ]");
                        //放糖提醒 & @所有人 间隔 3分钟
                        if (System.currentTimeMillis()-lastNotifyTime > 3*60*1000) {
                            wxGroupIds.forEach(s -> {
                                modifyGroupNotice("go! go! go!",s);
                            });
                            HttpUtil.get("https://sc.ftqq.com/SCU135702T4d9eb61083345ff9bc246a0e755e0b0d5fd879eaa898e.send?text="+msg);
                            logSmart.info("发送放糖和艾特所有人提醒." + msg);
                            lastNotifyTime = System.currentTimeMillis();
                        }

                        //普通群消息间隔 30S
                        if (System.currentTimeMillis() - lastSendTime > 30*1000){
                            wxGroupIds.forEach(s -> {
                                sendGroupMsg(msg, s);
                            });
                            logSmart.info("发送群消息提醒." + msg);
                            lastSendTime = System.currentTimeMillis();
                        }

                    }
                }
            } catch (Exception e) {
                logError.error("异常",e);
            }
        }
    }

    private void openTest(ItemsDTO itemsDTO) {
        if (openTest && itemsDTO.getBookDivideTimes().equals("2021-02-01")){
            itemsDTO.setStockNum(21);
        }
    }

    public static void sendGroupMsg(String msg, String groupId){
        try {
            // 封装返回数据结构
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("type", "100");// Api数值（可以参考 - api列表demo）
            map.put("msg", URLEncoder.encode(msg, "UTF-8"));// 发送内容
            map.put("to_wxid", groupId);// 对方id
            map.put("robot_wxid", robotWxId);// 账户id，用哪个账号去发送这条消息
            String result = HttpUtil.post(url, JSONObject.toJSONString(map));
            logSmart.info("发送群消息响应:[{}]",result);
        } catch (Exception e) {
            logError.error("发送群消息异常",e);
        }
    }

    public static void modifyGroupNotice(String notice, String groupId){
        try {
            // 封装返回数据结构
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("type", "308");// Api数值（可以参考 - api列表demo）
            map.put("notice", notice);// 发送内容
            map.put("group_wxid", groupId);// 对方id
            map.put("robot_wxid", robotWxId);// 账户id，用哪个账号去发送这条消息
            String result = HttpUtil.post(url, JSONObject.toJSONString(map));
            logSmart.info("设置群公告响应:[{}]",result);
        } catch (Exception e) {
            logError.error("设置群公告异常",e);
        }
    }
}
