package com.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 二叉搜索树(又称二叉排序树,二叉查找树)(Java). 实现二叉搜索树的增删查
 * 定义: 是个二叉树, 性质: 根节点值大于左子树中任意值,小于右子树中任意值. 每一个子树都是二叉搜索树.
 * @ClassName: BinarySearchTree
 * @author: wntl
 * @date: 2020年4月3日 上午11:28:48
 */
public class BinarySearchTree {

	transient Node root;
	
	volatile int count;
	/**
	 * 前序遍历
	 */
	private static final String TRAVERSAL_0 = "PREORDER";
	/**
	 * 中序遍历
	 */
	private static final String TRAVERSAL_1 = "INORDER";
	/**
	 * 后序遍历
	 */
	private static final String TRAVERSAL_2 = "POSTORDER";
	
	public BinarySearchTree() {
	}
	
	/**
	 * 创建节点
	 * @param value
	 * @return
	 */
	public Node newNode(int value){
		return new Node(null, null, null, value);
	}
	
	/**
	 * @param value
	 */
	public void add(int value){
		if(root == null){
			root = newNode(value);
		} else {
			Node node = root;
			Node cur = newNode(value);
			while(node != null){
				if(node.value > value){
					if(node.left != null){
						node = node.left;
					} else {
						node.left = cur;
						cur.parent = node;
						break;
					}
				} else if(node.value < value){
					if(node.right != null){
						node = node.right;
					} else {
						node.right = cur;
						cur.parent = node;
						break;
					}
				}
			}
		}
		count++;
	}
	
	/**
	 * 前序遍历. 中-左-右
	 * @return
	 */
	public List<Integer> preorderTraversal(){
		if(root == null){
			return null;
		}
		return traversal(root, new ArrayList<>(count), TRAVERSAL_0);
	}
	
	/**
	 * 中序遍历
	 * @return
	 */
	public List<Integer> inorderTraversal(){
		if(root == null){
			return null;
		}
		return traversal(root, new ArrayList<>(count), TRAVERSAL_1);
	}
	
	/**
	 * 后序遍历
	 * @return
	 */
	public List<Integer> postorderTraversal(){
		if(root == null){
			return null;
		}
		return traversal(root, new ArrayList<>(count), TRAVERSAL_2);
	}
	
	/**
	 * @param node
	 * @param list
	 * @param order
	 * @return
	 * 
	 */
	public List<Integer> traversal(Node node, List<Integer> list, String order){
		switch (order) {
		case TRAVERSAL_0:
			//首先加入根节点值
			list.add(node.value);
			//加入左节点值
			if(node.left != null){
				list = traversal(node.left, list, TRAVERSAL_0);
			}
			//加入右节点值
			if(node.right != null){
				list = traversal(node.right, list, TRAVERSAL_0);
			}
			break;
		case TRAVERSAL_1:
			//首先加入左节点值
			if(node.left != null){
				list = traversal(node.left, list, TRAVERSAL_1);
			}
			//加入根节点值
			list.add(node.value);
			//加入右节点值
			if(node.right != null){
				list = traversal(node.right, list, TRAVERSAL_1);
			}
			break;
		case TRAVERSAL_2:
			//首先加入左节点值
			if(node.left != null){
				list = traversal(node.left, list, TRAVERSAL_2);
			}
			//加入右节点值
			if(node.right != null){
				list = traversal(node.right, list, TRAVERSAL_2);
			}
			//加入根节点值
			list.add(node.value);
			break;
		default:
			break;
		}
		
		return list;
	}
	
