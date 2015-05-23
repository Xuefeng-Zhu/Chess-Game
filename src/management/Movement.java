package management;

import board.Location;
import pieces.Piece;

public class Movement {
	public Piece piece;
	public Location preLocation;
	public Piece capturedPiece;

	public Movement(Piece piece) {
		this.piece = piece;
		this.preLocation = piece.getLocation();
	}
}
