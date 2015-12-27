import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import java.io.File;
import java.awt.*;  
import javax.swing.*;  
import javax.swing.Timer;
import java.io.Serializable;

public class GameWindow extends JFrame implements ActionListener
{
	//ints
	private int fCounter = 0;	

	//timers
	private Timer t;

	//Panels
	private GamePanel gamePanel;

	//menu bars
	private JMenuBar menuBar;

	//menus
	private JMenu fileMenu;

	//menu items
	private JMenuItem save;	
	private JMenuItem load;	
	private JMenuItem exit;	
	private JMenuItem newGame;

	//menu panels
	private JPanel menuPanel;

	//File objects	
	private File file;

	//File Chooser
	private JFileChooser chooser;

	//user objects
	private Player humanPlayer;
	private AI aIPlayer;
	private FileActions fileActions;
	//**************************************************************************constructor	
	public GameWindow()
	{
		//set specifications for frame
		super("New Game v 0.01a: pre-Alpha");
		this.setResizable(false);
		setFocusable(true);		
		setSize(1600, 900);
		

		//sets user class objects
		humanPlayer = new Player();
		aIPlayer = new AI();
		fileActions = new FileActions();

		//panels
		gamePanel = new GamePanel(this);
		gamePanel.setSize(1600, 900);
		gamePanel.setFocusable(true);
		addKeyListener(gamePanel);
		addMouseListener(gamePanel);
		addMouseMotionListener(gamePanel);
		setVisible(true);

		//adds panels to the frame with layout
		add(gamePanel);	

		//add file choosers
		chooser = new JFileChooser();
		
		//adds file objects
		file = new File("");		

		//timers
		t = new Timer(1, this);
		t.start();	
	}
	//methods
	//save files
	public void saveGame()
	{
		fileActions.addObject(gamePanel);		
		fileActions.saveGame();
	}
	//loads files
	public void loadGame()
	{
		this.remove(gamePanel);
		gamePanel = null;

		gamePanel = fileActions.loadGame();

		this.add(gamePanel);
		gamePanel.setFocusable(true);
		addKeyListener(gamePanel);
		addMouseListener(gamePanel);
		addMouseMotionListener(gamePanel);
		add(gamePanel);
	}
	//returns to main menu
	public void returnMainMenu()
	{
		this.remove(gamePanel);
		gamePanel = null;

		gamePanel = new GamePanel(this);
		this.add(gamePanel);
		gamePanel.setFocusable(true);
		addKeyListener(gamePanel);
		addMouseListener(gamePanel);
		addMouseMotionListener(gamePanel);
		add(gamePanel);
	}
	//new game
	public void newGame()
	{
		gamePanel.setNewGameSetup(true);
		gamePanel.setMainMenuView(false);
	}
	//exit game
	public void exitGame()
	{
		System.exit(0);
	}

	//listeners
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub	
		
		gamePanel.repaint();		
	}	
}
