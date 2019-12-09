package org.mine.quartz.job.logic;

import java.util.Map;
import java.util.concurrent.Callable;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;

public class JobRunnable implements Callable<CallableResult>{

	private BaseServiceTasketExcutor excutor;
	
	private Map<String, Object> excutorInfo;
	
	public JobRunnable(BaseServiceTasketExcutor excutor, Map<String, Object> excutorInfo) {
		this.excutor = excutor;
		this.excutorInfo = excutorInfo;
	}
	
	@Override
	public CallableResult call(){
		CallableResult result = new CallableResult();
		try{
			GitContext.doIndependentTransActionControl(new BatchOperator() {
				
				@Override
				public Object call(Map<String, Object> map) {
					// TODO Auto-generated method stub
					excutor.excutor(map);
					return null;
				}
			}, excutorInfo);
			result.setResult(true);
		} catch(Throwable e){
			result.setResult(false);
			result.setMseeage(e instanceof MineException ? (((MineException)e).getError_msg()) : e.getMessage());
		}
		return result;
	}
}
