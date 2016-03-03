import java.util.ArrayList;
import java.util.Iterator;




public class TernaryTree<T> implements TernaryTreeInterface<T>
{
	private int numberOfNodes;
	private int height;
	Node rootNode;
	private  boolean isEmpty = false;
	private int treeIndex;
	private TernaryTreeInterface<T> lTree;
	private TernaryTreeInterface<T> mTree;
	private TernaryTreeInterface<T> rTree;
	private int nodeNum = 0;
	
	
	/**
	 * Initialize empty tree
	 */
	public TernaryTree()
	{
		numberOfNodes = 0;
		height = 0;
		rootNode = null;
		isEmpty = true;
		treeIndex = 0;
	}
	
	/**
	 * Initializes a tree whose root node contains rootData
	 * @param root
	 */
	public TernaryTree(Node root)
	{
		numberOfNodes = 1;
		height = 0;
		rootNode = root;
		isEmpty = false;
		treeIndex = 1;
	}
	
	/**
	 * Initializes a tree whose root node contains rootData and whose child subtrees are leftTree, middleTree, and rightTree
	 * @param rootData data of our new rootNode
	 * @param leftTree our LeftTree
	 * @param middleTree our MiddleTree
	 * @param rightTree our rightTree
	 */
	public TernaryTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree) {
		this.rootNode = new Node(rootData);
		this.lTree = leftTree;
		this.mTree = middleTree;
		this.rTree = rightTree;
	}

	public boolean addNode(T nodeData, Node currentNode, int currentHeight, int nodesOnLevel)
	{
		boolean added = false;
		int data = (int) nodeData;

		
		if(rootNode != null)
		{
			nodesOnLevel = (nodesOnLevel + 1)*3;
			numberOfNodes++;
		}
		
		if(rootNode == null) // This is when the root node is added
		{
			rootNode = new Node(nodeData);
			height++;
			nodeNum++;
			return true;
		}
		
		if(currentHeight > height) return false;
		
		if(!currentNode.hasLeftChild)
		{
			Node newNode = new Node(nodeData);
			currentNode.setLeftChild(newNode);
			if(currentHeight == 1) height++;  // If in root node
			numberOfNodes++;
			treeIndex++;
			nodeNum++;
			return true;
		}
		else if(!currentNode.hasMiddleChild)
		{
			Node newNode = new Node(nodeData);
			currentNode.setMiddleChild(newNode);
			numberOfNodes++;
			treeIndex++;
			nodeNum++;
			return true;
		}
		else if(!currentNode.hasRightChild) 
		{
			Node newNode = new Node(nodeData);
			currentNode.setRightChild(newNode);
			numberOfNodes++;
			treeIndex++;
			nodeNum++;
			return true;
		}
		else
		{
			if((currentHeight != height) && (treeIndex >= nodesOnLevel))
			{
				currentHeight++;
				added = addNode(nodeData, currentNode.getLeftChild(), currentHeight, nodesOnLevel);
				currentHeight--;
				
				if(added == true) return true;
				
				currentHeight++;
				added = addNode(nodeData, currentNode.getMiddleChild(), currentHeight, nodesOnLevel);
				currentHeight--;
				
				if(added == true) return true;
				
				currentHeight++;
				added = addNode(nodeData, currentNode.getRightChild(), currentHeight,nodesOnLevel);
				currentHeight--;
				
				if(added == true) return true;	
			
				return false;
			}
			else
			{
				if(treeIndex != nodesOnLevel) return false;
				height++;
				return addNode(nodeData, rootNode, 0, 1);
			}
		}
		
		
	}

	@Override
	public T getRootData() 
	{
		return (T) rootNode.getData();
	}

	@Override
	public int getHeight() 
	{
		return height;
	}

	@Override
	public int getNumberOfNodes() 
	{
		return nodeNum;
	}

	@Override
	public boolean isEmpty() 
	{
		return rootNode == null;
	}

	@Override
	public void clear() 
	{
		rootNode = null;
		
	}

	@Override
	/**
	* Creates an iterator to traverse the tree in preorder fashion
	* @return the iterator
	*/
	public Iterator<T> getPreorderIterator() {
		// TODO Auto-generated method stub
		return new PreOrderIterator<T>();
		
	}
	
	private class PreOrderIterator<T> implements Iterator<T>{
		private Node curNode;
		private Node storedPosition;
		private boolean hasNextItem = false;
		Node parentNode = curNode;
		Node childNode = null;
		

		
		private PreOrderIterator() {
			this.curNode = getRootNode();
		}
		
		public boolean hasNext() {
			return curNode != null;
		}
		

		public T next(){
			if (getRootNode() == null) return null;
			
			
			if (curNode.visited == false) {
				curNode = curNode;
				curNode.visited = true;
			} else if (curNode.hasLeftChild && curNode.leftChild.visited == false) {
				curNode = curNode.getLeftChild();
				curNode.visited = true;
			} else if (curNode.parent.leftChild == curNode) {
				curNode = curNode.parent.middleChild;
				while(curNode.leftChild != null && curNode.leftChild.visited == false) {
					curNode = curNode.leftChild;
				}
				curNode.visited = true;
			} else if (curNode.parent.middleChild == curNode) {
				curNode = curNode.parent.rightChild;
				while(curNode.leftChild != null && curNode.leftChild.visited == false) {
					curNode = curNode.leftChild;
				}
				curNode.visited = true;
			} else {
				if (curNode.parent.parent.middleChild != null && 
						curNode.parent.parent.middleChild.visited == false) {
					curNode = curNode.parent.parent.middleChild; 
				} else if (curNode.parent.parent.rightChild != null && 
						curNode.parent.parent.rightChild.visited == false) {
					curNode = curNode.parent.parent.rightChild;
				} else {
					curNode = null;
				}
				curNode.visited = true;
				
			}

			return (T)curNode.data;
			
			}
		}
		
	

	@Override
	public Iterator<T> getPostorderIterator() {
		// TODO Auto-generated method stub
		return new PostOrderIterator<T>();
	}
	
	private class PostOrderIterator<T> implements Iterator<T>{
		private Node curNode;
		private Node storedPosition;
		private Node parent;
		private boolean hasNextItem = false;
		Node parentNode = curNode;
		Node childNode = null;
		ArrayList<Node> stack;


		private PostOrderIterator() {
			this.curNode = getRootNode();
			stack = new ArrayList<Node>();
			stack.add(rootNode);
		}
		
		public boolean hasNext() {
			return !stack.isEmpty();
		}
		
   
	
		public T next(){
			if (curNode == null) return null;
			
			if (curNode.leftChild != null && curNode.leftChild.visited == false) {
				while (curNode.hasLeftChild) {
					curNode = curNode.leftChild;
				} // end while
				curNode.visited = true;
			} else if (curNode.middleChild != null && curNode.middleChild.visited == false) {
				curNode = curNode.middleChild;
				curNode.visited = true;
			} else if (curNode.rightChild != null && curNode.middleChild.visited == false) {
				curNode = curNode.rightChild;
				curNode.visited = true;
			} else if (curNode.parent.leftChild == curNode) {
				curNode = curNode.parent.middleChild;
				while(curNode.leftChild != null && curNode.leftChild.visited == false) {
					curNode = curNode.leftChild;
				}
				curNode.visited = true;
			} else if (curNode.parent.middleChild == curNode) {
				curNode = curNode.parent.rightChild;
				while(curNode.leftChild != null && curNode.leftChild.visited == false) {
					curNode = curNode.leftChild;
				}
				curNode.visited = true;
			} else {
				curNode = curNode.parent;
				curNode.visited = true;
			}
	
			return (T)curNode.data;
			}
		}

	@Override
	/**
	 * Furthermore, you do not need to implement getInorderIterator. Instead, this
     * method should also throw a java.lang.UnsupportedOperationException. Instead, include in
     * the comments for this method a short description of why TernaryTree does not support
     * inorder traversal. .... because where should the root be? 
	 */
	public Iterator<T> getInorderIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> getLevelOrderIterator() {
		// TODO Auto-generated method stub
		
		
		return  new LevelOrderIterator<T>();
	}

	private class LevelOrderIterator<T> implements Iterator<T>{
		private Node curNode;
		private Node storedPosition;
		private boolean hasNextItem = false;
		Node parentNode = curNode;
		Node childNode = null;
		private Node visitedNode;
		private int iteratorHeight = 0;
		private int numIters = 0;
		Node returnNode;
		ArrayList<Node> que = new ArrayList<Node>();
		
		//Queue<Node> que = new LinkedList<Node>();
		private LevelOrderIterator() {
			this.curNode = getRootNode();
			que.add(curNode);
		}
		
		public boolean hasNext() {
			return curNode != null;
		}
		public T next() {
			if (getRootNode() == null) return null;
			
			returnNode = que.remove(0);
			
				if(returnNode.hasLeftChild) {
					que.add(returnNode.leftChild);
				}
				
				if (returnNode.hasMiddleChild()) {
					que.add(returnNode.middleChild);
				}
				
				if (returnNode.hasRightChild) {
					que.add(returnNode.rightChild);
				}
				
			return (T)returnNode.data;
			
		}
		

	}

	@Override
	public void setTree(T rootData) 
	{
		rootNode = (Node) rootData;
		
	}

	@Override
	public void setTree(T rootData, TernaryTreeInterface<T> leftTree,
			TernaryTreeInterface<T> middleTree,
			TernaryTreeInterface<T> rightTree)
	{
		
		// code here
	}
	
	public Node getRootNode() {
		return rootNode;
	}
	
	private class Node<T>
	{
		private T data;
		private Node parent;
		private Node leftChild;
		private Node middleChild;
		private Node rightChild;
		private boolean hasLeftChild;
		private boolean hasMiddleChild;
		private boolean hasRightChild;
		private boolean isFull;
		private boolean visited;
		private int nodeNum;
		
		public Node()
		{
			data = null;
			parent = null;
			leftChild = null;
			middleChild = null;
			rightChild = null;
			isFull = false;
		}
		
		public Node(T nodeData)
		{
			data = nodeData;
			parent = null;
			leftChild = null;
			middleChild = null;
			rightChild = null;
			isFull = false;
		}

		public T getData()
		{
			return data;
		}

		public void setData(T data) 
		{
			this.data = data;
		}

		public Node getLeftChild() 
		{
			return leftChild;
		}

		public void setLeftChild(Node leftChild)
		{
			this.leftChild = leftChild;
			this.leftChild.parent = this;
			hasLeftChild = true;
		}

		public Node getMiddleChild()
		{
			return middleChild;
		}

		public void setMiddleChild(Node middleChild)
		{
			this.middleChild = middleChild;
			this.middleChild.parent = this;
			hasMiddleChild = true;
		}

		public Node getRightChild()
		{
			return rightChild;
		}

		public void setRightChild(Node rightChild)
		{
			this.rightChild = rightChild;
			this.rightChild.parent = this;
			hasRightChild = true;
			isFull = true;
		}

		public boolean HasLeftChild() {
			return hasLeftChild;
		}

		public void setHasLeftChild(boolean hasLeftChild) {
			this.hasLeftChild = hasLeftChild;
		}

		public boolean hasMiddleChild() {
			return hasMiddleChild;
		}

		public void setHasMiddleChild(boolean hasMiddleChild) {
			this.hasMiddleChild = hasMiddleChild;
		}

		public boolean hasRightChild() {
			return hasRightChild;
		}

		public void setHasRightChild(boolean hasRightChild) {
			this.hasRightChild = hasRightChild;
		}
		
		public Node getParent() {
			return parent;
		}

		public boolean isFull() {
			return isFull;
		}

		public void setFull(boolean isFull) {
			this.isFull = isFull;
		}
	}

}
