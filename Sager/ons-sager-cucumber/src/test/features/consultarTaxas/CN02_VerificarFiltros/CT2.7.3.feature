# language: pt

Funcionalidade: Consultar taxas informando filtro pelo nome da instalação

Cenário: Usar um nome de agente de Interligação internacional existente (Sucesso)

Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "01/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP acumulada"
E eu seleciono o tipo de taxa "TEIF acumulada"
E eu seleciono o tipo de taxa "TEIFa acumulada"
E eu aperto a lista "Usinas"
E eu informo o valor "CIEN" no campo "Agente" na lista "Interligações Internacionais"
E eu seleciono o elemento "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 item no grid "CI CV.GARABI 1"
E eu deveria ver um item no grid "CI CV.GARABI 1" com valor: Mês Referência: "01/2008"; "TEIP acumulada": "0,292148"; "TEIF acumulada": "0,158050597"; "TEIFa acumulada": "0,00002"