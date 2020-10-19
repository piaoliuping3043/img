package vip.crazyboy.img.dao;

import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;
import org.jfaster.mango.crud.CrudDao;
import vip.crazyboy.img.entity.BiZhiPO;
import vip.crazyboy.img.entity.ImgPO;

import java.util.List;

@DB(name = "crawler", table = "img_bizhi")
public interface ImgBiZhiDao extends CrudDao<BiZhiPO,Long> {

    @SQL("SELECT url FROM img WHERE article_id = :1")
    List<ImgPO> getAllImgUrl(long articleLd);

    @SQL("SELECT url FROM img WHERE article_id = :1 limit 1")
    String getFirstImgUrl(long articleLd);
}
