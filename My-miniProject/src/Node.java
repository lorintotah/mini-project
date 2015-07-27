
import java.util.*;



public class Node {
	public Node leftChild;
	public Node rightChild;
	private Messages messages;  // array list with the numbers of messages in him
	private ArrayList<Integer> Attributes;
	private double entropy; // h(x)
	private boolean isLeaf; //
	private int name;
	private int NL;
	private int x;
	private double Hx;

	public Node() {
		this.messages = new Messages();
		Attributes = new ArrayList<Integer>();
		this.setName(0);
		setEntropy(0.0);
		setLeftChild(null);
		setRightChild(null);
		setLeaf(true);
		this.setNL(0);
		this.x = 0;
	}




	public ArrayList<Integer> getAttr(){
		return Attributes;
	}


	public void chooseForum(){
		int count = 0;
		int index = 0;

		for (int i = 1; i <= this.messages.size(); i++) {
			if (messages.getMatrix().get(i).size()>count)
			{
				index = i;
				count  = messages.getMatrix().get(i).size();
			}
		}

		this.setName(index);
	}
	public Messages getMessages(){
		return messages;
	}

	public Node(ArrayList<Integer> Att,Messages msg) {
		this.messages =msg;
		Attributes = Att;
		this.setName(0);
		setLeftChild(null);
		setRightChild(null);
		setLeaf(true);
		setEntropy(calculateEntropyForLeaf());
		this.setNL(msg.getMatrix().size());
		calculateWhichXIsBest();
	}


	public Node(Node oldNode) {
		this.messages = new Messages();
		for (int i = 1; i <= oldNode.messages.getMatrix().size(); i++) {
			this.messages.getMatrix().put(i, new ArrayList<LinkedHashSet<Integer>>());
			for (int j = 0; j < oldNode.messages.getMatrix().get(i).size(); j++) {
				this.messages.getMatrix().get(i).add(oldNode.messages.getMatrix().get(i).get(j));
			}
		}

		this.Attributes = new ArrayList<Integer>();
		for (int i = 0; i < oldNode.Attributes.size(); i++) {
			this.Attributes.add(oldNode.Attributes.get(i));
		}
		this.name = oldNode.name;
		this.entropy = oldNode.entropy;
		setLeftChild(null);
		setRightChild(null);
		this.isLeaf = oldNode.isLeaf;
		this.NL = oldNode.NL;
		this.x = oldNode.x;
		}
	

	private void calculateWhichXIsBest() {
		int index = 0;
		ArrayList<Double> HxArray = new ArrayList<Double>();
		for (int i = 0; i < this.getAttr().size(); i++) {
			Messages messagesWith = new Messages();
			Messages messagesWithOut = new Messages();
			this.NLa(i,messagesWith, messagesWithOut);
			double HLa = 0; //with
			double HLb = 0; //without

			double NiWith = 0;
			double NiWithOut = 0;
			// CALCULATE H(La) - messagesWith

			for (int j = 1; j <= messagesWith.getMatrix().size(); j++) {
				NiWith = messagesWith.getMatrix().get(j).size();
				if (NiWith != 0 )
					HLa +=  (NiWith / this.getNL()) * (Math.log(this.getNL()) - Math.log(NiWith));
			}
			// CALCULATE H(Lb) - messagesWith
			for (int j = 1; j <= messagesWithOut.getMatrix().size(); j++) {
				NiWithOut = messagesWithOut.getMatrix().get(j).size();
				if (NiWithOut != 0)
					HLb += (NiWithOut / this.getNL()) * (Math.log(this.getNL()) - Math.log(NiWithOut));
			}


			double Hx = 0;
			Hx = (NiWith / this.getNL()) * HLa + (NiWithOut / this.getNL() ) * HLb;	
			HxArray.add(Hx);


		}
		double max = 0;
		for (int j = 0; j < HxArray.size(); j++) {
			if (HxArray.get(j) > max ){
				max = HxArray.get(j);
				index = j;
			}
		}
		this.x = index;
		System.out.println("x ---->" +x);
		this.setHx(max);
		
	}


	public int NLa(int attr, Messages messagesWith, Messages messagesWithOut){
		//int attr = this.index;
		int countWithAttr = 0;
		Messages temp = messages;
		// Iterate trough the map
		for (int j=1; j<=temp.size(); j++){
			// Iterate trough the array list of messages
			messagesWith.getMatrix().put(j, new ArrayList<LinkedHashSet<Integer>>());
			messagesWithOut.getMatrix().put(j, new ArrayList<LinkedHashSet<Integer>>());

			for (int i = 0; i<temp.getMatrix().get(j).size(); i++){
				// Check if the current attribute(word) is in the set(sentence)
				if (temp.getMatrix().get(j).get(i).contains(attr)){
					messagesWith.getMatrix().get(j).add(temp.getMatrix().get(j).get(i));
					countWithAttr++;
				}
				else
					messagesWithOut.getMatrix().get(j).add(temp.getMatrix().get(j).get(i));
			}
		}
		return countWithAttr;
	}


	public double calculateEntropyForLeaf(){
		double entropy = 0;
		double Ni = 0;
		for (int i=1; i <= messages.getMatrix().size(); i++){
			Ni = messages.getMatrix().get(i).size();
			if (Ni != 0 )
				entropy +=  (Ni / messages.sumAllMessages()) * (Math.log( messages.sumAllMessages()) - Math.log(Ni));
		}

		return entropy;

	}

	public double calculateInformationGain(int attr){

		return -1;

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

	public int getNL() {
		return NL;
	}

	public void setNL(int nL) {
		NL = nL;
	}

	public double getHx() {
		return Hx;
	}

	public void setHx(double hx) {
		Hx = hx;
	}


	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}




	public int getX() {
		return this.x;
	}


}

