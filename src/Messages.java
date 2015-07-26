
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;


public class Messages {

	private HashMap<Integer,ArrayList<LinkedHashSet<Integer>>> _workingSet;


	public Messages(){
		_workingSet = new HashMap<Integer,ArrayList<LinkedHashSet<Integer>>>();

	}

//	public int searchForword(int i){
//		int count = 0;
//		for (int j = 0; j < _workingSet.size(); j++){
//			if (_workingSet.get(j)._indexes.contains(i))
//				count++;
//		}
//		return count;
//	}

	public HashMap<Integer,ArrayList<LinkedHashSet<Integer>>> getMatrix(){
		return _workingSet;
	}

	public LinkedHashSet<Integer> getSet(int index){
		return _workingSet.get(index)._indexes;
	}

	public void updateMatrix(HashMap<Integer, ArrayList<String>> messages, Dictionary dict){
		for (int i: messages.keySet()){
			for (int j = 0;j < messages.get(i).size(); j++){
				String[] curr = messages.get(i).get(j).toString().split(" +|,|'|!|$|>|<|>|\" |:|\\.|\\?|-|\\(|\\)");
				LinkedHashSet<Integer> temp = new LinkedHashSet<>();
				// For every word in the message find the corresponding entry in the dictionary 
				for (int k=0; k < curr.length; k++){
					int index = dict.getDictionary().indexOf(curr[k]); // Index of the corresponding entry in the dictionary 
					// for the current word
					temp.add(index);
				}
				
				_workingSet.get(i).add(temp);

			}
		}
	}

	public int getForumFromMessage(int i){
		return _workingSet.get(i)._label;
	}

	public void print(){
		for (int i=0; i<100; i++){
			LinkedHashSet<Integer> temp =_workingSet.get(i)._indexes;
			System.out.println(temp.toString());
		}
	}
}
