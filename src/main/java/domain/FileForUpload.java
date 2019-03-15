package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IN")
public class FileForUpload {
    //文件列表
    @XmlElement(name = "META_DATA")
    private META_DATA META_DATA;

    public domain.META_DATA getMETA_DATA() {
        return META_DATA;
    }

    public void setMETA_DATA(domain.META_DATA META_DATA) {
        this.META_DATA = META_DATA;
    }
}

/**
 * <?xml version="1.0" encoding="utf-8"?>
 * <IN>
 *     <META_DATA>
 *         <APP_CODE>CREDIT</APP_CODE>
 *         <APP_NO>C803620180602003574</APP_NO>
 *         <CASE_NO>Y017121514192646</CASE_NO>
 *         <TREE_NODE>
 *             <TREE ID="01" NAME="身份证明">
 *                 <PAGE ACTION="ADD" OPERATE_TIME="2017-12-15 14:21:03" FILENAME="IDnumber1513310481401.jpg" TYPE="01"/>
 *                 <PAGE ACTION="ADD" OPERATE_TIME="2017-12-15 14:21:03" FILENAME="IDnumber1513310481394.jpg" TYPE="01"/>
 *             </TREE>
 *         </TREE_NODE>
 *     </META_DATA>
 * </IN>
 */
