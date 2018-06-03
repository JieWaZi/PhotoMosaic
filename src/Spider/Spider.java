package Spider;

import ImageMosaic.ImageConfig;
import Manager.ExecutorsManager;
import TaskRunner.DownloadTaskRunner;
import TaskRunner.SpiderTaskRunner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Rider on 2018/5/31.
 */
public class Spider {

    private String url;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public Spider(String url) {
        this.url = url;
    }

    private Document getDocument() throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                .cookie("auth", "token")
                .timeout(5000)
                .get();
        return doc;
    }

    public void downloadImage() throws InterruptedException, IOException {
        System.out.println("进入新页面，开始下载图片.......");
        String nextPage = null;
        Element body = this.getDocument().body();
        Elements imgs = body.select("ul.portrait_list li a img");
        Elements a = body.select("div.page a");
        if (a.get(a.size() - 2).text().equals("下一页")) {
            nextPage = a.get(a.size() - 2).attr("href");
        } else if (a.get(a.size() - 1).text().equals("下一页")) {
            nextPage = a.get(a.size() - 1).attr("href");
        } else {
            System.out.println("网站图片全部爬去完毕");
            ExecutorsManager.getSpiderServiceInstance().shutdown();
            ExecutorsManager.getDownloadImageServiceInstance().shutdown();
            return;
        }
        SpiderTaskRunner spiderTaskRunner = new SpiderTaskRunner(new Spider(SpiderTest.URL + nextPage));
        ExecutorsManager.getSpiderServiceInstance().execute(spiderTaskRunner);
        List<String> imageUrls = imageUrl(imgs);
        for (String url : imageUrls) {
            ExecutorsManager.getDownloadImageServiceInstance().execute(new DownloadTaskRunner(url));
        }
    }

    private List<String> imageUrl(Elements elements) {
        List<String> list = new ArrayList<>();
        for (Element img : elements) {
            list.add(img.attr("src"));
        }
        return list;
    }

    private boolean isSpiderServiceShutDown() {
        if (ExecutorsManager.getSpiderServiceInstance().isTerminated()) {
            return true;
        }
        return false;
    }

    private boolean isDownloadServiceShutDown() {
        if (ExecutorsManager.getDownloadImageServiceInstance().isTerminated()) {
            return true;
        }
        return false;
    }
}
