# language: pt

Funcionalidade: Solicitar cálculo filtrando pelo nome do agente

Cenário: Testar o comportamento do filtro de Agente para a seleção da instalação Usar um nome de agente de usina existente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu informo o valor "FURNAS" para o campo "Agente" na lista "Usinas"
	E eu seleciono todos os itens da lista "Usinas"
	E eu aperto o botão "Agendar Cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"

Cenário: Testar o comportamento do filtro de Agente para a seleção da instalação Usar um nome de agente de usina inexistente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "ZZZ" para o campo "Agente" na lista "Usinas"
	Então eu deveria ver 0 itens na lista "Usinas"

Cenário: Testar o comportamento do filtro de Agente para a seleção da instalação Usar um nome de agente de Interligação internacional existente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "ELETROSUL" para o campo "Agente" na lista "Interligações Internacionais"
	E eu seleciono todos os itens da lista "Interligações Internacionais"
	E eu aperto o botão "Agendar Cálculo" 
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 7 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.URUGUAIANA"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro de Agente para a seleção da instalação Usar um nome de agente de  Interligação internacional  inexistente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "ZZZ" para o campo "Agente" na lista "Interligações Internacionais"
	Então eu deveria ver 0 itens na lista "Interligações Internacionais"

Cenário: Testar o comportamento do filtro de Agente para a seleção da instalação Escolher mais de um agente por nome e por instalação
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu informo o valor "CHESF" para o campo "Agente" na lista "Usinas"
	E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
	E eu informo o valor "COPEL-GT" para o campo "Agente" na lista "Usinas"
	E eu seleciono o item "BENTO MUNHOZ" na lista "Usinas"
	E eu aperto o botão "Agendar Cálculo" 
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 5 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro de Agente para a seleção da instalação Escolher mais de um agente por nome e por instalação
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "ELETROSUL" para o campo "Agente" na lista "Interligações Internacionais"
	E eu seleciono o item "CI LIVRAMENTO 2" na lista "Interligações Internacionais"
	E eu informo o valor "CIEN" para o campo "Agente" na lista "Interligações Internacionais"
	E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
	E eu aperto o botão "Agendar Cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
#	E eu deveria ver 5 itens no grid "Agendamentos pré-existentes"
#	E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 2"
#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro de Agente para a seleção da instalaçãoEscolher agente por nome de agente e instalação usina existente e um agente por nome e instalação usina inexistente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "CHESF" para o campo "Agente" na lista "Usinas"
	E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
	E eu informo o valor "ZZZ" para o campo "Agente" na lista "Usinas"
	Então eu deveria ver 0 itens na lista "Usinas"
	#	E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: U.SOBRADINHO"

Cenário: Testar o comportamento do filtro de Agente para a seleção da instalação Escolher um agente por nome e instalação interligação Internacional existente e um um agente por nome e instalação interligação Internacional inexistente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "ELETROSUL" para o campo "Agente" na lista "Interligações Internacionais"
	E eu seleciono o item "CI LIVRAMENTO 2" na lista "Interligações Internacionais"
	E eu informo o valor "ZZZ" para o campo "Agente" na lista "Interligações Internacionais"
	Então eu deveria ver 0 itens na lista "Interligações Internacionais"
	#	E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: CI LIVRAMENTO 2"

