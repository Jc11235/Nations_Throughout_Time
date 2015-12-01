import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.io.Serializable;

public class Unit implements Serializable
{
	//variables
	public String unitName;
	public String specialAbility;

	public int x;
	public int y;	
	public int movement;
	public int maxMovement;
	public int strength;
	public int health;
	public int productionCost;
	private int destinationX = x;
	private int destinationY = y;

	public ArrayList<String> nonAccessibleTerrainType;
	public ArrayList<String> nonAccessibleTerrainFeatures;

	private boolean focus = false;
	public boolean specialAbilityActivate = false;

	public ImageIcon unitImage;

	//user class obejects 	

	//constructor
	public Unit()
	{		
		
	}
	//setters
	public void setX(int newX)
	{
		x = newX;
	}
	public void setY(int newY)
	{
		y = newY;
	}
	public void setDestinationX(int newX)
	{
		destinationX = newX;
	}
	public void setDestinationY(int newY)
	{
		destinationY = newY;
	}
	public void setFocus(boolean newFocus)
	{
		focus = newFocus;
	}
	public void setMovement(int newMovement)
	{
		movement = newMovement;
	}
	public void setSpecialAbilityActivate(boolean newBool)
	{
		specialAbilityActivate = newBool;
	}

	
	//getters
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getMovement()
	{
		return movement;
	}
	public int getMaxMovement()
	{
		return maxMovement;
	}
	public int getDestinationX()
	{
		return destinationX;
	}
	public int getDestinationY()
	{
		return destinationY;
	}
	public boolean getFocus()
	{
		return focus;
	}
	public boolean getSpecialAbilityActivate()
	{
		return specialAbilityActivate;
	}
	public ImageIcon getUnitImage()
	{
		return unitImage;
	}	
	public String getName()
	{
		return unitName;
	}
	public String getSpecialAbility()
	{
		return specialAbility;
	}
	public int getStrength()
	{
		return strength;
	}
	public int getHealth()
	{
		return health;
	}
	public int getProductionCost()
	{
		return productionCost;
	}
	public ArrayList<String> getNonAccessibleTerrainType()
	{
		return nonAccessibleTerrainType;
	}
	public ArrayList<String> getNonAccessibleTerrainFeatures()
	{
		return nonAccessibleTerrainFeatures;
	}

	//additional methods
	public void moveUnit(int destX, int destY)
	{
		destinationX = destX;
		destinationY = destY;

		int mapX = (int)Math.floor((x/75));
		int mapY = (int)Math.floor((y/75));

		int travelX = destinationX - mapX;
		int travelY = destinationY - mapY;

		int tempX = 0;
		int tempY = 0;

		//diagonals
		if(destinationX <= mapX+movement && destinationY <= mapY+movement)
		{
			while(movement > 0)
			{
				tempX = (int)Math.floor((x/75));
				tempY = (int)Math.floor((y/75));

				if(destinationX == tempX && destinationY == tempY)
				{
					break;
				}
					
				if(destX > tempX && destY > tempY)
				{
					x += 75;
					y += 75; 
				}
				else if(destX > tempX && destY < tempY)
				{
					x += 75;
					y -= 75; 
				}
				else if(destX < tempX && destY < tempY)
				{	
					x -= 75;
					y -= 75; 
				}
				else if(destX < tempX && destY > tempY)
				{
					x -= 75;
					y += 75; 
				}
				//vertical
				else if(destX == tempX && destY < tempY)
				{
					y -= 75; 
				}	
				else if(destX == tempX&& destY > tempY)
				{
					y += 75; 
				}
				//horizontal
				else if(destX > tempX && destY == tempY)
				{
					x += 75; 
				}
				else if(destX < tempX && destY == tempY)
				{
					x -= 75; 
				}
			}						
		}
	}		
}