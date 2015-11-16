import javax.swing.ImageIcon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;


public class City implements Serializable
{
	//variables
	public String cityName;

	public int x;
	public int y;
	private int locationX;
	private int locationY;
	private int production = 0;
	private int maxProductionNumber = 0;
	private int population = 1;
	private int food = 0;
	private int maxFoodNumber = 0;
	private int health = 0;
	private int maxHealthNumber = 0;
	private int foodForGrowth = 10;
	private int storedFood = 0;
	private int turnsUntilGrowth = 0;
	private int influence = 1;

	private boolean focus = false;
	private boolean productionFocus = false;
	private boolean foodFocus = false;
	private boolean healthFocus = false;
	private boolean[] focusSelection = new boolean[3];

	public ImageIcon cityImage = new ImageIcon(getClass().getResource("/Images/Units/city.png"));

	private ArrayList<Terrain> cityTiles = new ArrayList<Terrain>();
	private ArrayList<Terrain> workedTiles = new ArrayList<Terrain>();

	//user class obejects 	

	//constructor
	public City(int spawnX, int spawnY,ArrayList<Terrain> newCityTiles)
	{
		x = spawnX;
		y = spawnY;
		locationX = x;
		locationY = y;

		cityName = "";

		for(int i = 0; i < newCityTiles.size(); i++)
		{
			cityTiles.add(newCityTiles.get(i));
		}
		for(int i = 0; i < 3; i++)
		{
			focusSelection[i] = false;
		}
		foodFocus = true;
		setTileYields();
		turnsUntilGrowth = ((foodForGrowth*population)/food) +1;		
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
	public void setInfluence(int newInfluence)
	{
		influence = newInfluence;
	}
	public void setCityName(String newName)
	{
		cityName = newName;
	}	
	public void setFocus(boolean newFocus)
	{
		focus = newFocus;
	}
	public void setProductionFocus(boolean newFocus)
	{
		productionFocus = newFocus;
	}
	public void setFoodFocus(boolean newFocus)
	{
		foodFocus = newFocus;
	}
	public void setHealthFocus(boolean newFocus)
	{
		healthFocus = newFocus;
	}
	public void setCityTiles(Terrain newCityTile)
	{
		cityTiles.add(newCityTile);
	}
	public void setProduction()
	{
		if(productionFocus == true)
		{
			production = 0;			
			workedTiles.clear();

			//arranges tiles based on max to high terrain values
			Collections.sort(cityTiles,new TerrainComp("Production"));			
			
			//adds correct worked tiles
			for(int i = 0; i < population; i++)
			{
				workedTiles.add(cityTiles.get(i));
			}
			//determines the amount of food that can be worked
			for(int i = 0; i < workedTiles.size(); i++)
			{				
				production += workedTiles.get(i).getProduction();
			}
			maxProductionNumber++;
		}
		else
		{
			production = 0;
			//if(maxFoodNumber < 1 || maxHealthNumber < 1)
				//production = 0;			
			for(int i = 0; i < workedTiles.size(); i++)
			{
				//if(maxFoodNumber > 1 || maxHealthNumber > 1)
					//break;				
				production += workedTiles.get(i).getProduction();
			}
			maxProductionNumber = 0;
		}
	}
	public void setFood()
	{
		if(foodFocus == true)
		{
			food = 0;			
			workedTiles.clear();

			Collections.sort(cityTiles,new TerrainComp("Food"));					
			
			//adds correct worked tiles
			for(int i = 0; i < population; i++)
			{
				workedTiles.add(cityTiles.get(i));
			}
			//determines the amount of food that can be worked
			for(int i = 0; i < workedTiles.size(); i++)
			{				
				food += workedTiles.get(i).getFood();
			}
			maxFoodNumber++;
		}
		else
		{
			//if(maxProductionNumber < 1 || maxHealthNumber < 1)
			food = 0;			
			for(int i = 0; i < workedTiles.size(); i++)
			{
				//if(maxProductionNumber > 1 || maxHealthNumber > 1)
					//break;								
				food += workedTiles.get(i).getFood();
			}
			maxFoodNumber = 0;
		}
		
	}
	public void setHealth()
	{		
		if(healthFocus == true)
		{
			health = 0;			
			workedTiles.clear();

			Collections.sort(cityTiles,new TerrainComp("Health"));					
			
			//adds correct worked tiles
			for(int i = 0; i < population; i++)
			{
				workedTiles.add(cityTiles.get(i));
			}
			//determines the amount of food that can be worked
			for(int i = 0; i < workedTiles.size(); i++)
			{				
				health += workedTiles.get(i).getHealth();
			}
			maxHealthNumber++;
		}
		else
		{
			health = 0;
			//if(maxProductionNumber < 1 || maxFoodNumber < 1)
				//health = 0;			
			for(int i = 0; i < workedTiles.size(); i++)
			{
				//if(maxFoodNumber > 1 || maxProductionNumber > 1)
					//break;				
				health += workedTiles.get(i).getHealth();
			}
			maxHealthNumber = 0;			
		}		
	}
	
	public void setTileYields()
	{
		if(productionFocus == true)
		{
			setProduction();
			setFood();
			setHealth();
			focusSelection[0] = true;
			focusSelection[1] = false;
			focusSelection[2] = false;
			setTurnsUntilGrowth();			
		}
		else if(foodFocus == true)
		{
			setFood();
			setProduction();		
			setHealth();
			focusSelection[0] = false;
			focusSelection[1] = true;
			focusSelection[2] = false;
			setTurnsUntilGrowth();
		}

		else if(healthFocus == true)
		{
			setHealth();
			setFood();
			setProduction();
			focusSelection[0] = false;
			focusSelection[1] = false;
			focusSelection[2] = true;
			setTurnsUntilGrowth();
		}		

		setTileYieldFalse();
	}
	public void setTileYieldFalse()
	{
		productionFocus = false;
		foodFocus = false;
		healthFocus = false;
	}
	public void setPopulation(int newPop)
	{
		population = newPop;
	}
	public void setTurnsUntilGrowth()
	{
		if(storedFood == 0)
			turnsUntilGrowth = ((foodForGrowth*population)/food) +1;
		else
			turnsUntilGrowth = ((foodForGrowth*population)/storedFood)+1;
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
	public int getLocationX()
	{
		return locationX;
	}
	public int getLocationY()
	{
		return locationY;
	}
	public int getInfluence()
	{
		return influence;
	}
	public int getProduction()
	{
		return production;
	}
	public int getFood()
	{
		return food;
	}
	public int getHealth()
	{
		return health;
	}
	public int getPopulation()
	{
		return population;
	}
	public int getTurnsUntilGrowth()
	{
		return turnsUntilGrowth;		
	}	
	public boolean getFocus()
	{
		return focus;
	}
	public boolean getProductionFocus()
	{
		return productionFocus;
	}
	public boolean getFoodFocus()
	{
		return foodFocus;
	}
	public boolean getHealthFocus()
	{
		return healthFocus;
	}	
	public ImageIcon getCityImage()
	{
		return cityImage;
	}	
	public String getName()
	{
		return cityName;
	}

	//other methods
	public void checkGrowth()
	{
		storedFood += food;

		if(storedFood >= foodForGrowth*population)
		{
			population++;
			storedFood = 0;

			setTurnsUntilGrowth();
			
			if(focusSelection[0] == true)
				productionFocus = true;
			else if(focusSelection[1] == true)
				foodFocus = true;
			else if(focusSelection[2] == true)
				healthFocus = true;
			setTileYields();
		}
		else
			turnsUntilGrowth--;
					
	}			
}
class TerrainComp implements Comparator<Terrain>
{ 
	private String s;

	public TerrainComp(String s1)
	{
		s = s1;
	}
    public int compare(Terrain t1, Terrain t2) 
    {
    	if(s == "Food")
    	{
    		if(t1.getFood() < t2.getFood())
    		{
            	return 1;
        	} 
        	else 
        	{
            	return -1;
        	}
    	}
    	if(s == "Production")
    	{
    		if(t1.getProduction() < t2.getProduction())
    		{
            	return 1;
        	} 
        	else 
        	{
            	return -1;
        	}
    	}
    	if(s == "Health")
    	{
    		if(t1.getHealth() < t2.getHealth())
    		{
            	return 1;
        	} 
        	else 
        	{
            	return -1;
        	}
    	}
    	return -1;        	
   	 }
}
