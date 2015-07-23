import java.util.LinkedHashSet;


public class Pair {

	public int _label;
	public LinkedHashSet<Integer> _indexes;

	public Pair(){
		_label = 0;
		_indexes = new LinkedHashSet<>();

	}
	public Pair(LinkedHashSet<Integer> set, int n){
		_label = n;
		_indexes = set;
	}
}
