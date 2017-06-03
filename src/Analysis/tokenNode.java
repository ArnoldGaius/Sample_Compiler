package Analysis;

public class tokenNode {
	private String word;
	private int token;
	private int row;
	public tokenNode(String word,int token,int row){
		setWord(word);
		setToken(token);
		setRow(row);
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public void setRow(int row){
		this.row=row;
	}
	public int getRow(){
		return this.row;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
