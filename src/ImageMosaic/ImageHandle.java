package ImageMosaic;

import Manager.ExecutorsManager;
import TaskRunner.CalculateTaskRunner;
import TaskRunner.ReadFileTaskRunner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Rider on 2018/5/31.
 */
public class ImageHandle {

    private String inputPath;
    private String outputPath;
    private static ConcurrentHashMap<String, int[]> map;

    public ImageHandle(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public void HandleImageConvert() throws IOException {
        initializeFiles(new File(ImageConfig.PICTURES));
        int block[][] = getImageBlock(new File(inputPath));
        BufferedImage outputImage = new BufferedImage(block.length * ImageConfig.BLOCK_WIDTH, block[0].length * ImageConfig.BLOCK_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics bg = outputImage.getGraphics();
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                int pixel = block[i][j];
                ExecutorsManager.calculateServiceInstance().execute(new CalculateTaskRunner(pixel, i, j, outputImage));
            }
        }
        ExecutorsManager.calculateServiceInstance().shutdown();
        while (!ExecutorsManager.calculateServiceInstance().isTerminated()) {
        }

        ImageIO.write(outputImage, "jpg", new File(outputPath + System.currentTimeMillis() + ".jpg"));
    }


    private int[][] getImageBlock(File file) {
        int width, height;
        int[][] block = {};
        BufferedImage newImage;
        newImage = narrowPicture(file);
        block = new int[newImage.getWidth()][newImage.getHeight()];
        for (int x = 0; x < block.length; x++) {
            for (int y = 0; y < block[0].length; y++) {
                block[x][y] = newImage.getRGB(x, y);
            }
        }

        return block;
    }

    private void initializeFiles(File dir) throws IOException {
        File[] files = dir.listFiles();
        int i = 0;
        for (File f : files) {
            if (f.isDirectory()) {
                initializeFiles(f);
            } else {
                ExecutorsManager.readFileServiceInstance().execute(new ReadFileTaskRunner(ImageMap(), f));
            }
        }
        ExecutorsManager.readFileServiceInstance().shutdown();
        while (!ExecutorsManager.readFileServiceInstance().isTerminated()) {
        }
        System.out.println("完成初始化 共有：" + ImageMap().size());
    }

    public static ConcurrentHashMap<String, int[]> ImageMap() {
        if (map == null) {
            map = new ConcurrentHashMap<>();
        }
        return map;
    }

    private BufferedImage narrowPicture(File file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        if (width > height) {
            width = ImageConfig.MAX_WIDTH;
            height = ImageConfig.MIN_HEIGHT;
        } else {
            width = ImageConfig.MIN_WIDTH;
            height = ImageConfig.MAX_HEIGHT;
        }
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().drawImage(bufferedImage, 0, 0, width, height, null);
        return newImage;
    }
}
