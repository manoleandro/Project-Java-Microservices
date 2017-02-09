# language: pt

Funcionalidade: Consultar taxas informando período para filtragem

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
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "01/2008"; "TEIP acum": "0,00728762895"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "02/2008"; "TEIP acum": "0,00731744198"
E eu deveria ver 2 itens no grid "CAMPOS"
E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "01/2008"; "TEIP acum": "0,0173869599"
E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "02/2008"; "TEIP acum": "0,0178939104"