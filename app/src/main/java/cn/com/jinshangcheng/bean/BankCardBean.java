package cn.com.jinshangcheng.bean;

/**
 * 银行卡
 */
public class BankCardBean extends BaseBean<BankCardBean> {
    public String accountid;
    public String userid;
    public String accountnum;
    public String accountuser;
    public String accountbank;
    public int isdefault;
    public long registtime;
    public long updatetime;

    public BankCardBean() {
    }

    @Override
    public String toString() {
        return "BankCardBean{" +
                "accountid='" + accountid + '\'' +
                ", userid='" + userid + '\'' +
                ", accountnum='" + accountnum + '\'' +
                ", accountuser='" + accountuser + '\'' +
                ", accountbank='" + accountbank + '\'' +
                ", isdefault='" + isdefault + '\'' +
                ", registtime='" + registtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
