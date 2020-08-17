<h1 align="center">API REST Gestão de Patrimônio</h1>
<p align="center">
<img alt="GitHub language count" src="https://img.shields.io/github/languages/count/hernanbs/gestao-patrimonio-server?style=flat-square" />
<img alt="GitHub code size in bytes" src="https://img.shields.io/github/languages/code-size/hernanbs/gestao-patrimonio-server?color=%232ec73a&style=flat-square" />
<img alt="GitHub" src="https://img.shields.io/github/license/hernanbs/gestao-patrimonio-server?color=%236537f0&style=flat-square" />
</p>

### :computer: Introdução
  Aplicação Web Rest que disponibiliza acesso a dados de Marcas e Patrimônios por meio de requisições HTTP em um formato padronizado JSON.
  Projeto feito inteiramente com java utilizando banco de dados PostgreSQL.
  
### :rocket: Instalação
* Execute o projeto utilizando [Eclipse](https://www.eclipse.org/downloads/packages/release/2020-06/r/eclipse-ide-enterprise-java-developers).
* Utilize servidor web [TomCat 7](https://tomcat.apache.org/download-70.cgi) para execução.
* Instale banco de dados [PostgreSQL](https://www.postgresql.org/download/)

### HTTP Endpoints

|    | Verbos HTTP | URI                                                                          |
|----|:-----------:|------------------------------------------------------------------------------|
| 01 | GET         | http://localhost:8080/projeto-gestao-patrimonio/rest/marcas                  |
| 02 | GET         | http://localhost:8080/projeto-gestao-patrimonio/rest/marcas/{id}             |
| 03 | GET         | http://localhost:8080/projeto-gestao-patrimonio/rest/marcas/{id}/patrimonios |
| 04 | POST        | http://localhost:8080/projeto-gestao-patrimonio/rest/marcas                  |
| 05 | PUT         | http://localhost:8080/projeto-gestao-patrimonio/rest/marcas/{id}             |
| 06 | DELETE      | http://localhost:8080/projeto-gestao-patrimonio/rest/marcas/{id}             |

#### 01 - Listar todas as marcas
 * Não necessita de body na requisição
 * Retorno no formato: 
  ```json
    [
      {
        "id": "conteudo",
        "nome": "conteudo" 
      }
    ]
  ```
#### 02 - Pesquisar uma marca usando seu Id
* Necessário adicionar id na url da requisição
* Não necessita de body na requisição
* Retorno no formato:
  ```json
      {
        "id": "conteudo",
        "nome": "conteudo" 
      }
  ```
#### 03 - Pesquisar patrimônios usando o Id de sua marca
* Necessário adicionar id na url da requisição
* Não necessita de body na requisição
* Retorno no formato:
  ```json
    [
      {
        "id": "conteudo",
        "nome": "conteudo" 
      }
    ]
  ```
#### 04 - Adiciona uma marca a base de dados
#### 05 - Editar uma marca existente da base de dados
#### 06 - Remover uma marca da base de dados

### :hammer_and_wrench: STATUS DAS RESPOSTAS
* 200: OK
* 201: Criado
* 204: Resposta sem conteúdo
* 3xx: Redirection
* 400: Má requisição
* 404: Página não encontrada
* 500: Erro interno do servidor
