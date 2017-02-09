## language: pt
#Funcionalidade: Consultar disponibilidade operacional para uma instalação
#@CT3.1.1.1.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.1.1.1.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação parcialmente suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  3 RS"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.1.1.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero) 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.1.2.1.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): todas as unidades geradoras estão suspensas no período do cálculo
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.1.2.1.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): algumas unidades geradoras estão suspensas no período do cálculo
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: operacional; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.1.2.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.1.3.1.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.1.3.1.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação parcialmente suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "20/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: operacional; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.1.3.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "12/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.1.4.1.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.1.4.1.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação parcialmente suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "20/08/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: operacional; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.1.4.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "12/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.1.4.3
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS; Tipo: operacional; Valor: 0"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: operacional; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 01/07/2014 e 09/07/2014)
#@CT3.1.5.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.1.6.1.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): Todas as unidades geradoras suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então todo item na lista "CAMPOS" com "Data" entre "01/12/2012" e "13/12/2012" deveria ter o valor "Tipo: operacional; Valor: 0"
#E eu deveria ver 0 itens na lista "CAMPOS" com "Data" entre "14/12/2012" e "31/12/2012"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   13 MW USI CAMPOS                1 RJ"  e "UG   13 MW USI CAMPOS                2 RJ" entre 01/12/2012 e 09/12/2012. A suspensão da instalação "CAMPOS" começa em 14/12/2012)
#@CT3.1.6.1.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): Parte das unidades geradoras suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS; Tipo: operacional; Valor: 0"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: operacional; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 01/07/2014 e 09/07/2014)
#@CT3.1.6.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.1.7.1.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): todas as unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então todo item na lista "CAMPOS" com "Data" entre "05/12/2012" e "13/12/2012" deveria ter o valor "Tipo: operacional; Valor: 0"
#E eu deveria ver 0 itens na lista "CAMPOS" com "Data" entre "14/12/2012" e "31/12/2012"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   13 MW USI CAMPOS                1 RJ"  e "UG   13 MW USI CAMPOS                2 RJ" entre 05/12/2012 e 09/12/2012. A suspensão da instalação "CAMPOS" começa em 14/12/2012)
#@CT3.1.7.1.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): parte das unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS; Tipo: operacional; Valor: 0"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: operacional; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 05/07/2014 e 09/07/2014)
#@CT3.1.7.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.1.8.1.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): todas as unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 item na lista "CAMPOS" com "Data" entre "14/12/2012" e "31/12/2012"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   13 MW USI CAMPOS                1 RJ"  e "UG   13 MW USI CAMPOS                2 RJ" entre 05/12/2012 e 09/12/2012. A suspensão da instalação "CAMPOS" começa em 14/12/2012)
#@CT3.1.8.1.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): parte das unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 05/07/2014 e 09/07/2014)
#@CT3.1.8.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em suspensão em parte do período solicitado para Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.1.9.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.1.9.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "30/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#@CT3.1.10.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.1.10.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "30/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
##
#@CT3.1.11.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.1.11.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "20/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#@CT3.1.12.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.1.12.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "20/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#@CT3.1.13.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "22/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 22/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 22/01/2013 e 29/01/2013)
#@CT3.1.13.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.URUGUAIANA" entre 23/05/2015 e 31/05/2015)
#@CT3.1.14.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "22/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 22/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 22/01/2013 e 29/01/2013)
#@CT3.1.14.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.URUGUAIANA" entre 23/05/2015 e 31/05/2015)
#@CT3.1.15.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando  Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "26/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 26/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 26/01/2013 e 29/01/2013)
#@CT3.1.15.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "27/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.URUGUAIANA" entre 27/05/2015 e 31/05/2015)
#@CT3.1.16.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "26/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 26/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 26/01/2013 e 29/01/2013)
#@CT3.1.16.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "27/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.URUGUAIANA" entre 27/05/2015 e 31/05/2015)
#@CT3.1.17.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "30/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.1.17.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "31/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "CI CV.GARABI 1"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.1.18.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "30/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.1.18.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "31/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "CI CV.GARABI 2"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.1.19.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "20/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.1.19.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "20/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "CI CV.GARABI 2"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.1.20.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "20/11/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.1.20.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "20/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "CI CV.GARABI 1"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.1.21.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "01/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.1.21.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.1.22.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "01/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.1.22.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.1.23.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "10/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.1.23.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.1.24.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "10/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.1.24.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade operacional do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.1.25.1
#Cenário: 
##
##
#@CT3.1.25.2
#Cenário: 
##
##
#@CT3.1.26.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "31/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.1.26.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.1.27.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "31/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.1.27.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.1.28.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "20/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.1.28.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "31/01/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.1.29.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "20/05/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.1.29.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade operacional para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.1.30.1
#Cenário: 
##
##
#@CT3.1.30.2
#Cenário: 
##
##
##Funcionalidade: Consultar disponibilidade comercial para uma instalação
#@CT3.2.1.1.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.2.1.1.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação parcialmente suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  3 RS"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.2.1.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero) 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.2.2.1.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em suspensão por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): todas as unidades geradoras estão suspensas no período do cálculo
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.2.2.1.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): algumas unidades geradoras estão suspensas no período do cálculo
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: comercial; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.2.2.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.2.3.1.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.2.3.1.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação parcialmente suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "20/08/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: comercial; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.2.3.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "12/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.2.4.1.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.2.4.1.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação parcialmente suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "20/08/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: comercial; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.2.4.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "12/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.2.4.3
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS; Tipo: comercial; Valor: 0"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: comercial; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 01/07/2014 e 09/07/2014)
#@CT3.2.5.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.2.6.1.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): Todas as unidades geradoras suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então todo item na lista "CAMPOS" com "Data" entre "01/12/2012" e "13/12/2012" deveria ter o valor "Tipo: comercial; Valor: 0"
#E eu deveria ver 0 itens na lista "CAMPOS" com "Data" entre "14/12/2012" e "31/12/2012"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   13 MW USI CAMPOS                1 RJ"  e "UG   13 MW USI CAMPOS                2 RJ" entre 01/12/2012 e 09/12/2012. A suspensão da instalação "CAMPOS" começa em 14/12/2012)
#@CT3.2.6.1.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): Parte das unidades geradoras suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS; Tipo: comercial; Valor: 0"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: comercial; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 01/07/2014 e 09/07/2014)
#@CT3.2.6.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.2.7.1.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): todas as unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então todo item na lista "CAMPOS" com "Data" entre "05/12/2012" e "13/12/2012" deveria ter o valor "Tipo: comercial; Valor: 0"
#E eu deveria ver 0 itens na lista "CAMPOS" com "Data" entre "14/12/2012" e "31/12/2012"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   13 MW USI CAMPOS                1 RJ"  e "UG   13 MW USI CAMPOS                2 RJ" entre 05/12/2012 e 09/12/2012. A suspensão da instalação "CAMPOS" começa em 14/12/2012)
#@CT3.2.7.1.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): parte das unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS; Tipo: comercial; Valor: 0"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: comercial; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 05/07/2014 e 09/07/2014)
#@CT3.2.7.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.2.8.1.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): todas as unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 item na lista "CAMPOS" com "Data" entre "14/12/2012" e "31/12/2012"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   13 MW USI CAMPOS                1 RJ"  e "UG   13 MW USI CAMPOS                2 RJ" entre 05/12/2012 e 09/12/2012. A suspensão da instalação "CAMPOS" começa em 14/12/2012)
#@CT3.2.8.1.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): parte das unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 05/07/2014 e 09/07/2014)
#@CT3.2.8.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em suspensão em parte do período solicitado para Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.2.9.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.2.9.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "30/06/2015" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#@CT3.2.10.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.2.10.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "30/06/2015" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
##
#@CT3.2.11.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.2.11.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "20/06/2015" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#@CT3.2.12.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.2.12.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "20/06/2015" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#@CT3.2.13.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "22/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 22/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 22/01/2013 e 29/01/2013)
#@CT3.2.13.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.URUGUAIANA" entre 23/05/2015 e 31/05/2015)
#@CT3.2.14.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "22/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 22/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 22/01/2013 e 29/01/2013)
#@CT3.1.14.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.URUGUAIANA" entre 23/05/2015 e 31/05/2015)
#@CT3.2.15.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando  Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "26/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 26/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 26/01/2013 e 29/01/2013)
#@CT3.2.15.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "27/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.URUGUAIANA" entre 27/05/2015 e 31/05/2015)
#@CT3.2.16.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "26/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 26/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 26/01/2013 e 29/01/2013)
#@CT3.2.16.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "27/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.URUGUAIANA" entre 27/05/2015 e 31/05/2015)
#@CT3.2.17.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "30/11/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.2.17.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "31/05/2002" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "CI CV.GARABI 1"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.2.18.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "30/11/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.2.18.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "31/05/2002" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "CI CV.GARABI 2"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.2.19.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "20/11/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.2.19.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "20/05/2002" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "CI CV.GARABI 2"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.2.20.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "20/11/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.2.20.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "20/05/2002" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "CI CV.GARABI 1"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.2.21.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "01/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.2.21.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.2.22.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "01/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.2.22.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.2.23.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "10/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.2.23.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.2.24.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "10/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.2.24.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade comercial do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.2.25.1
#Cenário: 
##
##
#@CT3.2.25.2
#Cenário: 
##
##
#@CT3.2.26.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "31/05/2001" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.2.26.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.2.27.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "31/05/2001" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.2.27.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.2.28.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "20/05/2001" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.2.28.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "31/01/2001" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.2.29.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "20/05/2001" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.2.29.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade comercial para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.2.30.1
#Cenário: 
##
##
#@CT3.2.30.2
#Cenário: 
##
##
##Funcionalidade: Consultar disponibilidade eletromecânica para uma instalação
#@CT3.3.1.1.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade operacional para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.3.1.1.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação parcialmente suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  3 RS"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.3.1.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero) 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.3.2.1.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): todas as unidades geradoras estão suspensas no período do cálculo
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.3.2.1.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): algumas unidades geradoras estão suspensas no período do cálculo
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "31/08/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: eletromecânica; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.3.2.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.3.3.1.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.3.3.1.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação parcialmente suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "20/08/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: eletromecânica; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.3.3.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "12/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.3.4.1.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação 100% suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/07/2013" para o campo "Data Inicial"
#E eu informo o valor "31/07/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CAMPOS; Data Início Suspensão: 14/12/2012"
#E eu deveria ver 0 itens na lista "CAMPOS"
#@CT3.3.4.1.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Unidade Geradora (Deverá ser igual a zero): Instalação parcialmente suspensa
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/08/2014" para o campo "Data Inicial"
#E eu informo o valor "20/08/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013; Equipamento: UG   63 MW P.MEDICI                  2 RS; Data Início Suspensão: 14/07/2014"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 480 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: eletromecânica; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   63 MW P.MEDICI                  3 RS" e "UG   63 MW P.MEDICI                  4 RS" entre 01/08/2014 e 09/08/2014)
#@CT3.3.4.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão  por todo período solicitado para cálculo utilizando Instalação Internacional (Deverá ser igual a zero)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "12/03/2011" para o campo "Data Inicial"
#E eu informo o valor "31/03/2011" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_009" e crítica "Instalação: CI CV.GARABI 2; Data Início Suspensão: 01/03/2011"
#E eu deveria ver 0 itens na lista "CI CV.GARABI 2"
#@CT3.3.4.3
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS; Tipo: eletromecânica; Valor: 0"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: eletromecânica; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 01/07/2014 e 09/07/2014)
#@CT3.3.5.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.3.6.1.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): Todas as unidades geradoras suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então todo item na lista "CAMPOS" com "Data" entre "01/12/2012" e "13/12/2012" deveria ter o valor "Tipo: eletromecânica; Valor: 0"
#E eu deveria ver 0 itens na lista "CAMPOS" com "Data" entre "14/12/2012" e "31/12/2012"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   13 MW USI CAMPOS                1 RJ"  e "UG   13 MW USI CAMPOS                2 RJ" entre 01/12/2012 e 09/12/2012. A suspensão da instalação "CAMPOS" começa em 14/12/2012)
#@CT3.3.6.1.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): Parte das unidades geradoras suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS; Tipo: eletromecânica; Valor: 0"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: eletromecânica; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 01/07/2014 e 09/07/2014)
#@CT3.3.6.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.3.7.1.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): todas as unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então todo item na lista "CAMPOS" com "Data" entre "05/12/2012" e "13/12/2012" deveria ter o valor "Tipo: eletromecânica; Valor: 0"
#E eu deveria ver 0 itens na lista "CAMPOS" com "Data" entre "14/12/2012" e "31/12/2012"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   13 MW USI CAMPOS                1 RJ"  e "UG   13 MW USI CAMPOS                2 RJ" entre 05/12/2012 e 09/12/2012. A suspensão da instalação "CAMPOS" começa em 14/12/2012)
#@CT3.3.7.1.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): parte das unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS; Tipo: eletromecânica; Valor: 0"
#E eu deveria ver 744 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS; Tipo: eletromecânica; Valor: 0"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 05/07/2014 e 09/07/2014)
#@CT3.3.7.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.3.8.1.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): todas as unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/12/2012" para o campo "Data Inicial"
#E eu informo o valor "31/12/2012" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 item na lista "CAMPOS" com "Data" entre "14/12/2012" e "31/12/2012"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   13 MW USI CAMPOS                1 RJ"  e "UG   13 MW USI CAMPOS                2 RJ" entre 05/12/2012 e 09/12/2012. A suspensão da instalação "CAMPOS" começa em 14/12/2012)
#@CT3.3.8.1.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso): parte das unidades geradoras da instalação estão suspensas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/07/2014" para o campo "Data Inicial"
#E eu informo o valor "31/07/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "P.MEDICI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de erro "RS_MENS_031" e crítica "Equipamento: UG   63 MW P.MEDICI                  1 RS; Data Início Suspensão: 02/12/2013"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  1 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  2 RS"
#E eu deveria ver 0 itens na lista "P.MEDICI" com o valor "Equipamento: UG   63 MW P.MEDICI                  3 RS"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG   63 MW P.MEDICI                  4 RS" entre 05/07/2014 e 09/07/2014)
#@CT3.3.8.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em suspensão em parte do período solicitado para Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/05/2011" para o campo "Data Inicial"
#E eu informo o valor "09/06/2011" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/05/2011" e "30/05/2011" e valor "Equipamento: CI CV.GARABI 2"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/06/2011 e 09/06/2011)
#@CT3.3.9.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.3.9.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "30/06/2015" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#@CT3.3.10.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.3.10.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "30/06/2015" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
##
#@CT3.3.11.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.3.11.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "20/06/2015" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#@CT3.3.12.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Unidade Geradora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "05/02/2013" para o campo "Data Inicial"
#E eu informo o valor "28/02/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: TERMONORTE I; Data: 30/01/2013"
#@CT3.3.12.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento desativado por todo período solicitado para cálculo utilizando Instalação Internacional (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/06/2015" para o campo "Data Inicial"
#E eu informo o valor "20/06/2015" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver uma mensagem de erro de código "RS_MENS_008" com crítica "Instalação: CI CV.URUGUAIANA; Data: 01/06/2015"
#@CT3.3.13.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "22/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 22/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 22/01/2013 e 29/01/2013)
#@CT3.3.13.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.URUGUAIANA" entre 23/05/2015 e 31/05/2015)
#@CT3.3.14.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "22/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 22/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 22/01/2013 e 29/01/2013)
#@CT3.3.14.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "23/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.URUGUAIANA" entre 23/05/2015 e 31/05/2015)
#@CT3.3.15.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando  Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "26/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 26/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 26/01/2013 e 29/01/2013)
#@CT3.3.15.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "27/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.URUGUAIANA" entre 27/05/2015 e 31/05/2015)
#@CT3.3.16.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "26/01/2013" para o campo "Data Inicial"
#E eu informo o valor "22/02/2013" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "TERMONORTE I" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "31/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              1 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              2 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              3 RO"
#E eu deveria ver 0 itens na lista "TERMONORTE I" com "Data" entre "30/01/2013" e "22/02/2013" com valor "Equipamento: UG   16 MW TERMONORTE I              4 RO"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG   16 MW TERMONORTE I              1 RO" entre 26/01/2013 e 30/01/2013)
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica dos equipamentos "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              2 RO", "UG   16 MW TERMONORTE I              3 RO" e "UG   16 MW TERMONORTE I              4 RO" entre 26/01/2013 e 29/01/2013)
#@CT3.3.16.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento desativado em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "27/05/2015" para o campo "Data Inicial"
#E eu informo o valor "23/06/2015" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.URUGUAIANA" com "Data" entre "01/06/2015" e "23/06/2015"
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.URUGUAIANA" entre 27/05/2015 e 31/05/2015)
#@CT3.3.17.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "30/11/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.3.17.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "31/05/2002" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "CI CV.GARABI 1"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.3.18.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "30/11/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.3.18.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "31/05/2002" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 744 itens na lista "CI CV.GARABI 2"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.3.19.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "20/11/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.3.19.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "20/05/2002" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "CI CV.GARABI 2"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.3.20.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/11/2014" para o campo "Data Inicial"
#E eu informo o valor "20/11/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "STO.ANT.JARI"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 01/11/2014 e 09/11/2014 que correspondem às 216 primeiros registros apresentados na tela. Nota: os equipamentos "UG  123 MW STO.ANT.JARI             02 AP", "UG  123 MW STO.ANT.JARI             03 AP" e "UG  3P4 MW STO.ANT.JARI             04 AP" entram em operação após 13/11/2014)
#@CT3.3.20.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2002" para o campo "Data Inicial"
#E eu informo o valor "20/05/2002" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 480 itens na lista "CI CV.GARABI 1"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.3.21.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "01/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.3.21.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.3.22.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "01/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.3.22.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "09/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "09/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.3.23.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "10/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.3.23.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.3.24.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Unidade Geradora (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/09/2014" para o campo "Data Inicial"
#E eu informo o valor "30/09/2014" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "STO.ANT.JARI" com "Data" entre "10/09/2009" e "16/09/2009"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "UG  123 MW STO.ANT.JARI             01 AP" entre 17/09/2014 e 25/09/2014 que correspondem às 216 primeiros registros apresentados na tela)
#@CT3.3.24.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento em operação comercial em parte do período solicitado para cálculo utilizando Instalação Internacional (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
#E eu informo o valor "09/05/2002" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 0 itens na lista "CI CV.GARABI 2" com "Data" entre "20/04/2002" e "30/04/2002"
##
##(Consultar a guia "Disponibilidades" para verificar os valores de disponibilidade eletromecânica do equipamento "CI CV.GARABI 2" entre 01/05/2002 e 09/05/2002)
#@CT3.3.25.1
#Cenário: 
##
##
#@CT3.3.25.2
#Cenário: 
##
##
#@CT3.3.26.1
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "31/05/2001" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.3.26.2
#Cenário: Selecionar um mês consolidado e calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.3.27.1
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "31/05/2001" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.3.27.2
#Cenário: Selecionar um mês de apuração e calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.3.28.1
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "20/05/2001" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.3.28.2
#Cenário: Selecionar parte de um mês consolidado e calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
#E eu informo o valor "31/01/2001" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.3.29.1
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Unidade Geradora (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/05/2001" para o campo "Data Inicial"
#E eu informo o valor "20/05/2001" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: UT MARIO LAGO; Data: 15/02/2002"
#@CT3.3.29.2
#Cenário: Selecionar parte de um mês em apuração e calcular disponibilidade eletromecânica para um equipamento que não entrou em operação comercial por todo período solicitado para cálculo utilizando Instalação Internacional (INSUCESSO)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "10/03/2001" para o campo "Data Inicial"
#E eu informo o valor "31/03/2001" para o campo "Data Final"
#E eu seleciono a opção "eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Interligações Internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
#E eu aperto o botão "Pesquisar"
#Então eu deveria a mensagem de erro de código "RS_MENS_006" com crítica "Instalação: CI CV.GARABI 2; Data: 01/05/2002"
#@CT3.3.30.1
#Cenário: 
##
##
#@CT3.3.30.2
#Cenário: 
##
##
##Funcionalidade: Gerar Gráfico das Disponibilidades
#@CT3.4.1
#Cenário: Exibição da disponibilidade Comercial
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Comercial" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto a guia "U.SOBRADINHO"
#E eu aperto o botão "Gerar Gráfico"
#Então eu deveria ver o modal "Gerar Gráfico" com os valores "Data Hora Min: 05/07/2010 00:00; Data Hora Max: 05/07/2010 04:00; Indisponibilidade: Comercial; Unidade Geradora: Equipamento: UG  175 MW U.SOBRADINHO              1 BA; Valor Min: 168; Valor Max: 172"
#E eu deveria ver o modal "Gerar Gráfico" com os valores "Data Hora Min: 05/07/2010 00:00; Data Hora Max: 05/07/2010 04:00; Indisponibilidade: Comercial; Unidade Geradora: Equipamento: UG  175 MW U.SOBRADINHO              2 BA; Valor Min: 168; Valor Max: 172"
#@CT3.4.2
#Cenário: Exibição da disponibilidade operacional
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Operacional" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto a guia "U.SOBRADINHO"
#E eu aperto o botão "Gerar Gráfico"
#Então eu deveria ver o modal "Gerar Gráfico" com os valores "Data Hora Min: 05/07/2010 00:00; Data Hora Max: 05/07/2010 04:00; Indisponibilidade: Operacional; Unidade Geradora: Equipamento: UG  175 MW U.SOBRADINHO              1 BA; Valor Min: 171; Valor Max: 175"
#E eu deveria ver o modal "Gerar Gráfico" com os valores "Data Hora Min: 05/07/2010 00:00; Data Hora Max: 05/07/2010 04:00; Indisponibilidade: Operacional; Unidade Geradora: Equipamento: UG  175 MW U.SOBRADINHO              2 BA; Valor Min: 171; Valor Max: 175"
#@CT3.4.3
#Cenário: Exibição da disponibilidade Eletromecânica
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto a guia "U.SOBRADINHO"
#E eu aperto o botão "Gerar Gráfico"
#Então eu deveria ver o modal "Gerar Gráfico" com os valores "Data Hora Min: 05/07/2010 00:00; Data Hora Max: 05/07/2010 04:00; Indisponibilidade: Eletromecânica; Unidade Geradora: Equipamento: UG  175 MW U.SOBRADINHO              1 BA; Valor Min: 170; Valor Max: 170"
#E eu deveria ver o modal "Gerar Gráfico" com os valores "Data Hora Min: 05/07/2010 00:00; Data Hora Max: 05/07/2010 04:00; Indisponibilidade: Eletromecânica; Unidade Geradora: Equipamento: UG  175 MW U.SOBRADINHO              2 BA; Valor Min: 170; Valor Max: 170"
#@CT3.4.4
#Cenário: Utilizando a opção de mudança de Unidade Geradora
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto a guia "U.SOBRADINHO"
#E eu aperto o botão "Gerar Gráfico"
#E eu seleciono o valor "Equipamento: UG  175 MW U.SOBRADINHO              2 BA" para o campo "Unidade Geradora" no modal "Gerar Gráfico"
#Então eu deveria ver o modal "Gerar Gráfico" com os valores "Data Hora Min: 05/07/2010 00:00; Data Hora Max: 05/07/2010 04:00; Indisponibilidade: Eletromecânica; Unidade Geradora: Equipamento: UG  175 MW U.SOBRADINHO              2 BA; Valor Min: 170; Valor Max: 170"
##Funcionalidade: Exportar Disponibilidade para Excel
#@CT3.5.1
#Cenário: Solicitar exportação para Excel quando não houver informações de Disponibilidade exibidas em tela
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#E eu aperto o botão "Exportar Excel"
#Então eu deveria ver a mensagem de erro "RS_MENS_015"
#@CT3.5.2
#Cenário: Solicitar exportação para Excel quando houver informações de Disponibilidade exibidas em tela
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
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
##Funcionalidade: Exportar Automaticamente para Excel
#@CT3.6.1
#Cenário: Consultar um período superior a 1 mês
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "31/09/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de sucesso de código "RS_MENS_014"
#E eu deveria poder baixar um arquivo Excel "Disponibilidades"
#@CT3.6.2
#Cenário: Consultar período igual ou inferior a 1 mês
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "10/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu não deveria poder baixar um arquivo Excel "Disponibilidades"
#@CT3.6.3
#Cenário: Consultar mais de 25 colunas de apresentação de disponibilidades
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "10/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver a mensagem de sucesso de código "RS_MENS_014"
#E eu deveria poder baixar um arquivo Excel "Disponibilidades"
#@CT3.6.4
#Cenário: Consultar 25 colunas de disponibilidades ou menos
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar disponibilidades"
#Quando eu informo o valor "01/07/2010" para o campo "Data Inicial"
#E eu informo o valor "10/07/2010" para o campo "Data Final"
#E eu seleciono a opção "Eletromecânica" para o campo "Tipo de Disponibilidade"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu não deveria poder baixar um arquivo Excel "Disponibilidades"
