package rob;
import java.awt.Color;
public enum NatureTerrain {
	EAU(Color.BLUE),
	FORET(Color.GREEN),
	ROCHE(Color.gray),
	TERRAIN_LIBRE(Color.BLACK),
	HABITAT(Color.YELLOW);
	private Color color;
	NatureTerrain(Color yellow) {
		// TODO Auto-generated constructor stub
		this.color = yellow;
	}
	public Color getColor() {
		return color;	
	}

}
