# language: pt
Funcionalidade: Consultar informações de agendamento 
@CT3.1.1
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.2
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.3
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.1
Cenário: 1) Verificar status dos agendamentos por usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 13 item no grid "Agendamentos de Cáculo de Taxas"
E eu deveria ver 7 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Status: Agendado"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Status: Cancelado"
E eu deveria ver 4 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Status: Finalizado"
@CT3.1.4
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.5
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.6
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.6
Cenário: 2) Verificar status dos agendamentos das Instalações Internacionais
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 17 item no grid "Agendamentos de Cáculo de Taxas"
E eu deveria ver 6 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Status: Agendado"
E eu deveria ver 4 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Status: Cancelado"
E eu deveria ver 7 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Status: Finalizado"
@CT3.1.7
Cenário: 3) Consultar agendamentos para mais de uma usina (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 7 item no grid "Agendamentos de Cáculo de Taxas"
E eu deveria ver 5 itens do grid "Agendamentos de Cálculo de Taxas" com valor " "Instalação: CAMPOS"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Intalação: UT MARIO LAGO"
@CT3.1.8
Cenário: 4) Consultar agendamentos para mais de uma interligação internacional (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "2" para o campo "Nome" 
E eu seleciono todos os itens na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 7 item no grid "Agendamentos de Cáculo de Taxas"
E eu deveria ver 4 itens do grid "Agendamentos de Cálculo de Taxas" com valor " "Instalação: CI LIVRAMENTO 2"
E eu deveria ver 3 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Intalação: CI CV.GARABI 2"
@CT3.1.9
Cenário: 5) Consultar um período que não possua agendamento (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "05/04/2014" para o campo "Data Inicial"
E eu informo o valor "30/04/2014" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT3.1.10
Cenário: 6) Consultar uma usina sem agendamento (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT3.1.11
Cenário: 7) Consultar uma interligação internacional sem agendamento (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "31/05/2015" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "ACARAY" para o campo "Nome" 
E eu seleciono o item "CI ACARAY" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT3.1.12
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.13
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.14
Cenário: 9) Consultar Usinas que possuam mais de um agendamento no período selelcionado
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/01/2016" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Agendamentos de Cáculo de Taxas"
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/01/2016" e "01/10/2017"
@CT3.1.15
Cenário: 10) Consultar Interligações Internacionais que possuam mais de um agendamento no período selelcionado
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/04/2014" para o campo "Data Inicial"
E eu informo o valor "30/06/2016" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu informo o valor "URUGUA" para o campo "Nome" 
E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "Agendamentos de Cáculo de Taxas" 
E todo item do grid "Agendamentos de Cálculo de Taxas" deveria ter "Data/Hora" entre "01/04/2014" e "30/06/2016"
E eu deveria ver todos os itens do grid "Agendamentos de Cálculo de Taxas" com valor "Instalação:CI CV.URUGUAIANA"
@CT3.1.16
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.17
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.18
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.19
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.20
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.18
Cenário: 1) Verificar status dos agendamentos para usinas (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-SE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 7 item no grid "Agendamentos de Cáculo de Taxas"
E eu deveria ver 4 itens do grid "Agendamentos de Cálculo de Taxas" com valor " "Instalação: CAMPOS"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Intalação: UT MARIO LAGO"
@CT3.1.21
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.22
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.23
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.24
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.25
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.25
Cenário: 2)  Verificar agendamentos para  Interligações Internacionais
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono todos os itens na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 17 item no grid "Agendamentos de Cáculo de Taxas"
E eu deveria ver 6 itens do grid "Agendamentos de Cálculo de Taxas" com valor "Status: Agendado"
E eu deveria ver 4 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Status: Cancelado"
E eu deveria ver 7 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Status: Finalizado"
@CT3.1.26
Cenário: 3) Consultar agendamento para mais de uma usina (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "COSR-SE"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 7 item no grid "Agendamentos de Cáculo de Taxas"
E eu deveria ver 5 itens do grid "Agendamentos de Cálculo de Taxas" com valor " "Instalação: CAMPOS"
E eu deveria ver 2 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Intalação: UT MARIO LAGO"
@CT3.1.27
Cenário: 4) Consultar agendamento para mais de uma interligação internacional (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"'
Então eu deveria ver 6 item no grid "Agendamentos de Cáculo de Taxas"
E eu deveria ver 3 itens do grid "Agendamentos de Cálculo de Taxas" com valor " "Instalação: CI CV.GARABI 1"
E eu deveria ver 3 itens no grid "Agendamentos de Cálculo de Taxas" com valor "Intalação: CI CV.GARABI 2"
@CT3.1.28
Cenário: 5) Consultar um período que não possua agendamento (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "05/04/2014" para o campo "Data Inicial"
E eu informo o valor "30/04/2014" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT3.1.29
Cenário: 6) Consultar uma usina sem agendamento (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono o item "STO.ANT.JARI" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT3.1.30
Cenário: 7) Consultar uma interligação internacional sem agendamento (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "31/05/2015" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI ACARAY" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT3.1.31
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.32
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.33
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.34
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.35
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.1.36
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.1
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.2
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.3
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.4
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.5
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.6
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.7
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.8
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.9
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.10
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.11
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.12
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.13
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.2.14
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.3.1
Cenário: 1) Ordenação de Agendamentos por Protocolo (origem "Cálculo de Taxas" por "Protocolo" ordem Crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Protocolo" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 item no grid "Agendamentos de Cáculo de Taxas"
E eu deveria ver 1 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2014"
E eu deveria ver 1 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2015"
E eu deveria ver 1 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2016"
E eu deveria ver 3 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017"
E eu deveria ver 1 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2014"
E eu deveria ver 1 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2015"
E eu deveria ver 1 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2016"
E eu deveria ver 1 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017"
E eu deveria ver 2 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:003-2017"
E eu deveria ver 1 no item no grid  "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:004-2017"
@CT3.3.2
Cenário: 1) Ordenação de Agendamentos por Protocolo (origem "Cálculo de Taxas" por "Protocolo" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Protocolo" no grid "Agendamentos de Cálculo de Taxas"
E eu aperto o título da coluna "Protocolo" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Protocolo:004-2017"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Protocolo:001-2014"
@CT3.3.3
Cenário: 2) Ordenação de Agendamentos por Instalação (origem "Cálculo de Taxas" por "Instalação" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Instalação" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Instalação:BENTO MUNHOZ"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Instalação:UT MARIO LAGO "
@CT3.3.4
Cenário: 2) Ordenação de Agendamentos por Instalação (origem "Cálculo de Taxas" por "Instalação" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Instalação" no grid "Agendamentos de Cálculo de Taxas"
E eu aperto o título da coluna "Instalação" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Instalação:UT MARIO LAGO"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Instalação:BENTO MUNHOZ"
@CT3.3.5
Cenário: 2) Ordenação de Agendamentos por Instalação (origem "Cenários" por "Instalação" crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Instalação" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Instalação:CAMPOS"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Instalação:U.SOBRADINHO"
@CT3.3.6
Cenário: 2) Ordenação de Agendamentos por Instalação (origem "Cenários" por "Instalação" decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Instalação" no grid "Agendamentos de Cenários"
E eu aperto o título da coluna "Instalação" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Instalação:U.SOBRADINHO"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Instalação:CAMPOS"
@CT3.3.7
Cenário: 3) Ordenação de Agendamentos por Data/Hora(origem "Cálculo de Taxas" por "Data/Hora" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data/Hora" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Data/Hora:01/04/2014 09:56"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Data/Hora:01/10/2017 10:00"
@CT3.3.8
Cenário: 3) Ordenação de Agendamentos por Data/Hora(origem "Cálculo de Taxas" por "Data/Hora" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data/Hora" no grid "Agendamentos de Cálculo de Taxas"
E eu aperto o título da coluna "Data/Hora" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Data/Hora:01/10/2017 10:00"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Data/Hora:01/04/2014 09:56"
@CT3.3.9
Cenário: 3) Ordenação de Agendamentos por Data/Hora(origem "Cenários" por "Data/Hora" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data/Hora" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Data/Hora:01/04/2014 09:56"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Data/Hora:06/10/2017 09:05"
@CT3.3.10
Cenário: 3) Ordenação de Agendamentos por Data/Hora(origem "Cenários" por "Data/Hora" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data/Hora" no grid "Agendamentos de Cenários"
E eu aperto o título da coluna "Data/Hora" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Data/Hora:06/10/2017 09:05"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Data/Hora:01/04/2014 09:56"
@CT3.3.11
Cenário: 3) Ordenação de Agendamentos por Data/Hora(origem "Retificação" por "Data/Hora" ordem crescente);
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data/Hora" no grid "Agendamentos de Retificação"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação"
E eu deveria ver no item 1 do grid  "Agendamentos de Retificação" o valor "Data/Hora:01/04/2014 09:56"
E eu deveria ver no item 20 do grid  "Agendamentos de Retificação" o valor "Data/Hora:06/10/2017 09:05"
@CT3.3.12
Cenário: 3) Ordenação de Agendamentos por Data/Hora(origem "Retificação" por "Data/Hora" ordem decrescente);
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data/Hora" no grid "Agendamentos de Retificação"
E eu aperto o título da coluna "Data/Hora" no grid "Agendamentos de Retificação"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação"
E eu deveria ver no item 1 do grid  "Agendamentos de Retificação" o valor "Data/Hora:06/10/2017 09:05"
E eu deveria ver no item 20 do grid  "Agendamentos de Retificação" o valor "Data/Hora:01/04/2014 09:56"
@CT3.3.13
Cenário: 4) Ordenação de Agendamentos por Solicitante(origem "Cálculo de Taxas" por "Solicitante" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Solicitante" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante:cnos"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante:SAGER"
@CT3.3.14
Cenário: 4) Ordenação de Agendamentos por Solicitante(origem "Cálculo de Taxas" por "Solicitante" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Solicitante" no grid "Agendamentos de Cálculo de Taxas"
E eu aperto o título da coluna "Solicitante" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante:SAGER"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante:cnos"
@CT3.3.15
Cenário: 4) Ordenação de Agendamentos por Solicitante(origem "Retificação" por "Solicitante" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Solicitante" no grid "Agendamentos de Retificação"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação"
E eu deveria ver no item 1 do grid  "Agendamentos de Retificação" o valor "Solicitante:cnos"
E eu deveria ver no item 20 do grid  "Agendamentos de Retificação" o valor "Solicitante:cosr-se2"
@CT3.3.16
Cenário: 4) Ordenação de Agendamentos por Solicitante(origem "Retificação" por "Solicitante" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Solicitante" no grid "Agendamentos de Retificação"
E eu aperto o título da coluna "Solicitante" no grid "Agendamentos de Retificação"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação"
E eu deveria ver no item 1 do grid  "Agendamentos de Retificação" o valor "Solicitante:cosr-se2"
E eu deveria ver no item 20 do grid  "Agendamentos de Retificação" o valor "Solicitante:cnos"
@CT3.3.17
Cenário: 5) Ordenação de Agendamentos por DD/MM/AAAA Inicial(origem "Cálculo de Taxas" por "Mes Inicial" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Mes Inicial" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Mes Inicial:01/2012"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Mes Inicial:01/2017"
@CT3.3.18
Cenário: 5) Ordenação de Agendamentos por DD/MM/AAAA Inicial(origem "Cálculo de Taxas" por "Mes Inicial" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Mes Inicial" no grid "Agendamentos de Cálculo de Taxas"
E eu aperto o título da coluna "Mes Inicial" no grid "Agendamentos de Cálculo de Taxas"
#
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Mes Inicial:01/2017"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Mes Inicial:01/2012"
@CT3.3.19
Cenário: 5) Ordenação de Agendamentos por DD/MM/AAAA Inicial(origem "Cenários" por "Data Início" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data Início" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Data Início:01/01/2012"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Data Início:01/03/2016"
@CT3.3.20
Cenário: 5) Ordenação de Agendamentos por DD/MM/AAAA Inicial(origem "Cenários" por "Data Início" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data Início" no grid "Agendamentos de Cenários"
E eu aperto o título da coluna "Data Início" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Data Início:01/03/2016"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Data Início:01/01/2012"
@CT3.3.21
Cenário: 6) Ordenação de Agendamentos por DD/MM/AAAA Final(origem "Cálculo de Taxas" por "Mes final" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Mes Final" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Mes Final:05/2012"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Mes Final:01/2017"
@CT3.3.22
Cenário: 6) Ordenação de Agendamentos por DD/MM/AAAA Final(origem "Cálculo de Taxas" por "Mes final" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Mes Final" no grid "Agendamentos de Cálculo de Taxas"
E eu aperto o título da coluna "Mes Final" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Mes Final:01/2017"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Mes Final:05/2012"
#
@CT3.3.23
Cenário: 6) Ordenação de Agendamentos por DD/MM/AAAA Final(origem "Cenários" por "Data Término" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data Término" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Data Término:01/05/2012"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Data Término:01/10/2016"
@CT3.3.24
Cenário: 6) Ordenação de Agendamentos por DD/MM/AAAA Final(origem "Cenários" por "Data Término" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Data Término" no grid "Agendamentos de Cenários"
E eu aperto o título da coluna "Data Término" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Data Término:01/10/2016"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Data Término:01/05/2012"
@CT3.3.25
Cenário: 7)Ordenação de Agendamentos por Status(origem "Cálculo de Taxas" por "Status" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Status" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Status:Agendado"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Status:Finalizado"
@CT3.3.26
Cenário: 7)Ordenação de Agendamentos por Status(origem "Cálculo de Taxas" por "Status" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Status" no grid "Agendamentos de Cálculo de Taxas"
E eu aperto o título da coluna "Status" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Status:Finalizado"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Status:Agendado"
#
@CT3.3.27
Cenário: 7)Ordenação de Agendamentos por Status(origem "Cenários" por "Situação" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Situação" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Status:Agendado"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Status:Finalizado"
@CT3.3.28
Cenário: 7)Ordenação de Agendamentos por Status(origem "Cenários" por "Situação" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Situação" no grid "Agendamentos de Cenários"
E eu aperto o título da coluna "Situação" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Status:Finalizado"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Status:Agendado"
@CT3.3.29
Cenário: 7)Ordenação de Agendamentos por Status(origem "Retificação" por "Situação" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Situação" no grid "Agendamentos de Retificação"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação"
E eu deveria ver no item 1 do grid  "Agendamentos de Retificação" o valor "Status:Agendado"
E eu deveria ver no item 20 do grid  "Agendamentos de Retificação" o valor "Status:Finalizado"
@CT3.3.30
Cenário: 7)Ordenação de Agendamentos por Status(origem "Retificação" por "Situação" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Situação" no grid "Agendamentos de Retificação"
E eu aperto o título da coluna "Situação" no grid "Agendamentos de Retificação"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação"
E eu deveria ver no item 1 do grid  "Agendamentos de Retificação" o valor "Status:Finalizado"
E eu deveria ver no item 20 do grid  "Agendamentos de Retificação" o valor "Status:Agendado"
@CT3.3.31
Cenário: 8) Ordenação de Agendamentos por Resultado (origem "Cálculo de Taxas" por "Resultado" ordem crescente);
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Resultado" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Resultado: "
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Resultado: OK"
@CT3.3.32
Cenário: 8) Ordenação de Agendamentos por Resultado (origem "Cálculo de Taxas" por "Resultado" ordem decrescente);
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Resultado" no grid "Agendamentos de Cálculo de Taxas"
E eu aperto o título da coluna "Resultado" no grid "Agendamentos de Cálculo de Taxas"
Então eu deveria ver 13 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver no item 1 do grid  "Agendamentos de Cálculo de Taxas" o valor "Resultado: OK"
E eu deveria ver no item 13 do grid  "Agendamentos de Cálculo de Taxas" o valor "Resultado: "
#
@CT3.3.33
Cenário: 8) Ordenação de Agendamentos por Resultado (origem "Cenários"" por "Resultado" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Resultado" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Resultado: "
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Resultado: OK"
@CT3.3.34
Cenário: 8) Ordenação de Agendamentos por Resultado (origem "Cenários"" por "Resultado" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Resultado" no grid "Agendamentos de Cenários"
E eu aperto o título da coluna "Resultado" no grid "Agendamentos de Cenários"
Então eu deveria ver 15 itens no grid "Agendamentos de Cenários"
E eu deveria ver no item 1 do grid  "Agendamentos de Cenários" o valor "Resultado: OK"
E eu deveria ver no item 15 do grid  "Agendamentos de Cenários" o valor "Resultado: "
@CT3.3.35
Cenário: 8) Ordenação de Agendamentos por Resultado (origem "Retificação" por "Resultado" ordem crescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Resultado" no grid "Agendamentos de Retificação"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação"
E eu deveria ver no item 1 do grid  "Agendamentos de Retificação" o valor "Resultado: "
E eu deveria ver no item 20 do grid  "Agendamentos de Retificação" o valor "Resultado: OK"
@CT3.3.36
Cenário: 8) Ordenação de Agendamentos por Resultado (origem "Retificação" por "Resultado" ordem decrescente)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu aperto o botão "Pesquisar"
E eu aperto o título da coluna "Resultado" no grid "Agendamentos de Retificação"
E eu aperto o título da coluna "Resultado" no grid "Agendamentos de Retificação"
Então eu deveria ver 20 itens no grid "Agendamentos de Retificação"
E eu deveria ver no item 1 do grid  "Agendamentos de Retificação" o valor "Resultado: OK"
E eu deveria ver no item 20 do grid  "Agendamentos de Retificação" o valor "Resultado: "
@CT3.4.1
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.4.1
Cenário: 1) Perfil COSR visualiza agendamentos realizados pelo SAGER para as Instalações da sua área (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-se2" e perfil "COSR-SE"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 7 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver 2 itens do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante: SAGER"
E eu deveria ver 3 itens do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante: cosr-se2"
E eu deveria ver 2 itens do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante: cnos"
@CT3.4.2
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.4.3
Cenário: 3) Perfil COSR visualiza agendamentos realizados pelo CNOS para as instalações que não estão na sua área (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 8 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver 1 item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:009-2017; Instalação: PORTO PECEM II"
@CT3.4.4
Cenário: 4) Perfil COSR visualiza agendamentos realizados pelo SAGER  para as instalações que não estão na sua área (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver 1 item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação: U.SOBRADINHO; Data Hora:01/02/2017 10:00; Solicitante:SAGER"
@CT3.4.5
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.4.5
Cenário: 5) Perfil CNOS visualiza todos os agendamentos realizados pelo SAGER 
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/06/2016" para o campo "Data Inicial"
E eu informo o valor "01/02/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 5 itens no grid "Agendamentos de Cálculo de Taxas"
E eu deveria ver 3 itens do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante: SAGER"
E eu deveria ver 1 itens do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante: cosr-ne1"
E eu deveria ver 1 itens do grid  "Agendamentos de Cálculo de Taxas" o valor "Solicitante: cnos"
@CT3.4.6
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.4.7
Cenário: Teste removido a pedido do ONS 
#
#
@CT3.5.1
Cenário: 1) Consultar um agendamento com status de finalizado e resultado OK nos logs de avisos de transferência (Retificação) (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu expando o item no grid "Agendamentos de Retificação" com o valor "Número da Tarefa:COSR T0006/2016"
Então eu deveria ver 2 atividades na tarefa "COSR T0006/2016" no grid "Agendamentos de Retificação" com valor "Status: Finalizado; Resultado: OK"
E nenhuma ativdade da tarefa "COSR T0006/2016" no grid "Agendamentos de Retificação" deveria ter um link habilitado no campo "Resultado"
@CT3.5.2
Cenário: 1) Consultar um agendamento com status de finalizado e resultado OK nos logs de avisos de cálculo (Interligação Internacional) (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto a guia "Interligaçao Internacional"
E eu seleciono todos os itens na lista "Interligaçao Internacional"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:003-2014; Instalação: CI CV.GARABI 1"
Então eu deveria ver o link do campo "Resultado" do protocolo "003-2014" no grid "Agendamentos de Retificação" desabilitado
@CT3.5.3
Cenário: 1) Consultar um agendamento com status de finalizado e resultado OK nos logs de avisos de cálculo (Usina) (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2016; Instalação: U.SOBRADINHO"
Então eu deveria ver o link do campo "Resultado" do protocolo "003-2014" no grid "Agendamentos de Retificação" G118desabilitado
@CT3.5.4
Cenário: 2) Consultar um agendamento com status de finalizado e resultado NÃO OK no log de aviso de transferência (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu expando o item no grid "Agendamentos de Retificação" com o valor "Tarefa: CNOS T0005/2015"
E eu aperto o link no campo "Resultado" da atividade "Transferência" da tarefa "CNOS T0005/2015"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Houve geração de críticas de sequência de eventos"
E eu deveria ver um link para "Retificar Eventos"
@CT3.5.5
Cenário: 3) Consultar um agendamento com status de finalizado e resultado NÃO OK no log de aviso de cálculo (Usinas) (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o link no campo "Resultado" do item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo: 002-2016: Instalação: CAMPOS"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação: CAMPOS Suspensão: 14/12/2012"
E eu deveria ver um link para "Retificar Eventos"
@CT3.5.6
Cenário: 3) Consultar um agendamento com status de finalizado e resultado NÃO OK no log de aviso de cálculo (Interligação Internacional) (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "02/05/2015" para o campo "Data Inicial"
E eu informo o valor "02/05/2015" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono todos os itens na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu aperto o link no campo "Resultado" do item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo: 003-2015; Instalação: CI LIVRAMENTO 2"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas. Instalação: CI LIVRAMENTO 2 Suspensão: 01/02/2001"
E eu deveria ver um link para "Retificar Eventos"
@CT3.5.7
Cenário: 4) Consultar um agendamento com status de AGENDADO e resultado OK no log de aviso de transferência (Sucesso)
#Não aplicável. Status Agendado não possui resultado preenchido.
#
@CT3.5.8
Cenário: 5) Consultar um agendamento com status de AGENDADO e resultado NÃO OK no log de aviso de transferência (Sucesso)
#Não aplicável. Status Agendado não possui resultado preenchido.
#
@CT3.5.9
Cenário: 6) Consultar um agendamento com status de FINALIZADO e resultado NÃO OK no log de aviso de cálculo (Sucesso)
#idem ao CT3.5.4 e CT3.5.5
#
@CT3.5.10
Cenário: 7) Consultar um agendamento com status de CANCELADO e resultados OK/NOK nos logs de avisos de transferência (INSucesso)
#Não aplicável. Status Cancelado não possui resultado preenchido.
#
@CT3.6.1
Cenário: 1) Consultar um agendamento com status de finalizado e resultado OK nos logs de avisos de criação de estrutura do cenário e de execução de cálculo (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu expando o item no grid "Agendamentos de Cenários" com o valor "Cenário: Liminar 1 solicitada por URUGUAIANA; Instalação: CI CV.URUGUAIANA"
Então eu deveria ver o link do campo "Resultado" da atividade "Criação do cenário" da tarefa do cenário "Liminar 1 solicitada por URUGUAIANA" desabilitado
@CT3.6.2
Cenário: 2) Consultar um agendamento com status de finalizado e resultado NÃO OK no log de criação de estrutura do cenário (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "cenários" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu expando o item no grid "Agendamentos de Cenários" com o valor "Cenário: Liminar PDS: Instalação: U.SOBRADINHO"
E eu aperto o link no campo "Resultado" no grid "Agendamentos de Cenários" da atividade "Criação de cenário" da tarefa do cenário "Cenário: Liminar PDS"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Houve geração de críticas"
@CT3.6.3
Cenário: 3) Consultar um agendamento com status de finalizado e resultado NÃO OK no log de aviso de execução de cálculo (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "cenários" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu expando o item no grid "Agendamentos de Cenários" da atividade "Cálculo das taxas" da tarefa do cenário "Liminar PDS"
E eu aperto o link do campo "Resultado" no grid "Agendamentos de Cenários" da tarefa do cenário "Cenário: Liminar PDS"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Houve geração de críticas"
@CT3.6.4
Cenário: 4) Consultar um agendamento com status de AGENDADO e resultado OK nos logs de criação de estrutura do cenário (Sucesso)
#Não aplicável. Status Agendado não possui resultado preenchido.
#
@CT3.6.5
Cenário: 5) Consultar um agendamento com status de AGENDADO e resultado NÃO OK no log de aviso de criação de estrutura do cenário (Sucesso)
#Não aplicável. Status Agendado não possui resultado preenchido.
#
@CT3.7.1
Cenário: 1) Visualizar log para os agendamentos de Execução de Cálculo do Cenário Oficial : OK (Sucesso)
#Não aplicável. Status Finalizado com Resultado OK não possui Log.
#
@CT3.7.2
Cenário: 1) Visualizar log para os agendamentos de Execução de Cálculo do Cenário Oficial NÃO OK (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono todos os itens na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto o link no campo "Resultado" do item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo: 001-2015; U.SOBRADINHO"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Houve geração de críticas"
@CT3.7.3
Cenário: 2) Visualizar log para os agendamentos de Execução de Cálculo do Cenário Alternativo OK (Sucesso)
#Não aplicável. Status Finalizado com Resultado OK não possui Log.
#
@CT3.7.4
Cenário: 2) Visualizar log para os agendamentos de Execução de Cálculo do Cenário Alternativo NÃO OK  (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu expando o item no grid "Agendamentos de Cenários" com o valor "Cenário: Liminar PDS; Data/Hora:01/05/2015 09:56; Situação: Finalizado; Resultado: NOK"
E eu aperto o link no campo "Resultado" da atividade "Cálculo de taxas" do cenário "Liminar PDS" no grid "Agendamentos de Cenários"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Houve geração de críticas"
@CT3.7.5
Cenário: 3) Visualizar log  para os agendamentos de Criação de Estrutura de Cenários OK (Sucesso)
#Não aplicável. Status Finalizado com Resultado OK não possui Log.
#
@CT3.7.6
Cenário: 3) Visualizar log  para os agendamentos de Criação de Estrutura de Cenários NÃO OK (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cenários" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu expando o item no grid "Agendamentos de Cenários" com o valor "Cenário: Liminar PDS; Data/Hora:01/05/2015 09:56; Situação: Finalizado; Resultado: NOK"  
E eu aperto o link no campo "Resultado" da atividade "Criação de Cenário" do cenário "Liminar PDS" no grid "Agendamentos de Cenários"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Houve geração de críticas"
@CT3.7.7
Cenário: 4) Visualizar log para agendamentos de Transferência de eventos  com link para a tarefa de retificação OK (Sucesso) 
#Não aplicável. Status Finalizado com Resultado OK não possui Log nem link para tarefa de retificação.
#
@CT3.7.8
Cenário: 4) Visualizar log para agendamentos de Transferência de eventos  com link para a tarefa de retificação NÃO OK  (Sucesso) 
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu expando o item no grid "Agendamentos de Retificação" com o valor "Tarefa: CNOS T0005/2015"
E eu aperto o link no campo "Resultado" da atividade "Transferência" da tarefa "CNOS T0005/2015"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Houve geração de críticas de sequência de eventos"
E eu deveria ver um link para "Retificar Eventos"
@CT3.7.9
Cenário: 5) Visualizar log para agendamentos de Execução de Cálculo referentes a uma tarefa de retificação OK (Sucesso)
#Não aplicável. Status Finalizado com Resultado OK não possui Log nem link para tarefa de retificação.
#
@CT3.7.10
Cenário: 5) Visualizar log para agendamentos de Execução de Cálculo referentes a uma tarefa de retificação NÃO OK  (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu expando o item no grid "Agendamentos de Retificação" com o valor "Número da Tarefa: CNOS T0005/2015; Data/Hora:01/05/2015 09:56; Situação: Finalizado; Resultado: NOK"
E eu aperto o link do campo "Resultado" da atividade "Cálculo de Taxas" da tarefa "CNOS T0005/2015" no item do grid "Agendamentos de Retificação"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver o valor "Houve geração de críticas"
E eu deveria ver um link para "Retificar Eventos"
@CT3.8.1.1
Cenário: 1) Reagendar um registro com Status agendado origem "Retificação" com data válida (Sucesso): Tarefa solicitado pelo CNOS
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Data/Hora:01/10/2017 09:00; Número da Tarefa: CNOS T0007/2017; Situação: Agendado"  
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
Então eu deveria ver 2 itens no grid "Agendamendos de Retificação"
E eu deveria ver 1 item no grid "Agendamentos de Retificação" com o valor "Número da Tarefa: CNOS T0007/2017 09:00; Data/Hora: 02/10/2017 10:00; Situação: Agendado"
#
@CT3.8.1.2
Cenário: 1) Reagendar um registro com Status agendado origem "Retificação" com data válida (Sucesso): Tarefa solicitado pelo COSR
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "05/10/2017" para o campo "Data Inicial"
E eu informo o valor "05/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Data/Hora:05/10/2017 09:04; Número da Tarefa: COSR T0009/2017; Situação: Agendado"  
E eu aperto o botão "Reagendar"
E eu informo o valor "06/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
Então eu deveria ver 2 itens no grid "Agendamendos de Retificação"
E eu deveria ver 1 item no grid "Agendamentos de Retificação" com o valor "Número da Tarefa: COSR T0009/2017 09:04; Data/Hora: 06/10/2017 10:00; Situação: Agendado"
#
@CT3.8.2
Cenário: 1) Reagendar um registro com Status agendado origem "Cálculo de Taxas" com data válida (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de Usinas
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Data/Hora:01/10/2017 10:00; Instalação: U.SOBRADINHO; Status: Agendado"  
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
Então eu deveria ver 1 itens no grid "Agendamendos de Cálculo de Taxas"
E eu deveria ver 1 item no grid "Agendamentos de Cálculo de Taxas" com o valor "Data/Hora: 02/10/2017 10:00; Status: Agendado"
#
@CT3.8.3
Cenário: 1) Reagendar um registro com Status agendado origem "Retificação" com data inválida (insucesso)
#Caso de teste não reproduzível
#
@CT3.8.4
Cenário: 1) Reagendar um registro com Status agendado origem "Cálculo de Taxas" com data inválida (insucesso)
#Caso de teste não reproduzível
#
@CT3.8.5
Cenário: 1) Reagendar um registro com Status agendado origem "Retificação" com hora inválida (insucesso)
#Caso de teste não reproduzível
#
@CT3.8.6
Cenário: 1) Reagendar um registro com Status agendado origem "Cálculo de Taxas" com hora inválida (insucesso)
#Caso de teste não reproduzível
#
@CT3.8.7
Cenário: 1) Reagendar um registro com Status agendado origem "Retificação" com agendamento duplicado (insucesso)
#Caso de teste removido, pois não faz parte do cenário de teste
#
@CT3.8.8
Cenário: 1) Reagendar um registro com Status agendado origem "Cálculo de Taxas" com agendamento duplicado (insucesso)
#Caso de teste removido, pois não faz parte do cenário de teste
#
@CT3.8.9
Cenário: 1) Reagendar um registro com Status agendado origem "Retificação" informando data/hora anterior a data corrente (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Data/Hora:01/10/2017 09:00; Número da Tarefa: CNOS T0007/2017; Situação: Agendado" 
E eu aperto o botão "Reagendar"
E eu informo o valor "01/10/2016 09:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu deveria ver a mensagem de erro de código "RS_MENS_025"
@CT3.8.10
Cenário: 1) Reagendar um registro com Status agendado origem "Cálculo de Taxas" informando data/hora anterior a data corrente (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de Usinas
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Data/Hora:01/10/2017 10:00; Instalação: U.SOBRADINHO; Status: Agendado"  
E eu aperto o botão "Reagendar"
E eu informo o valor "01/10/2016 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu deveria ver a mensagem de erro de código "RS_MENS_025"
@CT3.8.11
Cenário: 2) Reagendar um registro com Status finalizado origem "Retificação" (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2015" para o campo "Data Inicial"
E eu informo o valor "01/05/2015" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Data/Hora:01/05/2015 09:56; Número da Tarefa: CNOS T0005/2015" 
E eu aperto o botão "Reagendar"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.8.12
Cenário: 2) Reagendar um registro com Status finalizado origem "Cálculo de Taxas" (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Data/Hora:03/06/2016 14:30" e "Instalação:U.SOBRADINHO"  
E eu aperto o botão "Reagendar"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.8.13
Cenário: 3) Reagendar um registro com Status cancelado origem "Retificação" (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/04/2014" para o campo "Data Inicial"
E eu informo o valor "01/04/2014" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Data/Hora:01/04/2014 09:56; Número da Tarefa: CNOS T0005/2014"  
E eu aperto o botão "Reagendar"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.8.14
Cenário: 3) Reagendar um registro com Status cancelado origem "Cálculo de Taxas" (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/04/2014" para o campo "Data Inicial"
E eu informo o valor "01/04/2014" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Data/Hora:01/04/2014 09:56; Instalação:U.SOBRADINHO"  
E eu aperto o botão "Reagendar"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.9.1
Cenário: 1) Executar agora um agendamento com Status agendado origem "Cálculo de Taxas" (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Data/Hora:01/10/2017 10:00; Instalação:U.SOBRADINHO" 
E eu aperto o botão "Executar Agora"
E eu aguardo 10 minutos
Então deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com os valores  "Instalação:U.SOBRADINHO; Data/Hora:DATA_HORA_CORRENTE; status: Agendado"
#
@CT3.9.2.1
Cenário: 1) Executar agora um agendamento com Status agendado origem "Retificação" (Sucesso): retificação solicitada pelo CNOS
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor o valor "Número da Tarefa: CNOS T0007/2017; Data/Hora:01/10/2017 09:00"  
E eu aperto o botão "Executar Agora"
Então deveria ver o item do grid "Agendamentos de Cálculo de Taxas" com os valores "Número da Tarefa: CNOS T0007/2017; Data/Hora:DATA_HORA_CORRENTE; Situação: Agendado"
#
@CT3.9.2.2
Cenário: 1) Executar agora um agendamento com Status agendado origem "Retificação" (Sucesso): retificação solicitada pelo COSR
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "05/10/2017" para o campo "Data Inicial"
E eu informo o valor "05/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor o valor "Número da Tarefa: COSR T0009/2017; Data/Hora:05/10/2017 09:04"  
E eu aperto o botão "Executar Agora"
Então deveria ver o item do grid "Agendamentos de Cálculo de Taxas" com os valores "Número da Tarefa: COSR T0009/2017; Data/Hora:DATA_HORA_CORRENTE; Situação: Agendado"
#
@CT3.9.3
Cenário: 2) Executar agora um agendamento com Status finalizado origem "Cálculo de Taxas" (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo: 001-2016; Data/Hora:03/06/2016 14:30; Instalação:U.SOBRADINHO" 
E eu aperto o botão "Executar Agora"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.9.4
Cenário: 2) Executar agora um agendamento com Status finalizado origem "Retificação" (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Número da Tarefa: COSR T0007/2016; Data/Hora:03/06/2016 15:30" 
E eu aperto o botão "Executar Agora"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.9.5
Cenário: 3) Executar agora um agendamento com Status cancelado origem "Cálculo de Taxas" (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/04/2014" para o campo "Data Inicial"
E eu informo o valor "01/04/2014" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo: 001-2014; Data/Hora:01/04/2014 09:56; Instalação:U.SOBRADINHO" 
E eu aperto o botão "Executar Agora"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.9.6
Cenário: 3) Executar agora um agendamento com Status cancelado origem "Retificação" (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/04/2014" para o campo "Data Inicial"
E eu informo o valor "01/04/2014" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Número da Tarefa: CNOS T0005/2014; Data/Hora:01/04/2014 09:56; Situação:Cancelado" 
E eu aperto o botão "Executar Agora"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.10.1
Cenário: 1) Cancelar um agendamento com Status agendado origem "Cálculo de Taxas" (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"  
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo: 001-2016; Data/Hora:03/06/2016 14:30; Instalação:U.SOBRADINHO" 
E eu aperto o botão "Cancelar"
Então deveria ver o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo: 001-2016; Instalação:U.SOBRADINHO; Data/Hora:DATA/HORA CORRENTE; Status: Cancelado"
#
@CT3.10.2
Cenário: 1) Cancelar um agendamento com Status agendado origem "Retificação" (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-S" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "05/10/2017" para o campo "Data Inicial"
E eu informo o valor "05/10/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Número da Tarefa: COSR T0009/2017; Data/Hora:05/10/2017 09:04; Situação: Agendado"  
E eu aperto o botão "Cancelar"
Então deveria ver o item do grid "Agendamentos de Retificações" com com o valor "Número da Tarefa: COSR T0009/2017; Data/Hora:DATA/HORA CORRENTE; Situação: Cancelado"
#
@CT3.10.3
Cenário: 2) Cancelar um agendamento com Status finalizado origem "Cálculo de Taxas" (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"  
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo: 001-2016; Data/Hora:03/06/2016 14:30; Instalação:U.SOBRADINHO" 
E eu aperto o botão "Cancelar"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.10.4
Cenário: 2) Cancelar um agendamento com Status finalizado origem "Retificação" (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"  
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "03/06/2016" para o campo "Data Inicial"
E eu informo o valor "03/06/2016" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Número da Tarefa: 001-2016; Data/Hora:03/06/2016 14:30; Instalação:U.SOBRADINHO" 
E eu aperto o botão "Cancelar"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.10.5
Cenário: 3) Cancelar um agendamento com Status cancelado origem "Cálculo de Taxas" (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"   
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/04/2014" para o campo "Data Inicial"
E eu informo o valor "01/04/2014" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" 
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo: 001-2014; Data/Hora:01/04/2014 09:56; Instalação:U.SOBRADINHO" 
E eu aperto o botão "Cancelar"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
@CT3.10.6
Cenário: 3) Cancelar um agendamento com Status cancelado origem "Retificação" (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"  
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/04/2014" para o campo "Data Inicial"
E eu informo o valor "01/04/2014" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item no grid "Agendamentos de Retificação" com o valor "Número da Tarefa: CNOS T0005/2014; Data/Hora:01/04/2014 09:56; Situação: Cancelado" 
E eu aperto o botão "Cancelar"
E eu deveria ver a mensagem de erro de código "RS_MENS_123"
