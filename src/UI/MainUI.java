package UI;

import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import Analysis.AnalysisCodeToWord;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.awt.Font;

public class MainUI {
	private JFrame frame;
	private File file = null;
	private static Profile profile = new Profile();
	private static String  latestPath = (profile.read()?profile.latestPath:"C:/");
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.addWindowListener(new WindowAdapter(){
						   public void windowClosing(WindowEvent we){
							    profile.write(latestPath);//每次退出程序时把最后一次打开的目录写入到配置文件
							   }
						});
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void addWindowListener(WindowAdapter windowAdapter) {
		// TODO Auto-generated method stub
		  if(!new File(latestPath).exists()){
			   latestPath = "C:/";
		}
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 834, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		panel.add(menuBar);
		
		JMenu FileMenu = new JMenu("\u6587\u4EF6");
		FileMenu.setBackground(SystemColor.control);
		FileMenu.setFont(UIManager.getFont("ToolTip.font"));
		FileMenu.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(FileMenu);
		
		JMenuItem OpenFile_MI = new JMenuItem("\u8BFB\u53D6");
		
		FileMenu.add(OpenFile_MI);
		
		final JMenuItem SaveFile_MI = new JMenuItem("\u4FDD\u5B58");
		FileMenu.add(SaveFile_MI);
		
		JMenuItem Exit_MI = new JMenuItem("\u9000\u51FA");
		FileMenu.add(Exit_MI);
		
		JMenu editMenu = new JMenu("\u7F16\u8F91");
		editMenu.setHorizontalAlignment(SwingConstants.LEFT);
		editMenu.setFont(UIManager.getFont("ToolTip.font"));
		menuBar.add(editMenu);
		
		JMenuItem cancelMenu = new JMenuItem("\u64A4\u9500");
		editMenu.add(cancelMenu);
		
		JMenuItem cutMenu = new JMenuItem("\u526A\u5207");
		editMenu.add(cutMenu);
		
		JMenuItem copyMenu = new JMenuItem("\u590D\u5236");
		editMenu.add(copyMenu);
		
		JMenuItem parsteMenu = new JMenuItem("\u7C98\u8D34");
		editMenu.add(parsteMenu);
		
		JMenu WordAnaMenu = new JMenu("\u8BCD\u6CD5\u5206\u6790");
		menuBar.add(WordAnaMenu);
		WordAnaMenu.setHorizontalAlignment(SwingConstants.LEFT);
		WordAnaMenu.setFont(UIManager.getFont("ToolTip.font"));
		
		final JMenuItem wordAnalyzer_MI = new JMenuItem("\u8BCD\u6CD5\u5206\u6790\u5668");
		WordAnaMenu.add(wordAnalyzer_MI);
		
		JMenuItem reg2NFA2DFA2MFA = new JMenuItem("正则->NFA->DFA->MFA");
		WordAnaMenu.add(reg2NFA2DFA2MFA);
		
		JMenu GraMI = new JMenu("\u8BED\u6CD5\u5206\u6790");
		menuBar.add(GraMI);
		GraMI.setHorizontalAlignment(SwingConstants.LEFT);
		GraMI.setFont(UIManager.getFont("ToolTip.font"));
		
		JMenuItem GraAnalyzer_MI = new JMenuItem("\u8BED\u6CD5\u5206\u6790\u5668");
		GraMI.add(GraAnalyzer_MI);
		
		JMenuItem LL1_MI = new JMenuItem("LL1\u9884\u6D4B\u5206\u6790");
		GraMI.add(LL1_MI);
		
		JMenuItem operatorFirst_MI = new JMenuItem("\u8FD0\u7B97\u7B26\u4F18\u5148");
		GraMI.add(operatorFirst_MI);
		
		JMenuItem LR_Analyzer_MI = new JMenuItem("LR\u5206\u6790");
		GraMI.add(LR_Analyzer_MI);
		
		JMenu MidCodeMenu = new JMenu("\u4E2D\u95F4\u4EE3\u7801");
		MidCodeMenu.setFont(UIManager.getFont("ToolTip.font"));
		menuBar.add(MidCodeMenu);
		
		JMenu targetCodeMaking = new JMenu("\u76EE\u6807\u4EE3\u7801\u751F\u6210");
		targetCodeMaking.setFont(UIManager.getFont("ToolTip.font"));
		menuBar.add(targetCodeMaking);
		
		JMenu helpMenu = new JMenu("\u5E2E\u52A9");
		helpMenu.setHorizontalAlignment(SwingConstants.LEFT);
		helpMenu.setFont(UIManager.getFont("ToolTip.font"));
		menuBar.add(helpMenu);
		
		JMenuItem helpTextMI = new JMenuItem("\u5E2E\u52A9\u6587\u6863");
		helpMenu.add(helpTextMI);
		
		JMenuItem aboutMI = new JMenuItem("\u5173\u4E8E...");
		helpMenu.add(aboutMI);
		
		JPanel MainPanel = new JPanel();
		frame.getContentPane().add(MainPanel, BorderLayout.CENTER);
		MainPanel.setLayout(new GridLayout(1, 2));
		
		JPanel CodePanel = new JPanel();
		CodePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		CodePanel.setBorder (BorderFactory.createTitledBorder ("代码部分"));
		CodePanel.setPreferredSize(new Dimension(frame.getWidth()/2-10,frame.getHeight()));
		MainPanel.add(CodePanel, BorderLayout.WEST);
		CodePanel.setLayout(new BorderLayout(0, 0));
		
		final JTextArea codeArea = new JTextArea();
		codeArea.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		
		JScrollPane codeAreaScroll = new JScrollPane(codeArea);
		CodePanel.add(codeAreaScroll);
		
		JPanel RightPanel = new JPanel();
		RightPanel.setPreferredSize(new Dimension(frame.getWidth()/2,frame.getHeight()));
		MainPanel.add(RightPanel, BorderLayout.EAST);
		RightPanel.setLayout(new GridLayout(2, 1));
		
		final JPanel TextPanel = new JPanel();
		TextPanel.setToolTipText("");
		TextPanel.setBorder (BorderFactory.createTitledBorder (""));//设置标题
		RightPanel.add(TextPanel);
		TextPanel.setLayout(new BorderLayout(0, 0));
		
		final JTextArea textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textArea_1.setBackground(SystemColor.control);
		textArea_1.setEditable(false);
		
		JScrollPane scrollPane_1 = new JScrollPane(textArea_1);
		TextPanel.add(scrollPane_1);
		
		final JPanel ErrPanel = new JPanel();
		ErrPanel.setBorder (BorderFactory.createTitledBorder ("")); //设置标题
		RightPanel.add(ErrPanel);
		ErrPanel.setLayout(new BorderLayout(0, 0));
		
		final JTextArea textArea_2 = new JTextArea();
		textArea_2.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textArea_2.setBackground(SystemColor.control);
		textArea_2.setEditable(false);
		
		JScrollPane scrollPane_2 = new JScrollPane(textArea_2);
		ErrPanel.add(scrollPane_2);
		codeArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("remove");
				wordAnalyzer_MI.doClick();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("insert");
				wordAnalyzer_MI.doClick();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("change");
				wordAnalyzer_MI.doClick();
			}
		});
		
		
		final UndoManager undomg = new UndoManager(); //撤销 重做类
		codeArea.getDocument().addUndoableEditListener(undomg);
		codeArea.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.isControlDown()&&e.getKeyCode() == KeyEvent.VK_Z){
					if(undomg.canUndo()) { //撤销
					    undomg.undo();
					    System.out.println("Cancle");
					}else{
						System.out.println(" Cancle Failed !");
					}
				}
				if (e.isControlDown()&&e.getKeyCode() == KeyEvent.VK_S){
					SaveFile_MI.doClick();
					System.out.println("Save");
				}
				if (e.isControlDown()&&e.getKeyCode() == KeyEvent.VK_Y){
					if(undomg.canRedo()) { //恢复
					    undomg.redo();
					    System.out.println("Redo");
					}else{
						System.out.println(" Redo Failed !");
					}
					
				}
				if (e.isControlDown()&&e.getKeyCode() == KeyEvent.VK_C){
					codeArea.copy();
					System.out.println("Copy");
				}
				if (e.isControlDown()&&e.getKeyCode() == KeyEvent.VK_V){
					codeArea.paste();
					System.out.println("Paste");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});


		
		OpenFile_MI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				codeArea.setText("");
				textArea_1.setText("");
				textArea_2.setText("");
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
	                    	  codeArea.append(str+"\n");//将接收到的数据追加到TextArea中已有文本的后边
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
		
		SaveFile_MI.addActionListener(new ActionListener() {
			@Override
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
	                      out.write(codeArea.getText());
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
		Exit_MI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(-1);
			}
		});
		
		wordAnalyzer_MI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				textArea_1.setText("");
				textArea_2.setText("");
				AnalysisCodeToWord cdAnalyzer = new AnalysisCodeToWord();
				cdAnalyzer.readFromArea(codeArea);
				cdAnalyzer.run();
				TextPanel.setBorder (BorderFactory.createTitledBorder ("token表信息"));
				textArea_1.append(cdAnalyzer.token);
				textArea_1.append("------------------完成------------------");
				ErrPanel.setBorder (BorderFactory.createTitledBorder ("词法分析-错误提示")); //设置标题
				textArea_2.append(cdAnalyzer.errors);
				textArea_2.append("词法分析结束:\t"+cdAnalyzer.errors_num+"-error(s)\n");
			}
		});
		reg2NFA2DFA2MFA.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					reg2FA_UI window = new reg2FA_UI();
					window.returnThisJFrame().setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
}

