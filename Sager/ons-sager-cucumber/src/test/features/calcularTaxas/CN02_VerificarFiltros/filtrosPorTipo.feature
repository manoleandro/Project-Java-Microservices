# language: pt

Funcionalidade: Solicitar cálculo informando filtro por tipo

Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas:Não escolher o tipo de usina
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu seleciono todos os itens da lista "Usinas" 
	E eu aperto o botão "Agendar Cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 11 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
	#	E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"
	#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: UT MARIO LAGO"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas:Escolher Hidrelétricas
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu informo o valor "UHE" para o campo "Tipo" na lista "Usinas"
	E eu seleciono todos os itens da lista "Usinas" 
	E eu aperto o botão "Agendar Cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 5 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
	#	E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"

Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas: Escolher Termelétricas
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu informo o valor "UTE" para o campo "Tipo" na lista "Usinas"
	E eu seleciono todos os itens da lista "Usinas" 
	E eu aperto o botão "Agendar Cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 6 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
	#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: UT MARIO LAGO"

Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas: Escolher tipo inexistente de usina
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "ZZZ" para o campo "Tipo" na lista "Usinas"
	Então eu deveria ver 0 itens na lista "Usinas"

Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas: Escolher tipo inexistente de usina
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "ZZZ" para o campo "Tipo" na lista "Usinas"
	Então eu deveria ver 0 itens na lista "Usinas"

Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas: Escolher mais de um tipo de usina
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu informo o valor "UHE" para o campo "Tipo" na lista "Usinas"
	E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
	E eu informo o valor "UTE" para o campo "Tipo" na lista "Usinas"
	E eu seleciono o item "CAMPOS" na lista "Usinas" 
	E eu aperto o botão "Agendar Cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 8 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
	#	(realizar a consulta na guia "Agendamentos (Calc Taxas)")


