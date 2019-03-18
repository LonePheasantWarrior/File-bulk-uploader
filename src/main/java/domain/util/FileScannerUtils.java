package domain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * 目录扫描工具
 */
public class FileScannerUtils {
    private static Logger log = LoggerFactory.getLogger(FileScannerUtils.class);

    //TODO 具体路径待填充
    private static final String PATH = "C:\\Users\\wxhme\\Pictures\\照片";

    /**
     * 列出指定目录下的所有文件
     * @return 文件列表
     */
    public static Set<File> listAllFiles(){
        log.info("Scanning local files...");
        Set<File> fileReslut = new HashSet<>();

        File file = new File(PATH);
        doListForFile(file,fileReslut);
        if (fileReslut.size() > 0){
            log.info(fileReslut.size() + " files found");
        }else {
            log.warn("Did not find any files!");
        }
        return fileReslut;
    }

    /**
     * 列出指定目录下的所有文件名称
     * @return  文件名称列表
     */
    public static Set<String> listAllFileNames(){
        log.info("Scanning local files...");
        Set<String> nameReslut = new HashSet<>();

        File file = new File(PATH);
        doListForFileName(file,nameReslut);
        if (nameReslut.size() > 0){
            log.info(nameReslut.size() + " files found");
        }else {
            log.warn("Did not find any files!");
        }
        return nameReslut;
    }

    private static void doListForFile(File file,Set<File> fileResult){
        if (file.isFile()){
            fileResult.add(file);
        } else {
            if (file.exists() && file.isDirectory()){
                File[] files = file.listFiles();
                if (files != null && files.length > 0)
                for (File file1 : files){
                    doListForFile(file1,fileResult);
                }
            }
        }
    }

    private static void doListForFileName(File file,Set<String> nameReslut){
        if (file.isFile()){
            nameReslut.add(file.getName());
        } else {
            if (file.exists() && file.isDirectory()){
                File[] files = file.listFiles();
                if (files != null && files.length > 0)
                    for (File file1 : files){
                        doListForFileName(file1,nameReslut);
                    }
            }
        }
    }
}
