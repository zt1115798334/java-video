package org.example;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.IplImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author ${USER}
 * date: ${DATE} ${TIME}
 * description:
 */
public class Main {
    public static void main(String[] args) throws Exception {
// 视频路径换成自己的http://www.baidu.com/group1/M00/00/9E/rBIuClzSQHaAPClMAA5OVT6OM7w328.mp4
        long start = System.currentTimeMillis();
//        randomGrabberFFmpegImage("http://121.199.38.177:8795/static/8adf31c9f2391b47f429858d6a44239d.mp4", "E:\\file", "111");
//        randomGrabberImage("http://127.0.0.1:9000/file/%E5%85%9A%E6%97%97%E9%A3%98%E6%89%AC%E6%9C%89%E9%9F%B3%E4%B9%905%E5%88%86%E9%92%9F.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220527%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220527T053232Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=60675b80a85a6e54be85a4ec97cc22981d2824bcf3097307196272c8496c1d51", "E:\\file", "111");
//        randomGrabberFFmpegImage("E:\\file\\Fanney Khan 2018 Hindi 1080p.mkv", "E:\\file", "111");
//        long audioTime = getAudioTime("E:\\迅雷下载\\Virus 32 (2022) [Azerbaijan Dubbed] 1080p WEB-DLRip Saicord.mp4");
//        int audioTime = getAudioTime("http://127.0.0.1:9000/file/video/1531862491213828096.mp4");
//        System.out.println(audioTime);
//        LocalTime localTime = LocalTime.ofSecondOfDay(audioTime);
//        System.out.println(localTime);
//        System.out.println(System.currentTimeMillis() - start);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (ResolvingPower value : ResolvingPower.values()) {
            executorService.execute(
                    () -> {
                        try {
                            pVideo(value);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    });
        }
        executorService.shutdown();
        Thread.sleep(10000000);
//        String filePath = "E:\\英雄时刻\\117508834\\英雄时刻_20200410-21点38分46s.avi";
//        String s = SecureUtil.md5(Paths.get(filePath).toFile());
//        System.out.println("s = " + s);
//        String str = "http://127.0.0.1:9000/file/video1531862491213828096.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=admin%2F20220602%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220602T013720Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=9ab7f4cd0975b7282801817f7ab23a4c3c0d17265bc405181773550c691a62aa";
//        URL url = URLUtil.url(str);
//        System.out.println(StrUtil.removeSuffix(str,"?"+url.getQuery()));
//        System.out.println("URL is " + url.toString());
//        System.out.println("protocol is " + url.getProtocol());
//        System.out.println("file name is " + url.getFile());
//        System.out.println("host is " + url.getHost());
//        System.out.println("path is " + url.getPath());
//        System.out.println("port is " + url.getPort());
//        System.out.println("default port is " + url.getDefaultPort());

    }


    public static void pVideo(ResolvingPower resolvingPower) throws Exception {
        long start = System.currentTimeMillis();
        String p = modifyResolution("E:\\英雄时刻\\117508834\\英雄时刻_20200410-21点38分46s.avi", "E:\\笔记", resolvingPower, null);
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + "分辨率:" + resolvingPower.getP() + " path：" + p);
    }

    /**
     * @param filePath       视频路径
     * @param targetFilePath 第一帧图片存储位置
     * @param targetFileName 图片名称
     */
    public static void randomGrabberImage(String filePath, String targetFilePath, String targetFileName)
            throws Exception {
        try (FFmpegFrameGrabber ff = new FFmpegFrameGrabber(filePath)) {
            ff.start();
            String rotate = ff.getVideoMetadata("rotate");
            int ftp = ff.getLengthInFrames();
            Frame frame = null;
            int flag = 0;
            while (flag <= ftp) {
                frame = ff.grabImage();
                IplImage src;
                if (null != rotate && rotate.length() > 1) {
                    try (OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage()) {
                        src = converter.convert(frame);
                        frame = converter.convert(rotate(src, Integer.parseInt(rotate)));
                    }
                }
                if ((flag > 100) && (frame != null)) {
                    break;
                }
                flag++;
                System.out.println("flag = " + flag);
            }
            doExecuteFrame(frame, targetFilePath, targetFileName);
            ff.stop();
        }
    }

    /*
     * 旋转角度的
     */
    public static IplImage rotate(IplImage src, int angle) {
        IplImage img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
        opencv_core.cvTranspose(src, img);
        opencv_core.cvFlip(img, img, angle);
        return img;
    }

    public static void doExecuteFrame(Frame f, String targetFilePath, String targetFileName) {
        if (null == f || null == f.image) {
            return;
        }
        try (Java2DFrameConverter converter = new Java2DFrameConverter()) {
            String imageMat = "jpg";
            String FileName = targetFilePath + File.separator + targetFileName + "." + imageMat;
            BufferedImage bi = converter.getBufferedImage(f);
            System.out.println("width:" + bi.getWidth());
            System.out.println("height:" + bi.getHeight());
            File output = new File(FileName);
            ImageIO.write(bi, imageMat, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getAudioTime(String filePath) {
        int durationInSec = 0;
        try (FFmpegFrameGrabber ff = new FFmpegFrameGrabber(filePath)) {
            ff.start();
            Map<String, String> metadata = ff.getMetadata();
            for (Map.Entry<String, String> stringStringEntry : metadata.entrySet()) {
                System.out.println(stringStringEntry.getKey()+":"+stringStringEntry.getValue());
            }
            durationInSec = (int) ff.getFormatContext().duration() / 1000000;
            ff.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return durationInSec;
    }


    /**
     * 修改视频分辨率
     *
     * @param imagePath 原视频地址
     * @param outputDir 输出目录
     * @param width     宽度
     * @param height    高度
     * @param bitRate   码率
     * @return 视频地址
     * @throws Exception 异常
     */
    public static String modifyResolution(String videoPath, String outputDir, ResolvingPower resolvingPower, Integer bitRate)
            throws Exception {
        if (bitRate == null) {
            bitRate = 2000;
        }
        String ext = FileUtil.extName(videoPath);
        boolean noneMatch = Arrays.stream(VideoFormat.values()).noneMatch(videoFormat -> videoFormat.getString().equals(ext));
        if (noneMatch) {
            throw new Exception("format error");
        }
        String resultPath = Paths.get(outputDir).resolve(IdUtil.getSnowflakeNextIdStr() + "." + ext).toString();
        String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
        ProcessBuilder builder =
                new ProcessBuilder(
                        ffmpeg,
                        "-i",
                        videoPath,
                        "-s",
                        MessageFormat.format("{0}*{1}", String.valueOf(resolvingPower.getWith()), String.valueOf(resolvingPower.getHigh())),
                        "-b",
                        MessageFormat.format("{0}k", String.valueOf(bitRate)),
                        resultPath);
        builder.inheritIO().start().waitFor();
        return resultPath;
    }

}