	/**
	 * 删除节点
	 * 删除节点时中序遍历的顺序不变.则需要将中序遍历中大于删除节点的值中的最小值移动至删除节点位置处
	 * 例如: 树中序遍历为: 12345678, 删除5节点, 删除后的中序遍历为: 1234678
	 * 
	 * 思路: 
	 * 删除节点时,存在四种情况:
	 *  1. 删除的节点没有左右节点.  此时需要先判断删除节点处于其父节点的左右, 然后将指向该节点的引用清空,将删除节点的parent属性置空.
	 *  2. 删除的节点存在左节点没有右节点. 此时需要先判断删除节点处于其父节点的左右, 然后将删除节点的下一个节点上移, 修改下一个节点的parent属性和指向他的引用.将删除节点的属性置为空.
	 *  3. 删除的节点存在右节点没有左节点. 此时需要先判断删除节点处于其父节点的左右, 然后将删除节点的下一个节点上移, 修改下一个节点的parent属性和指向他的引用.将删除节点的属性置为空.
	 *  4. 删除的节点既存在左节点也存在右节点时: 
	 *  	4.1 首先从删除节点的右子树中获取大于删除后节点值的最小值.(即上述例子中的6. 注: 该替换一定存在于右子树中的最左边)
	 *  	4.2 判断是哪个节点.(注: 当为root节点时,便不需要此处判断)
	 *  		4.2.1. 当为左节点时: 将删除节点的parent节点的左子树指向替换节点. 替换几点的parent属性指向删除节点的parent.
	 *  		4.2.3. 当为右节点时: 将删除节点的parent节点的右子树指向替换节点. 替换几点的parent属性指向删除节点的parent.
	 *  	4.3. 判断替换节点是否存在子节点, 当存在子节点的时候一定是右子树. 将替换节点的右子树指向替换节点parent的左节点并且parent属性指向替换节点的parent.
	 *  	4.4. 将删除节点的左节点指向替换节点. 并且parent属性指向替换节点.
	 *  	4.5. 当替换节点的parent属性为删除的节点时, 参考2.
	 *  	4.6. 将删除节点的右子树指向替换节点的右子树,并且修改其parent属性.
	 *  	4.7. 将删除节点的左右节点及parent属性置null.
	 * @param value
	 * @return
	 */
	public Node deleteNode(int value){
		if(root == null){
			throw new NullPointerException();
		}
		Node node = findNode(value);
		if(node == null){
			throw new NullPointerException();
		}
		
		if(node.left == null && node.right == null){
			if(node.parent.left == node){
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
			node.parent = null;
		} else if(node.left != null && node.right == null){
			if(node.parent.left == node){
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}
			node.left.parent = node.parent;
			node.left = null;
			node.parent = null;
		} else if(node.left == null && node.right != null){
			if(node.parent.left == node){
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
			node.right.parent = node.parent;
			node.right = null;
			node.parent = null;
		} else {
			//当删除的节点存在左右子树的时候.
			//1.发现该节点整个右子树中最小值的节点
			Node minNode = findRightMin(node.right);
			if(minNode == null){
				throw new NullPointerException();
			}
			if(node != root){
				//2.判断当前删除节点是处于父节点的左边还是右边
				if(node.parent.left == node){
					//2.1当为左边时
					node.parent.left = minNode;
				} else {
					//2.2当删除节点在原有位置中存在于右子树中时
					node.parent.right = minNode;
				}
			}
			//3判断最小值节点的父节点是不是要删除的节点
			if(minNode.parent.value != node.value){
				//3.1否, 替换节点在原父节点只能为右子树中的最左边. 当为右子树的右边时,替换节点的parent为删除的节点.
				//如果替换节点存在右子树时,将改右子树放置替换节点原父节点中的左子树中. parent属性相应修改.
				if(minNode.right != null){
					minNode.parent.left = minNode.right;
					minNode.right.parent = minNode.parent;
				} else {
					minNode.parent.left = null;
				}
				//3.2 将删除节点的右节点挂于替换节点下面
				minNode.right = node.right;
				node.right.parent = minNode;
			}
			//4.修改替换节点的父节点
			minNode.parent = node.parent;
			//5.将删除节点的左右子树引用指向替换节点(最小值节点)
			minNode.left = node.left;
			//6.删除节点左节点parent指向替换节点
			node.left.parent = minNode;
			//7.将删除节点的引用清空,用于GC
			node.parent = null;
			node.left = null;
			node.right = null;
		}
		count--;
		return node;
	}
	
	/**
	 * 寻找节点
	 * @param value
	 * @return
	 */
	public Node findNode(int value){
		Node node = null;
		Node cur = root;
		while(cur != null){
			if(cur.value == value){
				node = cur;
				break;
			} else {
				if(cur.value > value){
					cur = cur.left;
				} else {
					cur = cur.right;
				}
			}
		}
		return node;
	}
	
	public Node findNodeByCondition(int value, int key){
		Node node = null;
		Node cur = root;
		int num = 0;
		while(cur != null){
			
		}
		
		return null;
	}
	
	/**
	 * 获取最小值节点
	 * @param node
	 * @return
	 */
	public Node findRightMin(Node node){
		if(node.left != null){
			node = findRightMin(node.left);
		} 
		return node;
	}
	
	public int getCount(){
		return count;
	}
	
	/**
	 * 定义二叉搜索树Node
	 * @author wntl
	 */
	private class Node{
		Node left;
		Node right;
		Node parent;
		int value;
		
		public Node(Node left, Node right, Node parent, int value) {
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.value = value;
		}
		
		public String toString() {
			return "Node = [ left : " + (left == null ? null : left.value) + ", right : "
					+ (right == null ? null : right.value) + ", parent : " + (parent == null ? null : parent.value)
					+ ", value : " + value + "]";
		}
	}
	
	public static void main(String[] args) {
		BinarySearchTree  tree = new BinarySearchTree();
		tree.add(14);
		tree.add(9);
		tree.add(20);
		tree.add(5);
		tree.add(11);
		tree.add(17);
		tree.add(27);
		tree.add(3);
		tree.add(7);
		tree.add(10);
		tree.add(12);
		tree.add(15);
		tree.add(18);
		tree.add(23);
		tree.add(30);
		tree.add(1);
		tree.add(2);
		tree.add(6);
		tree.add(8);
		tree.add(13);
		tree.add(16);
		tree.add(21);
		tree.add(31);
		tree.add(22);
		List<Integer> list = tree.preorderTraversal();
		System.out.println(toString(list));
		list = tree.inorderTraversal();
		System.out.println(toString(list));
		list = tree.postorderTraversal();
		System.out.println(toString(list));
		int deleteNodeValue = 30;
		System.out.println(tree.findNode(deleteNodeValue).toString());
		System.out.println(tree.getCount());
		System.out.println("=====delete " + deleteNodeValue + "======" + tree.deleteNode(deleteNodeValue).toString());
		System.out.println(tree.getCount());
		list = tree.preorderTraversal();
		System.out.println(toString(list));
		list = tree.inorderTraversal();
		System.out.println(toString(list));
		list = tree.postorderTraversal();
		System.out.println(toString(list));
	}
	
	public static String toString(List<Integer> list){
		StringBuffer buffer = new StringBuffer();
		for(Integer i : list){
			buffer.append(i).append(",");
		}
		return buffer.substring(0, buffer.length() - 1);
	}
}
