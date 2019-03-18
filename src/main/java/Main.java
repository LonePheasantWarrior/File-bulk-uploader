import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.*;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {
        log.info("Batch processor starting...");

        String[] configLocations = {"applicationContext.xml"};
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocations);

        TaskJob taskJob = (TaskJob) applicationContext.getBean("taskJob");
        try {
            taskJob.batchJob();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
