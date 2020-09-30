package vip.crazyboy.img.dao;

import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;
import org.jfaster.mango.crud.CrudDao;
import vip.crazyboy.img.entity.ArticlePO;
import vip.crazyboy.img.entity.BiZhiPO;
import vip.crazyboy.img.entity.BiZhiVO;

import java.util.List;

@DB(name = "crawler", table = "bizhi")
public interface BiZhiDao extends CrudDao<BiZhiPO,Long> {

    @SQL("SELECT id,title,type,img_url,update_date FROM bizhi WHERE #if(null != :1) type = :1 and #end" +
            " showed = 1 ORDER BY update_date DESC LIMIT :2,:3")
    List<BiZhiPO> getBiZhiList(String type, Integer start, Integer end);

    @SQL("SELECT id,title,type,img_url FROM bizhi WHERE #if(null != :1) type = :1 and #end" +
            " showed = 1 ORDER BY update_date DESC LIMIT :2,:3")
    List<BiZhiVO> getBiZhiListVo(String type, Integer start, Integer end);

    @SQL("SELECT id,title,type,url,img_url,update_date FROM bizhi WHERE showed = 0 LIMIT :1,1")
    BiZhiPO getUnShowed(long id);
}
