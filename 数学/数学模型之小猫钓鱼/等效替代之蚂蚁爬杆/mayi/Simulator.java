package mayi;

public class Simulator {
	 
	private static final int[] DIRECTIONS = {0x01, 0x02, 0x06, 0x08, 0x10}; //���ǵĶ����Ʒֱ���00001, 00010, 00100, 01000, 10000����֪��Ϊʲôʹ�������˰�
	
	private long longest = 0;
	private long shortest = Long.MAX_VALUE;
	
	/**
	 * ��ʼģ��
	 */
	public void simulate() {
		for (int i = 0; i < 32; i++) {
			Controller con = new Controller(this.getDirections(i));
			long time = con.start();
			if( time > longest ){
				longest = time;
			}
			if( time < shortest ){
				shortest = time;
			}
			System.out.println( " Time: " + time );
		}
	}
 
	/*
	 * �������ϳ�ʼλ��
	 */
	private Direction[] getDirections(int seed) {
		Direction[] dirs = new Direction[5];
		for(int i = 0; i < DIRECTIONS.length; i++) {
			if((DIRECTIONS[i] & seed) == 0) {
				dirs[i] = Direction.Left;
			} else {
				dirs[i] = Direction.Right;
			}
		}
		
		System.out.println( "Round: " + Integer.toBinaryString(seed));
 
		return dirs;
	}
 
	/**
	 * ��ӡ���
	 */
	public void getResult() {
		System.out.printf("Longest time %d.\nShortest Time: %d", longest,
				shortest);
	}
 
	public static void main( String[] args ){
		Simulator sim = new Simulator();
		
		sim.simulate();
		
		sim.getResult();
	}

}

