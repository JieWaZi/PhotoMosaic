package Spider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Rider on 2018/6/2.
 */
public class ExecutorsManager {
    private static ExecutorService downloadImageService;
    private static ExecutorService spiderService;

    public static ExecutorService getDownloadImageServiceInstance() {
        if (downloadImageService == null) {
            downloadImageService = Executors.newFixedThreadPool(10);
        }
        return downloadImageService;
    }

    public static ExecutorService getSpiderServiceInstance() {
        if (spiderService == null) {
            spiderService = Executors.newFixedThreadPool(20);
        }
        return spiderService;
    }

}
