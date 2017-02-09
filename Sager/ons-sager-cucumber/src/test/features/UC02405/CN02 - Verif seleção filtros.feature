# language: pt
Funcionalidade: Verificar Seleção de Filtros
@CT2.1.1
Cenário: 1) Data Inicial válido e Data Final válido,
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2016" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 7 itens no grid "Agendamentos de Retificação" 
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" entre "01/01/2016" e "01/10/2017"
@CT2.1.2
Cenário: 2) Data Inicial menor ou igual a Data Final, 
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Retificação" 
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com valor "Data/Hora: 01/10/2017"
@CT2.1.3
Cenário: 3) Data Inicial maior ou igual a 01/01/2001 e menor que a Data Corrente e Data Final menor que a Data Corrente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2015" para o campo "Data Inicial"
E eu informo o valor "01/01/2016" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Agendamentos de Retificação" 
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" entre "01/01/2015" e "01/01/2016"
@CT2.1.4
Cenário: 4) Data Inicial sem preenchimento
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "31/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação"  
E  eu deveria ver todos os itens do grid "Agendamentos de Retificação" com valor "Data/Hora" com valor anterior ou igual a "31/10/2017"
@CT2.1.5
Cenário: 5) Data Final sem prenchimento
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2016" para o campo "Data Inicial"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 10 itens no grid "Agendamentos de Retificação" 
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" com valor maior ou igual a "01/01/2016"
@CT2.1.6
Cenário: 6) Nenhuma data preenchida
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação" 
@CT2.1.7
Cenário: 7) Data Final maior ou igual a Data Corrente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 14 itens no grid "Agendamentos de Retificação"
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" anterior ou igual a "03/06/2016"
@CT2.1.8
Cenário: 1) Data Inicial válido e Data Final válido,
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/08/2015" para o campo "Data Inicial"
E eu informo o valor "01/08/2016" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Retificação" 
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" entre "01/08/2015" e "01/08/2016"
@CT2.1.9
Cenário: 2) Data Inicial menor ou igual a Data Final, 
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2015" para o campo "Data Inicial"
E eu informo o valor "01/10/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 itens no grid "Agendamentos de Retificação" 
E eu deveria ver um item do grid "Agendamentos de Retificação" com valor "Data/Hora: 01/10/2015"
@CT2.1.10
Cenário: 3) Data Inicial maior ou igual a 01/01/2001 e menor que a Data Corrente e Data Final menor que a Data Corrente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2015" para o campo "Data Inicial"
E eu informo o valor "01/01/2016" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Retificação" 
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" entre "01/01/2015" e "01/01/2016"
@CT2.1.11
Cenário: 4) Data Inicial sem preenchimento
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "31/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Agendamentos de Retificação"  
E  eu deveria ver todos os itens do grid "Agendamentos de Retificação" com valor "Data/Hora" com valor anterior ou igual a "31/10/2017"
@CT2.1.12
Cenário: 5) Data Final sem prenchimento
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2016" para o campo "Data Inicial"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Retificação" 
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" com valor maior ou igual a "01/01/2016"
@CT2.1.13
Cenário: 6) Nenhuma data preenchida
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Agendamentos de Retificação" 
@CT2.1.14
Cenário: 7) Data Final maior ou igual a Data Corrente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
# eu informo o valor "01/10/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Agendamentos de Retificação"
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" anterior ou igual a "01/10/2015"
@CT2.2.1
Cenário: 1) Data Inicial válido e Data Final inválido 
#Não reproduzível: Data Final Inválida
#
@CT2.2.2
Cenário: 2) Data Inicial inválido e Data Final válido 
#Não reproduzível: Data Final Inválida
#
@CT2.2.3
Cenário: 3) Data Inicial sem preenchimento e Data Final inválido
#Não reproduzível: Data Final Inválida
#
@CT2.2.4
Cenário: 4) Data Inicial inválido e Data Final sem preenchimento
#Não reproduzível: Data Final Inválida
#
@CT2.2.5
Cenário: 5) Data Inicial maior que Data Final
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2016" para o campo "Data Inicial"
E eu informo o valor "01/01/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_013"
@CT2.2.6
Cenário: 6) Data Inicial maior ou igual a Data Corrente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Agendamentos de Retificação" 
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" com valor maior ou igual a "01/01/2017"
@CT2.2.7
Cenário: 7) Data Inicial menor que 01/01/2001
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2000" para o campo "Data Inicial"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data Inicial"
@CT2.2.8
Cenário: 8) Data Final menor que 01/01/2001
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/06/2000" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data Final"
@CT2.2.9
Cenário: 1) Data Inicial válido e Data Final inválido 
#Não reproduzível: Data Final Inválida
#
@CT2.2.10
Cenário: 2) Data Inicial inválido e Data Final válido 
#Não reproduzível: Data Final Inválida
#
@CT2.2.11
Cenário: 3) Data Inicial sem preenchimento e Data Final inválido
#Não reproduzível: Data Final Inválida
#
@CT2.2.12
Cenário: 4) Data Inicial inválido e Data Final sem preenchimento
#Não reproduzível: Data Final Inválida
#
@CT2.2.13
Cenário: 5) Data Inicial maior que Data Final
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2016" para o campo "Data Inicial"
E eu informo o valor "01/01/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_013"
@CT2.2.14
Cenário: 6) Data Inicial maior ou igual a Data Corrente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 itens no grid "Agendamentos de Retificação" 
E eu deveria ver todos os itens do grid "Agendamentos de Retificação" com "Data/Hora" com valor maior ou igual a "01/01/2017"
@CT2.2.15
Cenário: 7) Data Inicial menor que 01/01/2001
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2000" para o campo "Data Inicial"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data Inicial"
@CT2.2.16
Cenário: 8) Data Final menor que 01/01/2001
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"  
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/06/2000" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data Final"
@CT2.3.1
Cenário: 1) Escolher Origem de Agendamento "Retificação";
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem do agendamento"
Então o conjunto de guias "Instalações" deveria ficar oculto
@CT2.3.2
Cenário: 2) Escolher Origem de Agendamento  "Cenários";
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cenários" para o campo "Origem do agendamento"
Então o conjunto de guias "Instalações" deveria ficar oculto
@CT2.3.3
Cenário: 3) Escolher Origem de Agendamento  "Cálculo de Taxas"
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem do agendamento"
Então o conjunto de guias "Instalações" deveria ficar disponível
@CT2.4.1
Cenário: 1) Sem escolher instalação  - Default de Instalação é Usina (SUCESSO)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Instalação"
@CT2.4.2
Cenário: 2) Escolhendo algumas usinas e interligações internacionais (Insucesso)
#Não aplicável
#
@CT2.4.3
Cenário: 3) Escolhendo algumas usinas (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 itens no grid "Agendamentos de Cálculo de Taxas"
E eu todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 001-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 002-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 003-2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Instalação: U.SOBRADINHO"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Instalação: CAMPOS"
@CT2.4.4
Cenário: 4) Escolhendo algumas interligações internacionais  (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.GARABI 1 ou CI CV.GARABI 2"
@CT2.4.5
Cenário: 5) Escolhendo todas as usinas  (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 7 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 3 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 001-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 002-2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 003-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 004-2017"
@CT2.4.6
Cenário: 6) Escolhendo todas as interligações internacionais  (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono todos os itens na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
@CT2.4.7
Cenário: 1) Sem escolher instalação  - Default de Instalação é Usina (SUCESSO)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Instalação"
@CT2.4.8
Cenário: 2) Escolhendo algumas usinas e interligações internacionais (Insucesso)
#Não apllicável
#
@CT2.4.9
Cenário: 3) Escolhendo algumas usinas (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item no grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:001-2017; Instalação:U.SOBRADINHO"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017; Instalação:U.SOBRADINHO"
E eu deveria ver 0 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:UT. FORTALEZA"
@CT2.4.10
Cenário: 4) Escolhendo algumas interligações internacionais  (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:003-2017; Instalação:CI CV.GARABI 1"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:003-2017; Instalação:CI CV.GARABI 2"
@CT2.4.11
Cenário: 5) Escolhendo todas as usinas  (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-se1" e perfil "COSR-SE"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 001-2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 003-2017"
@CT2.4.12
Cenário: 6) Escolhendo todas as interligações internacionais  (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono todos os itens na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 6 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 001-2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 003-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 005-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 006-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo: 007-2017"
@CT2.5.1
Cenário: 1) Usar um nome de instalação usina existente 
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item no grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:001-2017; Instalação:U.SOBRADINHO"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017; Instalação:U.SOBRADINHO"
@CT2.5.2
Cenário: 2) Usar um nome de instalação usina inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Nome" 
Então eu deveria ver 0 itens na lista "Usinas"
@CT2.5.3
Cenário: 3) Usar um nome de instalação Interligação internacional existente 
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendame]nto"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "GARAB" para o campo "Nome"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.GARABI 1"
@CT2.5.4
Cenário: 4) Usar um nome de instalação  Interligação internacional inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Nome"
#
Então eu deveria ver 0 itens na lista "Interligações Internacionais"
@CT2.5.5
Cenário: 5) Escolher mais de um nome de instalação existente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CAMPOS"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:001-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:003-2017"
@CT2.5.6
Cenário: 6) Escolher um nome de instalação usina existente e um nome instalação usina inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "ZZZ" para o campo "Nome" 
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:001-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017"
@CT2.5.7
Cenário: 7) Escolher um nome de instalação interligação Internacional existente e um nome instalação interligação Internacional inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Nome"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.GARABI 1"
@CT2.5.1
Cenário: 1) Usar um nome de instalação usina existente 
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:001-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017"
@CT2.5.2
Cenário: 2) Usar um nome de instalação usina inexistente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Nome" 
#
Então eu deveria ver 0 itens na lista "Usinas"
@CT2.5.3
Cenário: 3) Usar um nome de instalação Interligação internacional existente 
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendame]nto"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "GARAB" para o campo "Nome"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.GARABI 1"
@CT2.5.4
Cenário: 4) Usar um nome de instalação  Interligação internacional inexistente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Nome"
Então eu deveria ver 0 itens na lista "Interligações Internacionais"
@CT2.5.5
Cenário: 5) Escolher mais de um nome de instalação existente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "FORTA" para o campo "Nome" 
E eu seleciono o item "UT. FORTALEZA" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 0 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:UT. FORTALEZA"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:001-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017"
@CT2.5.6
Cenário: 6) Escolher um nome de instalação usina existente e um nome instalação usina inexistente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "ZZZ" para o campo "Nome" 
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:001-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017"
@CT2.5.7
Cenário: 7) Escolher um nome de instalação interligação Internacional existente e um nome instalação interligação Internacional inexistente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Nome"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.GARABI 1"
@CT2.6.1
Cenário: 1) Não escolher o tipo de usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 7 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 3 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:001-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:003-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:004-2017"
@CT2.6.2
Cenário: 2) Escolher Hidrelétricas
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UHE" para "Tipo"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 item no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver todos os itens "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
@CT2.6.3
Cenário: 3) Escolher Termelétricas
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UTE" para "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
@CT2.6.4
Cenário: 4) Escolher tipo inexistente de usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para "Tipo"
Então eu deveria ver 0 itens na lista "Usinas"
@CT2.6.5
Cenário: 5) Escolher mais de um tipo de usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu desmarco todos os itens na lista "Usinas"
E eu informo o valor "UHE" para o campo "Tipo" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "UTE" para o campo "Tipo" 
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 2 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CAMPOS"
@CT2.6.6
Cenário: 1) Não escolher o tipo de usina
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"  
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:001-2017"
E eu deveria ver 1 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017"
@CT2.6.7
Cenário: 2) Escolher Hidrelétricas
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UHE" para "Tipo"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 item no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver todos os itens "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
@CT2.6.8
Cenário: 3) Escolher Termelétricas
Dado que eu esteja autenticado com usuário "cosr-se1" e perfil "COSR-SE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UTE" para "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CAMPOS"
E eu deveria ver 2 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:UT MARIO LAGO"
@CT2.6.9
Cenário: 4) Escolher tipo inexistente de usina
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"  
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para "Tipo"
#
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
@CT2.6.10
Cenário: 5) Escolher mais de um tipo de usina
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu desmarco todos os itens na lista "Usinas"
E eu informo o valor "UHE" para o campo "Tipo" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "UTE" para o campo "Tipo" 
E eu seleciono o item "UT. FORTALEZA" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
@CT2.7.1
Cenário: 1) Usar um nome de agente de usina existente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
@CT2.7.2
Cenário: 2) Usar um nome de agente de usina inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Agente"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
@CT2.7.3
Cenário: 3) Usar um nome de agente de Interligação internacional existente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.GARABI 1"
@CT2.7.4
Cenário: 4) Usar um nome de agente de  Interligação internacional  inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ZZZZ" para o campo "Agente"
E eu seleciono o item "U.SOBRADINHO" na lista "Interligações Internacionais"
@CT2.7.5
Cenário: 5) Escolher mais de um agente existente para Usina 
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "FURNAS
#" para o campo "Agente" 
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
E eu deveria ver 2 os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CAMPOS"
@CT2.7.6
Cenário: 5) Escolher mais de um agente existente para Interligação Internacional
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu informo o valor "ELETROSUL" para o campo "Agente"
E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.GARABI 1"
E eu deveria ver 1 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.URUGUAIANA"
@CT2.7.7
Cenário: 6) Escolher agente existente e escolher agente inexistente para Usina 
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" 
E eu seleciono todos os itens da lista "Usinas"
E eu informo o valor "ZZZ" para o campo "Agente" 
Então eu deveria ver 0 itens na lista "Usinas" 
E eu deveria ver 5 itens selecionados na lista "Usinas" com valor "Agente: CHESF"
@CT2.7.8
Cenário: 6) Escolher agente existente e escolher agente inexistente para  Interligação Internacional
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente" na guia "Interligações Internacionais"
E eu seleciono todos os itens da lista "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Agente"  na guia "Interligações Internacionais"
Então eu deveria ver 0 itens na lista "Usinas" 
E eu deveria ver 6 itens selecionados na lista "Interligações Internacionais" com valor "Agente: CIEN"
@CT2.7.1
Cenário: 1) Usar um nome de agente de usina existente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:U.SOBRADINHO"
@CT2.7.2
Cenário: 2) Usar um nome de agente de usina inexistente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Agente"
Então eu deveria ver 0 itens na lista "Usinas" 
@CT2.7.3
Cenário: 3) Usar um nome de agente de Interligação internacional existente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.GARABI 1"
@CT2.7.4
Cenário: 4) Usar um nome de agente de  Interligação internacional  inexistente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ZZZZ" para o campo "Agente"
Então eu deveria ver 0 itens na lista "Interligações Internacionais" 
@CT2.7.5
Cenário: 5) Escolher mais de um agente existente para Usina 
Dado que eu esteja autenticado com usuário "cosr-se1" e perfil "COSR-SE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "PETROBRAS" para o campo "Agente" 
E eu informo o valor "CHESF" para o campo "Agente" 
E eu aperto o botão "Pesquisar"
Então eu deveria ver 7 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Agente: PETROBRAS ou CHESF"
@CT2.7.6
Cenário: 5) Escolher mais de um agente existente para Interligação Internacional
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu informo o valor "ELETROSUL" para o campo "Agente"
E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 item no grid "Agendamentos de Cálculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2017" e "31/12/2017"
E eu deveria ver 2 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.GARABI 1"
E eu deveria ver 1 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.URUGUAIANA"
@CT2.7.7
Cenário: 6) Escolher agente existente e escolher agente inexistente para Usina 
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" 
E eu seleciono todos os itens da lista "Usinas"
E eu informo o valor "ZZZ" para o campo "Agente" 
Então eu deveria ver 0 itens na lista "Usinas" 
E eu deveria ver 5 itens selecionados na lista "Usinas" com valor "Agente: CHESF"
@CT2.7.8
Cenário: 6) Escolher agente existente e escolher agente inexistente para  Interligação Internacional
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S"  
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2017" para o campo "Data Inicial"
E eu informo o valor "31/12/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente" na guia "Interligações Internacionais"
E eu seleciono todos os itens da lista "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Agente"  na guia "Interligações Internacionais"
Então eu deveria ver 0 itens na lista "Usinas" 
E eu deveria ver 6 itens selecionados na lista "Interligações Internacionais" com valor "Agente: CIEN"
