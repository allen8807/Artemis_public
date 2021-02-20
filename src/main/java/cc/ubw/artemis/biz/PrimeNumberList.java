package cc.ubw.artemis.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/5 0005
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 * Description:由于内存限制，建议最大值设为10,000,000
 */
public class PrimeNumberList {
    private static final Log LOG = LogFactory.getLog(PrimeNumberList.class);
    private static Long mMaxNumber = 2L;//该质数列表为该数字以下的质数
    private static List<Long> mPrimeNumbers = new ArrayList<>();
    public static final Long MAX_NUMBER = 10000000L;

    static {
        mPrimeNumbers.add(2L);
    }

    /**
     * 计算质数表
     *
     * @param maxNumber
     */
    public static void calcPrimeNumbers(Long maxNumber) {
        //如果小于已经计算的值
        if (maxNumber <= mMaxNumber) {
            return;
        }
        //如果不小于则计算
        forceCalcPrimeNumbers(maxNumber);
    }

    //为防止并发，这里加同步锁

    /**
     * 强制重新计算，为了防止一些异常情况导致数据错误
     * 为防止并发异常，这里加同步锁
     *
     * @param maxNumber
     */
    public synchronized static void forceCalcPrimeNumbers(Long maxNumber) {
        LOG.info("begin forceCalcPrimeNumbers, maxNumber is " + maxNumber);
        List<Long> primeNumbers = new ArrayList<>();
        primeNumbers.add(2L);
        //从已经有的数字开始计算
        for (Long i = 2L; i <= maxNumber; i++) {
            boolean res = checkPrimeNumberWhenCalc(i, primeNumbers);
            if (res) {
                primeNumbers.add(i);
            }
        }
        //更新静态变量
        mPrimeNumbers = primeNumbers;
        mMaxNumber = maxNumber;
        LOG.info("end forceCalcPrimeNumbers maxNumber is " + maxNumber
                + " mPrimeNumbers.size is " + mPrimeNumbers.size());
    }

    /**
     * @param first
     * @param last
     * @return 返回质数-位置的key-value，如果value是0则表示不在序列中
     */
    public static Map<Long, Integer> calcRangePrimeNum(Long first, Long last) {
        Map<Long, Integer> eligiblePrimes = new HashMap<>();
        if (first > last) {
            return eligiblePrimes;
        }
        Long begin = first;
        Long end = last;
        Long maxNumber = mMaxNumber;
        //如果都超范围了
        if (first > maxNumber) {
            for (long i = begin; i <= end; i++) {
                boolean isPrime = isPrimeNumber(i);
                if (isPrime) {
                    eligiblePrimes.put(i, 0);
                }
            }
        } else {
            for (int i = 0; i < mPrimeNumbers.size(); i++) {
                if (mPrimeNumbers.get(i) >= first && mPrimeNumbers.get(i) <= last) {
                    //注意计数规则
                    eligiblePrimes.put(mPrimeNumbers.get(i), i + 1);
                }
                if (mPrimeNumbers.get(i) > last) {
                    break;
                }
            }
            //超过的部分
            if (last > maxNumber) {
                begin = maxNumber;
                for (long i = begin; i <= end; i++) {
                    boolean isPrime = isPrimeNumber(i);
                    if (isPrime) {
                        eligiblePrimes.put(i, 0);
                    }
                }
            }
        }
        return eligiblePrimes;
    }

    private static boolean checkPrimeNumberWhenCalc(Long num, List<Long> primeNumbers) {
        boolean res = true;
        Long maxFactor = Double.valueOf(Math.sqrt(num)).longValue() + 1;
        for (Long p :
                primeNumbers) {
            if (p > maxFactor) {
                break;
            }
            if (num % p == 0) {
                res = false;
                break;
            }
        }
        return res;
    }

    public static boolean isPrimeNumber(Long l) {
        boolean primeFlag = true;
        Long maxPrimeNum = mMaxNumber;
        Long maxFactor = Double.valueOf(Math.sqrt(l)).longValue() + 1;
        //先检查已经有的部分
        for (Long prime :
                mPrimeNumbers) {
            if (prime > maxFactor) {
                break;
            }
            if (l % prime == 0) {
                primeFlag = false;
                break;
            }
        }
        //扫描超过质数表的部分,只能用累加法
        if (primeFlag) {
            for (long i = maxPrimeNum; i < maxFactor; i++) {
                if (l % i == 0) {
                    primeFlag = false;
                    break;
                }
            }
        }
        return primeFlag;
    }


    public static Long getMaxNumber() {
        return mMaxNumber;
    }

    public static List<Long> getPrimeNumbers() {
        return mPrimeNumbers;
    }

    public static List<Long> getClonePrimeNumbers() {
        return new ArrayList<>(mPrimeNumbers);
    }
}
