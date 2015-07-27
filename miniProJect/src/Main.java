


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;


public class Main {

	final static int numOfFiles = 2;
	
	public static void main(String[] args){
		int P = 20;
		Parser parser = new Parser();
		String file;
		ArrayList<String> sentences = new ArrayList<String>();
		for (int i = 1; i <= numOfFiles; i++){
			file = args[0] +i+".train";
			parser.parse(file,i);
		}
		HashMap<Integer, ArrayList<String>> TRAINING_SET = parser.getMessages();
		HashMap<Integer, ArrayList<String>> VALID_SET = new HashMap<Integer, ArrayList<String>>();


		for (int i = 1; i <= numOfFiles; i++) {
			VALID_SET.put(i, new ArrayList<String>());
			double max = Math.max( Math.ceil(TRAINING_SET.get(i).size() * (P * 0.01)), 1 );
			for (int j = 0; j < max; j++) {
				int thisP = (int)(Math.random() * TRAINING_SET.get(i).size());
				VALID_SET.get(i).add(TRAINING_SET.get(i).remove(thisP));
			}

		}

		Dictionary dict = new Dictionary();
		for (int i = 1; i <= numOfFiles; i++){
			// All the messages related to the i'th forum
			sentences = TRAINING_SET.get(i);
			// Bag of words for every message (j) from the i'th forum
			for (int j = 0; j < sentences.size(); j++){
				BagOfWords bag = new BagOfWords();
				bag.createBagFromMessage(sentences.get(j).toString());
				// Update the dictionary for the current message ( bag of words)
				dict.updateDictionary(bag);
			}
		}

		for (int i: TRAINING_SET.keySet()){
			for (int j = 0;j < TRAINING_SET.get(i).size(); j++){
				TRAINING_SET.get(i).get(j).toLowerCase();
			}
		}

		Messages m = new Messages();
		m.updateMatrix(TRAINING_SET, dict);
		m.print();
		
		System.out.println("=======DICTIONARY:======");
		dict.print();
		System.out.println("===================:");

		TreeSet<Pair> nodes = new TreeSet<Pair>(new MyComp());
		
		//first Tree - 1 split
		DecisionTree Tree = new DecisionTree(numOfFiles,2,m,nodes);
		Tree.start(dict);
		System.out.println(Tree);
		
		Messages validMSG = new Messages();
		validMSG.updateMatrix(VALID_SET, dict);

			
	//	System.out.println("A");
		ArrayList<ArrayList<Integer>> answers = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for (int j = 1; j <= validMSG.getMatrix().size(); j++) {
				for (int t = 0; t < validMSG.getMatrix().get(j).size(); t++) {
				//	System.out.println("C");

					arr.add(Tree.checkWhichForum(validMSG.getMatrix().get(j).get(t)));
//					System.out.println("D");

				}
			}
			answers.add(arr);
System.out.println(answers);
	}
	
}
