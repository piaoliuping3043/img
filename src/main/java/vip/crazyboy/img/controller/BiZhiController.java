package vip.crazyboy.img.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.crazyboy.img.dao.BiZhiDao;
import vip.crazyboy.img.dao.MarkDao;
import vip.crazyboy.img.dao.UserDao;
import vip.crazyboy.img.entity.*;
import vip.crazyboy.img.utils.CommonUtil;
import vip.crazyboy.img.utils.ResultVo;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/bizhi")
public class BiZhiController {

    @Autowired
    @SuppressWarnings("all")
    private BiZhiDao biZhiDao;

    @Autowired
    @SuppressWarnings("all")
    private UserDao userDao;

    @Autowired
    @SuppressWarnings("all")
    private MarkDao markDao;


    @RequestMapping("/show")
    public ResultVo getArticleList(String type, @RequestParam(defaultValue = "1") Integer pageNum, Model model){
        List<BiZhiVO> po = biZhiDao.getBiZhiListVo(type, (pageNum-1) * CommonUtil.NUM_PAGE, CommonUtil.NUM_PAGE);
        return ResultVo.success(po);
    }

    @RequestMapping("/login")
    public synchronized ResultVo login(Integer type, String userName, String password){
        switch (type){
            case 1:
                UserPO userPO = new UserPO();
                userPO.setUserName(userName);
                userPO.setPassword(password);
                userPO.setCreateTime(new Date());
                userPO.setUpdateTime(new Date());
                try {
                    userPO.setId(Long.valueOf(userDao.addAndReturnGeneratedId(userPO)));
                } catch (Exception e) {
                    return ResultVo.fail("用户已存在");
                }
                return ResultVo.success(userPO);
            case 2:
                UserPO userPo = userDao.getUser(userName, password);
                if (null == userPo){
                    return ResultVo.fail("用户不存在");
                }else {
                    return ResultVo.success(userPo);
                }
            default:
                return ResultVo.fail("指令错误");

        }
    }

    @RequestMapping("/mark/add")
    public ResultVo addMark(Long imgId, Long userId){
        MarkPO markPO = new MarkPO();
        markPO.setUserId(userId);
        markPO.setImgId(imgId);
        markPO.setCreateTime(new Date());
        markPO.setUpdateTime(new Date());
        try {
            markDao.add(markPO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.fail("重复添加");
        }
        return ResultVo.success(null);
    }

    @RequestMapping("/mark/get")
    public ResultVo getMark(Long userId){
        List<Long> marks = markDao.getMarks(userId);
        List<BiZhiVO> vos =biZhiDao.getByIds(marks);
        return ResultVo.success(vos);
    }
}
