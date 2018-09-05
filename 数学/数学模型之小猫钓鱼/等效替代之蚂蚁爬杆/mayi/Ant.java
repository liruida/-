package mayi;

public class Ant {
	private int position; //蚂蚁的位置
	Direction direction;  //爬行方向
	private boolean out = false; //是否已经爬出
 
	public Ant(int p, Direction dir){
		this.position = p;
		this.direction = dir;
	}
 
	/**
	 * 蚂蚁行走
	 */
	public void walk(){
		if(direction == Direction.Right){
			position++;
		}else{
			position--;
		}
	}
	
	/**
	 * 蚂蚁调头
	 */
	public void shift(){
		this.direction = (this.direction == Direction.Left) ? Direction.Right : Direction.Left;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public boolean isOut() {
		return out;
	}
 
	public void setOut(boolean out) {
		this.out = out;
	}

}

