/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.ubw.artemis.controller;

import cc.ubw.artemis.biz.*;
import cc.ubw.artemis.commom.JSONUtils;
import cc.ubw.artemis.task.AsyncTask;
import cc.ubw.artemis.vo.ResultMsg;
import cc.ubw.artemis.vo.ToolsInputVO;
import cc.ubw.artemis.vo.ToolsResultVO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Author Allen
 * Date 2019年11月5日 下午8:10:51
 * Version V1.0
 * Desc
 */
@Controller
public class ToolsController {
    private static final Log LOG = LogFactory.getLog(ToolsController.class);

    @Autowired
    private AsyncTask asyncTask;
//     @GetMapping("/check_id_card")
//    public String checkIDCardInput(Model model) {
//        model.addAttribute("idc", new IDCardVO());
//        return  "check_id_card";
//    }
//
//    @PostMapping("/check_id_card")
//    public String checkIDCard(IDCardVO idc,Model model) {
//        Boolean res = IDCard.checkIDCard(
//                idc.getDistrictSix(),
//                idc.getBirthdayEight(),
//                idc.getLastFour());
//        String resMsg;
//        String idNum =  idc.getDistrictSix() + idc.getBirthdayEight() + idc.getLastFour();
//        LOG.info("input id card is "+JSON.toJSONString(idc) );
//        if(res){
//            resMsg = idNum + " is right.";
//        }else{
//            String rightId = IDCard.giveRightIDCard(idc.getDistrictSix(),
//                    idc.getBirthdayEight(),
//                    idc.getLastFour());
//            if(StringUtils.isEmptyOrWhitespace(rightId)){
//                resMsg = idNum + " is wrong. And format is wrong.";
//            }else {
//                resMsg = idNum + " is wrong. The right one is " + rightId;
//            }
//        }
//        model.addAttribute("res", resMsg);
//        LOG.info(JSON.toJSONString(resMsg));
//       return "check_id_result";
//    }

    @GetMapping("/tools")
    public String getTools() {
        return "tools";
    }

    @GetMapping("/get_netease_lrc")
    public String getNeteaseLrc(Model model) {
        ToolsInputVO inputVO = new ToolsInputVO();
        inputVO.setFunctionName("获取网易云音乐歌词");
        inputVO.setFunctionValue("/post_netease_lrc");
        inputVO.setFirstParamName("歌曲Id,例如:1439212059");
        inputVO.setReferUrl("https://blog.csdn.net/lyandyhk/article/details/78485989");
        inputVO.setReferSlogan("参考/reference:网易云音乐歌曲带时间轴歌词的提取");
        model.addAttribute("input", inputVO);
        return "tools_input";
    }

    @PostMapping("/post_netease_lrc")
    public String postNeteaseLrc(ToolsInputVO inputVO, Model model) {
        ToolsResultVO resultVO = new ToolsResultVO();
        LOG.info("[post_netease_lrc] inputVO is " + JSONUtils.toJSONString(inputVO));
        LOG.info("[post_netease_lrc] input song id is " + inputVO.getFirstParamValue());
        NeteaseLrc neteaseLrc = new NeteaseLrc(inputVO.getFirstParamValue());
        try {
            neteaseLrc.generateFinalLrc();
        } catch (Exception e) {
            LOG.error("[post_netease_lrc] error in Netease generateFinalLrc", e);
        }
        LOG.info("[post_netease_lrc] result song lrc is " + neteaseLrc.getFinalLrc());
        resultVO.setResult(neteaseLrc.getFinalLrc());
        resultVO.setRetryUrl("get_netease_lrc");
        resultVO.setRetrySlogan("再试一次");
        model.addAttribute("result", resultVO);
        return "tools_result";
    }

