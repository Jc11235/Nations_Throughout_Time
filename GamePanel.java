import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.*;
import java.util.*;  
import javax.swing.*;  
import javax.swing.Timer;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Polygon;


public class GamePanel extends JPanel  implements ActionListener,KeyListener,MouseListener,MouseMotionListener,Serializable
{
	//globals
	private int mapHeight;
	private int mapWidth;
	private int mapX;
	private int mapY;
	private int miniMapY;
	private int miniMapX;
	private int minimapWidth;
	private int minimapHeight;	
	private int currentMouseMapHoriztonal;
	private int currentMouseMapVertical;	
	private int currentBuildOptionsVertical; 

	private boolean mainMenu;
	private boolean newGameSetup;
	private boolean mapView;
	private boolean mapSizeOptions;
	private boolean terrainOptionsAction;
	private boolean startGame;
	private boolean terrainFocus;
	private boolean cityConstructionFocus;
	private boolean[] move;

	private boolean startMusic;
	private boolean pauseMenu;

	private String terrainOptions;
	private String terrainType;
	private String mapSize;

	private Font mainMenuTitle;
	private Font mainMenuOptions;

	private ImageIcon mainBackground;

	private GameData gameData;

	private AudioPlayer audioPlayer;

	private GameWindow gameWindow;

	public GamePanel(GameWindow newGameWindow)
	{
		mapHeight = 0;
	 	mapWidth= 0;	
	 	mapX = 21;
	 	mapY = 12;	
	 	currentMouseMapHoriztonal= 0;
	 	currentMouseMapVertical= 0;
	 	currentBuildOptionsVertical = 0;
	 	miniMapY = 500;
		miniMapX = 1225;
		minimapWidth = 350;
		minimapHeight = 350;

	 	mapSize = "None";
	 	terrainType = "None";
		
	 	mainMenu = true;
	 	newGameSetup = false;
	 	mapView = false;
	 	mapSizeOptions = false;
	 	startGame = false;
	 	terrainFocus = false;
		cityConstructionFocus = false;
	 	move = new boolean[4];

	 	startMusic = true;
	 	pauseMenu = false;

		mainMenuTitle = new Font("Algerian",Font.BOLD,75);
		mainMenuOptions = new Font("Calibri",Font.BOLD,15);

	 	gameData = new GameData();

	 	audioPlayer = new AudioPlayer();

	 	gameWindow = newGameWindow;
	}

	//file data
	public void saveClassData()
	{
	}

	//getters
	public int getMapHeight()
	{
		return mapHeight;
	}
	public int getMapWidth()
	{
		return mapWidth;
	}	
	public boolean getMapView()
	{
		return mapView;
	}
	



	//setters
	public void setMapHeight(int height)
	{
		mapHeight = height;
	}
	public void setMapWidth(int width)
	{
		mapWidth = width;
	}
	public void setMapView(boolean b)
	{
		mapView = b;
	}
	public void setMainMenuView(boolean b)
	{
		mainMenu = b;
	}
	public void setNewGameSetup(boolean b)
	{
		newGameSetup = b;
	}
	//painters

	//paints the game
	public void paintComponent(Graphics g)
	{		
		if(mainMenu == true)
		{
			paintMainMenu(g);
		}
		if(newGameSetup == true)
		{
			paintSetupScreen(g);
		}
		else if(mapView == true)
		{
			if(startGame == true)
				gameData.newGameSetup(mapWidth,mapHeight,terrainOptions);
			startGame = false;

			paintTerrain(g);
			paintCities(g);
			paintUnits(g);
			paintHud(g);
			paintFileMenu(g);
			paintConstructionMenu(g);						
		}						
	}	
	//paints the terrain
	public void paintTerrain(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0,0,1600,900);

		//checks the terrain around units
		gameData.checkTerrainVisibility(mapWidth,mapHeight);

