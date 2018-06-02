package ImageMosaic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Rider on 2018/5/31.
 */
public class ImageHandle {

    private File inputImage;
    private File outputImage;

    public ImageHandle(File inputImage, File outputImage) {
        this.inputImage = inputImage;
        this.outputImage = outputImage;
    }

    public void HandleImageConvert() throws IOException {
        int block[][] = getBlockGray();
        BufferedImage outputImage = new BufferedImage(block.length * 30, block[0].length * 30, BufferedImage.TYPE_INT_RGB);
        Graphics bg = outputImage.getGraphics();
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                int pixel = block[i][j];
                BufferedImage image = ImageIO.read(new File("src/spiderPicture/" + getSuitImage(pixel)));
                bg.drawImage(image, i * 30, j * 30, 30, 30, null);
            }
        }
        ImageIO.write(outputImage, "jpg", new File("src/output/" + System.currentTimeMillis() + ".jpg"));
    }

    public int[][] getBlockGray() {
        int[][] block = ImageUtil.getImageBlock(inputImage);
        int[][] gray = new int[block.length][block[0].length];
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                int pixel = block[i][j];
                int r = (pixel & 0xff0000) >> 16;
                int g = (pixel & 0x00ff00) >> 8;
                int b = (pixel & 0x0000ff);
                //此处为将像素值转换为灰度值的方法，存在误差
                gray[i][j] = (int) (r * 0.3 + g * 0.59 + b * 0.11);
            }
        }
        return gray;
    }

    private String getSuitImage(int gray) {
        Map<String, Integer> map = ImageUtil.ImageMap();
        Map.Entry<String, Integer> gt = null, lt = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (gray > entry.getValue()) {
                gt = entry;
            } else {
                lt = entry;
                break;
            }
        }
        if (gt == null || lt == null) {
            return gt == null ? lt.getKey() : gt.getKey();
        }
        return 2 * gray - gt.getValue() - lt.getValue() > 0 ? lt.getKey() : gt.getKey();
    }


}
