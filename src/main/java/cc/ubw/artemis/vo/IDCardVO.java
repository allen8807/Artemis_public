/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.ubw.artemis.vo;

/**
 * Author Allen
 * Date 2019年11月11日 下午6:00:34
 * Version V1.0
 * Desc
 * 2019.12.08废弃
 */
public class IDCardVO {

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
     * @return the lastFour
     */
    public String getLastFour() {
        return lastFour;
    }

    /**
     * @param lastFour the lastFour to set
     */
    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    private String districtSix;
    private String birthdayEight;
    private String lastFour;
}
