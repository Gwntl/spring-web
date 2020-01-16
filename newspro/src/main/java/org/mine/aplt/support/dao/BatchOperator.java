package org.mine.aplt.support.dao;

/**
 * 批量操作回调 
 *	lambda表达式写法: <code> map -> (return ;)</code>
 * @filename BatchOperator.java 
 * @author wzaUsers
 * @date 2020年1月7日上午10:22:20 
 * @version v1.0
 */
@FunctionalInterface
public interface BatchOperator<R, I> {
	R call(I input);
}
