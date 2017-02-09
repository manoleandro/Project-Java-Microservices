# language: pt
Funcionalidade:Verificar Seleção de Filtros
@CT2.1
Cenário: Data Inicial válido e Data Final válido, EData Inicial menor ou igual a Data Final, EData Inicial maior ou igual a 01/01/2001 e menor que a Data Corrente e Data Final menor que a Data Corrente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              4 BA; Data Hora: 2001-01-19 16:55; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-19 16:57; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
#Funcionalidade:Verificar Seleção de Filtros
@CT2.2.1
Cenário: 1) Data Inicial válido e Data Final inválido 
#Não reproduzíve. A seleção da data é feita em calendário, portanto não é possivel informar uma Data Final Inválida
#
@CT2.2.2
Cenário: 2) Data Inicial inválido e Data Final válido 
#Não reproduzível. A seleção da data é feita em calendário, portanto não é possivel informar uma Data Inicial Inválida
#
@CT2.2.3
Cenário: 3) Data Inicial sem preenchimento e Data Final inválido
#Não reproduzível. A seleção da data é feita em calendário, portanto não é possivel informar uma Data Final Inválida
#
@CT2.2.4
Cenário: 4) Data Inicial inválido e Data Final sem preenchimento
#Não reproduzível. A seleção da data é feita em calendário, portanto não é possivel informar uma  Data Inicial Inválida
#
@CT2.2.5
Cenário: 5) Data Inicial maior que Data Final
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "15/01/2001" para o campo "Data Inicial"
E eu informo o valor "10/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_013"
@CT2.2.6
Cenário: 6) Data Inicial maior ou igual a Data Corrente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/06/2030" para o campo "Data Inicial"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_039" e crítica "Data Inicial"
@CT2.2.7
Cenário: 7) Data Final maior ou igual a Data Corrente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/06/2030" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4523 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter o valor "Instalação: U.SOBRADINHO"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.2.8
Cenário: 8) Data Inicial menor que 01/01/2001
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/06/2000" para o campo "Data Inicial"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data Inicial"
@CT2.2.9
Cenário: 9) Data Final menor que 01/01/2001
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/06/2000" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data Final"
@CT2.2.10
Cenário: 10) Data Inicial sem preenchimento
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "15/01/2001" para o campo "Data final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 6 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              2 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              3 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              4 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              6 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
@CT2.2.11
Cenário: 11) Data Final sem prenchimento
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "20/01/2001" para o campo "Data Inicial"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4515 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter o valor "Instalação: U.SOBRADINHO"
#(realizar o filtro na guia "Eventos - Usinas")
#Funcionalidade:Verificar Seleção de Filtros
@CT2.3.1
Cenário: 1) Sem escolher instalação (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Instalação"
@CT2.3.2
Cenário: 2) Escolhendo algumas usinas e interligações internacionais (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
#
Então eu deveria ver 2 itens selecionados na lista "Usinas" 
E eu deveria ver um item no lista "Usinas" com valor "Nome: U.SOBRADINHO"
E eu deveria ver um item no lista "Usinas" com valor "Nome: CAMPOS"
E eu deveria ver a guia "Interligação Internacionais" indisponível
@CT2.3.3
Cenário: 3) Escolhendo algumas usinas (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" na guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "CAMPOS" para o campo "Nome" na guia "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CAMPOS; Unidade Geradora: UG   13 MW USI CAMPOS                1 RJ; Data Hora: 2001-01-10 13:37; Estado Operativo: DEM; Origem: GUM; Disponibilidade: 0"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CAMPOS; Unidade Geradora: UG   13 MW USI CAMPOS                1 RJ; Data Hora: 2001-01-13 00:21; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 16"
@CT2.3.4
Cenário: 4) Escolhendo algumas interligações internacionais  (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "23/05/2001" para o campo "Data Inicial"
E eu informo o valor "27/05/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu seleciono "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
E eu seleciono "CI ACARAY" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.URUGUAIANA; Data Hora: 2001-05-24 07:46; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.URUGUAIANA; Data Hora: 2001-05-25 16:30; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2001-05-24 12:42; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 50"
@CT2.3.5
Cenário: 5) Escolhendo todas as usinas  (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas" 
E eu aperto o botão "Pesquisar"
Então eu deveria ver 85 itens no grid "Eventos"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.3.6
Cenário: 6) Escolhendo todas as interligações internacionais  (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/06/2007" para o campo "Data Inicial"
E eu informo o valor "15/06/2007" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono todos os itens da lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 8 itens no grid "Eventos"
#(realizar o filtro na guia "Eventos - Int Inter")
#Funcionalidade:Verificar Seleção de Filtros
@CT2.4.1
Cenário: 1) Usar um nome de instalação usina existente 
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" na guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              4 BA; Data Hora: 2001-01-19 16:55; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-19 16:57; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
@CT2.4.2
Cenário: 2) Usar um nome de instalação usina inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "31/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Nome" na guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Instalação"
@CT2.4.3
Cenário: 3) Usar um nome de instalação Interligação internacional existente 
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "13/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "GARAB" para o campo "Nome" na guia "Interligações Internacionais"
Então eu deveria ver 2 itens no grid "Inteligações Internacionais"
E eu deveria ver 1 itens na lista "Interligações internacionais" com valor "Nome: CI CV.GARABI 1"
E eu deveria ver 1 itens na lista "Interligações internacionais" com valor "Nome: CI CV.GARABI 2"
@CT2.4.4
Cenário: 4) Usar um nome de instalação  Interligação internacional  inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Nome" na guia "Interligações Internacionais"
E eu seleciono todos os itens na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Instalação"
@CT2.4.5
Cenário: 5) Escolher mais de um nome de instalação existente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/09/2005" para o campo "Data Inicial"
E eu informo o valor "10/10/2005" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "GARAB" para o campo "Nome" na guia "Interligações Internacionais"
E eu seleciono todos os itens na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 10 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "01/09/2005" e "10/10/2005"
E eu deveria ver 2 itens no grid "Eventos" com valor "Instalação: CI CV.GARABI 1"
E eu deveria ver 8 itens no grid "Eventos" com valor "Instalação: CI CV.GARABI 2"
#(realizar o filtro na guia "Eventos - Int Inter")
@CT2.4.6
Cenário: 6) Escolher um nome de instalação usina existente e um nome instalação usina inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2011" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" na guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "ZZZ" para o campo "Nome" na guia "Usinas"
Então eu deveria ver 0 itens na lista "Usinas"
E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: U.SOBRADINHO"
@CT2.4.7
Cenário: 7) Escolher um nome de instalação interligação Internacional existente e um nome instalação interligação Internacional inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2011" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ACA" para o campo "Nome" na guia "Interligações Internacionais"
E eu seleciono o item "CI.ACARAY" na lista "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Nome" na guia "Interligações Internacionais"
Então eu deveria ver 0 itens na lista "Interligação Internacionais" 
E eu deveria ver 1 itens selecionados na lista "Interligações Internacionais" com valor "Nome: CI.ACARAY"
#Funcionalidade:Verificar Seleção de Filtros
@CT2.5.1
Cenário: 1) Não escolher o tipo de usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "11/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 41 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "10/01/2001" e "11/01/2001"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.5.2
Cenário: 2) Escolher Hidrelétricas
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "11/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UHE" para o campo "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 21 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "10/01/2001" e "11/01/2001"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.5.3
Cenário: 3) Escolher Termelétricas
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "11/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UTE" para o campo "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 20 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "10/01/2001" e "11/01/2001"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.5.4
Cenário: 4) Escolher tipo inexistente de usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "31/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Tipo" na guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Tipo"
@CT2.5.5
Cenário: 5) Escolher mais de um tipo de usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "11/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UHE" para o campo "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu informo o valor "UTE" para o campo "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 41 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "10/01/2001" e "11/01/2001"
#(realizar o filtro na guia "Eventos - Usinas")
#Funcionalidade:Verificar Seleção de Filtros
@CT2.6.1
Cenário: 1) Usar um nome de agente de usina existente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" na guia "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 13 itens no grid "Eventos"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.6.2
Cenário: 2) Usar um nome de agente de usina inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "31/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Agente" na guia "Usinas"
E eu seleciono todos os itens da lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Agente"
@CT2.6.3
Cenário: 3) Usar um nome de agente de Interligação internacional existente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
E eu informo o valor "26/04/2002" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente" na guia "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 12 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter o valor "Agente: CIEN"
E todo item do grid "Eventos" deveria  ter "Data Hora" entre "20/04/2002" e "26/04/2002"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.6.4
Cenário: 4) Usar um nome de agente de  Interligação internacional  inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "13/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Agente" na guia "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Agente"
@CT2.6.5
Cenário: 5) Escolher mais de um agente por nome e por instalação
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "FURNAS" para o campo "Agente" na guia "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" na guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CAMPOS; Unidade Geradora: UG   13 MW USI CAMPOS                1 RJ; Data Hora: 2001-01-10 13:37; Estado Operativo: DEM; Origem: GUM; Disponibilidade: 0"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CAMPOS; Unidade Geradora: UG   13 MW USI CAMPOS                1 RJ; Data Hora: 2001-01-13 00:21; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 16"
@CT2.6.6
Cenário: 6) Escolher agente por nome de agente e instalação usina existente e um agente por nome e instalação usina inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "12/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" na guia "Usinas"
E eu seleciono todos os itens da lista "Usinas"
E eu informo o valor "ZZZ" para o campo "Agente" na guia "Usinas"
Então eu deveria ver 0 itens na lista "Usinas" 
E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Agente: CHESF"
@CT2.6.7
Cenário: 7) Escolher um agente por nome e instalação interligação Internacional existente e um um agente por nome e instalação interligação Internacional inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "13/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente" na guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Agente" na guia "Interligações Internacionais"
#
Então eu deveria ver 0 itens na lista "Interligação Internacionais" 
E eu deveria ver 1 itens selecionados na lista "Interligações Internacionais" com valor "Nome: CI CV.GARABI 1; Agente: CHESF"
#Funcionalidade:Verificar Seleção de Filtros
@CT2.7.1
Cenário: 1) Não escolher Usinas ou Interligações Internacionais (insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Instalação"
@CT2.7.2
Cenário: 2) Escolher apenas uma Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              4 BA; Data Hora: 2001-01-19 16:55; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-19 16:57; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
@CT2.7.3
Cenário: 3) Escolher apenas uma Interligação Internacional
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "13/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 00:55; Estado Operativo: LIG; Condição Operativa: RFO; Origem: INT; Disponibilidade: 995"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 04; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 1030"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 16:18; Estado Operativo: DAU; Origem: INT; Disponibilidade: 0"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 16:43; Estado Operativo: LIG; Condição Operativa: RFO; Origem: INT; Disponibilidade: 540"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 16:44; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 1030"
@CT2.7.4
Cenário: 4) Escolher uma Usina e uma Interligação Internacional (insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#
Então eu deveria ver 1 itens selecionados na guia "Usinas" com valor "Nome: U.SOBRADINHO"
E eu deveria ver a guia "Interligações Internacionais" indisponível
@CT2.7.5
Cenário: 5) Escolher várias Usinas e várias Interligações Internacionais (insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
#
Então eu deveria ver 2 itens selecionados na lista "Usinas" 
E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: U.SOBRADINHO" 
E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: CAMPOS"
E eu deveria ver a guia "Interligações Internacionais" indisponível
@CT2.7.6
Cenário: 6) Escolher todas as Usinas
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "11/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 41 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "10/01/2001" e "11/01/2001"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.7.7
Cenário: 7) Escolher todas as Interligações Internacionais
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2004" para o campo "Data Inicial"
E eu informo o valor "05/01/2004" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono todos os itens da lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 9 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2004-01-01 00:00; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 1030"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 2; Data Hora: 2004-01-01 00:00; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 1000"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.URUGUAIANA; Data Hora: 2004-01-01 00:00; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI LIVRAMENTO 2; Data Hora: 2004-01-01 00:00; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 74"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-01 00:00; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-01 01:18; Estado Operativo: DAU; Origem: INT; Disponibilidade: 0"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-01 01:35; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-05 12:22; Estado Operativo: DUR; Origem: INT; Disponibilidade: 0"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-05 14:54; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
@CT2.7.8
Cenário: 8) Escolher todas as Usinas e todas as Interligações Internacionais (insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "11/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
#
Então eu deveria ver 15 itens selecionados na guia "Usinas"
E eu deveria ver a guia "Interligações Internacionais" indisponível
#Funcionalidade:Verificar Seleção de Filtros
@CT2.8
Cenário: Data Inicial válido e Data Final válido, EData Inicial menor ou igual a Data Final, EData Inicial maior ou igual a 01/01/2001 e menor que a Data Corrente e Data Final menor que a Data Corrente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              4 BA; Data Hora: 2001-01-19 16:55; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-19 16:57; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
#Funcionalidade:Verificar Seleção de Filtros
@CT2.9.1
Cenário: 1) Data Inicial válido e Data Final inválido 
#Não reproduzível. A seleção da data é feita em calendário, portanto não é possivel informar uma  Data Final Inválida
#
@CT2.9.2
Cenário: 2) Data Inicial inválido e Data Final válido 
#Não reproduzível. A seleção da data é feita em calendário, portanto não é possivel informar uma  Data Inicial Inválida
#
@CT2.9.3
Cenário: 3) Data Inicial sem preenchimento e Data Final inválido
#Não reproduzível. A seleção da data é feita em calendário, portanto não é possivel informar uma  Data Final Inválida
#
@CT2.9.4
Cenário: 4) Data Inicial inválido e Data Final sem preenchimento
#Não reproduzível. A seleção da data é feita em calendário, portanto não é possivel informar uma  Data Inicial Inválida
#
@CT2.9.5
Cenário: 5) Data Inicial maior que Data Final
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "15/01/2001" para o campo "Data Inicial"
E eu informo o valor "10/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_013"
@CT2.9.6
Cenário: 6) Data Inicial maior ou igual que Data Corrente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/06/2030" para o campo "Data Inicial"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_039" e crítica "Data Inicial"
@CT2.9.7
Cenário: 7) Data Final maior ou igual a Data Corrente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/06/2030" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4523 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter o valor "Instalação: U.SOBRADINHO"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.9.8
Cenário: 8) Data Inicial menor que 01/01/2001
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/06/2000" para o campo "Data Inicial"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data Inicial"
@CT2.9.9
Cenário: 9) Data Final menor que 01/01/2001
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/06/2000" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_022" e crítica "Data Final"
@CT2.9.10
Cenário: 10) Data Inicial sem preenchimento
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "15/01/2001" para o campo "Data final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 6 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              2 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              3 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              4 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              6 BA; Data Hora: 2001-01-01 00:00; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
@CT2.9.11
Cenário: 11) Data Final sem prenchimento
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "20/01/2001" para o campo "Data Inicial"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4515 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter o valor "Instalação: U.SOBRADINHO"
#(realizar o filtro na guia "Eventos - Usinas")
#Funcionalidade:Verificar Seleção de Filtros
@CT2.10.1
Cenário: 1) Sem escolher instalação (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Instalação"
@CT2.10.2
Cenário: 2) Escolhendo algumas usinas e interligações internacionais (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu seleciono o item "USINA XINGO" na lista "Usinas"
#
Então eu deveria ver 2 itens selecionados na lista "Usinas" 
E eu deveria ver um item no lista "Usinas" com valor "Nome: U.SOBRADINHO"
E eu deveria ver um item no lista "Usinas" com valor "Nome: USINA XINGO"
E eu deveria ver a guia "Interligação Internacionais" indisponível
@CT2.10.3
Cenário: 3) Escolhendo algumas usinas (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "12/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" na guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "XINGO" para o campo "Nome" na guia "Usinas"
E eu seleciono o item "USINA XINGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: USINA XINGO; Unidade Geradora: UG  527 MW USINA XINGO               4 AL; Data Hora: 2001-01-10 15; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 527"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: USINA XINGO; Unidade Geradora: UG  527 MW USINA XINGO               4 AL; Data Hora: 2001-01-10 23:00; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 527"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: USINA XINGO; Unidade Geradora: UG  527 MW USINA XINGO               4 AL; Data Hora: 2001-01-12 15:41; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 527"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: USINA XINGO; Unidade Geradora: UG  527 MW USINA XINGO               4 AL; Data Hora: 2001-01-12 22:38; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 527"
@CT2.10.4
Cenário: 4) Escolhendo algumas interligações internacionais  (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "23/05/2001" para o campo "Data Inicial"
E eu informo o valor "27/05/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu seleciono "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
E eu seleciono "CI ACARAY" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.URUGUAIANA; Data Hora: 2001-05-24 07:46; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.URUGUAIANA; Data Hora: 2001-05-25 16:30; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2001-05-24 12:42; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 50"
@CT2.10.5
Cenário: 5) Escolhendo todas as usinas  (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas" 
E eu aperto o botão "Pesquisar"
Então eu deveria ver 11 itens no grid "Eventos"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.10.6
Cenário: 6) Escolhendo todas as interligações internacionais  (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/06/2007" para o campo "Data Inicial"
E eu informo o valor "15/06/2007" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono todos os itens da lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 8 itens no grid "Eventos"
#(realizar o filtro na guia "Eventos - Int Inter")
#Funcionalidade:Verificar Seleção de Filtros
@CT2.11.1
Cenário: 1) Usar um nome de instalação usina existente 
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" na guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              4 BA; Data Hora: 2001-01-19 16:55; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-19 16:57; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
@CT2.11.2
Cenário: 2) Usar um nome de instalação usina inexistente
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "31/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Nome" na guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Instalação"
@CT2.11.3
Cenário: 3) Usar um nome de instalação Interligação internacional existente 
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "13/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "GARAB" para o campo "Nome" na guia "Interligações Internacionais"
Então eu deveria ver 2 itens no grid "Inteligações Internacionais"
E eu deveria ver 1 itens na lista "Interligações internacionais" com valor "Nome: CI CV.GARABI 1"
E eu deveria ver 1 itens na lista "Interligações internacionais" com valor "Nome: CI CV.GARABI 2"
@CT2.11.4
Cenário: 4) Usar um nome de instalação  Interligação internacional  inexistente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Nome" na guia "Interligações Internacionais"
E eu seleciono todos os itens na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Instalação"
@CT2.11.5
Cenário: 5) Escolher mais de um nome de instalação existente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/09/2005" para o campo "Data Inicial"
E eu informo o valor "10/10/2005" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "GARAB" para o campo "Nome" na guia "Interligações Internacionais"
E eu seleciono todos os itens na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 10 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "01/09/2005" e "10/10/2005"
E eu deveria ver 2 itens no grid "Eventos" com valor "Instalação: CI CV.GARABI 1"
E eu deveria ver 8 itens no grid "Eventos" com valor "Instalação: CI CV.GARABI 2"
#(realizar o filtro na guia "Eventos - Int Inter")
@CT2.11.6
Cenário: 6) Escolher um nome de instalação usina existente e um nome instalação usina inexistente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2011" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" na guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu informo o valor "ZZZ" para o campo "Nome" na guia "Usinas"
Então eu deveria ver 0 itens na lista "Usinas" 
E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: U.SOBRADINHO"
@CT2.11.7
Cenário: 7) Escolher um nome de instalação interligação Internacional existente e um nome instalação interligação Internacional inexistente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2011" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ACA" para o campo "Nome" na guia "Interligações Internacionais"
E eu seleciono o item "CI.ACARAY" na lista "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Nome" na guia "Interligações Internacionais"
Então eu deveria ver 0 itens na lista "Interligação Internacionais" 
E eu deveria ver 1 itens selecionados na lista "Interligações Internacionais" com valor "Nome: CI.ACARAY"
#Funcionalidade:Verificar Seleção de Filtros
@CT2.12.1
Cenário: 1) Não escolher o tipo de usina
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/07/2004" para o campo "Data Inicial"
E eu informo o valor "20/07/2004" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 64 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "01/07/2004" e "20/07/2004"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.12.2
Cenário: 2) Escolher Hidrelétricas
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/07/2004" para o campo "Data Inicial"
E eu informo o valor "20/07/2004" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UHE" para o campo "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 52 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "01/07/2004" e "20/07/2004"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.12.3
Cenário: 3) Escolher Termelétricas
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/07/2004" para o campo "Data Inicial"
E eu informo o valor "20/07/2004" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UTE" para o campo "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 12 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "01/07/2004" e "20/07/2004"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.12.4
Cenário: 4) Escolher tipo inexistente de usina
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "31/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Tipo" na guia "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Tipo"
@CT2.12.5
Cenário: 5) Escolher mais de um tipo de usina
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/07/2004" para o campo "Data Inicial"
E eu informo o valor "20/07/2004" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UHE" para o campo "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu informo o valor "UTE" para o campo "Tipo"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 64 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "01/07/2004" e "20/07/2004"
#(realizar o filtro na guia "Eventos - Usinas")
#Funcionalidade:Verificar Seleção de Filtros
@CT2.13.1
Cenário: 1) Usar um nome de agente de usina existente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" na guia "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 13 itens no grid "Eventos"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.13.2
Cenário: 2) Usar um nome de agente de usina inexistente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "31/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "ZZZ" para o campo "Agente" na guia "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Agente"
@CT2.13.3
Cenário: 3) Usar um nome de agente de Interligação internacional existente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "20/04/2002" para o campo "Data Inicial"
E eu informo o valor "26/04/2002" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente" na guia "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 12 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter o valor "Agente: CIEN"
E todo item do grid "Eventos" deveria  ter "Data Hora" entre "20/04/2002" e "26/04/2002"
#(realizar o filtro na guia "Eventos - Int Inter")
@CT2.13.4
Cenário: 4) Usar um nome de agente de  Interligação internacional  inexistente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "13/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Agente" na guia "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Agente"
@CT2.13.5
Cenário: 5) Escolher mais de um agente por nome e por instalação
Dado que eu esteja autenticado com usuário "cosr-se1" e perfil "COSR-SE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "FURNAS" para o campo "Agente" na guia "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu informo o valor "PETROBRAS" para o campo "Agente" na guia "Usinas"
E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CAMPOS; Unidade Geradora: UG   13 MW USI CAMPOS                1 RJ; Data Hora: 2001-01-10 13:37; Estado Operativo: DEM; Origem: GUM; Disponibilidade: 0"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CAMPOS; Unidade Geradora: UG   13 MW USI CAMPOS                1 RJ; Data Hora: 2001-01-13 00:21; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 16"
@CT2.13.6
Cenário: 6) Escolher agente por nome de agente e instalação usina existente e um agente por nome e instalação usina inexistente
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "12/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "CHESF" para o campo "Agente" na guia "Usinas"
E eu seleciono todos os itens da lista "Usinas"
E eu informo o valor "ZZZ" para o campo "Agente" na guia "Usinas"
Então eu deveria ver 0 itens na lista "Usinas" 
E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: U.SOBRADINHO"
@CT2.13.7
Cenário: 7) Escolher um agente por nome e instalação interligação Internacional existente e um um agente por nome e instalação interligação Internacional inexistente
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "13/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "CIEN" para o campo "Agente" na guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu informo o valor "ZZZ" para o campo "Agente" na guia "Interligações Internacionais"
#
Então eu deveria ver 0 itens na lista "Interligação Internacionais" 
E eu deveria ver 1 itens selecionados na lista "Interligações Internacionais" com valor "Nome: CI.ACARAY"
#Funcionalidade:Verificar Seleção de Filtros
@CT2.14.1
Cenário: 1) Não escolher Usinas ou Interligações Internacionais (insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_002" com crítica "Instalação"
@CT2.14.2
Cenário: 2) Escolher apenas uma Usina
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-12 08:09; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              4 BA; Data Hora: 2001-01-19 16:55; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 175"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: U.SOBRADINHO; Unidade Geradora: UG  175 MW U.SOBRADINHO              5 BA; Data Hora: 2001-01-19 16:57; Estado Operativo: LCS; Condição Operativa: NOR; Disponibilidade: 175"
@CT2.14.3
Cenário: 3) Escolher apenas uma Interligação Internacional
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "13/01/2001" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 00:55; Estado Operativo: LIG; Condição Operativa: RFO; Origem: INT; Disponibilidade: 995"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 04; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 1030"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 16:18; Estado Operativo: DAU; Origem: INT; Disponibilidade: 0"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 16:43; Estado Operativo: LIG; Condição Operativa: RFO; Origem: INT; Disponibilidade: 540"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2001-01-10 16:44; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 1030"
@CT2.14.4
Cenário: 4) Escolher uma Usina e uma Interligação Internacional (insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#
Então eu deveria ver 1 itens selecionados na guia "Usinas" com valor "Nome: U.SOBRADINHO"
E eu deveria ver a guia "Interligações Internacionais" indisponível
@CT2.14.5
Cenário: 5) Escolher várias Usinas e várias Interligações Internacionais (insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "19/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu seleciono o item "USINA XINGO" na lista "Usinas"
#
Então eu deveria ver 1 itens selecionados na guia "Usinas" com valor "Nome: U.SOBRADINHO" 
E eu deveria ver 1 itens selecionados na guia "Usinas" com valor "Nome: USINA XINGO"
E eu deveria ver a guia "Interligações Internacionais" indisponível
@CT2.14.6
Cenário: 6) Escolher todas as Usinas
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "11/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "Eventos"
E todo item do grid "Eventos" deveria ter "Data Hora" entre "10/01/2001" e "11/01/2001"
#(realizar o filtro na guia "Eventos - Usinas")
@CT2.14.7
Cenário: 7) Escolher todas as Interligações Internacionais
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2004" para o campo "Data Inicial"
E eu informo o valor "05/01/2004" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono todos os itens da lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 9 itens no grid "Eventos"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 1; Data Hora: 2004-01-01 00:00; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 1030"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.GARABI 2; Data Hora: 2004-01-01 00:00; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 1000"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI CV.URUGUAIANA; Data Hora: 2004-01-01 00:00; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI LIVRAMENTO 2; Data Hora: 2004-01-01 00:00; Estado Operativo: DCO; Condição Operativa: NOR; Disponibilidade: 74"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-01 00:00; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-01 01:18; Estado Operativo: DAU; Origem: INT; Disponibilidade: 0"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-01 01:35; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-05 12:22; Estado Operativo: DUR; Origem: INT; Disponibilidade: 0"
E eu deveria ver um item no grid "Eventos" com valor "Instalação: CI ACARAY; Data Hora: 2004-01-05 14:54; Estado Operativo: LIG; Condição Operativa: NOR; Disponibilidade: 50"
@CT2.14.8
Cenário: 8) Escolher todas as Usinas e todas as Interligações Internacionais (insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "10/01/2001" para o campo "Data Inicial"
E eu informo o valor "11/01/2001" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
#
Então eu deveria ver 5 itens selecionados na guia "Usinas"
E eu deveria ver a guia "Interligações Internacionais" indisponível
