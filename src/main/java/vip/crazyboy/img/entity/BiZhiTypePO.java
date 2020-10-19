package vip.crazyboy.img.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class BiZhiTypePO {
    @ID
    @JsonIgnore
    private Long id;
    //分类
    private String type;
    //封面图片网址
    private String imgUrl;
    //是否展示
    @JsonIgnore
    private Integer showed;
    @JsonIgnore
    private Date createDate;
    @JsonIgnore
    private Date updateDate;
}
