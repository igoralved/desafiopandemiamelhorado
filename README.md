# desafiopandemiamelhorado

1.Introdução:

Nesse projeto, é possível verificar as unidades de saúde e seus atendimentos com etapas e testes, além de vê-los, cadastrá-los, atualizá-los e etc.

2.Entidades:
*Etapa: Cada uma tem uma descrição;
*Teste: Cada um tem uma descrição do resultado;
*Atendimento: Cada atendimento tem suas etapas e testes. Elas podem ou não estarem relacionadas com pandemia ou sem possibilidade de contágio. Elas também tem sua data
inicial e tempo de atendimento.
*Unidade de saúde: Cada uma tem um nome, data de quando começou a fazer atendimentos e número de pacientes.

3.DTOs (Data Transfer Objects):
  Cada entidade tem seu DTO. O DTO serve para colocar os dados de um membro da entidade no banco de dados.
  
4.Repositórios:
  Cada repositório tem os dados dos objetos que estão registrados no banco de dados. Também é possível buscar uma instância por id, remover, listar, entre outras.
  
5.Controllers (Controladores):
  Cada controlador tem seu repositório. Quando ele recebe uma requisição, ele devolve uma entidade de resposta (Response Entity).
  Métodos de requisição:
  
  *GET:Lista todas as instâncias registradas no repositório do controlador ou a que corresponde ao ID passado por parâmetro na requisição. Para mostrar por ID,
  avisa que
  
  *POST: Cadastra uma instância no repositório se ela for válida. Caso não seja, retorna uma mensagem de erro.
  
  *PUT: Atualiza a instância com o ID correspondente com os dados passados por parâmetro.
  
  *DELETE: Deleta a instância registrada com o ID correspondente ao do parâmetro, caso seja encontrada no repositório.
