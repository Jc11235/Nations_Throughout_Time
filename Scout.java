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
		nonAccessibleTerrainFeatures.add("Glaciers");
		nonAccessibleTerrainFeatures.add("Icebergs");

		unitImage = new ImageIcon(getClass().getResource("/Images/Units/scout.png"));

		x = spawnX;
		y = spawnY;	
		movement = 4;
		maxMovement = 4;
		strength = 10;
		health = 100;	
		productionCost = 10;
	}
	//setters
	
	//getters

	
}