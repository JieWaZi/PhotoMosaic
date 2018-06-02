package ImageMosaic;

import Spider.ExecutorsManager;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by Rider on 2018/5/30.
 */
public class ImageUtil {

    private static Map<String, Integer> map;

    public static void initializeFiles(File dir) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println(start);
        File[] files = dir.listFiles();
        int i = 0;
        for (File f : files) {
            if (f.isDirectory()) {
                initializeFiles(f);
            } else {
                ImageMap().put(f.getName(), ImageUtil.getGray(f));
            }
        }
        setImageMap(sortMapByValue(map));
        System.out.println(System.currentTimeMillis() - start);
        System.out.println("完成初始化 共有：" + ImageUtil.ImageMap().size());
    }

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

    public static Map<String, Integer> getFiles(File dir, Map<String, Integer> map) {
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                getFiles(f, map);
            } else {
                map.put(f.getName(), getGray(f));
            }
        }
        map = sortMapByValue(map);
        return map;
    }

    //获取图片的平均灰度值
    public static int getGray(File file) {
        int width, height;
        int a = 0, r = 0, g = 0, b = 0, gray = 0;
        try {
            if (file == null) {
                return -1;
            }
            BufferedImage bufferedImage = ImageIO.read(file);
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = bufferedImage.getRGB(x, y);
                    r = (pixel & 0xff0000) >> 16;
                    g = (pixel & 0x00ff00) >> 8;
                    b = (pixel & 0x0000ff);
                    //此处为将像素值转换为灰度值的方法，存在误差
                    gray = (int) (gray + r * 0.3 + g * 0.59 + b * 0.11);
                }
            }
            gray = gray / (width * height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gray;
    }

    public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
                oriMap.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
        Map.Entry<String, Integer> tmpEntry;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    public static Map<String, Integer> ImageMap() {
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    public static void setImageMap(Map<String, Integer> map) {
        ImageUtil.map = map;
    }
}