    @GetMapping("/get_check_id_card")
    public String getCheckIDCardInput(Model model) {
        ToolsInputVO inputVO = new ToolsInputVO();
        inputVO.setFunctionName("检查身份证号的合规性/Check Id Card Num");
        inputVO.setFunctionValue("/post_check_id_card");
        inputVO.setFirstParamName("身份证前6位地区码");
        inputVO.setSecondParamName("身份证中间8位生日码");
        inputVO.setThirdParamName("身份证后4位特征码");

        inputVO.setReferUrl("https://www.jianshu.com/p/ead5b08e9839");
        inputVO.setReferSlogan("参考/reference:身份证号码的编码规则及校验");
        model.addAttribute("input", inputVO);
        return "tools_input";
    }

    @PostMapping("/post_check_id_card")
    public String postCheckIDCard(ToolsInputVO inputVO, Model model) {
        ToolsResultVO resultVO = new ToolsResultVO();
        LOG.info("[post_check_id_card] inputVO is " + JSONUtils.toJSONString(inputVO));
        String idNum = inputVO.getFirstParamValue()
                + inputVO.getSecondParamValue()
                + inputVO.getThirdParamValue();
        LOG.info("[post_check_id_card] input id card is " + idNum);

        String resMsg;
        try {
            Boolean res = IDCard.checkIDCard(
                    inputVO.getFirstParamValue(),
                    inputVO.getSecondParamValue(),
                    inputVO.getThirdParamValue());
            if (res) {
                resMsg = idNum + " is right.";
            } else {
                String rightId = IDCard.giveRightIDCard(
                        inputVO.getFirstParamValue(),
                        inputVO.getSecondParamValue(),
                        inputVO.getThirdParamValue());
                if (StringUtils.isBlank(rightId)) {
                    resMsg = idNum + " is wrong. And format is wrong.";
                } else {
                    resMsg = idNum + " is wrong. The right one is " + rightId;
                }
            }
        } catch (Exception e) {
            LOG.error("[post_check_id_card] error in postCheckIDCard", e);
            resMsg = "error in postCheckIDCard";
        }

        resultVO.setResult(resMsg);
        LOG.info(JSONUtils.toJSONString(resMsg));
        resultVO.setRetryUrl("get_check_id_card");
        resultVO.setRetrySlogan("再试一次");
        model.addAttribute("result", resultVO);
        return "tools_result";
    }

    @GetMapping("/get_factorization")
    public String getFactorization(Model model) {
        ToolsInputVO inputVO = new ToolsInputVO();
        inputVO.setFunctionName("因数分解/Factorization");
        inputVO.setFunctionValue("/post_factorization");
        inputVO.setFunctionDesc("分解结果为质因数乘积，分解的最大值不得超过最大long值" + Long.MAX_VALUE);
        inputVO.setFirstParamName("输入一个正整数");

//        inputVO.setReferUrl("https://www.jianshu.com/p/ead5b08e9839");
//        inputVO.setReferSlogan("参考/reference:身份证号码的编码规则及校验");
        model.addAttribute("input", inputVO);
        return "tools_input";
    }

    @PostMapping("/post_factorization")
    public String postFactorization(ToolsInputVO inputVO, Model model) {
        ToolsResultVO resultVO = new ToolsResultVO();
        String resMsg = "";
        LOG.info("[post_factorization] inputVO is " + JSONUtils.toJSONString(inputVO));
        LOG.info("[post_factorization] input number is " + inputVO.getFirstParamValue());

        try {
            Long l = Long.parseLong(inputVO.getFirstParamValue());
            ResultMsg resultMsg = new ResultMsg();
            List<Long> factors = Factorization.factorizeByPrimeNumberList(l, resultMsg);
            StringBuilder sb = new StringBuilder();
            sb.append(l).append("=");
            if (!CollectionUtils.isEmpty(factors)) {
                boolean firstFlag = true;
                for (Long p : factors) {
                    if (!firstFlag) {
                        sb.append("*");
                    } else {
                        firstFlag = false;
                    }
                    sb.append(p);
                }
            } else {
                sb.append("分解失败");
            }
            sb.append("\r\n").append(resultMsg.getMsg());
            resMsg = sb.toString();
        } catch (NumberFormatException ne) {
            LOG.error("[post_factorization] parse error postFactorization", ne);
            resMsg = "数字格式错误";
        } catch (Exception e) {
            LOG.error("[post_factorization] error in postFactorization", e);
            resMsg = "发生异常";
        }
        LOG.info("[post_factorization] result is " + resMsg);
        resultVO.setResult(resMsg);
        resultVO.setRetryUrl("get_factorization");
        resultVO.setRetrySlogan("再试一次");
        model.addAttribute("result", resultVO);
        return "tools_result";
    }

