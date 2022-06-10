package org.example;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;

import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2022/6/2 14:59
 * description:
 */
public class UrlMain {
    public static void main(String[] args) {
        String url = "https://www.baidu.com.cn";
        QrCodeUtil.generate(url, 300, 300, Paths.get("/file/" + IdUtil.getSnowflakeNextIdStr() + ".jpg").toFile());

    }
}
