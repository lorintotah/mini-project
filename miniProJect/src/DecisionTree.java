import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;


public class DecisionTree {

	private Node root;
	private int NUMBER_OF_FILES;
	private Messages TRAINING_SET;
	private int T;
	private TreeSet<Pair> nodes = new TreeSet<Pair>();

	public DecisionTree(int NUMBER_OF_FILES,int T,Messages TRAINING_SET,TreeSet<Pair> nodes){
		this.NUMBER_OF_FILES = NUMBER_OF_FILES;
		this.T = T;
		this.TRAINING_SET = TRAINING_SET;
		this.nodes = nodes;
		
	}

	public DecisionTree start(Dictionary Dict){

		ArrayList <Integer> allDictWords = new ArrayList<Integer>();
		for (int i = 0; i < Dict.getDictionary().size(); i++) {
			allDictWords.add(i);
		}
		
		root = new Node(allDictWords,TRAINING_SET);
		root.setLeaf(true);
		getNodes().add(new Pair(root,0));
		return routines();
	}
	public void setRoot(Node root){
		this.root = root;
	}

	public Node getRoot(){
		return this.root;
	}


	public DecisionTree(DecisionTree tree,int T) {
		this.root = new Node(tree.root);
		copy(this.root , tree.root);
		this.T = T;
		this.NUMBER_OF_FILES = tree.NUMBER_OF_FILES;
		this.TRAINING_SET = tree.TRAINING_SET;
		this.nodes = new TreeSet<Pair>(new MyComp());
		for (Pair n : tree.nodes) {
		//	System.out.println("A");
			this.nodes.add(new Pair(n));
			//System.out.println(nodes);
		}
	}

	public void copy (Node dest,Node source)
	{
	  if(source.getLeftChild() != null)
	  {
	     dest.setLeftChild(new Node(source.leftChild));
	     copy(source.leftChild, dest.leftChild );
	  }
	  if(source.getRightChild() != null)
	  {
	     dest.setRightChild(new Node(source.rightChild));
	     copy(source.rightChild, dest.rightChild );
	  }
	}

	public DecisionTree routines() {
		for (int k = 0; k < T; k++) {
			Node checkNode = getNodes().pollLast().getNode();
			System.out.println(checkNode.getX() + " The choosen node");
			splitByWord(checkNode);
		}
		return this;
	}

	private void splitByWord(Node checkNode) {
		Messages messagesWith = new Messages();
		Messages messagesWithOut = new Messages();


		checkNode.setLeaf(false);
		checkNode.NLa(checkNode.getX(),messagesWith, messagesWithOut);
		
		ArrayList<Integer> AttrWithOutX = new ArrayList<Integer>();
		for (Integer i : checkNode.getAttr()){
			if (i != checkNode.getX())
				AttrWithOutX.add(i);
		}
		
		Node LeftNode = new Node(AttrWithOutX,messagesWithOut);
		Node RightNode = new Node(AttrWithOutX,messagesWith);

		double igL = (LeftNode.getEntropy() - LeftNode.getHx()) * LeftNode.getNL();
		double igR = (RightNode.getEntropy() - RightNode.getHx()) * RightNode.getNL();


		checkNode.setLeftChild(LeftNode);
		checkNode.setRightChild(RightNode);

		//System.out.println(getNodes().size());
		getNodes().add(new Pair(LeftNode,igL));
		getNodes().add(new Pair(RightNode,igR));
		System.out.println("Infor gain Left " +igL);

		System.out.println("Infor gain Right " +igR);
	}

	public int checkWhichForum(LinkedHashSet<Integer> sentence){
		Node checkIt = root;
		while (checkIt.isLeaf() == false)
			if ( sentence.contains(checkIt.getX()) )
				checkIt = checkIt.rightChild;
			else
				checkIt = checkIt.leftChild;

		checkIt.chooseForum();
		return checkIt.getName();

	}

	public TreeSet<Pair> getNodes() {
		return nodes;
	}

	public void setNodes(TreeSet<Pair> nodes) {
		this.nodes = nodes;
	}

}

