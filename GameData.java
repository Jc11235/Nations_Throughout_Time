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
		turnsToBuildList = new ArrayList<Integer>();
	 	
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
	public ArrayList<Integer> getTurnToBuildList()
	{
		return turnsToBuildList;
	}
	public int getTurnsToBuildListSpecific(int selection)
	{
		return turnsToBuildList.get(selection);
	}







	//game data stuff

	//generates the terrain
	public void setupTerrain(int mapWidth,int mapHeight)
	{
		//sets the terrain
		terrain  = new Terrain[mapHeight][mapWidth];
		fog = new ImageIcon[mapHeight][mapWidth];

		for(int i = 0; i < mapHeight; i++)
		{
			for(int j = 0; j < mapWidth; j++)
			{					
				terrain[i][j] = new Terrain(j,i,mapWidth,mapHeight);
				terrain[i][j].setTerrain(j,i);
				fog[i][j] = new ImageIcon(getClass().getResource("/Images/Terrain/fog.png"));
			}
		}
		//sets images for the terrain
		for(int i = 0; i < terrain.length; i++)
		{
			for(int j = 0; j < terrain[0].length; j++)
			{					
				terrain[i][j].setTerrainPhysical(j,i);				
			}
		}
	}
	//sets up the initial conditions of the game
	public void newGameSetup(int mapWidth,int mapHeight)
	{
		setupTerrain(mapWidth,mapHeight);

		int startSettlerX = r.nextInt(mapWidth)-3;
		int startSettlerY = r.nextInt(mapHeight)-3;

		while(startSettlerX -5 < 0 || startSettlerY -7 < 0 || startSettlerX + 10 > mapWidth || startSettlerY + 15 > mapHeight)
		{
			playerUnits.clear();

			startSettlerX = r.nextInt(mapWidth);
			startSettlerY = r.nextInt(mapHeight);

			currentMapHoriztonal = startSettlerX -5;
			currentMapVertical = startSettlerY -7;				
		}

		playerUnits.add(new Settler(75*startSettlerX,75*startSettlerY));
		playerUnits.add(new Scout(75*(startSettlerX-2),75*(startSettlerY-2)));

		playerBuildableUnits.add(playerUnits.get(0));
		playerBuildableUnits.add(playerUnits.get(1));
		reorganizeBuildableUnits();
	}
	//goes through the process of settling a new city and removing the settler from the player unit list
	public void settleNewCity()
	{
		//adds new tiles for the city
		ArrayList<Terrain> cityTiles = new ArrayList<Terrain>();
		int unitX = (int)Math.floor((playerUnits.get(unitFocusNumber).getX() + currentMapHoriztonal)/75);
		int unitY = (int)Math.floor((playerUnits.get(unitFocusNumber).getY() + currentMapVertical)/75);
		for(int k = unitY -1; k < unitY + 2; k++)
		{
			for(int j = unitX -1; j < unitX + 2; j++)
			{
				cityTiles.add(terrain[k][j]);
			}							
		}						
		//adds new city
		playerCities.add(new City(playerUnits.get(unitFocusNumber).getX(),playerUnits.get(unitFocusNumber).getY(),cityTiles));
		//adds a turn counter for construction into the build item list
		turnsToBuildList.add(playerCities.size()-1,-20000);
		setDefaultCityBuild(playerCities.size()-1,unitX,unitY);
		//removes settler
		playerUnits.remove(unitFocusNumber);
		unitFocusNumber = -1;
		newCitySettled = true;		
	}
	//checks the terrain around a newly settled city to eliminate fog where applicable
	public void checkNewCityTerrain(int mapWidth, int mapHeight)
	{
		int cityX = (int)Math.floor((playerCities.get(playerCities.size() - 1).getX()/75));
		int cityY = (int)Math.floor((playerCities.get(playerCities.size() - 1).getY()/75));

		for(int k = (cityY + currentMapVertical) -2; k < (cityY + currentMapVertical) + 3; k++)
		{
			for(int l = (cityX + currentMapHoriztonal) -2; l < (cityX + currentMapHoriztonal) + 3; l++)
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
	//checks to see if the unit can move to the selected tile
	public boolean checkUnitMovement(int newX,int newY)
	{
		int tempTypeCheck = 0;
		int tempFeaturesCheck = 0;

		if(newX != (int)Math.floor((playerUnits.get(unitFocusNumber).getX()/75)) || newY != (int)Math.floor((playerUnits.get(unitFocusNumber).getY()/75)))
		{
			for(int i = 0; i < playerUnits.get(unitFocusNumber).getNonAccessibleTerrainType().size(); i++)
			{
				if(terrain[newY+ currentMapVertical][newX + currentMapHoriztonal].getTerrainType() == playerUnits.get(unitFocusNumber).getNonAccessibleTerrainType().get(i))
					tempTypeCheck++;
			}
			for(int i = 0; i < playerUnits.get(unitFocusNumber).getNonAccessibleTerrainFeatures().size(); i++)
			{
				if(terrain[newY+ currentMapVertical][newX + currentMapHoriztonal].getTerrainFeatures() == playerUnits.get(unitFocusNumber).getNonAccessibleTerrainFeatures().get(i))
					tempFeaturesCheck++;
			}
			if(tempFeaturesCheck != 0  || tempTypeCheck != 0 || playerUnits.get(unitFocusNumber).getMovement() - terrain[newY+ currentMapVertical][newX + currentMapHoriztonal].getMovementCost() < 0 )
				return false;
			else
				return true;				
		}
		else
			return false;			
	}
	//changes unit data during movement
	public void updateUnitMovementData(int newX,int newY)
	{
		playerUnits.get(unitFocusNumber).setMovement(playerUnits.get(unitFocusNumber).getMovement() - terrain[newY + currentMapVertical][newX + currentMapHoriztonal].getMovementCost());
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
		for(int i = 0; i < turnsToBuildList.size(); i++)
		{
			turnsToBuildList.set(i,(int)Math.floor(buildUnitQueue.get(i).getProductionCost()/playerCities.get(i).getProduction()));
		}
	}
	//sets the default build for a city
	public void setDefaultCityBuild(int cityNumber,int spawnX, int spawnY)
	{
		buildUnitQueue.add(cityNumber,new Scout(75*spawnX, 75*spawnY));

		int turnsToBuild = calculateTurnsToBuildSpecific(cityNumber,"Default");

		turnsToBuildList.set(cityNumber,turnsToBuild);
	}
	//builds the unit when it's finished
	public void setNewUnitToBuild(int selection, String name, int spawnX, int spawnY)
	{		
		int turnsToBuild = calculateTurnsToBuildSpecific(selection,"City");

		turnsToBuildList.set(cityFocusNumber,turnsToBuild);

		if(name.equals("Settler"))
			buildUnitQueue.set(cityFocusNumber,new Settler(75*spawnX, 75*spawnY));
		if(name.equals("Scout"))
			buildUnitQueue.set(cityFocusNumber,new Scout(75*spawnX, 75*spawnY));
		
	}		
	//builds the next unit in the queue
	public void buildNewUnit(int cityNumber)
	{
		//"builds" the new unit
		playerUnits.add(buildUnitQueue.get(cityNumber));

		int unitX = (int)Math.floor((playerCities.get(cityNumber).getX() + currentMapHoriztonal)/75);
		int unitY = (int)Math.floor((playerCities.get(cityNumber).getY() + currentMapVertical)/75);

		//default new construction is a scout, player must physically change this
		setDefaultCityBuild(cityNumber,unitX,unitY);		
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
	//wraps up the turn and processes all relevant player data 
	public void endPlayerTurn()
	{
		//checks if new build times are present for each city
		//calculateTurnsToBuild();

		//checks production for cities
		for(int i = 0; i < turnsToBuildList.size(); i++)
		{
			if(turnsToBuildList.get(i) > 1)
				turnsToBuildList.set(i,turnsToBuildList.get(i)-1);
			else if(turnsToBuildList.get(i) <= 1 && turnsToBuildList.get(i) > -15000)
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
