package ImageMosaic;

import java.io.IOException;

/**
 * Created by Rider on 2018/5/31.
 */
public class Test {
    public static void main(String[] args) {
        try {
            ImageHandle imageHandle = new ImageHandle(ImageConfig.INPUT_IMAGE, ImageConfig.OUTPUT_PATH);
            imageHandle.HandleImageConvert();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
