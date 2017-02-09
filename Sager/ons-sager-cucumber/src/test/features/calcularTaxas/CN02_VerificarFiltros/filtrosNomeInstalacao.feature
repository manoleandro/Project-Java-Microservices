# language: pt

Funcionalidade: Solicitar cálculo filtrando pelo nome da instalação

Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasUsar um nome de instalação usina existente 
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu informo o valor "SOBRA" para o campo "Nome" na lista "Usinas"
	E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
	E eu aperto o botão "Agendar Cálculo"
	#	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
	#	E todo item no grid "Agendamentos pré-existentes" deveria ter o valor "Instalação: U.SOBRADINHO"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasUsar um nome de instalação usina inexistente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "ZZZ" para o campo "Nome" na lista "Usinas"
	Então eu deveria ver 0 itens na lista "Usinas"

Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmas Usar um nome de instalação Interligação internacional existente 
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "GARABI" para o campo "Nome" na lista "Interligações Internacionais"
	E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
	E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações Internacionais"
	E eu aperto o botão "Agendar cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 1"
	#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 2"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasUsar um nome de instalação  Interligação internacional  inexistente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "ZZZ" para o campo "Nome" na lista "Interligações Internacionais"
	Então eu deveria ver 0 itens na lista "Interligações Internacionais"

Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasEscolher mais de um nome de instalação existente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "CI CV.GARABI 1" para o campo "Nome" na lista "Interligações Internacionais"
	E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
	E eu informo o valor "CI ACARAY" para o campo "Nome" na lista "Interligações Internacionais"
	E eu seleciono o item "CI ACARAY" na lista "Interligações internacionais"
	E eu aperto o botão "Agendar cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 1"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI ACARAY"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmas Escolher um nome de instalação usina existente e um nome instalação usina inexistente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "U.SOBRADINHO" para o campo "Nome" na lista "Usinas"
	E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
	E eu informo o valor "ZZZ" para o campo "Nome" na lista "Usinas"
	Então eu deveria ver 0 itens na lista "Usinas"
	#	E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: U.SOBRADINHO"

Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmas Escolher um nome de instalação interligação Internacional existente e um nome instalação interligação Internacional inexistente
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "CI CV.GARABI 1" para o campo "Nome" na lista "Interligações internacionais"
	E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações internacionais"
	E eu informo o valor "ZZZ" para o campo "Nome" na lista "Interligações internacionais"
	Então eu deveria ver 0 itens na lista "Interligações Internacionais"
#		E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: CI CV.GARABI 1"


