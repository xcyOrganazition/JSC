package cn.com.jinshangcheng.bean;

/**
 * 我的收益
 */
public class IncomeBean {

    /**
     * totalIncome : 28
     * remainIncome : 28
     * allWithdraw : 0
     */

    private String totalIncome;
    private String remainIncome;
    private String allWithdraw;

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getRemainIncome() {
        return remainIncome;
    }

    public void setRemainIncome(String remainIncome) {
        this.remainIncome = remainIncome;
    }

    public String getAllWithdraw() {
        return allWithdraw;
    }

    public void setAllWithdraw(String allWithdraw) {
        this.allWithdraw = allWithdraw;
    }
}
