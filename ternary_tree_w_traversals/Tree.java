
public class Tree<T> implements TreeInterface<T> 
{
	private int numberOfNodes;
	private int height;
	private Node rootNode;
	private  boolean isEmpty;
	private int treeIndex;
	private int nodeNum;
	
	public Tree()
	{
		numberOfNodes = 0;
		height = 0;
		rootNode = null;
		isEmpty = true;
		treeIndex =0;
	}
	
	public Tree(Node root)
	{
		numberOfNodes = 1;
		height = 0;
		rootNode = root;
		isEmpty = false;
		treeIndex = 1;
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
		isEmpty = true;
	}
	
	public Node getRootNode() {
		return rootNode;
	}

	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
	}

	private class Node<T>
	{
		private T data;
		private Node leftChild;
		private Node middleChild;
		private Node rightChild;
		private boolean hasLeftChild;
		private boolean hasMiddleChild;
		private boolean hasRightChild;
		private boolean isFull;
		
		public Node()
		{
			data = null;
			leftChild = null;
			middleChild = null;
			rightChild = null;
			isFull = false;
		}
		
		public Node(T nodeData)
		{
			data = nodeData;
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
			hasLeftChild = true;
		}

		public Node getMiddleChild()
		{
			return middleChild;
		}

		public void setMiddleChild(Node middleChild)
		{
			this.middleChild = middleChild;
			hasMiddleChild = true;
		}

		public Node getRightChild()
		{
			return rightChild;
		}

		public void setRightChild(Node rightChild)
		{
			this.rightChild = rightChild;
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

		public boolean isFull() {
			return isFull;
		}

		public void setFull(boolean isFull) {
			this.isFull = isFull;
		}
	}
}
