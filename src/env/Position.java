package env;

/**
 * Represents a single point in 2D space
 * @author Adrian Cheung
 */
public class Position {
	private double xPos;
	private double yPos;
	
	/**
	 * Create a position object
	 * @param xPos
	 * @param yPos
	 */
	public Position(double xPos, double yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	// getters and setters
	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	/**
	 * Get the distance between this point and otherP
	 * @param otherP: Another Position object
	 * @return Distance between this position and otherP
	 */
	public double getDist(Position otherP){
		double dx, dy;
		dx = this.xPos - otherP.xPos;
		dy = this.yPos - otherP.yPos;
		return Math.sqrt(Math.pow(dx,2) + Math.pow(dy, 2));
	}
	
}
