package com.algorithm;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 红黑树JAVA实现.</br>
 * 性质:</br>
 * 1. 是一个二叉搜索树. </br>
 * 2. 节点具有颜色属性, 分别为红色和黑色.</br>
 * 3. 根节点一定为黑色.</br>
 * 4. 红色节点不可连续, 红色节点的字节点一定是黑色.</br>
 * 5. 叶子节点为黑色. 其代表含义为(NIL/NULL节点, 即空节点.) </br>
 * 6. 任意一个节点抵达叶子节点的任何路径中路过的黑色节点个数都是一样.</br>
 * 
 * @ClassName: RedBlackTree
 * @author: wntl
 * @date: 2020年4月16日 下午7:28:19
 */
public class RedBlackTree extends Tree implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 7725062496373252696L;

	Node root;

	AtomicInteger count = new AtomicInteger(0);
	/**
	 * 红色
	 * @Fields RED
	 */
	static final int RED = 0;
	/**
	 * 黑色
	 * @Fields BLACK
	 */
	static final int BLACK = 1;

	Node newRoot(int value){
		return newNode(value, null,  BLACK);
	}
	
	Node newNode(int value, Node parent, int color) {
		return new Node(null, null, parent, color, value);
	}

	@Override
	void add(int value) {
		if (root == null) {
			root = newRoot(value);
		} else {
			Node cur = root;
			Node node = newNode(value, null, RED);
			while(cur != null){
				if (cur.value > value) {
					if(cur.left == null){
						cur.left = node;
						node.parent = cur;
						break;
					} else {
						cur = cur.left;
					}
				} else if (cur.value < value) {
					if (cur.right == null) {
						cur.right = node;
						node.parent = cur;
						break;
					} else {
						cur = cur.right;
					}
				}
			}
			//判断是否破坏了红黑树的结构
			fixAfterInsertion(node);
		}
	}
	
	/**
	 * 红黑树的旋转和变色.</br>
	 * <p>不需要变色及旋转场景: </br>
	 * 1. 当整个结构为空树的时候, 直接将插入节点颜色设置为黑色并且设置为root节点.</br>
	 * 	2. 当值已存在, 不插入(若为key, value形式, 直接替换值)</br>
	 * 	3. 当父节点是黑色的时候, 直接插入.
	 * 	<p>需要变色及旋转场景: </br>
	 * 1. 父节点是红色, 叔叔节点也是红色.</br>
	 * 		处理方法: 将父亲和叔叔节点设为黑色, 祖父节点设为红色, 最后将祖父节点设置为当前插入节点, 继续处理. </br>
	 * 2. 叔叔节点不存在或者为黑色节点时.</br>
	 * 		2.1: 插入节点的父节点是祖父节点的左节点.</br>
	 * 			2.1.1: 插入节点是父节点的左节点:  将父节点设为黑色, 祖父节点设为红色. 以祖父节点为中心进行右旋.</br>
	 * 			2.1.2: 插入节点是父节点的右节点:  以父节点为中心进行左旋, 将父节点设置为插入节点, 进行2.1.1处理.</br>
	 * 		2.2: 插入节点的父节点是祖父节点的右节点.</br>
	 * 			2.2.1: 插入节点是父节点的右节点:  将父节点设置为黑色, 祖父节点设为红色, 以祖父节点为中心进行左旋.</br>
	 * 			2.2.2: 插入节点是父节点的左节点:  以父节点为中心进行右旋, 将父节点设置为插入节点, 进行2.2.1处理.
	 * @param node
	 */
	void fixAfterInsertion(Node node){
		Node cur = node;
		while(cur != null && cur != root && cur.parent.color == RED){
			if (nodeParent(cur) == nodeLeft(nodeParent(nodeParent(cur)))) {
				Node right = nodeRight(nodeParent(nodeParent(cur)));
				//判断叔叔节点是什么颜色
				if(right.color == RED){
					setColor(nodeParent(cur), BLACK);
					setColor(right, BLACK);
					setColor(nodeParent(nodeParent(cur)), RED);
					cur = nodeParent(nodeParent(cur));
				} else {
					if (cur == nodeParent(cur).right) {
						cur = nodeParent(cur);
						leftRotate(cur);
					}
					setColor(nodeParent(cur), BLACK);
					setColor(nodeParent(nodeParent(cur)), RED);
					rightRotate(nodeParent(nodeParent(cur)));
				}
			} else {
				Node left = nodeLeft(nodeParent(nodeParent(cur)));
				if (left.color == RED) {
					setColor(nodeParent(cur), BLACK);
					setColor(left, BLACK);
					setColor(nodeParent(nodeParent(cur)), RED);
					cur = nodeParent(nodeParent(cur));
				} else {
					if (cur == nodeParent(cur).left) {
						cur = nodeParent(cur);
						rightRotate(cur);
					}
					setColor(nodeParent(cur), BLACK);
					setColor(nodeParent(nodeParent(cur)), RED);
					leftRotate(nodeParent(nodeParent(cur)));
				}
			}
		}
		root.color = BLACK;
	}
	
	void rightRotate(Node node){
		if(node != null){
			Node left = node.left;
			if (node.parent == null) {
				root = left;
			} else if (node.parent.left == node){
				node.parent.left = left;
			} else {
				node.parent.right = left;
			}
			if (left.right != null) {
				left.right.parent = node;
				node.left = left.right;
			}
			left.right = node;
			left.parent = node.parent;
			node.parent = left;
		}
	}
	
	void leftRotate(Node node){
		if(node != null){
			Node right = node.right;
			if (node.parent == null) {
				root = node;
			} else if (node.parent.left == node) {
				node.parent.left = right;
			} else {
				node.parent.right = right;
			}
			if(right.left != null){
				right.left.parent = node;
				node.right = right.left;
			}
			right.parent = node.parent;
			node.parent = right;
			right.left = node;
		}
	}
	
	Node nodeParent(Node node){
		return node == null ? null : node.parent;
	}
	
	Node nodeLeft(Node node){
		return node == null ? null : node.left;
	}
	
	Node nodeRight(Node node){
		return node == null ? null : node.right;
	}
	
	void setColor(Node node, int color){
		if (node != null) node.color = color;
	}

	@Override
	void remove(int value) {

	}

	static class Node {
		Node left;
		Node right;
		Node parent;
		int color;
		int value;

		public Node() {}

		public Node(Node left, Node right, Node parent, int color, int value) {
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.color = color;
			this.value = value;
		}

		@Override
		public String toString() {
			return "Node [left=" + (left != null ? left.value : null) + ", right="
					+ (right != null ? right.value : null) + ", parent=" + (parent != null ? parent.value : null)
					+ ", color=" + color + ", value=" + value + "]";
		}
	}
	
	public static void main(String[] args) {
		
	}
}
