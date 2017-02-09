## language: pt

#Funcionalidade: Consultar disponibilidades informando período para filtragem
#@CT2.1
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
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   13 MW USI CAMPOS                1 RJ"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   12 MW USI CAMPOS                2 RJ"
#E eu deveria ver 216 itens no grid "Disponibilidades" com "Data/Hora" entre "01/07/2001 00:00" e "09/07/2001 23:00"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional, comercial e eletromecânica)
##Funcionalidade: Consultar disponibilidades informando período para filtragem
#@CT2.2.1
#Cenário: 
##Teste não reproduzível: Mês Final inválido
##
#@CT2.2.2
#Cenário: 
##Teste não reproduzível: Mês Inicial inválido
##
#@CT2.2.3
#Cenário: 
##Teste não reproduzível: Mês Final inválido
##
#@CT2.2.4
#Cenário: 
##Teste não reproduzível: Mês Inicial inválido
##
#@CT2.2.5
#Cenário: Data Inicial maior que Data Final
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data inicial"
#E eu informo o valor "09/07/2001" para o campo "Data final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_004"
#@CT2.2.6
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
##
#@CT2.2.7
#Cenário: Data Final maior ou igual a Data Corrente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2010" para o campo "Data inicial"
#E eu informo o valor "31/07/2099" para o campo "Data final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Data final"
#@CT2.2.8
#Cenário: Data Inicial menor que 01/01/2001
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2000" para o campo " Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data inicial"
#@CT2.2.9
#Cenário: Data Final menor que 01/01/2001
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo " Data Inicial"
#E eu informo o valor "31/07/2000" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_004"
#E eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data final"
#@CT2.2.10
#Cenário: Data Inicial sem preenchimento
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Data inicial"
#@CT2.2.11
#Cenário: Data Final sem prenchimento
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Data final"
##Funcionalidade: Consultar disponibilidades por tipo de disponibilidades
#@CT2.3.1
#Cenário: Não escolher o tipo de disponibilidade(Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Tipo de Disponibilidade"
#@CT2.3.2
#Cenário: Escolher um tipo de disponibilidade(Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   13 MW USI CAMPOS                1 RJ; Tipo Disponibilidade: Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   12 MW USI CAMPOS                2 RJ; Tipo Disponibilidade: Operacional"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional)
#@CT2.3.3
#Cenário: Escolher mais de um tipo de disponibilidade(Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   13 MW USI CAMPOS                1 RJ; Tipo Disponibilidade: Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   12 MW USI CAMPOS                2 RJ; Tipo Disponibilidade: Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   13 MW USI CAMPOS                1 RJ; Tipo Disponibilidade: Comercial"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   12 MW USI CAMPOS                2 RJ; Tipo Disponibilidade: Comercial"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional e comercial)
#@CT2.3.4
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
#Então eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   13 MW USI CAMPOS                1 RJ; Tipo Disponibilidade: Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   12 MW USI CAMPOS                2 RJ; Tipo Disponibilidade: Operacional"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   13 MW USI CAMPOS                1 RJ; Tipo Disponibilidade: Comercial"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   12 MW USI CAMPOS                2 RJ; Tipo Disponibilidade: Comercial"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   13 MW USI CAMPOS                1 RJ; Tipo Disponibilidade: Eletromecânica"
#E eu deveria ver 216 itens no grid "Disponibilidades" com valor "Equipamento: UG   12 MW USI CAMPOS                2 RJ; Tipo Disponibilidade: Eletromecânica"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional, comercial e eletromecânica)
##Funcionalidade: Consultar disponibilidades informando filtro por tipo de instalação
#@CT2.4.1
#Cenário: Sem escolher instalação (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Instalação"
#@CT2.4.2.1
#Cenário: Escolhendo algumas usinas e interligações internacionais (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto a guia "Interligações Internacionais"
#E eu aperto a guia "Usinas"
#Então eu deveria ver 0 itens selecionados na lista "Usinas"
#@CT2.4.2.2
#Cenário: Escolhendo algumas interligações internacionais e usinas (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto a guia "Usinas"
#E eu aperto a guia "Interligações Internacionais"
#Então eu deveria ver 0 itens selecionados na lista "Interligações Internacionais"
#@CT2.4.3
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
#Então eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,717; Dia: 05/07/2001; Hora: 10:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,958; Dia: 05/07/2001; Hora: 11:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 05/07/2001; Hora: 12:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   12 MW USI CAMPOS                2 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades da instalação U.SOBRADINHO são calculadas pelo SAGER)
#@CT2.4.4
#Cenário: Escolhendo algumas interligações internacionais  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 01/05/2002; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 206,000; Dia: 02/05/2002; Hora: 08:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 0,000; Dia: 02/05/2002; Hora: 09:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 206,000; Dia: 02/05/2002; Hora: 17:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 02/05/2002; Hora: 18:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 446,333; Dia: 08/05/2002; Hora: 08:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 0,000; Dia: 08/05/2002; Hora: 09:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 85,833; Dia: 08/05/2002; Hora: 15:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 08/05/2002; Hora: 16:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI CV.GARABI 1". As disponibilidades da instalação "CI CV.GARABI 2" são calculadas pelo SAGER)
#@CT2.4.5
#Cenário: Escolhendo todas as usinas  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,717; Dia: 05/07/2001; Hora: 10:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,958; Dia: 05/07/2001; Hora: 11:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 05/07/2001; Hora: 12:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   12 MW USI CAMPOS                2 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades das outras usinas são calculadas pelo SAGER)
#@CT2.4.6
#Cenário: Escolhendo todas as interligações internacionais  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono todos os itens na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 01/05/2002; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 206,000; Dia: 02/05/2002; Hora: 08:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 0,000; Dia: 02/05/2002; Hora: 09:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 206,000; Dia: 02/05/2002; Hora: 17:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 02/05/2002; Hora: 18:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 446,333; Dia: 08/05/2002; Hora: 08:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 0,000; Dia: 08/05/2002; Hora: 09:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 85,833; Dia: 08/05/2002; Hora: 15:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 08/05/2002; Hora: 16:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI CV.GARABI 1". As disponibilidades das outras interligações internacionais são calculadas pelo SAGER)
##Funcionalidade: Consultar disponibilidadesfiltrando pelo nome da instalação
#@CT2.5.1
#Cenário: Usar um nome de instalação usina existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2009" para o campo "Data Inicial"
#E eu informo o valor "09/07/2009" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "CAMP" para o campo "Nome" da guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,717; Dia: 05/07/2001; Hora: 10:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,958; Dia: 05/07/2001; Hora: 11:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 05/07/2001; Hora: 12:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   12 MW USI CAMPOS                2 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades das outras usinas são calculadas pelo SAGER)
#@CT2.5.2
#Cenário: Usar um nome de instalação usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "ZZZ" no campo "Nome" da guia "Usinas"
#Então eu deveria ver 0 itens na lista "Usinas"
#@CT2.5.3
#Cenário: Usar um nome de instalação  Interligação internacional  existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "GARABI" para o campo "Nome" da guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 01/05/2002; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 206,000; Dia: 02/05/2002; Hora: 08:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 0,000; Dia: 02/05/2002; Hora: 09:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 206,000; Dia: 02/05/2002; Hora: 17:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 02/05/2002; Hora: 18:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 446,333; Dia: 08/05/2002; Hora: 08:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 0,000; Dia: 08/05/2002; Hora: 09:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 85,833; Dia: 08/05/2002; Hora: 15:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 08/05/2002; Hora: 16:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI CV.GARABI 1")
#@CT2.5.4
#Cenário:  Usar um nome de instalação Interligação internacional inexistente 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "ZZZ" para o campo "Nome" da guia "Interligações Internacionais"
#Então eu deveria ver 0 itens na lista "Interligações Internacionais"
#@CT2.5.5
#Cenário: Escolher mais de um nome de instalação existente
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
#Então eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,717; Dia: 05/07/2001; Hora: 10:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,958; Dia: 05/07/2001; Hora: 11:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 05/07/2001; Hora: 12:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   12 MW USI CAMPOS                2 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades da instalação U.SOBRADINHO são calculadas pelo SAGER)
#@CT2.5.6
#Cenário: 
##
##
#@CT2.5.7
#Cenário: 
##
##
##Funcionalidade: Consultar disponibilidades informando filtro por tipo de Fonte Energetica
#@CT2.6.1
#Cenário: Não escolher o tipo de Fonte Energetica
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,717; Dia: 05/07/2001; Hora: 10:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,958; Dia: 05/07/2001; Hora: 11:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 05/07/2001; Hora: 12:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   12 MW USI CAMPOS                2 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades das outras usinas são calculadas pelo SAGER)
#@CT2.6.2
#Cenário:  Escolher Tipo de Fonte Energetica igual a Hidrelétricas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "09/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "UHE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  123 MW STO.ANT.JARI             01 AP; Valor: 123,330; Dia: 01/11/2014; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  123 MW STO.ANT.JARI             01 AP; Valor: 12,330; Dia: 06/11/2014; Hora: 11:00"
#E eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  123 MW STO.ANT.JARI             01 AP; Valor: 0,000; Dia: 06/11/2014; Hora: 12:00"
#E eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  123 MW STO.ANT.JARI             01 AP; Valor: 26,721; Dia: 06/11/2014; Hora: 14:00"
#E eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  123 MW STO.ANT.JARI             01 AP; Valor: 123,330; Dia: 06/11/2014; Hora: 17:00"
#E eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  123 MW STO.ANT.JARI             01 AP; Valor: 20,555; Dia: 07/11/2014; Hora: 17:00"
#E eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  123 MW STO.ANT.JARI             01 AP; Valor: 123,330; Dia: 07/11/2014; Hora: 18:00"
#E eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  123 MW STO.ANT.JARI             02 AP; Valor: 0,000; Dia: 01/11/2014; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  123 MW STO.ANT.JARI             03 AP; Valor: 0,000; Dia: 01/11/2014; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "STO.ANT.JARI" com esses valores "Tipo: operacional; Equipamento: UG  3P4 MW STO.ANT.JARI             04 AP; Valor: 0,000; Dia: 01/11/2014; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "STO.ANT.JARI". As disponibilidades das outras usinas hidrelétricas são calculadas pelo SAGER)
#@CT2.6.3
#Cenário: Escolher  Tipo de Fonte Energetica igual a Termelétricas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2001" para o campo "Data Inicial"
#E eu informo o valor "09/07/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "UTE" para o campo "Tipo" da guia "Usinas"
#E eu seleciono todos os itens na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,717; Dia: 05/07/2001; Hora: 10:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,958; Dia: 05/07/2001; Hora: 11:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 05/07/2001; Hora: 12:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   12 MW USI CAMPOS                2 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades das outras usinas térmicas são calculadas pelo SAGER)
#@CT2.6.4
#Cenário: Escolher tipo de Fonte Energetica inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo "ZZZ" para o campo "Tipo" da guia "Usinas"
#Então eu deveria ver 0 itens na lista "Usinas"
#@CT2.6.5
#Cenário: Escolher mais de um tipo de Fonte Energetica
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
#Então eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,717; Dia: 05/07/2001; Hora: 10:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,958; Dia: 05/07/2001; Hora: 11:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 05/07/2001; Hora: 12:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   12 MW USI CAMPOS                2 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades das outras usinas são calculadas pelo SAGER)
##Funcionalidade: Consultar disponibilidadesfiltrando pelo nome do agente
#@CT2.7.1
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
#Então eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,717; Dia: 05/07/2001; Hora: 10:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,958; Dia: 05/07/2001; Hora: 11:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 05/07/2001; Hora: 12:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   12 MW USI CAMPOS                2 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS")
#@CT2.7.2
#Cenário: Usar um nome de agente de usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu informo o valor "ZZZ" para o campo "Agente"
#Então eu deveria ver 0 itens na lista "Usinas"
#@CT2.7.3
#Cenário: Usar um nome de agente de Interligação internacional existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#E eu informo o valor "CIEN" para o campo "Agente" da guia "Interligações Internacionais"
#E eu seleciono todos os itens na lista de Interligação Internacional
#Então eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 01/05/2002; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 206,000; Dia: 02/05/2002; Hora: 08:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 0,000; Dia: 02/05/2002; Hora: 09:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 206,000; Dia: 02/05/2002; Hora: 17:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 02/05/2002; Hora: 18:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 446,333; Dia: 08/05/2002; Hora: 08:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 0,000; Dia: 08/05/2002; Hora: 09:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 85,833; Dia: 08/05/2002; Hora: 15:00"
#E eu deveria ver uma disponibilidade na lista "CI CV.GARABI 1" com esses valores "Tipo: operacional; Equipamento: CI CV.GARABI 1; Valor: 1030,000; Dia: 08/05/2002; Hora: 16:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CI CV.GARABI 1". As disponibilidades da instalação "CI CV.GARABI 2" são calculadas pelo SAGER)
#@CT2.7.4
#Cenário: Usar um nome de agente de  Interligação internacional  inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu informo o valor "ZZZ" para o campo "Agente" da guia "Interligações Internacionais"
#Então eu deveria ver 0 itens na lista "Interligações Internacionais"
#@CT2.7.5
#Cenário: Escolher mais de um agente por nome e por instalação
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
#Então eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,717; Dia: 05/07/2001; Hora: 10:00"
#E eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 9,958; Dia: 05/07/2001; Hora: 11:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   13 MW USI CAMPOS                1 RJ; Valor: 16,000; Dia: 05/07/2001; Hora: 12:00"
##eu deveria ver uma disponibilidade na lista "CAMPOS" com esses valores "Tipo: operacional; Equipamento: UG   12 MW USI CAMPOS                2 RJ; Valor: 16,000; Dia: 01/07/2001; Hora: 00:00"
##(Consultar a guia "Disponibilidades" para verificar todos os valores de disponibilidade operacional da instalação "CAMPOS". As disponibilidades das usinas "USINA XINGO" e "U.SOBRADINHO" (ambas do agente "CHESF") são calculadas pelo SAGER))
#@CT2.7.6
#Cenário: 
##
##
#@CT2.7.7
#Cenário: 
