package com.jzfq.fms.job;

import com.jzfq.fms.AbstractTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhishuo on 11/16/16.
 */
@Transactional
public class GenerateRepayPlanTaskTest extends AbstractTest {

    @Autowired
    private GenerateRepayPlanTask generateRepayPlanTask;
    
    @Test
    @Rollback(false)
    public void calculateOverdueTest(){
        generateRepayPlanTask.calculateOverdue();
    }
    
    
    
}