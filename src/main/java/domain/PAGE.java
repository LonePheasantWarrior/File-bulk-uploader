package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

//影像属性
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PAGE")
public class PAGE {
    //传入标识
    @XmlAttribute(name = "ACTION")
    private String ACTION;

    //影像创建时间
    @XmlAttribute(name = "OPERATE_TIME")
    private String OPERATE_TIME;

    //影像名称
    @XmlAttribute(name = "FILENAME")
    private String FILENAME;

    //文件类型
    @XmlAttribute(name = "TYPE")
    private String TYPE;

    public String getACTION() {
        return ACTION;
    }

    public void setACTION(String ACTION) {
        this.ACTION = ACTION;
    }

    public String getOPERATE_TIME() {
        return OPERATE_TIME;
    }

    public void setOPERATE_TIME(String OPERATE_TIME) {
        this.OPERATE_TIME = OPERATE_TIME;
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public void setFILENAME(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
