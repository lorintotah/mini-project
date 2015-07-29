import java.util.Comparator;


public class compForDict implements Comparator<Pair> {

    @Override
    public int compare(Pair e1, Pair e2) {
        if(e1.getNumOfShows() > e2.getNumOfShows()){
            return 1;
        } else {
            return -1;
        }
    }
}
