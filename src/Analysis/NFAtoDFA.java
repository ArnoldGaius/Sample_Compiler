package Analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

public class NFAtoDFA {

	String temp[];
	String[] end;
	String[] sign;

	public NFAtoDFA(String nfatext, String starttemp, String endtemp,
			String signstring) {
		temp = nfatext.split("\t|\r\n");// (a*|b)*
		start = starttemp.split("[;:]")[1];
		String[] str_temp = endtemp.split("[;:]");
		end = new String[str_temp.length - 1];
		for (int i = 1; i < str_temp.length; i++) {
			end[i - 1] = str_temp[i];
		}
		String[] sign_temp = signstring.split("[;:]");
		sign = new String[sign_temp.length - 1];
		for (int i = 1; i < sign_temp.length; i++) {
			sign[i - 1] = sign_temp[i];
		}
	}

	ArrayList<Node> nfanode = new ArrayList<Node>();// 存储NFA中的节点信息
	ArrayList<Node> dfanode = new ArrayList<Node>();// 存储DFA中的节点信息
	ArrayList<String> movenode = new ArrayList<String>();
	Object[][] statuearray = new Object[100][2];// 存储T以及T中状态
	// String start = "7";
	String start = "";
	// String[] end ;
	// String[] end = {"8"};
	// String[] sign = { "a", "b" };
	public String tbstring = "";
	public String startstring = "开始状态集:";
	public String endstring = "终结状态集:";
	public String signstring = "符号集:";

	// 初始化数据
	public void nfainit() {
		// String nfatext =
		// "1\ta\t2\n3\tb\t4\n2\t#\t3\n5\tb\t6\n7\t#\t5\n7\t#\t1\n6\t#\t8\n4\t#\t8\n";
		// temp = nfatext.split("[\t\n]");
		// System.out.println(temp[0]);
		for (int i = 0; (i + 2) < temp.length;) {
			nfanode.add(new Node(temp[i], temp[i + 1], temp[i + 2]));
			i = i + 3;
		}
		for (int i = 0; i < sign.length; i++) {
			signstring = signstring + sign[i] + ";";
		}
	}

	public void display() {
		firstnode();
		String dfastart = "";
		String dfaend = "";
		String start1 = "";
		String end1 = "";
		Iterator<Node> dit = dfanode.iterator();
		while (dit.hasNext()) {
			Node node = dit.next();
			dfastart = node.getStart();
			dfaend = node.getEnd();
			tbstring += node + "\r\n";
			int finda = 0;
			int findb = 0;
			int find = 0;
			start1 = "";
			end1 = "";
			Iterator<Node> it1 = dfanode.iterator();
			while (it1.hasNext()) {
				Node node1 = it1.next();
				start1 = node1.getStart();
				end1 = node1.getEnd();
				if (dfastart.equals(end1)) {
					finda = 1;
					break;
				}
				if (dfaend.equals(start1)) {
					findb = 1;
					break;
				}
			}
			if (finda == 0) {
				find = 0;
				char[] ch = startstring.toCharArray();
				for (int i = 0; i < ch.length; i++) {
					if (dfastart.equals(String.valueOf(ch[i]))) {
						find = 1;
						break;
					}
				}
				if (find == 0) {
					startstring = startstring + dfastart + ";";
				}
			}
			if (findb == 0) {
				find = 0;
				char[] ch = endstring.toCharArray();
				for (int i = 0; i < ch.length; i++) {
					if (dfaend.equals(String.valueOf(ch[i]))) {
						find = 1;
						break;
					}
				}
				if (find == 0) {
					endstring = endstring + dfaend + ";";
				}
			}
		}
		// System.out.println(tbstring);
		// System.out.println(startstring);
		// System.out.println(endstring);
	}

