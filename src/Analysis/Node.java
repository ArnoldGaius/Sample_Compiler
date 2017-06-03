package Analysis;

public class Node {

	private String start;
	private String recive;
	private String end;

	public Node() {
		super();
	}

	public Node(String start, String recive, String end) {
		super();
		this.start = start;
		this.recive = recive;
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getRecive() {
		return recive;
	}

	public void setRecive(String recive) {
		this.recive = recive;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String toString() {
		return start + "\t" + recive + "\t" + end;
	}
}
