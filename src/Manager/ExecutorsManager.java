package Manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Rider on 2018/6/2.
 */
public class ExecutorsManager {
    private static ExecutorService downloadImageService;
    private static ExecutorService spiderService;
    private static ExecutorService calculateService;
    private static ExecutorService readFileService;
    private static int DOWNLOAD_POOLS = 10;
    private static int SPIDER_POOLS = 20;
    private static int CALCULATE_POOLS = 20;
    private static int READ_FILES_POOLS = 20;

    public static ExecutorService getDownloadImageServiceInstance() {
        if (downloadImageService == null) {
            downloadImageService = Executors.newFixedThreadPool(DOWNLOAD_POOLS);
        }
        return downloadImageService;
    }

    public static ExecutorService getSpiderServiceInstance() {
        if (spiderService == null) {
            spiderService = Executors.newFixedThreadPool(SPIDER_POOLS);
        }
        return spiderService;
    }

    public static ExecutorService calculateServiceInstance() {
        if (calculateService == null) {
            calculateService = Executors.newFixedThreadPool(CALCULATE_POOLS);
        }
        return calculateService;
    }

    public static ExecutorService readFileServiceInstance() {
        if (readFileService == null) {
            readFileService = Executors.newFixedThreadPool(READ_FILES_POOLS);
        }
        return readFileService;
    }

}
