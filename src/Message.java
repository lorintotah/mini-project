import java.util.ArrayList;
import java.util.Vector;



public class Message {

	private Vector<Boolean> _message;

	public Message(){

		_message = new Vector<Boolean>();

	}
	public Vector<Boolean> getMessage(){

		return _message;

	}
	
	public void printVector(){
		for (int i = 0; i < _message.size(); i++)
			System.out.println(_message.get(i));
	}

	/** Returns a vector representing the message corresponding to the words in the dictionary.
	 * Every entry in the vector is 1(true) if the word is in the dictionary, and 0(false) otherwise.
	 *@param a message msg, and a dictionary dict
	 *@return Boolean vector representing the message  */

	public Vector<Boolean> makeMessage(String msg, Dictionary dict){
		String[] arrayOfWords = msg.toLowerCase().split(" +|,|'|!|$|>|<|>|\" |:|\\.|\\?|-|\\(|\\)");
		//for (int i = 0; i<arrayOfWords.length; i++)
	//		System.out.println(arrayOfWords[i]);
		ArrayList<String> temp = dict.getDictionary();
		//For every word, checking if the dictionary contains it. If so, put up the bit
		for (int i = 0; i<arrayOfWords.length; i++){
			if (temp.contains(arrayOfWords[i]))
				_message.add(i, true);
			else{
				_message.add(i, false);
			}
				}
		return _message;
	}



}
