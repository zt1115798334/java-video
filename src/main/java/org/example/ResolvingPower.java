package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2022/4/25
 * description:
 */
@AllArgsConstructor
@Getter
public enum ResolvingPower {
    QHD("540p", 960, 540),
    HD("720P", 1280, 720),
    FHD("1080p", 1920, 1080);


    private final String p;
    private final Integer with;
    private final Integer high;
}
