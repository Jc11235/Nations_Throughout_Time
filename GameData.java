import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.*;
import java.util.*;  
import javax.swing.*;  
import javax.swing.Timer;
import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.io.File;

public class GameData implements Serializable
{
	private int turnNumber;
	private int cityFocusNumber;
	private int unitFocusNumber;
	private int currentMapHoriztonal;
	private int currentMapVertical;

	private  Random r;

	private Terrain[][] terrain;
	private ImageIcon[][] fog;
	private ArrayList<Unit> playerUnits;
	private ArrayList<City> playerCities;
	private ArrayList<Unit> playerBuildableUnits;
	private ArrayList<Unit> buildUnitQueue;
	private ArrayList<Integer> turnsToBuildList;
	
	private boolean playerTurn;
	private boolean newCitySettled;
	
	private Font mainMenuTitle;
	private Font mainMenuOptions;

	private Player player;

	private AI aI;

	public GameData()
	{
		turnNumber= 0;
	 	cityFocusNumber = -1;
	 	unitFocusNumber = 0;
		currentMapHoriztonal= 0;
	 	currentMapVertical= 0;

	 	r = new Random();

	 	playerUnits = new ArrayList<Unit>();
		playerCities = new ArrayList<City>();
		playerBuildableUnits = new ArrayList<Unit>();
		buildUnitQueue = new ArrayList<Unit>();
	 	
		newCitySettled = false;
		playerTurn = true;
	
	 	player = new Player();

	 	aI = new AI();
	}

	//setters

	public void setTurnNumber(int newTurnNumber)
	{
		turnNumber = newTurnNumber;
	}
	public void setPlayerTurn(boolean newTurnAction)
	{
		playerTurn = newTurnAction;
	}
	public void setCityFocusNumber(int newCityFocusNumber)
	{
		cityFocusNumber = newCityFocusNumber;
	}
	public void setUnitFocusNumber(int newUnitFocusNumber)
	{
		unitFocusNumber = newUnitFocusNumber;
	}
	public void setNewCitySettled(boolean newFocus)
	{
		newCitySettled = newFocus;
	}
	public void setCurrentMapVertical(int newVertical)
	{
		currentMapVertical = newVertical;
	}
	public void setCurrentMapHorizontal(int newHorizontal)
	{
		currentMapHoriztonal = newHorizontal;
	}


	//getters

	public int getTurnNumber()
	{
		return turnNumber;
	}
	public boolean getPlayerTurn()
	{
		return playerTurn;
	}
	public int getCityFocusNumber()
	{
		return cityFocusNumber;
	}
	public int getUnitFocusNumber()
	{
		return unitFocusNumber;
	}
	public int getCurrentMapHorizontal()
	{
		return currentMapHoriztonal;
	}
	public int getCurrentMapVertical()
	{
		return currentMapVertical;
	}	
	public boolean getNewCitySettled()
	{
		return newCitySettled;
	}
	public Terrain[][] getTerrain()
	{
		return terrain;
	}
	public Terrain getTerrainSpecific(int x, int y)
	{
		return terrain[y][x];
	}
	public ImageIcon[][] getFog()
	{
		return fog;
	}
	public ImageIcon getFogSpecific(int x, int y)
	{
		return fog[y][x];
	}
	public ArrayList<Unit> getPlayerUnits()
	{
		return playerUnits;
	}
	public Unit getPlayerUnitsSpecific(int selection)
	{
		return playerUnits.get(selection);
	}
	public ArrayList<City> getPlayerCities()
	{
		return playerCities;
	}
	public City getPlayerCitiesSpecific(int selection)
	{
		return playerCities.get(selection);
	}
	public ArrayList<Unit> getPlayerBuildableUnits()
	{
		return playerBuildableUnits;
	}
	public Unit getPlayerBuildableUnitsSpecific(int selection)
	{
		return playerBuildableUnits.get(selection);
	}
	public ArrayList<Unit> getPlayerBuildUnitQueue()
	{
		return buildUnitQueue;
	}
	public Unit getPlayerBuildUnitQueueSpecific(int selection)
	{
		return buildUnitQueue.get(selection);
	}


	//game data stuff

