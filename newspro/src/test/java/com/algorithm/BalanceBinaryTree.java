package com.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.mine.aplt.util.CommonUtils;

/**
 * @Description:  平衡二叉树(二叉搜索树的升级版). 左右子树的深度差绝对值不大于1. 其他性质参照二叉搜索树.
 * @ClassName: BalanceBinaryTree
 * @author: wntl
 * @date: 2020年4月8日 下午4:24:16
 */
public class BalanceBinaryTree extends Tree{
	/**
	 * 父节点
	 * @Fields root
	 */
	private Node root;
	/**
	 * 节点数
	 * @Fields count
	 */
	AtomicInteger count = new AtomicInteger(0);
	
	/**
	 * 旋转临界条件
	 * @Fields SPINNING_CONDITIONS
	 */
	static final int SPINNING_CONDITIONS = 1;
	/**
	 * 右旋(从左往右旋转, 被旋转节点成为右孩子)
	 * @Fields ROTATYPE_LL
	 */
	static final String ROTATYPE_LL = "LL";
	/**
	 * 先左旋再右旋(先从右往左,再从左往右)
	 * @Fields ROTATYPE_LR
	 */
	static final String ROTATYPE_LR = "LR";
	/**
	 * 先右旋再左旋(先从左往右,再从右往左)
	 * @Fields ROTATYPE_RL
	 */
	static final String ROTATYPE_RL = "RL";
	/**
	 * 左旋(从右往左)
	 * @Fields ROTATYPE_RR
	 */
	static final String ROTATYPE_RR = "RR";
	/**
	 * 左边
	 * @Fields DIRECTION_LEFT
	 */
	static final String DIRECTION_LEFT = "left";
	/**
	 * 右边
	 * @Fields DIRECTION_RIGHT
	 */
	static final String DIRECTION_RIGHT = "right";
	
	Node newRoot(int value){
		return newNode(null, value, 0);
	}
	
	Node newNode(Node parent, int value, int depth){
		return new Node(null, null, parent, value, depth);
	}
	
	public synchronized void add(int value){
		if(root == null){
			root = newRoot(value);
		} else {
			Node cur = root;
			Node node = newNode(null, value, 0);
			while(cur != null){
				if(cur.value > value){
					if(cur.left != null) {
						cur = cur.left;
					} else {
						node.depth = cur.depth + 1;
						node.parent = cur;
						cur.left = node;
						break;
					}
				} else if(cur.value < value){
					if(cur.right != null) {
						cur = cur.right;
					} else {
						node.depth = cur.depth + 1;
						node.parent = cur;
						cur.right = node;
						break;
					}
				}
			}
			//检查
			Node brokenNode = brokenNodes(node.parent);
			if(brokenNode != null){
				rotatingTreeStructure(existRotatype(brokenNode, value), brokenNode);
			}
		}
		count.incrementAndGet();
	}
	
	/**
	 * 从下往上查询节点模型是否被破坏</br>
	 * 依次检查插入节点的parent节点, 当返回值为null时, 代表结构未被破坏
	 * @param node
	 * @return
	 */
	private Node brokenNodes(Node node){
		if(node == null){
			return null;
		}
		return isNeedRotating(node) ? node : brokenNodes(node.parent);
	}
	
	/**
	 * 判断当前节点是否需要旋转</br>
	 * 中序遍历中数据的value值是有序的, 从小到大排序.</br>
	 * 根节点是在左右子树节点的中间. 按照根节点value值获取在集合中的位置.后将左右节点放入两个不同的数组中,</br>
	 * 获取其中depth最大的节点, 来计算左右子树深度差并且判断是否需要旋转</br>
	 * @param node
	 * @return
	 */
	private boolean isNeedRotating(Node node){
		return Math.abs(getBalanceFactor(node)) > 1;
	}
	
