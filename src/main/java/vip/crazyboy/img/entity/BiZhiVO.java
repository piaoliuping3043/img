package vip.crazyboy.img.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jfaster.mango.annotation.ID;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiZhiVO {
    @ID
    private Long id;
    //分类
    private String type;
    //标题
    private String title;
    //封面图片网址
    private String imgUrl;
}