	//generates the terrain
	public void setupTerrain(int mapWidth,int mapHeight,String terrainOptions)
	{
		//sets the terrain
		terrain  = new Terrain[mapHeight][mapWidth];
		fog = new ImageIcon[mapHeight][mapWidth];

		for(int i = 0; i < mapHeight; i++)
		{
			for(int j = 0; j < mapWidth; j++)
			{					
				terrain[i][j] = new Terrain(j,i,mapWidth,mapHeight);
				terrain[i][j].setTerrain(j,i,terrainOptions);
				terrain[i][j].setTileNumber(i*mapHeight+(j+1));

				fog[i][j] = new ImageIcon(getClass().getResource("/Images/Terrain/fog.png"));
			}
		}
		//sets images for the terrain
		for(int i = 0; i < terrain.length; i++)
		{
			for(int j = 0; j < terrain[0].length; j++)
			{					
				terrain[i][j].setTerrainPhysical(terrain,j,i,mapHeight,mapWidth);
				terrain[i][j].setTerrainSettings();				
			}
		}
	}
	//sets up the initial conditions of the game
	public void newGameSetup(int mapWidth,int mapHeight,String terrainOptions)
	{
		setupTerrain(mapWidth,mapHeight,terrainOptions);

		int startSettlerX = r.nextInt(mapWidth)-3;
		int startSettlerY = r.nextInt(mapHeight)-3;

		//SETTLER
		int newX = (int)Math.floor((startSettlerX/75));
		int newY = (int)Math.floor((startSettlerY/75));
		
		while(startSettlerX -5 < 0 || startSettlerY -7 < 0 || startSettlerX + 10 > mapWidth || startSettlerY + 15 > mapHeight && checkUnitMovement(newX,newY) == false) 
		{
			playerUnits.clear();

			startSettlerX = r.nextInt(mapWidth);
			startSettlerY = r.nextInt(mapHeight);

			currentMapHoriztonal = startSettlerX -5;
			currentMapVertical = startSettlerY -7;

			currentMapHoriztonal = 0;
			currentMapVertical = 0;				
		}

		playerUnits.add(new Settler(75*startSettlerX,75*startSettlerY));
		playerUnits.add(new Scout(75*(startSettlerX-2),75*(startSettlerY-2)));

		playerBuildableUnits.add(playerUnits.get(0));
		playerBuildableUnits.add(playerUnits.get(1));
		playerBuildableUnits.add(new Worker(-30,-30));
		reorganizeBuildableUnits();
	}
	//goes through the process of settling a new city and removing the settler from the player unit list
	public void settleNewCity(int mapWidth)
	{
		//adds new tiles for the city
		ArrayList<Terrain> cityTiles = new ArrayList<Terrain>();		

		int unitX = (int)Math.floor((playerUnits.get(unitFocusNumber).getX())/75);
		unitX = (currentMapHoriztonal+ unitX)%mapWidth;

		int unitY = (int)Math.floor((playerUnits.get(unitFocusNumber).getY())/75) + currentMapVertical;
		
		for(int k = unitY -1; k < unitY + 2; k++)
		{
			for(int j = unitX -1; j < unitX + 2; j++)
			{
				cityTiles.add(terrain[k][j]);
			}							
		}

		//adds new city
		playerCities.add(new City(playerUnits.get(unitFocusNumber).getX(),playerUnits.get(unitFocusNumber).getY(),cityTiles));
		//adds city tile number from terrain
		playerCities.get(playerCities.size()-1).setCityTileNumber(terrain[unitY][unitX].getTileNumber());
		//adds a turn counter for construction into the build item list
		setDefaultCityBuild(playerCities.size()-1);
		//removes settler
		playerUnits.remove(unitFocusNumber);
		//sets the city borders
		setCityBorders();

		unitFocusNumber = -1;
		newCitySettled = true;		
	}
	//checks the city borders and sets them, keep this, but it probably wont be implemented
	public ArrayList<ArrayList<Integer>> setCityBorders()
	{
		ArrayList<Integer> tempCityBoundaryTiles = new ArrayList<Integer>();
		//ArrayList<Object> cityBoundaryTiles = new ArrayList<Objectb>();
		ArrayList<Integer> tempCityBoundaryX = new ArrayList<Integer>();
		ArrayList<Integer> tempCityBoundaryY = new ArrayList<Integer>();

		ArrayList<ArrayList<Integer>> tempCityBoundaryTop = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> tempCityBoundaryRight = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> tempCityBoundaryLeft = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>>tempCityBoundaryBottom = new ArrayList<ArrayList<Integer>>();

		ArrayList<ArrayList<Integer>> polygonXY = new ArrayList<ArrayList<Integer>>();

		for(int i = 0; i < playerCities.size(); i++)
		{
			int cctn = playerCities.get(i).getCityTileNumber(); //tile number of the city itself

			for(int j = 0; j < playerCities.get(i).getTiles().size(); j++)
			{
				int tempTN = playerCities.get(i).getTilesSpecific(j).getTileNumber(); //tile numbers of all worked city tiles

				tempCityBoundaryTiles.add(playerCities.get(i).getTilesSpecific(j).getTileNumber());

				//removes non outer borders
				if(tempTN == cctn)
					tempCityBoundaryTiles.remove(tempCityBoundaryTiles.size()-1);
				for(int k = 0; k <  playerCities.get(i).getInfluence(); k++)
				{
					if(tempCityBoundaryTiles.get(tempCityBoundaryTiles.size()-1) >= cctn - k*25 - k && tempCityBoundaryTiles.get(tempCityBoundaryTiles.size()-1) <= cctn - k*25 + k)
						tempCityBoundaryTiles.remove(tempCityBoundaryTiles.size()-1);
					else if(tempCityBoundaryTiles.get(tempCityBoundaryTiles.size()-1) >= cctn + k*25 - k && tempCityBoundaryTiles.get(tempCityBoundaryTiles.size()-1) <= cctn + k*25 + k)
						tempCityBoundaryTiles.remove(tempCityBoundaryTiles.size()-1);
					else if(tempCityBoundaryTiles.get(tempCityBoundaryTiles.size()-1) >= cctn - k && tempCityBoundaryTiles.get(tempCityBoundaryTiles.size()-1) <= cctn + k)
						tempCityBoundaryTiles.remove(tempCityBoundaryTiles.size()-1);
				}				
			}

			int tempHeight = playerCities.get(i).getInfluence() - 1;
			//assigns X and Y values for border tiles around the cities
			for(int k = 0; k < tempCityBoundaryTiles.size(); k++)
			{
				//top row
				if(k < 2*playerCities.get(i).getInfluence() + 1)
				{
					//before middle
					if(k <= playerCities.get(i).getInfluence() + 1)
						tempCityBoundaryX.add(playerCities.get(i).getX() - (playerCities.get(i).getInfluence()-k)*75);
					//after middle
					else
						tempCityBoundaryX.add(playerCities.get(i).getX() + Math.abs((playerCities.get(i).getInfluence()-k))*75);	

					tempCityBoundaryY.add(playerCities.get(i).getY() - playerCities.get(i).getInfluence()*75);

					if(tempCityBoundaryTop.size() == 0)
					{
						tempCityBoundaryTop.add(tempCityBoundaryX);
						tempCityBoundaryTop.add(tempCityBoundaryX);
					}
					else
					{
						tempCityBoundaryTop.set(0,tempCityBoundaryX);
						tempCityBoundaryTop.set(1,tempCityBoundaryX);
					}


							
				}
				//upper right has 2 points in the polygon
				else if(k == 2*playerCities.get(i).getInfluence() + 1)
				{
					tempCityBoundaryX.add(playerCities.get(i).getX() + Math.abs((playerCities.get(i).getInfluence()-k))*75);
					tempCityBoundaryX.add(playerCities.get(i).getX() + Math.abs((playerCities.get(i).getInfluence()-(k+1)))*75);

					//this is added twice one for each point
					tempCityBoundaryY.add(playerCities.get(i).getY() - playerCities.get(i).getInfluence()*75);
					tempCityBoundaryY.add(playerCities.get(i).getY() - playerCities.get(i).getInfluence()*75);
				}
				//sides
				else if(k > 2*playerCities.get(i).getInfluence() + 1 && k < 6*playerCities.get(i).getInfluence())
				{
					//left
					if(k%2 == 0)
						tempCityBoundaryX.add(playerCities.get(i).getX() - playerCities.get(i).getInfluence()*75);						
					//right
					else
					{
						tempCityBoundaryX.add(playerCities.get(i).getX() + playerCities.get(i).getInfluence()*75);
						tempHeight--;
					}						
					
					tempCityBoundaryY.add(playerCities.get(i).getY() - tempHeight*75);
				}
				//bottom row
				else if(k >= 6*playerCities.get(i).getInfluence() && k < tempCityBoundaryTiles.size() - 1)
				{
					int tempX = (k % tempCityBoundaryTiles.size()) % 5;

					if( tempX <= playerCities.get(i).getInfluence() + 1)
						tempCityBoundaryX.add(playerCities.get(i).getX() - (playerCities.get(i).getInfluence()-tempX)*75);	
					else
						tempCityBoundaryX.add(playerCities.get(i).getX() + Math.abs((playerCities.get(i).getInfluence()+tempX))*75);

					tempCityBoundaryY.add(playerCities.get(i).getY() + playerCities.get(i).getInfluence()*75);	
				}
				//lower right has 2 points
				else if(k == tempCityBoundaryTiles.size()-1)
				{
					int tempX = (k % tempCityBoundaryTiles.size()) % 5;

					tempCityBoundaryX.add(playerCities.get(i).getX() + Math.abs((playerCities.get(i).getInfluence()-tempX))*75);
					tempCityBoundaryX.add(playerCities.get(i).getX() + Math.abs((playerCities.get(i).getInfluence()-(tempX+1)))*75);

					tempCityBoundaryY.add(playerCities.get(i).getY() + playerCities.get(i).getInfluence()*75);	
					tempCityBoundaryY.add(playerCities.get(i).getY() + playerCities.get(i).getInfluence()*75);	
				}
			}		
			polygonXY.add(tempCityBoundaryX);
			polygonXY.add(tempCityBoundaryY);						
		}
			//clears values for the next city
			//tempCityBoundaryTiles.clear();
			//tempCityBoundaryX.clear();
			//tempCityBoundaryY.clear();

		//System.out.println("ps: " + polygonXY.size());
		for(int j = 0; j < polygonXY.get(0).size(); j++)
		{
			//System.out.println("x: " + polygonXY.get(0).get(j) + " y: " + polygonXY.get(1).get(j));
		}
		
		//removes duplicate tiles
		/*for(int i = 0; i < cityBoundaryTiles.size(); i++)
		{
			for(int j = 1; j < cityBoundaryTiles.size(); j++)
			{
				int limit;
				if(cityBoundaryTiles.get(j).size() > cityBoundaryTiles.get(i).size())
					limit = cityBoundaryTiles.get(i).size();
				else
					limit = cityBoundaryTiles.get(j).size();
				for(int k = 0; k < limit; k++)
				{
					if(j != i && cityBoundaryTiles.get(i).get(k) == cityBoundaryTiles.get(j).get(k))
						cityBoundaryTiles.get(i).remove(k);
				}				
			}			
		}
		*/
		return polygonXY;
	}
	//checks the terrain around a newly settled city to eliminate fog where applicable
	public void checkNewCityTerrain(int mapWidth, int mapHeight)
	{
		int cityX = (int)Math.floor((playerCities.get(playerCities.size() - 1).getX()/75));
		int cityY = (int)Math.floor((playerCities.get(playerCities.size() - 1).getY()/75));

		cityX = (currentMapHoriztonal + cityX)%mapWidth;

		for(int k = (cityY + currentMapVertical) -2; k < (cityY + currentMapVertical) + 3; k++)
		{
			for(int l = (cityX) -2; l < (cityX) + 3; l++)
			{
				if(k > -1 && k < mapHeight && l > -1 && l < mapWidth)
				{
					if(terrain[k][l].getVisability() == false)
						terrain[k][l].setVisability(true);
				}					
			}							
		}
		newCitySettled = false;
	}
	public void checkTerrainVisibility(int mapWidth, int mapHeight)
	{
		for(int h = 0; h < playerUnits.size(); h++)
		{
			int unitX = (int)Math.floor((playerUnits.get(h).getX()/75));
			int unitY = (int)Math.floor((playerUnits.get(h).getY()/75));

			unitX = (currentMapHoriztonal + unitX)%mapWidth;

			for(int k = (unitY + currentMapVertical ) -1; k < (unitY + currentMapVertical) + 2; k++)
			{
				for(int l = (unitX) -1; l < (unitX) + 2; l++)
				{
					if(k > -1 && k < mapHeight && l > -1 && l < mapWidth)
					{						
						if(terrain[k][l].getVisability() == false)
							terrain[k][l].setVisability(true);													
					}					
				}							
			}										
		}		
	}
	//checks to see if the unit can move to the selected tile
	public boolean checkUnitMovement(int newX,int newY)
	{
		int tempTypeCheck = 0;
		int tempFeaturesCheck = 0;

		if(newX != (int)Math.floor((playerUnits.get(unitFocusNumber).getX()/75)) || newY != (int)Math.floor((playerUnits.get(unitFocusNumber).getY()/75)))
		{
			for(int i = 0; i < playerUnits.get(unitFocusNumber).getNonAccessibleTerrainType().size(); i++)
			{
				if(terrain[newY+ currentMapVertical][newX].getTerrainType() == playerUnits.get(unitFocusNumber).getNonAccessibleTerrainType().get(i))
					tempTypeCheck++;
			}
			for(int i = 0; i < playerUnits.get(unitFocusNumber).getNonAccessibleTerrainFeatures().size(); i++)
			{
				if(terrain[newY+ currentMapVertical][newX].getTerrainFeatures() == playerUnits.get(unitFocusNumber).getNonAccessibleTerrainFeatures().get(i))
					tempFeaturesCheck++;
			}
			if(tempFeaturesCheck != 0  || tempTypeCheck != 0 || playerUnits.get(unitFocusNumber).getMovement() - terrain[newY+ currentMapVertical][newX].getMovementCost() < 0 )
				return false;
			else
				return true;				
		}
		else
			return false;			
	}
	//changes unit data during movement
	public void updateUnitMovementData(int newX,int newY,int x)
	{
		playerUnits.get(unitFocusNumber).setMovement(playerUnits.get(unitFocusNumber).getMovement() - terrain[newY + currentMapVertical][newX].getMovementCost());
		//playerUnits.get(unitFocusNumber).setLocationX(x);
	}
	//changes all units x and y
	public void updateAllMovementData(int newX, int newY)
	{
		for(int i = 0; i < playerUnits.size(); i++)
		{
			playerUnits.get(i).setX(playerUnits.get(i).getX() + newX);
			playerUnits.get(i).setY(playerUnits.get(i).getY() + newY);
		}
		for(int i = 0; i < playerCities.size(); i++)
		{
			playerCities.get(i).setX(playerCities.get(i).getX() + newX);
			playerCities.get(i).setY(playerCities.get(i).getY() + newY);
		}
	}
	//reorganizes the buildable units by production cost least to greatest
	public void reorganizeBuildableUnits()
	{
		Collections.sort(playerBuildableUnits,new BuildableProductionComp());
	}
	//calculates the number of turns to build a certain unit
	public int calculateTurnsToBuildSpecific(int selection, String buildCondition)
	{		
		if(buildCondition.equals("Default"))//when a new city is built or the default city build is used
			return (int)Math.floor(buildUnitQueue.get(selection).getProductionCost()/playerCities.get(selection).getProduction());
		else//when the player selects a specific build
			return (int)Math.floor(playerBuildableUnits.get(selection).getProductionCost()/playerCities.get(cityFocusNumber).getProduction());
	}
	//resets the time to build something based on terrain focus changes
	public void calculateTurnsToBuild()
	{
		for(int i = 0; i < playerCities.size(); i++)
		{
			playerCities.get(i).setTurnsToBuild((int)Math.floor(buildUnitQueue.get(i).getProductionCost()/playerCities.get(i).getProduction()));
		}
	}
	//sets the default build for a city
	public void setDefaultCityBuild(int cityNumber)
	{
		buildUnitQueue.add(cityNumber,new Settler(-1, -1));
		playerCities.get(cityNumber).setConstructionName("Settler");

		int turnsToBuild = calculateTurnsToBuildSpecific(cityNumber,"Default");

		playerCities.get(cityNumber).setTurnsToBuild(turnsToBuild);
	}
	//builds the unit when it's finished
	public void setNewUnitToBuild(int selection, String name, int spawnX, int spawnY)
	{		
		int turnsToBuild = calculateTurnsToBuildSpecific(selection,"City");

		playerCities.get(cityFocusNumber).setTurnsToBuild(turnsToBuild);

		if(name.equals("Settler"))
		{
			buildUnitQueue.set(cityFocusNumber,new Settler(-1,-1));
			playerCities.get(cityFocusNumber).setConstructionName("Settler");
		}			
		else if(name.equals("Scout"))
		{
			buildUnitQueue.set(cityFocusNumber,new Scout(-1,-1));
			playerCities.get(cityFocusNumber).setConstructionName("Scout");
		}			
		else if(name.equals("Worker"))
		{
			buildUnitQueue.set(cityFocusNumber,new Worker(75*spawnX, 75*spawnY));
			playerCities.get(cityFocusNumber).setConstructionName("Worker");
		}		
	}		
	//builds the next unit in the queue
	public void buildNewUnit(int cityNumber)
	{
		//"builds" the new unit

		int spawnX = (int)Math.floor((playerCities.get(cityNumber).getX() + currentMapHoriztonal)/75);
		int spawnY = (int)Math.floor((playerCities.get(cityNumber).getY() + currentMapVertical)/75);

		if(playerCities.get(cityNumber).getConstructionName().equals("Settler"))
			playerUnits.add(new Settler(75*spawnX, 75*spawnY));
		else if(playerCities.get(cityNumber).getConstructionName().equals("Scout"))
			playerUnits.add(new Scout(75*spawnX, 75*spawnY));
		else if(playerCities.get(cityNumber).getConstructionName().equals("Worker"))
			playerUnits.add(new Worker(75*spawnX, 75*spawnY));

		//default new construction is a scout, player must physically change this
		setDefaultCityBuild(cityNumber);		
	}
	//resets city coordinates based on the loop of the map
	public void resetPlayerObjectsCoordinates(String direction,int mapWidth)
	{
		if(direction.equals("Right"))
		{
			for(int i = 0; i < playerCities.size(); i++)
			{
				if((playerCities.get(i).getX() - playerCities.get(i).getLocationX()== (mapWidth*75 -playerCities.get(i).getLocationX())))
					playerCities.get(i).setX(0);		
			}
			for(int i = 0; i < playerUnits.size(); i++)
			{
				if((playerUnits.get(i).getX() - playerUnits.get(i).getLocationX() >= (mapWidth*75 -playerUnits.get(i).getLocationX())))
					playerUnits.get(i).setX(0);		
			}
		}
		else if(direction.equals("Left"))
		{
			for(int i = 0; i < playerCities.size(); i++)
			{
				if(playerCities.get(i).getX() == 0)
					playerCities.get(i).setX(mapWidth*75);		
			}
			for(int i = 0; i < playerUnits.size(); i++)
			{
				if(playerUnits.get(i).getX() == 0)
					playerUnits.get(i).setX(mapWidth*75);		
			}
		}								
	}
	//resets all movement for player units
	public void resetPlayerUnitMovement()
	{
		//resets movement for all units
		for(int i = 0; i < playerUnits.size();i++)
		{
			playerUnits.get(i).setMovement(playerUnits.get(i).getMaxMovement());
		}
	}
	//processes audio
	public void createSound(String sound)
	{
		
	}
	//wraps up the turn and processes all relevant player data 
	public void endPlayerTurn()
	{
		//checks if new build times are present for each city
		//calculateTurnsToBuild();

		//checks production for cities
		for(int i = 0; i < playerCities.size(); i++)
		{
			if(playerCities.get(i).getTurnsToBuild() > 1)
				playerCities.get(i).setTurnsToBuild(playerCities.get(i).getTurnsToBuild()-1);
			else if(playerCities.get(i).getTurnsToBuild() <= 1 && playerCities.get(i).getTurnsToBuild() > -15000)
				buildNewUnit(i);
		}

		//resets all player units movement		
		resetPlayerUnitMovement();		
	}	
}
//comparator classes
class BuildableProductionComp implements Comparator<Unit>
{	
	public int compare(Unit u1, Unit u2) 
    {
    	if(u1.getProductionCost() > u2.getProductionCost())
    	{
    		return 1;
    	}
    	else
    	{
    		return -1;
    	}
    }
}
