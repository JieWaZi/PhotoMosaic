package TaskRunner;

import ImageMosaic.ImageConfig;
import ImageMosaic.ImageHandle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Rider on 2018/6/3.
 */
public class CalculateTaskRunner implements Runnable {
    private int pixel;
    private int x;
    private int y;
    private BufferedImage bufferedImage;

    public CalculateTaskRunner(int pixel, int x, int y, BufferedImage bufferedImage) {
        this.pixel = pixel;
        this.x = x;
        this.y = y;
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void run() {
        try {
            BufferedImage image = ImageIO.read(new File(ImageConfig.PICTURES + getSuitImage(pixel)));
            bufferedImage.getGraphics().drawImage(image, x * ImageConfig.BLOCK_WIDTH, y * ImageConfig.BLOCK_HEIGHT, ImageConfig.BLOCK_WIDTH, ImageConfig.BLOCK_HEIGHT, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSuitImage(int pleix) {
        Set<Map.Entry<String, int[]>> sets = ImageHandle.ImageMap().entrySet();
        int[] blockRGB = getBlockRGB(pleix);
        String suitImage = "";
        double minVariance = Double.MAX_VALUE;
        int i = 0;
        for (Map.Entry<String, int[]> entry : sets) {
            int[] imageRGB = entry.getValue();
            if (getVariance(blockRGB, imageRGB) <= minVariance) {
                minVariance = getVariance(blockRGB, imageRGB);
                suitImage = entry.getKey();
            }
        }
        return suitImage;
    }

    private double getVariance(int[] a, int[] b) {
        return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2) + Math.pow(a[2] - b[2], 2));
    }

    private int[] getBlockRGB(int pixel) {
        int[] rgb = new int[3];
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0x00ff00) >> 8;
        rgb[2] = (pixel & 0x0000ff);
        return rgb;
    }
}
