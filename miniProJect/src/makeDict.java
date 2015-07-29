



import java.util.ArrayList;


public class makeDict {

	private ArrayList<Pair> _dictionary;


	public makeDict(){

		_dictionary = new ArrayList<Pair>();
	}

	public ArrayList<Pair> getDictionary(){

		return _dictionary;
	}

	public void print(){
		for (int i=0 ; i< _dictionary.size(); i++){
			System.out.println(i +" " +_dictionary.get(i).getWord().toString()+" Shows: " +_dictionary.get(i).getNumOfShows());
			
		}
	}

	/** Updating existing dictionary from current word-set(bag of words)
	 * No duplicates are allowed
	 * @param Bag of words object, bow.
	 */
	public void updateDictionary(BagOfWords bow){
		ArrayList<Pair> temp = bow.getBag();
		for (int i = 0; i<temp.size(); i++){
			boolean isFound = false;
			for (int j = 0; j < _dictionary.size() && !isFound; j++) {
				int tmp = 0;
				if (_dictionary.get(j).getWord().equals(temp.get(i).getWord())){
					isFound = true;
					tmp = _dictionary.get(j).getNumOfShows();
					tmp += temp.get(i).getNumOfShows();
					_dictionary.get(j).setNumOfShows(tmp);
				}


			}
			if (!isFound)
				_dictionary.add(temp.get(i));

		}


	}
}