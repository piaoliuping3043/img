package vip.crazyboy.img.dao;

import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;
import org.jfaster.mango.crud.CrudDao;
import vip.crazyboy.img.entity.BiZhiTypePO;

import java.util.List;

@DB(name = "crawler", table = "bizhi_type")
public interface BiZhiTypeDao extends CrudDao<BiZhiTypePO,Long> {


    @SQL("SELECT type,img_url FROM bizhi_type WHERE showed=1 order by update_date desc")
    List<BiZhiTypePO> getAll();
}
