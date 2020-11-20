package com.quartz.traditional;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.Properties;

import org.mine.aplt.exception.MineException;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 重写Quartz. 以满足自定义的各种定时需求.
 * @ClassName: StdSchedulerFactoryDecoration
 * @author: wntl
 * @date: 2020年6月11日 下午7:21:56
 */
public class StdSchedulerFactoryDecoration extends StdSchedulerFactory {
	private final static Logger logger = LoggerFactory.getLogger(StdSchedulerFactoryDecoration.class);
	/**
	 * scheduler实例名称
	 */
	private String instanceName = "";
	/**
	 * 线程名
	 */
	private String threadName = "";
	/**
	 * 线程数
	 */
	private int threadCount = 0;
	
	/**
	 * @param: @param instanceName scheduler实例名称
	 * @param: @param threadName 线程名, 可为空
	 * @param: @param threadCount  线程数, 可为0
	 * @throws
	 */  
	public StdSchedulerFactoryDecoration(String instanceName, String threadName, int threadCount) {
		this.instanceName = instanceName;
		if (threadCount > 0) this.threadCount = threadCount;
		if (threadName != null && threadName.length() > 0) this.threadName = threadName;
	}
	
	/**
	 * @return the instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @param instanceName the instanceName to set
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
	/**
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * @param threadName the threadName to set
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	/**
	 * @return the threadCount
	 */
	public int getThreadCount() {
		return threadCount;
	}

	/**
	 * @param threadCount the threadCount to set
	 */
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	/**
	 * <p>Description: </p>
	 * <p>Title: initialize</p>
	 * @throws SchedulerException
	 * @see org.quartz.impl.StdSchedulerFactory#initialize()
	 */
	@Override
	public void initialize() throws SchedulerException {
		String requestedFile = System.getProperty(PROPERTIES_FILE);
		String propFileName = requestedFile != null ? requestedFile : "batch.properties";
		File propFile = new File(propFileName);
		Properties props = new Properties();
		InputStream in = null;
		try {
			String propSrc = "";
			if (propFile.exists()) {
				try {
					if (requestedFile != null) {
						propSrc = "specified file: '" + requestedFile + "'";
					} else {
						propSrc = "default file in current working dir: 'batch.properties'";
					}
					logger.info(propSrc);
					in = new BufferedInputStream(new FileInputStream(propFileName));
					props.load(in);
				} catch (IOException ioe) {
					throw new SchedulerException("Properties file: '" + propFileName + "' could not be read.", ioe);
				}
			} else if (requestedFile != null) {
				in = Thread.currentThread().getContextClassLoader().getResourceAsStream(requestedFile);
				if (in == null) {
					throw new SchedulerException("Properties file: '" + requestedFile + "' could not be found.");
				}
				propSrc = "specified file: '" + requestedFile + "' in the class resource path.";
				in = new BufferedInputStream(in);
				try {
					props.load(in);
				} catch (IOException ioe) {
					throw new SchedulerException("Properties file: '" + requestedFile + "' could not be read.", ioe);
				}
				logger.info(propSrc);
			} else {
				propSrc = "default resource file in Quartz package: 'batch.properties'";
				logger.info(propSrc);
				ClassLoader cl = getClass().getClassLoader();
				if (cl == null) {
					cl = findClassloader();
				}
				if (cl == null) {
					throw new SchedulerConfigException("Unable to find a class loader on the current thread or class.");
				}
				in = cl.getResourceAsStream("batch.properties");
				if (in == null) {
					in = cl.getResourceAsStream("/batch.properties");
				}
				if (in == null) {
					in = cl.getResourceAsStream("org/quartz/quartz.properties");
				}
				if (in == null) {
					throw new SchedulerException("Default batch.properties not found in class path");
				}
				try {
					props.load(in);
				} catch (IOException ioe) {
					throw new SchedulerException("Resource properties file: 'org/batch/batch.properties' "
							+ "could not be read from the classpath.", ioe);
				}
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignore) {/*ignore*/}
			}
		}

		Properties sysProps = null;
		try {
			sysProps = System.getProperties();
		} catch (AccessControlException e) {
			logger.error(MineException.getStackTrace(e));
			getLog().warn("Skipping overriding batch properties with System properties "
					+ "during initialization because of an AccessControlException.  "
					+ "This is likely due to not having read/write access for "
					+ "java.util.PropertyPermission as required by java.lang.System.getProperties().  "
					+ "To resolve this warning, either add this permission to your policy file or "
					+ "use a non-default version of initialize().", e);
		}
		if (sysProps != null) {
			props.putAll(sysProps);
		}
		customProps(props);
		initialize(props);
	}
	
	private ClassLoader findClassloader() {
		// work-around set context loader for windows-service started jvms
		// (QUARTZ-748)
		if (Thread.currentThread().getContextClassLoader() == null && getClass().getClassLoader() != null) {
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		}
		return Thread.currentThread().getContextClassLoader();
	}
	
	/**
	 * 自定义参数值
	 * @param props
	 */
	private void customProps(Properties props) {
		// 输入实例名
		if (instanceName != null && instanceName.trim().length() > 0) {
			props.put(PROP_SCHED_INSTANCE_NAME, instanceName);
			props.put(PROP_SCHED_INSTANCE_ID, instanceName);
		}

		// 指定当前Scheduler实例执行时的线程名, 未指定的话使用当前instanceName+_QuartzSchedulerThread
		if (threadName != null && threadName.trim().length() > 0) {
			props.put("org.batch.scheduler.threadName", threadName);
		}

		if (threadCount > 0) {
			props.put("org.quartz.threadPool.threadCount", threadCount);
		}
	}
}
