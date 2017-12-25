/**
 * point has x and y coordinates.
 */
public class Point{
    private int x, y;

    /**
     * constructor.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * get the x-coordinate.
     *
     * @return int.
     */
    public int getX() {
        return x;
    }

    /**
     * get the y-coordinate.
     *
     * @return int.
     */
    public int getY() {
        return y;
    }

    /**
     * check if this point is equal to the given object.
     *
     * @param other other object.
     * @return true if the object is point and has the same coordinates, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Point) {
            Point otherPoint = (Point) other;
            return this.x == otherPoint.getX() && this.y == otherPoint.getY();
        }
        return false;
    }
}