	/**
	 * 获取指定节点的左右子树高度差
	 * @param node
	 * @return
	 */
	private int getBalanceFactor(Node node){
		List<Node> list = new ArrayList<>(count.get());
		traversal(node, list, TRAVERSAL_1);
		int size = list.size() - 1;
		//获取判断节点的位置
		int index = searchIndex(list, node.value);
		//根据判断节点的位置, 将左边的节点放入对应数组中
		Node[] lefts = new Node[index];
		if(lefts.length > 0){
			System.arraycopy(listToArray(list), 0, lefts, 0, index);
		}
		//根据判断节点的位置, 将右边的节点放入对应的数组中
		Node[] rights = new Node[size - index];
		if(rights.length > 0){
			System.arraycopy(listToArray(list), index + 1, rights, 0, size - index);
		}
		//获取左子树中最大深度
		int leftMaxDepth = getMaxDepth(lefts, node);
		//获取左边深度差
		int leftDepth = leftMaxDepth < 0 ? 0 : leftMaxDepth - node.depth;
		//获取右子树中最大深度
		int rightMaxDepth = getMaxDepth(rights, node);
		//获取右边深度差
		int rightDepth = rightMaxDepth <= 0 ? 0 : rightMaxDepth - node.depth;
		//判断左右深度差绝对值是否大于1
		return leftDepth - rightDepth;
	}
	
	/**
	 * 判断当前属于哪种旋转结构</br>
	 * 旋转结构</br>
	 * 破坏平衡时, 插入节点处于被破坏节点: </br>
	 * 		1. 左子树中的左子树中. LL(从左往右旋转)</br>
	 * 		2. 左子树中的右子树中. LR(先将破环节点的下一个左节点内结构从右往左, 再将破环节点整个结构从左往右)</br>
	 *		3. 右子树中的左子树中, RL(先将破环节点的下一个右节点内结构从左往右, 再将破环节点整个结构从右往左)</br>
	 *		4. 右子树中的右子树中. RR(从右往左)</br>
	 * @param node
	 * @param value
	 * @return
	 */
	private String existRotatype(Node brokenNode, int value){
		String rotatype = "";
		//1.判断插入节点是在被破坏的节点的位置.
		if (brokenNode.value > value && brokenNode.left.value > value) {
			rotatype = ROTATYPE_LL;
		} else if (brokenNode.value > value && brokenNode.left.value < value) {
			rotatype = ROTATYPE_LR;
		} else if (brokenNode.value < value && brokenNode.right.value > value) {
			rotatype = ROTATYPE_RL;
		} else if (brokenNode.value < value && brokenNode.right.value < value) {
			rotatype = ROTATYPE_RR;
		}
		return rotatype;
	}
	
