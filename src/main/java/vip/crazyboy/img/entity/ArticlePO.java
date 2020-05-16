package vip.crazyboy.img.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jfaster.mango.annotation.ID;
import org.jfaster.mango.annotation.Ignore;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePO {
    @ID
    private Long id;
    //分类
    private String type;
    //标题
    private String title;
    //网址
    private String url;
    //父网页地址
    private String fatherUrl;
    //封面图片网址
    private String imgUrl;
    //是否已经下载
    private Integer download;
    //是否展示
    private Integer showed;

    private Date createDate;
    private Date updateDate;

    @Ignore
    private String updateDateStr;
    @Ignore
    private ArticlePO beforeArt;
    @Ignore
    private ArticlePO nextArt;
}
