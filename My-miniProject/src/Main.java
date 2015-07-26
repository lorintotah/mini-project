

import java.util.ArrayList;
import java.util.HashMap;


public class Main{

	public static void main(String[] args){
		final int numOfFiles = 4;
		Parser parser = new Parser();
		String file;
		ArrayList<String> sentences = new ArrayList<String>();
		for (int i = 1; i <= numOfFiles; i++){
			file = args[0] +i+".train";
			parser.parse(file,i);
		}
		HashMap<Integer, ArrayList<String>> messages = parser.getMessages();
		Dictionary dict = new Dictionary();
		for (int i = 1; i < 5; i++){

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
		m.print();
		
		
		
		System.out.println("           ");
		dict.print();
	}
	

}

