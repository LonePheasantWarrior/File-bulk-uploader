package service;

import domain.util.FileScannerUtils;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;
import java.util.Set;

public class FileScannerProcessor implements ItemProcessor<String, BatchContext> {
    @Override
    public BatchContext process(String args) throws Exception {
        BatchContext batchContext = new BatchContext();
        Set<File> fileList = FileScannerUtils.listAllFiles();
        batchContext.setFileList(fileList);
        return batchContext;
    }
}
