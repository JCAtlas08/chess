

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";

    private static final String RESOURCES_BASS_PNG = "bass.png";
    private static final String RESOURCES_WASS_PNG = "wass.png";
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    private Piece currPiece;
    private Square fromMoveSquare;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    
    //used to track the history of every piece that's been on a square
    private ArrayList<Piece>[][] history;
    private int track = 0;

    //
    private ArrayList<Square> legal;

    
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];

        history = new ArrayList[8][8]; //All of this junk is to initialize each arraylist in all 64 squares
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                history[r][c] = new ArrayList<Piece>();
            }
        }

        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //TO BE IMPLEMENTED FIRST
        for (int num1 = 0; num1 < 8; num1++) {
            for (int num2 = 0; num2 < 8; num2++) {
                boolean bColor = !(((num1 % 2 == 0) && !(num2 % 2 == 0)) || (!(num1 % 2 == 0) && (num2 % 2 == 0)));
                board[num1][num2]= new Square(this, bColor, num1, num2);
                this.add(board[num1][num2]);
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
    }

    
	//set up the board such that the black pieces are on one side and the white pieces are on the other.
	//since we only have one kind of piece for now you need only set the same number of pieces on either side.
	//it's up to you how you wish to arrange your pieces.
    private void initializePieces() {
        //Place pieces
    	board[0][0].put(new Rook(false, RESOURCES_BROOK_PNG));
    	board[0][1].put(new Assassin(false, RESOURCES_BKNIGHT_PNG));
        board[0][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][3].put(new Assassin(false, RESOURCES_BQUEEN_PNG));
        board[0][4].put(new King(false, RESOURCES_BKING_PNG));
        board[0][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][6].put(new Assassin(false, RESOURCES_BKNIGHT_PNG));
        board[0][7].put(new Rook(false, RESOURCES_BROOK_PNG));

        board[1][0].put(new Pawn(false, RESOURCES_BPAWN_PNG));
    	board[1][1].put(new Pawn(false, RESOURCES_BPAWN_PNG));
        board[1][2].put(new Pawn(false, RESOURCES_BPAWN_PNG));
        board[1][3].put(new Assassin(false, RESOURCES_BASS_PNG));
        board[1][4].put(new Assassin(false, RESOURCES_BASS_PNG));
        board[1][5].put(new Pawn(false, RESOURCES_BPAWN_PNG));
        board[1][6].put(new Pawn(false, RESOURCES_BPAWN_PNG));
        board[1][7].put(new Pawn(false, RESOURCES_BPAWN_PNG));

        board[6][0].put(new Pawn(true, RESOURCES_WPAWN_PNG));
    	board[6][1].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        board[6][2].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        board[6][3].put(new Assassin(true, RESOURCES_WASS_PNG));
        board[6][4].put(new Assassin(true, RESOURCES_WASS_PNG));
        board[6][5].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        board[6][6].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        board[6][7].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        
        board[7][0].put(new Rook(true, RESOURCES_WROOK_PNG));
    	board[7][1].put(new Assassin(true, RESOURCES_WKNIGHT_PNG));
        board[7][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][3].put(new Assassin(true, RESOURCES_WQUEEN_PNG));
        board[7][4].put(new King(true, RESOURCES_WKING_PNG));
        board[7][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][6].put(new Assassin(true, RESOURCES_WKNIGHT_PNG));
        board[7][7].put(new Rook(true, RESOURCES_WROOK_PNG));

        //Board History
        recordHistory();
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Assassin p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
     
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if(sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
            }
        }
    	if (currPiece != null) {
            if ((currPiece.getColor() == whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            if (sq.getOccupyingPiece().getColor() == whiteTurn) {
                currPiece = sq.getOccupyingPiece();
                fromMoveSquare = sq;
                if (!currPiece.getColor() && whiteTurn)
                    return;
                if (currPiece.getColor() && !whiteTurn)
                    return;

                legal = currPiece.getLegalMoves(this, fromMoveSquare);

                /*
                int i = 0;
                while (i < legal.size()) {
                    if (checkForCheck(legal.get(i), currPiece.getColor())) {
                        legal.remove(i);
                        continue;
                    }
                    i++;
                }
                */

                sq.setDisplay(false);
            }
        }
        repaint();
    }

    //TO BE IMPLEMENTED!
    //should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to it's new board location. 
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        
        if (currPiece != null) {
            if (legal.contains(endSquare)) { //If a legal move
                Piece pred = fromMoveSquare.removePiece(); // !!!Still removes the piece that was originally there!!!
                Piece prey = endSquare.getOccupyingPiece(); // !!!Does nothing to the endSquare!!!

                endSquare.put(pred);

                if (isInCheck(currPiece.getColor())) { //If the move you just did put your king in check, undo
                    fromMoveSquare.put(pred);
                    endSquare.put(prey);
                    //DOES NOT CHANGE THE PIECE HISTORY

                } else {
                    if (track < history[0][0].size() - 1) { //If there was an undo made
                        clearHistory(track); //Clear the history past the index
                    }

                    //If no check, no change | Piece History
                    recordHistory();
                    track++; //Move on in time (index) through the piece history

                    whiteTurn = !whiteTurn; //End turn
                }
            }

            for(Square [] row: board) { //Reset borders
                for(Square s: row) {
                    s.setBorder(null);
                }
            }
        
            fromMoveSquare.setDisplay(true);
            currPiece = null;
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        if (currPiece != null) {
            for(Square s: legal) { //Display all legal moves
                s.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            }
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    //precondition - the board is initialized and contains a king of either color. The boolean kingColor corresponds to the color of the king we wish to know the status of.
    //postcondition - returns true of the king is in check and false otherwise.
	public boolean isInCheck(boolean kingColor){
        Square king = null;

		int row = 0;
        int col = 0;
        while (row < 8) { //Find king
            if (col > 7) {
                col = 0;
                row++;
                continue;
            }

            Square checkSquare = board[row][col];
            if (checkSquare.isOccupied() && ((checkSquare.getOccupyingPiece() instanceof King) && (checkSquare.getOccupyingPiece().getColor() == kingColor))) {
                king = checkSquare;
                break;
            }
            
            col++;
        }

        row = 0;
        col = 0;
        while (row < 8) {
            if (col > 7) {
                col = 0;
                row++;
                continue;
            }

            Square tempSquare = board[row][col];
            if (tempSquare.isOccupied() && (tempSquare.getOccupyingPiece().getColor() != kingColor)) {
                ArrayList<Square> tempS = tempSquare.getOccupyingPiece().getControlledSquares(board, tempSquare);
                
                if (tempS.contains(king)) {
                    return true;
                }
            }

            col++;
        }

        return false; //If the king is safe
    }

    /*
    public boolean checkForCheck(Square s, boolean c) {
        Piece temp = fromMoveSquare.getOccupyingPiece();
        board[fromMoveSquare.getRow()][fromMoveSquare.getCol()].put(null);
        Square king = null;

		int row = 0;
        int col = 0;
        while (row < 8) { //Find king
            if (col > 7) {
                col = 0;
                row++;
                continue;
            }

            Square checkSquare = board[row][col];
            if (checkSquare.isOccupied() && ((checkSquare.getOccupyingPiece() instanceof King) && (checkSquare.getOccupyingPiece().getColor() == c))) {
                king = checkSquare;
                break;
            }
            
            col++;
        }

        row = 0;
        col = 0;
        while (row < 8) {
            if (col > 7) {
                col = 0;
                row++;
                continue;
            }

            Square tempSquare = board[row][col];
            if (tempSquare.isOccupied() && tempSquare.getOccupyingPiece().getColor() != c) {
                ArrayList<Square> tempS = tempSquare.getOccupyingPiece().getControlledSquares(board, tempSquare);
                
                if (tempS.contains(king)) {
                    board[fromMoveSquare.getRow()][fromMoveSquare.getCol()].put(temp);
                    return true;
                }
            }

            col++;
        }
        board[fromMoveSquare.getRow()][fromMoveSquare.getCol()].put(temp);
        return false;
    }
    */

    public void clearHistory(int idx) {
        int temp = history[0][0].size() - (idx + 1);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                for (int i = 0; i < temp; i++) {
                    history[r][c].removeLast(); //Removes the last item in the arraylist
                }
            }
        }
    }

    public void undo() {
        if (track > 0) {
            track--;

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    Square s = board[r][c];

                    if (getPieceInHistory(s, track) != null) {
                        board[r][c].put(history[r][c].get(track));

                    } else {
                        board[r][c].put(null);
                    }
                }
            }

            whiteTurn = !whiteTurn;
        }

        repaint();
    }

    public void redo() {
        if (track < history[0][0].size() - 1) {
            track++;

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    Square s = board[r][c];

                    if (getPieceInHistory(s, track) != null) {
                        board[r][c].put(history[r][c].get(track));

                    } else {
                        board[r][c].put(null);
                    }
                }
            }

            whiteTurn = !whiteTurn;
        }

        repaint();
    }

    public void recordHistory() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[r][c].isOccupied()) {
                    history[r][c].add(board[r][c].getOccupyingPiece());
                } else {
                    history[r][c].add(null);
                }
            }
        }
    }

    public Piece getPieceInHistory(Square s, int t) {
        ArrayList<Piece> p = history[s.getRow()][s.getCol()];
        
        return p.get(t);
    }
}