import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

public class Pawn extends Piece {
    private boolean color = this.getColor();

    public Pawn(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }

    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList <Square> controlled = new ArrayList<Square>();
        int fwbw;

        if (color) {
            fwbw = 1;
        } else {
            fwbw = -1;
        }

        int r = start.getRow() - fwbw;
        int c = start.getCol() - fwbw;
        if ((r < 8) && (r > -1) && (c < 8) && (c > -1)) {
            controlled.add(board[r][c]);
        }

        r = start.getRow() - fwbw;
        c = start.getCol() + fwbw;
        if ((r < 8) && (r > -1) && (c < 8) && (c > -1)) {
            controlled.add(board[r][c]);
        }

        return controlled;
    }

    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList <Square> legal = new ArrayList<Square>();
        Square[][] board = b.getSquareArray();
        int fwbw;

        if (color) {
            fwbw = 1;
        } else {
            fwbw = -1;
        }

        int r = start.getRow() - fwbw;
        if ((r < 8) && (r > -1)) {
            Square s = board[r][start.getCol()];
            if (!s.isOccupied()) {
                legal.add(s);

                r = start.getRow() - (2 * fwbw);
                if ((r < 8) && (r > -1)) {
                    s = board[r][start.getCol()];
                    if (!s.isOccupied()) {
                        legal.add(s);
                    }
                }
            }
        }

        r = start.getRow() - fwbw;
        int c = start.getCol() - fwbw;
        if ((r < 8) && (r > -1) && (c < 8) && (c > -1)) {
            Square s = board[r][c];
            if (s.isOccupied()) {
                if (s.getOccupyingPiece().getColor() != color) {
                    legal.add(s);
                }
            }
        }

        r = start.getRow() - fwbw;
        c = start.getCol() + fwbw;
        if ((r < 8) && (r > -1) && (c < 8) && (c > -1)) {
            Square s = board[r][c];
            if (s.isOccupied()) {
                if (s.getOccupyingPiece().getColor() != color) {
                    legal.add(s);
                }
            }
        }

        return legal;
    }

    public String toString() {
        return "A " + super.toString() + " pawn";
    }
}