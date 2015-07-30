import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;


public class DecisionTree {

	private Node root;
	private int NUMBER_OF_FILES;
	private Messages TRAINING_SET;
	private int T;
	private TreeSet<Node> nodes = new TreeSet<Node>(new MyComp());

	public DecisionTree(int NUMBER_OF_FILES,int T,Messages TRAINING_SET,TreeSet<Node> nodes){
		this.NUMBER_OF_FILES = NUMBER_OF_FILES;
		this.T = T;
		this.TRAINING_SET = TRAINING_SET;
		this.nodes = nodes;

	}

	public DecisionTree(DecisionTree old){
		this.NUMBER_OF_FILES = old.NUMBER_OF_FILES;
		this.T = old.T;
		this.TRAINING_SET = new Messages();
		// We want to deep copy each element in TRAINING_SET
		for (int i = 1; i <= old.TRAINING_SET.getMatrix().size(); i++) {
			this.TRAINING_SET.getMatrix().put(i, new ArrayList<LinkedHashSet<Integer>>());
			for (int j = 0; j < old.TRAINING_SET.getMatrix().get(i).size(); j++)
				this.TRAINING_SET.getMatrix().get(i).add(old.TRAINING_SET.getMatrix().get(i).get(j));
		}
		this.nodes = new TreeSet<Node>(new MyComp());
		// TODO: copy deep nodes
		this.root = new Node(old.root,nodes);
	}

	public DecisionTree start(Dictionary Dict){
		ArrayList <Integer> allDictWords = new ArrayList<Integer>();
		for (int i = 0; i < Dict.getDictionary().size(); i++) {
			allDictWords.add(i);
		}

		root = new Node(allDictWords,TRAINING_SET);
		//root.setLeaf(true);
		getNodes().add(root);
		return routines();
	}
	public void setRoot(Node root){
		this.root = root;
	}

	public Node getRoot(){
		return this.root;
	}

	public DecisionTree routines() {
		for (int k = 0; k < T; k++) {
			Node checkNode = null;
			if (!getNodes().isEmpty())
			{
				checkNode = getNodes().pollLast();
				splitByWord(checkNode);	
			//	System.out.println("spliting ... "+checkNode.getX());
				}
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

		double igL = (LeftNode.getEntropy() - LeftNode.getHx()) * LeftNode.getMessages().sumAllMessages();
		double igR = (RightNode.getEntropy() - RightNode.getHx()) * RightNode.getMessages().sumAllMessages();

		LeftNode.setInformationGain(igL);
		RightNode.setInformationGain(igR);

		checkNode.setLeftChild(LeftNode);
		checkNode.setRightChild(RightNode);


		getNodes().add(new Node(LeftNode,new TreeSet<Node>(new MyComp())));
		getNodes().add(new Node(RightNode,new TreeSet<Node>(new MyComp())));

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

public TreeSet<Node> getNodes() {
	return nodes;
}

public void setNodes(TreeSet<Node> nodes) {
	this.nodes = nodes;
}

public void setTandStart(int t2) {
	this.T = t2;
	this.routines();

}
}

