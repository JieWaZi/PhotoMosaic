package Spider;

import Manager.ExecutorsManager;
import TaskRunner.SpiderTaskRunner;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Rider on 2018/6/1.
 */
public class SpiderTest {
    public static String URL = "http://www.liqucn.com/bz/";

    public static void main(String args[]) {
        AtomicInteger num = new AtomicInteger(0);
        SpiderTaskRunner spiderTaskRunner = new SpiderTaskRunner(new Spider(URL));
        ExecutorsManager.getSpiderServiceInstance().execute(spiderTaskRunner);
    }
}
