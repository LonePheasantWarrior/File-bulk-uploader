package domain.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 目录扫描工具
 */
public class FileScannerUtils {
    private static Log log = LogFactory.getLog(FileScannerUtils.class);

    //TODO 具体路径待填充
    private static final String PATH = "";

    /**
     * 列出指定目录下的所有文件
     * @return 文件列表
     */
    public static List<File> listAllFiles(){
        File file = new File(PATH);
        return doListForFile(file);
    }

    /**
     * 列出指定目录下的所有文件名称
     * @return  文件名称列表
     */
    public static List<String> listAllFileNames(){
        File file = new File(PATH);
        return doListForFileName(file);
    }

    private static List<File> doListForFile(File file){
        int count = 0;
        List<File> reslut = new ArrayList<>();

        if (file.isFile()){
            count++;
            reslut.add(file);
        } else {
            if (file.exists() && file.isDirectory()){
                File[] files = file.listFiles();
                if (files != null && files.length > 0)
                for (File file1 : files){
                    doListForFile(file1);
                }
            }
        }
        log.info(count + " files found");
        return reslut;
    }

    private static List<String> doListForFileName(File file){
        int count = 0;
        List<String> reslut = new ArrayList<>();

        if (file.isFile()){
            count++;
            reslut.add(file.getName());
        } else {
            if (file.exists() && file.isDirectory()){
                File[] files = file.listFiles();
                if (files != null && files.length > 0)
                    for (File file1 : files){
                        doListForFileName(file1);
                    }
            }
        }
        log.info(count + " files found");
        return reslut;
    }
}
