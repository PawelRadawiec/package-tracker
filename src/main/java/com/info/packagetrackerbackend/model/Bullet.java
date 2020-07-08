package com.info.packagetrackerbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bullet {

    private BulletType code;
    private Boolean done;
    private String header;
    private String content;

    public Bullet(BulletType code, String header, String content) {
        this.code = code;
        this.header = header;
        this.content = content;
    }
}