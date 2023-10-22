package edu.dhu.model;

import lombok.Data;

@Data
public class RespBean extends Throwable {
    private Integer code;
    private String message;
    private Object data;

    public static RespBean ok(String msg) {
        return new RespBean(200, msg, null);
    }

    public static RespBean ok(String msg, Object obj) {
        return new RespBean(200, msg, obj);
    }

    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    public static RespBean error(String msg, Object obj) {
        return new RespBean(500, msg, obj);
    }

    public RespBean(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
