package ImageMosaic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by Rider on 2018/5/30.
 */
public class ImageUtil {

    private static volatile Map<String, int[]> map;

    public static int[][] getImageBlock(File file) {
        int width, height;
        int[][] block = {};
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
            block = new int[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    block[x][y] = bufferedImage.getRGB(x, y);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return block;
    }

    public static void initializeFiles(File dir) throws IOException {
        File[] files = dir.listFiles();
        int i = 0;
        for (File f : files) {
            if (f.isDirectory()) {
                initializeFiles(f);
            } else {
                ImageMap().put(f.getName(), ImageUtil.getImageAvrRGB(f));
            }
        }
        System.out.println("完成初始化 共有：" + ImageUtil.ImageMap().size());
    }

    //获取图片的平均RGB
    public static int[] getImageAvrRGB(File file) {
        int width = 0, height = 0;
        int[] rgb = new int[3];
        int i = 0;
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = bufferedImage.getRGB(x, y);
                    rgb[0] = rgb[0] + ((pixel & 0xff0000) >> 16);
                    rgb[1] = rgb[1] + ((pixel & 0x00ff00) >> 8);
                    rgb[2] = rgb[2] + ((pixel & 0x0000ff));

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        rgb[0] = rgb[0] / (width * height);
        rgb[1] = rgb[1] / (width * height);
        rgb[2] = rgb[2] / (width * height);
        return rgb;
    }

    public static int[] getBlockRGB(int pixel) {
        int[] rgb = new int[3];
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0x00ff00) >> 8;
        rgb[2] = (pixel & 0x0000ff);
        return rgb;
    }


    public static Map<String, int[]> ImageMap() {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

}
