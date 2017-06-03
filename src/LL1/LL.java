package LL1;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import LL1V2.First;

public class LL {
	private static List<String> prt = new ArrayList<String>();
	private static List<FF> first = new ArrayList<FF>();
	private static List<FF> follow = new ArrayList<FF>();
	private static List<TableE> table = new ArrayList<TableE>();
	private static List<String> nonSymbol = new ArrayList<String>();
	private static List<String> terminalSymbol = new ArrayList<String>();
	public String firstString = new String();
	public String followString = new String();

	public static void main(String[] args) {
		LL l = new LL();
		l.read();// 从文件读取文法表达式并且对读到的表达式进行预处理
		System.out.println("读入文法分析分解后为：");
		l.printP();
		System.out.println("非终结符集:");
		for(String str:nonSymbol){
			System.out.print(str+"\t");
		}
		System.out.println();
		System.out.println("终结符集:");
		for(String str:terminalSymbol){
			System.out.print(str+"\t");
		}
		l.FindFirstP();
		List<FF> list_copy = new ArrayList<FF>();
		do{
			list_copy.clear();
			list_copy.addAll(first);
			l.FindFirstS();
		}
		while(!list_copy.equals(first));

		do{
			list_copy.clear();
			list_copy.addAll(follow);
			l.findFollowP();
		}
		while(!list_copy.equals(follow));
		System.out.println("first集：");
		l.firstString=l.printF(first);
		System.out.println("follow集：");
		l.followString=l.printF(follow);
		l.createTable();// 创建LL(1)表
		l.printT();// 打印表
	}
	public List<String> getNonSymbol(){
		return nonSymbol;
	}
	public List<String> getTerminalSymbol(){
		return terminalSymbol;
	}
	
	public void run(){
		System.out.println("读入文法分析分解后为：");
		printP();
		System.out.println("非终结符集:");
		for(String str:nonSymbol){
			System.out.print(str+"\t");
		}
		System.out.println();
		System.out.println("终结符集:");
		for(String str:terminalSymbol){
			System.out.print(str+"\t");
		}
		System.out.println();
		FindFirstP();
		List<FF> list_copy = new ArrayList<FF>();
		do{
			list_copy.clear();
			list_copy.addAll(first);
			FindFirstS();
		}
		while(!list_copy.equals(first));

		do{
			list_copy.clear();
			list_copy.addAll(follow);
			findFollowP();
		}
		while(!list_copy.equals(follow));

		System.out.println("first集：");
		firstString=printF(first);
		System.out.println("follow集：");
		followString=printF(follow);

		String temp = terminalSymbol.get(0);
		terminalSymbol.set(0, "预测表");
		terminalSymbol.add(temp);
		createTable();// 创建LL(1)表
		getCellData();
	}
	
	public void init(){
		prt = new ArrayList<String>();
		first = new ArrayList<FF>();
		follow = new ArrayList<FF>();
		table = new ArrayList<TableE>();
		nonSymbol = new ArrayList<String>();
		terminalSymbol = new ArrayList<String>();
		firstString = new String();
		followString = new String();
	}
	
	public void read_from_JTextArea(String text){
		init();
		if(text!=null){
			String[] lineString = text.split("\n");
			for(String str : lineString)
				pretreatment(str); // 调用预处理方法
		}
	}

