
public class Pair {

	private Node node;
	private double ig;
	
	public Pair(Node node, double ig){
		this.node = node;
		this.ig = ig;
	}
	public Pair(Pair n) {
		this.node = new Node(n.getNode());
		this.ig = n.getIg();
	}
	
	public double getIg() {
		return ig;
	}
	public void setIg(int ig) {
		this.ig = ig;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	
}
