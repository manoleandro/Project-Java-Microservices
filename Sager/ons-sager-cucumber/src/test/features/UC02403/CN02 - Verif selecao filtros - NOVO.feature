### language: pt
#
#Funcionalidade: Consultar disponibilidades informando período para filtragem
#---------------------------------- TESTADO E OK -----------------------------------------------------
##@CT2.1
#Cenário: Data inicial e Data final válidos: Data Inicial menor ou igual a Data Final, Data Inicial maior ou igual a 01/01/2001 e menor que a Data Corrente e Data Final menor que a Data Corrente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG1"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG2"
#E eu deveria ver 216 itens no grid "Disponibilidades" com "Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional, comercial e eletromecânica)
###Funcionalidade: Consultar disponibilidades informando período para filtragem
##@CT2.2.1
##Cenário: 
###Teste não reproduzível: Mês Final inválido
###
##@CT2.2.2
##Cenário: 
###Teste não reproduzível: Mês Inicial inválido
###
##@CT2.2.3
##Cenário: 
###Teste não reproduzível: Mês Final inválido
###
##@CT2.2.4
##Cenário: 
###Teste não reproduzível: Mês Inicial inválido
###
##@CT2.2.5
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
##Cenário: Data Inicial maior que Data Final
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2099" para o campo "Data inicial"
#E eu informo o valor "01/08/2099" para o campo "Data final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Data inicial"
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
##@CT2.2.6
#Cenário: Data Inicial maior ou igual a Data Corrente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2099" para o campo "Data inicial"
#E eu informo o valor "09/07/2001" para o campo "Data final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_004"
#E eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Data inicial"
###
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
##@CT2.2.7
##Cenário: Data Final maior ou igual a Data Corrente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2010" para o campo "Data inicial"
#E eu informo o valor "31/07/2099" para o campo "Data final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Data final"
#---------------------------------- TESTADO E OK -----------------------------------------------------
##@CT2.2.8
#Cenário: Data Inicial menor que 01/01/2001
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2000" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data inicial"
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
##@CT2.2.9
#Cenário: Data Final menor que 01/01/2001
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo " Data Inicial"
#E eu informo o valor "31/07/2000" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_013"
#E eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data final"
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
##@CT2.2.10
#Cenário: Data Inicial sem preenchimento
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Data inicial"
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
##@CT2.2.11
#Cenário: Data Final sem prenchimento
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#
#Funcionalidade: Consultar disponibilidades por tipo de disponibilidades
#---------------------------------- TESTADO E OK -----------------------------------------------------
##@CT2.3.1
#Cenário: Não escolher o tipo de disponibilidade(Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Tipo de Disponibilidade"
#---------------------------------- TESTADO E OK -----------------------------------------------------
##@CT2.3.2
#Cenário: Escolher um tipo de disponibilidade(Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG1" ; Tipo Disponibilidade: "Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG2" ; Tipo Disponibilidade: "Operacional"
#
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional)
#---------------------------------- TESTADO E OK -----------------------------------------------------
##@CT2.3.3
#Cenário: Escolher os três tipos de disponibilidade(Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG1" ; Tipo Disponibilidade: "Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG2" ; Tipo Disponibilidade: "Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG1" ; Tipo Disponibilidade: "Comercial"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG2" ; Tipo Disponibilidade: "Comercial"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG1" ; Tipo Disponibilidade: "Eletromecânica"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG2" ; Tipo Disponibilidade: "Eletromecânica"
#
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional e comercial)
###Funcionalidade: Consultar disponibilidades informando filtro por tipo de instalação
##@CT2.3.4
#---------------------------------- TESTADO E OK  -----------------------------------------------------
#Cenário: Sem escolher instalação (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Instalação"
#
#---------------------------------- INCOMPLETO VALIDANDR COM O LEANDRO -----------------------------------------------------
##@CT2.4.1
##Cenário: Escolhendo algumas usinas e interligações internacionais (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto a guia "Interligações Internacionais"
#E eu aperto a guia "Usinas"
#Então eu deveria ver 1 itens selecionados na guia "Usinas" com valor "Nome: U.SOBRADINHO"
#E eu deveria ver a guia "Interligações Internacionais" indisponível
#
#---------------------------------- INCOMPLETO VALIDANDR COM O LEANDRO -----------------------------------------------------
##@CT2.4.2.1
##Cenário: Escolhendo algumas interligações internacionais e usinas (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto a guia "Usinas"
#E eu aperto a guia "Interligações Internacionais"
#Então eu deveria ver 1 itens selecionados na guia "Interligações Internacionais" com valor "Nome: CI CV.URUGUAIANA"
#E eu deveria ver a guia "Usinas" indisponível
#
#---------------------------------- TESTADO E OK  -----------------------------------------------------
##@CT2.4.2.2
#Cenário:  Escolhendo algumas usinas (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP0UG1" ; Tipo Disponibilidade: "Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "BAUSB-0UG1" ; Tipo Disponibilidade: "Operacional"
#
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades da instalação U.SOBRADINHO são calculadas pelo SAGER)
#---------------------------------- TESTADO E OK -----------------------------------------------------
##@CT2.4.3
#Cenário:  Escolhendo algumas interligações internacionais  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor Tipo: operacional; Equipamento: "ARBRRSGBI1" ; Tipo Disponibilidade: "Operacioanal"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Tipo: operacional; Equipamento: "ARBRRSGBI2" ; Tipo Disponibilidade: "Operacioanal"
#
#(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI CV.GARABI 1" e "CI CV.GARABI 2")
#---------------------------------- INCOMPLETO VALIDANDR COM O ZE -----------------------------------------------------
##@CT2.4.4
##Cenário: Escolhendo todas as usinas  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 4248 itens selecionados na guia "Usinas"
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações. As disponibilidades das outras usinas são calculadas pelo SAGER)
#---------------------------------- INCOMPLETO VALIDANDR COM O LEANDRO -----------------------------------------------------
##@CT2.4.5
#Cenário: Escolhendo todas as interligações internacionais  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono todos os itens na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Tipo: operacional; Equipamento: CI CV.GARABI 1; Tipo Disponibilidade: Operacioanal"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Tipo: operacional; Equipamento: CI CV.GARABI 2; Tipo Disponibilidade: Operacioanal"
#
#(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI CV.GARABI 1". As disponibilidades das outras interligações internacionais são calculadas pelo SAGER)
###Funcionalidade: Consultar disponibilidadesfiltrando pelo nome da instalação
#
#---------------------------------- INCOMPLETO VALIDANDR COM O ZE -----------------------------------------------------
##@CT2.4.6
#Cenário: Usar um nome de instalação usina existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "09/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "MEDI" para o campo "Nome" da guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 648 itens selecionados na guia "Usinas"
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações. As disponibilidades das outras usinas são calculadas pelo SAGER)
#
###Funcionalidade: Consultar disponibilidadesfiltrando pelo nome da instalação
#---------------------------------- TESTADO E OK  -----------------------------------------------------
##@CT2.5.1
#Cenário: Usar um nome de instalação usina existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "ZZZ" no campo "Nome" da guia "Usinas"
#Então eu deveria ver 0 itens na lista "Usinas"
#
#---------------------------------- TESTADO E OK  -----------------------------------------------------
##@CT2.5.2
#Cenário: Usar um nome de instalação  Interligação internacional  existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "ZZZ" no campo "Nome" da guia "Usinas"
#Então eu deveria ver 0 itens na lista "Usinas"
#
#---------------------------------- INCOMPLETO VALIDANDR COM O ZE -----------------------------------------------------
##@CT2.5.3
#Cenário: Usar um nome de instalação Interligação internacional inexistente 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "ZZZ" para o campo "Nome" da guia "Interligações Internacionais"
#Então eu deveria ver 0 itens na lista "Interligações Internacionais"
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI CV.GARABI 1")
#---------------------------------- TESTADO E OK  -----------------------------------------------------
##@CT2.5.4
#Cenário:  Escolher mais de um nome de instalação existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "CAMP" para o campo "Nome" da guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu informo o valor "SOBRA" para o campo "Nome" da guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP_13P8_UG1" ; Tipo Disponibilidade: "Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP_13P8_UG2" ; Tipo Disponibilidade: "Operacional"
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades da instalação U.SOBRADINHO são calculadas pelo SAGER)
#---------------------------------- TESTADO E OK  -----------------------------------------------------
##@CT2.5.5
#Cenário: Escolher um nome de instalação usina existente e um nome instalação usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "19/01/2011" para o campo "Data Final"
#E eu aperto a guia "Usinas"
#E eu informo o valor "SOBRA" para o campo "Nome" na guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu informo o valor "ZZZ" para o campo "Nome" na guia "Usinas"
#Então eu deveria ver 0 itens na lista "Usinas"
#E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "U.SOBRADINHO"
#
#---------------------------------- INCOMPLETO VALIDANDR COM O LEANDRO -----------------------------------------------------
##@CT2.5.6
##Cenário: Escolher um nome de instalação interligação Internacional existente e um nome instalação interligação Internacional inexistente
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "19/01/2011" para o campo "Data Final"
#E eu aperto a guia "Usinas"
#E eu informo o valor "SOBRA" para o campo "Nome" na guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu informo o valor "ZZZ" para o campo "Nome" na guia "Usinas"
#Então eu deveria ver 0 itens na lista "Interligações Internacionais" 
#E eu deveria ver 1 itens selecionados na lista "Interligações Internacionais" com valor "Nome: CI.ACARAY"
#
#---------------------------------- INCOMPLETO VALIDANDR COM O ZE -----------------------------------------------------
##@CT2.5.7
##Cenário: Não escolher o tipo de Fonte Energetica
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG1"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG2; Tipo Disponibilidade: Operacional"
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS")
#
#---------------------------------- INCOMPLETO VALIDANDR COM O ZE -----------------------------------------------------
##@CT2.5.8
##Cenário:  Escolher Tipo de Fonte Energetica igual a Hidrelétricas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "09/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "UHE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 1080 itens  selecionados na guia "Usinas"
#E eu deveria ver 432 itens no grid "Disponibilidades" com valor "Equipamento: APSAJ_13P8_UG01; Tipo Disponibilidade: Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: APSAJ_13P8_UG02; Tipo Disponibilidade: Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: APSAJ_13P8_UG03; Tipo Disponibilidade: Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: APSAJ_4P1_UG04; Tipo Disponibilidade: Operacional"
#
#---------------------------------- INCOMPLETO VALIDANDR COM O LEANDRO -----------------------------------------------------
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "STO.ANT.JARI")
##@CT2.5.7
##Cenário: Escolher  Tipo de Fonte Energetica igual a Termelétricas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "UTE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG1"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG2; Tipo Disponibilidade: Operacional"
#
#---------------------------------- TESTADO E OK  -----------------------------------------------------
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS")
##@CT2.5.7
#Cenário: Escolher tipo de Fonte Energetica inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "ZZZ" para o campo "Tipo" da guia "Usinas"
#Então eu deveria ver 0 itens na lista "Usinas"
#
#---------------------------------- INCOMPLETO VALIDANDR COM O LEANDRO -----------------------------------------------------
##@CT2.5.7
##Cenário: Escolher mais de um tipo de Fonte Energetica
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "UTE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu informo "UHE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG1"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG2; Tipo Disponibilidade: Operacional"
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS")
#---------------------------------- TESTADO E OK -----------------------------------------------------
##@CT2.5.7
#Cenário: Usar um nome de agente de usina existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "FURNAS" para o campo "Agente" da guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP_13P8_UG1" ; Agente: FURNAS; Tipo Disponibilidade: "Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor Equipamento: "RJUSCP_13P8_UG2" ; Agente: FURNAS; Tipo Disponibilidade: "Operacional"
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional do agente "FURNAS")
#
#---------------------------------- TESTADO E OK  -----------------------------------------------------
##@CT2.5.7
#Cenário: Usar um nome de agente de usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "ZZZ" para o campo "Agente"
#Então eu deveria ver 0 itens na lista "Usinas"
#
#---------------------------------- INCOMPLETO VALIDANDR COM O LEANDRO -----------------------------------------------------
##@CT2.5.7
##Cenário: Usar um nome de agente de Interligação internacional existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#E eu informo o valor "CIEN" para o campo "Agente" da guia "Interligações Internacionais"
#E eu seleciono todos os itens na lista de Interligação Internacional
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Tipo: operacional; Equipamento: CI CV.GARABI 1; Tipo Disponibilidade: Operacioanal; Agente CIEN"
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI CV.GARABI 1")
#
#
#---------------------------------- TESTADO E OK  -----------------------------------------------------
##@CT2.5.7
#Cenário: Usar um nome de agente de  Interligação internacional  inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "ZZZ" para o campo "Agente" da guia "Interligações Internacionais"
#Então eu deveria ver 0 itens na lista "Interligações Internacionais"
#
#---------------------------------- INCOMPLETO VALIDANDR COM O LEANDRO -----------------------------------------------------
##@CT2.5.7
##Cenário: Escolher mais de um agente por nome e por instalação
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "FURNAS" para o campo "Agente" da guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu informo "CHESF" para o campo "Agente" da guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG1; Agente: FURNAS; Tipo Disponibilidade: Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG2; Agente: FURNAS; Tipo Disponibilidade: Operacional"
#
###(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional do agente "FURNAS")
#
#---------------------------------- INCOMPLETO VALIDANDR COM O LEANDRO -----------------------------------------------------
##@CT2.5.7
##Cenário: Escolher agente por nome de agente e instalação usina existente e um agente por nome e instalação usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "12/01/2001" para o campo "Data Final"
#E eu aperto a guia "Usinas"
#E eu informo o valor "CHESF" para o campo "Agente" na guia "Usinas"
#E eu seleciono todos os itens da lista "Usinas"
#E eu informo o valor "ZZZ" para o campo "Agente" na guia "Usinas"
#Então eu deveria ver 0 itens na lista "Usinas" 
#E eu deveria ver 2 itens selecionados na lista "Usinas" com valor "Agente: CHESF"
#
##@CT2.5.7
#Cenário: Escolher um agente por nome e instalação interligação Internacional existente e um um agente por nome e instalação interligação Internacional inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "13/01/2001" para o campo "Data Final"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "CIEN" para o campo "Agente" na guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
#E eu informo o valor "ZZZ" para o campo "Agente" na guia "Interligações Internacionais"
#Então eu deveria ver 0 itens na lista "Interligações Internacionais" 
#E eu deveria ver 1 itens selecionados na lista "Interligações Internacionais" com valor "Nome: CI CV.GARABI 1; Agente: CIEN"
#
