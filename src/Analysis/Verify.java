package Analysis;

public class Verify {

	String ss;

	public Verify(String tx) {
		ss = tx;
	}

	public boolean validation() {
		int find = 0;// ±ê¼ÇÀ¨ºÅ
		boolean result = false;
		// String ss = "(a*|b)*";//(a*|b)*
		if (ss.equals("")) {
			return true;
		}
		char[] ch = ss.toCharArray();
		if (ch[0] == '*' || ch[0] == ')' || ch[0] == '|' || ch[0] == '.') {
			return false;
		}
		if ((ch[0] == '(') || (ch[0] >= 'a' && ch[0] <= 'z')
				|| (ch[0] >= 'A' && ch[0] <= 'Z')
				|| (ch[0] >= '0' && ch[0] <= '9')) {
			if (ch[0] == '(') {
				find++;
			}
			for (int i = 1; i < ch.length - 1; i++) {
				if (ch[i] == '(') {
					find++;
				}
				if (ch[i] == ')') {
					find--;
				}
				if (ch[i] == '|') {
					if ((ch[i - 1] == '*' || ch[i - 1] == ')'
							|| (ch[i - 1] >= 'A' && ch[i - 1] <= 'Z')
							|| (ch[i - 1] >= 'a' && ch[i - 1] <= 'z') || (ch[i - 1] >= '0' && ch[i - 1] <= '9'))
							&& (i + 1 < ch.length)
							&& (ch[i + 1] == '('
									|| (ch[i + 1] >= 'A' && ch[i + 1] <= 'Z')
									|| (ch[i + 1] >= 'a' && ch[i + 1] <= 'z') || (ch[i + 1] >= '0' && ch[i + 1] <= '9'))) {
						result = true;
					} else
						return false;
				}
				if (ch[i] == '*' || ch[i] == '.') {
					if ((ch[i - 1] == ')'
							|| (ch[i - 1] >= 'A' && ch[i - 1] <= 'Z')
							|| (ch[i - 1] >= 'a' && ch[i - 1] <= 'z') || (ch[i - 1] >= '0' && ch[i - 1] <= '9'))
							&& (i + 1 < ch.length)
							&& (ch[i + 1] == '|' || ch[i + 1] == ')'
									|| ch[i + 1] == '('
									|| (ch[i + 1] >= 'A' && ch[i + 1] <= 'Z')
									|| (ch[i + 1] >= 'a' && ch[i + 1] <= 'z') || (ch[i + 1] >= '0' && ch[i + 1] <= '9'))) {
						result = true;
					} else
						return false;
				}
			}
			if (find == 0)
				result = true;
			if (find != 0)
				return false;
		}
		if (ch[ch.length - 1] == ')' || ch[ch.length - 1] == '*'
				|| ch[ch.length - 1] == '.'
				|| (ch[ch.length - 1] >= 'a' && ch[ch.length - 1] <= 'z')
				|| (ch[ch.length - 1] >= 'A' && ch[ch.length - 1] <= 'Z')
				|| (ch[ch.length - 1] >= '0' && ch[ch.length - 1] <= '9')) {
			if (ch[ch.length - 1] == ')') {
				find--;
				if (find == 0)
					result = true;
				else 
					return false;
			}
		} else {
			return false;
		}
		return true;
	}

	 public static void main(String[] args) {
	 // TODO Auto-generated method stub
	 Verify vf = new Verify("(a*|b)*");
	 System.out.println(vf.validation());
	 }
}
