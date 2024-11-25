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

        usuario.userLogin();

        System.out.println("######### REMOTANDO ############");
        do {
            if (usuario.getUserLoginStatus()) {
                System.out.println("ESCOLHA UMA OPÇÃO");

                if (usuario.getUserPermission() == 2) {
                    System.out.println("""
                        1 - Listar lugares         2 - Adicionar lugar
                        3 - Alterar lugar          4 - Quantidade total de lugares cadastrados
                        5 - Remover lugar          6 - Ver usuários cadastrados
                        7 - Apagar usuário         8 - Sair da conta 
                        9 - Finalizar sistema
                    """);
                } else {
                    System.out.println("""
                        1 - Listar lugares         2 - Adicionar lugar
                        3 - Alterar lugar          4 - Quantidade total de lugares cadastrados
                        5 - Sair da conta          9 - Finalizar sistema
                    """);
                }
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
                        estabelecimento.getQuantityOfPlacesRegistered();
                        break;
                    case 5:
                        if (usuario.getUserPermission() == 2) {
                            handleUpdatePlaces(sc, estabelecimento);
                        } else {
                            usuario.setUserLoginStatus(false);
                            System.out.println("Logout realizado!");
                        }
                        break;

                    case 6:
                        if (usuario.getUserPermission() == 2) {
                            usuario.getUserInformation();
                        }
                        break;

                    case 7:
                        handleRemoveUser(sc, usuario);
                        break;

                    case 8:
                        usuario.setUserLoginStatus(false);
                        System.out.println("Logout realizado!");
                        break;
                    default:
                        System.out.println("Sistema finalizado!");
                        banco.Desconectar();
                        return;
                }
            } else {
                System.out.println("Digite uma opção");
                System.out.println("1 - Efetuar login \t\t 2- Criar conta \t\t 9 - Finalizar sistema.");
                op = sc.nextInt();

                switch (op) {
                    case 1:
                        handleUserLogin(sc, usuario);
                        break;
                    case 2:
                        handleRegisterUser(sc, usuario);
                        break;
                    default:
                        banco.Desconectar();
                        System.out.println("Sistema finalizado!");
                        return;
                }
            }
        } while (true);
    }

    public static void handleUserLogin (Scanner sc, Usuario usuario) throws SQLException {
        System.out.println("EFETUAR LOGIN");
        sc.nextLine();

        System.out.print("Email: ");
        usuario.setUserMail(sc.nextLine());

        System.out.print("Senha: ");
        usuario.setUserPassword(sc.nextLine());
        usuario.userLogin();
    }

    public static void handleRemoveUser(Scanner sc, Usuario usuario) throws SQLException {
        System.out.println("------------- REMOVER LUGAR -------------");
        usuario.getUserInformation();
        System.out.println("Digite o ID do usuário que você deseja remover: ");
        sc.nextLine();
        usuario.setUserId(sc.nextInt());
        usuario.setDeleteUser();
    }

    public static void handleRegisterUser (Scanner sc, Usuario usuario) throws SQLException {
        System.out.println("CRIAR CONTA");
        sc.nextLine();

        System.out.print("Email: ");
        usuario.setUserMail(sc.nextLine());

        System.out.print("Senha: ");
        usuario.setUserPassword(sc.nextLine());

        usuario.setUserPermission(1);
        usuario.setUser();
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