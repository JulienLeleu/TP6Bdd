import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class testJdbc {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		
		Properties prop = new Properties();
		InputStream input = new FileInputStream("properties.txt");
		prop.load(input);
		input.close();
		
		String url = prop.getProperty("url");
		String nom = prop.getProperty("nom");
		String mdp = prop.getProperty("mdp");
		Connection con=null;
		
		//Enregistrement du driver
		Class.forName(prop.getProperty("driver"));
		
		//Connexion à la base
		con = DriverManager.getConnection(url,nom,mdp);
		System.out.println("Connexion établie avec succés");
		
		//Execution de la requete
		Statement stmt = con.createStatement();
		stmt.executeUpdate("DROP TABLE IF EXISTS user");
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user (idUser INTEGER PRIMARY KEY AUTOINCREMENT , nom TEXT, prenom TEXT, age INTEGER)");
		stmt.executeUpdate("DELETE FROM user");
		stmt.executeUpdate("INSERT INTO user (nom, prenom, age) VALUES('Leleu','Julien', '21')");
		stmt.executeUpdate("INSERT INTO user (nom, prenom, age) VALUES('Lefebvre','Mélanie', '20')");
		stmt.executeUpdate("INSERT INTO user (nom, prenom, age) VALUES('Cousyn','Charles', '20')");
		stmt.executeUpdate("INSERT INTO user (nom, prenom, age) VALUES('Herlem','Yann', '17')");
		con.close();
	}
}
