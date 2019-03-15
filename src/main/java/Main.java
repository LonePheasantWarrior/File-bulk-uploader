import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

public class Main {
    private String[] configLocations = {"applicationContext.xml"};
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocations);

    // 获取任务启动器
    JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
    JobRepository jobRepository = applicationContext.getBean(JobRepository.class);
    PlatformTransactionManager transactionManager = applicationContext.getBean(PlatformTransactionManager.class);



}
