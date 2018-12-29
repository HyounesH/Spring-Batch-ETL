package ma.younes.hamdane.batch.tests;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		
			ApplicationContext context = 
				new ClassPathXmlApplicationContext("ma/younes/hamdane/batch/config/appConfig.xml");
				
			JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
			Job job = (Job) context.getBean("importPersonnes");

			try {

				JobExecution execution = jobLauncher.run(job, new JobParameters());
				System.out.println("Exit Status : " + execution.getStatus());

			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Done");

		  }
}


