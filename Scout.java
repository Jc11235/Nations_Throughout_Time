import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Scout extends Unit
{
	//variables
	
	//user class obejects 	

	//constructor
	public Scout(int spawnX, int spawnY)
	{
		unitName = "Scout";
		specialAbility = "";

		nonAccessibleTerrainType = new ArrayList<String>();
		nonAccessibleTerrainFeatures = new ArrayList<String>();

		nonAccessibleTerrainType.add("Water");
		nonAccessibleTerrainType.add("Coast");
		nonAccessibleTerrainFeatures.add("Glaciers");
		nonAccessibleTerrainFeatures.add("Icebergs");

		unitImage = new ImageIcon(getClass().getResource("/Images/Units/scout.png"));

		x = spawnX;
		y = spawnY;	
		locationX = x;
		locationY = y;
		movement = 6;
		maxMovement = 6;
		strength = 2;
		health = 100;	
		productionCost = 15;
	}
	//setters
	
	//getters

	
}