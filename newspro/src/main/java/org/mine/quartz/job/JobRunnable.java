package org.mine.quartz.job;

import java.util.Map;
import java.util.concurrent.Callable;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTasketExcutor;

public class JobRunnable implements Callable<CallableResult>{

	private BaseServiceTasketExcutor excutor;
	
	private Map<String, Object> excutorInfo;
	
	public JobRunnable(BaseServiceTasketExcutor excutor, Map<String, Object> excutorInfo) {
		this.excutor = excutor;
		this.excutorInfo = excutorInfo;
	}
	
	@Override
	public CallableResult call() throws Exception {
		CallableResult result = new CallableResult();
		try{
			excutor.excutor(excutorInfo);
			result.setResult(true);
		} catch(Throwable e){
			result.setResult(false);
			result.setMseeage(e instanceof MineException ? (((MineException)e).getError_msg()) : e.getMessage());
		}
		return result;
	}
}
