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
		setSize(1600, 800);
		

		//creates a new menubar
		menuBar = new JMenuBar();

		//craetes a new file menu
		fileMenu = new JMenu("File");

		//creates the file options
		save = new JMenuItem("Save");
		save.addActionListener(this);
		load = new JMenuItem("Load");
		load.addActionListener(this);
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(this);
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);

		//adds file options to the file menu
		fileMenu.add(save);
		fileMenu.add(load);
		fileMenu.add(newGame);
		fileMenu.add(exit);

		//adds the file menu to the menubar
		menuBar.add(fileMenu);

		//establishes the menu panel as a new JPanel
		menuPanel = new JPanel();

		//adds the menu to the menubar
		menuPanel.add(menuBar);

		//sets user class objects
		humanPlayer = new Player();
		aIPlayer = new AI();
		fileActions = new FileActions();

		//panels
		gamePanel = new GamePanel();
		gamePanel.setSize(750, 750);
		gamePanel.setFocusable(true);
		addKeyListener(gamePanel);
		addMouseListener(gamePanel);
		addMouseMotionListener(gamePanel);
		setVisible(true);

		//adds panels to the frame with layout
		add(menuPanel, BorderLayout.EAST);
		add(gamePanel, BorderLayout.CENTER);	

		//add file choosers
		chooser = new JFileChooser();
		
		//adds file objects
		file = new File("");		

		//timers
		t = new Timer(1, this);
		t.start();	
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		//save a game
		if(e.getSource() == save)
		{
			//fCounter = chooser.showSaveDialog(this);
			//if(fCounter == JFileChooser.APPROVE_OPTION)
			//{
				//file = chooser.getSelectedFile();	
				fileActions.addObject(gamePanel);		
				fileActions.saveGame();
			//}
		}
		//load a save file
		if(e.getSource() == load)
		{
			this.remove(gamePanel);
			gamePanel = null;

			gamePanel = fileActions.loadGame();

			this.add(gamePanel);
			gamePanel.setFocusable(true);
			addKeyListener(gamePanel);
			addMouseListener(gamePanel);
			addMouseMotionListener(gamePanel);
			add(gamePanel, BorderLayout.CENTER);
			//setVisible(true);
		}
		//starts a new game
		if(e.getSource() == newGame)
		{
			//gamePanel.setNewGameSetup(true);
			gamePanel.setNewGameSetup(true);
			gamePanel.setMainMenuView(false);

		}
		//exits the game
		if(e.getSource() == exit)
		{
			System.exit(0);
		}
		gamePanel.repaint();		
	}
}
