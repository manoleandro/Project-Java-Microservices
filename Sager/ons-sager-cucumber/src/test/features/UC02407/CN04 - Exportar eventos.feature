# language: pt
Funcionalidade: Exportar eventos para Excel
@CT4.1.1
Cenário: Exportar a lista de Eventos sem aplicação de filtro
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "03/01/2001" para o campo "Data Final"
E eu aperto a guia "Usina"
E eu seleciono todos os itens na lista "Usinas" 
E eu aperto o botão "Pesquisar"
E eu aperto o botão "Exportar Excel"
Então eu deveria poder baixar um arquivo "Eventos"
@CT4.1.2
Cenário: Exportar a lista de Eventos com aplicação de filtro
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usina"
E eu seleciono todos os itens na lista "Usinas" 
E eu aperto o botão "Pesquisar"
E eu informo o valor "SOBRA" para o campo "Instalação" no grid "Eventos"
E eu aperto o botão "Exportar Excel"
Então eu deveria poder baixar um arquivo "Eventos"
@CT4.2.1
Cenário: Exportar a lista de Eventos sem aplicação de filtro
Dado que eu esteja autenticado com usuário  "cosr-ne1" e perfil "COSR-NE"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "03/01/2001" para o campo "Data Final"
E eu aperto a guia "Usina"
E eu seleciono todos os itens na lista "Usinas" 
E eu aperto o botão "Pesquisar"
E eu aperto o botão "Exportar Excel"
Então eu deveria poder baixar um arquivo "Eventos"
@CT4.2.2
Cenário: Exportar a lista de Eventos com aplicação de filtro
Dado que eu esteja autenticado com usuário  "cosr-ne1" e perfil "COSR-NE"
E eu esteja na página "Consultar eventos"
Quando eu informo o valor "01/01/2001" para o campo "Data Inicial"
E eu informo o valor "15/01/2001" para o campo "Data Final"
E eu aperto a guia "Usina"
E eu seleciono todos os itens na lista "Usinas" 
E eu aperto o botão "Pesquisar"
E eu informo o valor "SOBRA" para o campo "Instalação" no grid "Eventos"
E eu aperto o botão "Exportar Excel"
Então eu deveria poder baixar um arquivo "Eventos"
