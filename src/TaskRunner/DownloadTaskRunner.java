package TaskRunner;

import ImageMosaic.ImageConfig;
import Spider.Spider;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Rider on 2018/6/1.
 */
public class DownloadTaskRunner implements Runnable {

    private String imgUrl;

    public DownloadTaskRunner(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public void run() {
        try {
            URL u = new URL(imgUrl);
            DataInputStream dataInputStream = new DataInputStream(u.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(ImageConfig.PICTURES + UUID.randomUUID()) + ".jpg");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            Spider.atomicInteger.incrementAndGet();
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
