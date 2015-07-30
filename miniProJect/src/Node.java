
import java.util.*;



public class Node {
	public Node leftChild;
	public Node rightChild;
	private Messages messages;  // array list with the numbers of messages in him
	private ArrayList<Integer> Attributes;
	private double entropy; // h(x)
	private boolean isLeaf; //
	private int name;
	private double NL;
	private int x;
	private double Hx;
	private double informationGain;

	public Node(Node oldNode,TreeSet<Node> nodes) {
		this.messages = new Messages();
		for (int i = 1; i <= oldNode.messages.getMatrix().size(); i++) {
			this.messages.getMatrix().put(i, new ArrayList<LinkedHashSet<Integer>>());
			for (int j = 0; j < oldNode.messages.getMatrix().get(i).size(); j++) 
				this.messages.getMatrix().get(i).add(oldNode.messages.getMatrix().get(i).get(j));
		}
		this.Attributes = new ArrayList<Integer>();
		for (int i = 0; i < oldNode.Attributes.size(); i++)
			this.Attributes.add(oldNode.Attributes.get(i));
		this.name = oldNode.name;
		this.entropy = oldNode.entropy;
		this.isLeaf = oldNode.isLeaf; // ?? is it good ?
		this.NL = oldNode.NL;
		this.x = oldNode.x;
		this.informationGain = oldNode.informationGain;
		if (oldNode.isLeaf == true)
			nodes.add(this);
		if (oldNode.leftChild != null)
			this.leftChild = new Node(oldNode.leftChild,nodes);
		else
			setLeftChild(null);
		if (oldNode.rightChild != null)
			this.rightChild = new Node(oldNode.rightChild,nodes);
		else
			setRightChild(null);
	}


	public Node(ArrayList<Integer> Att,Messages msg) {
		this.messages = msg;
		Attributes = Att;
		this.setName(0);
		setLeftChild(null);
		setRightChild(null);
		setLeaf(true);
		this.setNL(messages.sumAllMessages());
		calculateEntropyForLeaf();
		calculateWhichXIsBest();
	}


	public ArrayList<Integer> getAttr(){
		return Attributes;
	}

	public void chooseForum(){
		int count = 0;
		int index = 0;

		for (int i = 1; i <= this.messages.getMatrix().size(); i++) {
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

	private void calculateWhichXIsBest() {
		ArrayList<Double> HxArray = new ArrayList<Double>();
		for (int i = 0; i < this.getAttr().size(); i++) {

			Messages messagesWith = new Messages();
			Messages messagesWithOut = new Messages();
			this.NLa(this.getAttr().get(i),messagesWith, messagesWithOut);

			if (messagesWith.sumAllMessages()!=0 && messagesWithOut.sumAllMessages()!=0 )
			{
				double HLa = 0; //with
				double HLb = 0; //without

				double NiWith = 0;
				double NiWithOut = 0;

				// CALCULATE H(La) - messagesWith

				for (int j = 1; j <= messagesWith.getMatrix().size(); j++) {
					NiWith = messagesWith.getMatrix().get(j).size();
					if (NiWith != 0 && (NiWith!= 1) )
						HLa +=  (NiWith / messagesWith.sumAllMessages()) * ( Math.log(messagesWith.sumAllMessages()) - Math.log(NiWith) );
				}
//				if (HLa==0)
//					for (int j = 1; j <= messagesWith.getMatrix().size(); j++){ 
//						NiWith = messagesWith.getMatrix().get(j).size();
//						System.out.println("NiWith " +NiWith);
//						System.out.println("messagesWith.sumAllMessages() "+messagesWith.sumAllMessages());
//						System.out.println("( Math.log(messagesWith.sumAllMessages()) - Math.log(NiWith):" +( Math.log(messagesWith.sumAllMessages()) - Math.log(NiWith)));
//						System.out.println(messagesWith.getMatrix().get(j));
//					}
				// CALCULATE H(Lb) - messagesWith
				for (int j = 1; j <= messagesWithOut.getMatrix().size(); j++) {
					NiWithOut = messagesWithOut.getMatrix().get(j).size();
					if (NiWithOut != 0 && (NiWithOut!= 1))
						HLb += (NiWithOut / messagesWithOut.sumAllMessages()) * (Math.log(messagesWithOut.sumAllMessages()) -Math.log(NiWithOut));
				}

				
				double Hxx = (messagesWith.sumAllMessages() / this.getMessages().sumAllMessages()) * HLa + (messagesWithOut.sumAllMessages() / this.getMessages().sumAllMessages()) * HLb;	
//			

//				System.out.println("(messagesWith.sumAllMessages() / this.getMessages().sumAllMessages()):"+(messagesWith.sumAllMessages() / this.getMessages().sumAllMessages()));
//				System.out.println("(messagesWithOut.sumAllMessages() / this.getMessages().sumAllMessages()):" +(messagesWithOut.sumAllMessages() / this.getMessages().sumAllMessages()));
//				System.out.println("============================");
//				System.out.println("messagesWith.sumAllMessages():" +messagesWith.sumAllMessages());
//				System.out.println("messagesWithOut.sumAllMessages():" +messagesWithOut.sumAllMessages());
//				System.out.println("this.getMessages().sumAllMessages():" +this.getMessages().sumAllMessages());
//				System.out.println("HLa: "+HLa);
//				System.out.println("HLb: "+HLb);
//				System.out.println("Hx:" +Hxx);
//				System.out.println("============================");

				HxArray.add(Hxx);


			}
			else{
				HxArray.add(Double.POSITIVE_INFINITY);
			}
		}

		double min = HxArray.get(0);

		int index = 0;
		for (int j = 1; j < HxArray.size(); j++) {
			if (HxArray.get(j) < min ){
				min = HxArray.get(j);
				index = j;
			}
		}
		this.x = this.getAttr().get(index);
		this.setHx(min);
	}


	public int NLa(int attr, Messages messagesWith, Messages messagesWithOut){
		//int attr = this.index;
		int countWithAttr = 0;
		Messages temp = messages;
		//System.out.println(temp.getMatrix().size());
		// Iterate trough the map
		for (int j=1; j<=temp.getMatrix().size(); j++){
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


	public void calculateEntropyForLeaf(){
		double entropy = 0;
		double Ni = 0;
		for (int i=1; i <= messages.getMatrix().size(); i++){
			Ni = messages.getMatrix().get(i).size();
			if (Ni != 0 ){
				entropy +=  (Ni / messages.sumAllMessages()) * ( Math.log(messages.sumAllMessages()) - Math.log(Ni) );
			}

		}


		this.entropy = entropy;
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

	public double getNL() {
		return NL;
	}

	public void setNL(double d) {
		NL = d;
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

	public void print() {
		print("", true);
	}

	private void print(String prefix, boolean isTail) {
		System.out.println(prefix + (isTail ? "└── " : "├── ") + x);
		if (!isLeaf){
			leftChild.print(prefix + (isTail ? "    " : "│   "), false);
			rightChild.print(prefix + (isTail ? "    " : "│   "), false);
		}

	}

	public void setInformationGain(double ig) {
		this.informationGain = ig;
	}

	public double getInformationGain() {
		return informationGain;
	}



}

