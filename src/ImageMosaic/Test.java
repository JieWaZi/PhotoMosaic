package ImageMosaic;

import java.io.File;
import java.io.IOException;

/**
 * Created by Rider on 2018/5/31.
 */
public class Test {
    public static void main(String[] args) {
        try {
            ImageUtil.initializeFiles(new File("src/SpiderPicture/"));
            ImageHandle imageHandle = new ImageHandle(new File("src/input/1.jpg"), new File("src/output/1.jpg"));
            imageHandle.HandleImageConvert();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
