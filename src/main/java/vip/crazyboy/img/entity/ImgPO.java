package vip.crazyboy.img.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jfaster.mango.annotation.ID;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImgPO {
    @ID
    private Long id;
    //文章id
    private Long articleId;
    //文章标题
    private Long articleName;
    //图片链接
    private String url;
    //是否已经下载
    private Integer download;

    private Date createDate;
    private Date updateDate;
}
