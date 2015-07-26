
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;


public class Messages {

	private HashMap<Integer, ArrayList<LinkedHashSet<Integer>>> _workingSet;


	public Messages(){
		_workingSet = new HashMap<Integer, ArrayList<LinkedHashSet<Integer>>>();

	}

	//	public int searchForword(int i){
	//		int count = 0;
	//		for (int j = 1; j < 5; j++){
	//			if (_workingSet.get(j)._indexes.contains(i))
	//				count++;
	//		}
	//		return count;
	//	}

	public HashMap<Integer, ArrayList<LinkedHashSet<Integer>>> getMatrix(){
		return _workingSet;
	}

//	public void insert(int i,int index){
//		_workingSet.get(i).get(index);
//	}
//
//	public LinkedHashSet<Integer> getSet(int index){
//		return _workingSet.get(index)._indexes;
//	}

	
	public int size(){
		return _workingSet.size();
	}
	public void updateMatrix(HashMap<Integer, ArrayList<String>> messages, Dictionary dict){
		for (int i: messages.keySet()){
			_workingSet.put(i, new ArrayList<LinkedHashSet<Integer>>());
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
