package cc.ubw.artemis.biz;

import cc.ubw.artemis.vo.ResultMsg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/8 0008
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class Factorization {
    private static final Log LOG = LogFactory.getLog(Factorization.class);

    public static List<Long> factorizeNum(Long l) {
        Long temp = l;
        Long num = 0L;
        Long maxFactor = Double.valueOf(Math.sqrt(temp)).longValue() + 1;
        List<Long> factors = new ArrayList<>();
        boolean endFlag = false;
        while (!endFlag) {
            endFlag = true;
            for (long i = 2; i < maxFactor; i++) {
                num++;
                if (temp % i == 0) {
                    factors.add(i);
                    temp = temp / i;
                    endFlag = false;
                    maxFactor = Double.valueOf(Math.sqrt(temp)).longValue() + 1;
                    break;
                }
            }
        }
        factors.add(temp);
        LOG.info(l + "分解计算次数" + " Number is" + num);
        return factors;
    }

    public static List<Long> factorizeByPrimeNumberList(Long l, ResultMsg resultMsg) {
        List<Long> primeNums = PrimeNumberList.getPrimeNumbers();
        Long temp = l;
        Long num = 0L;
        Long maxFactor = Double.valueOf(Math.sqrt(temp)).longValue() + 1;
        List<Long> factors = new ArrayList<>();
        boolean endFlag = false;
        while (!endFlag) {
            endFlag = true;
            //先扫描质数表
            for (Long prime :
                    primeNums) {
                if (prime > maxFactor) {
                    break;
                }
                num++;
                if (temp % prime == 0) {
                    factors.add(prime);
                    temp = temp / prime;
                    endFlag = false;
                    maxFactor = Double.valueOf(Math.sqrt(temp)).longValue() + 1;
                    break;
                }
            }
        }
        //扫描超过质数表的部分,只能用累加法
        endFlag = false;
        Long maxPrimeNum = PrimeNumberList.getMaxNumber();
        while (!endFlag) {
            endFlag = true;
            for (long i = maxPrimeNum; i < maxFactor; i++) {
                num++;
                if (temp % i == 0) {
                    factors.add(i);
                    temp = temp / i;
                    endFlag = false;
                    maxFactor = Double.valueOf(Math.sqrt(temp)).longValue() + 1;
                    break;
                }
            }
        }
        factors.add(temp);
        LOG.info(l + " 分解计算次数" + " Number is" + num);
        resultMsg.setMsg("分解计算次数为" + num);
        return factors;
    }

}