    @GetMapping("/get_prime_numbers")
    public String getPrimeNumbers(Model model) {
        ToolsInputVO inputVO = new ToolsInputVO();
        inputVO.setFunctionName("获取范围内的质数以及序号/Prime Numbers");
        inputVO.setFunctionValue("/post_prime_numbers");
        inputVO.setFunctionDesc("质数序号仅支持10,000,000以内,序号为0表示不知道位置,范围请不要超过10,000");
        inputVO.setFirstParamName("输入范围开始值(包含)");
        inputVO.setSecondParamName("输入范围结束值(包含)");

//        inputVO.setReferUrl("https://www.jianshu.com/p/ead5b08e9839");
//        inputVO.setReferSlogan("参考/reference:身份证号码的编码规则及校验");
        model.addAttribute("input", inputVO);
        return "tools_input";
    }

    @PostMapping("/post_prime_numbers")
    public String postPrimeNumbers(ToolsInputVO inputVO, Model model) {

        ToolsResultVO resultVO = new ToolsResultVO();
        String resMsg = "";
        LOG.info("[post_prime_numbers] inputVO is " + JSONUtils.toJSONString(inputVO));
        LOG.info("[post_prime_numbers] input range is [" + inputVO.getFirstParamValue()
                + "," + inputVO.getSecondParamValue() + "]");
        StringBuilder sb = new StringBuilder();
        List<Long> eligiblePrimes = new ArrayList<>();
        Long first = 0L;
        Long last = 0L;
        try {
            first = Long.parseLong(inputVO.getFirstParamValue());
            last = Long.parseLong(inputVO.getSecondParamValue());
            Long maxNumber = PrimeNumberList.getMaxNumber();
            if (first > last || last - first > 10000) {
                sb.append("范围有误,范围请不要超过10,000");
            } else {
                Map<Long, Integer> primeMap = PrimeNumberList.calcRangePrimeNum(first, last);
                eligiblePrimes.addAll(primeMap.keySet());
                Collections.sort(eligiblePrimes);
                for (Long p :
                        eligiblePrimes) {
                    sb.append("第\t").append(primeMap.get(p)).append("\t个质数为\t").append(p).append("\r\n");
                }
                sb.append("[").append(first).append(",").append(last).append("]").append("范围内共有")
                        .append(eligiblePrimes.size()).append("个质数");
            }
            resMsg = sb.toString();
            //这里手动触发异步调用
            if (PrimeNumberList.getMaxNumber() < PrimeNumberList.MAX_NUMBER) {
                long start = System.currentTimeMillis();
                asyncTask.callCalcPrimeNumbers();
                long end = System.currentTimeMillis();
                LOG.info("[post_prime_numbers]异步调用asyncTask.callCalcPrimeNumbers cost " + (end - start) + "ms");
            }
        } catch (NumberFormatException ne) {
            LOG.error("[post_prime_numbers] parse error postPrimeNumbers", ne);
            resMsg = "数字格式错误";
        } catch (Exception e) {
            LOG.error("[post_prime_numbers] error in postPrimeNumbers", e);
            resMsg = "发生异常";
        }
        if (eligiblePrimes.size() > 0) {
            StringBuilder sbb = new StringBuilder();
            sbb.append("[").append(first).append(",").append(last).append("]").append("范围内共有")
                    .append(eligiblePrimes.size()).append("个质数");
            LOG.info("[post_prime_numbers] result is " + sbb.toString());
        } else {
            LOG.info("[post_prime_numbers] result is " + resMsg);
        }
        resultVO.setResult(resMsg);
        resultVO.setRetryUrl("get_prime_numbers");
        resultVO.setRetrySlogan("再试一次");
        model.addAttribute("result", resultVO);
        return "tools_result";
    }

