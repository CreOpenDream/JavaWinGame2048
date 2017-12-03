package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game2048 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 */
	public Game2048()
	{
		// 设置标题
		setTitle("2048");
		// 设定窗口大小
		setSize(400,420);
		//设置窗口不可改变大小
		setResizable(false);
		// 设定窗口起始位置
		setLocation(300,100);
		// 设定关闭
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设定布局方式为GridLayout型
		setLayout(new BorderLayout());
		JPanel jpMenu = new JPanel(new FlowLayout());
		jpMenu.setPreferredSize(new Dimension(400,20));
		JPanel jpGame = new JPanel(new GridLayout(4,4,3,3));
		//setLayout(new GridLayout(4,4,3,3));
		// game开始
		new Operation(this, jpGame, jpMenu);
		// 设为可视
		setVisible(true);
		// 设定Frame的缺省外观
		setDefaultLookAndFeelDecorated(true);
		add(jpMenu, BorderLayout.NORTH);
		add(jpGame, BorderLayout.CENTER);
	
	}

	/**
	 * 程序入口点
	 */
	public static void main(String args[]) 
	{
		
		// 调用构造函数
		new Game2048();
	}

}