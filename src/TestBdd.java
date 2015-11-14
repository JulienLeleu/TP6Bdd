import java.io.IOException;
import java.sql.SQLException;


public class TestBdd {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		BddTools bdd = new BddTools("mabase");
		System.out.println(bdd.nbLines("user"));
		System.out.println(bdd.describe("user"));
		System.out.println(bdd.getRequest("select * from user"));
		bdd.fermer();
	}
}