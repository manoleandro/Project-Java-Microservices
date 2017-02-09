## language: pt

Funcionalidade: Inclusão de Data/Hora Agendamento

#Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasUsar um nome de instalação usina existente 
#	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#	E eu esteja na página "Solicitar cálculo de taxa"
#	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#	E eu informo o valor "Abril 2010" para o campo "Mês final"
#	E eu aperto a guia "Usinas"
#	E eu informo o valor "SOBRA" para o campo "Nome" na lista "Usinas"
#	E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#	E eu aperto o botão "Agendar Cálculo"
	
Cenário: Consultar TEIP acumulada para uma instalação: a) Usina (Sucesso)
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Consultar taxas"
	Quando eu informo o valor "04/2020" para o campo "Mês inicial"
	E eu informo o valor "01/2020" para o campo "Mês final"
	E eu seleciono o tipo de taxa "TEIP mensal"
	E eu aperto a lista "Usinas"
	E eu seleciono o elemento "CAMPOS" na lista "Usinas"
	E eu aperto o botão "Pesquisar"
	Então eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Mês inicial"; "Mês final"

#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Consultar taxas"
#Quando eu informo o valor "01/2008" para o campo "Mês inicial"
#E eu informo o valor "02/2008" para o campo "Mês final"
#E eu seleciono o tipo de taxa "TEIP mensal"
#E eu seleciono o tipo de taxa "TEIF mensal"
#E eu aperto a lista "Usinas"
#E eu seleciono o elemento "USINA XINGO" na lista "Usinas"
#E eu seleciono o elemento "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Pesquisar"
#Então eu deveria ver 2 itens no grid "USINA XINGO"
#E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "01/2008"; "TEIP mensal": "0,01711"; "TEIF mensal": "0,00189744204"
#E eu deveria ver um item no grid "USINA XINGO" com valor: Mês Referência: "02/2008"; TEIP mensal": "0,032232"; "TEIF mensal": "0,148415595"
#E eu deveria ver 2 itens no grid "CAMPOS"
#E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "01/2008"; "TEIP mensal": "0,319725"; "TEIF mensal": "0,262479395"
#E eu deveria ver um item no grid "CAMPOS" com valor: Mês Referência: "02/2008"; "TEIP mensal": "0,001789"; "TEIF mensal": "0,198454306"

	
	
	
	