	@SuppressWarnings("unchecked")
	public void convert(int statue) {
		String temp = "";
		for (int i = 0; (statue <= statuearray.length)
				&& (i < statuearray.length) && (statuearray[i][1] != null)
				&& ((Boolean) statuearray[i][1] == false); i++) {
			// 标记T
			statuearray[i][1] = true;
			// 循环每个输入字母
			for (int j = 0; j < sign.length; j++) {
				temp = move(statuearray[i], sign[j]);
				if (!temp.equals("")) {
					ArrayList<String> listtemp = new ArrayList<String>();
					ArrayList<String> statuetemp = new ArrayList<String>();
					listtemp = closure(temp);
					int find = 0;
					for (int k = 0; k < statuearray.length && k < statue + 1; k++) {
						statuetemp = (ArrayList<String>) statuearray[k][0];
						if (statuetemp != null) {
							// 排序
							Collections.sort(statuetemp);
							Collections.sort(listtemp);
							if (statuetemp.equals(listtemp)) {
								find = 1;
								dfanode.add(new Node(String.valueOf(i),
										sign[j], String.valueOf(k)));
							}
						}
					}
					if (find == 0) {
						statue++;
						if (statue < statuearray.length) {
							statuearray[statue][0] = listtemp;
							statuearray[statue][1] = false;
							dfanode.add(new Node(String.valueOf(i), sign[j],
									String.valueOf(statue)));
						}
					}
				}
			}
		}
	}

	public void firstnode() {
		nfainit();
		Stack<String> scstr = new Stack<String>();
		String scstrtemp = "";
		String nfastart;
		String nfarecive;
		String nfaend;
		int statue = 0;// 标记T的状态序号
		ArrayList<String> listtemp = new ArrayList<String>();
		scstr.push(start);
		while (!scstr.isEmpty()) {
			scstrtemp = scstr.pop();
			Iterator<Node> nfait = nfanode.iterator();
			while (nfait.hasNext()) {
				Node nfanode = nfait.next();
				nfastart = nfanode.getStart();
				nfarecive = nfanode.getRecive();
				nfaend = nfanode.getEnd();
				if (nfastart.equals(scstrtemp) && nfarecive.equals("#")) {
					if (!listtemp.contains(nfaend)) {
						listtemp.add(nfaend);
						scstr.push(nfaend);
					}
				}
			}
		}
		listtemp.add(start);
		// System.out.println(closurenode);
		statuearray[statue][0] = listtemp;
		statuearray[statue][1] = false;
		// closurenode.clear();
		// System.out.println(statuearray[statue][0]);
		// statuearray中存在尚未标记的子集
		convert(statue);
	}

	public ArrayList<String> closure(String temp) {
		Stack<String> scstr = new Stack<String>();
		char[] ch = temp.toCharArray();
		String nfastart;
		String nfarecive;
		String nfaend;
		String scstrtemp = "";
		// String closuretemp="";
		ArrayList<String> listtemp = new ArrayList<String>();
		for (int i = 0; i < ch.length; i++) {
			scstr.push(String.valueOf(ch[i]));
			while (!scstr.isEmpty()) {
				scstrtemp = scstr.pop();
				Iterator<Node> nfait = nfanode.iterator();
				while (nfait.hasNext()) {
					Node nfanode = nfait.next();
					// System.out.println(node);
					nfastart = nfanode.getStart();
					nfarecive = nfanode.getRecive();
					nfaend = nfanode.getEnd();
					if (nfastart.equals(scstrtemp) && nfarecive.equals("#")) {
						if (!listtemp.contains(nfaend)) {
							listtemp.add(nfaend);
							scstr.push(nfaend);
						}
					}
				}
			}
		}
		for (int i = 0; i < ch.length; i++) {
			if (!listtemp.contains(String.valueOf(ch[i]))) {
				listtemp.add(String.valueOf(ch[i]));
			}
		}
		// System.out.println("---------" + listtemp);
		return listtemp;
	}

	@SuppressWarnings("unchecked")
	public String move(Object[] array, String fuhao) {
		String movetemp = "";// 对于每一个符号经move运算后所得结果
		String movestart;
		String moverecive;
		String moveend;
		Iterator<String> moveit = ((ArrayList<String>) array[0]).iterator();
		// 迭代每个T中的元素
		while (moveit.hasNext()) {
			String nfamove = moveit.next();
			// 迭代NFA中的节点信息
			Iterator<Node> nfait = nfanode.iterator();
			while (nfait.hasNext()) {
				Node nfanode = nfait.next();
				// System.out.println(node);
				movestart = nfanode.getStart();
				moverecive = nfanode.getRecive();
				moveend = nfanode.getEnd();
				if (movestart.equals(nfamove) && moverecive.equals(fuhao)) {
					movetemp = movetemp + moveend;
				}
			}
		}
		return movetemp;
	}

	 public static void main(String[] args) {
	 NFAtoDFA nd = new NFAtoDFA("","","","");
	 nd.display();
	 }
}
