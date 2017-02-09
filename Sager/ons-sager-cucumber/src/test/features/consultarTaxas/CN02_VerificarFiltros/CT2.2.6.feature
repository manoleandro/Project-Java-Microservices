# language: pt

Funcionalidade: Consultar taxas informando período para filtragem

Cenário: Consultar taxas informando período para filtragem

Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "04/2020" para o campo "Mês inicial"
E eu informo o valor "01/2020" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Mês inicial"; "Mês final"
E fim do teste