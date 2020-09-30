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
import vip.crazyboy.img.entity.ArticlePO;
import vip.crazyboy.img.entity.BiZhiPO;
import vip.crazyboy.img.entity.BiZhiVO;
import vip.crazyboy.img.utils.CommonUtil;
import vip.crazyboy.img.utils.ResultVo;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/bizhi")
public class BiZhiController {

    @Autowired
    @SuppressWarnings("all")
    private BiZhiDao biZhiDao;


    @RequestMapping("/show")
    public ResultVo getArticleList(String type, @RequestParam(defaultValue = "1") Integer pageNum, Model model){
        List<BiZhiVO> po = biZhiDao.getBiZhiListVo(type, (pageNum-1) * CommonUtil.NUM_PAGE, CommonUtil.NUM_PAGE);
        return ResultVo.success(po);
    }

}
