# language: pt

Funcionalidade: Solicitar cálculo informando período para filtragem

##@CT01001

Esquema do Cenario: Solicitar o agendamento do cálculo de taxas informando a data de início maior que janeiro de 2001 com data final igual a data inicial

###Pesquisa das Taxas
Dado que eu esteja autenticado com usuário <Login> e perfil <Perfil>
E eu esteja na página "Solicitar cálculo das taxas"
Quando eu informo no campo "Data Inicial" o valor <DataInicial>
E eu informo no campo "Data Final" o valor <DataFinal>
E eu seleciono a instalação <Instalacao> na guia <TipoInstalacao>
E eu aperto o botão <Botao>
Então eu deveria ver a modal "Agendamento de Cálculo" com o grid <TipoGrid> preenchido com o <Resultado>:

Exemplos: 
| Login | Perfil  | DataInicial | DataFinal |   Instalacao  | TipoInstalacao |           TipoGrid          |       Botao       |
|"cnos" | "CNOS"  | "01/2017"   | "01/2017" | "USINA XINGO" |  "Usinas"      | "Agendamento pré-existente" | "Agendar Cálculo" |