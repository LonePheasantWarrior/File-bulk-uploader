import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import service.BatchContext;
import service.FileScannerProcessor;
import service.FileUploadProcessor;
import service.XmlUploadProcessor;

public class Main {
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.info("Batch processor starting...");

        String[] configLocations = {"applicationContext.xml"};
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocations);

        PlatformTransactionManager transactionManager = applicationContext.getBean(PlatformTransactionManager.class);

        // 获取任务启动器
        JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
        SimpleJobRepository jobRepository = applicationContext.getBean(SimpleJobRepository.class);

        //初始化Processor
        FileScannerProcessor fileScannerProcessor = new FileScannerProcessor();
        FileUploadProcessor fileUploadProcessor = new FileUploadProcessor();
        XmlUploadProcessor xmlUploadProcessor = new XmlUploadProcessor();

        // 创建Step
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
        Step step = stepBuilderFactory.get("step")
                .<String, BatchContext>chunk(1)
                .processor(fileScannerProcessor)
                .build();

        Step step2 = stepBuilderFactory.get("step")
                .<BatchContext, BatchContext>chunk(1)
                .processor(fileUploadProcessor)
                .build();

        Step step3 = stepBuilderFactory.get("step")
                .<BatchContext, String>chunk(1)
                .processor(xmlUploadProcessor)
                .build();

        JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
        Job job = jobBuilderFactory.get("job")
                .start(step)
                .start(step2)
                .start(step3)
                .build();

        jobLauncher.run(job, new JobParameters());

    }

}
