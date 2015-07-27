import java.util.*;



public class Node {
	public Node leftChild;
	public Node rightChild;
	private HashMap<Integer,ArrayList<LinkedHashSet<Integer>>> messages;  // array list with the numbers of messages in him
	private ArrayList<Integer> Attributes;
	private double entropy; // h(x)
	private boolean isLeaf; //
	private String name;

	public Node() {
		this.messages = new HashMap<Integer,ArrayList<LinkedHashSet<Integer>>> ();
		Attributes = new ArrayList<Integer>();
		this.name = "";
		setEntropy(0.0);
		setLeftChild(null);
		setRightChild(null);
		setLeaf(false);
	}

	public Node(ArrayList<Integer> Att,HashMap<Integer,ArrayList<LinkedHashSet<Integer>>> msg) {
		this.messages =msg;
		Attributes = Att;
		this.name = "";
		setEntropy(0.0);
		setLeftChild(null);
		setRightChild(null);
		setLeaf(false);
	}

	public void SplitMessages(int attr,ArrayList<Pair> messagesWith,ArrayList<Pair> messagesWithOut){
		for (int i = 0; i<messages.size(); i++){
			if (messages.get(i)._indexes.contains(attr))
				messagesWith.add(messages.get(i));
			else
				messagesWithOut.add(messages.get(i));
		}
	}

	public void REGA(int attr){
		ArrayList<Pair> messagesWith = new ArrayList<Pair>();
		ArrayList<Pair> messagesWithOut = new ArrayList<Pair>();
		SplitMessages(attr,messagesWith,messagesWithOut);
		
		// Calculate Ni(L) for each i
		int NiL = 0;
		for (int i = 0; i < messagesWith.size(); i++) {
			messagesWith.get(i).
		}
		
	}
	public int NiL(int i){
		int count = 0;
		for (int j = 0; j<messages.size(); j++){
			if (messages.get(j)._label == i)
				count++;
		}
		return count;
	}

	public int NiLa(int i, int attr){
		int count = 0;
		for (int j = 0; j<messages.size(); j++ ){
			if (messages.get(j)._indexes.contains(attr)){
				if (messages.get(j)._label == i)
					count++;
			}
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
	public double calculateEntropyForNode(int attr){	
		double entropy = 0;
		int nla = this.NLa(attr);
		int nlb = this.NL() - nla;
		int hla = 0;
		for (int i=1; i<5; i++){
			
		}
		
		entropy = ((nla/this.NL())* rightChild.calculateEntropyForLeaf())/
				((nlb/this.NL())* leftChild.calculateEntropyForLeaf());
		return entropy;
	}

	public double calculateInformationGain(int attr){

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