	/**
	 * 根据旋转类型旋转树的结构
	 * @param rotatype
	 * @param brokenNode
	 */
	private void rotatingTreeStructure(String rotatype, Node brokenNode){
		System.out.println(rotatype);
		Node rotaNode = null;
		Node brokenNextNode = null;
		switch (rotatype) {
		case ROTATYPE_LL:
			rotaNode = brokenNode.left;
			if(brokenNode == root){
				root = rotaNode;
			}
			LL(brokenNode, rotaNode, true);
			break;
		case ROTATYPE_LR:
			//1.1获取LR中borken节点的左节点
			brokenNextNode = brokenNode.left;
			//1.2获取LR中先左转的旋转节点
			rotaNode = brokenNextNode.right;
			RR(brokenNextNode, rotaNode, true);
			if(brokenNode == root){
				root = rotaNode;
			}
			LL(brokenNode, rotaNode, true);
			break;
		case ROTATYPE_RL:
			brokenNextNode = brokenNode.right;
			rotaNode = brokenNextNode.left;
			LL(brokenNextNode, rotaNode, false);
			if(brokenNode == root){
				root = rotaNode;
			}
			RR(brokenNode, rotaNode, false);
			break;
		case ROTATYPE_RR:
			rotaNode = brokenNode.right;
			if(brokenNode == root){
				root = rotaNode;
			}
			RR(brokenNode, rotaNode, false);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 从左向右旋转
	 * @param brokenNode 被破坏节点(被替换节点)
	 * @param rotaNode 旋转节点(替换节点)
	 * @param direction 左右标志 true-左  false-右
	 */
	private void LL(Node brokenNode, Node rotaNode, boolean direction){
		//调整深度
		brokenNode.depth++;
		if(brokenNode.right != null) incrementDepth(brokenNode.right);
		rotaNode.depth--;
		if(rotaNode.left != null) decrementDepth(rotaNode.left);
		//替换节点
		if(brokenNode.parent != null) {
			if(direction){
				brokenNode.parent.left = rotaNode;
			} else {
				brokenNode.parent.right = rotaNode;
			}
		}
		rotaNode.parent = brokenNode.parent;
		brokenNode.parent = rotaNode;
		brokenNode.left = rotaNode.right;
		if(rotaNode.right != null) rotaNode.right.parent = brokenNode;
		rotaNode.right = brokenNode;
	}
	
	/**
	 * 从右向左旋转
	 * @param brokenNode 被破坏节点(被替换节点)
	 * @param rotaNode 旋转节点(替换节点)
	 * @param direction 左右标志 true-左  false-右
	 */
	private void RR(Node brokenNode, Node rotaNode, boolean direction){
		//处理被破坏节点深度及其左子树的左子树所有节点深度加一
		brokenNode.depth++;
		if(brokenNode.left != null) incrementDepth(brokenNode.left);
		//旋转节点及其右子树所有节点深度减一
		rotaNode.depth--;
		if(rotaNode.right!= null) decrementDepth(rotaNode.right);
		//替换节点. broken节点的左节点(N)和旋转节点替换. 旋转节点替换至N处, 旋转节点左节点放至N节点的右节点处
		if(brokenNode.parent != null) {
			if(direction){
				brokenNode.parent.left = rotaNode;
			} else {
				brokenNode.parent.right = rotaNode;
			}
		}
		rotaNode.parent = brokenNode.parent;
		brokenNode.parent = rotaNode;
		brokenNode.right = rotaNode.left;
		if(rotaNode.left != null) rotaNode.left.parent = brokenNode;
		rotaNode.left = brokenNode;
	}
	
	
	/**
	 * 深度减一
	 * @param node
	 */
	private void decrementDepth(Node node){
		node.depth--;
		if(node.left != null){
			decrementDepth(node.left);
		}
		if(node.right != null){
			decrementDepth(node.right);
		}
	}
	
	/**
	 * 深度加一
	 * @param node
	 */
	private void incrementDepth(Node node){
		node.depth++;
		if(node.left != null){
			incrementDepth(node.left);
		}
		if(node.right != null){
			incrementDepth(node.right);
		}
	}
	
	public Node[] listToArray(List<Node> list){
		int size = list.size();
		Node[] nodes = new Node[size];
		for(int i = 0; i < size; i++){
			nodes[i] = list.get(i);
		}
		return nodes;
	}
	
	/**
	 * 获取数组中深度最大的值
	 * @param nodes
	 * @return
	 */
	public int getMaxDepth(Node[] nodes, Node parent){
		int length = nodes.length;
		Node temp = null;
		for(int i = 0; i < length - 1; i++){
			if(nodes[i].depth >= nodes[ i + 1].depth){
				temp = nodes[i];
				nodes[i] = nodes[ i + 1];
				nodes[ i + 1] = temp;
			}
		}
		return length > 0 ? nodes[length - 1].depth : parent.depth;
	}
	
	/**
	 * 按照值搜索Node节点在list中的位置(二分法查找)
	 * @param list
	 * @param value
	 * @return
	 */
	int searchIndex(List<Node> list, int value){
		int size = list.size();
		int start = 0;
		int end = size - 1;
		int mid = 0;
		while(start <= end){
			mid = (start + end) / 2;
			if (value < list.get(mid).value){
				end = mid - 1;
			} else if (value > list.get(mid).value) {
				start = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
	
	/**
	 * remove node
	 * @param value
	 * @return
	 */
	@Override
	public synchronized void remove(int value){
		if(root == null){
			throw new NullPointerException();
		}
		Node node  = findNodeByValue(value);
		if(node == null){
			throw new NullPointerException();
		}
		//需检查节点, 检查其在删除节点之后 平衡是否被破坏
		Node checkNode = node.parent;
		
		if (node.left == null && node.right == null) {
			if (root == node) {
				root = null;
			} else {
				if(node.parent.left == node){
					node.parent.left = null;
				} else {
					node.parent.right = null;
				}
				node.parent = null;
			}
		} else if (node.left != null && node.right == null) {
			if(root == node){
				root = node.left;
			} else {
				if(node.parent.left == node){
					node.parent.left = node.left;
				} else {
					node.parent.right = node.left;
				}
			}
			node.left.parent = node.parent;
			decrementDepth(node.left);
			node.left = null;
			node.parent = null;
		} else if (node.left == null && node.right != null) {
			if(root == node){
				root = node.right;
			} else {
				if(node.parent.left == node){
					node.parent.left = node.right;
				} else {
					node.parent.right = node.right;
				}
			}
			node.right.parent = node.parent;
			decrementDepth(node.right);
			node.right = null;
			node.parent = null;
		} else {
			//当删除的节点存在左右子树的时候.
			//1.发现该节点整个右子树中最小值的节点
			Node minNode = findRightMin(node.right);
			if(minNode == null){
				throw new NullPointerException();
			}
			//获取最小节点的原parent值
			checkNode = minNode.parent;
			
			if(node != root){
				//2.判断当前删除节点是处于父节点的左边还是右边
				if(node.parent.left == node){
					//2.1当为左边时
					node.parent.left = minNode;
				} else {
					//2.2当删除节点在原有位置中存在于右子树中时
					node.parent.right = minNode;
				}
			} else {
				root = minNode;
			}
			//3判断最小值节点的父节点是不是要删除的节点
			if(minNode.parent.value != node.value){
				//3.1否, 替换节点在原父节点只能为右子树中的最左边. 当为右子树的右边时,替换节点的parent为删除的节点.
				//如果替换节点存在右子树时,将改右子树放置替换节点原父节点中的左子树中. parent属性相应修改.
				if(minNode.right != null){
					minNode.parent.left = minNode.right;
					minNode.right.parent = minNode.parent;
					decrementDepth(minNode.right);
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
			minNode.depth = 0;
			//7.将删除节点的引用清空,用于GC
			node.parent = null;
			node.left = null;
			node.right = null;
		}
		
		// 自上而下查找是否平衡别破坏
		Node n = null;
		while((n = brokenNodes(checkNode)) != null){
			removeRotating(n);
			if (checkNode.parent == null) break;
			checkNode = checkNode.parent;
			if(LOGGER.isTraceEnabled()){
				LOGGER.trace("rotating node ：{}", node.toString());
			}
		}
		
		count.decrementAndGet();
	}
	
	/**
	 * 删除节点后, 判断树结构是否失衡. 找寻失衡的节点并且旋转树结构.</br>
	 * 旋转逻辑:</br>
	 * 一、失衡节点(A)左子树高度高.</br>
	 * 		失衡节点左子树节点(B). B节点失衡因子(BF) = B节点左子树高度 - B节点右子树高度.</br>
	 * 		1. BF = 1 || BF = 0时. 直接右旋(从左向右旋转).</br>
	 * 		2. BF = -1 时, 先左旋在右旋(先从右向左, 再从左向右旋转).</br>
	 * 二、失衡节点(A)右子树高度高.</br>
	 * 		失衡节点右子树节点(C). C节点失衡因子(BF) = C节点左子树高度 - C节点右子树高度.</br>
	 * 		1. BF = 1时, 先右旋再左旋(先左想右, 再从右先左旋转).</br>
	 * 		2. BF = 0 || BF = -1时, 直接左旋(从右向左旋转).</br>
	 * @param node
	 */
	void removeRotating(Node node) {
		if (node != null) {
			int brokenFactor = getBalanceFactor(node);
			int bf = 0;
			String rotatype = "";
			//当左子树高度高时
			if (brokenFactor > 1) {
				bf = getBalanceFactor(node.left);
				if(bf == 0 || bf == 1){
					rotatype = ROTATYPE_LL;
				} else if (bf == -1) {
					rotatype = ROTATYPE_LR;
				}
			} 
			//当右子树高度高时
			else if (brokenFactor < -1) {
				bf = getBalanceFactor(node.right);
				if(bf == 0 || bf == -1){
					rotatype = ROTATYPE_RR;
				} else if (bf == 1) {
					rotatype = ROTATYPE_RL;
				}
			}
			if (CommonUtils.isNotEmpty(rotatype)) rotatingTreeStructure(rotatype, node);
		}
	}
	
	/**
	 * 获取最小值节点
	 * @param node
	 * @return
	 */
	Node findRightMin(Node node){
		if(node.left != null){
			node = findRightMin(node.left);
		}
		return node;
	}
	
	public Node findNodeByValue(int value){
		Node node = null;
		Node cur = root;
		while(cur != null){
			if (cur.value > value) {
				cur = cur.left;
			} else if (cur.value < value) {
				cur = cur.right;
			} else {
				node = cur;
				break;
			}
		}
		return node;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer();
		List<Node> list = new ArrayList<>();
		traversal(root, list, TRAVERSAL_1);
		for(Node node : list){
			str.append(node.toString()).append(",");
		}
		return str.substring(0, str.length() - 1);
	}
	
	public String toStringValue(){
		StringBuffer str = new StringBuffer();
		List<Integer> list = new ArrayList<>();
		traversalValue(root, list, TRAVERSAL_1);
		for(int value : list){
			str.append(value).append(",");
		}
		return str.substring(0, str.length() - 1);
	}
	
	/**
	 * 前序遍历. 中-左-右
	 * @return
	 */
	public List<Integer> preorderTraversal(){
		if(root == null){
			return null;
		}
		return traversalValue(root, new ArrayList<>(count.get()), TRAVERSAL_0);
	}
	
	/**
	 * 中序遍历
	 * @return
	 */
	public List<Integer> inorderTraversal(){
		if(root == null){
			return null;
		}
		return traversalValue(root, new ArrayList<>(count.get()), TRAVERSAL_1);
	}
	
	/**
	 * 后序遍历
	 * @return
	 */
	public List<Integer> postorderTraversal(){
		if(root == null){
			return null;
		}
		return traversalValue(root, new ArrayList<>(count.get()), TRAVERSAL_2);
	}
	
	/**
	 * 二叉树遍历(Node 存储)
	 * @param node
	 * @param list
	 */
	List<Node> traversal(Node node, List<Node> list, String order){
		if(root == null){
			return null;
		}
		switch (order) {
		case TRAVERSAL_0:
			list.add(node); 
			if (node.left != null){
				list = traversal(node.left, list, TRAVERSAL_0);
			}
			if(node.right != null){
				list = traversal(node.right, list, TRAVERSAL_0);
			}
			break;
		case TRAVERSAL_1:
			if(node.left != null){
				list = traversal(node.left, list, TRAVERSAL_1);
			}
			list.add(node);
			if(node.right != null){
				list = traversal(node.right, list, TRAVERSAL_1);
			}
			break;
		case TRAVERSAL_2:
			if(node.left != null){
				list = traversal(node.left, list, TRAVERSAL_2);
			}
			if(node.right != null){
				list = traversal(node.right, list, TRAVERSAL_2);
			}
			list.add(node);
			break;
		}
		return list;
	}
	
	/**
	 * 二叉树遍历(值存储)
	 * @param node
	 * @param list
	 */
	List<Integer> traversalValue(Node node, List<Integer> list, String order){
		switch (order) {
		case TRAVERSAL_0:
			list.add(node.value); 
			if (node.left != null){
				list = traversalValue(node.left, list, TRAVERSAL_0);
			}
			if(node.right != null){
				list = traversalValue(node.right, list, TRAVERSAL_0);
			}
			break;
		case TRAVERSAL_1:
			if(node.left != null){
				list = traversalValue(node.left, list, TRAVERSAL_1);
			}
			list.add(node.value);
			if(node.right != null){
				list = traversalValue(node.right, list, TRAVERSAL_1);
			}
			break;
		case TRAVERSAL_2:
			if(node.left != null){
				list = traversalValue(node.left, list, TRAVERSAL_2);
			}
			if(node.right != null){
				list = traversalValue(node.right, list, TRAVERSAL_2);
			}
			list.add(node.value);
			break;
		}
		return list;
	}
	
	static class Node{
		Node left;
		Node right;
		Node parent;
		int value;
		int depth;
		
		public Node(Node left, Node right, Node parent, int value, int depth){
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.value = value;
			this.depth = depth;
		}
		
		public String toString() {
			return "Node : [value = " + value + ", left = " + (left == null ? null : left.value) + ", right = "
					+ (right == null ? null : right.value) + ", parent = " + (parent == null ? null : parent.value)
					+ ", depth = " + depth + "]";
		}
	}
	
	public static void main(String[] args) {
		BalanceBinaryTree tree = new BalanceBinaryTree();
		tree.add(50);
		tree.add(25);
		tree.add(100);
		tree.add(75);
		tree.add(15);
		tree.add(200);
		tree.add(300);
//		tree.add(180);
//		tree.add(25);
//		tree.add(60);
//		tree.add(15);
//		tree.add(35);
//		tree.add(70);
//		tree.add(9);
//		tree.add(20);
//		tree.add(30);
//		tree.add(41);
//		tree.add(55);
//		tree.add(45);
//		tree.add(48);
//		tree.add(49);
//		tree.add(300);
//		tree.add(400);
//		tree.add(80);
//		tree.add(95);
//		tree.add(99);
		tree.remove(50);
		System.out.println(tree.toStringValue());
		System.out.println(tree.toString());
//		Node node = tree.brokenNodes(tree.findNodeByValue(10).parent);
//		System.out.println(node == null ? "null" : node.toString());
//		List<Integer> list = new ArrayList<>();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		list.add(4);
//		list.add(5);
//		list.add(6);
//		list.add(8);
//		int index = search(list, 3);
//		System.out.println(index);
//		int size = list.size() - 1;
//		Object[] lefts = new Object[index];
//		System.arraycopy(list.toArray(), 0, lefts, 0, index);
//		System.out.println(Arrays.toString(lefts));
//		System.out.println(Arrays.toString(list.toArray()));
//		Object[] rights = new Object[size - index];
//		System.arraycopy(list.toArray(), index + 1, rights, 0, size - index);
//		System.out.println(Arrays.toString(rights));
//		System.out.println(Arrays.toString(list.toArray()));
//		int[] a = new int[]{1,3,10,5,4,9,8,7,6};
//		int length = a.length;
//		int temp = 0;
//		for(int i = 0; i < length - 1; i++){
//			if(a[i] >= a[ i + 1]){
//				temp = a[i];
//				a[i] = a[ i + 1];
//				a[ i + 1] = temp;
//			}
//		}
//		System.out.println(a[length - 1]);
//		System.out.println(muti(4));
	}
	
	public static int muti(int value){
		if(value <= 0){
			return 0;
		} else if(value == 1){
			return value;
		} else {
			return value * muti(value - 1);
		}
	}
	
	public static int search(List<Integer> list, int value){
		int size = list.size();
		int start = 0;
		int end = size - 1;
		int mid = 0;
		while(start <= end){
			mid = (start + end) / 2;
			if(value < list.get(mid)){
				end = mid - 1;
			} else if (value > list.get(mid)) {
				start = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
}
