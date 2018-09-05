package mayi;

public class Ant {
	private int position; //���ϵ�λ��
	Direction direction;  //���з���
	private boolean out = false; //�Ƿ��Ѿ�����
 
	public Ant(int p, Direction dir){
		this.position = p;
		this.direction = dir;
	}
 
	/**
	 * ��������
	 */
	public void walk(){
		if(direction == Direction.Right){
			position++;
		}else{
			position--;
		}
	}
	
	/**
	 * ���ϵ�ͷ
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

