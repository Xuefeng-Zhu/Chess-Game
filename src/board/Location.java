package board;

/**
 * Object used to store location information of pieces
 * 
 * @author Xuefeng Zhu
 *
 */
public class Location {
	private int row;
	private int col;

	/**
	 * Construct a Location at certain row and column
	 * 
	 * @param row
	 * @param col
	 */
	public Location(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Construct a Location based on another Location
	 * 
	 * @param source
	 *            Location needed to copy
	 */
	public Location(Location source) {
		this.row = source.row;
		this.col = source.col;
	}

	/**
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Check if two Locations are equal
	 * 
	 * @param location
	 *            Location to compare with
	 * @return boolean flag
	 */
	public boolean equals(Location location) {
		return row == location.row && col == location.col;
	}

	/**
	 * Shift a Location based on rowOffset and colOffset
	 * 
	 * @param rowOffset
	 *            how many row needed to shift
	 * @param colOffset
	 *            how many column needed to shift
	 */
	public void shiftLocation(int rowOffset, int colOffset) {
		row += rowOffset;
		col += colOffset;
	}

}
