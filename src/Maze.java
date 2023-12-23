
import java.util.ArrayList;
import java.util.List;

/**
 * 迷宫对象
 *
 * @author 见瑞彬
 *
 */
public class Maze {

    /** 迷宫成员对象数组 */
    private MazeMember[][] mazeTable;
    /** 迷宫成员对象水平数量 */
    private int rowCount;
    /** 迷宫成员对象垂直数量 */
    private int colCount;
    /** 迷宫访问者垂直方向坐标 */
    private int visitorVertical = 0;
    /** 迷宫访问者水平方向坐标 */
    private int visitorHorizontal = 0;

    public Maze() {
        // 初始化方格数量 5*5
        rowCount = 5;
        colCount = 5;
    }

    /**
     * 创建迷宫
     *
     * @return
     */
    public MazeMember[][] create() {
        // 初始化迷宫的每一个成员对象
        mazeTable = new MazeMember[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                mazeTable[i][j] = new MazeMember();
                mazeTable[i][j].setTopLineShow(true);
                mazeTable[i][j].setBottomLineShow(true);
                mazeTable[i][j].setLeftLineShow(true);
                mazeTable[i][j].setRightLineShow(true);
            }
        }

        // 初始化迷宫访问者位置（0,0）
        visitorVertical = 0;
        visitorHorizontal = 0;
        mazeTable[0][0].setVisited(true);

        // 遍历所有迷宫成员对象，并进行迷宫成员对象的推墙操作
        while (!hasFinishMazeTable()) {
            // 迷宫访问者前进到下一步
            boolean visitorMoveSuccess = moveToNextStep(visitorVertical, visitorHorizontal);
            // 迷宫访问者前进失败场合
            if (!visitorMoveSuccess) {
                // 重新定位迷宫访问者的位置
                // 跳跃到曾经访问过的迷宫成员对象的位置
                updateVisitorPoint();
            }
        }

