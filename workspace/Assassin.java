
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

//Joshua Campbell

//Assassin
//The assassin works like a pon, capturing diagonally, however, only being able to do so backwards.
//The assassin may also move 1-2 spaces forward, and 1 space back.
//    []      ([] = movable squares)
//    []
//    ()      (() = player position)
//  XXXXXX    (XX = capturable/moveable squares)

public class Assassin extends Piece {
  public Assassin(boolean isWhite, String img_file) {
    super(isWhite, img_file);
  }

  //Precondition: 2d array of type board and the square of the placed last clicked (not null)
  //Postcondition: returns an arrayList containing every space that the piece could capture
  public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
    ArrayList<Square> controlledSquares = new ArrayList<Square>();
    ArrayList<Integer> gridRange = new ArrayList<Integer>();
    for (int i = 0; i < 8; i++) {
      gridRange.add(i);
    }

    int row = start.getRow();
    int col = start.getCol();
    int sColor;

    if (this.getColor()) {
      sColor = -1;
    } else {
      sColor = 1;
    }
    
    if (gridRange.contains(row - sColor)) { // One space back
      controlledSquares.add(board[row - sColor][col]);
    }
    if (gridRange.contains(row - sColor) && gridRange.contains(col - 1)) { // One space back diagonally (left)
      controlledSquares.add(board[row - sColor][col - 1]);
    }
    if (gridRange.contains(row - sColor) && gridRange.contains(col + 1)) { // One space back diagonally (right)
      controlledSquares.add(board[row - sColor][col + 1]);
    }
    
    return controlledSquares;
  }
  
  //Precondition: the current board with all positions and the square of the placed last clicked (not null)
  //Postcondition: returns an arrayList containing every space that the piece can legally move into/capture
  public ArrayList<Square> getLegalMoves(Board b, Square start){ 
    Square[][] board = b.getSquareArray();
    ArrayList<Square> legalSquares = new ArrayList<Square>();
    ArrayList<Integer> gridRange = new ArrayList<Integer>();
    for (int i = 0; i < 8; i++) {
      gridRange.add(i);
    }

    int row = start.getRow();
    int col = start.getCol();
    boolean pColor = this.getColor();
    int sColor;
    Square spot;

    if (pColor) {
      sColor = -1;
    } else {
      sColor = 1;
    }

    if (gridRange.contains(row + sColor)) {
      spot = board[row + sColor][col]; // One space in front
      if (!spot.isOccupied()) {
        legalSquares.add(spot);
      }
    }
    if (gridRange.contains(row + (2 * sColor))) {
      spot = board[row + (2 * sColor)][col]; // Two spaces in front
      if (!spot.isOccupied()) {
        legalSquares.add(spot);
      }
    }

    if (gridRange.contains(row - sColor)) {
      spot = board[row - sColor][col]; // One space back
      if (!spot.isOccupied() || (spot.isOccupied() && spot.getOccupyingPiece().getColor() != pColor)) {
        legalSquares.add(spot);
      }
    }
    if (gridRange.contains(row - sColor) && gridRange.contains(col - 1)) {
      spot = board[row - sColor][col - 1]; // One space back diagonally (left)
      if (!spot.isOccupied() || (spot.isOccupied() && spot.getOccupyingPiece().getColor() != pColor)) {
        legalSquares.add(spot);
      }
    }
    if (gridRange.contains(row - sColor) && gridRange.contains(col + 1)) {
      spot = board[row - sColor][col + 1]; // One space back diagonally (right)
      if (!spot.isOccupied() || (spot.isOccupied() && spot.getOccupyingPiece().getColor() != pColor)) {
        legalSquares.add(spot);
      }
    }

    return legalSquares;
  }

  //Precondition: none
  //Postcondition: returns a string containing another string from parent class piece
  public String toString() {
    return "A " + super.toString() + " Assassin";
  }
}