import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private String email;
    private String senha;
    private int permissao;
    private Connection conn;

    public Usuario() {
        this.email = "";
        this.senha = "";
        this.permissao = 0;
        this.conn = null;
    }

    public void setConn(Connection connection) {
        this.conn = connection;
    }

    public String getUserMail() {
        return email;
    }

    public void setUserMail(String mail) {
        this.email = mail;
    }

    public void setUserPassword(String pass) {
        this.senha = pass;
    }

    public int getUserPermission() {
        return permissao;
    }

    public void setUserPermission(int permission) {
        this.permissao = permission;
    }

    public void setUser() throws SQLException {
        try {
            String sql = "INSERT INTO usuario(email, senha, id_permissao) VALUES (?,?,?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.email);
            pstmt.setString(2, this.senha);
            pstmt.setInt(3, this.permissao);
            pstmt.executeUpdate();

        } catch (SQLException err) {
            System.err.println(err);
        }
    }

    public void getUserInformation() throws SQLException {
        try {
            String sql = "SELECT email, tipo FROM usuario u INNER JOIN tipo_permissao permissao ON u.id_permissao = permissao.id_permissao;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.isBeforeFirst()) {
                System.out.println("----------------------------------------------");
                System.out.println("Permissão dos usuários");
                System.out.println("----------------------------------------------");
                while (rs.next()) {
                    String mail = rs.getString("email");
                    String permission = rs.getString("tipo");
                    System.out.printf("E-mail: %s \t|\t Tipo de permissão: %s", mail, permission);
                }
            } else {
                System.out.println("Não há nenhum dado cadastrado ainda!");
            }

        } catch (SQLException err) {
            System.err.println(err);
        }
    }
}
