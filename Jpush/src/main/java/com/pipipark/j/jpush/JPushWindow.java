package com.pipipark.j.jpush;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.util.Vector;

import javax.swing.SwingConstants;

import com.pipipark.j.jpush.message.JMessage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class JPushWindow {

	private JFrame frame;
	private JTextField senderText;
	private JTextField receiveText;
	private JTextField msgTypeText;
	private JTextField msgTitleText;
	private JTextField msgContentText;
	private JTextField appKeyText;
	private JTextField masterSecretText;
	private JComboBox<String> typeText;
	private JTextArea returnMsgText;
	private JComboBox<String> clientTypeText;
	
	
	private Vector<String> typeData = new Vector<String>();
	private Vector<String> clientData = new Vector<String>();
	
	private String[] keyData = new String[]{"a32ee4da7de5842f38107fed","ba587ae13094f3be876d8474"};//家长端,教师端
	private String[] secretData = new String[]{"9b99ebb6efbd2273711440d0","d34bc3e5414fc2230e1c4ca1"};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JPushWindow window = new JPushWindow();
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
	public JPushWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("极光推送工具");
		frame.setResizable(false);
		frame.setBounds(100, 100, 400, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		typeData.add("Alias");
		typeData.add("Tags");
		
		clientData.add("Parent");
		clientData.add("Teacher");
		clientData.add("Others");
		
		JLabel appKey = new JLabel("AppKey");
		appKey.setHorizontalAlignment(SwingConstants.RIGHT);
		appKey.setBounds(20, 58, 55, 25);
		frame.getContentPane().add(appKey);
		
		appKeyText = new JTextField();
		appKeyText.setEditable(false);
		appKeyText.setText(keyData[0]);
		appKeyText.setBounds(80, 58, 290, 25);
		frame.getContentPane().add(appKeyText);
		appKeyText.setColumns(10);
		
		JLabel masterSecret = new JLabel("Secret");
		masterSecret.setHorizontalAlignment(SwingConstants.RIGHT);
		masterSecret.setBounds(20, 93, 55, 25);
		frame.getContentPane().add(masterSecret);
		
		masterSecretText = new JTextField();
		masterSecretText.setEditable(false);
		masterSecretText.setText(secretData[0]);
		masterSecretText.setBounds(80, 93, 290, 25);
		frame.getContentPane().add(masterSecretText);
		masterSecretText.setColumns(10);
		
		JSeparator sep0 = new JSeparator();
		sep0.setBounds(20, 129, 350, 5);
		frame.getContentPane().add(sep0);
		
		JLabel type = new JLabel("推送类型");
		type.setBounds(20, 142, 55, 25);
		frame.getContentPane().add(type);
		
		typeText = new JComboBox<String>(typeData);
		typeText.setBounds(80, 142, 290, 25);
		frame.getContentPane().add(typeText);
		JLabel sender = new JLabel("推送用户");
		sender.setBounds(20, 187, 55, 25);
		frame.getContentPane().add(sender);
		
		senderText = new JTextField();
		senderText.setBounds(80, 187, 290, 25);
		frame.getContentPane().add(senderText);
		senderText.setColumns(10);
		
		JLabel receive = new JLabel("接收用户");
		receive.setBounds(20, 232, 55, 25);
		frame.getContentPane().add(receive);
		
		receiveText = new JTextField();
		receiveText.setToolTipText("多个接收者用\",\"隔开");
		receiveText.setBounds(80, 228, 290, 25);
		frame.getContentPane().add(receiveText);
		receiveText.setColumns(10);
		
		JSeparator sep1 = new JSeparator();
		sep1.setBounds(20, 277, 350, 5);
		frame.getContentPane().add(sep1);
		
		JLabel msgType = new JLabel("消息类型");
		msgType.setBounds(20, 295, 55, 25);
		frame.getContentPane().add(msgType);
		
		msgTypeText = new JTextField();
		msgTypeText.setBounds(80, 295, 80, 25);
		frame.getContentPane().add(msgTypeText);
		msgTypeText.setColumns(10);
		
		JLabel msgNum = new JLabel("剩余消息");
		msgNum.setBounds(210, 295, 55, 25);
		frame.getContentPane().add(msgNum);
		
		JLabel msgNumUnit = new JLabel("条");
		msgNumUnit.setBounds(355, 295, 15, 25);
		frame.getContentPane().add(msgNumUnit);
		
		JLabel msgTitle = new JLabel("消息标题");
		msgTitle.setBounds(20, 340, 55, 25);
		frame.getContentPane().add(msgTitle);
		
		msgTitleText = new JTextField();
		msgTitleText.setBounds(80, 340, 290, 25);
		frame.getContentPane().add(msgTitleText);
		msgTitleText.setColumns(10);
		
		JLabel msgContent = new JLabel("消息内容");
		msgContent.setBounds(20, 385, 55, 25);
		frame.getContentPane().add(msgContent);
		
		msgContentText = new JTextField();
		msgContentText.setBounds(80, 385, 290, 25);
		frame.getContentPane().add(msgContentText);
		msgContentText.setColumns(10);
		
		
		JButton sendBtn = new JButton("发送");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item = typeText.getSelectedItem().toString();
				JMessage message = JPush.buildMessage(appKeyText.getText(),masterSecretText.getText());
				String[] rs = receiveText.getText().split(",");
				message.sender(senderText.getText())
				.type(Integer.parseInt(msgTypeText.getText()))
				.title(msgTitleText.getText())
				.content(msgContentText.getText())
				.sound(1).shake(1);
				String returnMsg = "";
				if("Alias".equals(item)){
					if(rs.length==0){
						returnMsg = "未填写接收者.";
					}else if(rs.length>1){
						returnMsg += JPush.sendByAlias(message, rs)+"\r\n";
					}else{
						returnMsg += JPush.sendByAlias(message.receive(rs[0]), rs[0])+"\r\n";
					}
				}else if("Tags".equals(item)){
					returnMsg += JPush.sendByTags(message, rs)+"\r\n";
				}
				returnMsgText.setText(returnMsg);
			}
		});
		
		JButton button = new JButton("广播");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnMsgText.setText(JPush.alertAll(JPush.buildMessage(appKeyText.getText(),masterSecretText.getText()).content(msgContentText.getText())));
			}
		});
		button.setBounds(200, 418, 80, 30);
		frame.getContentPane().add(button);
		sendBtn.setBounds(290, 418, 80, 30);
		frame.getContentPane().add(sendBtn);
		
		JSeparator sep2 = new JSeparator();
		sep2.setBounds(20, 462, 350, 5);
		frame.getContentPane().add(sep2);
		
		JLabel returnMsg = new JLabel("返回信息");
		returnMsg.setBounds(20, 472, 80, 25);
		frame.getContentPane().add(returnMsg);
		
		returnMsgText = new JTextArea();
		returnMsgText.setEditable(false);
		returnMsgText.setBorder(new LineBorder(new Color(0, 0, 0)));
		returnMsgText.setBounds(20, 502, 350, 80);
		frame.getContentPane().add(returnMsgText);
		
		clientTypeText = new JComboBox<String>(clientData);
		clientTypeText.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					String itemStr = e.getItem().toString();
					if(clientData.get(0).equals(itemStr)){
						appKeyText.setText(keyData[0]);
						appKeyText.setEditable(false);
						masterSecretText.setText(secretData[0]);
						masterSecretText.setEditable(false);
					}else if(clientData.get(1).equals(itemStr)){
						appKeyText.setText(keyData[1]);
						appKeyText.setEditable(false);
						masterSecretText.setText(secretData[1]);
						masterSecretText.setEditable(false);
					}else{
						appKeyText.setText("");
						appKeyText.setEditable(true);
						masterSecretText.setText("");
						masterSecretText.setEditable(true);
					}
				}
			}
		});
		clientTypeText.setBounds(80, 20, 100, 25);
		frame.getContentPane().add(clientTypeText);
		
		JLabel clientType = new JLabel("客户端");
		clientType.setHorizontalAlignment(SwingConstants.RIGHT);
		clientType.setBounds(20, 20, 55, 25);
		frame.getContentPane().add(clientType);
	}

	@SuppressWarnings("unused")
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
