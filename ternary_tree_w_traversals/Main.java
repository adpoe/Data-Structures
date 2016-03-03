import java.util.Iterator;



public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		TernaryTree emptyTree = new TernaryTree();
		System.out.println("#################################");
		System.out.println("#######INITIATING TESTS##########");
		System.out.println("#################################");
		System.out.println("\n");
		
		TernaryTree myTree = new TernaryTree();
		for (int i = 0; i<13; i++) {
			myTree.addNode(i, myTree.rootNode, 1, 0);
		}
		
		System.out.println("########POST-ORDER ITERATOR##########");
		System.out.println("Expected order: 4,5,6,1,7,8,9,10,11,12,3,0");
		System.out.println("\n");
		TernaryTree postOrderTree = new TernaryTree();
		for (int i=0;  i<13; i++) {
			postOrderTree.addNode(i, postOrderTree.rootNode, 1, 0);
		}
		
		Iterator postIter = postOrderTree.getPostorderIterator();
		for (int i=0; i<13; i++){
			System.out.println("Tree Item "+ i+":      "+  postIter.next());
		}
		System.out.println("#################################");
		System.out.println("\n");
		System.out.println("########PRE-ORDER ITERATOR##########");
		System.out.println("Expected order: 0,1,4,5,2,7,8,9,3,10,11,12");
		System.out.println("\n");
		TernaryTree preOrderTree = new TernaryTree();
		for (int i=0;  i<13; i++) {
			preOrderTree.addNode(i, preOrderTree.rootNode, 1, 0);
		}
		
		Iterator preIter = preOrderTree.getPreorderIterator();
		for (int i=0; i<13; i++){
			System.out.println("Tree Item "+ i+":      "+  preIter.next() );
		}
		System.out.println("#################################");	
		
		System.out.println("\n");
		System.out.println("########LEVEL-ORDER ITERATOR##########");
		System.out.println("Expected order: 0,1,2,3,4,5,6,7,8,9,10,11,12");
		System.out.println("\n");
		TernaryTree levelOrderTree = new TernaryTree();
		for (int i=0;  i<13; i++) {
			levelOrderTree.addNode(i, levelOrderTree.rootNode, 1, 0);
		}
	
		Iterator levelIter = levelOrderTree.getLevelOrderIterator();
		for (int i=0; i<13; i++){
			System.out.println("Tree Item "+ i+":      "+  levelIter.next() );
		}
		System.out.println("#################################");	
		System.out.println("########IN-ORDER ITERATOR##########");
		System.out.println("Expected order: error message");
		
		TernaryTree inOrderTree = new TernaryTree();
		for (int i=0;  i<13; i++) {
			inOrderTree.addNode(i, inOrderTree.rootNode, 1, 0);
		}
		Iterator inOrderIter = inOrderTree.getInorderIterator();

		System.out.println("#################################");	
		System.out.println("\n");
	
		System.out.println("#######TREE OPERATION INTERFACE - TESTS##########");
		System.out.println("--------------------------");
		System.out.println("Testing:: Tree.getRootData(). Should equal zero");
		System.out.println("Get root data =  " + myTree.getRootData());
		System.out.println("--------------------------");
		System.out.println("\n");
		System.out.println("--------------------------");
		System.out.println("Testing:: Tree.getHeight(). Should equal 2");
		System.out.println("Get height =  " + myTree.getHeight());
		System.out.println("--------------------------");
		System.out.println("\n");
		System.out.println("--------------------------");
		System.out.println("Testing:: Tree.getNumberOfNodes(). Should equal 13");
		System.out.println("Get numberOfNodes =  " + myTree.getNumberOfNodes());
		System.out.println("--------------------------");
		System.out.println("\n");
		System.out.println("--------------------------");
		System.out.println("Testing:: Tree.isEmpty(). Should equal false");
		System.out.println("isEmpty? =  " + myTree.isEmpty());
		System.out.println("\n");
		System.out.println("Testing:: Tree.isEmpty(). Should equal true");
		System.out.println("isEmpty? =  " + emptyTree.isEmpty());
		System.out.println("--------------------------");
		System.out.println("Testing:: Tree.clear(). Calling isEmpty on full tree, and confirming it is successful.");
							myTree.clear();
		System.out.println("Has clear worked?? =  " + myTree.isEmpty());
		System.out.println("\n");
		System.out.println("--------------------------");
		
	
		System.exit(0);
	
	
	}
		
	
	
}
