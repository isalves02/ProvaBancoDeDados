import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private int id;
    private String email;
    private String senha;
    private int permissao;
    private Connection conn;
    private boolean hasUserLoggedIn;

    public Usuario() {
        this.id = 0;
        this.email = "";
        this.senha = "";
        this.permissao = 0;
        this.conn = null;
        this.hasUserLoggedIn = false;
    }

    public void setConn(Connection connection) {
        this.conn = connection;
    }

    public void setUserId(int id) {
        this.id = id;
    }

    public boolean getUserLoginStatus() {
        return hasUserLoggedIn;
    }

    public void setUserLoginStatus(boolean status) {
        this.hasUserLoggedIn = status;
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

    public void setDeleteUser() throws SQLException {
        try {
            String sql = "CALL remover_usuario(?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, this.id);

            pstmt.executeUpdate();
            System.out.println("Usuário excluído com sucesso");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }

    public void getUserInformation() throws SQLException {
        try {
            String sql = "SELECT * FROM vw_listar_usuarios;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.isBeforeFirst()) {
                System.out.println("LISTA DE USUÁRIOS");
                while (rs.next()) {
                    String id = rs.getString("id_usuario");
                    String mail = rs.getString("email");
                    String permission = rs.getString("tipo");
                    System.out.printf("ID: %-10s\t|\t E-mail: %-30s \t|\t Tipo de permissão: %-20s%n", id, mail, permission);
                }
            } else {
                System.out.println("Não há nenhum dado cadastrado ainda!");
            }

        } catch (SQLException err) {
            System.err.println(err);
        }
    }

    public void userLogin() throws SQLException {
        try {
            String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.email);
            pstmt.setString(2, this.senha);

            ResultSet rs = pstmt.executeQuery();
            String mail = "";
            String password = "";
            int permission = 0;

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    mail = rs.getString("email");
                    password = rs.getString("senha");
                    permission = rs.getInt("id_permissao");
                }

                if (this.email.equals(mail) && this.senha.equals(password)) {
                    this.permissao = permission;
                    this.hasUserLoggedIn = true;
                    System.out.println("Login efetuado com sucesso!");
                } else {
                    System.out.println("TENTE NOVAMENTE");
                }
            } else {
                if (!this.email.isEmpty() && !this.senha.isEmpty()) {
                    System.out.println("Credenciais inválidas. Verifique suas credenciais ou CRIE SUA CONTA.");
                }
            }
        } catch (SQLException err) {
           System.err.println(err);
        }
    }
}
