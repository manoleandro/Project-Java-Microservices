# language: pt

Funcionalidade: Consultar taxas informando filtro pelo nome da instalação

Cenário: Usar um nome de agente de usina existente (Sucesso)

Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "04/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu seleciono o tipo de taxa "TEIFa mensal"
E eu aperto a lista "Usinas"
E eu informo o valor "UTE" no campo "Tipo" na lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 4 itens no grid "CAMPOS"
E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "01/2008"; "TEIP mensal": "0,319725"; "TEIF mensal": "0,262479395"; "TEIFa mensal": "0,207263"
E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "02/2008"; "TEIP mensal": "0,001789"; "TEIF mensal": "0,198454306"; "TEIFa mensal": "0,192284"
E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "03/2008"; "TEIP mensal": "0,129514"; "TEIF mensal": "0,366282314"; "TEIFa mensal": "0,3125"
E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "04/2008"; "TEIP mensal": "0,009309"; "TEIF mensal": "0,736644626"; "TEIFa mensal": "0,488413"