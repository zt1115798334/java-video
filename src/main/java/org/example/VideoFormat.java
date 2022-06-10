package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2022/6/1 15:05
 * description:
 */
@Getter
@AllArgsConstructor
public enum VideoFormat {

    wmv("wmv"),
    asf("asf"),
    asx("asx"),
    rm("rm"),
    rmvb("rmvb"),
    mpg("mpg"),
    mpeg("mpeg"),
    mpe("mpe"),
    v_3gp("3gp"),
    mov("mov"),
    mp4("mp4"),
    m4v("m4v"),
    avi("avi"),
    dat("dat"),
    mkv("mkv"),
    flv("flv"),
    vob("vob");

    private final String string;
}
