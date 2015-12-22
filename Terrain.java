import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;



public class Terrain implements Serializable
{
	private String terrainType;
	private String terrainFeatures;
	private String terrainResources;

	private int locationX;
	private int locationY;
	private int mapHeight;
	private int mapWidth;
	private int movementCost;
	private int food;
	private int production;
	private int health;
	private int science;

	private  Random r = new Random();

	private ImageIcon terrainPicture;
	private ImageIcon terrainFeaturesPicture;
	private ImageIcon terrainResourcePicture;

	private boolean visable;

	public Terrain(int x, int y, int height, int width)
	{
		mapHeight = height;
		mapWidth = width;
		visable = false;
	}

////////////////////////////////////////////////////////////////////////// getters

	public boolean getVisability()
	{
		return visable;
	}
	public String getTerrainType()
	{
		return terrainType;
	}
	public String getTerrainFeatures()
	{
		return terrainFeatures;
	}
	public String getTerrainResources()
	{
		return terrainResources;
	}
	public ImageIcon getTerrainImage()
	{
		return terrainPicture;
	}
	public ImageIcon getTerrainFeaturesImage()
	{
		return terrainFeaturesPicture;
	}
	public ImageIcon getTerrainResourceImage()
	{
		return terrainResourcePicture;
	}
	public int getFood()
	{
		return food;
	}
	public int getProduction()
	{
		return production;
	}
	public int getHealth()
	{
		return health;
	}
	public int getScience()
	{
		return science;
	}
	public int getMovementCost()
	{
		return movementCost;
	}
////////////////////////////////////////////////////////////////////////// setters
	public void setVisability(boolean newVisability)
	{
		visable = newVisability;
	}
	public void setFood(int newFood)
	{
		food = newFood;
	}
	public void setProduction(int newProduction)
	{
		production = newProduction;
	}
	public void setHealth(int newHealth)
	{
		health = newHealth;
	}
	public void setScience(int newScience)
	{
		science = newScience;
	}
	public void setTerrainType(String s)
	{
		terrainType = s;
	}
	public void setTerrainFeatures(String s)
	{
		terrainFeatures = s;
	}
	public void setTerrainResources(String s)
	{
		terrainResources = s;
	}
	//creates the actual terrain layout
	public void setTerrain(int x,int y,String terrainOption)
	{
		//Terrain Types: 1. Desert 2. Jungle 3. Plains 4. Water 5. Tundra 6. Marshland
		//Terrain Features Types: 1. Mountains 2. Hills 3. Glaciers 4. Icebergs 5. Forest
		//Terrain Resource Types: (land resources (production)) 1. Stone 2. Iron 3. Copper 4. Tin 5. Aluminum 6. Coal 7. Uranium 8. Oil 9. Natural Gas
		//Terrain Resource Types: (land resources (food)) 1. Cows 2. Deer 3. Pigs 4. Chickens 5. Bananas 6. Peaches 7. Apples
		//Terrain Resource Types: (land resources (luxury)) 1. Tea 2. Tobacco 3. Silk 4. Cotton 5. Gems 6. Gold 7. Silver
		//Terrain Resource Types: (water resources (food)) 1. Fish 2. Crab 3. Shrimp 4. Clams 
		//Terrain Resource Types: (water resources (luxury)) 1. Pearls
		int distanceAway;

		locationY = y;
		locationX = x;

		int terrainTypeSelection = r.nextInt(20);

		if(terrainOption.equals("Pangea"))
		{
			if( y <=  3 || y >= mapHeight - 3 && x > 1 && x < mapWidth-2)
			{
				//sets the terrain type
				if( terrainTypeSelection == 0)
					terrainType = "Plains";
				else if(terrainTypeSelection >=1 && terrainTypeSelection < 15)
					terrainType = "Water";
				else if(terrainTypeSelection >=15 && terrainTypeSelection < 21)
					terrainType = "Tundra";								
			}
			else if( (y >=4 && y <= 6) || (y >=9 && y < mapHeight-3) && x > 1 && x < mapWidth-2)
			{
				if( terrainTypeSelection <= 1)
					terrainType = "Desert";								
				else if( terrainTypeSelection >= 2 && terrainTypeSelection < 4)
					terrainType = "Jungle";								
				else if(terrainTypeSelection >=4 && terrainTypeSelection < 13)
					terrainType = "Plains";							
				else if(terrainTypeSelection >=13 && terrainTypeSelection < 15)
					terrainType = "Marshland";							
				else if(terrainTypeSelection >=15 && terrainTypeSelection < 21)
					terrainType = "Water";		
			}
			else if( y >=7 && y <9 && x > 1 && x < mapWidth-2)
			{
				if( terrainTypeSelection <= 4)
					terrainType = "Plains";								
				else if( terrainTypeSelection >= 4 && terrainTypeSelection < 6)
					terrainType = "Marshland";						
				else if(terrainTypeSelection >=6 && terrainTypeSelection < 10)
					terrainType = "Desert";								
				else if(terrainTypeSelection >=10 && terrainTypeSelection < 15)
					terrainType = "Jungle";
				else if(terrainTypeSelection >=15 && terrainTypeSelection < 21)
					terrainType = "Water";			
			}
			else if(y > -1 && y < mapHeight && x < 2 || x > mapWidth-3)
			{
				terrainType = "Water";
			}
		}
		else if(terrainOption.equals("Archipelago"))
		{
			if( y <=  3 || y >= mapHeight - 3 && x > 1 && x < mapWidth-2)
			{
				//sets the terrain type
				if( terrainTypeSelection == 0)
					terrainType = "Plains";
				else if(terrainTypeSelection >=1 && terrainTypeSelection < 15)
					terrainType = "Water";
				else if(terrainTypeSelection >=15 && terrainTypeSelection < 21)
					terrainType = "Tundra";								
			}
			else if( (y >=4 && y <= 6) || (y >=9 && y < mapHeight-3) && x > 1 && x < mapWidth-2)
			{
				if( terrainTypeSelection <= 1)
					terrainType = "Desert";								
				else if( terrainTypeSelection >= 2 && terrainTypeSelection < 4)
					terrainType = "Jungle";								
				else if(terrainTypeSelection >=4 && terrainTypeSelection < 9)
					terrainType = "Plains";							
				else if(terrainTypeSelection >=9 && terrainTypeSelection < 12)
					terrainType = "Marshland";							
				else if(terrainTypeSelection >=12 && terrainTypeSelection < 21)
					terrainType = "Water";		
			}
			else if( y >=7 && y <9 && x > 1 && x < mapWidth-2)
			{
				if( terrainTypeSelection <= 3)
					terrainType = "Plains";								
				else if( terrainTypeSelection >= 3 && terrainTypeSelection < 6)
					terrainType = "Marshland";						
				else if(terrainTypeSelection >=6 && terrainTypeSelection < 8)
					terrainType = "Desert";								
				else if(terrainTypeSelection >=8 && terrainTypeSelection < 11)
					terrainType = "Jungle";
				else if(terrainTypeSelection >=11 && terrainTypeSelection < 21)
					terrainType = "Water";			
			}
			else if(y > -1 && y < mapHeight && x < 2 || x > mapWidth-3)
			{
				terrainType = "Water";
			}
		}	

		setTerrainFeatures(x,y);		
	}
	public void setTerrainFeatures(int x, int y)
	{
		int terrainFeaturesSelection = r.nextInt(19);
		int terrainResourceSelection = r.nextInt(100);

		if( y <=  3 || y >= mapHeight - 3 && x > 1 && x < mapWidth-2)
		{
			//sets the terrain type
			if(terrainType == "Plains")
			{
				terrainResources = ""; //temp measure
			}		
			//sets terrain features
			if(terrainType == "Water")
			{
				if(terrainFeaturesSelection > -1 && terrainFeaturesSelection < 5)
				{
					terrainResources = ""; //temp measure
					terrainFeatures = "Iceberg";
				}										
				else if(terrainFeaturesSelection > 4 && terrainFeaturesSelection < 7)
				{
					terrainResources = ""; //temp measure
					terrainFeatures = "Glacier";
				}					
				else if(terrainFeatures != "Iceberg" || terrainFeatures != "Glacier")
				{
					terrainFeatures = "";	

					//sets terrain resources
					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Pearls";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Fish";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Crab";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Shrimp";
					else if(terrainResourceSelection >= 12 && terrainResourceSelection < 15)
						terrainResources = "Clams";
					else if(terrainResourceSelection >= 15)
						terrainResources = "";
				}								
			}
			else if(terrainType == "Tundra")
			{
				//sets terrain features
				//hill
				if(terrainFeaturesSelection > -1 && terrainFeaturesSelection < 7)
				{
					terrainFeatures = "Hill";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Deer";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Oil";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Natural Gas";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 12 && terrainResourceSelection < 15)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 15 && terrainResourceSelection < 18)
						terrainResources = "Natural Gas";
					else if(terrainResourceSelection >= 18)
						terrainResources = "";
				}
				//mountain					
				else if(terrainFeaturesSelection >= 7 && terrainFeaturesSelection < 10)
				{
					terrainFeatures = "Mountain";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Coal";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 9)
						terrainResources = "";
				}	
				//forest					
				else if(terrainFeaturesSelection >= 10 && terrainFeaturesSelection < 18)
				{
					terrainFeatures = "Forest";	

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Deer";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Oil";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Natural Gas";
					else if(terrainResourceSelection >= 9)
						terrainResources = "";
				}	
				//no features				
				else if(terrainFeaturesSelection >= 18)
				{
					terrainFeatures = "";					
					
					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Oil";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Natural Gas";
					else if(terrainResourceSelection >= 6)
						terrainResources = "";
				}							
			}
			else if((terrainType != "Water") && (terrainType != "Tundra"))
			{
				terrainFeatures = "";
				terrainResources = "";
			}				
		}
		else if( (y >=4 && y <= 6) || (y >=9 && y < mapHeight-3) && x > 1 && x < mapWidth-2)
		{			

			if(terrainType == "Desert")
			{
				if(terrainFeaturesSelection > -1 && terrainFeaturesSelection < 6)
				{
					terrainFeatures = "Hill";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Natural Gas";
					else if(terrainResourceSelection >= 9)
						terrainResources = "";
				}					
				else if(terrainFeaturesSelection >= 6 && terrainFeaturesSelection < 8)
				{
					terrainFeatures = "Mountain";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Aluminum";
					else if(terrainResourceSelection >= 9)
						terrainResources = "";
				}						
				else if(terrainFeaturesSelection >= 8)
				{
					terrainFeatures = "";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Natural Gas";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Oil";
					else if(terrainResourceSelection >= 12)
						terrainResources = "";
				}
			}				
			else if(terrainType == "Jungle")
			{
				if(terrainFeaturesSelection > -1 && terrainFeaturesSelection < 3)
				{
					terrainFeatures = "Hill";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Coal";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Aluminum";
					else if(terrainResourceSelection >= 12 && terrainResourceSelection < 15)
						terrainResources = "Tin";
					else if(terrainResourceSelection >= 15 && terrainResourceSelection < 18)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 18 && terrainResourceSelection < 21)
						terrainResources = "Banana";
					else if(terrainResourceSelection >= 21 && terrainResourceSelection < 24)
						terrainResources = "Pig";
					else if(terrainResourceSelection >= 24 && terrainResourceSelection < 27)
						terrainResources = "Cows";
					else if(terrainResourceSelection >= 27 && terrainResourceSelection < 30)
						terrainResources = "Tea";
					else if(terrainResourceSelection >= 30 && terrainResourceSelection < 33)
						terrainResources = "Silk";
					else if(terrainResourceSelection >= 33 && terrainResourceSelection < 36)
						terrainResources = "Gems";
					else if(terrainResourceSelection >= 36 && terrainResourceSelection < 39)
						terrainResources = "Gold";
					else if(terrainResourceSelection >= 39 && terrainResourceSelection < 42)
						terrainResources = "Silver";
					else if(terrainResourceSelection >= 42)
						terrainResources = "";
				}					
				else if(terrainFeaturesSelection >= 3 && terrainFeaturesSelection < 5)
				{
					terrainFeatures = "Mountain";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Coal";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Aluminum";
					else if(terrainResourceSelection >= 12 && terrainResourceSelection < 15)
						terrainResources = "Tin";
					else if(terrainResourceSelection >= 15 && terrainResourceSelection < 18)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 18)
						terrainResources = "";
				}					
				else if(terrainFeaturesSelection >= 5)
				{
					terrainFeatures = "";
					terrainResources = "";
				}
			}				
			else if(terrainType == "Plains")
			{
				if(terrainFeaturesSelection > -1 && terrainFeaturesSelection < 3)
				{
					terrainFeatures = "Hill";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Tin";
					else if(terrainResourceSelection >= 12)
						terrainResources = "";
				}					
				else if(terrainFeaturesSelection >= 3 && terrainFeaturesSelection < 5)
				{
					terrainFeatures = "Mountain";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Tin";
					else if(terrainResourceSelection >= 12)
						terrainResources = "";
				}					
				else if(terrainFeaturesSelection >= 5)
				{
					terrainFeatures = "";
					terrainResources = "";
				}						
			}				
			else if(terrainType == "Marshland")
			{
				terrainFeatures = "";
				terrainResources = "";
			}				
			if(terrainType == "Water")
			{				
				terrainFeatures = "";	

				//sets terrain resources
				if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
					terrainResources = "Pearls";
				else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
					terrainResources = "Fish";
				else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
					terrainResources = "Crab";
				else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
					terrainResources = "Shrimp";
				else if(terrainResourceSelection >= 12 && terrainResourceSelection < 15)
					terrainResources = "Clams";
				else if(terrainResourceSelection >= 15)
					terrainResources = "";
			}											
		}
		else if(y >=7 && y <9 && x > 1 && x < mapWidth-2)
		{
			if(terrainType == "Plains")
			{
				if(terrainFeaturesSelection > -1 && terrainFeaturesSelection < 3)
				{
					terrainFeatures = "Hill";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Tin";
					else if(terrainResourceSelection >= 12)
						terrainResources = "";
				}					
				else if(terrainFeaturesSelection >= 3 && terrainFeaturesSelection < 5)
				{
					terrainFeatures = "Mountain";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Tin";
					else if(terrainResourceSelection >= 12)
						terrainResources = "";
				}					
				else if(terrainFeaturesSelection >= 5)
				{
					terrainFeatures = "";
					terrainResources = "";
				}
			}				
			else if(terrainType == "Marshland")
			{
				terrainFeatures = "";
				terrainResources = "";
			}				
			else if(terrainType == "Desert")
			{
				if(terrainFeaturesSelection > -1 && terrainFeaturesSelection < 6)
				{
					terrainFeatures = "Hill";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Natural Gas";
					else if(terrainResourceSelection >= 9)
						terrainResources = "";
				}					
				else if(terrainFeaturesSelection >= 6 && terrainFeaturesSelection < 8)
				{
					terrainFeatures = "Mountain";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Iron";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Aluminum";
					else if(terrainResourceSelection >= 9)
						terrainResources = "";
				}						
				else if(terrainFeaturesSelection >= 8)
				{
					terrainFeatures = "";

					if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
						terrainResources = "Stone";
					else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
						terrainResources = "Copper";
					else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
						terrainResources = "Natural Gas";
					else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
						terrainResources = "Oil";
					else if(terrainResourceSelection >= 12)
						terrainResources = "";
				}
			}				
			else if(terrainType == "Jungle")
			{
				if(terrainFeaturesSelection > -1 && terrainFeaturesSelection < 3)
				{
					terrainFeatures = "Hill";										
				}					
				else if(terrainFeaturesSelection >= 3 && terrainFeaturesSelection < 5)
				{
					terrainFeatures = "Mountain";					
				}					
				else if(terrainFeaturesSelection >= 5)
				{
					terrainFeatures = "";					
				}

				if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
					terrainResources = "Stone";
				else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
					terrainResources = "Iron";
				else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
					terrainResources = "Coal";
				else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
					terrainResources = "Aluminum";
				else if(terrainResourceSelection >= 12 && terrainResourceSelection < 15)
					terrainResources = "Tin";
				else if(terrainResourceSelection >= 15 && terrainResourceSelection < 18)
					terrainResources = "Copper";
				else if(terrainResourceSelection >= 18 && terrainResourceSelection < 21)
					terrainResources = "Banana";
				else if(terrainResourceSelection >= 21 && terrainResourceSelection < 24)
					terrainResources = "Pig";
				else if(terrainResourceSelection >= 24 && terrainResourceSelection < 27)
					terrainResources = "Cows";
				else if(terrainResourceSelection >= 27 && terrainResourceSelection < 30)
					terrainResources = "Tea";
				else if(terrainResourceSelection >= 30 && terrainResourceSelection < 33)
					terrainResources = "Silk";
				else if(terrainResourceSelection >= 33 && terrainResourceSelection < 36)
					terrainResources = "Gems";
				else if(terrainResourceSelection >= 36 && terrainResourceSelection < 39)
					terrainResources = "Gold";
				else if(terrainResourceSelection >= 39 && terrainResourceSelection < 42)
					terrainResources = "Silver";
				else if(terrainResourceSelection >= 42)
					terrainResources = "";
			}
			if(terrainType == "Water")
			{
				terrainFeatures = "";	

				//sets terrain resources
				if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
					terrainResources = "Pearls";
				else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
					terrainResources = "Fish";
				else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
					terrainResources = "Crab";
				else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
					terrainResources = "Shrimp";
				else if(terrainResourceSelection >= 12 && terrainResourceSelection < 15)
					terrainResources = "Clams";
				else if(terrainResourceSelection >= 15)
					terrainResources = "";
			}			
			else if((terrainType != "Desert") && (terrainType != "Jungle") && (terrainType != "Plains"))
			{
				terrainFeatures = "";
				terrainResources = "";
			}
		}
		else if(x < 2 || x > mapWidth-3)
		{
			if(terrainType == "Water")
			{
				terrainFeatures = "";	

				//sets terrain resources
				if(terrainResourceSelection > -1 && terrainResourceSelection < 3)
					terrainResources = "Pearls";
				else if(terrainResourceSelection >= 3 && terrainResourceSelection < 6)
					terrainResources = "Fish";
				else if(terrainResourceSelection >= 6 && terrainResourceSelection < 9)
					terrainResources = "Crab";
				else if(terrainResourceSelection >= 9 && terrainResourceSelection < 12)
					terrainResources = "Shrimp";
				else if(terrainResourceSelection >= 12 && terrainResourceSelection < 15)
					terrainResources = "Clams";
				else if(terrainResourceSelection >= 15)
					terrainResources = "";
			}
		}
	}
	//paints the physical terrain layout
	public void setTerrainPhysical(Terrain[][] terrainBase, int x, int y, int mapHeight, int mapWidth)
	{
		//Terrain Types: 1. Desert 2. Jungle 3. Plains 4. Water 5. Tundra 6. Marshland
		//Terrain Features Types: 1. Mountains 2. Hills 3. Glaciers 4. Icebergs 5. Forest

		if(terrainType == "Desert")
			terrainPicture = new ImageIcon(getClass().getResource("/Images/Terrain/desert1.png"));
		else if(terrainType == "Jungle")
			terrainPicture = new ImageIcon(getClass().getResource("/Images/Terrain/jungle1.png"));
		else if(terrainType == "Plains")
			terrainPicture = new ImageIcon(getClass().getResource("/Images/Terrain/plains1.png"));
		else if(terrainType == "Water")
		{	
			boolean coastLine = false;	

			for(int i = y - 1; i < y + 2; i++)
			{
				for(int j = x - 1; j < x + 2; j++)
				{
					if(i > -1 && i < mapHeight && j > -1 && j < mapWidth)
					{
						if(!(terrainBase[i][j].getTerrainType().equals("Water")))
						{
							coastLine = true;
							break;
						}						
					}
				}
			}
			if(coastLine == true)
			{
				terrainPicture = new ImageIcon(getClass().getResource("/Images/Terrain/coast.png"));
				terrainType = "Coast";
			}
				
			else
				terrainPicture = new ImageIcon(getClass().getResource("/Images/Terrain/ocean.png"));						
		}			
		else if(terrainType == "Tundra")
			terrainPicture = new ImageIcon(getClass().getResource("/Images/Terrain/tundra1.png"));
		else if(terrainType == "Marshland")
			terrainPicture = new ImageIcon(getClass().getResource("/Images/Terrain/marsh1.png"));		

		if(terrainFeatures == "Mountain")
			terrainFeaturesPicture = new ImageIcon(getClass().getResource("/Images/Terrain/mountain.png"));
		else if(terrainFeatures == "Hill")
			terrainFeaturesPicture = new ImageIcon(getClass().getResource("/Images/Terrain/hill.png"));
		else if(terrainFeatures == "Iceberg")
			terrainFeaturesPicture = new ImageIcon(getClass().getResource("/Images/Terrain/iceberg.png"));
		else if(terrainFeatures == "Glacier")
			terrainFeaturesPicture = new ImageIcon(getClass().getResource("/Images/Terrain/glacier.png"));
		else if(terrainFeatures == "Forest")
			terrainFeaturesPicture = new ImageIcon(getClass().getResource("/Images/Terrain/forest.png"));
		else if(terrainFeatures != "Mountain" || terrainFeatures != "Forest" || terrainFeatures != "Glaciers" || terrainFeatures != "Hill" ||terrainFeatures != "Iceberg")
			terrainFeaturesPicture = terrainPicture;

		//Terrain Resource Types: (land resources (production)) 1. Stone 2. Iron 3. Copper 4. Tin 5. Aluminum 6. Coal 7. Uranium 8. Oil 9. Natural Gas
		//Terrain Resource Types: (land resources (food)) 1. Cows 2. Deer 3. Pigs 4. Chickens 5. Bananas 6. Peaches 7. Apples
		//Terrain Resource Types: (land resources (luxury)) 1. Tea 2. Tobacco 3. Silk 4. Cotton 5. Gems 6. Gold 7. Silver
		//Terrain Resource Types: (water resources (food)) 1. Fish 2. Crab 3. Shrimp 4. Clams 
		//Terrain Resource Types: (water resources (luxury)) 1. Pearls

		if(terrainResources == "Stone")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/stone.png"));
		else if(terrainResources == "Iron")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/iron.png"));
		else if(terrainResources == "Copper")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/copper.png"));
		else if(terrainResources == "Tin")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/tin.png"));
		else if(terrainResources == "Aluminum")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/aluminum.png"));
		else if(terrainResources == "Coal")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/coal.png"));
		else if(terrainResources == "Uranium")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/uranium.png"));
		else if(terrainResources == "Oil")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/oil.png"));
		else if(terrainResources == "Natural Gas")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/naturalgas.png"));
		else if(terrainResources == "Cows")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/cow.png"));
		else if(terrainResources == "Deer")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/deer.png"));
		else if(terrainResources == "Pig")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/pig.png"));
		else if(terrainResources == "Chickens")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/chicken.png"));
		else if(terrainResources == "Banana")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/banana.png"));
		else if(terrainResources == "Peaches")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/peach.png"));
		else if(terrainResources == "Apples")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/apple.png"));
		else if(terrainResources == "Tobacco")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/tobacco.png"));
		else if(terrainResources == "Tea")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/tea.png"));
		else if(terrainResources == "Cotton")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/cotton.png"));
		else if(terrainResources == "Silk")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/silk.png"));
		else if(terrainResources == "Gems")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/gems.png"));
		else if(terrainResources == "Gold")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/gold.png"));
		else if(terrainResources == "Silver")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/silver.png"));
		else if(terrainResources == "Fish")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/fish.png"));
		else if(terrainResources == "Crab")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/crab.png"));
		else if(terrainResources == "Shrimp")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/shrimp.png"));
		else if(terrainResources == "Clams")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/clams.png"));
		else if(terrainResources == "Pearls")
			terrainResourcePicture = new ImageIcon(getClass().getResource("/Images/Terrain/Resources/pearls.png"));
		else if(terrainResources == "")
			terrainResourcePicture = terrainFeaturesPicture;		
	}
	//sets the terrain stats
	public void setTerrainSettings()
	{
		//base terrain values
		if(terrainType == "Desert")
		{
			movementCost = 1;
			food = 1;
			production = 1;
			health = -1;
			science = 1;
		}
		else if(terrainType == "Jungle")
		{
			movementCost = 2;
			food = 3;
			production = 2;
			health = -1;
			science = 2;
		}
		else if(terrainType == "Plains")
		{
			movementCost = 1;
			food = 2;
			production = 3;
			health = 0;
			science = 1;
		}
		else if(terrainType == "Water" || terrainType == "Coast")
		{
			movementCost = 1;
			food = 3;
			production = 1;
			health = +1;
			science = 2;
		}
		else if(terrainType == "Tundra")
		{
			movementCost = 1;
			food = 1;
			production = 1;
			health = 0;
			science = 1;
		}
		else if(terrainType == "Marshland")
		{
			movementCost = 2;
			food = 1;
			production = 1;
			health = -1;
			science = 1;
		}

		if(terrainFeatures == "Mountain")
			movementCost = movementCost + 3;
		else if(terrainFeatures == "Hill")
			movementCost = movementCost + 1;
		else if(terrainFeatures == "Iceberg")
			movementCost = movementCost + 1;		
		else if(terrainFeatures == "Forest")
			movementCost = movementCost + 1;

		//terrain base modifiers
		if(terrainResources == "Stone")
			setProduction(production + 1);
		else if(terrainResources == "Copper")
			setProduction(production + 2);
		else if(terrainResources == "Aluminum")
			setProduction(production + 2);
		else if(terrainResources == "Iron")
			setProduction(production + 2);
		else if(terrainResources == "Tin")
			setProduction(production + 1);
		else if(terrainResources == "Oil")
			setProduction(production + 3);
		else if(terrainResources == "Natural Gas")
			setProduction(production + 3);
		else if(terrainResources == "Uranium")
			setProduction(production + 2);
		else if(terrainResources == "Coal")
			setProduction(production + 3);

		else if(terrainResources == "Cows")
		{
			setFood(food + 3);
			setHealth(health - 1);
		}			
		else if(terrainResources == "Deer")
			setFood(food + 2);
		else if(terrainResources == "Pigs")
		{
			setFood(food + 2);
			setHealth(health - 1);
		}			
		else if(terrainResources == "Chickens")
		{
			setFood(food + 2);
			setHealth(health - 1);
		}			
		else if(terrainResources == "Bananas")
		{
			setFood(food + 2);
			setHealth(health + 1);
		}
		else if(terrainResources == "Peaches")
		{
			setFood(food + 2);
			setHealth(health + 1);
		}
		else if(terrainResources == "Apples")
		{
			setFood(food + 2);
			setHealth(health + 1);
		}

		else if(terrainResources == "Tobacco")
			setProduction(production + 1);
		else if(terrainResources == "Cotton")
			setProduction(production + 2);
		else if(terrainResources == "Silk")
			setProduction(production + 1);
		else if(terrainResources == "Gems")
			setProduction(production + 3);
		else if(terrainResources == "Gold")
			setProduction(production + 2);
		else if(terrainResources == "Silver")
			setProduction(production + 2);

		else if(terrainResources == "Fish")
		{
			setFood(food + 4);
			setHealth(health + 1);
		}
		else if(terrainResources == "Crab")
		{
			setFood(food + 3);
			setHealth(health + 1);
		}
		else if(terrainResources == "Shrimp")
		{
			setFood(food + 3);
			setHealth(health + 1);
		}
		else if(terrainResources == "Clams")
		{
			setFood(food + 2);
			setHealth(health + 1);
		}

		else if(terrainResources == "Pearls")
			setProduction(production + 1);
	}
}
