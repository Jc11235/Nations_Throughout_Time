import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Worker extends Unit
{
	//variables
	
	//user class obejects 	

	public Worker(int spawnX, int spawnY)
	{
		unitName = "Worker";
		specialAbility = "Build Improvement";

		nonAccessibleTerrainType = new ArrayList<String>();
		nonAccessibleTerrainFeatures = new ArrayList<String>();

		nonAccessibleTerrainType.add("Water");
		nonAccessibleTerrainFeatures.add("Glaciers");
		nonAccessibleTerrainFeatures.add("Icebergs");

		unitImage = new ImageIcon(getClass().getResource("/Images/Units/worker.png"));

		x = spawnX;
		y = spawnY;	
		movement = 4;
		maxMovement = 4;
		strength = 1;
		health = 100;	
		productionCost = 10;
	}
	//setters
	
	//getters

	
}