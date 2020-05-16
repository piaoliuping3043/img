package vip.crazyboy.img.controller;

import cn.hutool.captcha.generator.RandomGenerator;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.crazyboy.img.config.EnvConfig;
import vip.crazyboy.img.dao.ArticleDao;
import vip.crazyboy.img.dao.ImgDao;
import vip.crazyboy.img.entity.ArticlePO;
import vip.crazyboy.img.entity.ImgPO;
import vip.crazyboy.img.utils.CommonUtil;
import vip.crazyboy.img.utils.RelativeDateFormat;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private EnvConfig envConfig;
    @Autowired
    @SuppressWarnings("all")
    private ImgDao imgDao;
    @Autowired
    @SuppressWarnings("all")
    private ArticleDao articleDao;

    LoadingCache<Long, ArticlePO> articleCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .maximumSize(3000)
            .initialCapacity(100)
            .build(new CacheLoader<Long, ArticlePO>() {
                @Override
                public ArticlePO load(Long id) {
                    return articleDao.getOne(id);
                }
            });


    @RequestMapping("/")
    public String getArticleList(String type, @RequestParam(defaultValue = "1") Integer pageNum, Model model){
        List<ArticlePO> po = articleDao.getArticleList(type, (pageNum-1) * CommonUtil.NUM_PAGE, CommonUtil.NUM_PAGE);
        if (StringUtils.isNotBlank(type) || null != type) {
            model.addAttribute("nextPageUrl", "/?type="+type+"&pageNum="+(pageNum+1));
        }else {
            model.addAttribute("nextPageUrl", "/?pageNum="+(pageNum+1));
        }
        publicVar(model, po);
        model.addAttribute("currentType", type);
        return "list";
    }

    @RequestMapping("/search")
    public String search(String keyWord, @RequestParam(defaultValue = "1") Integer pageNum, Model model){
        List<ArticlePO> po = articleDao.getByTitle("%"+keyWord+"%", (pageNum-1) * CommonUtil.NUM_PAGE, CommonUtil.NUM_PAGE);
        model.addAttribute("nextPageUrl", "?keyWord="+keyWord+"&pageNum="+(pageNum+1));
        publicVar(model, po);
        return "list";
    }


    @RequestMapping("/detail")
    public String index(Long id, Model model) throws Exception{
        ArticlePO articlePO = articleCache.get(id);
        List<ImgPO> allImgUrl = imgDao.getAllImgUrl(id);
        model.addAttribute("imgList", allImgUrl);
        model.addAttribute("article", articlePO);
        model.addAttribute("domainName", envConfig.getDomainName());
        model.addAttribute("currentType", articlePO.getType());
        return "detail";
    }

    @RequestMapping("/getNoShow")
    public String getUnShowed(Model model){
        int random = ThreadLocalRandom.current().nextInt(Long.valueOf(articleDao.count()).intValue());
        ArticlePO articlePO = articleDao.getUnShowed(random);
        List<ImgPO> allImgUrl = imgDao.getAllImgUrl(articlePO.getId());
        model.addAttribute("imgList", allImgUrl);
        model.addAttribute("article", articlePO);
        model.addAttribute("domainName", envConfig.getDomainName());
        return "detail_un_show";
    }

    @RequestMapping("/update")
    public String update(Long id,Integer state) throws Exception{
        ArticlePO articlePO = articleCache.get(id);
        articlePO.setShowed(state);
        articleDao.update(articlePO);
        if (state.equals(2)){
            return "redirect:/update";
        }
        return "redirect:/";
    }

    private void publicVar(Model model, List<ArticlePO> poList) {
        for (int i = 0; i < poList.size(); i++) {
            poList.get(i).setUpdateDateStr(RelativeDateFormat.format(poList.get(i).getUpdateDate()));
        }
        model.addAttribute("poList", poList);
        model.addAttribute("domainName", envConfig.getDomainName());
    }
}
