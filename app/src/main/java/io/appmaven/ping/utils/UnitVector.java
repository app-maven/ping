package io.appmaven.ping.utils;

public class UnitVector extends Vector {
    public UnitVector(int x, int y) {
        super(x, y);

        if (this.x != 0 && this.y != 0) {
            this.normalise();
        }
    }

    private void normalise(){
        int magnitude = (int)Math.sqrt(this.x * this.x + this.y * this.y);

        int unitX = this.x / magnitude;
        int unitY = this.y / magnitude;

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