		//checks terrain around newly settled city
		if(gameData.getNewCitySettled() == true)
			gameData.checkNewCityTerrain(mapWidth, mapHeight);
		//paints the terrain
		for(int i = 0; i < mapY;i++)
		{
			for(int j = 0; j < mapX; j++)
			{
				if((gameData.getCurrentMapHorizontal()+j)%mapWidth >= 0)
				{				
					if(gameData.getTerrainSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth, i + gameData.getCurrentMapVertical()).getVisability() == true)
					{					
						gameData.getTerrainSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth, i + Math.abs((gameData.getCurrentMapVertical()%mapHeight))).getTerrainImage().paintIcon(this,g,j*75,i*75);
						gameData.getTerrainSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth, i + Math.abs((gameData.getCurrentMapVertical()%mapHeight))).getTerrainFeaturesImage().paintIcon(this,g,j*75,i*75);
						gameData.getTerrainSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth, i + Math.abs((gameData.getCurrentMapVertical()%mapHeight))).getTerrainResourceImage().paintIcon(this,g,j*75,i*75);
					}
					else
						gameData.getFogSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth, i + Math.abs((gameData.getCurrentMapVertical()%mapHeight))).paintIcon(this,g,j*75,i*75);
				}
				else
				{
					if(gameData.getTerrainSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth + mapWidth, i + gameData.getCurrentMapVertical()).getVisability() == true)
					{
						gameData.getTerrainSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth + mapWidth, i + Math.abs((gameData.getCurrentMapVertical()%mapHeight))).getTerrainImage().paintIcon(this,g,j*75,i*75);
						gameData.getTerrainSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth + mapWidth, i + Math.abs((gameData.getCurrentMapVertical()%mapHeight))).getTerrainFeaturesImage().paintIcon(this,g,j*75,i*75);
						gameData.getTerrainSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth + mapWidth, i + Math.abs((gameData.getCurrentMapVertical()%mapHeight))).getTerrainResourceImage().paintIcon(this,g,j*75,i*75);
					}
					else
						gameData.getFogSpecific((gameData.getCurrentMapHorizontal()+j)%mapWidth + mapWidth, i + Math.abs((gameData.getCurrentMapVertical()%mapHeight))).paintIcon(this,g,j*75,i*75);
				}								
			}
		}
		//creates the physical grid
		for(int i = 0; i < 21; i++)
		{
			g.fillRect(i*75,0,1,900);
		}
		for(int i = 0; i < 21; i++)
		{
			g.fillRect(0,i*75,1600,1);
		}
	}

	//paints units
	public void paintUnits(Graphics g)
	{
		for(int i = 0; i < gameData.getPlayerUnits().size(); i++)
		{
			//paints the unit
			if(gameData.getPlayerUnitsSpecific(i).getX() >= 0 && gameData.getPlayerUnitsSpecific(i).getX() <= 1600 && gameData.getPlayerUnitsSpecific(i).getY() >= 0 && gameData.getPlayerUnitsSpecific(i).getY() < 900)
				gameData.getPlayerUnitsSpecific(i).getUnitImage().paintIcon(this,g,gameData.getPlayerUnitsSpecific(i).getX(),gameData.getPlayerUnitsSpecific(i).getY());

			//if a unit has been clicked on
			if(gameData.getUnitFocusNumber() > -1)
			{
				g.setColor(Color.blue);
				g.drawOval(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getX(),gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getY(),75,70);

				//if a units special ability was clicked
				if(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getSpecialAbilityActivate() == true)
				{
					//settle a city	
					if(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getName().equals("Settler"))
					{
						gameData.settleNewCity(mapWidth);
						gameData.getPlayerCitiesSpecific(gameData.getPlayerCities().size()-1).setCityName(JOptionPane.showInputDialog(this,"City Settled!" + "\n" + "Choose your city's name:"));
					}	
				}								
			}
		}
	}

	//paints cities
	public void paintCities(Graphics g)
	{		
		for(int i = 0; i < gameData.getPlayerCities().size(); i++)
		{
			g.setColor(Color.blue);
			//paints cities
			gameData.getPlayerCitiesSpecific(i).getCityImage().paintIcon(this,g,gameData.getPlayerCitiesSpecific(i).getX(),gameData.getPlayerCitiesSpecific(i).getY());	
			Font population = new Font("Impact",Font.BOLD,25);
			g.setFont(population);
			g.drawString("" + gameData.getPlayerCitiesSpecific(i).getPopulation(),gameData.getPlayerCitiesSpecific(i).getX()+5,gameData.getPlayerCitiesSpecific(i).getY()+25);

			//draws the territory tiles			
			int cityTile = gameData.getPlayerCitiesSpecific(i).getCityTileNumber();

			int cityY = gameData.getPlayerCitiesSpecific(i).getY();
			int cityX = gameData.getPlayerCitiesSpecific(i).getX();

			
			int distanceY = gameData.getPlayerCitiesSpecific(i).getInfluence();
			for(int j = 0; j < gameData.getPlayerCitiesSpecific(i).getInfluence()*2 + 1; j++)
			{
				int distanceX = gameData.getPlayerCitiesSpecific(i).getInfluence();
				
				for(int k = 0; k < gameData.getPlayerCitiesSpecific(i).getInfluence()*2 + 1; k++)
				{
					g.setColor(Color.red);					

					g.drawRect(cityX -  (distanceX*75), cityY - (distanceY*75), 75, 75);

					distanceX--;
				}
				distanceY--;				
			}
		}		
	}
	//paints the constructions menu
	public void paintConstructionMenu(Graphics g)
	{
		//if a city was clicked on
		if(gameData.getCityFocusNumber() > -1 && cityConstructionFocus == true)
		{
			int cityX = gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getX();

			g.setColor(Color.gray);
			if(cityX - 150 > 0)
			{
				g.fillRect(cityX - 150,100,200,500);
				g.fillRect(cityX - 250,100,100,100);
				g.setColor(Color.white);
				for(int i = 0; i < 2; i++)
				{
					g.draw3DRect(cityX - 250,100+50*i,100,50,true);
				}
				g.drawString("Units",cityX - 225, 125);
				g.drawString("Buildings",cityX - 225, 175);
				for(int i = 0; i < 10; i++)
				{
					g.draw3DRect(cityX - 150,100+50*i,200,50,true);
				}
			}				
			else
			{
				g.fillRect(cityX + 150,100,200,500);
				g.setColor(Color.white);
				for(int i = 0; i < 2; i++)
				{
					g.draw3DRect(cityX + 100,100+50*i,50,50,true);
				}
				for(int i = 0; i < 10; i++)
				{
					g.draw3DRect(cityX + 150,100+50*i,200,50,true);
				}
			}
			for(int i = 0; i < gameData.getPlayerBuildableUnits().size(); i++)
			{
				if(i - currentBuildOptionsVertical < gameData.getPlayerBuildableUnits().size())
				{
					if(cityX - 150 > 0)
						g.drawString(gameData.getPlayerBuildableUnitsSpecific(i-currentBuildOptionsVertical).getName() + "     Turns: " + gameData.calculateTurnsToBuildSpecific(i-currentBuildOptionsVertical,"City"),cityX - 100,125+50*i);	
					else
						g.drawString(gameData.getPlayerBuildableUnitsSpecific(i-currentBuildOptionsVertical).getName() + "     Turns: " + gameData.calculateTurnsToBuildSpecific(i-currentBuildOptionsVertical,"City"),cityX + 200,125+50*i);	
				}				
			}					
		}
	}
	
	//paints main menu
	public void paintMainMenu(Graphics g)
	{
		if(startMusic == true)
		{
			//audioPlayer.playAudio("/Sounds/mainMusic.wav");
			startMusic = false;
		}
		
		mainBackground = new ImageIcon(getClass().getResource("/Images/Backgrounds/StartBackground.png"));
		mainBackground.paintIcon(this,g,0,0);

		g.setFont(mainMenuTitle);
		g.setColor(Color.white);
		g.drawString("Nations Throughout Time",200,100);

		//menu options
		g.draw3DRect(700,400,100,50,true);
		g.draw3DRect(700,460,100,50,true);
		g.draw3DRect(700,520,100,50,true);

		Font mainMenuOptionsI = new Font("Calibri",Font.BOLD,18);
		g.setFont(mainMenuOptionsI);
		g.drawString("New Game",710,425);
		g.drawString("Load Game",710,485);
		g.drawString("Exit",710,545);
	}


	//paints main menu
	public void paintSetupScreen(Graphics g)
	{
		//audioPlayer.setStopPlayback(true);
		mainBackground = new ImageIcon(getClass().getResource("/Images/Backgrounds/StartBackground.png"));
		mainBackground.paintIcon(this,g,0,0);

		Font mainMenuOptionsI = new Font("Calibri",Font.BOLD,18);

		g.setFont(mainMenuOptionsI);
		g.setColor(Color.white);

		for(int i = 0; i < 4; i++)
		{
			g.draw3DRect(1000,50 + 50*(i),225,50,true);
		}

		g.drawString("Game Setup ",1015,75);
		g.drawString("Map Size: " + mapSize,1015,125);
		g.drawString("Terrain Type: " + terrainType,1015,175);


		g.drawString("MapSize",610,125);

		g.draw3DRect(600,100,125,50,true);

		if(mapSizeOptions == true)
		{
			for(int i = 0; i < 4; i++)
			{
				g.draw3DRect(725,50*(i+1),100,50,true);
			}
			g.drawString("Small",735,75);
			g.drawString("Medium",735,125);
			g.drawString("Large",735,175);
			g.drawString("Huge",735,225);
		}

		g.drawString("Terrain Type",610,225);

		g.draw3DRect(600,200,125,50,true);

		if(terrainOptionsAction == true)
		{
			for(int i = 0; i < 2; i++)
			{
				g.draw3DRect(725,150+50*(i),125,50,true);
			}
			g.drawString("Pangea",735,175);
			g.drawString("Archipelago",735,225);
			
		}

		g.draw3DRect(900,700,100,50,true);
		g.drawString("Start",925,725);
	}


	//paints the informations screen
	public void paintHud(Graphics g)
	{		
		g.setFont(mainMenuOptions);

		//player info	
		
		//game info
		g.setColor(Color.gray);
		g.fillRect(1120,760,85,80);

		g.setColor(Color.white);
		g.draw3DRect(1125,775,75,40,true);
		g.drawString("Next Turn",1130,795);
		
		
		if(terrainFocus == true)
		{
			//tile info
			int terrainInfoX = 1000;
			g.setColor(Color.gray);
			g.fillRect(terrainInfoX,600,200,130);

			g.setColor(Color.white);
			g.draw3DRect(terrainInfoX,600,200,130,true);

			int terrainX;

			if((gameData.getCurrentMapHorizontal()+ currentMouseMapHoriztonal)%mapWidth >= 0)
				terrainX = (gameData.getCurrentMapHorizontal()+ currentMouseMapHoriztonal)%mapWidth;
			else
				terrainX = (gameData.getCurrentMapHorizontal()+ currentMouseMapHoriztonal)%mapWidth + mapWidth;

			if(gameData.getTerrainSpecific(terrainX,currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainFeatures() != "")
				g.drawString(gameData.getTerrainSpecific(terrainX,currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainType() + " , " + gameData.getTerrainSpecific(terrainX,currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainFeatures(),terrainInfoX + 10,620);
			else
				g.drawString(gameData.getTerrainSpecific(terrainX,currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainType(),terrainInfoX + 10,620);

			g.drawString(gameData.getTerrainSpecific(terrainX,currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainResources(),terrainInfoX + 10,640);
			g.drawString("Food: " + gameData.getTerrainSpecific(terrainX,currentMouseMapVertical+gameData.getCurrentMapVertical()).getFood(),terrainInfoX + 10,660);
			g.drawString("Production: " + gameData.getTerrainSpecific(terrainX,currentMouseMapVertical+gameData.getCurrentMapVertical()).getProduction(),terrainInfoX + 10,680);
			g.drawString("Health: " + gameData.getTerrainSpecific(terrainX,currentMouseMapVertical+gameData.getCurrentMapVertical()).getHealth(),terrainInfoX + 10,700);
			g.drawString("Science: " + gameData.getTerrainSpecific(terrainX,currentMouseMapVertical+gameData.getCurrentMapVertical()).getScience(),terrainInfoX + 10,720);

		}
			
		//unit info		
		if(gameData.getUnitFocusNumber() > -1)
		{
			g.setColor(Color.gray);
			g.fillRect(10,650,200,175);
			g.draw3DRect(10,650,200,175,true);	

			g.fillRect(220,650,50,25);
			g.fillRect(220,680,50,25);
			g.fillRect(220,680,50,25);

			g.setColor(Color.white);
			g.draw3DRect(220,650,50,25,true);
			g.draw3DRect(220,680,50,25,true);
			g.draw3DRect(220,680,50,25,true);

			g.drawString(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getName(), 20,665);
			g.drawString("Strength: " + gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getStrength(), 20,685);	
			g.drawString("Health: " + gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getHealth(), 20,705);	
			g.drawString("Movement: " + gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getMovement(), 20,725);	

			if(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getSpecialAbility() != "")
			{
				g.drawString(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getSpecialAbility(),225,665);
			}		
		}

		//city info				

		if(gameData.getCityFocusNumber() > -1)
		{
			g.setColor(Color.gray);
			g.fillRect(1175,265,200,200);
			g.fillRect(1380,265,110,25);
			g.fillRect(1380,295,110,25);
			g.fillRect(1380,325,110,25);
			g.fillRect(1380,355,110,25);
			g.fillRect(1380,385,110,25);
			g.fillRect(1380,415,110,25);
			g.fillRect(1380,445,110,25);

			g.draw3DRect(1175,265,200,200,true);

			g.draw3DRect(1380,265,110,25,true);
			g.draw3DRect(1380,295,110,25,true);
			g.draw3DRect(1380,325,110,25,true);
			g.draw3DRect(1380,355,110,25,true);
			g.draw3DRect(1380,385,110,25,true);
			g.draw3DRect(1380,415,110,25,true);
			g.draw3DRect(1380,445,110,25,true);

			g.setColor(Color.white);
			g.drawString("Production Focus",1382,280);
			g.drawString("Food Focus",1382,310);
			g.drawString("Health Focus",1382,340);
			g.drawString("Gold Focus",1382,370);
			g.drawString("Manual Focus",1382,400);
			g.drawString("City Construction",1382,430);	

			g.drawString(gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getName(),1180,320);
			g.drawString("Population: " + gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getPopulation(),1180,340);
			g.drawString("Food: " + gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getFood() + "   Turns until growth: " + gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getTurnsUntilGrowth(),1180,360);
			g.drawString("Production: " + gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getProduction(),1180,380);
			g.drawString("Health: " + gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getHealth(),1180,400);
			if(gameData.getPlayerBuildUnitQueue().size() > 0)
				g.drawString("Building: " + gameData.getPlayerBuildUnitQueueSpecific(0).getName() + " in " + gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getTurnsToBuild(), 1180, 420);	
		}		

		//minimap
		g.drawRect(miniMapX,miniMapY,minimapWidth,minimapHeight);
		//minimap terrain
		for(int i = 0; i < mapWidth; i++)
		{			
			for(int j = 0; j < mapHeight; j++)
			{
				//base terrain
				if(gameData.getTerrainSpecific(i,j).getVisability() == false)
					g.setColor(Color.black);					
				else
				{
					//if(!(gameData.getTerrainSpecific(i,j).getTerrainType().equals("Water") || gameData.getTerrainSpecific(i,j).getTerrainType().equals("Coast")))
						//g.setColor(new Color(0, 102, 0));
					if(gameData.getTerrainSpecific(i,j).getTerrainType().equals("Desert"))
						g.setColor(Color.yellow);
					else if(gameData.getTerrainSpecific(i,j).getTerrainType().equals("Grassland"))
						g.setColor(new Color(0, 102, 0));
					else if(gameData.getTerrainSpecific(i,j).getTerrainType().equals("Plains"))
						g.setColor(Color.orange);
					else if(gameData.getTerrainSpecific(i,j).getTerrainType().equals("Tundra"))
						g.setColor(Color.gray);
					else if(gameData.getTerrainSpecific(i,j).getTerrainType().equals("Water"))
						g.setColor(new Color(0, 102, 204));
					else if(gameData.getTerrainSpecific(i,j).getTerrainType().equals("Coast"))
						g.setColor(new Color(0, 204, 204));					
											
				}
				g.fillRect(miniMapX + i*(minimapWidth/mapWidth), miniMapY + j*(minimapHeight/mapHeight), minimapWidth/mapWidth, minimapHeight/mapHeight);					
			}			
		}
		//minimap cities

		for(int i = 0; i < gameData.getPlayerCities().size(); i++)
		{
			int cityTile = gameData.getPlayerCitiesSpecific(i).getCityTileNumber();

			int cityY = cityTile/mapHeight;
			int cityX = cityTile%mapWidth-1;

			//draws the territory tiles
			int distanceY = gameData.getPlayerCitiesSpecific(i).getInfluence();
			for(int j = 0; j < gameData.getPlayerCitiesSpecific(i).getInfluence()*2 + 1; j++)
			{
				int distanceX = gameData.getPlayerCitiesSpecific(i).getInfluence();
				
				for(int k = 0; k < gameData.getPlayerCitiesSpecific(i).getInfluence()*2 + 1; k++)
				{
					g.setColor(Color.red);					

					g.fillRect(miniMapX + (cityX -  distanceX)*(minimapWidth/mapWidth), miniMapY + (cityY - distanceY)*(minimapHeight/mapHeight) , minimapWidth/mapWidth, minimapHeight/mapHeight);

					distanceX--;
				}
				distanceY--;				
			}

			//draw the city dot			
			g.setColor(Color.white);			

			g.fillOval(miniMapX + cityX*(minimapWidth/mapWidth), miniMapY + cityY*(minimapHeight/mapHeight), minimapWidth/mapWidth, minimapHeight/mapHeight);
		}

		g.setColor(Color.black);	
		//creates the physical grid
		for(int i = 0; i < mapWidth; i++)
		{
			g.fillRect(miniMapX + i*(minimapWidth/mapWidth),miniMapY,1,minimapHeight);
			g.fillRect(miniMapX,miniMapY + i*(minimapHeight/mapWidth),minimapWidth,1);
		}
	}
	//file menu
	public void paintFileMenu(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0,0,1600,30);

		g.setColor(Color.white);
		g.draw3DRect(1500,5,50,20,true);
		g.drawString("File",1510,20);
		g.drawString("Turn: " + gameData.getTurnNumber(),1450,20);

		if(pauseMenu == true)
		{
			int pauseMenuX = 700;
			g.setColor(Color.black);
			g.fillRect(pauseMenuX,400,200,200);

			g.setColor(Color.white);
			g.draw3DRect(pauseMenuX,400,200,50,true);
			g.draw3DRect(pauseMenuX,450,200,50,true);
			g.draw3DRect(pauseMenuX,500,200,50,true);
			g.draw3DRect(pauseMenuX,550,200,50,true);	

			g.drawString("Save",pauseMenuX + 50,425);
			g.drawString("Load",pauseMenuX + 50,475);
			g.drawString("Main Menu",pauseMenuX + 50,525);
			g.drawString("Exit to Windows",pauseMenuX + 50,575);
		}
	}
	//other methods	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub		
		
	}
	public void keyPressed(KeyEvent e)
	{
		if(mapView == true && cityConstructionFocus == false && pauseMenu == false)
		{
			//moves the map around
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
			{
				gameData.setCurrentMapHorizontal(gameData.getCurrentMapHorizontal()-1);					

				//units
				for(int i = 0; i < gameData.getPlayerUnits().size(); i++)
				{
					gameData.getPlayerUnitsSpecific(i).setX(gameData.getPlayerUnitsSpecific(i).getX() + 75);
				}
				//cities
				for(int i = 0; i < gameData.getPlayerCities().size(); i++)
				{
					gameData.getPlayerCitiesSpecific(i).setX(gameData.getPlayerCitiesSpecific(i).getX() + 75);
				}	

				gameData.resetPlayerObjectsCoordinates("Right",mapWidth);					
			}			
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
			{
				gameData.setCurrentMapHorizontal(gameData.getCurrentMapHorizontal()+1);

				for(int i = 0; i < gameData.getPlayerUnits().size(); i++)
				{
					gameData.getPlayerUnitsSpecific(i).setX(gameData.getPlayerUnitsSpecific(i).getX() - 75);
				}
				//cities
				for(int i = 0; i < gameData.getPlayerCities().size(); i++)
				{
					gameData.getPlayerCitiesSpecific(i).setX(gameData.getPlayerCitiesSpecific(i).getX() - 75);
				}

				gameData.resetPlayerObjectsCoordinates("Left",mapWidth);					
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
			{
				if(gameData.getCurrentMapVertical() - 1 > -1)
				{
					gameData.setCurrentMapVertical(gameData.getCurrentMapVertical() - 1);

					for(int i = 0; i < gameData.getPlayerUnits().size(); i++)
					{
						gameData.getPlayerUnitsSpecific(i).setY(gameData.getPlayerUnitsSpecific(i).getY() + 75);
					}
					//cities
					for(int i = 0; i < gameData.getPlayerCities().size(); i++)
					{
						gameData.getPlayerCitiesSpecific(i).setY(gameData.getPlayerCitiesSpecific(i).getY() + 75);
					}
				}									
			}		
			else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
			{
				if(gameData.getCurrentMapVertical() + 12  < mapHeight)
				{
					for(int i = 0; i < gameData.getPlayerUnits().size(); i++)
					{
						gameData.getPlayerUnitsSpecific(i).setY(gameData.getPlayerUnitsSpecific(i).getY() - 75);
					}
					gameData.setCurrentMapVertical(gameData.getCurrentMapVertical() + 1);
					//cities
					for(int i = 0; i < gameData.getPlayerCities().size(); i++)
					{
						gameData.getPlayerCitiesSpecific(i).setY(gameData.getPlayerCitiesSpecific(i).getY() - 75);
					}
				}										
			}					
		}
		if(mapView == true && cityConstructionFocus == true)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
			{
				if(0 - currentBuildOptionsVertical > 0)
					currentBuildOptionsVertical++;
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
			{
				if(0 - currentBuildOptionsVertical < gameData.getPlayerBuildableUnits().size())				
					currentBuildOptionsVertical--;
			}
		}
		//ESC
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			//pause menu
			if(pauseMenu == true && gameData.getCityFocusNumber() < 0)
				pauseMenu = false;
			else if(pauseMenu == false && gameData.getCityFocusNumber() < 0)
				pauseMenu = true;
			//city deselect
			if(mapView == true)
			{
				if(gameData.getCityFocusNumber() > -1)
					gameData.setCityFocusNumber(-1);												
			}			
			//goes back to main menu if aren't in game yet		
			if(newGameSetup = true)
			{
				newGameSetup = false;
				mapSizeOptions = false;
				terrainOptionsAction = false;
				mainMenu = true;
			}	
		}		
	}
	public void keyReleased(KeyEvent e)
	{

	}
	public void keyTyped(KeyEvent e)
	{

	}
	@Override
	public void mouseClicked(MouseEvent e)
	{
		//map sizes
		if(newGameSetup == true && mapSizeOptions == true && pauseMenu == false)
		{
			//small map
			if(e.getX() > 725 && e.getX() < 825 && e.getY() > 75 & e.getY() < 125)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");				
				mapSize = "Small";
				mapWidth = 25;
				mapHeight = 25;
				mapSizeOptions = false;
			}
			//medium map
			if(e.getX() > 725 && e.getX() < 825 && e.getY() > 126 & e.getY() < 175)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				mapSize = "Medium";
				mapWidth = 50;
				mapHeight = 50;
				mapSizeOptions = false;
			}
			//large map
			if(e.getX() > 725 && e.getX() < 825 && e.getY() > 176 & e.getY() < 225)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				mapSize = "Large";
				mapWidth = 70;
				mapHeight = 70;
				mapSizeOptions = false;
			}
			//huge map
			if(e.getX() > 725 && e.getX() < 825 && e.getY() > 226 & e.getY() < 275)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				mapSize = "Huge";
				mapWidth = 175;
				mapHeight = 175;
				mapSizeOptions = false;
			}
		}
		if(newGameSetup == true && pauseMenu == false)
		{
			//map sizes menu
			if(e.getX() > 600 && e.getX() < 700 && e.getY() > 100 & e.getY() < 150)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				if(mapSizeOptions == false)
					mapSizeOptions = true;
				else
					mapSizeOptions = false;
			}
			//terrain options
			//map sizes menu
			if(e.getX() > 600 && e.getX() < 700 && e.getY() > 200 & e.getY() < 250)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				if(terrainOptionsAction == false)
					terrainOptionsAction = true;
				else
					terrainOptionsAction = false;
			}
			//start game
			if(e.getX() > 900 && e.getX() < 1000 && e.getY() > 725 & e.getY() < 775)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				newGameSetup = false;
				mapView = true;	
				startGame = true;		
			}
		}
		//terrain options
		if(terrainOptionsAction == true && pauseMenu == false)
		{
			//map sizes menu
			if(e.getX() > 725 && e.getX() < 850 && e.getY() > 150 & e.getY() < 200)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");				
				terrainOptions = "Pangea";
				terrainType = terrainOptions;
				terrainOptionsAction = false;
			}
			//start game
			if(e.getX() > 725 && e.getX() < 850 && e.getY() > 200 & e.getY() < 250)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				terrainOptions = "Archipelago";
				terrainType = terrainOptions;
				terrainOptionsAction = false;		
			}
		}
		//building menu
		if(mapView == true && gameData.getCityFocusNumber() > -1 && cityConstructionFocus == true && pauseMenu == false)
		{
			int cityX = gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getX();
			int cityY = gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).getY();

			if(e.getX() > cityX - 150 && e.getX() < cityX+50)
			{			
				int tempy = e.getY()-25; //takes in to account the frame

				int buildY = (int)Math.floor(((tempy-100)/50));//sets base line for where the building menu is and what item is clicked

				gameData.setNewUnitToBuild(buildY-currentBuildOptionsVertical, gameData.getPlayerBuildableUnitsSpecific(buildY-currentBuildOptionsVertical).getName(),(int)Math.floor((cityX+gameData.getCurrentMapHorizontal())/75),(int)Math.floor((cityY+gameData.getCurrentMapVertical()-2)/75));
				cityConstructionFocus = false;
			}
		}
		if(gameData.getPlayerTurn() == true && pauseMenu == false)
		{
			//next turn
			if(e.getX() > 1125 && e.getX() < 1200 && e.getY() > 775 & e.getY() < 815)
			{
				gameData.setPlayerTurn(false);
				gameData.setUnitFocusNumber(-1);
				for(int i = 0; i < gameData.getPlayerCities().size(); i++)
				{
					gameData.getPlayerCitiesSpecific(i).checkGrowth();
				}
				gameData.endPlayerTurn();
				gameData.setPlayerTurn(true);
				gameData.setTurnNumber(gameData.getTurnNumber()+1);		
			}
			//minimap to terrain map movement			
			if(e.getX() > 1225 && e.getX() < 1575 && e.getY() > 525 & e.getY() < 875)
			{
				int tempx = e.getX()- 1225; //takes into account the frame
				int tempy = e.getY()- 525; //takes in to account the frame

				int x = (int)Math.floor((tempx/(minimapWidth/mapWidth)));
				int y = (int)Math.floor((tempy/(minimapWidth/mapWidth)));

				int terrainX;

				int tempMH = gameData.getCurrentMapHorizontal();
				int tempMV = gameData.getCurrentMapVertical();

				//establishes which array of the terrain the user has clicked
				terrainX = x%mapWidth;

				if(x - 10 > -1 &&  y - 6 > -1)
				{
					gameData.setCurrentMapHorizontal(x - 10);

					if(y - 6 + mapY < mapHeight)
						gameData.setCurrentMapVertical(y - 6);
					else
						gameData.setCurrentMapVertical(mapHeight - mapY);

					int deltaX = (tempMH - gameData.getCurrentMapHorizontal())*75;
					int deltaY = (tempMV - gameData.getCurrentMapVertical())*75;
					gameData.updateAllMovementData(deltaX,deltaY);
				}
				else if(x - 10 < -1 && y - 6 > -1)
				{
					gameData.setCurrentMapHorizontal(0);

					if(y - 6 + mapY < mapHeight)
						gameData.setCurrentMapVertical(y - 6);
					else
						gameData.setCurrentMapVertical(mapHeight - mapY);

					int deltaX = (tempMH - gameData.getCurrentMapHorizontal())*75;
					int deltaY = (tempMV - gameData.getCurrentMapVertical())*75;
					gameData.updateAllMovementData(deltaX,deltaY);
				}
				else if(x - 10 > -1 && y - 6 < -1)
				{
					gameData.setCurrentMapHorizontal(x - 10);
					gameData.setCurrentMapVertical(0);

					int deltaX = (tempMH - gameData.getCurrentMapHorizontal())*75;
					int deltaY = (tempMV - gameData.getCurrentMapVertical())*75;
					gameData.updateAllMovementData(deltaX,deltaY);
				}
			}			
			//special ability button
			if(mapView == true && gameData.getUnitFocusNumber() > -1)
			{
				if(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getSpecialAbility() != "")
				{
					if(e.getX() > 220 && e.getX() < 270 && e.getY() > 680 & e.getY() < 705)
					{
						audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
						gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).setSpecialAbilityActivate(true);
					}
				}
			}
			//sets a city to be focused
			for(int i = 0; i < gameData.getPlayerCities().size(); i++)
			{
				int unitX = (int)Math.floor((gameData.getPlayerCitiesSpecific(i).getX()/75));
				int unitY = (int)Math.floor((gameData.getPlayerCitiesSpecific(i).getY()/75));

				if(unitX == currentMouseMapHoriztonal && unitY == currentMouseMapVertical)
				{
					gameData.getPlayerCitiesSpecific(i).setFocus(true);
					gameData.setUnitFocusNumber(-1);
					gameData.setCityFocusNumber(i);
					break;
				}													
			}
			//city focuses
			if(gameData.getCityFocusNumber() > -1)
			{
				//production focus
				if(e.getX() > 1380 && e.getX() < 1490 && e.getY() > 295 & e.getY() < 320)
				{
					gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).setProductionFocus(true);
					gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).setTileYields();
				}
				//food focus
				if(e.getX() > 1380 && e.getX() < 1490 && e.getY() > 325 & e.getY() < 350)
				{
					gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).setFoodFocus(true);
					gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).setTileYields();
				}
				//Health focus
				if(e.getX() > 1380 && e.getX() < 1490 && e.getY() > 355 & e.getY() < 380)
				{
					gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).setHealthFocus(true);
					gameData.getPlayerCitiesSpecific(gameData.getCityFocusNumber()).setTileYields();
				}
				//gold focus
				if(e.getX() > 1380 && e.getX() < 1490 && e.getY() > 385 & e.getY() < 410)
				{
					//playerCities.get(cityFocusNumber).setHealth();
				}
				//manual focus
				if(e.getX() > 1380 && e.getX() < 1490 && e.getY() > 415 & e.getY() < 440)
				{
					
				}
				//construction
				if(e.getX() > 1380 && e.getX() < 1490 && e.getY() > 445 & e.getY() < 470)
				{
					if(cityConstructionFocus == false)
						cityConstructionFocus = true;
					else
						cityConstructionFocus = false;					
				}
			}
		}
		//pause menu
		if(mapView == true && pauseMenu == false)
		{
			if(e.getX() > 1500 && e.getX() < 1550 && e.getY() > 30 && e.getY() < 50)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				pauseMenu = true;
			}								
		}
		//main menu
		if(mainMenu == true && pauseMenu == false)
		{
			//new game			
			if(e.getX() > 700 && e.getX() < 800 && e.getY() > 400 && e.getY() < 450)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				gameWindow.newGame();
			}				
			//load game
			if(e.getX() > 700 && e.getX() < 800 && e.getY() > 460 && e.getY() < 510)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				gameWindow.loadGame();
			}				
			//exit game
			if(e.getX() > 700 && e.getX() < 800 && e.getY() > 520 && e.getY() < 570)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				gameWindow.exitGame();
			}				
		}
		if(pauseMenu == true)
		{
			//save game			
			if(e.getX() > 700 && e.getX() < 900 && e.getY() > 400 && e.getY() < 450)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				gameWindow.saveGame();
				pauseMenu = false;
			}				
			//load game			
			if(e.getX() > 700 && e.getX() < 900 && e.getY() > 450 && e.getY() < 500)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");
				gameWindow.loadGame();
			}				
			//return to main menu			
			if(e.getX() > 700 && e.getX() < 900 && e.getY() > 500 && e.getY() < 550)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");

				int t = JOptionPane.showConfirmDialog(this,"Are you sure you want to return to the main menu?",null, JOptionPane.YES_NO_OPTION);

				if(t == 0)
				{
					gameWindow.saveGame();
					gameWindow.returnMainMenu();
				}				
			}
			//exit game			
			if(e.getX() > 700 && e.getX() < 900 && e.getY() > 550 && e.getY() < 600)
			{
				audioPlayer.playAudio("/Sounds/primaryMouseClick.wav");

				int t = JOptionPane.showConfirmDialog(this,"Do you want to save before you quit?",null, JOptionPane.YES_NO_OPTION);

				if(t == 0)
				{
					pauseMenu = false;
					gameWindow.saveGame();
					gameWindow.exitGame();
				}
				else
					gameWindow.exitGame();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) 
	{
	
		
	}
	@Override
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		if(mapView == true && (e.getX() > 0 && e.getX() < 1125 && e.getY() > 0 & e.getY() < 750))
		{
			int x = e.getX()-3; //takes into account the frame
			int y = e.getY()-25; //takes in to account the frame

			//establishes which array of the terrain the user has clicked
			currentMouseMapHoriztonal = (int)Math.floor((x/75));
			currentMouseMapVertical = (int)Math.floor((y/75));

			//sets a unit to be focused
			for(int i = 0; i < gameData.getPlayerUnits().size(); i++)
			{
				int unitX = (int)Math.floor((gameData.getPlayerUnitsSpecific(i).getX()/75));
				int unitY = (int)Math.floor((gameData.getPlayerUnitsSpecific(i).getY()/75));

				if(unitX == currentMouseMapHoriztonal && unitY == currentMouseMapVertical)
				{
					gameData.getPlayerUnitsSpecific(i).setFocus(true);				
					gameData.setUnitFocusNumber(i);
					gameData.setCityFocusNumber(-1);
					break;
				}								
			}		
		}		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
		if(mapView == true && (e.getX() > 0 && e.getX() < 1125 && e.getY() > 0 & e.getY() < 750) && gameData.getUnitFocusNumber() > -1)
		{
			int x = e.getX()-3; //takes into account the frame
			int y = e.getY()-25; //takes in to account the frame

			//establishes which array of the terrain the user has clicked
			int newX = (int)Math.floor((x/75));
			int newY = (int)Math.floor((y/75));

			newX = (gameData.getCurrentMapHorizontal()+ newX)%mapWidth;

			System.out.println(newX);
		
			//moves selected unit
			if(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getMovement() > 0 && gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getFocus() == true && gameData.checkUnitMovement(newX,newY) == true)
			{				
				gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).moveUnit((int)Math.floor((x/75)),newY);
				gameData.updateUnitMovementData(newX,newY,(int)Math.floor((x/75)));
				gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).setFocus(false);
			}
		}		
	}
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
				
	}
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
		if(gameData.getPlayerTurn() == true)
		{
			//in terrain view
			if(mapView == true && (e.getX() > 0 && e.getX() < 1125 && e.getY() > 0 & e.getY() < 750))
			{
				int tempx = e.getX()-3; //takes into account the frame
				int tempy = e.getY()-25; //takes in to account the frame

				int x = (int)Math.floor((tempx/75));
				int y = (int)Math.floor((tempy/75));

				int terrainX;

				//establishes which array of the terrain the user has clicked
				if((gameData.getCurrentMapHorizontal()+ x)%mapWidth >= 0)
					terrainX = (gameData.getCurrentMapHorizontal()+ x)%mapWidth;
				else
					terrainX = (gameData.getCurrentMapHorizontal()+ x)%mapWidth + mapWidth;

				if(gameData.getTerrainSpecific(terrainX, y + gameData.getCurrentMapVertical()).getVisability() == true)
				{
					currentMouseMapHoriztonal = x;
					currentMouseMapVertical = y;
					terrainFocus = true;
				}
				else
				{
					terrainFocus = false;
				}											
			}
		}				
	}	
}
