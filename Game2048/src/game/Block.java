package game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Block extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 单元格值
	private int value;
	private Color color;
	
	/**
	 * 构造函数
	 */
	public Block() {
		// 初始化值为0
		setValue(0);
		// 设定字体
		setFont(new Font("Arial", Font.BOLD,42));
		// 设定初始颜色为灰色
		setColor(0);
		this.setHorizontalAlignment(CENTER);
		setBackground(new Color(122,222,100));
	}

	/**
	 * 获取值
	 */
	public int getValue()
	{
		return this.value;
	}

	/**
	 * 设定值
	 * @param 单元格值
	 */
	public void setValue(int value) {

		// 单元格值判断是否是0
		if(value == 0)
			this.setText(" ");
			// 如果值为0则不显示
		else
		// 根据值的不同设定不同的背景颜色、label字体
		{
			switch(value) {
			case 2:
				this.setText("2");
				break;
			case 4:
				this.setText("4");
				break;
			case 8:
				this.setText("8");
				break;
			case 16:
				this.setText("16");
				break;
			case 32:
				this.setText("32");
				break;
			case 64:
				this.setText("64");
				break;
			case 128:
				this.setText("128");
				break;
			case 256:
				this.setText("256");
				break;
			case 512:
				this.setText("512");
				break;
			case 1024:
				this.setText("1024");
				break;
			case 2048:
				this.setText("2048");
				break;
				
			}
		}
		setColor(value);
	}

	/**
	 * 根据值的不同设定不同的背景颜色
	 */
	public void setColor(int value) 
	{
		//switch判断显示颜色
		switch(value) {
		case 0:
			this.color = Color.gray;
			
			break;
		case 2:
			this.color = Color.LIGHT_GRAY;
			break;
		case 4:
			this.color = new Color(255,250,240);
			break;
		case 8:
			this.color = new Color(255,218,185);
			break;
		case 16:
			this.color = new Color(255,160,122);
			break;
		case 32:
			this.color = new Color(255,165,0);
			break;
		case 64:
			this.color = new Color(255,69,0);
			break;
		case 128:
			this.color = new Color(205,55,0);
			break;
		case 256:
			this.color = new Color(255,0,0);
			break;
		case 512:
			this.color = new Color(205,0,0);
			break;
		case 1024:
			this.color = new Color(139,0,0);
			break;
		case 2048:
			this.color = new Color(110, 0, 0);
			break;
		
		}
		setBackground(color);
	}
}
