import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;

public class FileActions implements Serializable
{
	private ArrayList<GamePanel> objects = new ArrayList<GamePanel>();

	//setters
	public void addObject(GamePanel gP)
	{
		objects.add(gP);
	}

	//getters
	
	public void saveGame()
	{
		try
		{  // Catch errors in I/O if necessary.
			// Open a file to write to, named SavedObj.sav.
			FileOutputStream saveFile=new FileOutputStream("NTT.sav");

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			// Now we do the save.
			for(int i = 0; i < objects.size();i++)
			{
				save.writeObject(objects.get(i));
				System.out.println(objects.get(i).getCurrentMapHorizontal());

			}
			// Close the file.
			save.close(); // This also closes saveFile.
		}
		catch(Exception exc)
		{
			exc.printStackTrace(); // If there was an error, print the info.
			
		}		
	}
	public GamePanel loadGame()
	{
		 GamePanel stuff = null;
		try
		{
			// Open file to read from, named SavedObj.sav.
			FileInputStream saveFile = new FileInputStream("NTT.sav");

			// Create an ObjectInputStream to get objects from save file.
			ObjectInputStream save = new ObjectInputStream(saveFile);

			// Now we do the restore.
			// readObject() returns a generic Object, we cast those back
			// into their original class type.
			// For primitive types, use the corresponding reference class.
	
			 stuff = (GamePanel) save.readObject();
			 System.out.println(stuff.getCurrentMapHorizontal());

			// Close the file.
			save.close(); // This also closes saveFile.

		}
		catch(Exception exc)
		{
			exc.printStackTrace(); // If there was an error, print the info.
		}

		return stuff;		
	}
}
