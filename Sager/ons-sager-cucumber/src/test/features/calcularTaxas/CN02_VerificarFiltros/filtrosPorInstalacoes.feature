# language: pt

Funcionalidade: Solicitar cálculo escolhendo instalações

Cenário: Testar as seguintes condições: Não escolher Usinas ou Interligações Internacionais (insucesso)
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto o botão "Agendar Cálculo" 
	#	Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Instalação"

Cenário: Testar as seguintes condições: Escolher apenas uma Usina
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
	E eu aperto o botão "Agendar Cálculo" 
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar as seguintes condições: Escolher apenas uma Interligação Internacional
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Interligações Internacionais"
	E eu seleciono o item "CI LIVRAMENTO 2" na lista "Interligações Internacionais"
	E eu aperto o botão "Agendar Cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 3 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar as seguintes condições: Escolher todas as Usinas
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu seleciono todos os itens da lista "Usinas"
	E eu aperto o botão "Agendar Cálculo" 
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 11 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
	#	E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"
	#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: UT MARIO LAGO"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")


Cenário: Testar as seguintes condições: Escolher todas as Interligações Internacionais
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando  eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Interligações Internacionais"
	E eu seleciono todos os itens da lista "Interligações Internacionais"
	E eu aperto o botão "Agendar Cálculo" 
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 13 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 1"
	#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 2"
	#	E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.URUGUAIANA"
	#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI ACARAY"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
