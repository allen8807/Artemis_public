package cc.ubw.artemis.task;

import cc.ubw.artemis.biz.PrimeNumberList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/8 0008
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Component
public class AsyncTask {
    private static final Log LOG = LogFactory.getLog(AsyncTask.class);

    @Async
    public void testTask1() throws InterruptedException {
        System.out.println("task1任务开始");
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(10000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task1任务耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
    }

    @Async
    public void testTask2() throws InterruptedException {
        System.out.println("task2任务开始");
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(1000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task2任务耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
    }

    @Async
    public void callCalcPrimeNumbers() throws InterruptedException {
        Long start = System.currentTimeMillis();
        PrimeNumberList.calcPrimeNumbers(PrimeNumberList.MAX_NUMBER);
        Long end = System.currentTimeMillis();
        LOG.info("calcPrimeNumberListTask time is " + (end - start) + " ms");
        LOG.info("maxNum " + PrimeNumberList.getMaxNumber() + "primeNum nums " + PrimeNumberList.getPrimeNumbers().size());
    }

    @Async
    public void callCalcPrimeNumbers(Long l) throws InterruptedException {
        Long start = System.currentTimeMillis();
        PrimeNumberList.calcPrimeNumbers(l);
        Long end = System.currentTimeMillis();
        LOG.info("calcPrimeNumberListTask time is " + (end - start) + " ms");
        LOG.info("maxNum " + PrimeNumberList.getMaxNumber() + "primeNum nums " + PrimeNumberList.getPrimeNumbers().size());
    }
}
