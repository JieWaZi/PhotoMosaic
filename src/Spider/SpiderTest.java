package Spider;

import TaskRunner.SpiderTaskRunner;

/**
 * Created by Rider on 2018/6/1.
 */
public class SpiderTest {
    public static void main(String args[]) {

        SpiderTaskRunner spiderTaskRunner = new SpiderTaskRunner(new Spider("http://www.liqucn.com/bz/"));
        ExecutorsManager.getSpiderServiceInstance().execute(spiderTaskRunner);

    }
}
