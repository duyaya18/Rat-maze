
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 用迷宫算法，随机生成迷宫
 * 思想：生成迷宫的过程就是一个在小方格里砸墙的过程
 * 迷宫算法规则：
 *     1，只有当隔壁房间没去过的时候，墙才可以砸
 *     2, 无墙可砸的时候，就传送到一个去过的房间里
 *     3, 每一个房间都要到达
 * @author 见瑞彬
 *
 */
public class StartFrame extends JFrame implements ActionListener {

    /** 序列ID */
    private static final long serialVersionUID = 1L;
    private int frameX = 0;
    private int frameY = 0;
    private int frameW = 800;
    private int frameH = 700;

    /** 迷宫类 */
    private Maze mazeObject;
    /** 迷宫区域 */
    private Rectangle mazeArea;
    /** 按钮<<重新生成迷宫>> */
    private JButton remakeMazeButton;
    private JTextField rowCountField;
    private JTextField colCountField;
    private JLabel rowCountLabel;

    // 居中
    {
        Dimension clientSize = Toolkit.getDefaultToolkit().getScreenSize();
        frameX = (int) ((clientSize.getWidth() - frameW) / 2);//这里是设定出现在屏幕的位置
        frameY = (int) ((clientSize.getHeight() - frameH) / 2);

        // 设定迷宫
        mazeObject = new Maze();
        // 设定迷宫区域
        mazeArea = new Rectangle(20, 80, 600, 600);
    }

    public StartFrame() {

        // 设定布局
        FlowLayout flow = new FlowLayout();
        flow.setAlignment(FlowLayout.LEFT);
        this.setLayout(flow);

        // 添加按钮
        remakeMazeButton = new JButton("重新生成迷宫");
        remakeMazeButton.addActionListener(this);
        this.add(remakeMazeButton);

        // 设定水平垂直的方格数
        rowCountLabel = new JLabel("水平方格数");
        this.add(rowCountLabel);
        rowCountField = new JTextField(6);
        this.add(rowCountField);

        // 窗体属性
        this.setBounds(frameX, frameY, frameW, frameH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.validate();
    }

    /**
     * 重绘窗体
     */
    //@Override
    public void paint(Graphics g) {//这个函数无需我们主动调用
        super.paint(g);
        drawMaze(g);
    }

    /**
     * 画迷宫
     *
     * @param g
     */
    private void drawMaze(Graphics g) {
        // 迷宫线颜色
        g.setColor(Color.BLACK);

        // 画迷宫区域边线
        g.drawRect(mazeArea.x, mazeArea.y, mazeArea.width, mazeArea.height);

        // 取得每个方格宽度和高度
        int width = mazeArea.width / mazeObject.getRowCount();
        int height = mazeArea.height / mazeObject.getColCount();

        // 绘制迷宫元素
        MazeMember[][] mazeTable = mazeObject.create();
        // 设定左上角方格为起点
        mazeTable[0][0].setMazeMemberId(MazeMember.ID.VISITOR);
        // 设定右下角方格为终点
        mazeTable[mazeObject.getRowCount() - 1][mazeObject.getColCount() - 1].setMazeMemberId(MazeMember.ID.TARGET);
        // 遍历迷宫区域的所有方格
        for (int i = 0; i < mazeObject.getRowCount(); i++) {
            for (int j = 0; j < mazeObject.getColCount(); j++) {
                // 迷宫水平坐标
                int x = mazeArea.x + width * j;
                // 迷宫垂直坐标
                int y = mazeArea.y + height * i;
                // 获得迷宫方格对象
                MazeMember mazeMember = mazeTable[i][j];
                // 绘制方格中表示的边，隐藏不表示的边。
                mazeMember.drawMazeMember(g, x, y, width, height);
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        StartFrame mf = new StartFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.remakeMazeButton) {
            // 修改迷宫的水平或垂直方格数量
            try {
                mazeObject.setColCount(Integer.parseInt(rowCountField.getText()));
                mazeObject.setRowCount(Integer.parseInt(rowCountField.getText()));
            } catch (Exception e1) {
                if(mazeObject.getColCount() == 0) {
                    mazeObject.setColCount(10);
                    mazeObject.setRowCount(10);
                }
            }
            // 重绘迷宫
            this.repaint();
        }
    }

}