    @GetMapping("/get_random_equations")
    public String getRandomEquations(Model model) {
        ToolsInputVO inputVO = new ToolsInputVO();
        inputVO.setFunctionName("随机生成加减法算式和答案");
        inputVO.setFunctionValue("/post_random_equations");
        inputVO.setFunctionDesc("请输入加数的范围、和的范围以及算式个数（减法为加法的逆运算）");
        inputVO.setFirstParamName("输入范围开始值(包含)");
        inputVO.setSecondParamName("输入范围结束值(包含)");
        inputVO.setThirdParamName("输入生成的算式个数");
        inputVO.setForthParamName("输入和的开始值(包含)");
        inputVO.setFifthParamName("输入和的结束值(包含)");
        model.addAttribute("input", inputVO);
        return "tools_input";
    }

    @PostMapping("/post_random_equations")
    public String postRandomEquations(ToolsInputVO inputVO, Model model) {

        ToolsResultVO resultVO = new ToolsResultVO();
        String resMsg = "";
        LOG.info("[post_random_equations] inputVO is " + JSONUtils.toJSONString(inputVO));
        LOG.info("[post_random_equations] input range is [" + inputVO.getFirstParamValue()
                + "," + inputVO.getSecondParamValue() + "], equation number is " + inputVO.getThirdParamName());
        StringBuilder sb = new StringBuilder();

        int begin = 0;
        int end = 0;
        int sumBegin = 0;
        int sumEnd = 0;
        int equationNum = 0;
        Random r = new Random();
        Random op = new Random();
        //用于判断随机生成的加法或是减法算式
        int opFlag = 0;
        //问题字串
        StringBuilder sbQ = new StringBuilder();
        //答案字串
        StringBuilder sbA = new StringBuilder();
        try {
            begin = Integer.parseInt(inputVO.getFirstParamValue());
            end = Integer.parseInt(inputVO.getSecondParamValue());
            sumBegin = Integer.parseInt(inputVO.getForthParamValue());
            sumEnd = Integer.parseInt(inputVO.getFifthParamValue());
            equationNum = Integer.parseInt(inputVO.getThirdParamValue());
            if (begin > end || equationNum < 0 || sumBegin > sumEnd) {
                sb.append("范围有误，或者数量有误");
            } else if (begin + begin > sumEnd || end + end < sumBegin) {
                sb.append("两加数的范围不可能得到和的范围");
            } else {
                int len = inputVO.getThirdParamValue().length();
                StringBuilder patten = new StringBuilder();
                for (int i = 0; i < len; i++) {
                    patten.append("0");
                }
                DecimalFormat df = new DecimalFormat(patten.toString());
                for (int i = 1; i < equationNum + 1; i++) {
                    Integer plus1 = r.nextInt(end - begin + 1) + begin;
                    Integer plus2 = r.nextInt(end - begin + 1) + begin;
                    Integer res = plus1 + plus2;
                    if (res < sumBegin || res > sumEnd) {
                        i--;
                        continue;
                    }
                    opFlag = op.nextInt(2) % 2;
                    String iStr = df.format(i);
                    if (opFlag == 0) {
                        sbQ.append("第").append(iStr).append("题:    ").append(plus1).append(" + ").append(plus2).append(" = \n");
                        sbA.append("第").append(iStr).append("题:    ").append(plus1).append(" + ").append(plus2).append(" = ").append(res).append("\n");
                    } else if (opFlag == 1) {
                        sbQ.append("第").append(iStr).append("题:    ").append(res).append(" - ").append(plus1).append(" = \n");
                        sbA.append("第").append(iStr).append("题:    ").append(res).append(" - ").append(plus1).append(" = ").append(plus2).append("\n");
                    }
                }
            }
            resMsg = sb.append(sbQ.toString()).append("答案\n").append(sbA.toString()).toString();
        } catch (NumberFormatException ne) {
            LOG.error("[post_random_equations] parse error postRandomEquations", ne);
            resMsg = "数字格式错误";
        } catch (Exception e) {
            LOG.error("[post_random_equations] error in postRandomEquations", e);
            resMsg = "发生异常";
        }
        LOG.info("[post_random_equations] result is " + resMsg);
        resultVO.setResult(resMsg);
        resultVO.setRetryUrl("get_random_equations");
        resultVO.setRetrySlogan("再试一次");
        model.addAttribute("result", resultVO);
        return "tools_result";
    }


