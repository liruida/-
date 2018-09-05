package mayi;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
	 
	public static final int MAX_LENGTH = 27; 
	
	/*
	 * ���ϳ�ʼλ��
	 */
	private static final int[] POSITIONS = {3, 7, 11, 17, 23};
	
	private int online_number = 5; //����ľ���ϵ�����������ʼΪ5
	
    private long timer = 0; //��ʱ��
    
    ArrayList<Ant> ants;
    
    /**
     * ָ�����ϳ�ʼ����, ����������
     * @param directions ���ϳ�ʼ����
     */
    public Controller(Direction[] directions){
    	ants = new ArrayList<Ant>();
    	/*
         * ������ʼ���϶���
         */
    	for( int i = 0; i < POSITIONS.length; i++ ){
    		Ant ant = new Ant(POSITIONS[i], directions[i]);
    		ants.add(ant);
    	}
    }
    
    public long start(){
    	/*
    	 * ��ľ���ϵ�������Ϊ0��ȫ������
    	 */
    	while(online_number > 0){
    		shiftAnts();
    		moveAnts();
    		timer++;
    		analyzeAntStatus();
    	}
    	return timer;
    }
    
    //�ƶ����ڸ��ϵ�����
    private void moveAnts() {
    	for (Ant ant : ants) {
    		if (!ant.isOut())
    			ant.walk();
    	}
    }
    
    //�������ϵ�״̬
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
    
    //�ƶ�ǰ�����ϵ������Ƿ���Ҫ��ͷ
    //ʹ���˼��һ���ַ������Ƿ����ظ��ַ����㷨���ռ任ʱ��
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
				flags[ant.getPosition()] = (short)i; //�����û�����������λ�ã��ͼ�����ֻ���ϵ���š�
			} else { //����Ѿ����ˣ���ֻ���ϵ�ͷ
				ant.shift();
				ants.get(result).shift();
				System.out.println("Ant " + i + " and ant " + result + " shift at " + timer);
			}
    	}

    }

	
}