        // 迷宫成员对象数组
        return mazeTable;
    }

    /**
     * 迷宫访问者是否访问完所有迷宫成员对象
     *
     * @return
     */
    private boolean hasFinishMazeTable() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (!mazeTable[i][j].isVisited()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 抹去相邻的没有访问过的成员边界，并更新访问者位置
     *
     * @param nowLocation
     * @return
     */
    private boolean moveToNextStep(int vertical, int horizontal) {
        // 存储当前迷宫访问者位置的周围的上下左右四个位置
        // 避免随机到重复的位置
        List<Integer> locationLib = new ArrayList<>();

        // 遍历周围
        while (true) {
            // 所有相邻位置已经遍历完成，该成员不存在未访问过的相邻成员，终止遍历
            if (locationLib.size() == 4) {
                return false;
            }

            // 随机生成一个迷宫成员的上下左右相邻成员的序号
            // 0上 1下 2左 3右
            int locIndex = (int) (Math.random() * 4);

            // 下一个相邻位置
            if (!locationLib.contains(locIndex)) {
                locationLib.add(locIndex);

                // 上
                if (locIndex == 0) {
                    // 移动到上面一个位置
                    if (moveTop(vertical, horizontal)) {
                        return true;
                    }

                    // 下
                } else if (locIndex == 1) {
                    // 移动到下面一个位置
                    if (moveBottom(vertical, horizontal)) {
                        return true;
                    }

                    // 左
                } else if (locIndex == 2) {
                    // 移动到左面一个位置
                    if (moveLeft(vertical, horizontal)) {
                        return true;
                    }

                    // 右
                } else {
                    // 移动到右面一个位置
                    if (moveRight(vertical, horizontal)) {
                        return true;
                    }

                }
            }
        }
    }

    /**
     * 防止迷宫访问者出界
     *
     * @param vertical
     * @param horizontal
     * @return
     */
    private boolean isRightPoint(int vertical, int horizontal) {
        // 不出界，返回true
        return vertical >= 0 && vertical < rowCount && horizontal >= 0 && horizontal < colCount;
        // 出界返回false
    }

    /**
     * 向上移动
     *
     * @param vertical
     * @param horizontal
     * @return
     */
    private boolean moveTop(int vertical, int horizontal) {
        int nextVertical = vertical - 1;
        int nextHorizontal = horizontal;

        if (isRightPoint(nextVertical, nextHorizontal)) {
            // 未访问过场合
            if (!mazeTable[nextVertical][nextHorizontal].isVisited()) {
                // 上面的边隐藏
                mazeTable[vertical][horizontal].setTopLineShow(false);
                // 相邻成员对象，下面的边隐藏
                mazeTable[nextVertical][nextHorizontal].setBottomLineShow(false);
                // 相邻成员对象，标记为已访问
                mazeTable[nextVertical][nextHorizontal].setVisited(true);
                // 更新迷宫访问者位置
                visitorVertical = nextVertical;
                visitorHorizontal = nextHorizontal;
                return true;
            }
        }
        return false;
    }

    /**
     * 向下移动
     *
     * @param vertical
     * @param horizontal
     * @return
     */
    private boolean moveBottom(int vertical, int horizontal) {
        int nextVertical = vertical + 1;
        int nextHorizontal = horizontal;

        if (isRightPoint(nextVertical, nextHorizontal)) {
            // 未访问过场合
            if (!mazeTable[nextVertical][nextHorizontal].isVisited()) {
                // 下面的边隐藏
                mazeTable[vertical][horizontal].setBottomLineShow(false);
                // 相邻成员对象，上面的边隐藏
                mazeTable[nextVertical][nextHorizontal].setTopLineShow(false);
                // 相邻成员对象，标记为已访问
                mazeTable[nextVertical][nextHorizontal].setVisited(true);
                // 更新迷宫访问者位置
                visitorVertical = nextVertical;
                visitorHorizontal = nextHorizontal;
                return true;
            }
        }
        return false;
    }

    /**
     * 向左移动
     *
     * @param vertical
     * @param horizontal
     * @return
     */
    private boolean moveLeft(int vertical, int horizontal) {
        int nextVertical = vertical;
        int nextHorizontal = horizontal - 1;

        if (isRightPoint(nextVertical, nextHorizontal)) {
            // 未访问过场合
            if (!mazeTable[nextVertical][nextHorizontal].isVisited()) {
                // 左面的边隐藏
                mazeTable[vertical][horizontal].setLeftLineShow(false);
                // 相邻成员对象，右面的边隐藏
                mazeTable[nextVertical][nextHorizontal].setRightLineShow(false);
                // 相邻成员对象，标记为已访问
                mazeTable[nextVertical][nextHorizontal].setVisited(true);
                // 更新迷宫访问者位置
                visitorVertical = nextVertical;
                visitorHorizontal = nextHorizontal;
                return true;
            }
        }
        return false;
    }

    /**
     * 向左移动
     *
     * @param vertical
     * @param horizontal
     * @return
     */
    private boolean moveRight(int vertical, int horizontal) {
        int nextVertical = vertical;
        int nextHorizontal = horizontal + 1;

        if (isRightPoint(nextVertical, nextHorizontal)) {
            // 未访问过场合
            if (!mazeTable[nextVertical][nextHorizontal].isVisited()) {
                // 右面的边隐藏
                mazeTable[vertical][horizontal].setRightLineShow(false);
                // 相邻成员对象，左面的边隐藏
                mazeTable[nextVertical][nextHorizontal].setLeftLineShow(false);
                // 相邻成员对象，标记为已访问
                mazeTable[nextVertical][nextHorizontal].setVisited(true);
                // 更新迷宫访问者位置
                visitorVertical = nextVertical;
                visitorHorizontal = nextHorizontal;
                return true;
            }
        }
        return false;
    }

    /**
     * 迷宫访问者的位置，随机更新到曾经访问过的成员对象的位置
     *
     * @return
     */
    private void updateVisitorPoint() {

        while (true) {
            int visitorVertical = (int) (Math.random() * rowCount);
            int visitorHorizontal = (int) (Math.random() * colCount);
            // 已访问
            if (mazeTable[visitorVertical][visitorHorizontal].isVisited()) {
                // 更新位置
                this.visitorVertical = visitorVertical;
                this.visitorHorizontal = visitorHorizontal;
                // 标记已访问
                mazeTable[visitorVertical][visitorHorizontal].setVisited(true);
                break;
            }
        }
    }

    /**
     * 取得垂直数量
     *
     * @return 垂直数量
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * 修改垂直数量
     *
     * @param 垂直数量
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * 取得水平数量
     *
     * @return 水平数量
     */
    public int getColCount() {
        return colCount;
    }

    /**
     * 修改水平数量
     *
     * @param 水平数量
     */
    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

}

