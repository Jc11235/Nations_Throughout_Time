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
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class GamePanel extends JPanel  implements ActionListener,KeyListener,MouseListener,MouseMotionListener,Serializable
{
	//globals
	private  int mapHeight = 0;
	private  int mapWidth= 0;
	private  int currentMapHoriztonal= 0;
	private  int currentMapVertical= 0;
	private  int currentMouseMapHoriztonal= 0;
	private  int currentMouseMapVertical= 0;
	private  int turnNumber= 0;
	private  int unitFocusNumber = 0;
	private  int cityFocusNumber = -1;

	private  Random r = new Random();

	private Terrain[][] terrain;
	private ImageIcon[][] fog;
	private ArrayList<Unit> playerUnits = new ArrayList<Unit>();
	private ArrayList<City> playerCities = new ArrayList<City>();

	private boolean terrainCreate = true;
	private boolean mainMenu = true;
	private boolean newGameSetup = false;
	private boolean mapView = false;
	private boolean mapSizeOptions = false;
	private boolean playerTurn = true;
	private boolean newCitySettled = false;
	private boolean terrainFocus = false;
	private boolean cityConstructionFocus = false;
	private boolean[] move = new boolean[4];

	private Font mainMenuTitle = new Font("Algerian",Font.BOLD,75);
	private Font mainMenuOptions = new Font("Calibri",Font.BOLD,15);

	private Player player = new Player();

	private FileActions fA;

	private AI aI = new AI();

	public GamePanel()
	{

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
	public int getCurrentMapHorizontal()
	{
		return currentMapHoriztonal;
	}
	public boolean getMapView()
	{
		return mapView;
	}
	public Terrain[][] getTerrain()
	{
		return terrain;
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
			setupGame();
			
			paintTerrain(g);
			paintCities(g);
			paintUnits(g);
			paintHud(g);						
		}						
	}

	//sets up the inital conditions of the game
	public void setupGame()
	{
		if(terrainCreate == true)
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
				
			terrainCreate = false;
		}
	}

	public void paintTerrain(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0,0,1600,900);

		for(int h = 0; h < playerUnits.size(); h++)
		{
			int unitX = (int)Math.floor((playerUnits.get(h).getX()/75));
			int unitY = (int)Math.floor((playerUnits.get(h).getY()/75));

			for(int k = (unitY + currentMapVertical) -1; k < (unitY + currentMapVertical) + 2; k++)
			{
				for(int l = (unitX+ currentMapHoriztonal) -1; l < (unitX+currentMapHoriztonal) + 2; l++)
				{
					if(k > -1 && k < mapHeight && l > -1 && l < mapWidth)
					{
						if(terrain[k][l].getVisability() == false)
							terrain[k][l].setVisability(true);
					}					
				}							
			}							
		}
		if(newCitySettled == true)
		{
			int cityX = (int)Math.floor((playerCities.get(playerCities.size() - 1).getX()/75));
			int cityY = (int)Math.floor((playerCities.get(playerCities.size() - 1).getY()/75));

			for(int k = (cityY + currentMapVertical) -2; k < (cityY + currentMapVertical) + 3; k++)
			{
				for(int l = (cityX+ currentMapHoriztonal) -2; l < (cityX+currentMapHoriztonal) + 3; l++)
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
		//paints the terrain		
		for(int i = 0; i < 10;i++)
		{
			for(int j = 0; j < 15; j++)
			{				
				if(terrain[i+currentMapVertical][j+currentMapHoriztonal].getVisability() == true)
				{
					terrain[i+currentMapVertical][j+currentMapHoriztonal].getTerrainImage().paintIcon(this,g,j*75,i*75);
					terrain[i+currentMapVertical][j+currentMapHoriztonal].getTerrainFeaturesImage().paintIcon(this,g,j*75,i*75);
					terrain[i+currentMapVertical][j+currentMapHoriztonal].getTerrainResourceImage().paintIcon(this,g,j*75,i*75);
				}
				else
				{
					fog[i][j].paintIcon(this,g,j*75,i*75);
				}				
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
		for(int i = 0; i < playerUnits.size(); i++)
		{
			//paints the unit
			if(playerUnits.get(i).getX() >= 0 && playerUnits.get(i).getX() <= 1125 && playerUnits.get(i).getY() >= 0 && playerUnits.get(i).getY() < 750)
				playerUnits.get(i).getUnitImage().paintIcon(this,g,playerUnits.get(i).getX(),playerUnits.get(i).getY());

			//if a unit has been clicked on
			if(unitFocusNumber > -1)
			{
				g.setColor(Color.blue);
				g.drawOval(playerUnits.get(unitFocusNumber).getX(),playerUnits.get(unitFocusNumber).getY(),75,70);

				//if a units special ability was clicked
				if(playerUnits.get(unitFocusNumber).getSpecialAbilityActivate() == true)
				{
					//settle a city	
					if(playerUnits.get(unitFocusNumber).getName().equals("Settler"))
					{
						//adds new tiles for the city
						ArrayList<Terrain> cityTiles = new ArrayList<Terrain>();
						int unitX = (int)Math.floor((playerUnits.get(unitFocusNumber).getX()/75));
						int unitY = (int)Math.floor((playerUnits.get(unitFocusNumber).getY()/75));
						for(int k = (unitY + currentMapVertical) -1; k < (unitY + currentMapVertical) + 2; k++)
						{
							for(int j = (unitX+ currentMapHoriztonal) -1; j < (unitX+currentMapHoriztonal) + 2; j++)
							{
								cityTiles.add(terrain[k][j]);
							}							
						}						
						//adds new city
						playerCities.add(new City(playerUnits.get(unitFocusNumber).getX(),playerUnits.get(unitFocusNumber).getY(),cityTiles));					
						//removes settler
						playerUnits.remove(unitFocusNumber);
						unitFocusNumber = -1;
						newCitySettled = true;

						playerCities.get(playerCities.size()-1).setCityName(JOptionPane.showInputDialog(this,"City Settled!" + "\n" + "Choose your city's name:")) ;

					}	
				}								
			}
		}
	}

	//paints cities
	public void paintCities(Graphics g)
	{
		g.setColor(Color.blue);
		for(int i = 0; i < playerCities.size(); i++)
		{
			//paints cities
			playerCities.get(i).getCityImage().paintIcon(this,g,playerCities.get(i).getX(),playerCities.get(i).getY());	
			Font population = new Font("Impact",Font.BOLD,25);
			g.setFont(population);
			g.drawString("" + playerCities.get(i).getPopulation(),playerCities.get(i).getX()+5,playerCities.get(i).getY()+25);

			//city border
			int cityX = playerCities.get(i).getX();
			int cityY = playerCities.get(i).getY();

			g.fillRect(cityX-75*playerCities.get(i).getInfluence(),playerCities.get(i).getY()-75 - 75*(playerCities.get(i).getInfluence()-1),10,75*3 + 75*2*(playerCities.get(i).getInfluence() - 1));	
			g.fillRect(playerCities.get(i).getX()+75*2 + 75*(playerCities.get(i).getInfluence()-1),playerCities.get(i).getY()-75*playerCities.get(i).getInfluence(),10,75*3 + 75*2*(playerCities.get(i).getInfluence() - 1));
			g.fillRect(playerCities.get(i).getX()-75*playerCities.get(i).getInfluence(),playerCities.get(i).getY()-75 - 75*(playerCities.get(i).getInfluence()-1),75*3 + 75*2*(playerCities.get(i).getInfluence() - 1),10);
			g.fillRect(playerCities.get(i).getX()-75*playerCities.get(i).getInfluence(),playerCities.get(i).getY()+75*playerCities.get(i).getInfluence()+65,75*3 + 75*2*(playerCities.get(i).getInfluence() - 1),10);
		}
		//if a city was clicked on
		if(cityFocusNumber > -1 && cityConstructionFocus == true)
		{
			g.setColor(Color.gray);
			g.fillRect(500,400,400,300);
		}
		

	}
	//paints main menu
	public void paintMainMenu(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillRect(0,0,1450,750);

		g.setFont(mainMenuTitle);
		g.setColor(Color.white);
		g.drawString("Nations Throughout Time",200,100);
	}


	//paints main menu
	public void paintSetupScreen(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillRect(0,0,1450,750);

		g.setFont(mainMenuOptions);
		g.setColor(Color.white);
		g.drawString("MapSize",125,125);

		g.draw3DRect(100,100,100,50,true);

		if(mapSizeOptions == true)
		{
			for(int i = 0; i < 4; i++)
			{
				g.draw3DRect(250,50*(i+1),100,50,true);
			}
			g.drawString("Small",275,75);
			g.drawString("Medium",275,125);
			g.drawString("Large",275,175);
			g.drawString("Huge",275,225);
		}

		g.draw3DRect(200,700,100,50,true);
		g.drawString("Start",225,725);
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
			if(terrain[currentMouseMapVertical+currentMapVertical][currentMouseMapHoriztonal+currentMapHoriztonal].getTerrainFeatures() != "")
				g.drawString(terrain[currentMouseMapVertical+currentMapVertical][currentMouseMapHoriztonal+currentMapHoriztonal].getTerrainType() + " , " + terrain[currentMouseMapVertical+currentMapVertical][currentMouseMapHoriztonal+currentMapHoriztonal].getTerrainFeatures(),1180,40);
			else
				g.drawString(terrain[currentMouseMapVertical+currentMapVertical][currentMouseMapHoriztonal+currentMapHoriztonal].getTerrainType(),1180,40);

			g.drawString(terrain[currentMouseMapVertical+currentMapVertical][currentMouseMapHoriztonal+currentMapHoriztonal].getTerrainResources(),1180,60);
			g.drawString("Food: " + terrain[currentMouseMapVertical+currentMapVertical][currentMouseMapHoriztonal+currentMapHoriztonal].getFood(),1180,80);
			g.drawString("Production: " + terrain[currentMouseMapVertical+currentMapVertical][currentMouseMapHoriztonal+currentMapHoriztonal].getProduction(),1180,100);
			g.drawString("Health: " + terrain[currentMouseMapVertical+currentMapVertical][currentMouseMapHoriztonal+currentMapHoriztonal].getHealth(),1180,120);
			g.drawString("Science: " + terrain[currentMouseMapVertical+currentMapVertical][currentMouseMapHoriztonal+currentMapHoriztonal].getScience(),1180,140);
		}
			
		//unit info	
		g.draw3DRect(1175,160,200,100,true);
		
		if(unitFocusNumber > -1)
		{
			g.draw3DRect(1380,160,50,25,true);
			g.draw3DRect(1380,190,50,25,true);
			g.draw3DRect(1380,220,50,25,true);

			g.drawString(playerUnits.get(unitFocusNumber).getName(), 1180,180);
			g.drawString("Strength: " + playerUnits.get(unitFocusNumber).getStrength(), 1180,200);	
			g.drawString("Health: " + playerUnits.get(unitFocusNumber).getHealth(), 1180,220);	
			g.drawString("Movement: " + playerUnits.get(unitFocusNumber).getMovement(), 1180,240);	

			if(playerUnits.get(unitFocusNumber).getSpecialAbility() != "")
			{
				g.drawString(playerUnits.get(unitFocusNumber).getSpecialAbility(),1385,180);
			}		
		}

		//city info
		g.draw3DRect(1175,265,200,200,true);		

		if(cityFocusNumber > -1)
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

			g.drawString(playerCities.get(cityFocusNumber).getName(),1180,320);
			g.drawString("Population: " + playerCities.get(cityFocusNumber).getPopulation(),1180,340);
			g.drawString("Food: " + playerCities.get(cityFocusNumber).getFood() + "   Turns until growth: " + playerCities.get(cityFocusNumber).getTurnsUntilGrowth(),1180,360);
			g.drawString("Production: " + playerCities.get(cityFocusNumber).getProduction(),1180,380);
			g.drawString("Health: " + playerCities.get(cityFocusNumber).getHealth(),1180,400);	
		}

		//game info
		g.drawString("Turn: " + turnNumber,1450,50);
	}

	//paints mini map


	//other methods
	public void resetPlayerTurn()
	{
		for(int i = 0; i < playerUnits.size();i++)
		{
			playerUnits.get(i).setMovement(playerUnits.get(i).getMaxMovement());
		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub		
		
	}
	public void keyPressed(KeyEvent e)
	{
		if(mapView == true)
		{
			//moves the map around
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
			{
				if(currentMapHoriztonal - 1 >= 0)
				{
					currentMapHoriztonal--;

					//units
					for(int i = 0; i < playerUnits.size(); i++)
					{
						playerUnits.get(i).setX(playerUnits.get(i).getX() + 75);
					}
					//cities
					for(int i = 0; i < playerCities.size(); i++)
					{
						playerCities.get(i).setX(playerCities.get(i).getX() + 75);
					}	
				}					
			}			
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
			{
				if(currentMapHoriztonal + 15 < mapWidth)
				{
					currentMapHoriztonal++;

					for(int i = 0; i < playerUnits.size(); i++)
					{
						playerUnits.get(i).setX(playerUnits.get(i).getX() - 75);
					}
					//cities
					for(int i = 0; i < playerCities.size(); i++)
					{
						playerCities.get(i).setX(playerCities.get(i).getX() - 75);
					}
				}					
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
			{
				if(currentMapVertical - 1 > -1)
				{
					currentMapVertical--;

					for(int i = 0; i < playerUnits.size(); i++)
					{
						playerUnits.get(i).setY(playerUnits.get(i).getY() + 75);
					}
					//cities
					for(int i = 0; i < playerCities.size(); i++)
					{
						playerCities.get(i).setY(playerCities.get(i).getY() + 75);
					}
				}									
			}		
			else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
			{
				if(currentMapVertical + 10  < mapHeight)
				{
					for(int i = 0; i < playerUnits.size(); i++)
					{
						playerUnits.get(i).setY(playerUnits.get(i).getY() - 75);
					}
					currentMapVertical++;
					//cities
					for(int i = 0; i < playerCities.size(); i++)
					{
						playerCities.get(i).setY(playerCities.get(i).getY() - 75);
					}
				}										
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
			if(e.getX() > 250 && e.getX() < 350 && e.getY() > 75 & e.getY() < 125)
			{
				mapWidth = 25;
				mapHeight = 25;
				mapSizeOptions = false;
			}
			//medium map
			if(e.getX() > 250 && e.getX() < 350 && e.getY() > 126 & e.getY() < 175)
			{
				mapWidth = 50;
				mapHeight = 50;
				mapSizeOptions = false;
			}
			//large map
			if(e.getX() > 250 && e.getX() < 350 && e.getY() > 176 & e.getY() < 225)
			{
				mapWidth = 75;
				mapHeight = 75;
				mapSizeOptions = false;
			}
			//huge map
			if(e.getX() > 250 && e.getX() < 350 && e.getY() > 226 & e.getY() < 275)
			{
				mapWidth = 100;
				mapHeight = 100;
				mapSizeOptions = false;
			}
		}
		if(newGameSetup == true)
		{
			//map sizes menu
			if(e.getX() > 100 && e.getX() < 200 && e.getY() > 100 & e.getY() < 150)
			{
				mapSizeOptions = true;
			}
			//start game
			if(e.getX() > 200 && e.getX() < 300 && e.getY() > 725 & e.getY() < 775)
			{
				newGameSetup = false;
				mapView = true;			
			}
		}
		if(playerTurn == true)
		{
			//next turn
			if(e.getX() > 1450 && e.getX() < 1525 && e.getY() > 85 & e.getY() < 125)
			{
				playerTurn = false;
				unitFocusNumber = -1;
				for(int i = 0; i < playerCities.size(); i++)
				{
					playerCities.get(i).checkGrowth();
				}
				//aI.AITurn();
				resetPlayerTurn();
				playerTurn = true;
				turnNumber++;			
			}
			
			//special ability button
			if(mapView == true && unitFocusNumber > -1)
			{
				if(playerUnits.get(unitFocusNumber).getSpecialAbility() != "")
				{
					if(e.getX() > 1380 && e.getX() < 1430 && e.getY() > 185 & e.getY() < 210)
					{
						playerUnits.get(unitFocusNumber).setSpecialAbilityActivate(true);
					}
				}
			}
			//sets a city to be focused
			for(int i = 0; i < playerCities.size(); i++)
			{
				int unitX = (int)Math.floor((playerCities.get(i).getX()/75));
				int unitY = (int)Math.floor((playerCities.get(i).getY()/75));

				if(unitX == currentMouseMapHoriztonal && unitY == currentMouseMapVertical)
				{
					playerCities.get(i).setFocus(true);
					unitFocusNumber = -1;
					cityFocusNumber = i;
					break;
				}									
			}
			//city focuses
			if(cityFocusNumber > -1)
			{
				//production focus
				if(e.getX() > 1380 && e.getX() < 1490 && e.getY() > 295 & e.getY() < 320)
				{
					playerCities.get(cityFocusNumber).setProductionFocus(true);
					playerCities.get(cityFocusNumber).setTileYields();
				}
				//food focus
				if(e.getX() > 1380 && e.getX() < 1490 && e.getY() > 325 & e.getY() < 350)
				{
					playerCities.get(cityFocusNumber).setFoodFocus(true);
					playerCities.get(cityFocusNumber).setTileYields();
				}
				//Health focus
				if(e.getX() > 1380 && e.getX() < 1490 && e.getY() > 355 & e.getY() < 380)
				{
					playerCities.get(cityFocusNumber).setHealthFocus(true);
					playerCities.get(cityFocusNumber).setTileYields();
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
			for(int i = 0; i < playerUnits.size(); i++)
			{
				int unitX = (int)Math.floor((playerUnits.get(i).getX()/75));
				int unitY = (int)Math.floor((playerUnits.get(i).getY()/75));

				if(unitX == currentMouseMapHoriztonal && unitY == currentMouseMapVertical)
				{
					playerUnits.get(i).setFocus(true);
					cityFocusNumber = -1;
					unitFocusNumber = i;
					break;
				}					
			}		
		}		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
		if(mapView == true && (e.getX() > 0 && e.getX() < 1125 && e.getY() > 0 & e.getY() < 750) && unitFocusNumber > -1)
		{
			int x = e.getX()-3; //takes into account the frame
			int y = e.getY()-25; //takes in to account the frame

			//establishes which array of the terrain the user has clicked
			int newX = (int)Math.floor((x/75));
			int newY= (int)Math.floor((y/75));
		
			//moves selected unit
			if(playerUnits.get(unitFocusNumber).getFocus() == true)
			{				
				playerUnits.get(unitFocusNumber).moveUnit(newX,newY);
				playerUnits.get(unitFocusNumber).setFocus(false);
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
		if(playerTurn == true)
		{
			//in terrain view
			if(mapView == true && (e.getX() > 0 && e.getX() < 1125 && e.getY() > 0 & e.getY() < 750))
			{
				int tempx = e.getX()-3; //takes into account the frame
				int tempy = e.getY()-25; //takes in to account the frame

				int x = (int)Math.floor((tempx/75));
				int y = (int)Math.floor((tempy/75));

				//establishes which array of the terrain the user has clicked
				if(terrain[y + currentMapVertical][x + currentMapHoriztonal].getVisability() == true)
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
