import java.util.ArrayList;


public class Dictionary {

	private ArrayList<String> _dictionary;


	public Dictionary(){

		_dictionary = new ArrayList<String>();
	}
	
	public ArrayList<String> getDictionary(){

		return _dictionary;
	}
	
	public void print(){
		for (int i=0 ; i< _dictionary.size(); i++){
			System.out.println(_dictionary.get(i).toString());
		}
	}
	
	/** Updating existing dictionary from current word-set(bag of words)
	 * No duplicates are allowed
	 * @param Bag of words object, bow.
	 */
	public void updateDictionary(BagOfWords bow){

		ArrayList<String> temp = bow.getBag();
		for (int i = 0; i<temp.size(); i++){
			if (!_dictionary.contains(temp.get(i)))
				_dictionary.add(temp.get(i));
		}

	}


}
