# language: pt

Funcionalidade: Consultar taxas informando filtro pelo nome da instalação

Cenário: Escolhendo algumas usinas (Sucesso)

Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "02/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "USINA XINGO" na lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "USINA XINGO"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "01/2008"; "TEIP mensal": "0,01711"; "TEIF mensal": "0,00189744204"
E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "02/2008"; TEIP mensal": "0,032232"; "TEIF mensal": "0,148415595"
E eu deveria ver 2 itens no grid "CAMPOS"
E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "01/2008"; "TEIP mensal": "0,319725"; "TEIF mensal": "0,262479395"
E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "02/2008"; "TEIP mensal": "0,001789"; "TEIF mensal": "0,198454306"
