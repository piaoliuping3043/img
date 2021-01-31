package vip.crazyboy.img.listener;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import vip.crazyboy.img.dao.BiZhiDao;
import vip.crazyboy.img.entity.BiZhiPO;
import vip.crazyboy.img.utils.StandardThreadExecutor;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description 爬虫
 * @author liupeng 
 * @updateTime 2020/10/18 8:08 下午 
 */
//@Component
@Slf4j
public class CrawlerListener implements ApplicationListener<ContextRefreshedEvent> {
    private static LinkedBlockingQueue<String> imgQueue = new LinkedBlockingQueue<String>();
    private static LinkedBlockingQueue<String> pageQueue = new LinkedBlockingQueue<String>();
    private static ExecutorService pageThreadExecutor;
    private static ExecutorService imgThreadExecutor;

    @Autowired
    @SuppressWarnings("all")
    private BiZhiDao biZhiDao;

    static {
        pageThreadExecutor = new StandardThreadExecutor(20,20,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>(),"page");
        imgThreadExecutor = new StandardThreadExecutor(20,20,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>(),"img");
        int i = 1;
        while (i <= 643 ){
            pageQueue.add("https://sj.enterdesk.com/1080x1920/"+i+".html");
            i++;
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {
            while (true) {
                try {
                    String url = pageQueue.take();
                    pageThreadExecutor.execute(() -> {
                        log.info("正在解析:{}", url);
                        String response = null;
                        try {
                            response = HttpUtil.get(url, 20000);
                        } catch (Exception e) {
                            log.info("重新添加任务{}",url);
                            pageQueue.add(url);
                            return;
                        }
                        Document document = Jsoup.parse(response);
                        Elements elements = Optional.ofNullable(document).map(d -> d.getElementsByClass("egeli_pic_dl"))
                                .map(e -> e.select("dd"))
                                .map(e -> e.select("a"))
                                .orElse(new Elements());
                        elements.forEach(element -> {
                            String imgUrl = element.attr("href");
                            imgQueue.add(imgUrl);
                            log.info("添加壁纸任务:{}", imgUrl);
                        });
                    });
                } catch (Exception e) {
                    log.error("异常",e);
                }
            }
        }).start();

        new Thread(() -> {
            try {
                while (true){
                    String imgUrl = imgQueue.take();
                    imgThreadExecutor.execute(new Thread(() -> {
                        String kind = "热门";
                        String response = null;
                        try {
                            response = HttpUtil.get(imgUrl,20000);
                        } catch (Exception e) {
                            log.error("请求超时{}",imgUrl);
                            imgQueue.add(imgUrl);
                            return;
                        }
                        Document document = Jsoup.parse(response);
                        Elements elements = Optional.ofNullable(document).map(d -> d.getElementsByClass("arc_location"))
                                .map(e -> e.select("a"))
                                .orElse(new Elements());
                        if (elements.size()>=1){
                            Element element = elements.get(1);
                            if (StringUtils.isNotBlank(element.text())) {
                                kind = element.text();
                            }
                        }
                        Elements urlElements = Optional.ofNullable(document).map(d -> d.getElementsByClass("arc_main_pic_img")).orElse(new Elements());
                        String imgUrlLast = urlElements.get(0).attr("src");
                        log.info("分类:{}======地址:{}",kind,imgUrlLast);
                        BiZhiPO biZhiPO = new BiZhiPO();
                        biZhiPO.setCreateDate(new Date());
                        biZhiPO.setUpdateDate(new Date());
                        biZhiPO.setImgUrl(imgUrlLast);
                        biZhiPO.setType(kind);
                        biZhiPO.setTitle(urlElements.get(0).attr("title"));
                        biZhiPO.setShowed(1);
                        biZhiPO.setDownload(0);
                        biZhiDao.add(biZhiPO);
                    }));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
