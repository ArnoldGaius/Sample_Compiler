package YYtest;

import java.util.ArrayList;
import java.util.HashMap;

public class test {
		public static void main(String[] args){
			ArrayList<Word> m  = new ArrayList<Word>();
			//²âÊÔ 1 ²âÊÔif 
			
//			m.add(new Word(8, "if"));
//			m.add(new Word(34, "a"));
//			m.add(new Word(25, "-"));
//			m.add(new Word(34, "c"));
//			m.add(new Word(29, ">"));
//			m.add(new Word(34, "b"));
//			m.add(new Word(21, "and"));
//			m.add(new Word(34, "b"));
//			m.add(new Word(29, ">"));
//			m.add(new Word(35, "3"));
//			m.add(new Word(9, "then"));
//			m.add(new Word(34, "c"));
//			m.add(new Word(54, ":="));
//			m.add(new Word(34, "b"));
//			m.add(new Word(26, "*"));
//			m.add(new Word(34, "c"));
//			m.add(new Word(24, "+"));
//			m.add(new Word(34, "c"));
//			m.add(new Word(40, ";"));
			
			//********************************************
			//²âÊÔ 2 ²âÊÔ for
//			m.add(new Word(13, "for"));
//			m.add(new Word(34, "a"));
//			m.add(new Word(54, ":="));
//			m.add(new Word(35, "3"));
//			m.add(new Word(24, "+"));
//			m.add(new Word(34, "c"));
//			m.add(new Word(14, "to"));
//			m.add(new Word(35, "3"));
//			m.add(new Word(12, "do"));
//			m.add(new Word(34, "b"));
//			m.add(new Word(54, ":="));
//			m.add(new Word(35, "3"));
//			m.add(new Word(40, ";"));
			
			//²âÊÔ while
//			m.add(new Word(11, "while"));
//			m.add(new Word(34, "a"));
//			m.add(new Word(29, ">"));
//			m.add(new Word(34, "b"));
//			m.add(new Word(12, "do"));
//			m.add(new Word(34, "c"));
//			m.add(new Word(54, ":="));
//			m.add(new Word(35, "5"));
//			m.add(new Word(40, ";"));
			
			//²âÊÔ repeat
//			m.add(new Word(11, "while"));
//			m.add(new Word(34, "a"));
//			m.add(new Word(29, ">"));
//			m.add(new Word(34, "b"));
//			m.add(new Word(12, "do"));
//			m.add(new Word(34, "c"));
//			m.add(new Word(54, ":="));
//			m.add(new Word(35, "5"));
//			m.add(new Word(40, ";"));
			
			semTest g=new semTest(m);
			HashMap<String,String> var = new HashMap<String, String>(); 
			var.put("a", "integer");
			var.put("b", "integer");
			var.put("c", "integer");
			g.setVar(var);
			g.run();
		}
}
