/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.ubw.artemis.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Allen 2019年11月5日 下午3:32:28 V1.0
 */
public class IDCard {

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the districtSix
     */
    public String getDistrictSix() {
        return districtSix;
    }

    /**
     * @param districtSix the districtSix to set
     */
    public void setDistrictSix(String districtSix) {
        this.districtSix = districtSix;
    }

    /**
     * @return the birthdayEight
     */
    public String getBirthdayEight() {
        return birthdayEight;
    }

    /**
     * @param birthdayEight the birthdayEight to set
     */
    public void setBirthdayEight(String birthdayEight) {
        this.birthdayEight = birthdayEight;
    }

    /**
     * @return the randomTwo
     */
    public String getRandomTwo() {
        return randomTwo;
    }

    /**
     * @param randomTwo the randomTwo to set
     */
    public void setRandomTwo(String randomTwo) {
        this.randomTwo = randomTwo;
    }

    /**
     * @return the genderOne
     */
    public String getGenderOne() {
        return genderOne;
    }

    /**
     * @param genderOne the genderOne to set
     */
    public void setGenderOne(String genderOne) {
        this.genderOne = genderOne;
    }

    /**
     * @return the checkOne
     */
    public String getCheckOne() {
        return checkOne;
    }

    /**
     * @param checkOne the checkOne to set
     */
    public void setCheckOne(String checkOne) {
        this.checkOne = checkOne;
    }

    private String id;

    private String districtSix;
    private String birthdayEight;
    private String randomTwo;
    private String genderOne;
    private String checkOne;

    //身份证数组
    List<Integer> idCardArray;
    //身份证校验位，从右向左， 2^(i-1) mod 11 ,这里反过来
    static List<Integer> w;

    static {
        List<Integer> wTemp = new ArrayList<>();
        //身份证校验位，从右向左， 2^(i-1) mod 11 ,这里先添加最后反过来
        wTemp.add(1);
        wTemp.add(2);
        wTemp.add(4);
        wTemp.add(8);
        wTemp.add(5);

        wTemp.add(10);
        wTemp.add(9);
        wTemp.add(7);
        wTemp.add(3);
        wTemp.add(6);

        wTemp.add(1);
        wTemp.add(2);
        wTemp.add(4);
        wTemp.add(8);
        wTemp.add(5);

        wTemp.add(10);
        wTemp.add(9);
        wTemp.add(7);

        Collections.reverse(wTemp);
        w = wTemp;
    }

    public IDCard(String districtSix,
                  String birthdayEight,
                  String gender) {
        Random rd = new Random(System.currentTimeMillis());
        Integer a = rd.nextInt(10);
        Integer b = rd.nextInt(10);
        String rdTwo = Integer.toString(a) + Integer.toString(b);
        generateCard(districtSix, birthdayEight, rdTwo, gender);

    }

    final void generateCard(String districtSix,
                            String birthdayEight,
                            String randomTwo,
                            String gender) {
        this.setDistrictSix(districtSix);
        this.setBirthdayEight(birthdayEight);
        this.setRandomTwo(randomTwo);
        this.setGenderOne(gender);
        String idTemp
                = this.getDistrictSix() + this.getBirthdayEight() + this.getRandomTwo() + this.getGenderOne();

        idCardArray = new ArrayList<>();
        for (int i = 0; i < idTemp.length(); i++) {
            Integer n = Integer.parseInt(String.valueOf(idTemp.charAt(i)));
            idCardArray.add(n);
        }
        Integer checkNum = calcCheckNum(idCardArray);
        if (checkNum == 10) {
            idTemp = idTemp + "x";
        } else {
            idTemp = idTemp + String.valueOf(checkNum);
        }
        id = idTemp;
    }

    private static Integer calcCheckNum(List<Integer> front17) {
        Integer res;
        Integer sum = 0;
        for (int i = 0; i < front17.size(); i++) {
            sum = front17.get(i) * w.get(i) + sum;
        }
        res = (12 - (sum % 11)) % 11;
        return res;
    }

    /**
     * 校验身份证号
     *
     * @param districtSix
     * @param birthdayEight
     * @param lastFour
     * @return
     */
    public static Boolean checkIDCard(String districtSix,
                                      String birthdayEight,
                                      String lastFour) {
        String idTemp = districtSix + birthdayEight + lastFour;
        List<Integer> a17 = new ArrayList<>();
        for (int i = 0; i < idTemp.length() - 1; i++) {
            if (!Character.isDigit(idTemp.charAt(i))) {
                return false;
            }
            Integer n = Integer.parseInt(String.valueOf(idTemp.charAt(i)));
            a17.add(n);
        }
        Integer a18;
        if (Character.isDigit(idTemp.charAt(17))) {
            a18 = Integer.parseInt(String.valueOf(idTemp.charAt(17)));
        } else if (idTemp.charAt(17) == 'x' || idTemp.charAt(17) == 'X') {
            a18 = 10;
        } else {
            return false;
        }
        Integer checkNum = calcCheckNum(a17);
        return checkNum.equals(a18);
    }

    /**
     * 返回正确的身份证号
     *
     * @param districtSix
     * @param birthdayEight
     * @param lastFour
     * @return
     */
    public static String giveRightIDCard(String districtSix,
                                         String birthdayEight,
                                         String lastFour) {
        String idTemp = districtSix + birthdayEight + lastFour.substring(0, 3);
        List<Integer> a17 = new ArrayList<>();
        for (int i = 0; i < idTemp.length(); i++) {
            if (!Character.isDigit(idTemp.charAt(i))) {
                return "";
            }
            Integer n = Integer.parseInt(String.valueOf(idTemp.charAt(i)));
            a17.add(n);
        }

        Integer checkNum = calcCheckNum(a17);
        if (checkNum == 10) {
            idTemp = idTemp + "X";
        } else {
            idTemp = idTemp + String.valueOf(checkNum);
        }
        return idTemp;
    }

}
