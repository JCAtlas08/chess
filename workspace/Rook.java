// By Jasper Goldstein


// Piece: The rook
// The rook muves up and down columns and rows but can not jump over pieces
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//
public class Rook extends Piece {
  private boolean color = this.getColor();

    public Rook (boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }
  // the get controlled squares takes in a board made of a double array of type squares as well as the start square 
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
      ArrayList <Square> controlledArrayList = new ArrayList<Square>();
      for (int col = start.getCol()+1; col<8;col++){
        if(board[start.getRow()][col].isOccupied()){
          controlledArrayList.add(board[start.getRow()][col]);
          break;
          
        }
        else{
          controlledArrayList.add(board[start.getRow()][col]);
        }
      }
      for (int colB = start.getCol()-1; colB>0;colB--){
        if(board[start.getRow()][colB].isOccupied()){
          controlledArrayList.add(board[start.getRow()][colB]);
          break;
        }
        else{
          controlledArrayList.add(board[start.getRow()][colB]);
        }
      }
      for (int rowB = start.getCol()-1; rowB>0;rowB--){
        if(board[rowB][start.getCol()].isOccupied()){
          controlledArrayList.add(board[rowB][start.getCol()]);
          break;
    
        }
        else{
          controlledArrayList.add(board[rowB][start.getCol()]);
        }
      }
      for (int row = start.getRow()+1; row<8;row++){
        if(board[row][start.getCol()].isOccupied()){
          controlledArrayList.add(board[row][start.getCol()]);
          break;
        }
        else{
          controlledArrayList.add(board[row][start.getCol()]);
        }
      }
      
    return controlledArrayList;
  }
     
    

    //PRECONDITION This function takes the current board as an input as well the peices current ssquare 
    //Postcondition it retuns an array list that has every possible move for the rook
    public ArrayList<Square> getLegalMoves(Board b, Square start){
      // THE ROOK SACRIFICE: moves in lines up and down and side to side and cant jump
      
     Square [][] board = b.getSquareArray();
      ArrayList<Square> spaces  = new ArrayList<Square>();
      //right
        for (int col = start.getCol()+1; col<8;col++){
          if(board[start.getRow()][col].isOccupied()){
            if(board[start.getRow()][col].getOccupyingPiece().getColor() == color){
                break;
            }
            else{
            spaces.add(board[start.getRow()][col]);
            break;
            }
          }
          else{
            spaces.add(board[start.getRow()][col]);
          }
        }
        // //left
        for (int colB = start.getCol()-1; colB>=0;colB--){
          if(board[start.getRow()][colB].isOccupied()){
            if(board[start.getRow()][colB].getOccupyingPiece().getColor() == color){
              break;
          }
        
            spaces.add(board[start.getRow()][colB]);
            break;
      
          }
          else{
            spaces.add(board[start.getRow()][colB]);
          }
        }
        //up
        for (int rowB = start.getRow()-1; rowB>=0;rowB--){
          if(board[rowB][start.getCol()].isOccupied()){
            if(board[rowB][start.getCol()].getOccupyingPiece().getColor() == color){
              break;
          }
        
            spaces.add(board[rowB][start.getCol()]);
            break;
      
          }
          else{
            spaces.add(board[rowB][start.getCol()]);
          }
        }
        // //down
        for (int row = start.getRow()+1; row<8;row++){
          if(board[row][start.getCol()].isOccupied()){
            if(board[row][start.getCol()].getOccupyingPiece().getColor() == color){
              break;
          }
            spaces.add(board[row][start.getCol()]);
            break;
          }
          else{
            spaces.add(board[row][start.getCol()]);
          }
        }
        
    	return spaces;
    }
      // color has been established
    public String toString(){

        return "A " + super.toString() + " rook";

    }
  }