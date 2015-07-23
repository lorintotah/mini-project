
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;


public class Matrix {

	private ArrayList<Pair> _workingSet;


	public Matrix(){
		_workingSet = new ArrayList<Pair>();

	}

	public int searchForword(int i){
		int count = 0;
		for (int j = 0; j < _workingSet.size(); j++){
			if (_workingSet.get(j)._indexes.contains(i))
				count++;
		}
		return count;
	}

	public ArrayList<Pair> getMatrix(){
		return _workingSet;
	}
	public void insert(int i,int index){
		_workingSet.get(i)._indexes.add(index);
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
				Pair p = new Pair(temp, i);
				_workingSet.add(p);

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
