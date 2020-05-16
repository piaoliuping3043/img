package vip.crazyboy.img;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vip.crazyboy.img.dao.ArticleDao;
import vip.crazyboy.img.dao.ImgDao;
import vip.crazyboy.img.entity.ArticlePO;
import vip.crazyboy.img.entity.ImgPO;

import java.util.List;

@SpringBootTest
@Slf4j
class ImgApplicationTests {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ImgDao imgDao;

    @Test
    void contextLoads() {
        List<ArticlePO> all = articleDao.getAll();
        log.info("读取所有文章完毕");
        all.forEach(articlePO -> {
            String imgUrl = imgDao.getFirstImgUrl(articlePO.getId());
            if (StringUtils.isNotBlank(imgUrl)) {
                articlePO.setImgUrl(imgUrl);
            }
        });
        log.info("设置所有图片完毕");
        List<List<ArticlePO>> partition = Lists.partition(all, 50);
        partition.forEach(articlePOS -> {
            articleDao.update(articlePOS);
            log.info("批量更新50条");
        });


    }

}
