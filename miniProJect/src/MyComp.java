import java.util.Comparator;


public class MyComp implements Comparator<Node> {

    @Override
    public int compare(Node e1, Node e2) {
    	System.out.println(e1.getInformationGain());
    	System.out.println(e2.getInformationGain());
        if(e1.getInformationGain() > e2.getInformationGain()){
            return 1;
        } else {
            return -1;
        }
    }
}
