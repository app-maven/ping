package io.appmaven.ping.utils;

public class UnitVector extends Vector {
    public UnitVector(int x, int y) {
        super(x, y);

        this.normalise();
    }

    private void normalise(){
        int magnitude = (int)Math.sqrt(this.x * this.x + this.y * this.y);

        int unitX = this.x / magnitude;
        int unitY = this.x / magnitude;

        this.x = (unitX);
        this.y = (unitY);
    }

    public UnitVector reflectX() {
        return new UnitVector(-this.x, this.y);
    }

    public UnitVector reflectY() {
        return new UnitVector(this.x, -this.y);
    }
}
