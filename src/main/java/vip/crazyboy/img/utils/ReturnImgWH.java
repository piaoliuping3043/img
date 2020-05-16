package vip.crazyboy.img.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ReturnImgWH {
    /**
     * 获取网络图片的宽和高
     * @param url
     * @return
     */
    public static String getWidthAndHeight(String url) {
        try {
            InputStream is = new URL(url).openStream();
            BufferedImage sourceImg = ImageIO.read(is);
            int width = sourceImg.getWidth();
            int height = sourceImg.getHeight();
            return width+"*"+height;
        } catch (IOException e) {

        }
        return "";
    }
}