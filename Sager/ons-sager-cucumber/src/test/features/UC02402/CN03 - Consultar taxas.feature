# language: pt
Funcionalidade: Consultar Taxas
@CT3.1
Cenário: Consultar TEIP acumulada para uma instalação: a) Usina (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "02/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP acumulada"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu seleciono o elemento "USINA XINGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "USINA XINGO"
E eu deveria ver um item no grid "USINA XINGO" com valor: "Mês Referência: 01/2008; TEIP acum: 0,00728762895"
E eu deveria ver um item no grid "USINA XINGO" com valor: "Mês Referência: 02/2008; TEIP acum: 0,00731744198"
E eu deveria ver 2 itens no grid "CAMPOS"
E eu deveria ver um item no grid "CAMPOS" com valor: "Mês Referência: 01/2008; TEIP acum: 0,0173869599"
E eu deveria ver um item no grid "CAMPOS" com valor: "Mês Referência: 02/2008; TEIP acum: 0,0178939104"
#Funcionalidade: Verificar versionamento de taxas
@CT3.2
Cenário: Consultar uma usina  e um período que tenha recálculos (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "02/2016" para o campo "Mês inicial"
E eu informo o valor "03/2016" para o campo "Mês final"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "U.SOBRADINHO" na lista "Usinas"
E eu seleciono a opção "Visualizar versionamento de cálculos"
Então eu deveria ver 2 itens no "Versionamento de Cálculos"
E eu deveria ver um item com valor: "Mês Referência: 02/2016; Nome: Liminar ASTT (teste); Status: Ativo"
E eu deveria ver um item com valor: "Mês Referência: 03/2016; Nome: Liminar ASS; Status: Ativo"
#Funcionalidade: Consultar cenário de cálculo
@CT3.3
Cenário: Consultar uma usina e um período que tenha cenário (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "02/2016" para o campo "Mês inicial"
E eu informo o valor "03/2016" para o campo "Mês final"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "U.SOBRADINHO" na lista "Usinas"
E eu seleciono a opção "Visualizar cenários de cálculos"
Então eu deveria ver 2 itens no "Cenário de Cálculos"
E eu deveria ver um item com valor: "Mês Referência: 02/2016; Nome: Liminar ASTT (teste); Status: Ativo"
E eu deveria ver um item com valor: "Mês Referência: 03/2016; Nome: Liminar ASS; Status: Ativo"
#Funcionalidade: Exportar taxas para Excel
@CT3.4
Cenário: Solicitar exportação para Excel das informações referenciadas em página (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "02/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP acumulada"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu seleciono o elemento "USINA XINGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o botão "Exportar Excel"
Então eu deveria ver 2 itens na guia "USINA XINGO" no arquivo excel exportado e salvo em pasta do windows
E eu deveria ver um item com valor: "Mês Referência: 01/2008; TEIP acum: 0,00728762895"
E eu deveria ver um item com valor: "Mês Referência: 02/2008; TEIP acum: 0,00731744198"
E eu deveria ver 2 itens na guia "CAMPOS" do arquivo excel exportado
E eu deveria ver um item no grid "CAMPOS" com valor: "Mês Referência: 01/2008; TEIP acum: 0,0173869599"
E eu deveria ver um item no grid "CAMPOS" com valor: "Mês Referência: 02/2008; TEIP acum: 0,0178939104"
#Funcionalidade: Gerar Gráfico de taxas
@CT3.5
Cenário: Gerar gráfico das informações exibidas em página (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "02/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP acumulada"
E eu seleciono o tipo de taxa "TEIF_oper acumulada"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o botão "Gerar Gráficol"
Então eu deveria ver uma página modal com um gráfico de barras
E eu deveria ver uma barra vertical com valor: "TEIP acum: 0,0173869599" no item horizontal do mês "Janeiro"
E eu deveria ver uma barra vertical com valor: "TEIF_oper acum: 0,0127079897" no item horizontal do mês "Janeiro"
E eu deveria ver uma barra vertical com valor: "TEIP acum: 0,0178939104" no item horizontal do mês "Fevereiro"
E eu deveria ver um item no grid "CAMPOS" com valor: "TEIF_oper acum: 0,0159994196" no item horizontal do mês "Fevereiro"
#Funcionalidade: Visualizar Memória de Cálculo
@CT3.6
Cenário: Consultar TEIP mensal para: a)Usina (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "01/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto a imagem do item com valor: "Mês Referência: 01/2008; TEIP mensal: 0,319725"
Então eu deveria ver 3 linhas na lista "Unidade Geradora"
E eu deveria ver uma linha com valor: "Mês: 01/2008"
E eu deveria ver uma linha com valor: "Unidade geradora: RJUSCP_13P8_UG1; HD: 302,433333"
E eu deveria ver uma linha com valor: "Unidade geradora: RJUSCP_13P8_UG2 HD: 648,616667"
#Funcionalidade: Expandir memória de cálculo por mês de Referência
@CT3.7
Cenário: Escolher uma taxa mensal (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "08/2002" para o campo "Mês inicial"
E eu informo o valor "08/2002" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto a imagem do primeiro elemento "TEIP acum" na lista "Usinas"
#Não será necessário fazer esse teste, pois os passos correspondentes a esse cenário foram incluidos no 3.6
#Funcionalidade: Expandir memória de cálculo por Unidade Geradora
@CT3.8
Cenário: Escolher uma taxa mensal (Insucesso)
#Não reproduzível/necessário
#
#Funcionalidade: Visualizar Eventos por Taxas
@CT3.9
Cenário: Selecionar um taxa aonde foi usado parâmetros no cálculo (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "01/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto a imagem do item com valor: "Mês Referência: 01/2008; TEIP mensal: 0,319725"
E eu deveria ver uma linha com valor: "Unidade geradora: RJUSCP_13P8_UG1; HD: 302,433333"
E eu aperto o botão "Visualizar eventos"
Então eu deveria ver a página "Consultar eventos"
#Funcionalidade: Visualizar Eventos por Mês de Referência
@CT3.10
Cenário: Selecionar um mês de referência que tenha eventos (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "01/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto a imagem do item com valor: "Mês Referência: 01/2008; TEIP mensal: 0,319725"
Então eu deveria ver 3 linhas na lista "Unidade Geradora"
E eu deveria ver uma linha com valor: "Mês: 01/2008"
E eu deveria ver uma linha com valor: "Unidade geradora: RJUSCP_13P8_UG1; HD: 302,433333"
E eu deveria ver uma linha com valor: "Unidade geradora: RJUSCP_13P8_UG2 HD: 648,616667"
#Funcionalidade: Visualizar Eventos por Unidade Geradora
@CT3.11
Cenário: Selecionar uma unidade geradora que tenha eventos (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar Eventos"
Quando eu informo o valor "01/2007" para o campo "Mês inicial"
E eu informo o valor "02/2007" para o campo "Mês final"
E eu informo o valor  "GOUSCB0UG1" no campo "unidade geradora" na lista "Eventos"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 itens no grid "Eventos"
E eu deveria ver 4 unidades geradoras de "GOUSCB0UG1" 
#Funcionalidade: Visualizar Eventos por Parâmetros
@CT3.12
Cenário: Selecionar um parâmetro (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "01/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto a imagem do item com valor: "Mês Referência: 01/2008; TEIP mensal: 0,319725"
Então eu deveria ver 3 linhas na lista "Unidade Geradora"
E eu deveria ver uma linha com valor: "Mês: 01/2008"
E eu deveria ver uma linha com valor: "Unidade geradora: RJUSCP_13P8_UG1; HD: 302,433333"
E eu deveria ver uma linha com valor: "Unidade geradora: RJUSCP_13P8_UG2 HD: 648,616667"
#Funcionalidade: Exportar para Excel (Memória de Cálculo)
@CT3.13
Cenário: Solicitar exportação para Excel das informações referenciadas em página (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "01/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto a imagem do item com valor: "Mês Referência: 01/2008; TEIP mensal: 0,319725"
Então eu deveria ver 3 itens na guia "CAMPOS"  no arquivo excel exportado e salvo em pasta do windows
E eu deveria ver uma linha com valor: "Mês: 01/2008"
E eu deveria ver uma linha com valor: "Unidade geradora: RJUSCP_13P8_UG1; HD: 302,433333"
E eu deveria ver uma linha com valor: "Unidade geradora: RJUSCP_13P8_UG2 HD: 648,616667"
#Funcionalidade: Gerar Gráfico (Memória de Cálculo)
@CT3.14
Cenário: Gerar gráfico dos  parâmetros de cada unidade geradora por mês (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "02/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIF"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "USINA XINGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto a guia "USINA XINGO"
E eu aperto o botão "Gerar Gráfico"
Então eu deveria ver uma página modal com um gráfico de barras
E eu deveria ver uma linha com valor: "Mês: 01/2008"
E eu deveria ver uma barra vertical com valor: "Unidade geradora: RJUSCP_13P8_UG1; HD: 302,433333"
E eu deveria ver uma barra vertical com valor: "Unidade geradora: RJUSCP_13P8_UG2 HD: 648,616667"
#Funcionalidade: Gerar Gráfico de Taxas
@CT3.15
Cenário: Gerar gráfico de barras das e taxas exibidas em página (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "02/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP acumulada"
E eu seleciono o tipo de taxa "TEIF_oper acumulada"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o botão "Gerar Gráficol"
Então eu deveria ver uma página modal com um gráfico de barras
E eu deveria ver uma barra vertical com valor: "TEIP acum: 0,0173869599" no item horizontal do mês "Janeiro"
E eu deveria ver uma barra vertical com valor: "TEIF_oper acum: 0,0127079897" no item horizontal do mês "Janeiro"
E eu deveria ver uma barra vertical com valor: "TEIP acum: 0,0178939104" no item horizontal do mês "Fevereiro"
E eu deveria ver um item no grid "CAMPOS" com valor: "TEIF_oper acum: 0,0159994196" no item horizontal do mês "Fevereiro"