	public void read() {// 读取文件函数
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(".\\LL1_1.txt"));
			System.out.println("读入的文法是：");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				pretreatment(line); // 调用预处理方法
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (br != null) {
				br.close();
				br = null;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void pretreatment(String line) {// 对输入字符进行预处理。分割 形如
		// A->B|a的表达式为A->B和A->a
		Pattern p = Pattern
				.compile("([A-Z]?[']?)([-]?[>]?)([\\w\\W &&[^|]]*)([|]?)");// 重复问题。
		Matcher m = p.matcher(line);
		String emp = "";
		boolean flag = false;

		do {
			flag = false;
			// boolean b=m.find();
			// System.out.println("find "+b);
			if (m.find()) {
				String str = "";
				if (m.group(1).length() > 0 && m.group(2).length() > 0) {
					emp = m.group(1);
					str = emp + "->" + m.group(3);// 解决正则表达式问题。第二次寻找时组1和组3有交叉的问题
				} else
					str = emp + "->" + m.group(1) + m.group(2) + m.group(3);

				// System.out.println(m.group(3)+" "+m.group(4)+" "+m.group(3).length());
				prt.add(str);

				if (m.group(4).length() > 0)
					flag = true;
				// System.out.println("flag "+flag);
			}

		} while (flag);

	}

	public void printP() {// 打印全部的文法表达式
		Iterator<String> it = prt.iterator();
		while (it.hasNext()) {
			String str = it.next();
			System.out.println(str);
			String nonterminal_terminal [] = str.split("->");
			if(!nonSymbol.contains(nonterminal_terminal[0]))
				nonSymbol.add(nonterminal_terminal[0]);
		}
		it = prt.iterator();
		while (it.hasNext()) {
			String str = it.next();
			String[] nonterminal_terminal  = str.split("->")[1].split("");
			for (String ch : nonterminal_terminal){
				if(!nonSymbol.contains(ch)&&ch.length()>0&&!terminalSymbol.contains(ch))
					terminalSymbol.add(String.valueOf(ch));
			}
		}
		if(terminalSymbol.contains("ε")){
			terminalSymbol.set(terminalSymbol.indexOf("ε"), "#");
		}
	}

	public String printF(List<FF> first) {// 打印全部first或follow集
		Iterator<FF> it = first.iterator();
		String str = new String();
		while (it.hasNext()) {
			FF ft = it.next();
			str+=ft.print();
		}
		return str;
	}

	public void FindFirstP() {// 进行找first集的第一步操作。
		Iterator<String> it = prt.iterator();
		Pattern p = Pattern.compile("([A-Z][']?)(->)([\\w\\W&&[^A-Z]]*)([.]*)");
		while (it.hasNext()) {
			Matcher m = p.matcher(it.next());
			if (m.find() && m.group(3).length() > 0) {
				// System.out.println(m.group(1));
				if (!cmpAdd(m.group(1), m.group(3), first)) {
					FF fc = new FF(m.group(1));
					// System.out.println(m.group(1));
					fc.addf(m.group(3));
					first.add(fc);
				}

			}
		}
	}

	public void FindFirstS() {// 进行查找first集的进一步操作。
		Iterator<String> it = prt.iterator();
		while (it.hasNext()) {
			String str = it.next();
			Pattern p = Pattern.compile("([A-Z][']?)(->)([A-Z][']?)");
			Matcher m = p.matcher(str);
			if (str.matches("[A-Z][']?->[[A-Z][']?]+")) {// 如果表达式符合Y->Y1Y2Y3......Yk的形式则执行
				p = Pattern.compile("([A-Z]?[']?)([-]?[>]?)([A-Z][']?)");
				m = p.matcher(str);
				String emp = "";
				boolean flag;
				do {
					flag = false;
					if (m.find()) {
						FF fc, ff;
						if (m.group(1).length() > 0) {
							emp = m.group(1);
						}
						ff = findE(emp, first);
						if (m.group(3).length() > 0) {
							fc = findE(m.group(3), first);
							if (fc.conteinZero()) {// 如果Yi的first集中含有ε，直接将Yi的first集中的内容加到ff中
								flag = true;
								ff.addf(fc);
							} else {// 如果Yi的first集中不含有ε，直接将Yi的first集中的内容加到ff中，并且取出加入的ε
								ff.addf(fc);
								ff.reMove('ε');// 因为只要前面的都为有ε即可，最后取到一个可以不含ε

							}
						}
					}
				} while (flag);
			} else if (m.find()) {// 如果表达式不符合Y->Y1Y2Y3......Yk的形式则执行
				// System.out.println(m.group(1)+" "+m.group(3));
				FF f1 = findE(m.group(1), first);
				FF f2 = findE(m.group(3), first);
				f1.addf(f2.exceptZore());
			}
		}
	}

	private FF findE(String name, List<FF> first) {// 在找到A的first/follow集中的集合。
		Iterator<FF> it = first.iterator();
		FF ft = null;
		while (it.hasNext()) {
			ft = it.next();
			if (ft.getName().equals(name)) {
				return ft;
			}
		}
		ft = new FF(name);
		first.add(ft);
		return ft;
	}

	private boolean cmpAdd(String name, String str, List<FF> first) {// 把新找到的A的First/Follow集中元素加入到其相应的集中。
		Iterator<FF> it = first.iterator(); // 加入成功返回true,否则返回false;
		while (it.hasNext()) {
			FF ft = it.next();
			if (ft.getName().equals(name)) {
				ft.addf(str);
				return true;
			}
		}
		return false;
	}

	private void findFollowP() {// 创建follow集
		Pattern p = Pattern
				.compile("([A-Z]?[']?)([-]?[>]?)([\\w\\W &&[^A-Z]]*)([[A-Z][']?]*)([\\w\\W &&[^A-Z]]*)");
		Iterator<String> it = prt.iterator();
		Matcher m = null;
		boolean IsFirst = true;
		while (it.hasNext()) {
			String wf = it.next();
			m = p.matcher(wf);
			boolean match = false;

			String ename = "";
			String emp = "";
			do {
				match = m.find();
				if (match) {
					emp = emp + m.group();// 获取到现在为止获取到的wf中的字符。
					if (m.group(1).length() > 0) {
						ename = m.group(1);
					}
					// System.out.println(emp+" "+wf.length()+" "+emp.length()+" "+m.group(4));
					FF fc = findE(ename, follow);
					if (IsFirst) {
						fc.addf('#');// 文法开始符处理与其他非终结符处理的不同之处。
						IsFirst = false;
					}
					if (wf.length() == emp.length()) { // 将所取到的字符相加得到总字符长度与原字符长度比较即可
						if (m.group(5).length() > 0) {
							// System.out.println(m.group(5));
							String emp5 = m.group(5);
							String en = "";
							Pattern p1 = Pattern.compile("([A-Z][']?)");
							Matcher m1 = p1.matcher(m.group(4));
							while (m1.find()) {
								en = m1.group();
							}
							if (en.length() > 0) {
								FF f = findE(en, follow);
								f.addf(emp5);
							}

						} else if (m.group(4).length() > 0) {
							Pattern p1 = Pattern.compile("([A-Z][']?)");
							Matcher m1 = p1.matcher(m.group(4));
							List<String> lT1 = new ArrayList<String>();
							List<String> lT2 = new ArrayList<String>();
							while (m1.find()) {
								// System.out.println(emp+" "+m.group(4)+" "+m1.group());
								lT1.add(m1.group());
								lT2.add(m1.group());
							}
							Iterator<String> it1 = lT1.iterator();
							while (it1.hasNext()) {
								String name1 = it1.next();
								// System.out.println(" "+name1);
								String name2 = "";
								Iterator<String> it2 = lT2.iterator();

								while (it2.hasNext()) {
									name2 = it2.next();
									// System.out.println(name2+" "+name1);
									if (name2.equals(name1))
										break;
								}
								FF flw = findE(name1, follow);
								boolean flage = true;
								while (it2.hasNext()) {// 必须是紧紧跟随在E之后的终结符才算follow集中
									name2 = it2.next();
									// System.out.println(name2+" "+name1);
									FF fst = findE(name2, first);
									flw.addf(fst.exceptZore());
									if (!fst.conteinZero()) {
										flage = false;
										break;// 重要，否则，将会导致程序失败。使如A->BCD形式的表达式，
										// 含ε时，仍旧将D的first集中的内容加入到了B的follow集中
									}
								}
								if (flage) {// 完成A->aBb形式，将A的follow集加到B的follow集中。
									FF main = findE(ename, follow);
									flw.addf(main);
								}
							}
						}
						match = false;
					} else if (m.group(5).length() > 0) {
						// System.out.println(m.group(5));
						String emp5 = m.group(5);
						String en = "";
						Pattern p1 = Pattern.compile("([A-Z][']?)");
						Matcher m1 = p1.matcher(m.group(4));
						while (m1.find()) {
							en = m1.group();
						}
						if (en.length() > 0) {
							FF f = findE(en, follow);
							f.addf(emp5);
						}
					}

				}

			} while (match);

		}
	}

	private void createTable() {// 创建ll(1)表
		Iterator<String> it = prt.iterator();
		Pattern p = Pattern.compile("([A-Z][']?)(->)([\\w\\W&&[^A-Z]]?)");
		Matcher m = null;
		while (it.hasNext()) {
			String exp = it.next();
			m = p.matcher(exp);
			boolean b = m.find();
			// System.out.println(b);
			if (b && m.group(1).length() > 0) {
				// System.out.println(m.group(1));
				FF ft = this.findE(m.group(1), first);
				if (IsSingle(m.group(1) + m.group(2))) {// 只用m.group(1)时，会造成E与
					// E'匹配产生错误。丢失表项
					// System.out.println(m.group(1));
					String collect = ft.exceptZore().getCollect();
					int length = collect.length();
					for (int i = 0; i < length; i++) {
						TableE t = new TableE(m.group(1), collect.charAt(i),
								exp);
						if (!isHas(t))
							table.add(t);
					}
				} else if (m.group(3).length() > 0) {
					// System.out.println(m.group(1)+" "+m.group(3));
					// FF ft=this.findE(m.group(1), first);
					if (m.group(3).charAt(0) != 'ε') {
						// System.out.println(m.group(1));
						if (ft.conteinChar(m.group(3).charAt(0))) {
							// System.out.println(m.group(1));
							TableE t = new TableE(m.group(1), m.group(3)
									.charAt(0), exp);
							if (!isHas(t))
								table.add(t);
						}
					}
				}

				if (ft.conteinZero()) {
					FF ff = this.findE(m.group(1), follow);
					String collect = ff.exceptZore().getCollect();
					int length = collect.length();
					for (int i = 0; i < length; i++) {
						TableE t = new TableE(m.group(1), collect.charAt(i), m
								.group(1)
								+ "->ε");
						if (!isHas(t))
							table.add(t);
					}
				}

			}
		}
	}

	private boolean isHas(TableE t) {// 判断在表中是不是已经存在了M[A,a],
		Iterator<TableE> it = table.iterator();
		while (it.hasNext()) {
			if (it.next().isEquals(t))
				return true;
		}
		return false;
	}

	private boolean IsSingle(String str) {// 判断表达式 A->a是不是唯一表达式。
		Iterator<String> it = prt.iterator();
		Pattern p = Pattern.compile(str);
		int sign = 0;
		while (it.hasNext()) {
			Matcher m = p.matcher(it.next());
			if (m.find()) {
				sign++;
			}
			if (sign > 1)
				return false;
		}
		return true;
	}
	
	public String[][] getCellData(){
		String[][] cellData = new String[nonSymbol.size()][terminalSymbol.size()];
		for(int i=0;i<nonSymbol.size();i++){
			cellData[i][0]=nonSymbol.get(i);
		}
		Iterator<TableE> it = table.iterator();
		while (it.hasNext()) {
			TableE t = it.next();
			t.print();
			int row = nonSymbol.indexOf(t.getNT());
			int col = terminalSymbol.indexOf(t.getYT());
			System.out.println(String.valueOf(row)+"\t"+String.valueOf(col));
			cellData[row][col]=t.getExp();
		}
		return cellData;
	}

	private void printT() {// 打印整张表格
		Iterator<TableE> it = table.iterator();
		System.out.println("创建的LL(1)分析表：");
		String emp = "";
		while (it.hasNext()) {
			TableE t = it.next();
			String current = t.getNT();
			if (current.equals(emp))
				t.print();
			else {
				System.out.println();
				t.print();
				emp = current;
			}
		}
	}
}
