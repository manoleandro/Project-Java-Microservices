# language: pt

Funcionalidade: Consultar taxas informando filtro por tipo de taxa

Cenário: Selecionar TEIP mensal (Sucesso); 

Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "04/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIFa mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "USINA XINGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 item no grid "USINA XINGO"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "01/2008"; "TEIFa mensal": "0,000736"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "02/2008"; "TEIFa mensal": "0,002984"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "03/2008"; "TEIFa mensal": "0,172301"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "04/2008"; "TEIFa mensal": "0,402023"
E fim do teste