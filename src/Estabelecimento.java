import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Estabelecimento {
    private int id;
    private String nome;
    private String instagram;
    private String endereco;
    private int id_tipo_estabelecimento;
    private Connection conn;

    public Estabelecimento() {
        this.id = 0;
        this.nome = "";
        this.instagram = "";
        this.endereco = "";
        this.id_tipo_estabelecimento = 0;
        this.conn = null;
    }

    public void setConn(Connection connection) {
        this.conn = connection;
    }

    public void setPlaceId(int id) {
        this.id = id;
    }

    public void setPlaceName(String name) {
        this.nome = name;
    }

    public void setPlaceInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setPlaceAddress(String address) {
        this.endereco = address;
    }

    public void setPlaceType(int type) {
        this.id_tipo_estabelecimento = type;
    }

    public void setUpdatePlace() throws SQLException {
        try {
            String sql = "SELECT * FROM estabelecimento WHERE id_estabelecimento = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();

            String oldName = null;
            String oldIG = null;
            String oldAddress = null;
            int oldType = 0;

            if (rs.next()) {
                oldName = rs.getString("nome");
                oldIG = rs.getString("instagram");
                oldAddress = rs.getString("endereco");
                oldType = rs.getInt("id_tipo_estabelecimento");
            } else {
                System.out.println("Não há nenhum dado cadastrado para o ID fornecido!");
                return;
            }

            String updateSql = "UPDATE estabelecimento " +
                    "SET nome = ?, instagram = ?, endereco = ?, id_tipo_estabelecimento = ? " +
                    "WHERE id_estabelecimento = ?;";
            PreparedStatement pstmtUpdate = conn.prepareStatement(updateSql);

            pstmtUpdate.setString(1, this.nome != null ? this.nome : oldName);
            pstmtUpdate.setString(2, this.instagram != null ? this.instagram : oldIG);
            pstmtUpdate.setString(3, this.endereco != null ? this.endereco : oldAddress);
            pstmtUpdate.setInt(4, this.id_tipo_estabelecimento != 0 ? this.id_tipo_estabelecimento : oldType);
            pstmtUpdate.setInt(5, this.id);

            int rowsAffected = pstmtUpdate.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Estabelecimento atualizado com sucesso!");
            } else {
                System.out.println("A atualização falhou! Nenhuma linha foi alterada.");
            }
        } catch (SQLException err) {
            System.err.println("Erro ao atualizar o estabelecimento: " + err.getMessage());
        }
    }

    public void setDeletePlace() throws SQLException {
        try {
            String sql ="DELETE FROM estabelecimento WHERE id_estabelecimento = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, this.id);
            pstmt.executeUpdate();

            System.out.println("Lugar deletado com sucesso!");

        } catch (SQLException err) {
            System.err.println(err);
        }
    }

    public void setNewPlace() throws SQLException {
        try {
            String sql ="INSERT INTO estabelecimento(nome, instagram, endereco, id_tipo_estabelecimento)\n" +
                    "VALUES(?, ?, ?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.nome);
            pstmt.setString(2, this.instagram);
            pstmt.setString(3, this.endereco);
            pstmt.setInt(4, this.id_tipo_estabelecimento);
            pstmt.executeUpdate();

            System.out.println("Lugar registrado com sucesso!");

        } catch (SQLException err) {
            System.err.println(err);
        }
    }

    public void getPlacesInformation() {
        try {
            String sql ="SELECT id_estabelecimento, nome, instagram, endereco, tipo_estabelecimento\n" +
                        "FROM estabelecimento e\n" +
                        "INNER JOIN tipo_estabelecimento tipo\n" +
                        "ON e.id_tipo_estabelecimento = tipo.id_tipo_estabelecimento;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.isBeforeFirst()) {
                System.out.println("LISTA DE LUGARES");
                System.out.printf("%-10s | %-25s | %-25s | %-25s | %-25s%n", "ID", "Nome", "Instagram", "Endereço", "Tipo");

                while (rs.next()) {
                    int id = rs.getInt("id_estabelecimento");
                    String name = rs.getString("nome");
                    String instagram = rs.getString("instagram");
                    String address = rs.getString("endereco");
                    String type = rs.getString("tipo_estabelecimento");

                    System.out.printf("%-10d | %-25s | %-25s | %-25s | %-25s%n", id, name, instagram, address, type);
                }
            } else {
                System.out.println("Não há nenhum dado cadastrado ainda!");
            }

        } catch (SQLException err) {
            System.err.println(err);
        }
    }

    public void getQuantityOfPlacesRegistered() {
        int quantidade = 0;
        try {
            String sql = "SELECT quantidade_estabelecimentos();";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                quantidade = rs.getInt(1);
            }
        } catch (SQLException err) {
            System.err.println(err);
        }

        System.out.printf("Há %d lugar(es) cadastrado(s) %n", quantidade);
    }
}
