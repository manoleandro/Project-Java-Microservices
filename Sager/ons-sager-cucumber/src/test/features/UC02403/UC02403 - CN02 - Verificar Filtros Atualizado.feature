### language: pt
#
Funcionalidade: Consultar disponibilidades informando período para filtragem
#
#---------------------------------- TESTADO E VALIDADO -----------------------------------------------------
##@CT2.1
#Cenario: Data inicial e Data final válidos: Data Inicial menor ou igual a Data Final, Data Inicial maior ou igual a 01/01/2001 e menor que a Data Corrente e Data Final menor que a Data Corrente
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
#Então eu deveria ver 216 itens no grid "CAMPOS" com "Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2"
##(Consultar a guia Disponibilidades" para verificar os valores de disponibilidade operacional, comercial e eletromecânica)"
#
###@CT2.2.1
#Cenario: Mês Inicial válido e Mês Final inválido 
#Não reproduzível. A seleção da data é feita em componente que não possibilita entrada de dados inválidos,
###@CT2.2.2
#Cenario: Mês Inicial inválido e Mês Final válido 
#Não reproduzível. A seleção da data é feita em componente que não possibilita entrada de dados inválidos,
###@CT2.2.3
#Cenario: Mês Inicial sem preenchimento e Mês Final inválido
#Não reproduzível. A seleção da data é feita em componente que não possibilita entrada de dados inválidos,
###@CT2.2.4
#Cenario: 4) Mês Inicial inválido e Mês Final sem preenchimento
#Não reproduzível. A seleção da data é feita em componente que não possibilita entrada de dados inválidos,
#
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
###@CT2.2.5
#Cenario: Data Inicial maior que Data Final
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/07/2001" para o campo "Data Inicial"
#E eu informo o valor "01/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_013"
##
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
###@CT2.2.6
#Cenario: Data Inicial maior ou igual a Data Corrente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2099" para o campo "Data Inicial"
#E eu informo o valor "01/08/2099" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_026" e crítica "Data Inicial"
##
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
###@CT2.2.7
#Cenario: Data Final maior ou igual a Data Corrente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2099" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_026" e crítica "Data Final"
##
#---------------------------------- TESTADO E VALIDADO -----------------------------------------------------
###@CT2.2.8
#Cenario: Data Inicial menor que 01/01/2001
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2000" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_022" e crítica "Data Inicial"
##
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
###@CT2.2.9
#Cenario: Data Final menor que 01/01/2001
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo " "Data Inicial"
#E eu informo o valor "31/07/2000" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_013"
#E eu deveria ver a mensagem de aviso de código "RS_MENS_022" e crítica "Data Final"
##
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
###@CT2.2.10
#Cenario: Data Inicial sem preenchimento
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_002" e crítica "Data Inicial"
##
#---------------------------------- INCOMPLETO NAO DA PRA REPRODUZIR -----------------------------------------------------
###@CT2.2.11
#Cenario: Data Final sem prenchimento
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_002" e crítica "Data Final"
##
#---------------------------------- TESTADO E VALIDADO -----------------------------------------------------
###@CT2.3.1
#Cenario: Não escolher o tipo de disponibilidade(Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_002" e crítica "Tipo de Disponibilidade"
##
#---------------------------------- TESTADO E VALIDADO -----------------------------------------------------
###@CT2.3.2
#Cenario: Escolher um tipo de disponibilidade(Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CAMPOS" com "Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Operacional"
#(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional)"
##
#---------------------------------- TESTADO E VALIDADO -----------------------------------------------------
###@CT2.3.3
#Cenario: Escolher mais de um tipo de disponibilidade(Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CAMPOS" com "Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Comercial"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Comercial"
##(Consultar a guia Disponibilidades" para verificar os valores de disponibilidade operacional e comercial da instalação "CAMPOS")"
##
#---------------------------------- TESTADO E VALIDADO -----------------------------------------------------
###@CT2.3.4
#Cenario: Escolher os três tipos de disponibilidade(Sucesso)
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
#Então eu deveria ver 216 itens no grid "CAMPOS" com "Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Comercial"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Comercial"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Eletromecânica"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Eletromecânica"
##(Consultar a guia Disponibilidades" para verificar os valores de disponibilidade operacional, comercial e eletromecânica da instalação "CAMPOS")"
##
#---------------------------------- TESTADO E VALIDADO -----------------------------------------------------
###@CT2.4.1
#Cenario: Sem escolher instalação (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_002" e crítica "Instalação"
##
#---------------------------------- FALTA IMPLEMENTAR -----------------------------------------------------
###@CT2.4.2.1
#Cenario: Escolhendo algumas usinas e interligações internacionais (Insucesso)
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
##
#---------------------------------- FALTA IMPLEMENTAR -----------------------------------------------------
###@CT2.4.2.2
#Cenario: Escolhendo algumas interligações internacionais e usinas (Insucesso)
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
#---------------------------------- TESTADO E VALIDADO -----------------------------------------------------
###@CT2.4.3
#Cenario:  Escolhendo algumas usinas (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CAMPOS" com "Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com "Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG3" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG4" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG5" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG6" ; Tipo: "Operacional"
##(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações "CAMPOS" e "U.SOBRADINHO")"
##
###@CT2.4.4
#Cenario: Escolhendo algumas interligações internacionais  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CI CV.GARABI 1" com "Data/Hora" entre "01/05/2002 00:00" e "09/05/2002 23:00"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 1" com valor Equipamento: "CI CV.GARABI 1" ; Tipo: "Operacional" 
#E eu deveria ver 216 itens no grid "CI CV.GARABI 2" com Data/Hora" entre "01/05/2002 00:00" e "09/05/2002 23:00"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 2" com valor Equipamento: "CI CV.GARABI 2" ; Tipo: "Operacional" 
##(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalações "CI CV.GARABI 1" e "CI CV.GARABI 2")"
##
###@CT2.4.5
#Cenario: Escolhendo todas as usinas  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "BENTO MUNHOZ" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CANDIOTA III" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "P.MEDICI" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "PORTO PECEM I" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "PORTO PECEM II" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "STO.ANT.JARI" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "TERMONORTE I" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.CHARQUEADAS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UHE BALBINA" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UHE JIRAU" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "USINA XINGO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UT MARIO LAGO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UT. FORTALEZA" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
##(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações "BENTO MUNHOZ", "CAMPOS", "CANDIOTA III", "P.MEDICI", "PORTO PECEM I","PORTO PECEM II", "STO.ANT.JARI","TERMONORTE I", "U.CHARQUEADAS","U.SOBRADINHO", "UHE BALBINA","UHE JIRAU", "USINA XINGO","UT MARIO LAGO" e "UT. FORTALEZA")"
##
###@CT2.4.6
#Cenario: Escolhendo todas as interligações internacionais  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono todos os itens no grid "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CI ACARAY" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CI ACARAY" com valor Equipamento: "CI ACARAY" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CI CV.URUGUAIANA" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CI CV.URUGUAIANA" com valor Equipamento: "CI CV.URUGUAIANA" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 1" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 1" com valor Equipamento: "CI CV.GARABI 1" ; Tipo: "Operacional" 
#E eu deveria ver 216 itens no grid "CI CV.GARABI 2" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 2" com valor Equipamento: "CI CV.GARABI 2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CI LIVRAMENTO 2" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CI LIVRAMENTO 2" com valor Equipamento: "CI LIVRAMENTO 2" ; Tipo: "Operacional"
##(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalações "CI ACARAY", "CI CV.URUGUAIANA", "CI CV.GARABI 1", "CI CV.GARABI 2" e "CI LIVRAMENTO 2")"
##
###@CT2.5.1
#Cenario: Usar um nome de instalação usina existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "09/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "MEDI" para o campo "Nome" da guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens selecionados na guia "P.MEDICI" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "P.MEDICI" com valor Equipamento: "RSUPME_13P8_UG1" ; Tipo: "Operacional" 
#E eu deveria ver 216 itens no grid "P.MEDICI" com valor Equipamento: "RSUPME_13P8_UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "P.MEDICI" com valor Equipamento: "RSUPME_13P8_UG3" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "P.MEDICI" com valor Equipamento: "RSUPME_13P8_UG4" ; Tipo: "Operacional"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação P.MEDICI)"
##
###@CT2.5.2
#Cenario: Usar um nome de instalação usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "ZZZ" no campo "Nome" da guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_015"
##
###@CT2.5.3
#Cenario: Usar um nome de instalação  Interligação internacional  existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "GARABI" para o campo "Nome" da guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens selecionados na guia "CI CV.GARABI 1" com Data/Hora" entre "01/05/2002 00:00" e "09/05/2002 23:00"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 1" com valor Equipamento: "CI CV.GARABI 1" ; Tipo: "Operacional"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI CV.GARABI 1")"
##
###@CT2.5.4
#Cenario:  Usar um nome de instalação Interligação internacional inexistente 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "ZZZ" para o campo "Nome" da guia "Interligações Internacionais"
#E eu seleciono todos os itens no grid "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_015"
##
###@CT2.5.5
#Cenario: Escolher mais de um nome de instalação existente
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
#Então eu deveria ver 216 itens selecionados na guia "CAMPOS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens selecionados na guia "U.SOBRADINHO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG3" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG4" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG5" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG6" ; Tipo: "Operacional"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades da instalação U.SOBRADINHO são calculadas pelo SAGER)"
##
###@CT2.5.6
#Cenario: Escolher um nome de instalação usina existente e um nome instalação usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu aperto a guia "Usinas"
#E eu informo o valor "SOBRA" para o campo "Nome" na guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu informo o valor "ZZZ" para o campo "Nome" na guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens selecionados na guia "U.SOBRADINHO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG3" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG4" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG5" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG6" ; Tipo: "Operacional"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades da instalação U.SOBRADINHO são calculadas pelo SAGER)"
##
###@CT2.5.7
#Cenario: Escolher um nome de instalação interligação Internacional existente e um nome instalação interligação Internacional inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "ACA" para o campo "Nome" na guia "Interligações Internacionais"
#E eu seleciono o item "CI.ACARAY" na lista "Interligações Internacionais"
#E eu informo o valor "ZZZ" para o campo "Nome" na guia "Interligações Internacionais"
#Então eu deveria ver 216 itens selecionados na guia "CI.ACARAY" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CI.ACARAY" com valor "Tipo: Operacional; Equipamento: CI.ACARAY"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI.ACARAY")"
##
###@CT2.6.1
#Cenario: Não escolher o tipo de Fonte Energetica
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "BENTO MUNHOZ" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CANDIOTA III" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "P.MEDICI" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "PORTO PECEM I" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "PORTO PECEM II" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "STO.ANT.JARI" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "TERMONORTE I" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.CHARQUEADAS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UHE BALBINA" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UHE JIRAU" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "USINA XINGO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UT MARIO LAGO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UT. FORTALEZA" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações "BENTO MUNHOZ", "CAMPOS", "CANDIOTA III", "P.MEDICI", "PORTO PECEM I","PORTO PECEM II", "STO.ANT.JARI","TERMONORTE I", "U.CHARQUEADAS","U.SOBRADINHO", "UHE BALBINA","UHE JIRAU", "USINA XINGO","UT MARIO LAGO" e "UT. FORTALEZA")"
##
###@CT2.6.2
#Cenario:  Escolher Tipo de Fonte Energetica igual a Hidrelétricas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "09/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "UHE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "BENTO MUNHOZ" com Data/Hora" entre "01/11/2014 00:00" e "09/11/2014 23:00"
#E eu deveria ver 216 itens no grid "STO.ANT.JARI" com Data/Hora" entre "01/11/2014 00:00" e "09/11/2014 23:00"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com Data/Hora" entre "01/11/2014 00:00" e "09/11/2014 23:00"
#E eu deveria ver 216 itens no grid "UHE BALBINA" com Data/Hora" entre "01/11/2014 00:00" e "09/11/2014 23:00"
#E eu deveria ver 216 itens no grid "UHE JIRAU" com Data/Hora" entre "01/11/2014 00:00" e "09/11/2014 23:00"
#E eu deveria ver 216 itens no grid "USINA XINGO" com Data/Hora" entre "01/11/2014 00:00" e "09/11/2014 23:00"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações "BENTO MUNHOZ", "STO.ANT.JARI","U.SOBRADINHO", "UHE BALBINA","UHE JIRAU" e "USINA XINGO")"
##
###@CT2.6.3
#Cenario: Escolher  Tipo de Fonte Energetica igual a Termelétricas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "UTE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CAMPOS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CANDIOTA III" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "P.MEDICI" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "PORTO PECEM I" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "PORTO PECEM II" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "TERMONORTE I" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.CHARQUEADAS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UT MARIO LAGO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UT. FORTALEZA" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações "CAMPOS", "CANDIOTA III", "P.MEDICI", "PORTO PECEM I","PORTO PECEM II", "TERMONORTE I", "U.CHARQUEADAS","UT MARIO LAGO" e "UT. FORTALEZA")"
##
###@CT2.6.4
#Cenario: Escolher tipo de Fonte Energetica inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "ZZZ" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_015"
##
###@CT2.6.5
#Cenario: Escolher mais de um tipo de Fonte Energetica
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "UTE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu informo "UHE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "BENTO MUNHOZ" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CANDIOTA III" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "P.MEDICI" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "PORTO PECEM I" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "PORTO PECEM II" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "STO.ANT.JARI" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "TERMONORTE I" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.CHARQUEADAS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UHE BALBINA" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UHE JIRAU" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "USINA XINGO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UT MARIO LAGO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "UT. FORTALEZA" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações "BENTO MUNHOZ", "CAMPOS", "CANDIOTA III", "P.MEDICI", "PORTO PECEM I","PORTO PECEM II", "STO.ANT.JARI","TERMONORTE I", "U.CHARQUEADAS","U.SOBRADINHO", "UHE BALBINA","UHE JIRAU", "USINA XINGO","UT MARIO LAGO" e "UT. FORTALEZA")"
##
###@CT2.7.1
#Cenario: Usar um nome de agente de usina existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "FURNAS" para o campo "Agente" da guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CAMPOS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Operacional"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional do agente "FURNAS")"
##
###@CT2.7.2
#Cenario: Usar um nome de agente de usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "ZZZ" para o campo "Agente"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_015"
##
###@CT2.7.3
#Cenario: Usar um nome de agente de Interligação internacional existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#E eu informo o valor "CIEN" para o campo "Agente" da guia "Interligações Internacionais"
#E eu seleciono todos os itens na lista de Interligação Internacional
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CI CV.GARABI 1" com Data/Hora" entre "01/05/2002 00:00" e "09/05/2002 23:00"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 1" com valor Equipamento: "CI CV.GARABI 1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 2" com Data/Hora" entre "01/05/2002 00:00" e "09/05/2002 23:00"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 2" com valor Equipamento: "CI CV.GARABI 2" ; Tipo: "Operacional"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações "CI CV.GARABI 1" e "CI CV.GARABI 2")"
##
###@CT2.7.4
#Cenario: Usar um nome de agente de  Interligação internacional  inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "ZZZ" para o campo "Agente" da guia "Interligações Internacionais"
#E eu aperto a guia "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de aviso de código "RS_MENS_015"
##
###@CT2.7.5
#Cenario: Escolher mais de um agente por nome e por instalação
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "FURNAS" para o campo "Agente" da guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu informo "CHESF" para o campo "Agente" da guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CAMPOS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG3" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG4" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG5" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "U.SOBRADINHO" com valor Equipamento: "BAUSB-0UG6" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "USINA XINGO" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "USINA XINGO" com valor Equipamento: "ALUXG_18P0_UG1" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "USINA XINGO" com valor Equipamento: "ALUXG_18P0_UG2" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "USINA XINGO" com valor Equipamento: "ALUXG_18P0_UG3" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "USINA XINGO" com valor Equipamento: "ALUXG_18P0_UG4" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "USINA XINGO" com valor Equipamento: "ALUXG_18P0_UG5" ; Tipo: "Operacional"
#E eu deveria ver 216 itens no grid "USINA XINGO" com valor Equipamento: "ALUXG_18P0_UG6" ; Tipo: "Operacional"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações CAMPOS, U.SOBRADINHO e USINA XINGO)"
##
###@CT2.7.6
#Cenario: Escolher agente por nome de agente e instalação usina existente e um agente por nome e instalação usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu aperto a guia "Usinas"
#E eu informo o valor "FURNAS" para o campo "Agente" na guia "Usinas"
#E eu seleciono todos os itens da lista "Usinas"
#E eu informo o valor "ZZZ" para o campo "Agente" na guia "Usinas"
#E eu seleciono todos os itens no grid "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CAMPOS" com Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG1"
#E eu deveria ver 216 itens no grid "CAMPOS" com valor Equipamento: "RJUSCP0UG2"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional das instalações CAMPOS)"
##
###@CT2.7.7
#Cenario: Escolher um agente por nome e instalação interligação Internacional existente e um um agente por nome e instalação interligação Internacional inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "CIEN" para o campo "Agente" na guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
#E eu informo o valor "ZZZ" para o campo "Agente" na guia "Interligações Internacionais"
#E eu aperto a guia "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "CI CV.GARABI 1" com Data/Hora" entre "01/05/2002 00:00" e "09/05/2002 23:00"
#E eu deveria ver 216 itens no grid "CI CV.GARABI 1" com valor Equipamento: "CI CV.GARABI 1" ; Tipo: "Operacional"
###(Consultar a guia Disponibilidades" para verificar todos os valores de disponibilidade operacional dainstalação "CI CV.GARABI 1")"
