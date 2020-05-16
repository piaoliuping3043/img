package vip.crazyboy.img.dao;

import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;
import org.jfaster.mango.crud.CrudDao;
import vip.crazyboy.img.entity.ArticlePO;

import java.util.List;

@DB(name = "crawler", table = "article")
public interface ArticleDao extends CrudDao<ArticlePO,Long> {

    @SQL("SELECT id,title,type,url,img_url,update_date FROM article WHERE #if(null != :1) type = :1 and #end" +
            " showed = 1 ORDER BY update_date DESC LIMIT :2,:3")
    List<ArticlePO> getArticleList(String type, Integer start, Integer end);

    @SQL("SELECT id,title,type,url,img_url,update_date FROM article WHERE #if(null != :1) title like :1 and #end" +
            " showed = 1 ORDER BY update_date DESC LIMIT :2,:3")
    List<ArticlePO> getByTitle(String title, Integer start, Integer end);

    @SQL("SELECT id,title,type,url,img_url,update_date FROM article WHERE showed = 0 LIMIT :1,1")
    ArticlePO getUnShowed(long id);
}
