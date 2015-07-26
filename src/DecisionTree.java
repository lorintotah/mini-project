import java.util.ArrayList;


public class DecisionTree {

		private Node root;
		private int NUMBER_OF_FILES;
		private Messages VALID_SET;
		private Messages TRAINING_SET;
		private int T;
		
		public DecisionTree(int T,int NUMBER_OF_FILES){
			this.root = null;
			this.NUMBER_OF_FILES = NUMBER_OF_FILES;
			this.T = T;
		}
	
		private void start(Dictionary Dict,Messages matrix){
			ArrayList <Integer> allDictWords = new ArrayList<Integer>();
			for (int i = 0; i < Dict.getDictionary().size(); i++) {
				allDictWords.add(i);
			}
			root = new Node(allDictWords,matrix.getMatrix());
			root.setLeaf(true);
		}
		
		private void pickValidSet(int P){
			for (int j = 0; j < NUMBER_OF_FILES; j++){
			// choose P% random Messages from TRAINING_SET to VALID_SET
			}
			
		}
	
}
