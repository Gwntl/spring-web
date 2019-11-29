package org.mine.aplt.codeGenerator;

import org.mine.aplt.codeGenerator.dto.DelegationDto;

public class StringBufferDelegation {

	public static final String TABS = "\t";
	
	private ThreadLocal<DelegationDto> deldgationDto = new ThreadLocal<DelegationDto>(){
		@Override
		protected DelegationDto initialValue(){
			return new DelegationDto();
		}
	};
	
	public StringBuffer getStringBuffer(){
		return deldgationDto.get().getBuffer();
	}
	
	public Integer getTabsCount(){
		return deldgationDto.get().getTabsCount();
	}
	
	public void setTabsCount(int count){
		deldgationDto.get().setTabsCount(count);
	}
	
	public StringBuffer append(String str){
		int count = getTabsCount();
		char[] chars = str.toCharArray();
		int length = chars.length;
		while(length >= 0){
			if(chars[length] == 123){
				count ++;
				break;
			}
			
			if(chars[length] == 125){
				count --;
				break;
			}
		}
		int count_bak = count;
		StringBuffer tabs = new StringBuffer();
		if(count_bak > 0){
			while(count_bak > 0){
				
				count_bak--;
			}
		}
		
		
		return getStringBuffer().append(str);
				
	}
}
