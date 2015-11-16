import javax.swing.ImageIcon;

public class Scout extends Unit
{
	//variables
	
	//user class obejects 	

	//constructor
	public Scout(int spawnX, int spawnY)
	{
		unitName = "Scout";
		specialAbility = "";
		unitImage = new ImageIcon(getClass().getResource("/Images/Units/scout.png"));
		x = spawnX;
		y = spawnY;	
		movement = 4;
		maxMovement = 4;
		strength = 10;
		health = 100;	
	}
	//setters
	
	//getters

	
}