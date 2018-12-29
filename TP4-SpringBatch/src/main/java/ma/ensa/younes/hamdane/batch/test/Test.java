package ma.ensa.younes.hamdane.batch.test;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ma.ensa.younes.hamdane.batch.configuration.AppConfig;

public class Test {
	public static void main(String[] args) throws Exception{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
	   Job job = (Job) ctx.getBean("importPersonnes");
	   JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher"); 
	   JobExecution jobex = jobLauncher.run(job, new JobParameters());
	   System.out.println(jobex.getStatus());
		
	}
	
}
