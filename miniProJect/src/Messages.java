

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
/**
 This class's purpose is to save for each forum the set of words (indexes in the
 dictionary) for each message in that forum  
 */

public class Messages {

	private HashMap<Integer, ArrayList<LinkedHashSet<Integer>>> _workingSet;


	public Messages(){
		_workingSet = new HashMap<Integer, ArrayList<LinkedHashSet<Integer>>>();

	}

	public HashMap<Integer, ArrayList<LinkedHashSet<Integer>>> getMatrix(){
		return _workingSet;
	}


	public int size(){
		return _workingSet.size();
	}
	
	/**
	 * Updating the corresponding matrix with a set of messages and dictionary
	 * each message is examine by it words to see if they are in the dictionary
	 * if so, the relevant index is added to the set, if not, nothing happens
	 * @param messages
	 * @param dict
	 */
	public void updateMatrix(HashMap<Integer, ArrayList<String>> messages, Dictionary dict){
		int index;
		//For each forum:
		for (int i: messages.keySet()){
			_workingSet.put(i, new ArrayList<LinkedHashSet<Integer>>());
			//Iterating through the messages of the forum
			for (int j = 0;j < messages.get(i).size(); j++){
				//split the whole message into words
				String[] curr = messages.get(i).get(j).toString().split(" +|,|'|!|$|>|<|>|\" |:|\\.|\\?|-|\\(|\\)");
				LinkedHashSet<Integer> temp = new LinkedHashSet<>();
				// For every word in the message find the corresponding entry in the dictionary 
				for (int k=0; k < curr.length; k++){
					if (dict.getDictionary().indexOf(curr[k]) != -1){
						index = dict.getDictionary().indexOf(curr[k]); // Index of the corresponding entry in the dictionary 
						// for the current word
						temp.add(index);}
				}
				_workingSet.get(i).add(temp);
			}
		}
	}




	public double sumAllMessages(){
		double count = 0;
		for (int i = 1; i <= _workingSet.size(); i++) 
		{
			count += _workingSet.get(i).size();


		}

		return count;
	}

	public void print(){
		for (int j = 1; j <=
				_workingSet.size(); j++) {
			System.out.println("forum     " +j);
			for (int i=0; i<_workingSet.get(j).size(); i++){
				LinkedHashSet<Integer> temp =_workingSet.get(j).get(i);
				System.out.println(temp.toString());
			}
		}
	}
}
