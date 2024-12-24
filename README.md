# Spring Security JWT Authentication API

Este repositório contém uma implementação de uma API de autenticação usando **JSON Web Tokens (JWT)** para autenticação e autorização. O JWT é assinado com o algoritmo **RS256** (RSA), que utiliza um par de chaves públicas e privadas. Esta API é construída usando o framework **Spring Boot** e **Spring Security**.

## Tecnologias Usadas

- **Java** (versão 11 ou superior)
- **Spring Boot** (versão 2.x)
- **Spring Security**
- **JWT** (JSON Web Tokens)
- **RSA** (para assinatura do JWT)
- **Maven** (para gerenciamento de dependências)

## Funcionalidades

- **Cadastro de usuários**: Usuários podem se cadastrar fornecendo um nome de usuário e senha.
- **Autenticação**: Usuários podem fazer login e obter um JWT que é usado para autenticar requisições subsequentes.
- **Autorização**: As rotas da API são protegidas, permitindo acesso apenas a usuários com a role apropriada.
- **Token Expiração**: O JWT tem um tempo de expiração configurado (10 horas por padrão).

## Estrutura do Projeto

A estrutura básica do projeto segue o padrão típico de uma aplicação Spring Boot:

src/ ├── main/ │ ├── java/ │ │ ├── com/ │ │ │ ├── achartechnologies/ │ │ │ │ ├── security_demo_api/ │ │ │ │ │ ├── config/ │ │ │ │ │ │ └── SecurityConfig.java │ │ │ │ │ ├── controller/ │ │ │ │ │ │ └── AuthController.java │ │ │ │ │ ├── model/ │ │ │ │ │ │ └── User.java │ │ │ │ │ ├── repository/ │ │ │ │ │ │ └── UserRepository.java │ │ │ │ │ ├── service/ │ │ │ │ │ │ ├── JwtService.java │ │ │ │ │ │ └── UserDetailsServiceImpl.java │ │ │ │ │ └── SecurityDemoApiApplication.java ├── resources/ │ ├── application.properties │ └── static/ └── pom.xml

markdown
Copy code

## Como Rodar o Projeto

### Requisitos

- **Java 11** ou superior.
- **Maven** (para gerenciar dependências e construir o projeto).
- **Chaves RSA** (privada e pública) para assinar e verificar os tokens.

### Passos para Rodar Localmente

1. **Clonar o repositório:**

   Primeiro, clone o repositório para o seu computador:

   ```bash
   git clone https://github.com/username/repository-name.git
   cd repository-name
Gerar as chaves RSA:

Se você ainda não tem um par de chaves RSA, gere-as usando o OpenSSL ou o keytool do Java. Aqui está um exemplo de como gerar as chaves usando OpenSSL:

bash
Copy code
# Gerar chave privada RSA (2048 bits)
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048

# Gerar chave pública RSA
openssl rsa -pubout -in private_key.pem -out public_key.pem
Configurar as chaves no projeto:

Coloque as chaves private_key.pem e public_key.pem em um diretório no seu projeto, por exemplo, src/main/resources/keys/. Depois, configure os caminhos para as chaves no arquivo JwtService.java:

java
Copy code
private final String privateKeyPath = "src/main/resources/keys/private_key.pem";
private final String publicKeyPath = "src/main/resources/keys/public_key.pem";
Instalar as dependências e rodar a aplicação:

Certifique-se de que o Maven esteja instalado e execute o seguinte comando para compilar e rodar a aplicação:

bash
Copy code
mvn spring-boot:run
A API será executada no localhost:8080 por padrão.

Endpoints da API
1. Cadastro de Usuário
   Endpoint: POST /api/auth/register

Body (JSON):

json
Copy code
{
"username": "nomeusuario",
"password": "senha"
}
Resposta (sucesso):

json
Copy code
{
"message": "User registered successfully"
}
2. Login de Usuário (Geração do Token)
   Endpoint: POST /api/auth/login

Body (JSON):

json
Copy code
{
"username": "nomeusuario",
"password": "senha"
}
Resposta (sucesso):

json
Copy code
{
"token": "jwt_token_aqui"
}
3. Página de Boas-Vindas para Administradores
   Endpoint: GET /api/admin/welcome

Headers: Authorization: Bearer <token>

Resposta (sucesso):

json
Copy code
{
"message": "Welcome Admin"
}
4. Página de Boas-Vindas para Usuários
   Endpoint: GET /api/user/welcome

Headers: Authorization: Bearer <token>

Resposta (sucesso):

json
Copy code
{
"message": "Welcome User"
}
Configuração de Segurança
O acesso aos endpoints é protegido por JWT. Para autenticação e autorização, você precisa incluir o token JWT no cabeçalho da requisição HTTP. O código da aplicação está configurado para garantir que apenas usuários com roles adequadas tenham acesso a certas rotas.

Endpoints públicos: /api/auth/** (registro e login).
Endpoints protegidos: /api/admin/** (somente usuários com a role ADMIN), /api/user/** (usuários com a role USER ou ADMIN).
Arquivos de Configuração
application.properties
O arquivo application.properties contém as configurações do Spring Boot, como a porta da aplicação, configuração do banco de dados, entre outras opções.

properties
Copy code
server.port=8080
jwt.secret.key=mySecretKey
jwt.token.expiration=3600000
Configuração de Segurança
A configuração de segurança (SecurityConfig.java) utiliza o filtro JwtAuthenticationFilter para interceptar requisições e verificar a validade do token JWT.

java
Copy code
@Override
public void configure(HttpSecurity http) throws Exception {
http.csrf().disable()
.authorizeRequests()
.antMatchers("/api/auth/**").permitAll()
.antMatchers("/api/admin/**").hasRole("ADMIN")
.antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
.anyRequest().authenticated()
.and()
.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
}
Testando com o Postman/Insomnia
Você pode usar o Postman ou Insomnia para testar a API. Para isso:

Registrar um usuário: Faça uma requisição POST para /api/auth/register.
Fazer login: Faça uma requisição POST para /api/auth/login para obter o JWT.
Acessar rotas protegidas: Use o token obtido para acessar as rotas protegidas (por exemplo, /api/admin/welcome).
Contribuindo
Sinta-se à vontade para contribuir com este projeto! Caso encontre algum problema ou tenha sugestões de melhorias, abra uma issue ou faça um pull request.

Licença
Este projeto está licenciado sob a MIT License.

perl
Copy code













