package com.quartz.job.abs;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.util.CommonUtils;
import org.quartz.JobKey;
import org.terracotta.quartz.wrappers.JobWrapper;

public class Test {

//	@After
//	public void tearDown() throws Exception {
//	}

	@org.junit.Test
	public void test() {
//		fail("Not yet implemented");
		HashMap<JobKey, JobWrapper> jobsByKey = new HashMap<>();
		jobsByKey.put(new JobKey(ApltContanst.DEFAULT_JOB_NAME +1L, ApltContanst.DEFAULT_JOB_GROUP), null);
		jobsByKey.put(new JobKey(ApltContanst.DEFAULT_JOB_NAME +2L, ApltContanst.DEFAULT_JOB_GROUP), null);
		System.out.println(CommonUtils.toString(jobsByKey));
	}

}
