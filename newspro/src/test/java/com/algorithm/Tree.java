package com.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 树结构基础类
 * @ClassName: Tree
 * @author: wntl
 * @date: 2020年4月16日 下午4:16:18
 */
public abstract class Tree {
	protected static final Logger LOGGER = LoggerFactory.getLogger(Tree.class);
	/**
	 * 前序遍历
	 */
	static final String TRAVERSAL_0 = "PREORDER";
	/**
	 * 中序遍历
	 */
	static final String TRAVERSAL_1 = "INORDER";
	/**
	 * 后序遍历
	 */
	static final String TRAVERSAL_2 = "POSTORDER";
	
	 abstract void add(int value);
	 
	 abstract void remove(int value);
}
