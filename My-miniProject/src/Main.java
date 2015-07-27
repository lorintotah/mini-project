

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;


public class Main{

	final static int numOfFiles = 4;


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

		//System.out.println("TRAINING " +TRAINING_SET);
		System.out.println("VALID " +VALID_SET);
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
		System.out.println(dict.getDictionary().size());

		for (int i: TRAINING_SET.keySet()){
			for (int j = 0;j < TRAINING_SET.get(i).size(); j++){
				TRAINING_SET.get(i).get(j).toLowerCase();
			}
		}

		Messages m = new Messages();
		m.updateMatrix(TRAINING_SET, dict);
		
		TreeSet<Pair> nodes = new TreeSet<Pair>(new MyComp());
		
		//first Tree - 1 split
		DecisionTree Tree = new DecisionTree(4,1,m,nodes);
		
		//the trees array
		ArrayList<DecisionTree> Trees = new ArrayList<DecisionTree>();
		Trees.add(Tree.start(dict, m));

		int L = 3;

		for (int t = (int) Math.pow(2, 0); t < Math.pow(2, L); t *= 2) {
			System.out.println("t 1  " +t);
			DecisionTree newTree = new DecisionTree(Tree, t);
			newTree.routines();			
			Trees.add(newTree);
			System.out.println("t/2/   " +t);

			Tree = newTree;

			System.out.println("t 3  " +t);

		}
		dict.getDictionary().size();
		Messages validMSG = new Messages();
		validMSG.updateMatrix(VALID_SET, dict);

			
	//	System.out.println("A");
		ArrayList<ArrayList<Integer>> answers = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < Trees.size(); i++) {
			System.out.println("Tree number : " +i);
			ArrayList<Integer> arr = new ArrayList<Integer>();

		//	System.out.println("B");
			for (int j = 1; j <= validMSG.getMatrix().size(); j++) {
				for (int t = 0; t < validMSG.getMatrix().get(j).size(); t++) {
				//	System.out.println("C");

					arr.add(Trees.get(i).checkWhichForum(validMSG.getMatrix().get(j).get(t)));
//					System.out.println("D");

				}
			}
			answers.add(arr);

		}
	
		for (int i = 0; i < answers.size(); i++) {
			System.out.println(answers.get(i).toString());
		}


	}
	
	
}
