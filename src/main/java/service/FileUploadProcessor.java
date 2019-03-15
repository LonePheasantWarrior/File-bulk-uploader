package service;

import domain.util.FileUploader;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileUploadProcessor implements ItemProcessor<BatchContext,BatchContext> {
    @Override
    public BatchContext process(BatchContext batchContext) throws Exception {
        Set<File> fileList = batchContext.getFileList();
        Set<String> uploadedName = new HashSet<>();

        for (File file : fileList){
            if (FileUploader.ftpUpload(file,file.getName())){
                uploadedName.add(file.getName());
            }
        }

        batchContext.setUploadedNameList(uploadedName);
        return batchContext;
    }
}
