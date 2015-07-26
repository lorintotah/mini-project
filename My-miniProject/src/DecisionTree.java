import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class DecisionTree {

	private Node root;
	private int NUMBER_OF_FILES;
	private Messages TRAINING_SET;
	private int T;
	private TreeSet<Pair> nodes;

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

	public DecisionTree()
	{
		
	}
	public void deepCopy(DecisionTree old)
	{
			
	        Node left = root.getLeftChild();
	        Node right = root.getRightChild();
	       
	        if (left != null)
	        {
	            copy.setLeftChild(new Node(left));
	            deepCopy(left, copy.getLeftChild());
	        }
	       
	        if (right != null)
	        {
	            copy.setRightChild(new Node(right));
	            deepCopy(right, copy.getRightChild());
	        }
	}
	
	
	public DecisionTree(DecisionTree tree,int T) {
		root = new Node(tree.root.getAttr(),tree.root.getMessages());
		Node tmp = root;
		Node tmp2 = tree.root;
		while (!tmp.isLeaf()){

			tmp.setLeftChild(tmp2.leftChild);
			tmp.setRightChild(tmp2.getRightChild());

		}
		this.root = tree.root;
		this.NUMBER_OF_FILES = tree.NUMBER_OF_FILES;
		this.T = T;
		this.TRAINING_SET = tree.TRAINING_SET;
		this.nodes = tree.nodes;
	}


	public DecisionTree start(Dictionary Dict,Messages TRAINING_SET){
		ArrayList <Integer> allDictWords = new ArrayList<Integer>();
		for (int i = 0; i < Dict.getDictionary().size(); i++) {
			allDictWords.add(i);
		}
		root = new Node(allDictWords,TRAINING_SET);
		root.setLeaf(true);
		nodes.add(new Pair(root,0));

		return routines();

	}

	public DecisionTree routines() {
		for (int k = 0; k < T; k++) {
			Node checkNode = nodes.last().getNode();
			nodes.pollLast();
			System.out.println(checkNode);
			splitByWord(checkNode.getIndex(),checkNode);
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

		System.out.println(nodes.size());
		nodes.add(new Pair(LeftNode,igL));
		nodes.add(new Pair(RightNode,igR));
	}

	public int checkWhichForum(LinkedHashSet<Integer> sentence){
		Node checkIt = root;
		while (checkIt.isLeaf() == false)
			if ( sentence.contains(checkIt.getName()) )
				checkIt = checkIt.rightChild;
			else
				checkIt = checkIt.leftChild;

		checkIt.chooseForum();
		return checkIt.getName();

	}

}

