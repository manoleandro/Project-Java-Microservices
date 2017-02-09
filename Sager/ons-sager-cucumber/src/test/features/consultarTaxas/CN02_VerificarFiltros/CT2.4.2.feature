# language: pt

Funcionalidade: Consultar taxas informando filtro pelo nome da instalação

Cenário: Escolhendo algumas usinas e interligações internacionais (Insucesso)

Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "04/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a lista "Usinas"
E eu seleciono o elemento "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver o grid "U.SOBRADINHO"
E eu deveria ver a guia "Interligação Internacionais" indisponível
E fim do teste