package Analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class ExpressiontoNFA {

	char[] ch;

	public ExpressiontoNFA(String expression) {
		ch = expression.toCharArray();
	}

	ArrayList<Node> nfaNode = new ArrayList<Node>();
	Stack<String> startStack = new Stack<String>();
	Stack<String> endStack = new Stack<String>();
	public String tastring = "";
	public String nfastartstr = "开始状态集:";
	public String nfaendstr = "终结状态集:";
	public String signstring = "符号集:";
	int index = 0;

	public void init() {
		// String expression = "(a*|b)*";// (a*|b)*
		// ch = expression.toCharArray();
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] >= 'a' && ch[i] <= 'z') {
				if (!al.contains(String.valueOf(ch[i]))) {
					al.add(String.valueOf(ch[i]));
				}
			}
		}
		Iterator<String> it = al.iterator();
		while (it.hasNext()) {
			signstring += it.next() + ";";
		}
	}

	public void display() {
		convert();
		Iterator<Node> it = nfaNode.iterator();
		while (it.hasNext()) {
			Node node = it.next();
			tastring += node + "\r\n";
		}
		if (!startStack.isEmpty()) {
			nfastartstr += startStack.peek() + ";";
		}
		if (!endStack.isEmpty()) {
			nfaendstr += endStack.peek() + ";";
		}
		 System.out.println(tastring);
		 System.out.println(signstring);
		 System.out.println(nfastartstr);
		 System.out.println(nfaendstr);
	}

	public void convert() {
		init();
		String startNode = "";
		String recivenode = "";
		String endNode = "";
		String tempstack = "";
		String nodetemp = "";
		String endtemp = "";
		int find = 0;//
		for (int i = 0; i < ch.length; i++) {
			while (i < ch.length && ch[i] >= 'a' && ch[i] <= 'z') {
				startNode = String.valueOf(index++);
				recivenode = String.valueOf(ch[i]);
				endNode = String.valueOf(index++);
				nfaNode.add(new Node(startNode, recivenode, endNode));
				startStack.add(startNode);
				while (!endStack.isEmpty()) {
					find = 1;
					tempstack = endStack.pop();
					nfaNode.add(new Node(tempstack, "#", startStack.peek()));
				}
				i++;
				if (find == 1) {
					startStack.pop();
					find = 0;
				}
				endStack.add(endNode);
			}
			endtemp = endNode;
			if (i < ch.length && ch[i] == '|') {
				// find=1;
				i++;
				while (i < ch.length && ch[i] >= 'a' && ch[i] <= 'z') {
					startNode = String.valueOf(index++);
					recivenode = String.valueOf(ch[i]);
					endNode = String.valueOf(index++);
					nfaNode.add(new Node(startNode, recivenode, endNode));
					startStack.add(startNode);
					i++;
					endStack.add(endNode);
				}
				// while 将第二部分中的每个节点连接起来
				nodetemp = endStack.pop();
				while (!endStack.peek().equals(endtemp)) {
					endNode = endStack.pop();
					startNode = startStack.pop();
					nfaNode.add(new Node(endNode, "#", startNode));
				}
				endStack.push(nodetemp);
				//新生成开始节点->连接之前的开始节点->新生成结束节点->连接之前的结束节点
				startNode = String.valueOf(index++);
				while (!startStack.isEmpty()) {
					tempstack = startStack.pop();
					nfaNode.add(new Node(startNode, "#", tempstack));
				}
				startStack.push(startNode);
				endNode = String.valueOf(index++);
				while (!endStack.isEmpty()) {
					tempstack = endStack.pop();
					nfaNode.add(new Node(tempstack, "#", endNode));
				}
				endStack.push(endNode);
			}
			if (i < ch.length && ch[i] == '*') {
				nfaNode.add(new Node(endStack.peek(), "#", startStack.peek()));
				startNode = String.valueOf(index++);
				nfaNode.add(new Node(startNode, "#", startStack.pop()));
				startStack.push(startNode);
				endNode = String.valueOf(index++);
				nfaNode.add(new Node(endStack.pop(), "#", endNode));
				endStack.push(endNode);
				nfaNode.add(new Node(startNode, "#", endNode));
			}
		}
	}

	 public static void main(String[] args) {
	 ExpressiontoNFA en = new ExpressiontoNFA("(a|b)");
	 en.display();
	 }

}
