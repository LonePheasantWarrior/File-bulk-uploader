package domain;

import javax.xml.bind.annotation.*;
import java.util.List;

//影像类型
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TREE")
public class TREE {
    //影像类型
    @XmlAttribute(name = "ID")
    private String ID;

    //影像类型名称
    @XmlAttribute(name = "NAME")
    private String NAME;

    //影像相关信息
    @XmlElement(name = "PAGE")
    private List<PAGE> PAGE;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public List<domain.PAGE> getPAGE() {
        return PAGE;
    }

    public void setPAGE(List<domain.PAGE> PAGE) {
        this.PAGE = PAGE;
    }
}
