package org.mine.aplt.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 
 * @filename MineException.java 
 * @author wzaUsers
 * @date 2019年8月21日下午5:19:35 
 * @version v1.0
 */
public class MineException extends RuntimeException{
	
	private static final long serialVersionUID = 11745362295480131L;
	
	/**
	 * 错误信息
	 */
	private String error_msg;
	
	/**
	 * 错误代码
	 */
	private String error_code;
	
	/**
	 * 错误内容堆栈信息
	 */
	private String error_infos;
	
	/**
	 * 错误信息
	 * @return
	 */
	public String getError_msg() {
		return error_msg;
	}

	/**
	 * 错误信息
	 * @param error_msg
	 */
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	/**
	 * 错误代码
	 * @return
	 */
	public String getError_code() {
		return error_code;
	}

	/**
	 * 错误代码
	 * @param error_code
	 */
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	/**
	 * 错误内容堆栈信息
	 * @return the error_infos
	 */
	public String getError_infos() {
		return error_infos;
	}

	/**
	 * 错误内容堆栈信息
	 * @param error_infos the error_infos to set
	 */
	public void setError_infos(String error_infos) {
		this.error_infos = error_infos;
	}

	public MineException(String error_code, String error_msg){
		super("[" + (error_code == null ? "" : error_code) + "]" + error_msg);
		this.error_code = error_code;
		this.error_msg = error_msg;
	}
	
	public static String getStackTrace(Throwable e){
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			e.printStackTrace(new PrintWriter(stream, true));
			String stack = stream.toString();
			stream.close();
			return stack;
		} catch (IOException e1) {
		}
		return null;
	}
}
