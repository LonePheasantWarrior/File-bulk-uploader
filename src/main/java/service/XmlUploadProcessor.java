package service;

import domain.META_DATA;
import domain.PAGE;
import domain.TREE;
import domain.TREE_NODE;
import domain.util.FileUploader;
import domain.util.JAXBUtils;
import domain.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

public class XmlUploadProcessor implements Processor<BatchContext,String> {
    private static Logger logger = LoggerFactory.getLogger(XmlUploadProcessor.class);

    private static final int RETRY = 3;
    private static final String APP_CODE = "CREDIT";
    private static final String CASE_NO = "Y017121514192646";

    @Override
    public String process(BatchContext batchContext) throws Exception {

        Set<String> uploadedNames = batchContext.getUploadedNameList();
        String app_no = "C8037" + TimeUtils.getCurrentTimeSeconds();
        logger.info("APP_NO: " + app_no);

        if (!(uploadedNames.size() > 0)){
            logger.error("No uploaded file to process");
            return null;
        }else {
            List<PAGE> PAGEList = new ArrayList<>();
            for (String fileName : uploadedNames){
                PAGE page = new PAGE();
                page.setACTION("add");
                page.setOPERATE_TIME(TimeUtils.getSystemTime());
                page.setFILENAME(fileName);
                page.setTYPE("01");

                PAGEList.add(page);
            }

            TREE tree = new TREE();
            tree.setID("01");
            tree.setNAME("身份证明");
            tree.setPAGE(PAGEList);

            TREE_NODE tree_node = new TREE_NODE();
            tree_node.setTREE(tree);

            META_DATA meta_data = new META_DATA();
            meta_data.setAPP_CODE(APP_CODE);
            meta_data.setAPP_NO(app_no);
            meta_data.setCASE_NO(CASE_NO);
            meta_data.setTREE_NODE(tree_node);

            String xml = JAXBUtils.convertToXml(meta_data);
            batchContext.setXmlStr(xml);

            logger.info("\n" + xml);

            xmlUpload(xml,app_no);

            return xml;
        }
    }

    private void xmlUpload(String xml,String app_no) {
        if (xml != null && !"".equals(xml)){
            try {
                doXmlUpload(xml,app_no,0);
            } catch (InterruptedException e) {
                logger.error("xml file upload error",e);
            }
        }else {
            logger.error("The XML file to be uploaded is empty");
        }
    }

    private void doXmlUpload(String xml,String app_no,int reTry) throws InterruptedException {
        try {
            if (!FileUploader.ftpUpload(xml.getBytes(StandardCharsets.UTF_8),app_no + ".xml")){
                logger.warn("The file of [" + app_no + ".xml] Upload fail,will be re-uploaded in two seconds");
                throw new Exception("Upload fail");
            }
        } catch (Exception e) {
            if (reTry++ < RETRY){
                sleep(2000);
                logger.debug("Start the "+reTry+"th upload");
                doXmlUpload(xml,app_no,reTry);
            }else {
                logger.error("Upload failed, the number of retries is " + --reTry);
            }
        }
    }

}
