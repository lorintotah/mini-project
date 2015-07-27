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
		this.root = new Node();
		this.NUMBER_OF_FILES = NUMBER_OF_FILES;
		this.T = T;
		this.TRAINING_SET = TRAINING_SET;
		this.nodes = nodes;
	}


	public void setRoot(Node root){
		this.root = root;
	}

	public Node getRoot(){
		return this.root;
	}
	//
	//	this.messages = new Messages();
	//	Attributes = new ArrayList<Integer>();
	//	this.setName(0);
	//	setEntropy(0.0);
	//	setLeftChild(null);
	//	setRightChild(null);
	//	setLeaf(true);
	//	this.setNL(0);
	//	this.x = 0;


	public DecisionTree(DecisionTree tree,int T) {
		this.root = new Node(tree.root);
		copy(this.root , tree.root);
		this.T = T;
		this.NUMBER_OF_FILES = tree.NUMBER_OF_FILES;
		this.TRAINING_SET = tree.TRAINING_SET;
		this.nodes = new TreeSet<Pair>(new MyComp());
		System.out.println(nodes);
		for (Pair n : tree.nodes) {
			System.out.println("A");
			this.nodes.add(new Pair(n));
			System.out.println(nodes);
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



	public DecisionTree start(Dictionary Dict,Messages TRAINING_SET){
		ArrayList <Integer> allDictWords = new ArrayList<Integer>();
		for (int i = 0; i < Dict.getDictionary().size(); i++) {
			allDictWords.add(i);
		}
		root = new Node(allDictWords,TRAINING_SET);
		root.setLeaf(true);
		getNodes().add(new Pair(root,0));
		return routines();

	}

	public DecisionTree routines() {
		for (int k = 0; k < T; k++) {
			Node checkNode = getNodes().last().getNode();
			getNodes().pollLast();
			System.out.println(checkNode);
			splitByWord(checkNode.getX(),checkNode);
		}
		return this;
	}

	private void splitByWord(int index,Node checkNode) {
		Messages messagesWith = new Messages();
		Messages messagesWithOut = new Messages();

		checkNode.setName(index);
		checkNode.setLeaf(false);

		checkNode.NLa(index,messagesWith, messagesWithOut);
		checkNode.getAttr().remove(index);

		Node LeftNode = new Node(checkNode.getAttr(),messagesWith);
		Node RightNode = new Node(checkNode.getAttr(),messagesWithOut);
		double igL = (LeftNode.getEntropy() - LeftNode.getHx()) * LeftNode.getNL();
		double igR = (RightNode.getEntropy() - RightNode.getHx()) * RightNode.getNL();


		checkNode.setLeftChild(LeftNode);
		checkNode.setRightChild(RightNode);

		System.out.println(getNodes().size());
		getNodes().add(new Pair(LeftNode,igL));
		getNodes().add(new Pair(RightNode,igR));
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

