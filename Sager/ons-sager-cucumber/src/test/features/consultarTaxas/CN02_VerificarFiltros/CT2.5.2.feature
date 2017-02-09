# language: pt

Funcionalidade: Consultar taxas informando filtro pelo nome da instalação

Cenário: Usar um nome de instalação usina inexistente (Insucesso)

Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar taxas"
Quando eu informo o valor "01/2008" para o campo "Mês inicial"
E eu informo o valor "04/2008" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a lista "Usina"
E eu informo o valor "OGNIX ANISU" no campo "Nome" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu não deveria ver nenhuma taxa de "Usinas"
E fim do teste