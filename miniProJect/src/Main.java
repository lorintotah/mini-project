



import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;



public class Main {

	final static int numOfFiles = 4;

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{


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

		makeDict dict2 = new makeDict();
		for (int i = 1; i <= numOfFiles; i++){
			// All the messages related to the i'th forum
			sentences = TRAINING_SET.get(i);
			// Bag of words for every message (j) from the i'th forum
			//System.out.println(sentences.size());

			for (int j = 0; j < sentences.size(); j++){
				BagOfWords bag = new BagOfWords();
				bag.createBagFromMessage(sentences.get(j).toString());
				// Update the dictionary for the current message ( bag of words)
				dict2.updateDictionary(bag);
			}


		}
		TreeSet<Pair> maxDict = new  TreeSet<Pair>(new compForDict());
		for (Pair pair : dict2.getDictionary()) {
			maxDict.add(pair);
		}
		ArrayList<String> goodDict = new ArrayList<String>();

		int SIZE = maxDict.size();

		if (maxDict.size() > 10000){
			SIZE = 10000;
		}
		for (int j = 0; j < SIZE; j++) {
			goodDict.add(maxDict.pollLast().getWord());
		}



		for (int i: TRAINING_SET.keySet()){
			for (int j = 0;j < TRAINING_SET.get(i).size(); j++){
				TRAINING_SET.get(i).get(j).toLowerCase();
			}
		}

		Dictionary dict = new Dictionary();
		dict.setDictionary(goodDict);
		System.out.println("the dict is good" +dict.getDictionary().size());
		PrintWriter writer = new PrintWriter("/Users/Lorin/Downloads/projectdata/20newsgroups/dict.txt", "UTF-8");
		for (int i=0 ; i< dict.getDictionary().size(); i++){

			writer.println(i +" " +dict.getDictionary().get(i).toString());
		}

		Messages m = new Messages();
		m.updateMatrix(TRAINING_SET, dict);

		Messages validMSG = new Messages();
		validMSG.updateMatrix(VALID_SET, dict);

		TreeSet<Node> nodes = new TreeSet<Node>(new MyComp());

		//first Tree - 1 split
		DecisionTree Tree = new DecisionTree(numOfFiles,1,m,nodes);
		Tree.start(dict);

		ArrayList<DecisionTree> Trees = new ArrayList<DecisionTree>();
		Trees.add(Tree);

		int L = 7;

		for (int t = (int) Math.pow(2, 0); t < Math.pow(2, L); t *= 2) {
			DecisionTree newTree = new DecisionTree(Tree);
			newTree.setTandStart(t);

			Trees.add(newTree);
		//	System.out.println(newTree.getNodes());
			Tree = newTree;
		}

		//		for (int i = 0; i < Trees.size(); i++) {
		//			System.out.println("============================");
		//			Trees.get(i).getRoot().print();
		//			System.out.println("============================");
		//
		//
		//		}
		ArrayList<ArrayList<Integer>> guessAnswers = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> arr = null ;

		for (int k = 0; k < Trees.size(); k++) {
			for (int j = 1; j <= validMSG.getMatrix().size(); j++) {
				arr = new ArrayList<Integer>();
				for (int t = 0; t < validMSG.getMatrix().get(j).size(); t++) {
					arr.add(Tree.checkWhichForum(validMSG.getMatrix().get(j).get(t)));

				}
			}
			guessAnswers.add(arr);
		}


		//	System.out.println("THE TREE GUESS: " +answers);

		int[] countA = new int[Trees.size()];
		int count=0;
		for (int i = 0; i < guessAnswers.size(); i++) {
			for (int j = 0; j < guessAnswers.get(i).size(); j++) {
				if (guessAnswers.get(i).get(j) == i+1)
					count++;
				//	System.out.println(count);
			}
			countA[i]= count;
		}
		int index = 0;
		int max = 0;
		DecisionTree TheChoosenTree= null;
		for (int i = 0; i < countA.length; i++) {
			if (max <countA[i]){
				max = countA[i];
				index = i;
			}
			TheChoosenTree = Trees.get(index);
			System.out.println("The Valid answers " +max +"from :"+validMSG.sumAllMessages());
		}

		System.out.println("============================");
		TheChoosenTree.getRoot().print();
		System.out.println("============================");

		Parser parserTest = new Parser();
		String file2 = args[0]+"test.examples";
		parserTest.parse(file2,1);
		HashMap<Integer, ArrayList<String>> TEST_SET = parserTest.getMessages();


		for (int i: TEST_SET.keySet()){
			for (int j = 0;j < TEST_SET.get(i).size(); j++){
				TEST_SET.get(i).get(j).toLowerCase();
			}
		}

		Messages mTest = new Messages();
		mTest.updateMatrix(TEST_SET, dict);

		arr = new ArrayList<Integer>();
		for (int t = 0; t < mTest.getMatrix().get(1).size(); t++) {
			arr.add(TheChoosenTree.checkWhichForum(mTest.getMatrix().get(1).get(t)));

		}
		System.out.println(arr);
		Parser parserLabel = new Parser();
		String file3 = args[0]+"test.labels";
		parserLabel.parse(file3,1);
		HashMap<Integer, ArrayList<String>> pl = parserLabel.getMessages();
		System.out.println(pl);

		int count2=0;
		for (int i = 0; i <pl.get(1).size(); i++) {
			if (arr.get(i) == Integer.parseInt(pl.get(1).get(i)))
				count2++;
		}
		System.out.println(count2);
	}


}