    @GetMapping("/get_random_divide_equations")
    public String getRandomDivideEquations(Model model) {
        ToolsInputVO inputVO = new ToolsInputVO();
        inputVO.setFunctionName("随机生成带余数除法算式和答案");
        inputVO.setFunctionValue("/post_random_divide_equations");
        inputVO.setFunctionDesc("请输入被除数、除数的范围以及算式个数");
        inputVO.setFirstParamName("输入被除数范围开始值(包含)");
        inputVO.setSecondParamName("输入被除数范围结束值(包含)");
        inputVO.setThirdParamName("输入生成的算式个数");
        inputVO.setForthParamName("输入除数的开始值(包含)");
        inputVO.setFifthParamName("输入除数的结束值(包含)");
        model.addAttribute("input", inputVO);
        return "tools_input";
    }

    @PostMapping("/post_random_divide_equations")
    public String postRandomDivideEquations(ToolsInputVO inputVO, Model model) {

        ToolsResultVO resultVO = new ToolsResultVO();
        String resMsg = "";
        LOG.info("[post_random_divide_equations] inputVO is " + JSONUtils.toJSONString(inputVO));
        LOG.info("[post_random_divide_equations] input range is [" + inputVO.getFirstParamValue()
                + "," + inputVO.getSecondParamValue() + "], equation number is " + inputVO.getThirdParamName());
        StringBuilder sb = new StringBuilder();

        int dividendBegin = 0;
        int dividendEnd = 0;
        int divisorBegin = 0;
        int divisorEnd = 0;
        int equationNum = 0;
        Random r = new Random();
        Random op = new Random();

        //问题字串
        StringBuilder sbQ = new StringBuilder();
        //答案字串
        StringBuilder sbA = new StringBuilder();
        try {
            dividendBegin = Integer.parseInt(inputVO.getFirstParamValue());
            dividendEnd = Integer.parseInt(inputVO.getSecondParamValue());
            divisorBegin = Integer.parseInt(inputVO.getForthParamValue());
            divisorEnd = Integer.parseInt(inputVO.getFifthParamValue());
            equationNum = Integer.parseInt(inputVO.getThirdParamValue());
            if (dividendBegin > dividendEnd || equationNum < 0 || divisorBegin > divisorEnd || (divisorBegin == divisorEnd &&  divisorBegin ==0) ) {
                sb.append("范围有误，或者数量有误");
            }  else {
                int len = inputVO.getThirdParamValue().length();
                StringBuilder patten = new StringBuilder();
                for (int i = 0; i < len; i++) {
                    patten.append("0");
                }
                DecimalFormat df = new DecimalFormat(patten.toString());
                for (int i = 1; i < equationNum + 1; i++) {
                    Integer dividend = r.nextInt(dividendEnd - dividendBegin + 1) + dividendBegin;
                    Integer divisor = r.nextInt(divisorEnd - divisorBegin + 1) + divisorBegin;
                    if(divisor == 0){
                        i--;
                        continue;
                    }
                    Integer quotient = dividend/divisor;
                    Integer remainder = dividend%divisor;
                    if(remainder == 0){
                        i--;
                        continue;
                    }
                    if (dividend < divisor ) {
                        i--;
                        continue;
                    }

                    String iStr = df.format(i);

                    sbQ.append("第").append(iStr).append("题:    ").append(dividend).append(" ÷ ").append(divisor).append(" = \n");
                    sbA.append("第").append(iStr).append("题:    ").append(dividend).append(" ÷ ").append(divisor).append(" = ")
                            .append(quotient).append("余").append(remainder).append("\n");



                }
            }
            resMsg = sb.append(sbQ.toString()).append("答案\n").append(sbA.toString()).toString();
        } catch (NumberFormatException ne) {
            LOG.error("[post_random_equations] parse error postRandomEquations", ne);
            resMsg = "数字格式错误";
        } catch (Exception e) {
            LOG.error("[post_random_equations] error in postRandomEquations", e);
            resMsg = "发生异常";
        }
        LOG.info("[post_random_equations] result is " + resMsg);
        resultVO.setResult(resMsg);
        resultVO.setRetryUrl("get_random_divide_equations");
        resultVO.setRetrySlogan("再试一次");
        model.addAttribute("result", resultVO);
        return "tools_result";
    }

