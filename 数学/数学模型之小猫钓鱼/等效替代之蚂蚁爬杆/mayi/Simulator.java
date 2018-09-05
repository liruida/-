package mayi;

public class Simulator {
	 
	private static final int[] DIRECTIONS = {0x01, 0x02, 0x06, 0x08, 0x10}; //它们的二进制分别是00001, 00010, 00100, 01000, 10000，你知道为什么使用它们了吧
	
	private long longest = 0;
	private long shortest = Long.MAX_VALUE;
	
	/**
	 * 开始模拟
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
	 * 创建蚂蚁初始位置
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
	 * 打印结果
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

