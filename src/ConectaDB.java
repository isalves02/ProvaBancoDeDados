import javax.swing.*;
import java.sql.*;

public class ConectaDB {
    private Connection con = null;
    private final String endereco;
    private final String usuario;
    private final String senha;

    public ConectaDB(){
        this.senha = "postgres";
        this.usuario = "postgres";
        this.endereco = "jdbc:postgresql://localhost:5432/remotando";
    }

    public void Conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(endereco, usuario, senha);
            JOptionPane.showMessageDialog(null, "Banco conectado com sucesso");
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar o driver");

        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "Erro na query");
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void Desconectar() {
        try {
            con.close();
            JOptionPane.showMessageDialog(null, "Desconectado com sucesso!");
        } catch (SQLException onConClose) {
            JOptionPane.showMessageDialog(null, "Erro ao desconectar o banco");
        }
    }
}