package ma.ensa.younes.hamdane.batch.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ma.ensa.younes.hamdane.batch.entities.Personne;
import ma.ensa.younes.hamdane.batch.items.BatchLauncher;
import ma.ensa.younes.hamdane.batch.items.ItemPersonneProcessor;
import ma.ensa.younes.hamdane.batch.items.ItemPersonneWriter;

@Configuration
@ComponentScan("ma.ensa.younes.hamdane.batch.dao")
@EnableBatchProcessing
@EnableTransactionManagement
@EnableScheduling
public class AppConfig {

	@Value("/personnes.txt")
	private Resource input;
	
	@Autowired
    public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/tp_batchScheduled");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addAnnotatedClasses(Personne.class);
		sessionBuilder.addProperties(getHibernateProperties());
		return sessionBuilder.buildSessionFactory();
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		return properties;
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
	
	@Bean
    public LineMapper<Personne> lineMapper() {
        DefaultLineMapper<Personne> lineMapper = new DefaultLineMapper<Personne>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "cin", "nom", "prenom","civilite" });
        lineTokenizer.setDelimiter(",");
        BeanWrapperFieldSetMapper<Personne> fieldSetMapper = new BeanWrapperFieldSetMapper<Personne>();
        fieldSetMapper.setTargetType(Personne.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
	
    @Bean
    public FlatFileItemReader<Personne> reader() {
        FlatFileItemReader<Personne> itemReader = new FlatFileItemReader<Personne>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setResource(input);
        return itemReader;
    }
    @Bean
    public ItemProcessor<Personne,Personne> processor(){
    	return new ItemPersonneProcessor();
    }
    
    @Bean
    public ItemWriter<Personne> writer(){
    	return new ItemPersonneWriter();
    }
	
    @Bean(name="importPersonnes")
    public Job importPersones(JobBuilderFactory jobs) {
        return jobs.get("importPersones")
                .start(step())
                .build();
    }
    
    @Bean
    public Step step() {
        return stepBuilderFactory.get("step1")
                .<Personne,Personne>chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    
    
    @Bean
    public JobRepository jobRepository() throws Exception  {
        return new MapJobRepositoryFactoryBean(getTransactionManager(getSessionFactory(getDataSource()))).getObject();
    }
    
    @Bean(name="jobLauncher")
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;
    }
    @Bean
    public BatchLauncher launchBatch(){
    	return new BatchLauncher();
    }
    
    @Scheduled(cron = "4 57 * * * *")
    public void scheduleFixedDelayTask() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
          launchBatch().run();	
    }
    

}
