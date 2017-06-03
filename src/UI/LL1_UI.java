package UI;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import LL1.LL;
import LL1.stepTableNode;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.awt.SystemColor;

public class LL1_UI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LL1_UI window = new LL1_UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LL1_UI() {
		initialize();
	}
	public JFrame getFrame(){
		return this.frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Image icon = Toolkit.getDefaultToolkit().getImage("steam.jpg");
		frame.setIconImage(icon);
		frame.setBounds(100, 100, 911, 560);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(15);
		flowLayout.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JButton openFile_Bt = new JButton("\u6253\u5F00\u6587\u4EF6");
		openFile_Bt.setBackground(SystemColor.menu);
		openFile_Bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
		panel.add(openFile_Bt);
		
		JButton Confirm_Bt = new JButton("\u786E\u8BA4\u6587\u6CD5");
		Confirm_Bt.setBackground(SystemColor.menu);
		Confirm_Bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
		panel.add(Confirm_Bt);
		
		JButton saveFile_Bt = new JButton("\u4FDD\u5B58\u6587\u4EF6");
		saveFile_Bt.setBackground(SystemColor.menu);
		saveFile_Bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
		panel.add(saveFile_Bt);
		
		JButton First_Bt = new JButton("\u6C42First\u96C6");
		First_Bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
		First_Bt.setBackground(SystemColor.menu);
		panel.add(First_Bt);
		
		JButton Follow_Bt = new JButton("\u6C42Follow\u96C6");
		Follow_Bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
		Follow_Bt.setBackground(SystemColor.menu);
		panel.add(Follow_Bt);
		
		JButton CreatAnaTable_Bt = new JButton("\u6784\u9020\u5206\u6790\u8868");
		CreatAnaTable_Bt.setBackground(SystemColor.menu);
		CreatAnaTable_Bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
		panel.add(CreatAnaTable_Bt);
		
		JButton Exit_bt = new JButton("\u9000\u51FA");
		Exit_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		Exit_bt.setBackground(SystemColor.menu);
		Exit_bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 19));
		panel.add(Exit_bt);
		
		JPanel Main_panel = new JPanel();
		frame.getContentPane().add(Main_panel, BorderLayout.CENTER);
		Main_panel.setLayout(new GridLayout(1, 2));
		
		JPanel Left_Panel = new JPanel();
		Left_Panel.setLayout(new GridLayout(3, 1));
		Main_panel.add(Left_Panel);
		
		JPanel Input_Panel = new JPanel();
		Input_Panel.setBorder(BorderFactory.createTitledBorder ("文法输入："));
		Left_Panel.add(Input_Panel);
		Input_Panel.setLayout(new BorderLayout(0, 0));
		
		final JTextArea InputCodeArea = new JTextArea();
		InputCodeArea.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		JScrollPane InputScrollPane = new JScrollPane(InputCodeArea);
		Input_Panel.add(InputScrollPane);
		
		JPanel First_Panel = new JPanel();
		First_Panel.setBorder(BorderFactory.createTitledBorder ("First集："));
		Left_Panel.add(First_Panel);
		final JTextArea FirstArea = new JTextArea();
		FirstArea.setEditable(false);
		FirstArea.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		final JScrollPane FirstScrollPane = new JScrollPane(FirstArea);		
		First_Panel.setLayout(new BorderLayout(0, 0));
		First_Panel.add(FirstScrollPane);
		
		
		
		JPanel Follow_Panel = new JPanel();
		Follow_Panel.setBorder(BorderFactory.createTitledBorder ("Follow集："));
		Left_Panel.add(Follow_Panel);		
		final JTextArea FollowArea = new JTextArea();
		FollowArea.setEditable(false);
		FollowArea.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		Follow_Panel.setLayout(new BorderLayout(0, 0));
		JScrollPane FollowScrollPane = new JScrollPane(FollowArea);
		Follow_Panel.add(FollowScrollPane);

		
		JPanel Right_Panel = new JPanel();
		Right_Panel.setLayout(new GridLayout(2, 1));
		Main_panel.add(Right_Panel);
		
		JPanel AnaTable_Panel = new JPanel();
		AnaTable_Panel.setBorder(BorderFactory.createTitledBorder ("预测分析表"));
		Right_Panel.add(AnaTable_Panel);
		AnaTable_Panel.setLayout(new BorderLayout(0, 0));
		final JScrollPane table_ScrollPane = new JScrollPane();	
		AnaTable_Panel.add(table_ScrollPane);
		
		JPanel Step_Panel = new JPanel();
		Right_Panel.add(Step_Panel);
		Step_Panel.setLayout(new BorderLayout(0, 0));
		
		JPanel InputWords_Panel = new JPanel();
		Step_Panel.add(InputWords_Panel, BorderLayout.NORTH);
		InputWords_Panel.setLayout(new BorderLayout(0, 0));
		
		txtFiled = new JTextField();
		txtFiled.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		txtFiled.setText("i+i");
		txtFiled.setColumns(10);
		InputWords_Panel.add(txtFiled);
		
		JLabel label = new JLabel(" \u8F93\u5165\u5F85\u5206\u6790\u53E5\u5B50: ");
		label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		InputWords_Panel.add(label, BorderLayout.WEST);
		
		JPanel StepSon_Panel = new JPanel();
		Step_Panel.add(StepSon_Panel, BorderLayout.CENTER);
		StepSon_Panel.setLayout(new BorderLayout(0, 0));
		
		JPanel sonBt_Panel = new JPanel();
		StepSon_Panel.add(sonBt_Panel, BorderLayout.NORTH);
		sonBt_Panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		
		JButton showAll_Bt = new JButton("\u4E00\u952E\u663E\u793A");
		showAll_Bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		showAll_Bt.setBackground(SystemColor.menu);
		sonBt_Panel.add(showAll_Bt);
		
		JButton step_Bt = new JButton("\u5355\u6B65\u663E\u793A");
		step_Bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		step_Bt.setBackground(SystemColor.menu);
		sonBt_Panel.add(step_Bt);
		
		JPanel sonTable_Panel = new JPanel();
		StepSon_Panel.add(sonTable_Panel, BorderLayout.CENTER);
		sonTable_Panel.setLayout(new BorderLayout(0, 0));
		
		final JScrollPane StepTableScrollPane = new JScrollPane();
		sonTable_Panel.add(StepTableScrollPane);
		
		/**
		 * 确认文法按钮监听
		 */
		Confirm_Bt.addActionListener(new ActionListener() {
			@SuppressWarnings("serial")
			public void actionPerformed(ActionEvent e) {
				if(InputCodeArea.getText().length()==0){
					JOptionPane.showMessageDialog(frame, "请在文法输入框内输入文法", "ERROR !",JOptionPane.ERROR_MESSAGE);  
					return;
				}
				FirstArea.setText("");;
				FollowArea.setText("");
				LL LL1 = new LL();
				LL1.read_from_JTextArea(InputCodeArea.getText());
				LL1.run();
				LL1_firstString=LL1.firstString;
				LL1_followString=LL1.followString;
//				FirstArea.append(LL1.firstString);
//				FollowArea.append(LL1.followString);
				List<String> terminalSymbol = LL1.getTerminalSymbol();
				String[] columnNames = terminalSymbol.toArray(new String[terminalSymbol.size()]);
				String[][] cellData = LL1.getCellData();
				
				AnaTableColumnNames = LL1.getTerminalSymbol();
				AnaTableRowNames=LL1.getNonSymbol();
				AnaTableCellData = cellData;
				AnaTable = new JTable(new DefaultTableModel(cellData, columnNames) {
					  public boolean isCellEditable(int row, int column) {
				  		    return false;
				  		  }
				  		});
//				table_ScrollPane.setViewportView(AnaTable);
			}
		});
		/**
		 * First集按钮监听
		 */
		First_Bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FirstArea.setText("");;
				FirstArea.append(LL1_firstString);
			}
		});
		/**
		 * Follow集按钮监听
		 */
		Follow_Bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FollowArea.setText("");
				FollowArea.append(LL1_followString);
			}
		});
		/**
		 * 构造分析表
		 */
		CreatAnaTable_Bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_ScrollPane.setViewportView(AnaTable);
			}
		});
		/**
		 * 一键显示按钮
		 */
		showAll_Bt.addActionListener(new ActionListener() {
			@SuppressWarnings("serial")
			public void actionPerformed(ActionEvent e) {
				if(AnaTableCellData==null){
					JOptionPane.showMessageDialog(frame, "请构建预测分析表", "ERROR !",JOptionPane.ERROR_MESSAGE);  
					return;
				}
				if(txtFiled.getText().length()==0){
					JOptionPane.showMessageDialog(frame, "请在待分析句子框内输入待分析句子", "ERROR !",JOptionPane.ERROR_MESSAGE);  
					return;
				}
				int NO=1;
				String[] columnNames = new String[]{"步骤","符号栈","输入串","所用产生式"};
				ArrayList<stepTableNode> stepTable = new ArrayList<stepTableNode>();
				Stack<String> symStack = new Stack<String>();
				symStack.push("#");
				symStack.push(AnaTableCellData[0][0]);
				char []temp = txtFiled.getText().toCharArray();
				LinkedList<String> inputString = new LinkedList<String>();
				for(char ch : temp){
					inputString.add(String.valueOf(ch));
				}
				inputString.add("#");
				stepTableNode headNode = new stepTableNode(NO++, "#"+AnaTableCellData[0][0], txtFiled.getText()+"#", "Initial State");
				stepTable.add(headNode);
				while(true){
					String sym = symStack.peek();
					String nonSym = inputString.peek();
					if(sym.equals(nonSym)){
						if("#".equals(sym)){
							System.out.println("分析完毕!");
							break;
						}
						symStack.pop();
						inputString.pop();
						String symString = "";
						String nonSymString = "";
						for(String str : symStack){
							symString+=str;
						}
						 Iterator<String> inputStringIt = inputString.iterator();
						 while(inputStringIt.hasNext()){
							 nonSymString+=inputStringIt.next();
						 }
						stepTableNode Node = new stepTableNode(NO++, symString, nonSymString, "匹配");
						Node.printStepTableNode();
						stepTable.add(Node);
						continue;
					}
					int row = AnaTableRowNames.indexOf(sym);
					int col = AnaTableColumnNames.indexOf(nonSym);
					String exp = null;
					if(row!=-1&&col!=-1)
						exp= AnaTableCellData[row][col];
					if(exp!=null){
						symStack.pop();
						char [] pushSym = new StringBuffer(exp.split("->")[1]).reverse().toString().toCharArray();
						for(char ch:pushSym){
							if(ch!='ε')
								symStack.push(String.valueOf(ch));
						}
						
						String symString = "";
						String nonSymString = "";
						for(String str : symStack){
							symString+=str;
						}
						 Iterator<String> inputStringIt = inputString.iterator();
						 while(inputStringIt.hasNext()){
							 nonSymString+=inputStringIt.next();
						 }
						stepTableNode Node = new stepTableNode(NO++, symString, nonSymString, exp);
						Node.printStepTableNode();
						stepTable.add(Node);
					}else{
						System.out.println("分析失败!");
						JOptionPane.showMessageDialog(frame, "分析失败,不是该文法的一个句子", "ERROR",JOptionPane.ERROR_MESSAGE);  
						for(stepTableNode Node:stepTable){
							Node.printStepTableNode();
						}
						break;
					}
				}
				
				String[][] StepTableCellData = new String [stepTable.size()][4];
				NO=0;
				for(stepTableNode Node:stepTable){
					StepTableCellData[NO++]=Node.getNodeData();
				}
				
				StepTable = new JTable(new DefaultTableModel(StepTableCellData, columnNames) {
					  public boolean isCellEditable(int row, int column) {
				  		    return false;
				  		  }
				});
				StepTableScrollPane.setViewportView(StepTable);
			}
		});
		/**
		 * 单步显示按钮
		 */
		step_Bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(AnaTableCellData==null){
					JOptionPane.showMessageDialog(frame, "请构建预测分析表", "ERROR !",JOptionPane.ERROR_MESSAGE);  
					return;
				}
				if(txtFiled.getText().length()==0){
					JOptionPane.showMessageDialog(frame, "请在待分析句子框内输入待分析句子", "ERROR !",JOptionPane.ERROR_MESSAGE);  
					return;
				}
				oneStepClickTimes++;
				int NO=1;
				String[] columnNames = new String[]{"步骤","符号栈","输入串","所用产生式"};
				ArrayList<stepTableNode> stepTable = new ArrayList<stepTableNode>();
				Stack<String> symStack = new Stack<String>();
				symStack.push("#");
				symStack.push(AnaTableCellData[0][0]);
				char []temp = txtFiled.getText().toCharArray();
				LinkedList<String> inputString = new LinkedList<String>();
				for(char ch : temp){
					inputString.add(String.valueOf(ch));
				}
				inputString.add("#");
				stepTableNode headNode = new stepTableNode(NO++, "#"+AnaTableCellData[0][0], txtFiled.getText()+"#", "Initial State");
				stepTable.add(headNode);
				while(true){
					String sym = symStack.peek();
					String nonSym = inputString.peek();
					if(sym.equals(nonSym)){
						if("#".equals(sym)){
							System.out.println("分析完毕!");
							break;
						}
						symStack.pop();
						inputString.pop();
						String symString = "";
						String nonSymString = "";
						for(String str : symStack){
							symString+=str;
						}
						 Iterator<String> inputStringIt = inputString.iterator();
						 while(inputStringIt.hasNext()){
							 nonSymString+=inputStringIt.next();
						 }
						stepTableNode Node = new stepTableNode(NO++, symString, nonSymString, "匹配");
						Node.printStepTableNode();
						stepTable.add(Node);
						continue;
					}
					int row = AnaTableRowNames.indexOf(sym);
					int col = AnaTableColumnNames.indexOf(nonSym);
					String exp = null;
					if(row!=-1&&col!=-1)
						exp= AnaTableCellData[row][col];
					if(exp!=null){
						symStack.pop();
						char [] pushSym = new StringBuffer(exp.split("->")[1]).reverse().toString().toCharArray();
						for(char ch:pushSym){
							if(ch!='ε')
								symStack.push(String.valueOf(ch));
						}
						
						String symString = "";
						String nonSymString = "";
						for(String str : symStack){
							symString+=str;
						}
						 Iterator<String> inputStringIt = inputString.iterator();
						 while(inputStringIt.hasNext()){
							 nonSymString+=inputStringIt.next();
						 }
						stepTableNode Node = new stepTableNode(NO++, symString, nonSymString, exp);
						Node.printStepTableNode();
						stepTable.add(Node);
					}else{
						System.out.println("分析失败!");
						JOptionPane.showMessageDialog(frame, "分析失败,不是该文法的一个句子", "ERROR",JOptionPane.ERROR_MESSAGE);  
						for(stepTableNode Node:stepTable){
							Node.printStepTableNode();
						}
						break;
					}
				}
				
				String[][] StepTableCellData = new String [stepTable.size()][4];
				if(oneStepClickTimes>stepTable.size()){
					oneStepClickTimes=0;
					JOptionPane.showMessageDialog(frame, "单步显示完成!再次点击将重新显示", "Finished",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				for(NO=0;NO<oneStepClickTimes;NO++){
					StepTableCellData[NO]=stepTable.get(NO).getNodeData();
				}
				
				StepTable = new JTable(new DefaultTableModel(StepTableCellData, columnNames) {
					  public boolean isCellEditable(int row, int column) {
				  		    return false;
				  		  }
				});
				StepTableScrollPane.setViewportView(StepTable);
			}
		});
		/**
		 * 打开文件按钮
		 */
		openFile_Bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputCodeArea.setText("");
				FirstArea.setText("");
				FollowArea.setText("");
				AnaTable=new JTable();
				StepTable=new JTable();
				JFileChooser jfc = new JFileChooser();
				if(!new File(latestPath).exists())
					latestPath = "C:/";
				else
					jfc = new JFileChooser(latestPath);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt & Gaius", "txt", "Gaius");
				jfc.setFileFilter(filter);// 设定文件选择格式
		        jfc.showOpenDialog(jfc);
		        file= jfc.getSelectedFile();
		        try{
		               if(file != null) {
		            	  latestPath = String.valueOf(file);
	                      FileInputStream in = new FileInputStream(file);
	                      InputStreamReader ipr = new InputStreamReader(in, "UTF-8");
	                      BufferedReader bfr = new BufferedReader(ipr);
	                      String str = "";
	                      while((str=bfr.readLine()) != null) {
	                    	  InputCodeArea.append(str+"\n");//将接收到的数据追加到TextArea中已有文本的后边
	                    }
	                      bfr.close();
	                      profile.write(String.valueOf(file));
		              }
		        }catch (FileNotFoundException e1) {
		               System.out.println("打开文件失败");
		        }catch (IOException e2) {
		               System.out.println("打开文件失败");
		        }
			}
		});
		/**
		 * 保存文件按钮
		 */
		saveFile_Bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   if(file == null) {
		               JFileChooser jfc = new JFileChooser();
		               if(!new File(latestPath).exists())
							latestPath = "C:/";
						else
							jfc = new JFileChooser(latestPath);
		               FileNameExtensionFilter filter = new FileNameExtensionFilter("txt & Gaius", "txt", "Gaius");
		               jfc.setFileFilter(filter);// 设定文件选择格式
		               jfc.showSaveDialog(jfc);
		               file= jfc.getSelectedFile();
		               if(file != null) {
		            	   latestPath = String.valueOf(file);
		                      try{
		                             file.createNewFile();
		                      }catch (IOException e1) {
		                             //TODO Auto-generated catch block
		                             e1.printStackTrace();
		                      }
		               }
		        }
		        OutputStreamWriter out = null;
		        if(file != null) {
		        	latestPath = String.valueOf(file);
	               try{
	                      FileOutputStream fos= new FileOutputStream(file);
	                      out= new OutputStreamWriter(fos, "UTF-8");
	                      out.write(InputCodeArea.getText());
	                      out.flush();
	                      out.close();
	                      profile.write(String.valueOf(file));
	               }catch (IOException e2) {
	                      //TODO Auto-generated catch block
	                      e2.printStackTrace();
	               }
		        }
			}
		});
	}
	private static Profile profile = new Profile();
	private static String  latestPath = (profile.read()?profile.latestPath:"C:/");
	static String[][] AnaTableCellData = null;
	static List<String> AnaTableColumnNames = null;
	static List<String> AnaTableRowNames = null;
	static int oneStepClickTimes = 0;
	private File file = null;
	static JTable AnaTable = null;
	static JTable StepTable = null;
	String LL1_firstString = new String();
	String LL1_followString = new String();
	private JTextField txtFiled;
	
}
