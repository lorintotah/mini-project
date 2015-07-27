

import java.util.ArrayList;


public class BagOfWords {
	private ArrayList<String> _wordSet;


	public BagOfWords(){
		_wordSet = new ArrayList<String>();
	}
	
	public BagOfWords(ArrayList<String> list){
		_wordSet = list;
	}

	public void add(String word){
		_wordSet.add(word);
	}
	public ArrayList<String> getBag(){
		
		return _wordSet;
	}

	/**Prints the entire set of words */
	public void print(){
		for(int i = 0; i <_wordSet.size(); i++) {
			System.out.println(_wordSet.get(i).toString());
		}
	}

	/** creates a bag of words from one sentence
	 * Only words with meaning count, and no duplicates are allowed (like a set)
	 * @param a String representing the sentence */
	public void createBagFromMessage (String message){
	//	System.out.println("The full message : " + message);
		String[] temp = message.toLowerCase().split(" +|,|'|!|$|>|<|>|\" |:|\\.|\\?|-|\\(|\\)");
		for (int i = 0; i < temp.length; i++) {
			// Match to see if it is a "real" word
			//	System.out.println(temp[i]);
			if (temp[i].matches("[a-z]+") && !temp[i].contains("@")){
				//If the word is not already in the bag
				if (!_wordSet.contains(temp[i]))
					_wordSet.add(temp[i]);
			}
		}
		
	}
}


