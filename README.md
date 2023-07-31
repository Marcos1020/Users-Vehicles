  # Users-Vehicles

![Users-vehicles](https://github.com/Marcos1020/Users-Vehicles/assets/83420181/5fe3a17c-784d-4b34-b95b-bba6f0f66ebe)

APi criada para simulação de um sistema de busca de veiculos cadastrados em uma base de dados, utilizndo a modelagem de APIs Rest e seus conceitos.

utilizando o padrão de projetos MVC, conceitos de clean code. e as mais variadas ferramentas para o desenvolvimento e a melhor performance dentr elas 

  *JAVA
  
  *SPRING 
  
  *Spring cloud
  
  *Spring Security
  
  *docker-compose 
  
  *Swagger-documentation
  
  *Rest-Assured
  
  *JUnit

![Diagrama](https://github.com/Marcos1020/Users-Vehicles/assets/83420181/94d1f4d5-6bbf-4ddf-85b1-4e7b5656ecf1)

  # instruções basicas de funcionamento

Para rodar o projeto é necessario ter o docker instalado e o docker-compose configurado. E uma interface para o banco de dados por exemplo o DBeaver.

tendo essas configurações funcionando, será necessario ir no terminal abrir a pasta do projeto e abrir a pasta Docker e digitar o comando "docker-compose up -d"
para gerar uma imagem do banco de dados no caso o MySQL, feito isso rodar o projeto na classe Application. e utilizar o Swagger para realizar os testes das rotas criadas

  # DETALHE IMPORTANTE

Será necessario baixar a API sendEmail, pois tem uma integração que é feita com ela que quando gera um novo cadastro, um email é enviado ao e-mail informado no cadastro de um novo usuario e veiculo.

  # SWAGGER

http://localhost:9060/swagger-ui/index.html#/user-controller
