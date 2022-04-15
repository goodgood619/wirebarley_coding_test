package com.example.wirebarley_coding_test.config;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import com.example.wirebarley_coding_test.job.ExchangeRateJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

  @Bean
  public JobDetail checkExchangeRate() {
    return JobBuilder.newJob().ofType(ExchangeRateJob.class)
        .storeDurably()
        .withIdentity("checkExchangeRate")
        .withDescription("earn Exchange Rate")
        .build();
  }

  @Bean
  public Trigger checkExchangeRateTrigger(JobDetail checkExchangeRate) {
    return TriggerBuilder.newTrigger().forJob(checkExchangeRate)
        .withIdentity("checkExchangeRate")
        .withDescription("earn Exchange Rate")
        .withSchedule(cronSchedule("0/30 * * * * ?")) // 30초 간격으로 최신화
        .build();
  }

}
