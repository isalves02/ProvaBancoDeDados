# Sistema de Gerenciamento de Estabelecimentos
Este sistema foi desenvolvido para a disiciplina de banco de dados do 2° semestre da Universidade Católica do Salvador, com a proposta de gerenciar informações sobre estabelecimentos, como nome, endereço, tipo e outras características, com integração com banco de dados PostgreSQL. 
O sistema oferece uma interface no terminal para adicionar, atualizar e excluir dados dos estabelecimentos registrados, além de permitir a manipulação das permissões de usuários.

## Funcionalidades principais
- Cadastro de Estabelecimentos: Permite adicionar novos estabelecimentos ao banco de dados, fornecendo informações como nome, endereço, Instagram e tipo de estabelecimento (cafeteria, restaurante, coworking gratuito, etc.);
- Atualização de Estabelecimentos: Facilita a atualização de dados de estabelecimentos, permitindo modificar informações como nome, endereço e tipo. Caso algum dado não seja fornecido, o sistema mantém o valor previamente cadastrado no banco de dados;
- Exclusão de Estabelecimentos: Oferece a funcionalidade de deletar um estabelecimento da base de dados com base no seu ID;
- Conexão com PostgreSQL: O sistema utiliza JDBC para se conectar a um banco de dados PostgreSQL, realizando operações de leitura e escrita de dados;
- Também é utilizado uso de function para retornar a quantidade de estabelecimentos cadastrados.

# Tecnologias utilizadas
Java: Linguagem principal utilizada para o desenvolvimento do sistema;
PostgreSQL: Banco de dados utilizado para armazenar as informações dos estabelecimentos;
JDBC: Biblioteca utilizada para comunicação entre o Java e o banco de dados;
PreparedStatement: Para evitar SQL injection e facilitar a manipulação dos dados no banco de dados.
