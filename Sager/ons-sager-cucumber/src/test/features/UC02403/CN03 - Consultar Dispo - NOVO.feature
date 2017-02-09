### language: pt
#Funcionalidade: Consultar disponibilidade operacional para uma instalação
##@CT3.1.1.1.1
#Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS Suspensão: 14/12/2012
###Nesse caso as tres Unidades Geradoras de Campos estão suspensas no mesmo período solicitado, então o resultado esperado está correto
###OK, mas incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
###A mensagem mais adequada é a MENS_09 pois a instalação toda está suspensa.
###E o texto apresentado da mensagem 31 não está correto.
#
#-------------------------------------------- VERIFICAR VALORES ------------------------------------------
##@CT3.1.1.1.2
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação parcialmente suspensa
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG2"
#E eu deveria ver 744 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG3"
#E eu deveria ver 744  itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG4"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "RSUPME_13P8_UG3" e "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014     
###Nesse caso apenas uma das Unidades Geradoras está suspensa, então, o sistema deve apresentar a disponibilidade da Unidade Geradora que está em operação comercial. E a mensagem de que a Unidade Geradora XXX está em Suspensão no perído XXXX e o valor de sua disponibilidade é 0 (zero).
###Adequar a Massa de Dados
###A massa de dados não está de acordo com o resultado esperado descrito.
###Incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
###A massa de dados não está de acordo com o resultado esperado descrito (só existemm dados até 09/08/2014)
###Obs.: Verificar a quantidade após a disponibilização da massa completa de dados
#
#------------------------------------ OK ------------------------------------------------
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional   
##@CT3.1.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2003" para o campo "Data Inicial"
#E eu informo o valor "31/03/2003" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data início: 01/05/2002"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CI CV.GARABI 2 Data início: 01/05/2002
###A massa de dados não está de acordo com o resultado esperado descrito.
###Incluir na mensagem quais interligações internacionais estão suspensas e os respectivos períodos de suspensão.
###Obs.: Como todos os equipamentos estão suspensos, foi gerada uma única mensagem de erro apontando para a instalação.
#
#-------------------------------------------------- OK ----------------------------------------------------------
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação 100% suspensa
##@CT3.1.3.1.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015"
#Equipamento: RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
#E eu deveria ver 0 itens na lista "CAMPOS"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS 
#Equipamento:  RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015
#Equipamento:  RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
#OBS
#Adequar a Massa de Dados
#Incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
#
#
#----------------------------------- VALIDAR VALORES 0 MAS OK ------------------------------------------------------
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação parcialmente suspensa
##@CT3.1.3.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "20/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014      
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG2"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG3"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG4"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "RSUPME_13P8_UG3" e "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  
###Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014   
###Nesse caso apenas uma das Unidades Geradoras está suspensa, então, o sistema deve apresentar a disponibilidade da Unidade Geradora que está em operação comercial. E a mensagem de que a Unidade Geradora XXX está em Suspensão no perído XXXX e o valor de sua disponibilidade é 0 (zero).
###Adequar a Massa de Dados
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
###A massa de dados não está de acordo com o resultado esperado descrito (só existemm dados até 09/08/2014)
###Obs.: Verificar a quantidade após a disponibilização da massa completa de dados
#
#
#----------------------------------- OK -------------------------------------------
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional  
##@CT3.1.3.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "12/03/2003" para o campo "Data Inicial"
#E eu informo o valor "31/03/2003" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data início: 01/05/2002"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CI CV.GARABI 2 Data início: 01/05/2002
###OBS
###OK, mas Incluir na mensagem quais equipamentos estão suspensas e os respectivos períodos de suspensão.
###Obs.: Como todos os equipamentos estão suspensos, foi gerada uma única mensagem de erro apontando para a instalação.
###Período alterado
#
#
#---------------------------------------  BUG !!!!! -------------------------------------
#
##Funcionalidade:Selecionar um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.1.4.3
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica 
#"Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 432 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG2; Tipo: operacional"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG3; Tipo: operacional"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG4; Tipo: operacional"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###P Medici terá uma Unidade Geradora em Suspensão a partir do dia 14/07/14
###Adequar a massa de dados
###Apresentar resultado conforme caso de uso
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
###A massa de dados não está de acordo com o resultado esperado descrito (só existemm dados até 09/07/2014)
###Obs.: Verificar a quantidade após a disponibilização da massa completa de dados
#
#
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.1.5.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"'
#Então eu deveria ver 528 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "30/04/2002"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional" que informa que o equipamento "CI CV.GARABI 2" entrou em suspensão em 01/05/2002)
###ORIENTAÇÃO PARA O PROGRAMADOR SOMENTE PARA O CASO DO MÊS EM APURAÇÃO:
###Zerar a data da última consolidação para todos os eventos do equipamento "CI CV.GARABI 2" com data verificada posterior ou igual a "09/05/2011"
###Incluir a Data de Suspensão  no arquivo de Massa de Dados
###Apresentar as disponibilidades conforme solicitado no Caso de Uso.
###Adequar o Arquivo de Massa de Dados
###O procedimento não atende ao cenário proposto.
###O resultado não corresponde ao esperado
###[Se considerarmos a guia "Supensões", o resultado está correto. A guia "Disponibilidades (Suspensões)" foi removida da planilha "SAGER - Cálculo de Disponibilidades" - Jérôme]
#
##Funcionalidade: Selecionar um mês consolidado/em apuração  e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): Parte das unidades geradoras suspensas
##@CT3.1.6.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica 
#"Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver a mensagem de erro "RS_MENS_031" e crítica 
#"Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2013; Data fim: 19/04/2016"
#E eu deveria ver 432 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG2; Tipo: operacional"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG3; Tipo: operacional"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG4; Tipo: operacional"
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "RSUPME_13P8_UG4")
###ORIENTAÇÃO PARA O PROGRAMADOR (no caso de um período em apuração):
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG1" com data verificada posterior ou igual a "01/07/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG2" com data verificada posterior ou igual a "01/07/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG3" com data verificada posterior ou igual a "01/07/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG4" com data verificada posterior ou igual a "01/07/2014"
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
###A massa de dados disponibilizada não permite conferir o resultado esperado descrito.
###Obs.: Verificar a quantidade após a disponibilização da massa completa de dados
#
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): todas as unidades geradoras da instalação estão suspensas
##@CT3.1.7.1.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015"
#Equipamento: RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
#E eu deveria ver 216 itens na lista "CAMPOS" com valor "Equipamento: RJUSCP_13P8_UG1"
#E eu deveria ver 216 itens na lista "CAMPOS" com valor "Equipamento: RJUSCP_13P8_UG2"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS 
#Equipamento:  RJUSCP_13P8_UG1;Data início: 14/12/2012; Data fim: 08/04/2015
#Equipamento:  RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
###OBS
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE3
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
###Obs.: Pelo que verificamos, a Unidade Geradora UG3 da Usina de Campos não entrou em operação, logo não será incluída nos resultados
###A massa de dados disponibilizada não permite conferir o resultado esperado descrito.
###Obs.: Verificar a quantidade após a disponibilização da massa completa de dados
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): parte das unidades geradoras da instalação estão suspensas
##@CT3.1.7.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG1; Suspensão Data Início: 02/12/2013; Data fim: 19/04/2016"
#E eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG2; Suspensão Data Início:: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 216 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG2; Tipo: operacional"
#E eu deveria ver 648 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG3; Tipo: operacional"
#E eu deveria ver 648 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG4; Tipo: operacional"
#(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "RSUPME_13P8_UG4")
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014      
###Favor incluir o cálculo da disponibilidade de P Medici no arquivo de Massa de Dados
###P Medici terá uma Unidade Geradora em Suspensão a partir do dia 14/07/14
###Adequar a massa de dados
###Apresentar resultado conforme caso de uso
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
###A massa de dados disponibilizada não permite conferir o resultado esperado descrito.
###Obs.: Verificar a quantidade após a disponibilização da massa completa de dados
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para Instalação Internacional (Sucesso)
##@CT3.1.7.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"'
#Então eu deveria ver 264 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "09/05/2002"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional" que informa que o equipamento "CI CV.GARABI 2" entrou em suspensão em 01/05/2002)
###O procedimento não atende ao cenário proposto, está considerando mais de um mês consolidado.
###O resultado não corresponde ao procedimento e o procedimento não condiz com o cenário
###Obs.: Verificar a quantidade após a disponibilização da massa completa de dados
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
##@CT3.1.9.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  TERMONORTE I desativada em 30/01/2013
###OBS
###OK, mas falta incluir a guia unidade geradora 
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
##@CT3.1.9.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "30/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  CI CV.URUGUAIANA desativada em 01/06/2015
#
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
##@CT3.1.11.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  TERMONORTE I desativada em 30/01/2013
###OBS
###OK, mas falta incluir a guia unidade geradora 
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
##@CT3.1.11.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "20/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  CI CV.URUGUAIANA desativada em 01/06/2015
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.1.13.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "22/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: ROTNE1_13P8_UG1; Data Desativação: 31/01/2013; Equipamento: ROTNE1_13P8_UG2; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG3; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG4; Data Desativação: 30/01/2013;" 
#E eu deveria ver 240 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG1; Tipo: operacional"
#E eu deveria ver 216 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG2; Tipo: operacional"
#E eu deveria ver 216 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG3" Tipo: operacional"
#E eu deveria ver 216 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG4; Tipo: operacional"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG1"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG2"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG3"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG4"
#Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: ROTNE1_13P8_UG1 desativado em 31/01/2013; Equipamento: ROTNE1_13P8_UG2 desativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG3 desativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG4 desativado em 30/01/2013;
###OBS
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração  e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.1.13.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: CV.URUGUAIANA; Data Desativação: 01/06/2015;"
#E eu deveria ver 216 itens na lista "CV.URUGUAIANA" com valor "Equipamento: CV.URUGUAIANA; Tipo: Operacional"
#Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: CV.URUGUAIANA desativado 01/06/2015;
###OBS
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando  Unidade Geradora (Sucesso)
##@CT3.1.15.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "26/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: ROTNE1_13P8_UG1; Data Desativação: 31/01/2013; Equipamento: ROTNE1_13P8_UG2; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG3; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG4; Data Desativação: 30/01/2013;" 
#E eu deveria ver 144 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG1; Tipo: operacional"
#E eu deveria ver 120 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG2; Tipo: operacional"
#E eu deveria ver 120 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG3" Tipo: operacional"
#E eu deveria ver 120 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG4; Tipo: operacional"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG1"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG2"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG3"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG4"
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: ROTNE1_13P8_UG1 esativado em 31/01/2013; Equipamento: ROTNE1_13P8_UG2 esativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG3 esativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG4 esativado em 30/01/2013;
###OBS
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.1.15.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "27/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: CV.URUGUAIANA; Data Desativação: 01/06/2015;"
#E eu deveria ver 120 itens na lista "CV.URUGUAIANA" com valor "Equipamento: CV.URUGUAIANA; Tipo: Operacional"
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: CV.URUGUAIANA desativado 01/06/2015;
###OBS 
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.1.17.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/10/2014" para o campo "Data Inicial"
#E eu informo o valor "31/10/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG01; Tipo: operacional"
###OBS
###ORIENTAÇÃO PARA O PROGRAMADOR PARA O CASO 'EM APURAÇÃO':
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG01" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG02" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG03" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_4P1_UG04" com data verificada posterior ou igual a "01/11/2014"
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste.
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.1.17.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2002" para o campo "Data Inicial"
#E eu informo o valor "31/03/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "CI CV.GARABI 2"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2")
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste, não há dados para o mês inteiro.
###Obs.: Vamos utilizar o SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.1.19.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "20/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG01; Tipo: operacional"
#E eu deveria ver 408 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG02; Tipo: operacional"
#E eu deveria ver 168 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG03; Tipo: operacional"
#
#(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "APSAJ_13P8_UG01". Nota: os equipamentos "APSAJ_13P8_UG02" entrou em operação após 14/11/2014, "APSAJ_13P8_UG03" entrou em operação após 31/12/2014 e "APSAJ_4P1_UG04" entrou em operação após 19/12/2014 
#ORIENTAÇÃO PARA O PROGRAMADOR PARA O CASO "EM APURAÇÃO":
#Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG01" com data verificada posterior ou igual a "01/11/2014"
#Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG02" com data verificada posterior ou igual a "01/11/2014"
#Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG03" com data verificada posterior ou igual a "01/11/2014"
#Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_4P1_UG04" com data verificada posterior ou igual a "01/11/2014"
#O resultado esperado não condiz com a massa de teste.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.1.19.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "20/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "CI CV.GARABI 2"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2")
###O resultado esperado não condiz com a massa de teste, não há dados para o período inteiro.
###Obs.: Vamos utilizar o SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.1.21.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "01/09/2009" e "16/09/2009"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "APSAJ_13P8_UG01")
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste.
###Obs.: Vamos utilizar o SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.1.21.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "09/05/2002"
#
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional".
###Nota: os equipamentos "CI CV.GARABI 2" entrou em operação após 01/05/2002)
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.1.23.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "10/09/2009" e "16/09/2009"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "APSAJ_13P8_UG01")
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste.
###Obs.: Vamos utilizar o SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.1.23.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens na lista "CI CV.GARABI 2" com "Data" entre "20/04/2002" e "09/05/2002"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional".
###Nota: os equipamentos "CI CV.GARABI 2" entrou em operação após 01/05/2002)
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
##@CT3.1.26.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "31/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 19/12/2001"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação UT MARIO LAGO  Entrada Em Operação em 19/12/2001.
###OK
###OK, mas entrou em operação em 19/12/2001
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
##@CT3.1.26.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação CI CV.GARABI 2  Entrada Em Operação em 01/05/2002.
#
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
##@CT3.1.28.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "20/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 19/12/2001"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação UT MARIO LAGO  Entrada Em Operação em 19/12/2001.
###OK, mas entrou em operação em 19/12/2001
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
##@CT3.1.28.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "31/01/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação CI CV.GARABI 2  Entrada Em Operação em 01/05/2002.
#
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação 100% suspensa
##@CT3.2.1.1.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RJUSCP_13P8_UG1; Data Início: 14/12/2012; Data fim: 08/04/2015"
#Equipamento: RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
#E eu deveria ver 0 itens na lista "CAMPOS"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS 
#Equipamento:  RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015
#Equipamento:  RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
###OBS
###Nesse caso as tres Unidades Geradoras de Campos estão suspensas no mesmo período solicitado, então o resultado esperado está correto
###Adequar a Massa de Dados
###(O valor de disponibilidade (0) é retornado pelo ODM que considera o período de suspensão da instalação CAMPOS [Jérôme])
###OK, mas incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação parcialmente suspensa
##@CT3.2.1.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG2"
#E eu deveria ver 744 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG3"
#E eu deveria ver 744  itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG4"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "RSUPME_13P8_UG3" e "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS 
###Equipamento:  RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015
###Equipamento:  RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
###Nesse caso as tres Unidades Geradoras de Campos estão suspensas no mesmo período solicitado, então o resultado esperado está correto
###Adequar a Massa de Dados
###(O valor de disponibilidade (0) é retornado pelo ODM que considera o período de suspensão da instalação CAMPOS [Jérôme])
###OK, mas incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação parcialmente suspensa
##@CT3.2.1.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica 
#"Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG2"
#E eu deveria ver 744 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG3"
#E eu deveria ver 744  itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG4"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "RSUPME_13P8_UG3" e "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014      
###Nesse caso apenas uma das Unidades Geradoras está suspensa, então, o sistema deve apresentar a disponibilidade da Unidade Geradora que está em operação comercial. E a mensagem de que a Unidade Geradora XXX está em Suspensão no perído XXXX e o valor de sua disponibilidade é 0 (zero).
###Adequar a Massa de Dados
###A massa de dados não está de acordo com o resultado esperado descrito.
###Incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional
##@CT3.2.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2003" para o campo "Data Inicial"
#E eu informo o valor "31/03/2003" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data início: 01/05/2002"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CI CV.GARABI 2 Data início: 01/05/2002
###A massa de dados não está de acordo com o resultado esperado descrito.
###Incluir na mensagem quais interligações internacionais estão suspensas e os respectivos períodos de suspensão.
###[Jérôme: Período alterado]
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação 100% suspensa
##@CT3.2.3.1.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015"
#Equipamento: RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
#E eu deveria ver 0 itens na lista "CAMPOS"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS 
###Equipamento:  RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015
###Equipamento:  RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
###Adequar a Massa de Dados
###Incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação parcialmente suspensa
##@CT3.2.3.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "20/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG2"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG3"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG4"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "RSUPME_13P8_UG3" e "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014    
###Nesse caso apenas uma das Unidades Geradoras está suspensa, então, o sistema deve apresentar a disponibilidade da Unidade Geradora que está em operação comercial. E a mensagem de que a Unidade Geradora XXX está em Suspensão no perído XXXX e o valor de sua disponibilidade é 0 (zero).
###Adequar a Massa de Dados
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional  
##@CT3.2.3.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "12/03/2003" para o campo "Data Inicial"
#E eu informo o valor "31/03/2003" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data início: 01/05/2002"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CI CV.GARABI 2 Data início: 01/05/2002
###OK, mas Incluir na mensagem quais equipamentos estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.2.4.3
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica 
#"Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 432 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG2; Tipo: Comercial"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG3; Tipo: Comercial"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG4; Tipo: Comercial"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###P Medici terá uma Unidade Geradora em Suspensão a partir do dia 14/07/14
###Adequar a massa de dados
###Apresentar resultado conforme caso de uso
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.2.5.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"'
#Então eu deveria ver 768 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "09/05/2002"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional" que informa que o equipamento "CI CV.GARABI 2" entrou em operação após 01/05/2002)
###ORIENTAÇÃO PARA O PROGRAMADOR SOMENTE PARA O CASO DO MÊS EM APURAÇÃO:
###Zerar a data da última consolidação para todos os eventos do equipamento "CI CV.GARABI 2" com data verificada posterior ou igual a "09/05/2011"
###Incluir a Data de Suspensão  no arquivo de Massa de Dados
###Apresentar as disponibilidades conforme solicitado no Caso de Uso.
###Adequar o Arquivo de Massa de Dados
###O procedimento não atende ao cenário proposto.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): Parte das unidades geradoras suspensas
##@CT3.2.6.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica 
#"Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 432 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG2; Tipo: Comercial"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG3; Tipo: Comercial"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG4; Tipo: Comercial"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  Equipamento:  RSUPME_13P8_UG1; Suspensão: 14/12/2012"
###ORIENTAÇÃO PARA O PROGRAMADOR NO CASO DE UM MÊS EM APURAÇÃO:
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG1" com data verificada posterior ou igual a "01/07/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG2" com data verificada posterior ou igual a "01/07/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG3" com data verificada posterior ou igual a "01/07/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG4" com data verificada posterior ou igual a "01/07/2014"
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): todas as unidades geradoras da instalação estão suspensas
##@CT3.2.7.1.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica  "Equipamento: RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015"
#Equipamento: RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
#E eu deveria ver 216 itens na lista "CAMPOS" com valor "Equipamento: RJUSCP_13P8_UG1"
#E eu deveria ver 216 itens na lista "CAMPOS" com valor "Equipamento: RJUSCP_13P8_UG2"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS 
###Equipamento:  RJUSCP_13P8_UG1;Data início: 14/12/2012; Data fim: 08/04/2015
###Equipamento:  RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE3
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
###Obs.: Pelo que verificamos, a Unidade Geradora UG3 da Usina de Campos não entrou em operação, logo não será incluída nos resultados
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): parte das unidades geradoras da instalação estão suspensas
##@CT3.2.7.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG1; Data Início Suspensão: 02/12/2013; Equipamento: RSUPME_13P8_UG2; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 216 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG2; Tipo: Comercial"
#E eu deveria ver 648 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG3; Tipo: Comercial"
#E eu deveria ver 648 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG4; Tipo: Comercial"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014    
###Favor incluir o cálculo da disponibilidade de P Medici no arquivo de Massa de Dados
###P Medici terá uma Unidade Geradora em Suspensão a partir do dia 14/07/14
###Adequar a massa de dados
###Apresentar resultado conforme caso de uso
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para Instalação Internacional (Sucesso)
##@CT3.2.7.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/05/2011" e "09/06/2011" e valor "Equipamento: CI CV.GARABI 2"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2")
###O procedimento não atende ao cenário proposto, está considerando mais de um mês consolidado.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
##@CT3.2.9.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  TERMONORTE I desativada em 30/01/2013
###OK, mas falta incluir a guia unidade geradora 
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
##@CT3.2.9.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "30/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  CI CV.URUGUAIANA desativada em 01/06/2015
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
##@CT3.2.11.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  TERMONORTE I desativada em 30/01/2013
#OK, mas falta incluir a guia unidade geradora 
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
##@CT3.2.11.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "20/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  CI CV.URUGUAIANA desativada em 01/06/2015
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.2.13.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "22/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: ROTNE1_13P8_UG1; Data Desativação: 31/01/2013; Equipamento: ROTNE1_13P8_UG2; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG3; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG4; Data Desativação: 30/01/2013;" 
#E eu deveria ver 240 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG1; Tipo: Comercial"
#E eu deveria ver 216 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG2; Tipo: Comercial"
#E eu deveria ver 216 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG3" Tipo: Comercial"
#E eu deveria ver 216 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG4; Tipo: Comercial"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG1"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG2"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG3"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG4"
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: ROTNE1_13P8_UG1 desativado em 31/01/2013; Equipamento: ROTNE1_13P8_UG2 desativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG3 desativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG4 desativado em 30/01/2013;
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração  e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.2.13.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: CV.URUGUAIANA; Data Desativação: 01/06/2015;"
#E eu deveria ver 216 itens na lista "CV.URUGUAIANA" com valor "Equipamento: CV.URUGUAIANA; Tipo: Comercial"
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: CV.URUGUAIANA desativado 01/06/2015;
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração  e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.2.13.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: CV.URUGUAIANA; Data Desativação: 01/06/2015;"
#E eu deveria ver 216 itens na lista "CV.URUGUAIANA" com valor "Equipamento: CV.URUGUAIANA; Tipo: Comercial"
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: CV.URUGUAIANA desativado 01/06/2015;
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando  Unidade Geradora (Sucesso)
##@CT3.2.15.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "26/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: ROTNE1_13P8_UG1; Data Desativação: 31/01/2013; Equipamento: ROTNE1_13P8_UG2; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG3; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG4; Data Desativação: 30/01/2013;" 
#E eu deveria ver 144 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG1; Tipo: Comercial"
#E eu deveria ver 120 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG2; Tipo: Comercial"
#E eu deveria ver 120 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG3" Tipo: Comercial"
#E eu deveria ver 120 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG4; Tipo: Comercial"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG1"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG2"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG3"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG4"
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: ROTNE1_13P8_UG1 esativado em 31/01/2013; Equipamento: ROTNE1_13P8_UG2 esativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG3 esativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG4 esativado em 30/01/2013;
###O procedimento não atende ao cenário proposto, com relação às datas.
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.2.15.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "27/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: CV.URUGUAIANA; Data Desativação: 01/06/2015;"
#E eu deveria ver 120 itens na lista "CV.URUGUAIANA" com valor "Equipamento: CV.URUGUAIANA; Tipo: Comercial"
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: CV.URUGUAIANA desativado 01/06/2015;
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.2.17.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/10/2014" para o campo "Data Inicial"
#E eu informo o valor "31/10/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG01; Tipo: Comercial"
###OBS
###ORIENTAÇÃO PARA O PROGRAMADOR NO CASO DE UM MÊS EM APURAÇÃO:
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG01" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG02" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG03" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_4P1_UG04" com data verificada posterior ou igual a "01/11/2014"
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste.
#
#
# ---------------------------------------------------- OK ---------------------------------------------------- 
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.2.17.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "31/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "CI CV.GARABI 2"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2")
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste, não há dados para o mês inteiro.
###Obs.: Vamos utilizar o SAGER para gerar as indisponibilidades para a execução dos testes
#
# ---------------------------------------------------- OK ---------------------------------------------------- 
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.2.19.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "20/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG01; Tipo: Comercial"
#E eu deveria ver 408 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG02; Tipo: Comercial"
#E eu deveria ver 168 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG03; Tipo: Comercial"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "APSAJ_13P8_UG01". Nota: os equipamentos "APSAJ_13P8_UG02" entrou em operação após 14/11/2014, "APSAJ_13P8_UG03" entrou em operação após 31/12/2014 e "APSAJ_4P1_UG04" entrou em operação após 19/12/2014 
###ORIENTAÇÃO PARA O PROGRAMADOR PARA O CASO "EM APURAÇÃO":
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG01" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG02" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG03" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_4P1_UG04" com data verificada posterior ou igual a "01/11/2014"
###O resultado esperado não condiz com a massa de teste.
#
# ---------------------------------------------------- OK ---------------------------------------------------- 
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.2.19.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "20/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "CI CV.GARABI 2"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2")
###O resultado esperado não condiz com a massa de teste, não há dados para o período inteiro.
###Obs.: Vamos utilizar o SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.2.21.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "01/09/2009" e "16/09/2009"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "APSAJ_13P8_UG01")
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.2.21.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "09/05/2002"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional".
###Nota: os equipamentos "CI CV.GARABI 2" entrou em operação após 01/05/2002)
###O procedimento não atende ao cenário proposto, com relação às datas.
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.2.23.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "10/09/2009" e "16/09/2009"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "APSAJ_13P8_UG01")
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste.
#
#
# ---------------------------------------------------- OK ---------------------------------------------------- 
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.2.23.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens na lista "CI CV.GARABI 2" com "Data" entre "20/04/2002" e "09/05/2002"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional".
###Nota: os equipamentos "CI CV.GARABI 2" entrou em operação após 01/05/2002)
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
##@CT3.2.26.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "31/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 19/12/2001"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação UT MARIO LAGO  Entrada Em Operação em 19/12/2001.
###OK, mas entrou em operação em 19/12/2001
#
#
# ---------------------------------------------------- OK ---------------------------------------------------- 
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
##@CT3.2.26.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação CI CV.GARABI 2  Entrada Em Operação em 01/05/2002.
#
# ---------------------------------------------------- OK ---------------------------------------------------- 
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
##@CT3.2.28.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "20/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 19/12/2001"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação UT MARIO LAGO  Entrada Em Operação em 19/12/2001.
###OK, mas entrou em operação em 19/12/2001
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
##@CT3.2.28.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "31/01/2001" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação CI CV.GARABI 2  Entrada Em Operação em 01/05/2002.
#
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação 100% suspensa
##@CT3.3.1.1.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RJUSCP_13P8_UG1; Data Início: 14/12/2012; Data fim: 08/04/2015"
#Equipamento: RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
#E eu deveria ver 0 itens na lista "CAMPOS"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS 
###Equipamento:  RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015
###Equipamento:  RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
###Nesse caso as tres Unidades Geradoras de Campos estão suspensas no mesmo período solicitado, então o resultado esperado está correto
###Adequar a Massa de Dados
###(O valor de disponibilidade (0) é retornado pelo ODM que considera o período de suspensão da instalação CAMPOS [Jérôme])
###Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
###OK, mas incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação parcialmente suspensa
##@CT3.3.1.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG2"
#E eu deveria ver 744 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG3"
#E eu deveria ver 744  itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG4"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "RSUPME_13P8_UG3" e "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014 
###Nesse caso apenas uma das Unidades Geradoras está suspensa, então, o sistema deve apresentar a disponibilidade da Unidade Geradora que está em operação eletromecânica. E a mensagem de que a Unidade Geradora XXX está em Suspensão no perído XXXX e o valor de sua disponibilidade é 0 (zero).
###Adequar a Massa de Dados
###A massa de dados não está de acordo com o resultado esperado descrito.
###Incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional  
##@CT3.3.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2003" para o campo "Data Inicial"
#E eu informo o valor "31/03/2003" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data início: 01/05/2002"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CI CV.GARABI 2 Data início: 01/05/2002
###A massa de dados não está de acordo com o resultado esperado descrito.
###Incluir na mensagem quais interligações internacionais estão suspensas e os respectivos períodos de suspensão.
#
#
# ---------------------------------------------------- OK ---------------------------------------------------- 
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação 100% suspensa
##@CT3.3.3.1.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015"
#Equipamento: RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
#E eu deveria ver 0 itens na lista "CAMPOS"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS 
###Equipamento:  RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015
###Equipamento:  RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
###Adequar a Massa de Dados
###Incluir na mensagem quais unidades geradoras estão suspensas e os respectivos períodos de suspensão.
#
# 
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora  : Instalação parcialmente suspensa
##@CT3.3.3.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "20/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG2"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG3"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG4"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "RSUPME_13P8_UG3" e "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014 
###Nesse caso apenas uma das Unidades Geradoras está suspensa, então, o sistema deve apresentar a disponibilidade da Unidade Geradora que está em operação comercial. E a mensagem de que a Unidade Geradora XXX está em Suspensão no perído XXXX e o valor de sua disponibilidade é 0 (zero).
###Adequar a Massa de Dados
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional  
##@CT3.3.3.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "12/03/2003" para o campo "Data Inicial"
#E eu informo o valor "31/03/2003" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data início: 01/05/2002"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CI CV.GARABI 2 Data início: 01/05/2002
###OK, mas Incluir na mensagem quais equipamentos estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.3.4.3
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica 
#"Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 432 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG2; Tipo: Eletromecânica"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG3; Tipo: Eletromecânica"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG4; Tipo: Eletromecânica"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###P Medici terá uma Unidade Geradora em Suspensão a partir do dia 14/07/14
###Adequar a massa de dados
###Apresentar resultado conforme caso de uso
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.3.5.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"'
#Então eu deveria ver 768 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "09/05/2002"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional" que informa que o equipamento "CI CV.GARABI 2" entrou em operação após 01/05/2002)
###ORIENTAÇÃO PARA O PROGRAMADOR SOMENTE PARA O CASO DO MÊS EM APURAÇÃO:
###Zerar a data da última consolidação para todos os eventos do equipamento "CI CV.GARABI 2" com data verificada posterior ou igual a "09/05/2011"
###Incluir a Data de Suspensão  no arquivo de Massa de Dados
###Apresentar as disponibilidades conforme solicitado no Caso de Uso.
###Adequar o Arquivo de Massa de Dados
###O procedimento não atende ao cenário proposto.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): Parte das unidades geradoras suspensas
##@CT3.3.6.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica 
#"Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 432 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG2; Tipo: Eletromecânica"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG3; Tipo: Eletromecânica"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG4; Tipo: Eletromecânica"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "RSUPME_13P8_UG4")
###ORIENTAÇÃO PARA O PROGRAMADOR PARA UM MÊS EM APURAÇÃO:
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG1" com data verificada posterior ou igual a "01/07/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG2" com data verificada posterior ou igual a "01/07/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG3" com data verificada posterior ou igual a "01/07/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "RSUPME_13P8_UG4" com data verificada posterior ou igual a "01/07/2014"
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): todas as unidades geradoras da instalação estão suspensas
##@CT3.3.7.1.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RJUSCP_13P8_UG1; Data início: 14/12/2012; Data fim: 08/04/2015"
#Equipamento: RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
#E eu deveria ver 216 itens na lista "CAMPOS" com valor "Equipamento: RJUSCP_13P8_UG1"
#E eu deveria ver 216 itens na lista "CAMPOS" com valor "Equipamento: RJUSCP_13P8_UG2"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação CAMPOS 
###Equipamento:  RJUSCP_13P8_UG1;Data início: 14/12/2012; Data fim: 08/04/2015
###Equipamento:  RJUSCP_13P8_UG2; Data início: 14/12/2012; Data fim: 08/04/2015
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE3
###Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
#
#
# 
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): parte das unidades geradoras da instalação estão suspensas
##@CT3.3.7.1.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: RSUPME_13P8_UG1; Data Início Suspensão: 02/12/2013; Equipamento: RSUPME_13P8_UG2; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: RSUPME_13P8_UG1"
#E eu deveria ver 216 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG2; Tipo: Eletromecânica"
#E eu deveria ver 648 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG3; Tipo: Eletromecânica"
#E eu deveria ver 648 itens na lista "P.MEDICI" com o valor "Equipamento: RSUPME_13P8_UG4; Tipo: Eletromecânica"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "RSUPME_13P8_UG4")
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam suspensos.  "Equipamento: RSUPME_13P8_UG1; Data início: 02/12/2013; Data fim: 19/04/2016
###Equipamento: RSUPME_13P8_UG2; Data início: 14/07/2014   
###Favor incluir o cálculo da disponibilidade de P Medici no arquivo de Massa de Dados
###P Medici terá uma Unidade Geradora em Suspensão a partir do dia 14/07/14
###Adequar a massa de dados
###Apresentar resultado conforme caso de uso
###A massa de dados não está de acordo com o resultado esperado descrito.
###Falta descrição UGE4
##Incluir na mensagem quais unidade geradora estão suspensas e os respectivos períodos de suspensão.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para Instalação Internacional (Sucesso)
##@CT3.3.7.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/05/2011" e "09/06/2011" e valor "Equipamento: CI CV.GARABI 2"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2")
###O procedimento não atende ao cenário proposto, está considerando mais de um mês consolidado.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
##@CT3.3.9.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  TERMONORTE I desativada em 30/01/2013
#OK, mas falta incluir a guia unidade geradora 
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
##@CT3.3.9.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "30/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  CI CV.URUGUAIANA desativada em 01/06/2015
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
##@CT3.3.11.1 
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas estão desativadas. Instalação:  TERMONORTE I desativada em 30/01/2013
###OK, mas falta incluir a guia unidade geradora 
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
##@CT3.3.11.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "20/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#
#
#              BBBBBBBBBBBBBBBBBBBBBUUUUUUUUUUUUUUUUUUUUGGGGGGGGGGGGGGGGGGG
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.3.13.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "22/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: ROTNE1_13P8_UG1; Data Desativação: 31/01/2013; Equipamento: ROTNE1_13P8_UG2; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG3; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG4; Data Desativação: 30/01/2013;" 
#E eu deveria ver 240 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG1; Tipo: Eletromecânica"
#E eu deveria ver 216 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG2; Tipo: Eletromecânica"
#E eu deveria ver 216 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG3" Tipo: Eletromecânica"
#E eu deveria ver 216 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG4; Tipo: Eletromecânica"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG1"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG2"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG3"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG4"
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: ROTNE1_13P8_UG1 desativado em 31/01/2013; Equipamento: ROTNE1_13P8_UG2 desativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG3 desativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG4 desativado em 30/01/2013;
###O procedimento não atende ao cenário proposto, com relação às datas.
#
##Funcionalidade: Selecionar um mês consolidado/em apuração  e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.3.13.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: CV.URUGUAIANA; Data Desativação: 01/06/2015;"
#E eu deveria ver 216 itens na lista "CV.URUGUAIANA" com valor "Equipamento: CV.URUGUAIANA; Tipo: Eletromecânica"
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: CV.URUGUAIANA desativado 01/06/2015;
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando  Unidade Geradora (Sucesso)
##@CT3.3.15.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "26/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: ROTNE1_13P8_UG1; Data Desativação: 31/01/2013; Equipamento: ROTNE1_13P8_UG2; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG3; Data Desativação: 30/01/2013; Equipamento: ROTNE1_13P8_UG4; Data Desativação: 30/01/2013;" 
#E eu deveria ver 144 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG1; Tipo: Eletromecânica"
#E eu deveria ver 120 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG2; Tipo: Eletromecânica"
#E eu deveria ver 120 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG3" Tipo: Eletromecânica"
#E eu deveria ver 120 itens na lista "TERMONOTE I" com o valor "Equipamento: ROTNE1_13P8_UG4; Tipo: Eletromecânica"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG1"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG2"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG3"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: ROTNE1_13P8_UG4"
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: ROTNE1_13P8_UG1 esativado em 31/01/2013; Equipamento: ROTNE1_13P8_UG2 esativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG3 esativado em 30/01/2013; Equipamento: ROTNE1_13P8_UG4 esativado em 30/01/2013;
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.3.15.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "27/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_030" e crítica "Equipamento: CV.URUGUAIANA; Data Desativação: 01/06/2015;"
#E eu deveria ver 120 itens na lista "CV.URUGUAIANA" com valor "Equipamento: CV.URUGUAIANA; Tipo: Eletromecânica"
###OBS
###Mensagem: Não há dados no período informado para os seguintes equipamentos pois os mesmos estavam desativados. Equipamento: CV.URUGUAIANA desativado 01/06/2015;
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.3.17.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/10/2014" para o campo "Data Inicial"
#E eu informo o valor "31/10/2014" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG01; Tipo: Eletromecânica"
###OBS
###ORIENTAÇÃO PARA O PROGRAMADOR PARA O CASO 'EM APURAÇÃO':
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG01" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG02" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG03" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_4P1_UG04" com data verificada posterior ou igual a "01/11/2014"
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.3.17.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "31/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "CI CV.GARABI 2"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2")
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste, não há dados para o mês inteiro.
###Obs.: Vamos utilizar o SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.3.19.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "20/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG01; Tipo: Eletromecânica"
#E eu deveria ver 408 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG02; Tipo: Eletromecânica"
#E eu deveria ver 168 itens na lista "STO.ANT.JARI" com o valor "Equipamento: APSAJ_13P8_UG03; Tipo: Eletromecânica"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "APSAJ_13P8_UG01". Nota: os equipamentos "APSAJ_13P8_UG02" entrou em operação após 14/11/2014, "APSAJ_13P8_UG03" entrou em operação após 31/12/2014 e "APSAJ_4P1_UG04" entrou em operação após 19/12/2014 
###ORIENTAÇÃO PARA O PROGRAMADOR PARA O CASO "EM APURAÇÃO":
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG01" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG02" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_13P8_UG03" com data verificada posterior ou igual a "01/11/2014"
###Zerar a data da última consolidação para todos os eventos do equipamento "APSAJ_4P1_UG04" com data verificada posterior ou igual a "01/11/2014"
###O resultado esperado não condiz com a massa de teste.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.3.19.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "20/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "CI CV.GARABI 2"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2")
###O resultado esperado não condiz com a massa de teste, não há dados para o período inteiro.
###Obs.: Vamos utilizar o SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.3.21.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "01/09/2009" e "16/09/2009"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "APSAJ_13P8_UG01")
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O resultado esperado não condiz com a massa de teste.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.3.21.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "09/05/2002"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional".
###Nota: os equipamentos "CI CV.GARABI 2" entrou em operação após 01/05/2002)
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
##@CT3.3.23.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "10/09/2009" e "16/09/2009"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "APSAJ_13P8_UG01")
###O resultado esperado não condiz com a massa de teste.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
##@CT3.3.23.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 216 itens na lista "CI CV.GARABI 2" com "Data" entre "20/04/2002" e "09/05/2002"
###OBS
###(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" e a guia "Interligação Internacional".
###Nota: os equipamentos "CI CV.GARABI 2" entrou em operação após 01/05/2002)
###FAVOR INFORMAR OS CÁLCULOS CONFORME ACORDADO
###O procedimento não atende ao cenário proposto, com relação às datas.
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
##@CT3.3.26.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "31/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 19/12/2001"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação UT MARIO LAGO  Entrada Em Operação em 19/12/2001.
###OK, mas entrou em operação em 19/12/2001
#
#
##Funcionalidade: Selecionar um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
##@CT3.3.26.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação CI CV.GARABI 2  Entrada Em Operação em 01/05/2002.
#
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
##@CT3.3.28.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "20/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 19/12/2001"
###OBS
###Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação UT MARIO LAGO  Entrada Em Operação em 19/12/2001.
###OK, mas entrou em operação em 19/12/2001
#
##Funcionalidade: Selecionar parte de um mês consolidado/em apuraçãoe calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
##@CT3.3.28.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "31/01/2001" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#Mensagem: Não há dados no período informado para as seguintes instalações pois as mesmas não estavam em operação comercial. Instalação CI CV.GARABI 2  Entrada Em Operação em 01/05/2002.
#
#
##Funcionalidade: Exibição da disponibilidade Comercial
##@CT3.4.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "05/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto a guia "U.SOBRADINHO"
#E eu aperto o botão "Gerar Gráfico"
#Então eu deveria ver o modal "Gerar Gráfico" com valor "Gráfico: Linhas";
#E eu deveria ver o moda "Gerar Gráfico" com o valor "Usina: U.SOBRADINHO"
#E eu deveria ver o modal "Gerar Gráfico" com o eixo X com os valores "Data" entre "01/07/2010" e "05/07/2010"
#E eu deveria ver o modal "Gerar Gráfico" com o eixo Y com os valor "Tipo: Comercial"
###OBS
###O gráfico gerado será um gráfico de Linhas o eixo Y conterá a disponibilidade Comercial, cuja unidade MW/h selecionada e o eixo X conterá o período informado, cuja unidade será dia e hora.
###O título do gráfico será "Disponibilidade Intergralizada por hora - Comercial"
###ORIENTAÇÃO PARA O PROGRAMADOR:
###Zerar a base de eventos de mudança de estado operativo, pois não testamos o cálculo da disponibilidade aqui
###Explicitar como será o gráfico com seus eixos e a massa de dados que será externalizada no mesmo, bem como os recursos que o gráfico terá.
###Exibição de um tipo de disponibilidade e de todos os tipos de disponibilidade em um mesmo gráfico, etc.
###Obs.: Um exemplo de gráfico poderá ser gerado através da utilização dos dados do SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Exibição da disponibilidade operacional
##@CT3.4.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto a guia "U.SOBRADINHO"
#E eu aperto o botão "Gerar Gráfico"
#Então eu deveria ver o modal "Gerar Gráfico" com valor "Gráfico: Linhas";
#E eu deveria ver o moda "Gerar Gráfico" com o valor "Usina: U.SOBRADINHO"
#E eu deveria ver o modal "Gerar Gráfico" com o eixo X com os valores "Data" entre "01/07/2010" e "31/07/2010"
#E eu deveria ver o modal "Gerar Gráfico" com o eixo Y com os valor "Tipo: Operacional"
###OBS
###O gráfico gerado será um gráfico de Linhas o eixo Y conterá a disponibilidade Operacional,  cuja unidade MW/h selecionada e o eixo X conterá o período informado, cuja unidade será dia e hora.
###O título do gráfico será "Disponibilidade Intergralizada por hora - Operacional"
###ORIENTAÇÃO PARA O PROGRAMADOR:
###Zerar a base de eventos de mudança de estado operativo, pois não testamos o cálculo da disponibilidade aqui
###Explicitar como será o gráfico com seus eixos e a massa de dados que será externalizada no mesmo, bem como os recursos que o gráfico terá.
###Exibição de um tipo de disponibilidade e de todos os tipos de disponibilidade em um mesmo gráfico, etc.
###Obs.: Um exemplo de gráfico poderá ser gerado através da utilização dos dados do SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Exibição da disponibilidade Eletromecânica
##@CT3.4.3
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto a guia "U.SOBRADINHO"
#E eu aperto o botão "Gerar Gráfico"
#Então eu deveria ver o modal "Gerar Gráfico" com valor "Gráfico: Linhas";
#E eu deveria ver o moda "Gerar Gráfico" com o valor "Usina: U.SOBRADINHO"
#E eu deveria ver o modal "Gerar Gráfico" com o eixo X com os valores "Data" entre "01/07/2010" e "31/07/2010"
#E eu deveria ver o modal "Gerar Gráfico" com o eixo Y com os valor "Tipo: Eletromecânica"
###OBS
###O gráfico gerado será um gráfico de Linhas o eixo Y conterá a disponibilidade Eletromecânica, cuja unidade MW/h selecionada e o eixo X conterá o período informado, cuja unidade será dia e hora.
###O título do gráfico será "Disponibilidade Intergralizada por hora - Eletromecânica"
###ORIENTAÇÃO PARA O PROGRAMADOR:
###Zerar a base de eventos de mudança de estado operativo, pois não testamos o cálculo da disponibilidade aqui
###Explicitar como será o gráfico com seus eixos e a massa de dados que será externalizada no mesmo, bem como os recursos que o gráfico terá.
###Exibição de um tipo de disponibilidade e de todos os tipos de disponibilidade em um mesmo gráfico, etc.
###Obs.: Um exemplo de gráfico poderá ser gerado através da utilização dos dados do SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Utilizando a opção de mudança de Unidade Geradora
##@CT3.4.4
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "10/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto a guia "U.SOBRADINHO"
#E eu aperto o botão "Gerar Gráfico"
#E eu seleciono o valor "Equipamento: BAUSB-01G2-02" para o campo "Unidade Geradora" no modal "Gerar Gráfico"
#Então eu deveria ver o modal "Gerar Gráfico" com valor "Gráfico: Linhas";
#E eu deveria ver o modal "Gerar Gráfico" com o valor "Equpamento: BAUSB-01G2-02"
#E eu deveria ver o modal "Gerar Gráfico" com o eixo X com os valores "Data" entre "01/07/2010" e "10/07/2010"
#E eu deveria ver o modal "Gerar Gráfico" com o eixo Y com os valor "Tipo: Eletromecânica"
###OBS
###O gráfico gerado será um gráfico de Linhas o eixo Y conterá a disponibilidade Eletromecânica, cuja unidade MW/h selecionada e o eixo X conterá o período informado, cuja unidade será dia e hora.
###O título do gráfico será "Disponibilidade Intergralizada por hora - Eletromecânica"
###ORIENTAÇÃO PARA O PROGRAMADOR:
###Zerar a base de eventos de mudança de estado operativo, pois não testamos o cálculo da disponibilidade aqui
###Explicitar como será o gráfico com seus eixos e a massa de dados que será externalizada no mesmo, bem como os recursos que o gráfico terá.
###Exibição de um tipo de disponibilidade e de todos os tipos de disponibilidade em um mesmo gráfico, etc.
###Obs.: Um exemplo de gráfico poderá ser gerado através da utilização dos dados do SAGER para gerar as indisponibilidades para a execução dos testes
#
#
##Funcionalidade: Solicitar exportação para Excel quando não houver informações de Disponibilidade exibidas em tela
##@CT3.5.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto o botão "Exportar Excel"
#Então eu deveria ver a mensagem de erro "RS_MENS_015"
###OBS
###Mensagem: Não existem dados para os filtros selecionados.
###ORIENTAÇÃO PARA O PROGRAMADOR:
###Zerar a base de eventos de mudança de estado operativo, pois não testamos o cálculo da disponibilidade aqui
#
#
##Funcionalidade:Solicitar exportação para Excel quando houver informações de Disponibilidade exibidas em tela
##@CT3.5.2
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto o botão "Exportar Excel"
#Então eu deveria poder baixar um arquivo Excel "Disponibilidades"
###OBS
###VERIFICAÇÃO MANUAL:
###O arquivo gerado conterá uma guia com as informações da Usina "U.SOBRADINHO" com as informações da disponibilidade de 01/07/2010 à 31/07/2010 por hora, com 744 linhas. As seguintes colunas serão disponibilizadas: Instalação; Data/Hora; Tipo de Disponibilidade Eletromecânica (E);  Valor.
###E o arquivo gerado conterá uma guia com as informações da Usina "CAMPOS" com as informações da disponibilidade de 01/07/2010 à 31/07/2010 por hora, com 744 linhas.
###As seguintes colunas serão disponibilizadas: Instalação; PARA CADA UNIDADE GERADORA AS SEGUINTES COLUNAS SERÃO DISPONIBILIZADAS: Unidade Geradora; Data/Hora; Tipo de Disponibilidade Eletromecânica (E);  Valor.
###Explicitar como serão apresentados os dados no excel expotado, a massa de dados utilizada e o recurso gráfico exportado..
###Exportação de um tipo de disponibilidade e de todos os tipos de disponibilidade em um mesmo Excel, etc.
###Obs.: Será possível escolher mais de um Tipo de Disponibilidade para a geração da planilha em Excel
#
#
##Funcionalidade: Consultar um período superior a 1 mês
##@CT3.6.1
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/09/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de sucesso de código "RS_MENS_014"
#E eu deveria poder baixar um arquivo Excel "Disponibilidades"
###OBS
###Mensagem: A Pesquisa retornou muitos dados inviabilizando a exibição em tela. Os dados geraram um arquivo em Excel disponível para Download.
###ORIENTAÇÃO PARA PROGRAMADOR:
###Duplicar as disponibilidades eletromecânicas integralizadas para Julho (completar o mês), Agosto e Setembro 2010 para os equipamentos "BAUSB_13P8_UG1" e "BAUSB_13P8_UG2"
###VERIFICAÇÃO MANUAL:
###o arquivo gerado conterá uma guia com as informações da Usina "U.SOBRADINHO" com as informações da disponibilidade de 01/07/2010 à 31/07/2010, com 744 linhas. As seguintes colunas serão disponibilizadas: Instalação; PARA CADA UNIDADE GERADORA AS SEGUINTES COLUNAS SERÃO DISPONIBILIZADAS: Unidade Geradora; Data/Hora; Tipo de Disponibilidade Eletromecânica (E);  Valor.
###Explicitar como serão apresentados os dados no excel expotado, a massa de dados utilizada e o recurso gráfico exportado..
###Exportação de um tipo de disponibilidade e de todos os tipos de disponibilidade em um mesmo Excel, etc.
###Obs.: Será possível escolher mais de um Tipo de Disponibilidade para a geração da planilha em Excel
#
#
##Funcionalidade: Consultar período igual ou inferior a 1 mês
##@CT3.6.2 
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "10/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria poder baixar um arquivo Excel "Disponibilidades"
###OBS
###VERIFICAÇÃO MANUAL:
###O arquivo gerado conterá uma guia com as informações da Usina "U.SOBRADINHO" com as informações da disponibilidade de 01/07/2010 à 10/07/2010 por hora, com 240 linhas.
###As seguintes colunas serão disponibilizadas: Instalação; PARA CADA UNIDADE GERADORA AS SEGUINTES COLUNAS SERÃO DISPONIBILIZADAS: Unidade Geradora; Data/Hora; Tipo de Disponibilidade Eletromecânica (E);  Valor.
###O resultado não está correto.
###Explicitar como será o gráfico com seus eixos e a massa de dados que será externalizada no mesmo, bem como os recursos que o gráfico terá.
###Exibição de um tipo de disponibilidade e de todos os tipos de disponibilidade em um mesmo gráfico, etc.
#
#
##Funcionalidade: Consultar mais de 25 colunas de apresentação de disponibilidades
##@CT3.6.3
##Cenário: Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "10/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UHE JIRAU" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de sucesso de código "RS_MENS_014"
#E eu deveria poder baixar um arquivo Excel "Disponibilidades"
###OBS
###ORIENTAÇÃO PARA PROGRAMADOR:
###Duplicar as disponibilidades eletromecânicas integralizadas para Julho (completar o mês) do equipamento "BAUSB_13P8_UG1" para todos os equipamentos de "UHE JIRAU"
###VERIFICAÇÃO MANUAL:
###No arquivo gerado, a guia "UHE JIRAU" contém as disponibilidades eletromecânicas integralizadas de Julho 2010 para todos os equipamentos de "UHE JIRAU"
###As seguintes colunas serão disponibilizadas: Instalação; PARA CADA UNIDADE GERADORA AS SEGUINTES COLUNAS SERÃO DISPONIBILIZADAS: Unidade Geradora; Data/Hora; Tipo de Disponibilidade Eletromecânica (E);  Valor.
###Explicitar como serão apresentados os dados no excel expotado, a massa de dados utilizada e o recurso gráfico exportado..
###Exportação de um tipo de disponibilidade e de todos os tipos de disponibilidade em um mesmo Excel, etc.
###Obs.: A Usina UHE JIRAU tem 42 unidades geradoras
#
#
##Funcionalidade: Consultar 25 colunas de disponibilidades ou menos
##@CT3.6.4
##Cenário:  Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "10/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 240 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG1; Tipo Disponibilidade: Eletromecânica"
#E eu deveria ver 240 itens no grid "Disponibilidades" com valor "Equipamento: RJUSCP_13P8_UG2; Tipo Disponibilidade: Eletromecânica"
#Então eu deveria poder baixar um arquivo Excel "Disponibilidades"
###OBS
###VERIFICAÇÃO MANUAL:
###O arquivo gerado conterá uma guia com as informações da Usina "U.SOBRADINHO" com as informações da disponibilidade de 01/07/2010 à 10/07/2010 por hora, com 240 linhas. As seguintes colunas serão disponibilizadas: Instalação; PARA CADA UNIDADE GERADORA AS SEGUINTES COLUNAS SERÃO DISPONIBILIZADAS: Unidade Geradora; Data/Hora; Tipo de Disponibilidade Eletromecânica (E);  Valor.
###O resultado não está correto.
###Explicitar como será o gráfico com seus eixos e a massa de dados que será externalizada no mesmo, bem como os recursos que o gráfico terá.
###Exibição de um tipo de disponibilidade e de todos os tipos de disponibilidade em um mesmo gráfico, etc.
#
