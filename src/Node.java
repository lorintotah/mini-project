
import java.util.*;



public class Node {
	public Node leftChild;
	public Node rightChild;
	private ArrayList<Pair> messages;  // array list with the numbers of messages in him
	private ArrayList<String> Attributes;
	private double entropy; // h(x)
	private boolean isLeaf; //
	private String name;

	public Node() {
		this.messages = new ArrayList<Pair>();
		Attributes = new ArrayList<String>();
		this.name = "";
		setEntropy(0.0);
		setLeftChild(null);
		setRightChild(null);
		setLeaf(false);
	}

	public Node(ArrayList<String> Att,ArrayList<Pair> msg) {
		this.messages =msg;
		Attributes = Att;
		this.name = "";
		setEntropy(0.0);
		setLeftChild(null);
		setRightChild(null);
		setLeaf(false);
	}

	public int NLa(int attr){
		int countWithAttr = 0;
		for (int i = 0; i<messages.size(); i++){
			if (messages.get(i)._indexes.contains(attr))
				countWithAttr++;
		}
		return countWithAttr;
	}

	public int NiL(int i){
		int count = 0;
		for (int j = 0; j<messages.size(); j++){
			if (messages.get(j)._label == i)
				count++;
		}
		return count;
	}
	
	public int NL(){
		return messages.size();
	}

	public double calculateEntropyForLeaf(){
		double entropy = 0;
		for (int i=1; i < 5; i++){
			entropy += (NiL(i)/NL()) * (Math.log(NL()) - Math.log(NiL(i)) );
		}
		return entropy;

	}


	public double calculateInformationGain(Matrix m){

		return -1;

	}

	public void setData(ArrayList<Pair> data) {
		this.messages = data;
	}

	public ArrayList<Pair> getData() {
		return messages;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public double getEntropy() {
		return entropy;
	}

	public void setLeftChild(Node left) {
		this.leftChild = left;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setRightChild(Node right) {
		this.rightChild = right;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public boolean isLeaf() {
		return isLeaf;
	}


}

