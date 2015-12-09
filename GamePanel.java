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


public class GamePanel extends JPanel  implements ActionListener,KeyListener,MouseListener,MouseMotionListener,Serializable
{
	//globals
	private  int mapHeight;
	private  int mapWidth;
	
	private  int currentMouseMapHoriztonal;
	private  int currentMouseMapVertical;	
	private  int currentBuildOptionsVertical; 

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

	private String terrainOptions;
	private String terrainType;
	private String mapSize;

	private Font mainMenuTitle;
	private Font mainMenuOptions;

	private ImageIcon mainBackground;

	private GameData gameData;

	private AudioPlayer audioPlayer;

	public GamePanel()
	{
		mapHeight = 0;
	 	mapWidth= 0;		
	 	currentMouseMapHoriztonal= 0;
	 	currentMouseMapVertical= 0;
	 	currentBuildOptionsVertical = 0;

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

		mainMenuTitle = new Font("Algerian",Font.BOLD,75);
		mainMenuOptions = new Font("Calibri",Font.BOLD,15);

	 	gameData = new GameData();

	 	audioPlayer = new AudioPlayer();
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
			paintConstructionMenu(g);						
		}						
	}	
	//paints the terrain
	public void paintTerrain(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0,0,1600,900);

		for(int h = 0; h < gameData.getPlayerUnits().size(); h++)
		{
			int unitX = (int)Math.floor((gameData.getPlayerUnitsSpecific(h).getX()/75));
			int unitY = (int)Math.floor((gameData.getPlayerUnitsSpecific(h).getY()/75));

			for(int k = (unitY + gameData.getCurrentMapVertical()) -1; k < (unitY + gameData.getCurrentMapVertical()) + 2; k++)
			{
				for(int l = (unitX+ gameData.getCurrentMapHorizontal()) -1; l < (unitX+gameData.getCurrentMapHorizontal()) + 2; l++)
				{
					if(k > -1 && k < mapHeight && l > -1 && l < mapWidth)
					{
						if(gameData.getTerrainSpecific(l,k).getVisability() == false)
							gameData.getTerrainSpecific(l,k).setVisability(true);
					}					
				}							
			}							
		}
		//checks terrain around newly settled city
		if(gameData.getNewCitySettled() == true)
			gameData.checkNewCityTerrain(mapWidth, mapHeight);
		//paints the terrain		
		for(int i = 0; i < 10;i++)
		{
			for(int j = 0; j < 15; j++)
			{				
				//if(gameData.getTerrainSpecific(j+gameData.getCurrentMapHorizontal(), i + gameData.getCurrentMapVertical()).getVisability() == true)
				//{
					gameData.getTerrainSpecific(j+gameData.getCurrentMapHorizontal(), i + gameData.getCurrentMapVertical()).getTerrainImage().paintIcon(this,g,j*75,i*75);
					gameData.getTerrainSpecific(j+gameData.getCurrentMapHorizontal(), i + gameData.getCurrentMapVertical()).getTerrainFeaturesImage().paintIcon(this,g,j*75,i*75);
					gameData.getTerrainSpecific(j+gameData.getCurrentMapHorizontal(), i + gameData.getCurrentMapVertical()).getTerrainResourceImage().paintIcon(this,g,j*75,i*75);
				//}
				//else
				//{
					//gameData.getFogSpecific(j+gameData.getCurrentMapHorizontal(), i + gameData.getCurrentMapVertical()).paintIcon(this,g,j*75,i*75);
				//}				
			}
		}
		//creates the physical grid
		for(int i = 0; i < 16; i++)
		{
			g.fillRect(i*75,0,1,750);
		}
		for(int i = 0; i < 11; i++)
		{
			g.fillRect(0,i*75,1125,1);
		}
	}

	//paints units
	public void paintUnits(Graphics g)
	{
		for(int i = 0; i < gameData.getPlayerUnits().size(); i++)
		{
			//paints the unit
			if(gameData.getPlayerUnitsSpecific(i).getX() >= 0 && gameData.getPlayerUnitsSpecific(i).getX() <= 1125 && gameData.getPlayerUnitsSpecific(i).getY() >= 0 && gameData.getPlayerUnitsSpecific(i).getY() < 750)
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
						gameData.settleNewCity();
						gameData.getPlayerCitiesSpecific(gameData.getPlayerCities().size()-1).setCityName(JOptionPane.showInputDialog(this,"City Settled!" + "\n" + "Choose your city's name:"));
					}	
				}								
			}
		}
	}

	//paints cities
	public void paintCities(Graphics g)
	{
		g.setColor(Color.blue);
		for(int i = 0; i < gameData.getPlayerCities().size(); i++)
		{
			//paints cities
			gameData.getPlayerCitiesSpecific(i).getCityImage().paintIcon(this,g,gameData.getPlayerCitiesSpecific(i).getX(),gameData.getPlayerCitiesSpecific(i).getY());	
			Font population = new Font("Impact",Font.BOLD,25);
			g.setFont(population);
			g.drawString("" + gameData.getPlayerCitiesSpecific(i).getPopulation(),gameData.getPlayerCitiesSpecific(i).getX()+5,gameData.getPlayerCitiesSpecific(i).getY()+25);

			//city border
			int cityX = gameData.getPlayerCitiesSpecific(i).getX();
			int cityY = gameData.getPlayerCitiesSpecific(i).getY();

			g.fillRect(cityX-75*gameData.getPlayerCitiesSpecific(i).getInfluence(),gameData.getPlayerCitiesSpecific(i).getY()-75 - 75*(gameData.getPlayerCitiesSpecific(i).getInfluence()-1),10,75*3 + 75*2*(gameData.getPlayerCitiesSpecific(i).getInfluence() - 1));	
			g.fillRect(gameData.getPlayerCitiesSpecific(i).getX()+75*2 + 75*(gameData.getPlayerCitiesSpecific(i).getInfluence()-1),gameData.getPlayerCitiesSpecific(i).getY()-75*gameData.getPlayerCitiesSpecific(i).getInfluence(),10,75*3 + 75*2*(gameData.getPlayerCitiesSpecific(i).getInfluence() - 1));
			g.fillRect(gameData.getPlayerCitiesSpecific(i).getX()-75*gameData.getPlayerCitiesSpecific(i).getInfluence(),gameData.getPlayerCitiesSpecific(i).getY()-75 - 75*(gameData.getPlayerCitiesSpecific(i).getInfluence()-1),75*3 + 75*2*(gameData.getPlayerCitiesSpecific(i).getInfluence() - 1),10);
			g.fillRect(gameData.getPlayerCitiesSpecific(i).getX()-75*gameData.getPlayerCitiesSpecific(i).getInfluence(),gameData.getPlayerCitiesSpecific(i).getY()+75*gameData.getPlayerCitiesSpecific(i).getInfluence()+65,75*3 + 75*2*(gameData.getPlayerCitiesSpecific(i).getInfluence() - 1),10);
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
			audioPlayer.playAudio("/Sounds/mainMusic.wav");
			startMusic = false;
		}
		
		mainBackground = new ImageIcon(getClass().getResource("/Images/Backgrounds/StartBackground.png"));
		mainBackground.paintIcon(this,g,0,0);

		g.setFont(mainMenuTitle);
		g.setColor(Color.white);
		g.drawString("Nations Throughout Time",200,100);
	}


	//paints main menu
	public void paintSetupScreen(Graphics g)
	{
		audioPlayer.setStopPlayback(true);
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
		g.setColor(Color.gray);
		g.fillRect(1150,0,400,750);
		g.setFont(mainMenuOptions);

		//player info

		//next turn
		g.setColor(Color.white);
		g.draw3DRect(1450,60,75,40,true);
		g.drawString("Next Turn",1460,80);
		//tile info
		
		g.draw3DRect(1175,25,200,130,true);
		if(terrainFocus == true)
		{
			if(gameData.getTerrainSpecific(currentMouseMapHoriztonal+gameData.getCurrentMapHorizontal(),currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainFeatures() != "")
				g.drawString(gameData.getTerrainSpecific(currentMouseMapHoriztonal+gameData.getCurrentMapHorizontal(),currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainType() + " , " + gameData.getTerrainSpecific(currentMouseMapHoriztonal+gameData.getCurrentMapHorizontal(),currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainFeatures(),1180,40);
			else
				g.drawString(gameData.getTerrainSpecific(currentMouseMapHoriztonal+gameData.getCurrentMapHorizontal(),currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainType(),1180,40);

			g.drawString(gameData.getTerrainSpecific(currentMouseMapHoriztonal+gameData.getCurrentMapHorizontal(),currentMouseMapVertical+gameData.getCurrentMapVertical()).getTerrainResources(),1180,60);
			g.drawString("Food: " + gameData.getTerrainSpecific(currentMouseMapHoriztonal+gameData.getCurrentMapHorizontal(),currentMouseMapVertical+gameData.getCurrentMapVertical()).getFood(),1180,80);
			g.drawString("Production: " + gameData.getTerrainSpecific(currentMouseMapHoriztonal+gameData.getCurrentMapHorizontal(),currentMouseMapVertical+gameData.getCurrentMapVertical()).getProduction(),1180,100);
			g.drawString("Health: " + gameData.getTerrainSpecific(currentMouseMapHoriztonal+gameData.getCurrentMapHorizontal(),currentMouseMapVertical+gameData.getCurrentMapVertical()).getHealth(),1180,120);
			g.drawString("Science: " + gameData.getTerrainSpecific(currentMouseMapHoriztonal+gameData.getCurrentMapHorizontal(),currentMouseMapVertical+gameData.getCurrentMapVertical()).getScience(),1180,140);
		}
			
		//unit info	
		g.draw3DRect(1175,160,200,100,true);
		
		if(gameData.getUnitFocusNumber() > -1)
		{
			g.draw3DRect(1380,160,50,25,true);
			g.draw3DRect(1380,190,50,25,true);
			g.draw3DRect(1380,220,50,25,true);

			g.drawString(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getName(), 1180,180);
			g.drawString("Strength: " + gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getStrength(), 1180,200);	
			g.drawString("Health: " + gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getHealth(), 1180,220);	
			g.drawString("Movement: " + gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getMovement(), 1180,240);	

			if(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getSpecialAbility() != "")
			{
				g.drawString(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getSpecialAbility(),1385,180);
			}		
		}

		//city info
		g.draw3DRect(1175,265,200,200,true);		

		if(gameData.getCityFocusNumber() > -1)
		{
			g.draw3DRect(1380,265,110,25,true);
			g.draw3DRect(1380,295,110,25,true);
			g.draw3DRect(1380,325,110,25,true);
			g.draw3DRect(1380,355,110,25,true);
			g.draw3DRect(1380,385,110,25,true);
			g.draw3DRect(1380,415,110,25,true);
			g.draw3DRect(1380,445,110,25,true);

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

		//game info
		g.drawString("Turn: " + gameData.getTurnNumber(),1450,50);
	}

	//paints mini map


	//other methods	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub		
		
	}
	public void keyPressed(KeyEvent e)
	{
		if(mapView == true && cityConstructionFocus == false)
		{
			//moves the map around
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
			{
				if(gameData.getCurrentMapHorizontal() - 1 >= 0)
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
				}					
			}			
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
			{
				if(gameData.getCurrentMapHorizontal() + 15 < mapWidth)
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
				}					
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
				if(gameData.getCurrentMapVertical() + 10  < mapHeight)
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
		if(newGameSetup == true && mapSizeOptions == true)
		{
			//small map
			if(e.getX() > 725 && e.getX() < 825 && e.getY() > 75 & e.getY() < 125)
			{				
				mapSize = "Small";
				mapWidth = 25;
				mapHeight = 25;
				mapSizeOptions = false;
			}
			//medium map
			if(e.getX() > 725 && e.getX() < 825 && e.getY() > 126 & e.getY() < 175)
			{
				mapSize = "Medium";
				mapWidth = 50;
				mapHeight = 50;
				mapSizeOptions = false;
			}
			//large map
			if(e.getX() > 725 && e.getX() < 825 && e.getY() > 176 & e.getY() < 225)
			{
				mapSize = "Large";
				mapWidth = 75;
				mapHeight = 75;
				mapSizeOptions = false;
			}
			//huge map
			if(e.getX() > 725 && e.getX() < 825 && e.getY() > 226 & e.getY() < 275)
			{
				mapSize = "Huge";
				mapWidth = 100;
				mapHeight = 100;
				mapSizeOptions = false;
			}
		}
		if(newGameSetup == true)
		{
			//map sizes menu
			if(e.getX() > 600 && e.getX() < 700 && e.getY() > 100 & e.getY() < 150)
			{
				if(mapSizeOptions == false)
					mapSizeOptions = true;
				else
					mapSizeOptions = false;
			}
			//terrain options
			//map sizes menu
			if(e.getX() > 600 && e.getX() < 700 && e.getY() > 200 & e.getY() < 250)
			{
				if(terrainOptionsAction == false)
					terrainOptionsAction = true;
				else
					terrainOptionsAction = false;
			}
			//start game
			if(e.getX() > 900 && e.getX() < 1000 && e.getY() > 725 & e.getY() < 775)
			{
				newGameSetup = false;
				mapView = true;	
				startGame = true;		
			}
		}
		//terrain options
		if(terrainOptionsAction == true)
		{
			//map sizes menu
			if(e.getX() > 725 && e.getX() < 850 && e.getY() > 150 & e.getY() < 200)
			{				
				terrainOptions = "Pangea";
				terrainType = terrainOptions;
				terrainOptionsAction = false;
			}
			//start game
			if(e.getX() > 725 && e.getX() < 850 && e.getY() > 200 & e.getY() < 250)
			{
				terrainOptions = "Archipelago";
				terrainType = terrainOptions;
				terrainOptionsAction = false;		
			}
		}
		//building menu
		if(mapView == true && gameData.getCityFocusNumber() > -1 && cityConstructionFocus == true)
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
		if(gameData.getPlayerTurn() == true)
		{
			//next turn
			if(e.getX() > 1450 && e.getX() < 1525 && e.getY() > 85 & e.getY() < 125)
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
			
			//special ability button
			if(mapView == true && gameData.getUnitFocusNumber() > -1)
			{
				if(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getSpecialAbility() != "")
				{
					if(e.getX() > 1380 && e.getX() < 1430 && e.getY() > 185 & e.getY() < 210)
					{
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
		
			//moves selected unit
			if(gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getMovement() > 0 && gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).getFocus() == true && gameData.checkUnitMovement(newX,newY) == true)
			{				
				gameData.getPlayerUnitsSpecific(gameData.getUnitFocusNumber()).moveUnit(newX,newY);
				gameData.updateUnitMovementData(newX,newY);
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

				//establishes which array of the terrain the user has clicked
				if(gameData.getTerrainSpecific(x + gameData.getCurrentMapHorizontal(), y + gameData.getCurrentMapVertical()).getVisability() == true)
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
