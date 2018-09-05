package mayi;

public enum Direction {

	Left(0),
        Right(1),
	UNKNOWN(2);
    int index;

    public int getIndex() {
        return index;
    }

    Direction(int index) {
        this.index = index;
    }

    static Direction getDirectionByIndex(int index){
        for (Direction direction : Direction.values()) {
            if(direction.index == index){
                return direction;
            }
        }
        return UNKNOWN;
    }

}
