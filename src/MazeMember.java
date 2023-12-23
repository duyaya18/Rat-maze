
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MazeMember {
    public static enum ID {
        // 一般
        NORMAL,
        // 起点访问者
        VISITOR,
        // 终点目标
        TARGET
    };

    /** 是否访问过 */
    private boolean visited;
    /** 是否显示上面的边 */
    private boolean topLineShow;
    /** 是否显示下面的边 */
    private boolean bottomLineShow;
    /** 是否显示左面的边 */
    private boolean leftLineShow;
    /** 是否显示右面的边 */
    private boolean rightLineShow;
    /** 定义成员的身份 */
    private ID mazeMemberId;

    public MazeMember() {
        // 默认所有成员为一般
        mazeMemberId = ID.NORMAL;
    }

    public void drawMazeMember(Graphics g, int x, int y, int width, int height) {
        // 绘画上面的边
        if (topLineShow) {
            g.drawLine(x, y, x + width, y);
        }
        // 绘画下面的边
        if (bottomLineShow) {
            g.drawLine(x, y + height, x + width, y + height);
        }
        // 绘画左面的边
        if (leftLineShow) {
            g.drawLine(x, y, x, y + height);
        }
        // 绘画右面的边
        if (rightLineShow) {
            g.drawLine(x + width, y, x + width, y + height);
        }

        // 设定字体
        Font font = g.getFont();
        g.setFont(new Font(font.getFamily(), font.getStyle(), 10));

        // 绘画起点图案
        if (this.getMazeMemberId() == ID.VISITOR) {
            Color c = g.getColor();
            g.setColor(Color.GRAY);
            g.fillRect(x, y, width, height);
            g.setColor(c);
        }
        // 绘画终点图案
        if (this.getMazeMemberId() == ID.TARGET) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.fillRect(x, y, width, height);
            g.setColor(c);
        }

        // 还原字体
        g.setFont(font);
    }

    /**
     * @return the mazeMemberId
     */
    public ID getMazeMemberId() {
        return mazeMemberId;
    }

    /**
     * @param mazeMemberId the mazeMemberId to set
     */
    public void setMazeMemberId(ID mazeMemberId) {
        this.mazeMemberId = mazeMemberId;
    }

    /**
     * @return the visited
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * @param visited the visited to set
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * @param topLineShow the topLineShow to set
     */
    public void setTopLineShow(boolean topLineShow) {
        this.topLineShow = topLineShow;
    }

    /**
     * @param bottomLineShow the bottomLineShow to set
     */
    public void setBottomLineShow(boolean bottomLineShow) {
        this.bottomLineShow = bottomLineShow;
    }

    /**
     * @param leftLineShow the leftLineShow to set
     */
    public void setLeftLineShow(boolean leftLineShow) {
        this.leftLineShow = leftLineShow;
    }

    /**
     * @param rightLineShow the rightLineShow to set
     */
    public void setRightLineShow(boolean rightLineShow) {
        this.rightLineShow = rightLineShow;
    }

}

