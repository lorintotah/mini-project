



import java.util.ArrayList;


public class Dictionary {

	private ArrayList<String> _dictionary;


	public Dictionary(){

		_dictionary = new ArrayList<String>();
	}
	
	public void setDictionary(ArrayList<String> dic){
		_dictionary = dic;
	}

	public ArrayList<String> getDictionary(){

		return _dictionary;
	}

	public void print(){
		for (int i=0 ; i< _dictionary.size(); i++){
			System.out.println(i +" " +_dictionary.get(i).toString());
			}
	}

	/** Updating existing dictionary from current word-set(bag of words)
	 * No duplicates are allowed
	 * @param Bag of words object, bow.
	 */
	}
