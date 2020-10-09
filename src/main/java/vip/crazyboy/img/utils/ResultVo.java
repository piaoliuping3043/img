package vip.crazyboy.img.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo {
    private Integer code;
    private String msg;
    private Object data;
    public static ResultVo success(Object data){
        return new ResultVo(0,"success",data);
    }
    public static ResultVo fail(String msg){
        return new ResultVo(1,msg,null);
    }
}
