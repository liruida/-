package guangmap;

public enum Direction {

    EAST(0),
    WEST(1),
    SOUTH(2),
    NORTH(3),
    UNKNOWN(4);

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
