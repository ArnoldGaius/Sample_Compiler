package UI;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Analysis.DFAtoMFA;
import Analysis.ExpressiontoNFA;
import Analysis.NFAtoDFA;
import Analysis.Verify;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.UIManager;

public class reg2FA_UI {

	JFrame frame;
	private JTextField expTextField;
	private String signString;
	JDialog dialog = new JDialog();
	
	JTextArea nfata = new JTextArea();
	JTextArea dfata = new JTextArea();
	JTextArea mfata = new JTextArea();
	JButton dfa = new JButton("\u751F\u6210DFA");
	JButton mfa = new JButton("\u751F\u6210MFA");
	JButton nfa = new JButton("\u751F\u6210NFA");
	
	JLabel nfastart = new JLabel("开始状态集:");
	JLabel nfaend = new JLabel("终结状态集:");
	JLabel dfastart = new JLabel("开始状态集:");
	JLabel dfaend = new JLabel("终结状态集:");
	JLabel mfastart = new JLabel("开始状态集:");
	JLabel mfaend = new JLabel("终结状态集:");
	
	private JFileChooser filechooser = new JFileChooser(
			"D:\\Users\\clz0730\\Desktop");
	private int result;
	File fe;
	
	public void openFile(JTextArea ta, JLabel startlabel, JLabel endlabel,String str) {
		result = filechooser.showOpenDialog(dialog);
		ta.setText("");
		if (result == JFileChooser.APPROVE_OPTION) {
			fe = filechooser.getSelectedFile();
		}
		if (fe != null) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(fe));
				String line = null;
				if ((line = in.readLine()) != null) {
					startlabel.setText(line);
				}
				if ((line = in.readLine()) != null) {
					endlabel.setText(line);
				}
				if ((line = in.readLine()) != null) {
					signString = line;
				}
				while ((line = in.readLine()) != null) {
					ta.append(line + "\r\n");
				}
				in.close();
				ta.setCaretPosition(0);
			} catch (IOException ioe) {
				System.err.println(ioe);
			}
		}
	}

	public void saveFile(JTextArea ta, JLabel startlabel, JLabel endlabel,
			String str) {
		String temp = startlabel.getText() + "\r\n" + endlabel.getText()
				+ "\r\n" + str + "\r\n";
		result = filechooser.showSaveDialog(dialog);
		if (result == JFileChooser.APPROVE_OPTION) {
			fe = filechooser.getSelectedFile();
		}
		try {
			if (fe != null) {
				BufferedWriter out = new BufferedWriter(new FileWriter(fe));
				String text = ta.getText();
				out.write(temp);
				out.write(text);
				out.flush();
				out.close();
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
	
	
	public JFrame returnThisJFrame(){
		return this.frame;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					reg2FA_UI window = new reg2FA_UI();
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
	public reg2FA_UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Image icon = Toolkit.getDefaultToolkit().getImage("steam.jpg");
		frame.setIconImage(icon);
		frame.setBounds(100, 100, 886, 663);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel UpPanel = new JPanel();
		UpPanel.setBorder (BorderFactory.createTitledBorder ("表达式"));
		frame.getContentPane().add(UpPanel, BorderLayout.NORTH);
		
		JLabel UpLable = new JLabel("\u8BF7\u8F93\u5165\u4E00\u4E2A\u8868\u8FBE\u5F0F\uFF1A");
		UpLable.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		UpLable.setHorizontalAlignment(SwingConstants.LEFT);
		
		expTextField = new JTextField();
		expTextField.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		expTextField.setText("(a|b)*");
		expTextField.setColumns(15);
		
		JLabel exampLable = new JLabel("\u4F8B\u5982\uFF08a|b\uFF09*");
		exampLable.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		
		JButton verif_EXP_Bt = new JButton("\u9A8C\u8BC1\u6B63\u5219\u5F0F");
		verif_EXP_Bt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		
		JButton exitBt = new JButton("\u9000\u51FA");
		exitBt.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		UpPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		UpPanel.add(UpLable);
		UpPanel.add(expTextField);
		UpPanel.add(exampLable);
		UpPanel.add(verif_EXP_Bt);
		UpPanel.add(exitBt);
		
		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(1, 3));
		
		JPanel reg2NFA_panel = new JPanel();
		mainPanel.add(reg2NFA_panel);
		reg2NFA_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel reg2NFA_header = new JPanel();
		reg2NFA_panel.add(reg2NFA_header, BorderLayout.NORTH);
		reg2NFA_header.setLayout(new GridLayout(2, 1));
		
		JLabel label = new JLabel("正则式->NFA");
		label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		reg2NFA_header.add(label);
		
		JPanel panel_1 = new JPanel();
		reg2NFA_header.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 3));
		
		JLabel label_1 = new JLabel("\u8D77\u59CB\u72B6\u6001");
		label_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("\u63A5\u53D7\u7B26\u53F7");
		label_2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("\u5230\u8FBE\u72B6\u6001");
		label_3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_1.add(label_3);
		
		ScrollPane scrollPane = new ScrollPane();
		nfata = new JTextArea();
		nfata.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		nfata.setRows(10);
		scrollPane.add(nfata, BorderLayout.CENTER);
		reg2NFA_panel.add(scrollPane, BorderLayout.CENTER);
		

		
		JPanel reg2NFA_bottom = new JPanel();
		reg2NFA_panel.add(reg2NFA_bottom, BorderLayout.SOUTH);
		reg2NFA_bottom.setLayout(new GridLayout(3, 1, 0, 0));
		
		nfastart = new JLabel("\u5F00\u59CB\u72B6\u6001\u96C6:");
		nfastart.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		reg2NFA_bottom.add(nfastart);
		
		nfaend = new JLabel("\u7EC8\u7ED3\u72B6\u6001\u96C6:");
		nfaend.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		reg2NFA_bottom.add(nfaend);
		
		JPanel panel_4 = new JPanel();
		reg2NFA_bottom.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton nfaopen = new JButton("NFA\u6587\u4EF6");
		nfaopen.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_4.add(nfaopen);
		
		nfa = new JButton("\u751F\u6210NFA");
		nfa.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		nfa.setEnabled(false);
		panel_4.add(nfa);
		
		JButton nfasave = new JButton("\u4FDD\u5B58");
		nfasave.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_4.add(nfasave);
		
		JPanel NFA2DFA_panel = new JPanel();
		mainPanel.add(NFA2DFA_panel);
		NFA2DFA_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel NFA2DFA_header = new JPanel();
		NFA2DFA_panel.add(NFA2DFA_header, BorderLayout.NORTH);
		NFA2DFA_header.setLayout(new GridLayout(2, 1));
		
		JLabel label_4 = new JLabel("NFA->DFA");
		label_4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		NFA2DFA_header.add(label_4);
		
		JPanel panel_2 = new JPanel();
		NFA2DFA_header.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 3));
		
		JLabel label_5 = new JLabel("\u8D77\u59CB\u72B6\u6001");
		label_5.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_2.add(label_5);
		
		JLabel label_6 = new JLabel("\u63A5\u53D7\u7B26\u53F7");
		label_6.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_2.add(label_6);
		
		JLabel label_7 = new JLabel("\u5230\u8FBE\u72B6\u6001");
		label_7.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_2.add(label_7);
		
		JPanel NFA2DFA_bottom = new JPanel();
		NFA2DFA_panel.add(NFA2DFA_bottom, BorderLayout.SOUTH);
		NFA2DFA_bottom.setLayout(new GridLayout(3, 1, 0, 0));
		
		dfastart = new JLabel("\u5F00\u59CB\u72B6\u6001\u96C6:");
		dfastart.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		NFA2DFA_bottom.add(dfastart);
		
		dfaend = new JLabel("\u7EC8\u7ED3\u72B6\u6001\u96C6:");
		dfaend.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		NFA2DFA_bottom.add(dfaend);
		
		JPanel panel_6 = new JPanel();
		NFA2DFA_bottom.add(panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton dfaopen = new JButton("DFA\u6587\u4EF6");
		dfaopen.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_6.add(dfaopen);
		
		dfa = new JButton("\u751F\u6210DFA");
		dfa.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		dfa.setEnabled(false);
		panel_6.add(dfa);
		
		JButton dfasave = new JButton("\u4FDD\u5B58");
		dfasave.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_6.add(dfasave);
		
		ScrollPane scrollPane_1 = new ScrollPane();
		dfata = new JTextArea();
		dfata.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		dfata.setRows(10);
		scrollPane_1.add(dfata, BorderLayout.CENTER);
		NFA2DFA_panel.add(scrollPane_1, BorderLayout.CENTER);
		
		JPanel DFA2MFA_panel = new JPanel();
		mainPanel.add(DFA2MFA_panel);
		DFA2MFA_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel DFA2MFA_header = new JPanel();
		DFA2MFA_panel.add(DFA2MFA_header, BorderLayout.NORTH);
		DFA2MFA_header.setLayout(new GridLayout(2, 1));
		
		JLabel label_8 = new JLabel("DFA->MFA");
		label_8.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		DFA2MFA_header.add(label_8);
		
		JPanel panel_3 = new JPanel();
		DFA2MFA_header.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 3));
		
		JLabel label_9 = new JLabel("\u8D77\u59CB\u72B6\u6001");
		label_9.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_3.add(label_9);
		
		JLabel label_10 = new JLabel("\u63A5\u53D7\u7B26\u53F7");
		label_10.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_3.add(label_10);
		
		JLabel label_11 = new JLabel("\u5230\u8FBE\u72B6\u6001");
		label_11.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_3.add(label_11);
		
		ScrollPane scrollPane_2 = new ScrollPane();
		mfata = new JTextArea();
		mfata.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		mfata.setRows(10);
		scrollPane_2.add(mfata, BorderLayout.CENTER);
		DFA2MFA_panel.add(scrollPane_2, BorderLayout.CENTER);
		
		JPanel DFA2MFA_bottom = new JPanel();
		DFA2MFA_panel.add(DFA2MFA_bottom, BorderLayout.SOUTH);
		DFA2MFA_bottom.setLayout(new GridLayout(3, 1, 0, 0));
		
		mfastart = new JLabel("\u5F00\u59CB\u72B6\u6001\u96C6:");
		mfastart.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		DFA2MFA_bottom.add(mfastart);
		
		mfaend = new JLabel("\u7EC8\u7ED3\u72B6\u6001\u96C6:");
		mfaend.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		DFA2MFA_bottom.add(mfaend);
		
		JPanel panel_8 = new JPanel();
		DFA2MFA_bottom.add(panel_8);
		panel_8.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton mfaopen = new JButton("MFA\u6587\u4EF6");
		mfaopen.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_8.add(mfaopen);
		
		mfa = new JButton("\u751F\u6210MFA");
		mfa.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		mfa.setEnabled(false);
		panel_8.add(mfa);
		
		JButton mfasave = new JButton("\u4FDD\u5B58");
		mfasave.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		panel_8.add(mfasave);
		
		exitBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		
		verif_EXP_Bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Verify vf = new Verify(expTextField.getText().toString());
				 System.out.println(vf.validation());
				if (vf.validation()) {
					nfa.setEnabled(true);
				} else {
					JOptionPane jop = new JOptionPane();
					jop.setBounds(250, 300, 250, 150);
					JOptionPane.showMessageDialog(null, "您输入的正规式错误！！！",
							"Compiler", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		nfaopen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile(nfata, nfastart, nfaend,
						signString);
				dfa.setEnabled(true);
			}
		});
		nfa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExpressiontoNFA en = new ExpressiontoNFA(
						expTextField.getText().trim().toString());
				en.display();
				nfata.setText(en.tastring);//返回结果
				nfastart.setText(en.nfastartstr);
				nfaend.setText(en.nfaendstr);
				signString = en.signstring;
				dfa.setEnabled(true);
			}
		});
		nfasave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveFile(nfata, nfastart, nfaend,
						signString);
			}
		});
		dfaopen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile(dfata, dfastart, dfaend,
						signString);
				mfa.setEnabled(true);
			}
		});
		dfa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = nfata.getText().toString();
				String ss1 = nfastart.getText().toString();
				String ss2 = nfaend.getText().toString();
				NFAtoDFA nd = new NFAtoDFA(str, ss1, ss2,
						signString);
				nd.display();
				dfata.setText(nd.tbstring);
				dfastart.setText(nd.startstring);
				dfaend.setText(nd.endstring);
				signString = nd.signstring;
				mfa.setEnabled(true);
			}
		});
		dfasave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveFile(dfata, dfastart, dfaend,
						signString);
			}
		});
		mfaopen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile(mfata, mfastart, mfaend,
						signString);
			}
		});
		mfa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ss = dfata.getText().toString();
				String ss1 = dfastart.getText().toString();
				String ss2 = dfaend.getText().toString();
				DFAtoMFA dm = new DFAtoMFA(ss, ss1, ss2,
						signString);
				dm.display();
				mfata.setText(dm.tcstring);
				mfastart.setText("\t" + dm.mfastartstr);
				mfaend.setText("\t" + dm.mfaendstr);
				signString = dm.signstring;
			}
		});
		mfasave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveFile(mfata, mfastart, mfaend,
						signString);
			}
		});
	}

}
