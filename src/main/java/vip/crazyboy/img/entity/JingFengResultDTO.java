package vip.crazyboy.img.entity;

import lombok.Data;

@Data
public class JingFengResultDTO<T> {
    private Integer errcode;
    private String globalTicket;
    private T data;
}
