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
public class MarkPO {
    @ID
    private Long id;
    private Long userId;
    private Long imgId;

    private Date createTime;
    private Date updateTime;
}
