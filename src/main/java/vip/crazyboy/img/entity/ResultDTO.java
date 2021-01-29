package vip.crazyboy.img.entity;

import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer resCode;
    private String msg;
    private T data;
}
