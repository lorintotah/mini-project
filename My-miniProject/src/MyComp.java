import java.util.Comparator;


public class MyComp implements Comparator<Pair> {

    @Override
    public int compare(Pair e1, Pair e2) {
        if(e1.getIg() > e2.getIg()){
            return 1;
        } else {
            return -1;
        }
    }
}
