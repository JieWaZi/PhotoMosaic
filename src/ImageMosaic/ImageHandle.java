package ImageMosaic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Rider on 2018/5/31.
 */
public class ImageHandle {

    private String inputPath;
    private String outputPath;

    public ImageHandle(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public void HandleImageConvert() throws IOException {
        int block[][] = ImageUtil.getImageBlock(new File(inputPath));
        BufferedImage outputImage = new BufferedImage(block.length * 20, block[0].length * 20, BufferedImage.TYPE_INT_RGB);
        Graphics bg = outputImage.getGraphics();
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                int pixel = block[i][j];
                String s = getSuitImage(pixel);
                BufferedImage image = ImageIO.read(new File("src/spiderPicture/" + getSuitImage(pixel)));
                bg.drawImage(image, i * 20, j * 20, 20, 20, null);
            }
        }
        ImageIO.write(outputImage, "jpg", new File(outputPath + System.currentTimeMillis() + ".jpg"));
    }

    private String getSuitImage(int pleix) {
        Set<Map.Entry<String, int[]>> sets = ImageUtil.ImageMap().entrySet();
        int[] blockRGB = ImageUtil.getBlockRGB(pleix);
        int[] imageRGB;
        String suitImage = "";
        double minVariance = Double.MAX_VALUE;
        int i = 0;
        for (Map.Entry<String, int[]> entry : sets) {
            imageRGB = entry.getValue();
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

}
