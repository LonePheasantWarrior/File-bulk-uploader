package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//影像属性信息
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TREE_NODE")
public class TREE_NODE {
    //影像类型
    @XmlElement(name = "TREE")
    private TREE TREE;

    public domain.TREE getTREE() {
        return TREE;
    }

    public void setTREE(domain.TREE TREE) {
        this.TREE = TREE;
    }
}
