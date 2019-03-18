package service;

import domain.util.FileScannerUtils;

import java.io.File;
import java.util.Set;

public class FileScannerProcessor implements Processor<BatchContext, BatchContext> {
    @Override
    public BatchContext process(BatchContext batchContext) throws Exception {
        Set<File> fileList = FileScannerUtils.listAllFiles();
        batchContext.setFileList(fileList);
        return batchContext;
    }
}
