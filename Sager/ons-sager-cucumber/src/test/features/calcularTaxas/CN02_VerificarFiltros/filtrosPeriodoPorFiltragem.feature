## language: pt

Funcionalidade: Solicitar cálculo informando período para filtragem

Cenário: Solicitar cálculo informando período para filtragem
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu seleciono todos os itens da lista "Usinas"
	E eu aperto o botão "Agendar cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"

Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo: Mês Inicial maior que Mês Final
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	E eu informo o valor "Abril 2010" para o campo "Mês inicial"
	E eu informo o valor "Março 2010" para o campo "Mês final"
	E eu seleciono todos os itens da lista "Usinas"
	E eu aperto o botão "Agendar cálculo"
	#	Então eu deveria ver a mensagem de erro de código "RS_MENS_004"

Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Inicial maior ou igual a Data Corrente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2030" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu seleciono todos os itens da lista "Usinas"
	E eu aperto o botão "Agendar cálculo"
	#	Então eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Mês inicial"
	#	E eu deveria ver a mensagem de erro de código "RS_MENS_004"

Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Final maior ou igual a Data Corrente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Março 2030" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu seleciono todos os itens da lista "Usinas"
	E eu aperto o botão "Agendar cálculo"
#		Então eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Mês final"

Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Inicial menor que 01/01/2001
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2000" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu seleciono todos os itens da lista "Usinas"
	E eu aperto a guia "Usinas"
	E eu aperto o botão "Agendar cálculo"
	#	Então eu deveria ver a mensagem de erro "RS_MENS_022" e crítica "Mês inicial"

Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo: Mês Inicial sem preenchimento
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês final"
	E eu aperto o botão "Agendar cálculo"
	#	Então eu deveria ver a mensagem de erro "RS_MENS_002" e crítica "Mês inicial"

Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Final sem prenchimento
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Abril 2010" para o campo "Mês inicial"
	E eu aperto o botão "Agendar cálculo"
	#	Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Mês final"
