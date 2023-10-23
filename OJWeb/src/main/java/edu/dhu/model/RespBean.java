package edu.dhu.model;

import lombok.Data;

@Data
public class RespBean extends Throwable {
    private Integer code;
    private String message;
    private Object data;

    public static RespBean ok(String msg) {
        return new RespBean(0, msg, null);
    }

    public static RespBean ok(String msg, Object obj) {
        return new RespBean(0, msg, obj);
    }

    public static RespBean error(String msg) {
        return new RespBean(400, msg, null);
    }

    public static RespBean error(String msg, Object obj) {
        return new RespBean(400, msg, obj);
    }

    public RespBean(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
