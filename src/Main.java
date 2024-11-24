import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        int op;
        Scanner sc = new Scanner(System.in);
        Usuario usuario = new Usuario();
        Estabelecimento estabelecimento = new Estabelecimento();
        ConectaDB banco = new ConectaDB();

        banco.Conectar();
        usuario.setConn(banco.getConnection());
        estabelecimento.setConn(banco.getConnection());

       do {
            System.out.println("######### REMOTANDO ############");
            System.out.println("Digite uma opção");
            System.out.println("1 - Listar lugares \t\t 2- Adicionar lugar \n3 - Remover lugar \t\t 4 - Alterar lugar \n5- Quantidade total de lugares cadastrados \t\t 9 - Sair");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    estabelecimento.getPlacesInformation();
                    break;
                case 2:
                    handleAddPlaces(sc, estabelecimento);
                    break;
                case 3:
                    handleRemovePlaces(sc, estabelecimento);
                    break;

                case 4:
                    handleUpdatePlaces(sc, estabelecimento);
                    break;

                case 5:
                    estabelecimento.getQuantityOfPlacesRegistered();
                    break;

                default:
                    banco.Desconectar();
                    System.out.println("Sistema finalizado!");
                    return;
            }
        } while (op!=9);
    }

    public static void handleAddPlaces(Scanner sc, Estabelecimento estabelecimento) throws SQLException {
        System.out.println("------------- ADICIONAR LUGAR -------------");
        registerPlaceForm(sc, estabelecimento);
        estabelecimento.setNewPlace();
    }

    public static void handleRemovePlaces(Scanner sc, Estabelecimento estabelecimento) throws SQLException {
        System.out.println("------------- REMOVER LUGAR -------------");
        estabelecimento.getPlacesInformation();
        System.out.println("Digite o ID do lugar que você quer remover: ");
        sc.nextLine();
        estabelecimento.setPlaceId(sc.nextInt());

        estabelecimento.setDeletePlace();
    }

    public static void handleUpdatePlaces(Scanner sc, Estabelecimento estabelecimento) throws SQLException {
        System.out.println("------------- ATUALIZAR LUGAR -------------");
        estabelecimento.getPlacesInformation();
        System.out.println("Digite o ID do lugar que você quer atualizar: ");
        sc.nextLine();
        estabelecimento.setPlaceId(sc.nextInt());
        registerPlaceForm(sc, estabelecimento);

        estabelecimento.setUpdatePlace();
    }

    public static void registerPlaceForm(Scanner sc, Estabelecimento estabelecimento) {
        System.out.println("Digite o nome: ");
        sc.nextLine();
        estabelecimento.setPlaceName(sc.nextLine());

        System.out.println("Digite o instagram: ");
        estabelecimento.setPlaceInstagram(sc.nextLine());

        System.out.println("Digite o endereço: ");
        estabelecimento.setPlaceAddress(sc.nextLine());

        System.out.println("Selecione o tipo de estabelecimento: \n1- Cafeteria \t 2-Restaurante \t 3- Coworking gratuito");
        estabelecimento.setPlaceType(sc.nextInt());
    }
}