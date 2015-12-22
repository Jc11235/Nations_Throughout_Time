import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Settler extends Unit
{
	//variables
	
	//user class obejects 	

	public Settler(int spawnX, int spawnY)
	{
		unitName = "Settler";
		specialAbility = "Settle";

		nonAccessibleTerrainType = new ArrayList<String>();
		nonAccessibleTerrainFeatures = new ArrayList<String>();

		nonAccessibleTerrainType.add("Water");
		nonAccessibleTerrainType.add("Coast");
		nonAccessibleTerrainFeatures.add("Glaciers");
		nonAccessibleTerrainFeatures.add("Icebergs");

		unitImage = new ImageIcon(getClass().getResource("/Images/Units/settler.png"));

		x = spawnX;
		y = spawnY;	
		locationX = x;
		locationY = y;
		movement = 4;
		maxMovement = 4;
		strength = 0;
		health = 100;	
		productionCost = 20;
	}
	//setters
	
	//getters

	
}