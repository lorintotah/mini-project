import java.util.Comparator;


public class MyComp implements Comparator<Node> {

    @Override
    public int compare(Node e1, Node e2) {
    	
    	if(e1.getInformationGain() > e2.getInformationGain()){
            return 1;
        } else {
            return -1;
        }
    }
}
