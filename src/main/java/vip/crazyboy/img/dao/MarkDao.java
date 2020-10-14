package vip.crazyboy.img.dao;

import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;
import org.jfaster.mango.crud.CrudDao;
import vip.crazyboy.img.entity.MarkPO;

import java.util.List;

@DB(name = "crawler", table = "mark")
public interface MarkDao extends CrudDao<MarkPO,Long> {

    @SQL("SELECT img_id FROM mark WHERE user_id = :1 order by update_time desc")
    List<Long> getMarks(Long userId);

    @SQL("delete FROM mark WHERE user_id = :1 and img_id= :2")
    void delMarks(Long userId, Long imgId);
}