    @GetMapping("/get_words_statistics")
    public String getWordsStatistics(Model model) {
        ToolsInputVO inputVO = new ToolsInputVO();
        inputVO.setFunctionName("文本统计/WordsStatistics");
        inputVO.setFunctionValue("/post_words_statistics");
        inputVO.setFunctionDesc("统计文本中各个字的出现次数");
        inputVO.setFirstParamName("输入文本");

        model.addAttribute("input", inputVO);
        return "tools_input";
    }

    @PostMapping("/post_words_statistics")
    public String postWordsStatistics(ToolsInputVO inputVO, Model model) {
        ToolsResultVO resultVO = new ToolsResultVO();
        String resMsg = "";
        LOG.info("[post_words_statistics] inputVO is " + JSONUtils.toJSONString(inputVO));
        LOG.info("[post_words_statistics] input number is " + inputVO.getFirstParamValue());
        try {
            String s = inputVO.getFirstParamValue();
            Map<String, Long> wordMap = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                String temp = s.substring(i, i + 1);
                boolean newFlag = true;
                for (Map.Entry<String, Long> entry : wordMap.entrySet()) {
                    if (entry.getKey().equals(temp)) {
                        Long num = entry.getValue();
                        entry.setValue(num + 1);
                        newFlag = false;
                    }
                }
                if (newFlag) {
                    wordMap.put(temp, 1L);
                }
            }
            //排序
            List<WordNum> list = new ArrayList<>();
            for (Map.Entry<String, Long> entry : wordMap.entrySet()) {
                WordNum wn = new WordNum(entry.getKey(), entry.getValue());
                list.add(wn);
            }
            Collections.sort(list);
            Collections.reverse(list);
            StringBuilder sb = new StringBuilder();
            for (WordNum w :
                    list) {
                sb.append(w.getWord()).append(":").append(w.getSum()).append("\r\n");
            }
            resMsg = sb.toString();
        } catch (NumberFormatException ne) {
            LOG.error("[post_words_statistics] parse error postFactorization", ne);
            resMsg = "数字格式错误";
        } catch (Exception e) {
            LOG.error("[post_words_statistics] error in postFactorization", e);
            resMsg = "发生异常";
        }
        LOG.info("[post_words_statistics] result is " + resMsg);
        resultVO.setResult(resMsg);
        resultVO.setRetryUrl("get_words_statistics");
        resultVO.setRetrySlogan("再试一次");
        model.addAttribute("result", resultVO);
        return "tools_result";
    }


    @GetMapping("/get_toefl_article_json")
    public String getToeflArticleJson(Model model) {
        ToolsInputVO inputVO = new ToolsInputVO();
        inputVO.setFunctionName("生成托福阅读听力的JSON文件/ToeflArticleJson");
        inputVO.setFunctionValue("/post_toefl_article_json");
        String desc = "生成托福阅读听力的JSON文件题号类型格式如下:\n" +
                "\t都用_分隔，开头为O_然后是TPO套数，题型缩写，题号，听力或阅读缩写。\n" +
                "题型缩写如下:\n" +
                "\t阅读Passage:PR;\n" +
                "\t听力Conversation:LC;听力Lecture:LL;\n" +
                "\t独立口语:SI;口语Conversation:SC;口语Lecture:SL;\n" +
                "\t材料口语Conversation:SCM;材料口语Lecture:SLM;\n" +
                "\t综合写作Integrated:WT;独立写作Independent:WD;\n" +
                "\t阅读:R;听力:L\n";
        inputVO.setFunctionDesc(desc);
        inputVO.setFirstParamName("输入题号类型，格式为:\n" +
                "\tO_TPO套数_题型缩写_题号_听力或阅读缩写 例如：O_11_RP_1_R");
        inputVO.setSecondParamName("输入标题，可不填");
        inputVO.setThirdParamName("输入正文");
        model.addAttribute("input", inputVO);
        return "tools_input";
    }

    @PostMapping("/post_toefl_article_json")
    public String postToeflArticleJson(ToolsInputVO inputVO, Model model) {

        ToolsResultVO resultVO = new ToolsResultVO();
        String resMsg = "";
        String resJson = "";
        LOG.info("[post_toefl_article_json] inputVO is " + JSONUtils.toJSONString(inputVO));
        LOG.info("[post_toefl_article_json] input id is " + inputVO.getFirstParamValue()
                + "\n  input title is " + inputVO.getSecondParamValue() + "\n input txtBody is "
                + inputVO.getThirdParamValue());
        ToeflArticle ta = new ToeflArticle(inputVO.getFirstParamValue(),
                inputVO.getSecondParamValue(),
                inputVO.getThirdParamValue());
        LOG.info("[post_toefl_article_json] ta " + ta.allToString());

        resJson = JSONUtils.toPrettyJSONString(ta);
        resMsg = resJson;
        String analysisStr = "";
        if (ta.getTaar() != null) {
            analysisStr = "\r\n\r\n分析结果\r\n\r\n" + ta.getTaar().toFormatString();
        }
        resMsg = resMsg + analysisStr;
        LOG.info("[post_toefl_article_json] result is " + resMsg);
        resultVO.setResult(resMsg);
        resultVO.setRetryUrl("get_toefl_article_json");
        //  resultVO.setRetryUrl("json_export");
        resultVO.setRetrySlogan("再试一次");
        resultVO.setDownloadName(ta.getId() + ".json");
        resultVO.setDownloadJson(resJson);
        resultVO.setDownloadUrl("/json_export");
        resultVO.setDownloadSlogan("下载JSON文件");
        System.out.println("result" + resMsg);
        model.addAttribute("result", resultVO);
        return "tools_result";
    }


    @GetMapping("/get_toefl_article_analysis_result")
    public String getToeflArticleAnalysisResult(Model model) {
        ToolsInputVO inputVO = new ToolsInputVO();
        inputVO.setFunctionName("获取托福文章分析结果/ToeflArticleAnalysisResult");
        inputVO.setFunctionValue("/post_toefl_article_analysis_result");
        String desc = "生成托福阅读听力的JSON文件的分析结果";
        inputVO.setFunctionDesc(desc);
        inputVO.setFirstParamName("输入命令类型:\n" +
                "\t0 强制刷新文章列表并显示 \n" +
                "\t1 显示文章列表 \n" +
                "\t2 输入要分析的文章编号 \n" +
                "\t3 查看单独一篇文章及其内容 \n" +
                "\t4 分析所有文章  \n" +
                "\t5 分析所有阅读文章  \n" +
                "\t6 分析所有听力文章  \n"

        );
        inputVO.setSecondParamName("如果命令类型是2，这里输入要分析的文章id列表，用,或换行符等分隔，例如:O_15_RP_1_R,O_6_RP_2_R\n" +
                "如果命令类型是3，这里只输入一个文章id"
        );
        model.addAttribute("input", inputVO);
        return "tools_input";
    }


    @PostMapping("/post_toefl_article_analysis_result")
    public String postToeflArticleAnalysisResult(ToolsInputVO inputVO, Model model) {

        ToolsResultVO resultVO = new ToolsResultVO();
        String resMsg = "";
        LOG.info("[post_toefl_article_analysis_result] inputVO is " + JSONUtils.toJSONString(inputVO));
        LOG.info("[post_toefl_article_analysis_result] input command is " + inputVO.getFirstParamValue()
                + "\n  input list is " + inputVO.getSecondParamValue());

        Integer command = NumberUtils.toInt(inputVO.getFirstParamValue());
        if (command == 0 || command == 1) {
            if (command == 0) {
                AnalyseToeflArticle.refreshArticles();
            }
            String tableList = AnalyseToeflArticle.showAllListByTable();
            resMsg = AnalyseToeflArticle.showTableDesc() + tableList;
            resultVO.setDownloadName("all.csv");
            resultVO.setDownloadJson(tableList);
            resultVO.setDownloadUrl("/json_export");
            resultVO.setDownloadSlogan("下载CSV文件");
        } else if (command == 2) {
            String[] list = inputVO.getSecondParamValue().split("[\\W]");
            List<String> idList = new ArrayList<String>();
            for (String s :
                    list) {
                if (StringUtils.isNotBlank(s)) {
                    s = s.trim();
                    idList.add(s);
                    LOG.info(s);
                }

            }
            resMsg = AnalyseToeflArticle.analyseArticleListByIdList(idList).toFormatString();
        } else if (command == 3) {
            String id = inputVO.getSecondParamValue().trim();
            ToeflArticle ta = AnalyseToeflArticle.getToeflArtMap().get(id);
            if (ta != null) {
                resMsg = ta.showArticle();
            } else {
                resMsg = "错误的id";
            }

        } else if (command == 4) {
            resMsg = AnalyseToeflArticle.analyseAllArticleList().toFormatString();
        } else if (command == 5) {
            resMsg = AnalyseToeflArticle.analyseAllReadingArticleList().toFormatString();
        } else if (command == 6) {
            resMsg = AnalyseToeflArticle.analyseAllListeningArticleList().toFormatString();
        } else {
            resMsg = "错误的命令";
        }

        LOG.info("[post_toefl_article_json] result is " + resMsg);
        resultVO.setResult(resMsg);
        resultVO.setRetryUrl("get_toefl_article_analysis_result");
        resultVO.setRetrySlogan("再试一次");
        model.addAttribute("result", resultVO);
        return "tools_result";
    }


    /**
     * 生成excel并将虚拟好的数据写入
     *
     * @throws IOException
     */
    @GetMapping("/excelExport")
    @ResponseBody
    public Object excelExport(HttpServletResponse response) throws IOException {

        String ss = "sdfasfasdfasfsad";
        // 设置输出文件类型为excel文件
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //默认Excel名称
        response.setHeader("Content-disposition", "attachment;filename=user.xls");
        OutputStream os = response.getOutputStream();
        os.write(ss.getBytes());

        os.flush();
        os.close();
        return response;

    }


    /**
     * 生成excel并将虚拟好的数据写入
     *
     * @throws IOException
     */
    @PostMapping("/json_export")
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> jsonExport(ToolsResultVO resultVO) {
        String filename = "";
        String text = "";
        LOG.info("[json_export] resultVO is " + JSONUtils.toJSONString(resultVO));
        if (resultVO != null) {
            filename = resultVO.getDownloadName();
            text = resultVO.getDownloadJson();
        }
        final String content = text;
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(content.getBytes().length);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());
        final StreamingResponseBody ret = out -> {
            out.write(content.getBytes());
        };
        return new ResponseEntity<>(ret, headers, HttpStatus.OK);
    }
}
