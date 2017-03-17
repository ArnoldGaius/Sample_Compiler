package UI;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;

import java.awt.Font;

public class reg2FA_UI {

	private JFrame frame;
	private JTextField expTextField;
	
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
		frame.setBounds(100, 100, 767, 663);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel UpPanel = new JPanel();
		UpPanel.setBorder (BorderFactory.createTitledBorder ("表达式"));
		frame.getContentPane().add(UpPanel, BorderLayout.NORTH);
		
		JLabel UpLable = new JLabel("\u8BF7\u8F93\u5165\u4E00\u4E2A\u8868\u8FBE\u5F0F\uFF1A");
		UpLable.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		UpLable.setHorizontalAlignment(SwingConstants.LEFT);
		
		expTextField = new JTextField();
		expTextField.setColumns(15);
		
		JLabel exampLable = new JLabel("\u4F8B\u5982\uFF08a*|b\uFF09*");
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
		reg2NFA_panel.setBorder (BorderFactory.createTitledBorder ("正则式->NFA"));
		mainPanel.add(reg2NFA_panel);
		
		JPanel NFA2DFA_panel = new JPanel();
		NFA2DFA_panel.setBorder(BorderFactory.createTitledBorder ("NFA->DFA"));
		mainPanel.add(NFA2DFA_panel);
		
		JPanel DFA2MFA_panel = new JPanel();
		DFA2MFA_panel.setBorder(BorderFactory.createTitledBorder ("DFA->MFA"));
		mainPanel.add(DFA2MFA_panel);
	}

}
