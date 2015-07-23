
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Parser {

	private HashMap<Integer, ArrayList<String>> _message;
	
	public Parser(){
		_message = new HashMap<Integer,ArrayList<String>>();
	}
	
	/** Creates an hashMap of all messages related to the forum they are taken from
	 * Each entry has an indicator(the id of the forum) and an arrayList of all the messages from the forum
	 * @param an absolute path to the file */
	public void parse(String path, int key){
		String current;
		ArrayList<String> lst = new ArrayList<String>();
		BufferedReader buffer = null;
		try {
			//Read from file to the buffer
			buffer = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e1) {
			System.out.println("problem opening file" + e1);
		}
		try {
			// While we have more information to read
			while ((current = buffer.readLine()) != null){
				// Add the current message to the list
				lst.add(current);
				// Add the list to the current value of the key
				_message.put(key, lst);
			}
		} catch (IOException e) {
			System.out.println("problem reading from file");
		}
	}
	
	public HashMap< Integer, ArrayList<String>> getMessages(){
		return _message;
	}
}

