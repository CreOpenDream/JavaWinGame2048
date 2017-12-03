package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class Operation implements KeyListener {
	// 用于储存16个数据
	int [][] data;
	Block [][] block;
	// 设定版面
	JFrame jframe;
	JPanel jpGame;
	JPanel jpMenu;
	JLabel jlInfo;
	JLabel jlGameOver;
	// 上下左右的标志位,0上,1右,2下,3左
	// 用于累计移动的次数
	int counts = 0;
	// 用于判断是否还能加入新的数字
	boolean isFull = false;
	// 是否是移动数字合并
	boolean isMoved = true;
	//能加入的数字
	int addNum;
	private int score = 0;
	private int highScore = 0;
	/**
	 * 初始化
	 */
	public Operation(final JFrame frame, JPanel jpGame, JPanel jpMenu) {
		// 构造出panel
	
		block = new Block[4][4];
		this.jframe = frame;
		this.jpGame = jpGame;
		this.jpMenu = jpMenu;
		// 构造出长度为16的数组
		data = new int[][]{
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0}
		};
		
		// 初始化能加入新的数字
		 addNum = 2;
		//初始化数字方格内容
		for(int i=0; i < 4; i++) {
			for(int j=0; j < 4; j++) {
				block[i][j] = new Block();
				block[i][j].setValue(data[i][j]);
				addBlock(block[i][j]);
			}
		}
	
		//随机显示两个值
		int rx = new Random().nextInt(4);
		int ry = new Random().nextInt(4);
		int rx2;
		int ry2;
		data[rx][ry] = 2;
		do {
			 rx2 = new Random().nextInt(4);
			 ry2 = new Random().nextInt(4);
		}while(rx == rx2 && ry2 == ry);
		data[ry2][ry2] = 4;			
		updateData();
		//设定按键监听
		frame.addKeyListener(this);
		//显示菜单
		jlInfo = new JLabel("最高分"+highScore+"      分数:" + score +"    步数"+counts);
		jpMenu.add(jlInfo);
		
		jlGameOver = new JLabel("游戏失败");
		
		JMenuBar jmb = new JMenuBar();
		JMenu jmAbout = new JMenu("关于");
		
		jmAbout.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				JOptionPane.showConfirmDialog(jframe,"版本:v1.0\n开发者博客:www.ncgds.cn\n","关于",JOptionPane.PLAIN_MESSAGE);
				
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		jmb.add(jmAbout);
		jframe.setJMenuBar(jmb);
	}

	/**
	 * 画面里放入Block的值
	 */
	private void addBlock(Block bl) {
		// 往panel里加入block
			//jpGame.add(bl);
		jpGame.add(bl);
			// 不透明的标签
		bl.setOpaque(true);
	}

	/**
	 * 给随机位置显示Block的值
	 */
	public void appearBlock() {
		// 当还能加入随机的一个新的值得时候
		while(!isFull) {
			// 取一个0到15的随机整数，这个数作为随机加入盘中的2或4的位置
			Random rand = new Random();
			addNum = rand.nextInt(4);
			int addNum2 = rand.nextInt(4);
			int rand2or4 = rand.nextInt(100);
			// 如果这个数所在的block数组中值为0，即在为空的时候，加入一个2或4的数字
			if(data[addNum][addNum2 ] == 0)
				data[addNum][addNum2 ]= (rand2or4%2 == 0?2:4);
			// 跳出while
				break;
		}
				

	}

	/**
	 * 判断是否还有可以产生值的位置
	 */
	public void judgeAppear() 
	{
		// 统计block数组中是否含有值为0的元素，若没有，则isFull变为false
		isFull = true;
		for(int i=0; i < 4; i++){
			for(int j=0; j < 4; j++) {
				if(data[i][j] == 0)
					isFull = false;
			}
		}
	}
	
	/**
	 * 更新数据
	 */
	public void updateData() {
		for(int i=0; i < 4; i++) 
			for(int j=0; j < 4; j++) 
				block[i][j].setValue(data[i][j]);
	}
	/**
	 * 寻找数字方格值
	 */
	public int Find(int i, int j, int a, int b) {
		if(j == b) {
			for(int iIndex = i; iIndex < a; iIndex++) {
				if(data[iIndex][j] != 0)
					return data[iIndex][j];
			}
		}
		else if(i == a) {
			for(int iIndex = j; iIndex < b; iIndex++) {
				if(data[i][iIndex] != 0)
					return data[i][iIndex];
			}
		}
		//循环寻找数字方格值返回，找不到返回-1
		return -1;
	}
	/**
	 * 
	 * @param ix 合并后的 x坐标
	 * @param iy 合并后的 y坐标
	 * @param jx 待合并的 x坐标
	 * @param jy 待合并的 y坐标
	 */
	public void merge(int ix, int iy, int jx, int jy) {
		if(data[jx][jy] == data[ix][iy])
		{
			if(isMoved)
			score += data[jx][jy];
			data[ix][iy] *= 2;
			data[jx][jy] = 0;

		}
	}

	/**
	 * 按上按钮的处理
	 */
	public void upBlock() {
		//循环每一列的内容。将一列所有的值，都向上移动。相同数字的方块在靠拢、相撞时会相加
		int index = 0, curPos, step, tarPos;
		curPos = 3;
		step = 1;
		tarPos = 0;
		while (index < 3)
		{
			for(int i = 0; i < 4; i++)
			{
				for(int j = curPos; j != tarPos; j = j - step)
				{
					if(data[j - step][i] == 0 && data[j][i] != 0)
					{
						data[j - step][i] = data[j][i];
						data[j][i] = 0;
					}
				
				}
			}
			index++;
		}
		//合并相同数
		for (int i = 0; i < 4; i++) {
			merge(0,i, 1,i);
			merge(1,i, 2,i);
			merge(2,i, 3,i);
		}
	
	}

	/**
	 * 按下按钮的处理
	 */
	public void downBlock() {
		//循环每一列的内容。将一列所有的值，都向下移动。相同数字的方块在靠拢、相撞时会相加
		int index = 0, curPos, step, tarPos;
		curPos = 0;
		step = -1;
		tarPos = 3;
		while (index < 3)
		{
			for(int i = 0; i < 4; i++)
			{
				for(int j = curPos; j != tarPos; j = j - step)
				{
					if(data[j - step][i] == 0 && data[j][i] != 0)
					{
						data[j - step][i] = data[j][i];
						data[j][i] = 0;
					}
				}
			}
			index++;
		}
		//合并相同数
		for (int i = 0; i < 4; i++) {
			merge(3,i, 2,i);
			merge(2,i, 1,i);
			merge(1,i, 0,i);
		}

	}

	/**
	 * 按右按钮的处理
	 */
	public void rightBlock() {
		//循环每一行的内容。将一行所有的值，都向右移动。相同数字的方块在靠拢、相撞时会相加
		int index = 0, curPos, step, tarPos;
		curPos = 0;
		step = -1;
		tarPos = 3;
		while (index < 3)
		{
			
				for(int j = curPos; j != tarPos; j = j - step)
				{
					for(int i = 0; i < 4; i++)
					{ 
					if(data[i][j - step] == 0 && data[i][j] != 0)
					{
						data[i][j - step] = data[i][j];
						data[i][j] = 0;
					}
				}
			}
			index++;
		}

		//合并相同数
		//合并相同数
		for (int i = 0; i < 4; i++) {
			merge(i,3, i,2);
			merge(i,2, i,1);
			merge(i,1, i,0);
		}

	}

	/**
	 * 按左按钮的处理
	 */
	public void leftBlock() {
		//循环每一行的内容。将一行所有的值，都向左移动。相同数字的方块在靠拢、相撞时会相加
		int index = 0, curPos, step, tarPos;
		curPos = 3;
		step = 1;
		tarPos = 0;
		while (index < 3)
		{
			
				for(int j = curPos; j != tarPos; j = j - step)
				{
					for(int i = 0; i < 4; i++)
					{ 
					if(data[i][j - step] == 0 && data[i][j] != 0)
					{
						data[i][j - step] = data[i][j];
						data[i][j] = 0;
					}
				}
			}
			index++;
		}
		//合并相同数
				for (int i = 0; i < 4; i++) {
					merge(i,0, i,1);
					merge(i,1, i,2);
					merge(i,2, i,3);
				}

		
	}

	/**
	 * 游戏失败
	 */
	public void over() {
		// 当不能添加元素，并且不可移动的时失败，失败的时候在盘中央显示GAMEOVE，鼠标随意点击重启游戏
		if(isFull == true && canMove() == false){
			JOptionPane.showConfirmDialog(jframe, "您的分数:"+score+"\n点击确认重新开始游戏","游戏失败",JOptionPane.PLAIN_MESSAGE);
			reStart();
		}

	}
	public boolean canMove() {
		isMoved = false;
		int [][]data2 = new int[4][4];
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
				data2[i][j] = data[i][j];
		upBlock();
		downBlock();
		leftBlock();
		rightBlock();
	
		
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++) 
				if(data[i][j] == 0) {
					for(int a=0; a < 4; a++) 
						for(int b=0; b < 4; b++) 
							data[a][b] = data2[a][b];
					isMoved = true;
					return true;					
				}
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++) 
				data[i][j] = data2[i][j];
		isMoved = true;
		return false;
	}
	/**
	 * 游戏成功
	 */
	public void win() {
		// 当有一个block值为2048时获胜，获胜的时候在盘中央显示YOUWIN，鼠标随意点击重启游戏
		for(int i=0; i < 4; i++){
			for(int j=0; j < 4; j++) {
				if(data[i][j] == 2048) {
					JOptionPane.showConfirmDialog(jframe, "您的分数:"+score+"\n点击确认重新开始游戏","游戏通关",JOptionPane.PLAIN_MESSAGE);
					reStart();
					return;
				}
					
			}
		}
	}

	/**
	 * 重启游戏
	 */
	public void reStart()
	{
		// 初始化所有内容，重启游戏
		data = new int[][]{
			{0,0,0,0},
			{0,0,0,0},
			{0,0,2,0},
			{0,0,2,0}
	};
		counts = 0;
		highScore = score > highScore? score: highScore;
		score = 0;
		isFull = false;
		jlInfo.setText("最高分"+highScore+"      分数:" + score +"    步数"+counts);
		updateData();
		
		jpGame.revalidate();
		jpMenu.revalidate();
	}

	/**
	 * 判断按的上下左右键，并依次调用移动函数、判断函数、添加函数、判断是否输掉的函数
	 */
	public void keyPressed(KeyEvent e) 
	{
		switch (e.getKeyCode()) {
		//向上按钮
		case KeyEvent.VK_UP:
			upBlock();
			counts++;
			judgeAppear();
			appearBlock();
			updateData();
			jlInfo.setText("最高分"+highScore+"      分数:" + score +"    步数"+counts);
			jpGame.revalidate();
			over();
			break;
		//向下按钮
		case KeyEvent.VK_DOWN:
			downBlock();
			counts++;
			judgeAppear();
			appearBlock();
			updateData();
			jlInfo.setText("最高分"+highScore+"      分数:" + score +"    步数"+counts);
			jpGame.revalidate();
			over();
			break;   
		//向左按钮
		case KeyEvent.VK_LEFT:
			leftBlock();
			counts++;
			judgeAppear();
			appearBlock();
			updateData();
			jlInfo.setText("最高分"+highScore+"      分数:" + score +"    步数"+counts);
			jpGame.revalidate();
			over();
			break;
		//向右按钮
		case KeyEvent.VK_RIGHT:
			rightBlock();
			counts++;
			judgeAppear();
			appearBlock();
			updateData();
			jlInfo.setText("最高分"+highScore+"      分数:" + score +"    步数"+counts);
			jpGame.revalidate();
			over();
			break;
		}
	
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

}