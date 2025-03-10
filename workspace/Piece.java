
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;
    
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
        
        try {
            if (this.img == null) {
              this.img = ImageIO.read(getClass().getResource(img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    
    public boolean getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }
    
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
      ArrayList<Square> controlledSquares = new ArrayList<Square>();
      ArrayList<Integer> gridRange = new ArrayList<Integer>();
      for (int i = 0; i < 8; i++) {
        gridRange.add(i);
      }

      int row = start.getRow();
      int col = start.getCol();
      int sColor;

      if (start.getOccupyingPiece().getColor()) {
        sColor = 1;
      } else {
        sColor = -1;
      }
      
      if (gridRange.contains(row + sColor)) { // One space in front
        controlledSquares.add(board[row + sColor][col]);
      }
      if (gridRange.contains(row + (2 * sColor))) { // Two spaces in front
        controlledSquares.add(board[row + (2 * sColor)][col]);
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
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    public ArrayList<Square> getLegalMoves(Square[][] board, Square start){ //Can only move up two spaces, or one space back diagonally
      ArrayList<Square> legalSquares = new ArrayList<Square>();
      ArrayList<Integer> gridRange = new ArrayList<Integer>();
      for (int i = 0; i < 8; i++) {
        gridRange.add(i);
      }

      int row = start.getRow();
      int col = start.getCol();
      boolean pColor = start.getOccupyingPiece().getColor();
      int sColor;
      Square spot;

      if (pColor) {
        sColor = -1;
      } else {
        sColor = 1;
      }

      if (gridRange.contains(row + sColor)) {
        spot = board[row + sColor][col]; // One space in front
        if (!spot.isOccupied() || (spot.isOccupied() && spot.getOccupyingPiece().getColor() != pColor)) {
          legalSquares.add(spot);
        }
      }
      if (gridRange.contains(row + (2 * sColor))) {
        spot = board[row + (2 * sColor)][col]; // Two spaces in front
        if (!spot.isOccupied() || (spot.isOccupied() && spot.getOccupyingPiece().getColor() != pColor)) {
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
}