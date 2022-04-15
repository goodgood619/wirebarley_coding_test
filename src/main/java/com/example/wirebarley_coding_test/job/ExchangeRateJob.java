package com.example.wirebarley_coding_test.job;

import com.example.wirebarley_coding_test.batch.BatchService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ExchangeRateJob implements Job {

  private final BatchService batchService;

  public ExchangeRateJob(BatchService batchService) {
    this.batchService = batchService;
  }

  private final Logger logger = LoggerFactory.getLogger(ExchangeRateJob.class);

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    logger.info("매 30초 마다 실행 되는지 확인"
        + "");
    try {
      batchService.batchCheckExchangeRate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
