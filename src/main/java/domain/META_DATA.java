package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//文件
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "META_DATA")
public class META_DATA {
    //传入标示（默认传CREDIT）
    @XmlElement
    private String APP_CODE;

    //申请流水号
    @XmlElement
    private String APP_NO;

    //保单号
    @XmlElement
    private String CASE_NO;

    @XmlElement(name = "TREE_NODE")
    private TREE_NODE TREE_NODE;

    public String getAPP_CODE() {
        return APP_CODE;
    }

    public void setAPP_CODE(String APP_CODE) {
        this.APP_CODE = APP_CODE;
    }

    public String getAPP_NO() {
        return APP_NO;
    }

    public void setAPP_NO(String APP_NO) {
        this.APP_NO = APP_NO;
    }

    public String getCASE_NO() {
        return CASE_NO;
    }

    public void setCASE_NO(String CASE_NO) {
        this.CASE_NO = CASE_NO;
    }

    public domain.TREE_NODE getTREE_NODE() {
        return TREE_NODE;
    }

    public void setTREE_NODE(domain.TREE_NODE TREE_NODE) {
        this.TREE_NODE = TREE_NODE;
    }
}
