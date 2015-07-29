

import java.util.ArrayList;
import java.util.Arrays;


public class BagOfWords {
	private ArrayList<Pair> _wordSet;


	public BagOfWords(){
		_wordSet = new ArrayList<Pair>();
	}

	public BagOfWords(ArrayList<Pair> list){
		_wordSet = list;
	}

	public void add(Pair word){
		_wordSet.add(word);
	}
	public ArrayList<Pair> getBag(){

		return _wordSet;
	}

	/**Prints the entire set of words */
	public void print(){
		for(int i = 0; i <_wordSet.size(); i++) {
			System.out.println(_wordSet.get(i).getWord().toString()+" Shows: " +_wordSet.get(i).getNumOfShows());
		}
	}

	/** creates a bag of words from one sentence
	 * Only words with meaning count, and no duplicates are allowed (like a set)
	 * @param a String representing the sentence */
	public void createBagFromMessage (String message){
		//	System.out.println("The full message : " + message);
		
		String[] words = {"that", "this" , "have" , "with", "from" , "what" , "there",
				"would","they","writes","your","about","some","article","will","which",
				"more","than","other","know","does","like","just","then","think",
				"only", "because", "these","doesn" ,"being" , "also", "when","them", "well","were","been", "doesn", "even", "very", };
	 ArrayList<String> notGood = new ArrayList<String>(Arrays.asList(words)); //new ArrayList is only needed if you absolutely need an ArrayList


		
		String[] temp = message.toLowerCase().split(" +|,|'|!|$|>|<|>|\" |:|\\.|\\?|-|\\(|\\)");
		for (int i = 0; i < temp.length; i++) {
			// Match to see if it is a "real" word
			//	System.out.println(temp[i]);
			if (temp[i].matches("[a-z]+") && !temp[i].contains("@") && (temp[i].length() > 3) && !notGood.contains(temp[i]))
					{
				//If the word is not already in the bag
				if (_wordSet.size()==0)
					_wordSet.add(new Pair(temp[i],1));
				else{
					boolean isFound = false;
					for (int j = 0; j < _wordSet.size() && !isFound; j++) {
						int tmp2 = 0;
						if (_wordSet.get(j).getWord().equals(temp[i])){
							isFound = true;
							tmp2 = _wordSet.get(j).getNumOfShows();
							tmp2++;
							_wordSet.get(j).setNumOfShows(tmp2);
						}

					}
					
					if (!isFound){
						_wordSet.add(new Pair(temp[i],1));
					}
				}

			}
		}

	}
}


