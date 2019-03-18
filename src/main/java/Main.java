import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 批处理上传工具
 * @author wangxiaohu@csii.com.cn
 */
public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {
        log.info("Batch processor initializing...");

        String[] configLocations = {"applicationContext.xml"};
        new ClassPathXmlApplicationContext(configLocations);

        log.info("Batch processor loading finished");
    }

}
