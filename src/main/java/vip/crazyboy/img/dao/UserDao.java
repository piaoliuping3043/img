package vip.crazyboy.img.dao;

import org.jfaster.mango.annotation.DB;
import org.jfaster.mango.annotation.SQL;
import org.jfaster.mango.crud.CrudDao;
import vip.crazyboy.img.entity.UserPO;

import java.util.List;

@DB(name = "crawler", table = "user")
public interface UserDao extends CrudDao<UserPO,Long> {

    @SQL("SELECT * FROM user WHERE user_name = :1 and password=:2")
    UserPO getUser(String userName, String password);
}
