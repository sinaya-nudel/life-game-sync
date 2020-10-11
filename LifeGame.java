/* Author: Sinaya Nudel 203191663
 * Date: 18/04/18
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class LifeGame extends JPanel {
	private int[][] matrix; 
	private int sizeX; //the number of cell for each row
	private int sizeY; //the number of cell for each column
	private static final int STEPS = 1; //the number of steps from current cell to the cells that considered "neighbor"


	public LifeGame(int size_y, int size_x){
		this.sizeX=size_x;
		this.sizeY=size_y;
		matrix = new int[sizeY][sizeX];
		randMatrix(matrix); //puts a random 50:50 life or death values to the cells in the matrix. life as '1' and death as '0'.
		
	}
	public void play(){
		JFrame frame = new JFrame("Game of life");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(610, 610);
		frame.add(this);
		frame.setVisible(true);	
		
		boolean keepPlaying = true;
		while(keepPlaying){
			int reply = JOptionPane.showConfirmDialog(null,
			    "Do you want to move to the next generation?", "message",
			    JOptionPane.YES_NO_OPTION);
			if(reply == JOptionPane.YES_OPTION){
				setNextGen();
				repaint();
			}
			else
				keepPlaying=false;
		}
	
	}
	public void randMatrix(int[][] mat){   //puts a random 50:50 life or death values to the cells in the matrix. life as '1' and death as '0'.
		
		ArrayList<Integer> rand_nums = new ArrayList<Integer>(); //creating an arraylist with the same number of cells as the matrix. puts 0 or 1 50:50.
		for(int i=0;i<(this.sizeX*this.sizeY);i++){
			if(i<((this.sizeX*this.sizeY)/2))
				rand_nums.add(0);
			else
				rand_nums.add(1);
		}
		Collections.shuffle(rand_nums); //making the order of '0' and '1' random in the arraylist, and moving all the values to the matrix.
		for(int j=0;j<mat.length;j++)
			for(int i=0;i<mat[j].length;i++)
				mat[j][i]= rand_nums.remove(0);
	
	}
	
	public void setNextGen() {
		this.matrix =nextGenerationMatrix(this.matrix);
	}

	public int[][] nextGenerationMatrix(int[][] mat){ //the external method for the nextGenerationMatrix recursion. return the next generation matrix of the original matrix given as parameter.
		int[][] newMat = new int[this.sizeY][this.sizeX];
		return  nextGenerationMatrix(0,0,mat,newMat);
	}
	private int[][] nextGenerationMatrix(int i,int j,int[][] mat,int[][] newMatrix){ 
		int neighbors = howMuchNeighbors(i,j,mat,0,STEPS);
		if(isValid(i,j)){//only if the cell exist in the matrix
			if(mat[i][j]==0){
				if(neighbors==3)
					newMatrix[i][j]=1;
				else
					newMatrix[i][j]=0;
			}
			else if(mat[i][j]==1){ 
				if(neighbors<=1)
					newMatrix[i][j]=0;
				else if(neighbors>=4)
					newMatrix[i][j]=0;
				else if(neighbors==3||neighbors==2)
					newMatrix[i][j]=1;
			}
		}
		if(j==(this.sizeX-1)&&i<(this.sizeY-1)) //the end of the column- must move to the next new row if it's valid
			return nextGenerationMatrix(i+1,0,mat,newMatrix);
		else if(j<(this.sizeX-1)) // a move to the next cell in the column
			return nextGenerationMatrix(i,j+1,mat,newMatrix);
		else  //if j>SIZE-1 && i>SIZE-1  , the end of the matrix
			return newMatrix; 
	}
	
	private int howMuchNeighbors(int i,int j,int[][] mat,int count, int n){ //counts the number of neighbors for the given cell (due its indexes)
		if(isValid(i,j)&&mat[i][j]==1&&n==0)
			count=1;
		if(n!=0)
			count=howMuchNeighbors(i-1,j-1,mat,count,n-1)+howMuchNeighbors(i-1,j,mat,count,n-1)+howMuchNeighbors(i,j-1,mat,count,n-1)
			+howMuchNeighbors(i+1,j+1,mat,count,n-1)+howMuchNeighbors(i+1,j,mat,count,n-1)+howMuchNeighbors(i,j+1,mat,count,n-1)
			+howMuchNeighbors(i-1,j+1,mat,count,n-1)+howMuchNeighbors(i+1,j-1,mat,count,n-1);
		return count;
	}
	
	private boolean isValid(int i,int j){//checks if the cell is in the matrix's borders
		return (j>=0&&i>=0&&i<this.sizeY&&j<this.sizeX);
	}
	
	public void paintComponent(Graphics g) { //creating the graphics for the matrix
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		for(int i=70,z=0;z<sizeX;i+=45,z++){
			for(int j=70,x=0;x<sizeY;j+=45,x++){
				if(this.matrix[z][x]==0)
					g.drawRect(i, j, 40, 40);
				else if(this.matrix[z][x]==1)
					g.fillRect(i, j, 40, 40);
			}
		}
	}
		
}
