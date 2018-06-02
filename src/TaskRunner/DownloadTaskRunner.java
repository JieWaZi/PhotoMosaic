package TaskRunner;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

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
            FileOutputStream fileOutputStream = new FileOutputStream(new File("src/spiderPicture/" + UUID.randomUUID()) + ".jpg");
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
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
