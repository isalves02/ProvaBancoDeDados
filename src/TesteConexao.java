import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("--- inicio do teste");
        String driver = "org.postgresql.Driver";
        String user   = "postgres";
        String senha = "postgres";
        String url      = "jdbc:postgresql://localhost:5432/Bibliotecweas";

        try {
            Class.forName(driver);
            Connection con = null;
            con = (Connection) DriverManager.getConnection(url, user, senha);
            System.out.println("Conex�o realizada com sucesso.");

        }catch(Exception e)
        {
            System.err.println(e);
            System.err.print(e.getMessage());

        }
    }
}
