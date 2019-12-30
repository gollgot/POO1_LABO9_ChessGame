package engine;

class Vertical implements Move{

    private Direction direction;

    Vertical (Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction isValid(int fromX, int fromY, int toX, int toY, int distance) {
        // todo : La j'ai mis en dur ca check que pour aller en haut pour les blancs
       // int multiplier = 1;
        int gap = toY - fromY;
        if(fromX == toX && gap <= distance && gap >= 0){
            return Direction.UP;
        }else{
            return Direction.INVALID;
        }



    }
}
