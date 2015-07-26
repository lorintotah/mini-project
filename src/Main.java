

import java.util.ArrayList;
import java.util.HashMap;


public class Main{

	public static void main(String[] args){
		
		final int NUM_OF_FILES = 4;
		Parser parser = new Parser();
		String file;
		ArrayList<String> sentences = new ArrayList<String>();
		for (int i = 1; i < 5; i++){
		file = args[0] +i+".train";
		parser.parse(file,i);
		}
		HashMap<Integer, ArrayList<String>> messages = parser.getMessages();
		Dictionary dict = new Dictionary();
		for (int i = 1; i < NUM_OF_FILES; i++){
			
			// All the messages related to the i'th forum
			sentences = messages.get(i);
			
			// Bag of words for every message (j) from the i'th forum
			for (int j = 0; j < sentences.size(); j++){
				BagOfWords bag = new BagOfWords();
				bag.createBagFromMessage(sentences.get(j).toString());
				// Update the dictionary for the current message ( bag of words)
				dict.updateDictionary(bag);
			}
		}
		for (int i: messages.keySet()){
			for (int j = 0;j < messages.get(i).size(); j++){
				messages.get(i).get(j).toLowerCase();
			}
		}
		Messages m = new Messages();
		m.updateMatrix(messages, dict);
		int max = 0 ;
		int index = 0;
		for (int i = 0; i < NUM_OF_FILES; i++) {
			if ( m.getMatrix().get(0)._indexes.size() >  max)
			{
				max = m.getMatrix().get(0)._indexes.size();
				index = i;
			}
		}
		System.out.println("LORIN" + index);
	}

	private void findTheRightT(int L,int P,int NUM_OF_FILES){
		for (int i = 0; i < L; i++) {
			int T = 2^i;
			DecisionTree tmp = new DecisionTree(T,NUM_OF_FILES);
			tmp.start(Dict, matrix);
				
			}
			
		}
	}

}
