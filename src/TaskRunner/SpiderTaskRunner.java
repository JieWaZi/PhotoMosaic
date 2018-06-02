package TaskRunner;

import Spider.*;

import java.io.IOException;

/**
 * Created by Rider on 2018/6/1.
 */
public class SpiderTaskRunner implements Runnable {

    private Spider spider;

    public SpiderTaskRunner(Spider spider) {
        this.spider = spider;
    }

    @Override
    public void run() {
        System.out.println("爬虫线程启动，线程Id为：" + Thread.currentThread().getName());
        try {
            spider.downloadImage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            ExecutorsManager.getSpiderServiceInstance().shutdown();
        }
    }
}
