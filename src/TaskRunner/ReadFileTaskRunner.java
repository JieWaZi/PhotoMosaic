package TaskRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Rider on 2018/6/3.
 */
public class ReadFileTaskRunner implements Runnable {

    private ConcurrentHashMap<String, int[]> map;
    private File file;

    public ReadFileTaskRunner(ConcurrentHashMap<String, int[]> map, File file) {
        this.map = map;
        this.file = file;
    }

    @Override
    public void run() {
        map.put(file.getName(), getImageAvrRGB(file));
    }

    //获取图片的平均RGB
    private int[] getImageAvrRGB(File file) {
        int width = 0, height = 0;
        int[] rgb = new int[3];
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

}
