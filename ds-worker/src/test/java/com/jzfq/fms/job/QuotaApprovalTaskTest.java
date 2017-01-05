package com.jzfq.fms.job;

import com.jzfq.fms.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liu on 2016/12/13.
 */
@Transactional
public class QuotaApprovalTaskTest extends AbstractTest {
    @Autowired
    private QuotaApprovalTask quotaApprovalTask;

    @Test
    public void executeTest() throws Exception{
        quotaApprovalTask.execute();
    }

}
