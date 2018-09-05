package mayi;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
	 
	public static final int MAX_LENGTH = 27; 
	
	/*
	 * 蚂蚁初始位置
	 */
	private static final int[] POSITIONS = {3, 7, 11, 17, 23};
	
	private int online_number = 5; //还在木杆上的蚂蚁数，初始为5
	
    private long timer = 0; //计时器
    
    ArrayList<Ant> ants;
    
    /**
     * 指定蚂蚁初始方向, 创建控制器
     * @param directions 蚂蚁初始方向
     */
    public Controller(Direction[] directions){
    	ants = new ArrayList<Ant>();
    	/*
         * 创建初始蚂蚁队列
         */
    	for( int i = 0; i < POSITIONS.length; i++ ){
    		Ant ant = new Ant(POSITIONS[i], directions[i]);
    		ants.add(ant);
    	}
    }
    
    public long start(){
    	/*
    	 * 在木杆上的蚂蚁数为0即全部爬出
    	 */
    	while(online_number > 0){
    		shiftAnts();
    		moveAnts();
    		timer++;
    		analyzeAntStatus();
    	}
    	return timer;
    }
    
    //移动还在杆上的蚂蚁
    private void moveAnts() {
    	for (Ant ant : ants) {
    		if (!ant.isOut())
    			ant.walk();
    	}
    }
    
    //分析蚂蚁的状态
    private void analyzeAntStatus() {
    	for (Ant ant : ants) {
    		if (ant.isOut()) {
    			continue;
    		}
    		if ((ant.getPosition() == MAX_LENGTH) ||
    				(ant.getPosition() == 0)) {
    			ant.setOut(true);
    			online_number--;
    			System.out.println("Ant " + ants.indexOf(ant) + " is out at " + timer);
    		}
    	}
    }
    
    //移动前检查杆上的蚂蚁是否需要调头
    //使用了检查一串字符串中是否有重复字符的算法，空间换时间
    private void shiftAnts(){
    	short[] flags = new short[28];
    	Arrays.fill(flags, (short)-1);
    	
    	for (int i = 0; i < ants.size(); i++) {
    		Ant ant = ants.get(i);
    		/*if (ant.isOut()) {
    			continue;
    		}*/
    		short result = flags[ant.getPosition()];
			if(result == -1) {
				flags[ant.getPosition()] = (short)i; //如果还没有蚂蚁在这个位置，就记下这只蚂蚁的序号。
			} else { //如果已经有了，两只蚂蚁调头
				ant.shift();
				ants.get(result).shift();
				System.out.println("Ant " + i + " and ant " + result + " shift at " + timer);
			}
    	}

    }

	
}

