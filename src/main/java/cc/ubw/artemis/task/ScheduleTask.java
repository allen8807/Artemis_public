package cc.ubw.artemis.task;

import cc.ubw.artemis.biz.AnalyseToeflArticle;
import cc.ubw.artemis.biz.Dictionary;
import cc.ubw.artemis.biz.PrimeNumberList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/8 0008
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 * Description:
 */

/**
 * 一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。
 * <p>
 * 按顺序依次为
 * 秒（0~59）
 * 分钟（0~59）
 * 小时（0~23）
 * 天（月）（0~31，但是你需要考虑你月的天数）
 * 月（0~11）
 * 天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
 * 7.年份（1970－2099）
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {
    private static final Log LOG = LogFactory.getLog(ScheduleTask.class);

    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        LOG.info("执行静态定时任务时间: " + System.currentTimeMillis());
    }

    //添加定时任务
    @Scheduled(cron = "0 0 0/6 * * ?")
    private void calcPrimeNumberListTask() {
        Long start = System.currentTimeMillis();
        //定时任务就是为了防止异常情况，所以这里强制重算
        PrimeNumberList.forceCalcPrimeNumbers(PrimeNumberList.MAX_NUMBER);
        Long end = System.currentTimeMillis();
        LOG.info("calcPrimeNumberListTask time is " + (end - start) + " ms");
        LOG.info("maxNum " + PrimeNumberList.getMaxNumber() + "primeNum nums " + PrimeNumberList.getPrimeNumbers().size());
    }


    //添加定时任务
    @Scheduled(cron = "0 0/10 * * * ?")
    private void analyseToeflArticleListTask() {
        Long start = System.currentTimeMillis();
        AnalyseToeflArticle.refreshArticles();
        Long end = System.currentTimeMillis();
        LOG.info("analyseToeflArticleListTask time is " + (end - start) + " ms");
        LOG.info("ToeflArtList.size=" + AnalyseToeflArticle.getToeflArtList().size() +
                " ToeflArtMap.size=" + AnalyseToeflArticle.getToeflArtMap().size());
    }

    @Scheduled(cron = "0 0 * * * ?")
    private void loadDictionaryTask() {
        Long start = System.currentTimeMillis();
        Dictionary.refreshDictionaries();
        Long end = System.currentTimeMillis();
        LOG.info("loadDictionaryTask time is " + (end - start) + " ms");
        LOG.info(" Dictionary.getDictionaryMap.size=" + Dictionary.getDictionaryMap().size());
    }

//    @Scheduled(cron = "0/5 * * * * ?")
//    private void testTask() {
//        Long start = System.currentTimeMillis();
//        PrimeNumberList.calcPrimeNumbers(1000000L);
//        Long end = System.currentTimeMillis();
//        LOG.info("calcPrimeNumberListTask time is " + (end - start) + " ms");
//        LOG.info("maxNum " + PrimeNumberList.getMaxNumber() +"primeNum nums " + PrimeNumberList.getPrimeNumbers().size() );
//    }

}
