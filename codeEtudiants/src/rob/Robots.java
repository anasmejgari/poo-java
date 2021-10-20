package rob;

public enum Robots {

	DRONE("class rob.Drone", "Drone.png"), CHENILLE("class rob.RobChenille", "Chenille.png"),
	PATTES("class rob.RobPattes", "Pattes.png"), ROUE("class rob.RobRoue", "Roue.png");
	
	private String className;
	private String nomFichier;
	
	private Robots(String classe, String fichier) {
		className = classe;
		nomFichier = fichier;
	}
	
	public static String getFichier (String classe) {
		String retour = null;
		Robots[] values = Robots.values();
		for (Robots robot: values) {
			if (robot.className.equals(classe)) {
				retour = robot.nomFichier;
			}
		}
		return retour;
	}
}
