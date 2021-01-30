/**
 * Ryan Bradley
 * CS4306
 * Prof. Ahmed
 * Programming Assignment 1
 */
import java.util.Random;

public class Main
{
	//Constant variable to determine grid size
	private static final int GRID_SIZE = 10;
	//The 2D integer array for cells' states; 0 = dead, 1 = alive
	private static int[][] cellStates = new int[GRID_SIZE][GRID_SIZE];
	//A variable to keep track of generations
	private static int generation = 1;
	//Variables to keep track of time
	private static long startTime = System.currentTimeMillis();
	private static long previousTime = startTime;
	
	private static void initializeGrid()
	{
		Random random = new Random();
		for (int i = 0; i < GRID_SIZE; i++)
		{
			for (int j = 0; j < GRID_SIZE; j++)
			{
				int state = 0 + (int)(Math.random() * ((1 - 0) + 1));
				cellStates[i][j] = state;
			}
		}
	}
	
	//Prints each cell to the console for display
	private static void displayGrid()
	{
		System.out.println("[Generation]: " + generation);
		for (int i = 0; i < GRID_SIZE; i++)
		{
			for (int j = 0; j < GRID_SIZE; j++)
			{
				if (cellStates[i][j] == 1)
					System.out.print("#");
				else
					System.out.print("_");
				if (j < (GRID_SIZE - 1))
					System.out.print(" ");
			}
			System.out.println();
		}
		long now = System.currentTimeMillis();
		System.out.println("Time to execute: " + (now - previousTime) + "ms | Total time: " + (now - startTime) + "ms\n");
		previousTime = now;
	}
	
	//Determines the state of cells
	private static void survivalCheck()
	{
		int[][] temporaryStates = new int[GRID_SIZE][GRID_SIZE];
		
		for (int i = 0; i < GRID_SIZE; i++)
		{
			for (int j = 0; j < GRID_SIZE; j++)
			{
				try {
					int livingNeighbors = 0;
					
					//Checking each of the eight possible neighbors
					for (int k = -1; k <= 1; k++)
						for (int l = -1; l <= 1; l++)
							if (k != i && l != j)
								livingNeighbors += cellStates[i + k][j + l];
					//Avoid adding the cell we're checking
					livingNeighbors -= cellStates[i][j];
					
					//Cell is born
					if (cellStates[i][j] == 0 && livingNeighbors == 3)
						temporaryStates[i][j] = 1;
					//Cell dies to overpopulation or starvation
					else if (cellStates[i][j] == 1 && (livingNeighbors > 3 || livingNeighbors < 2))
						temporaryStates[i][j] = 0;
					else
						temporaryStates[i][j] = cellStates[i][j];
				}
				catch (Exception e)
				{
					//This catches any errors from out-of-bounds neighbors
				}
			}
		}
		cellStates = temporaryStates;
		generation++;
	}
	
	//Main method
	public static void main(String args[])
	{
		long startTime = System.currentTimeMillis();
		//10 generations
		initializeGrid();
		for (int i = 0; i < 9; i++)
			survivalCheck();
		displayGrid();
		for (int j = 1; j < 5; j++)
		{
			for (int i = 0; i < 10; i++)
				survivalCheck();
			displayGrid();
		}
	}
}
