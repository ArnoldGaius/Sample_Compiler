package Analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DFAtoMFA {

	String[] temp;
	String[] end;
	String[] sign;

	public DFAtoMFA(String dfatext, String starttemp, String endtemp,
			String signstring) {
		temp = dfatext.split("\t|\r\n");
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
		// for (int i = 0; i < sign.length; i++) {
		// System.out.println(sign[i]);
		// }
	}

	Object[] mfaarray = new Object[30];// 存放划分的每个数组信息
	ArrayList<Node> dfanode = new ArrayList<Node>();// 存放从DFA文本框读取的节点信息
	ArrayList<Node> mfanode = new ArrayList<Node>();// 存放从MFA文本框读取的节点信息
	String allnode[] = new String[10];
	int num = 2;
	// String dfatext = "0\ta\t1\n0\tb\t2\n1\tb\t3\n";
	// String[] temp ;
	// String start = "0";
	String start = "";

	// String[] end = { "2", "3" };
	public String tcstring = "";
	public String mfastartstr = "开始状态集:";
	public String mfaendstr = "终结状态集:";
	public String signstring = "符号集:";

	private void mfainit() {
		// String[] sign = { "a", "b" };
		// for (int i = 0; i < temp.length; i++) {
		// System.out.println(temp[i]);
		// }
		for (int i = 0; (i + 2) < temp.length;) {
			dfanode.add(new Node(temp[i], temp[i + 1], temp[i + 2]));
			i = i + 3;
		}
		// System.out.println(dfanode);
		nodenum();
		ArrayList<String> mfalist = new ArrayList<String>();
		for (int i = 0; i < end.length; i++) {
			mfalist.add(end[i]);
		}
		mfaarray[1] = mfalist;
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; (allnode[i] != null) && i < allnode.length; i++) {
			if (!mfalist.contains(allnode[i])) {
				al.add(allnode[i]);
			}
		}
		mfaarray[0] = al;
	}

	@SuppressWarnings("unchecked")
	public void display() {
		convert();
		String dfastart;
		String dfarecive;
		String dfaend;
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < mfaarray.length; i++) {
			al = (ArrayList<String>) mfaarray[i];
			if (al != null) {
				Collections.sort(al);
			}
			// System.out.println(al);
		}
		Node nodetemp1;
		Node nodetemp2;
		int findtemp = 0;
		for (int i = 0; i < dfanode.size(); i++) {
			Node node = dfanode.get(i);
			dfastart = node.getStart();
			dfarecive = node.getRecive();
			dfaend = node.getEnd();
			for (int j = 0; j < mfaarray.length; j++) {
				al = (ArrayList<String>) mfaarray[j];
				if (al != null) {
					if (al.contains(dfastart)) {
						dfastart = al.get(0);
					}
					if (al.contains(dfaend)) {
						dfaend = al.get(0);
					}
				}
			}
			findtemp = 0;
			nodetemp1 = new Node(dfastart, dfarecive, dfaend);// (a*|b)*
			Iterator<Node> it = mfanode.iterator();
			while (it.hasNext()) {
				nodetemp2 = it.next();
				if ((nodetemp2.getStart().equals(nodetemp1.getStart()))
						&& (nodetemp2.getRecive().equals(nodetemp1.getRecive()))
						&& (nodetemp2.getEnd().equals(nodetemp1.getEnd()))) {
					findtemp = 1;
					break;
				}
			}
			if (findtemp == 0) {
				mfanode.add(nodetemp1);
			}
		}
		// System.out.println("hello");
		Iterator<Node> itn = mfanode.iterator();
		while (itn.hasNext()) {
			Node node = itn.next();
			tcstring += node + "\r\n";
		}
		// System.out.println(dfanode);
		mfastartstr += start + ";";
		// int find=0;
		if (end.length == 1) {
			mfaendstr += end[0] + ";";
		} else {
			for (int i = 0; i < end.length - 1; i++) {
				// find=0;
				for (int j = i + 1; j < end.length; j++) {
					for (int j2 = 0; j2 < mfaarray.length; j2++) {
						ArrayList<String> al1 = (ArrayList<String>) mfaarray[j2];
						if (al1 != null) {
							Collections.sort(al1);
							if (al1.contains(end[i]) && al1.contains(end[j])) {
								mfaendstr += al1.get(0) + ";";
							}
						}
					}
				}
			}
		}
		// System.out.println(tcstring);
		// System.out.println(mfastartstr);
		// System.out.println(mfaendstr);
	}

	@SuppressWarnings("unchecked")
	public void convert() {
		mfainit();
		String dfastart1;
		String dfarecive1;
		String dfaend1;
		String dfastart2;
		String dfarecive2;
		String dfaend2;

		String receive1 = "";
		String receive2 = "";
		String end1 = "";
		String end2 = "";
		int temp1 = -1;
		int temp2 = -1;
		for (int i = 0; mfaarray[i] != null && i < mfaarray.length; i++) {
			Iterator<String> it1 = ((ArrayList<String>) mfaarray[i]).iterator();
			while (it1.hasNext()) {
				String arr1 = it1.next();
				ArrayList<String> al2 = (ArrayList<String>) mfaarray[i];
				Iterator<String> it2 = al2.iterator();
				ArrayList<String> l1 = new ArrayList<String>();
				while (it2.hasNext()) {
					String arr2 = it2.next();
					if (!arr1.equals(arr2)) {
						Iterator<Node> tn1 = dfanode.iterator();
						while (tn1.hasNext()) {
							Node node1 = tn1.next();
							dfastart1 = node1.getStart();
							dfarecive1 = node1.getRecive();
							dfaend1 = node1.getEnd();
							Iterator<Node> tn2 = dfanode.iterator();
							while (tn2.hasNext()) {
								Node node2 = tn2.next();
								dfastart2 = node2.getStart();
								dfarecive2 = node2.getRecive();
								dfaend2 = node2.getEnd();
								if (arr1.equals(dfastart1)) {
									receive1 = dfarecive1;
									end1 = dfaend1;
								}
								if (arr2.equals(dfastart2)) {
									receive2 = dfarecive2;
									end2 = dfaend2;
								}
								if ((receive1.equals(receive2))
										&& (!receive1.equals(""))
										|| (!receive2.equals(""))) {
									for (int j = 0; j < mfaarray.length; j++) {
										ArrayList<String> it = (ArrayList<String>) mfaarray[j];
										if (it != null) {
											if (it.contains(end1)) {
												temp1 = j;
											}
											if (it.contains(end2)) {
												temp2 = j;
											}
											if (temp1 != -1
													&& temp2 != -1
													&& ((temp1 != temp2) || ((temp1 == temp2) && (temp1 != i)))) {
												l1.add(arr2);
											}
											temp1 = -1;
											temp2 = -1;
										}
									}
								}
							}
							receive1 = "";
							receive2 = "";
							end1 = "";
							end2 = "";
						}
					}
				}
				mfaarray[num++] = l1;
				ArrayList<String> alt = (ArrayList<String>) mfaarray[num - 1];
				for (int j = 0; j < mfaarray.length - 1; j++) {
					ArrayList<String> alt1 = (ArrayList<String>) mfaarray[j];
					if (alt1 != null) {
						alt1.removeAll(alt);
					}
				}
			}
		}
	}

	// 整合DFA中包含的所有节点
	private void nodenum() {
		String dfastart;
		String dfaend;
		Iterator<Node> it = dfanode.iterator();
		int k = 0;
		int find = 0;
		while (it.hasNext()) {
			Node node = it.next();
			dfastart = node.getStart();
			dfaend = node.getEnd();
			find = 0;
			for (int i = 0; (i < allnode.length) && (allnode[i] != null); i++) {
				if (dfastart.equals(allnode[i])) {
					find = 1;
					break;
				}
			}
			if (find == 0) {
				allnode[k++] = dfastart;
			}
			find = 0;
			for (int i = 0; i < allnode.length; i++) {
				if (dfaend.equals(allnode[i])) {
					find = 1;
					break;
				}
			}
			if (find == 0) {
				allnode[k++] = dfaend;
			}
		}
	}

	// public static void main(String[] args) {
	// DFAtoMFA dm = new DFAtoMFA();
	// dm.display();
	// }
}