class Profile{
	 String latestPath = "C:/";
	 File file = new File("Opt.ini"); 
	 public Profile(){}
	 boolean create(){
	  boolean b = true;  
	  if(file!=null){
	   File directory = file.getParentFile();//获得文件的父目录
	   if(!(directory==null)){//目录不存在时
	    b = directory.mkdirs();//创建目录
	   }else{//存在目录
	    if(!file.exists()){//配置文件不存在时
	     try {
	      b = file.createNewFile();//创建配置文件
	     } catch (IOException e) {
	      b = false;
	     }
	    }
	   }
	  }  return b;
	}
	 
	 boolean read(){
	  Properties pro;//属性集
	  FileInputStream is = null;
	  boolean b = true;
	  if(!file.exists()){//配置文件不存在时
	   b = create();//创建一个
	   if(b)//创建成功后
	   b = write(latestPath);//初始化
	   else//创建失败即不存在配置文件时弹出对话框提示错误
	    JOptionPane.showConfirmDialog(null, "对不起，不存在配置文件！", "错误", 
	     JOptionPane.YES_NO_OPTION, 
	     JOptionPane.ERROR_MESSAGE);
	  }else{
	   try {
	    is = new FileInputStream(file);
	    pro = new Properties();
	    pro.load(is);//读取属性
	    latestPath = pro.getProperty("latestPath");//读取配置参数latestPath的值
	    is.close();
	   }
	   catch (IOException ex) {
	    ex.printStackTrace(); 
	    b =  false;
	   }
	  }
	  return b;
	 }
	 boolean write(String latestPath){  
	  this.latestPath = latestPath;
	  Properties pro = null;
	  FileOutputStream os = null;
	  boolean b = true;
	  try {
	   os = new FileOutputStream(file);
	   pro = new Properties();
	   
	   pro.setProperty("latestPath",latestPath); 
	     
	   pro.store(os,null); //将属性写入  
	   os.flush();
	   os.close();
	   
	   System.out.println("latestPath=" + latestPath);
	 
	  }
	  catch (IOException ioe) {
	   b = false;
	   ioe.printStackTrace();
	  }
	  return b;
	 }
}
