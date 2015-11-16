import javax.swing.ImageIcon;

public class Settler extends Unit
{
	//variables
	
	//user class obejects 	

	public Settler(int spawnX, int spawnY)
	{
		unitName = "Settler";
		specialAbility = "Settle";
		unitImage = new ImageIcon(getClass().getResource("/Images/Units/settler.png"));
		x = spawnX;
		y = spawnY;	
		movement = 2;
		maxMovement = 2;
		strength = 0;
		health = 100;	
	}
	//setters
	
	//getters

	
}