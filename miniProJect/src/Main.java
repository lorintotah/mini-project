



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


		int P = Integer.parseInt(args[2]);
		Parser parser = new Parser();
		String file;
		ArrayList<String> sentences = new ArrayList<String>();
		for (int i = 1; i <= numOfFiles; i++){
			file = args[0] +i+".train";
			parser.parse(file,i);
		}
		System.out.println("Starting...........");
		HashMap<Integer, ArrayList<String>> TRAINING_SET = parser.getMessages();
		HashMap<Integer, ArrayList<String>> VALID_SET = new HashMap<Integer, ArrayList<String>>();

		System.out.println("Choose " +P +"% Random Messages from TRAINING_SET to the VALID_SET");

		for (int i = 1; i <= numOfFiles; i++) {
			VALID_SET.put(i, new ArrayList<String>());
			double max = Math.max( Math.ceil(TRAINING_SET.get(i).size() * (P * 0.01)), 1 );
			for (int j = 0; j < max; j++) {
				int thisP = (int)(Math.random() * TRAINING_SET.get(i).size());
				VALID_SET.get(i).add(TRAINING_SET.get(i).remove(thisP));
			}

		}
		System.out.println("Build the dictionary...........");

		makeDict dict2 = new makeDict();
		for (int i = 1; i <= numOfFiles; i++){
			// All the messages related to the i'th forum
			sentences = TRAINING_SET.get(i);
			// Bag of words for every message (j) from the i'th forum

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
		System.out.println("Dictionary is ready...........");


		Messages m = new Messages();
		m.updateMatrix(TRAINING_SET, dict);

		Messages validMSG = new Messages();
		validMSG.updateMatrix(VALID_SET, dict);

		TreeSet<Node> nodes = new TreeSet<Node>(new MyComp());

		//first Tree - 1 split
		System.out.println("Start with the FIRST tree...........");

		DecisionTree Tree = new DecisionTree(numOfFiles,1,m,nodes);
		Tree.start(dict);

		ArrayList<DecisionTree> Trees = new ArrayList<DecisionTree>();
		Trees.add(Tree);

		int L = Integer.parseInt(args[3]);

		for (int t = (int) Math.pow(2, 0); t < Math.pow(2, L); t *= 2) {
			System.out.println("Making another tree...........");

			DecisionTree newTree = new DecisionTree(Tree);
			newTree.setTandStart(t);

			Trees.add(newTree);
			Tree = newTree;
		}
		PrintWriter writer = new PrintWriter(args[0]+args[4], "UTF-8");

		ArrayList<ArrayList<ArrayList<Integer>>> guessAnswers = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<ArrayList<Integer>>> TrainingGuess = new ArrayList<ArrayList<ArrayList<Integer>>>();

		ArrayList<Integer> arr = null ;
		ArrayList<Integer> TrainingArray = null ;

		int[] countA = new int[Trees.size()];
		int[] countB = new int[Trees.size()];

		for (int k = 0; k < Trees.size(); k++) {

			Tree = Trees.get(k);
			guessAnswers.add(new ArrayList<ArrayList<Integer>>());

			for (int j = 1; j <= validMSG.getMatrix().size(); j++) {
				arr = new ArrayList<Integer>();
				for (int t = 0; t < validMSG.getMatrix().get(j).size(); t++) {
					arr.add(Tree.checkWhichForum(validMSG.getMatrix().get(j).get(t)));
				}
				guessAnswers.get(k).add(arr);
			}
		}
		for (int k = 0; k < Trees.size(); k++) {
			Tree = Trees.get(k);
			TrainingGuess.add(new ArrayList<ArrayList<Integer>>());

			for (int j = 1; j <= m.getMatrix().size(); j++) {
				TrainingArray = new ArrayList<Integer>();
				for (int t = 0; t < m.getMatrix().get(j).size(); t++) {
					TrainingArray.add(Tree.checkWhichForum(m.getMatrix().get(j).get(t)));
				}
				TrainingGuess.get(k).add(TrainingArray);
			}
		}
		// [[1,2,3,4][1,2,3,4]...[1,2,3,4]]
		for (int i = 0; i < guessAnswers.size(); i++) {
			int count=0;
			for (int j = 0; j < guessAnswers.get(i).size(); j++) {
				for (int j2 = 0; j2 < guessAnswers.get(i).get(j).size(); j2++) {
					if (guessAnswers.get(i).get(j).get(j2) == j+1)
					{
						count++;
					}
				}
			}

			int count2=0;
			for (int j = 0; j < TrainingGuess.get(i).size(); j++) {
				for (int j2 = 0; j2 < TrainingGuess.get(i).get(j).size(); j2++) {
					if (TrainingGuess.get(i).get(j).get(j2) == j+1)
					{
						count2++;
					}
				}
			}
			countB[i]= count2;
			System.out.println("==================== For -  " +i +" Tree ================== ");
			System.out.println("============ Training Set =========================== ");
			System.out.println("============ Right Answers : " +count2 +" From:"+m.sumAllMessages() +"=========");
			System.out.println("============ In Precentage: " + (count2 / m.sumAllMessages()  )* 100+"%======");
			System.out.println("============ End Valid Set ==========================");

			

			
			countA[i]= count;
			System.out.println("============ Valid Set ============ ");
			System.out.println("============ Right Answers : " +count +" From:"+validMSG.sumAllMessages());
			System.out.println("============ In Precentage: " + (count /validMSG.sumAllMessages() ) * 100+"%");
			System.out.println("==================== End Training Set ===================");

	}
		int index = 0;
		int max = 0;
		DecisionTree TheChoosenTree= null;
		for (int i = 0; i < countA.length; i++) {
			if (max <countA[i]){
				max = countA[i];
				index = i;
			}
		}
			TheChoosenTree = Trees.get(index);
			System.out.println("============ The Choosen T " +index +"========");
			System.out.println("===================================");



		Parser parserTest = new Parser();
		String file2 = args[0]+args[1];
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
		Parser parserLabel = new Parser();
		String file3 = args[0]+"test.labels";
		parserLabel.parse(file3,1);
		HashMap<Integer, ArrayList<String>> pl = parserLabel.getMessages();
		int count = 0;
		for (int j = 0; j < arr.size(); j++) {
			writer.println(arr.get(j));
			if (arr.get(j) == Integer.parseInt(pl.get(1).get(j)))
				count++;

		}
		System.out.println("============ Examples Set ============ ");
		System.out.println("============ Right Answers : " +count +" From:"+mTest.sumAllMessages());
		System.out.println("============ In Precentage: " + (count /mTest.sumAllMessages()  )* 100);
		System.out.println("============ End Examples Set ========");
		

		writer.close();
	}
}

