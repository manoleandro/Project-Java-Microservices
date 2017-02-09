# language: pt

Funcionalidade: Consultar taxas informando filtro por tipo de taxa

Cenário: Selecionar TEIP mensal (Sucesso); 

Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "04/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIF oper mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "USINA XINGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 item no grid "USINA XINGO"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "01/2008"; "TEIF oper mensal": "0,00189744204"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "02/2008"; "TEIF oper mensal": "0,148415595"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "03/2008"; "TEIF oper mensal": "0,191511095"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "04/2008"; "TEIF oper mensal": "0,402023494"
E fim do teste