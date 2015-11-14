import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;


public class BddTools {
	private Connection con=null;
	
	private String url;
	private String nom;
	private String mdp;
	private String driver;
	
	
	public BddTools(String base) throws IOException, ClassNotFoundException, SQLException {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("properties.txt");
		prop.load(input);
		input.close();
		url = prop.getProperty("url");
		nom = prop.getProperty("nom");
		mdp = prop.getProperty("mdp");
		driver = prop.getProperty("driver");
		Class.forName(driver);
		connecter("");
	}
	
	public void connecter(String base) throws SQLException {
		con = DriverManager.getConnection(url,nom,mdp);
		System.out.println("Connexion établie avec succés");
	}
	
	public int nbLines(String table) throws SQLException {
		Statement stmt = con.createStatement();
		String query = "SELECT * FROM " + table;
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		return rsmd.getColumnCount();
	}
	
	public List getRequest (String query) throws SQLException {
		List<List> lignes = new ArrayList<List>();
		List<Object> colonnes = new ArrayList<Object>();
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int nbColumn = rsmd.getColumnCount();
		//on récupère le nom des colonnes
		for (int i = 1; i <= nbColumn; i++) {
			colonnes.add(rsmd.getColumnName(i));
		}
		lignes.add(colonnes);
		//on récupère les données
		while (rs.next()) {
			colonnes = new ArrayList(); //On réinitialise la liste
			for (int i = 1; i <= nbColumn; i++) {
				colonnes.add(rs.getObject(i));
			}
			lignes.add(colonnes);
		}
		return lignes;
	}
	
	public String describe(String table) throws SQLException {
		Statement stmt = con.createStatement();
		String query = "SELECT * FROM " + table ;
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int nbColumn = rsmd.getColumnCount();
		String details = "Description de la table : " + table +"\n";

		for (int i = 1; i < nbColumn; i++) {
			details += "[" + rsmd.getColumnName(i) + " : " + rsmd.getColumnTypeName(i) + "] ";
		}
		details += "[" + rsmd.getColumnName(nbColumn) + " : " + rsmd.getColumnTypeName(nbColumn) + "]";
		return details;
	}
	
	public void fermer() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
