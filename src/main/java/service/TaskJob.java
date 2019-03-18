package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;

public class TaskJob {
    private Logger logger = LoggerFactory.getLogger(TaskJob.class);

    private FileScannerProcessor fileScannerProcessor;
    private FileUploadProcessor fileUploadProcessor;
    private XmlUploadProcessor xmlUploadProcessor;

    private BatchContext batchContext;

    public void batchJob() throws Exception {
        batchContext.setUploadFailedList(new HashSet<String>());
        batchContext.setUploadedNameList(new HashSet<String>());
        batchContext.setFileList(new HashSet<File>());
        batchContext.setNameList(new HashSet<String>());
        batchContext.setXmlStr("");
        batchContext.setStatus("ready");

        logger.info("Batch processor starting...");
        long beginTime = System.currentTimeMillis();

        try {
            fileScannerProcessor.process(batchContext);
            fileUploadProcessor.process(batchContext);
            xmlUploadProcessor.process(batchContext);
        } catch (Exception e) {
            logger.error(e.toString());
            throw e;
        }

        batchContext.setStatus("completed");
        logger.info("Batch processor mission completed and takes " + (System.currentTimeMillis() - beginTime) / 1000 + " seconds");
    }



    public FileScannerProcessor getFileScannerProcessor() {
        return fileScannerProcessor;
    }

    public void setFileScannerProcessor(FileScannerProcessor fileScannerProcessor) {
        this.fileScannerProcessor = fileScannerProcessor;
    }

    public FileUploadProcessor getFileUploadProcessor() {
        return fileUploadProcessor;
    }

    public void setFileUploadProcessor(FileUploadProcessor fileUploadProcessor) {
        this.fileUploadProcessor = fileUploadProcessor;
    }

    public XmlUploadProcessor getXmlUploadProcessor() {
        return xmlUploadProcessor;
    }

    public void setXmlUploadProcessor(XmlUploadProcessor xmlUploadProcessor) {
        this.xmlUploadProcessor = xmlUploadProcessor;
    }

    public BatchContext getBatchContext() {
        return batchContext;
    }

    public void setBatchContext(BatchContext batchContext) {
        this.batchContext = batchContext;
    }
}
