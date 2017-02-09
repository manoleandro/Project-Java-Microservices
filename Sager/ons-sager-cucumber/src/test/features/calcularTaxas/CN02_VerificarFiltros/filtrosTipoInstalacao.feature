# language: pt

Funcionalidade: Solicitar cálculo informando filtro por tipo de instalação

Cenário: Testar o comportamento do filtro tipo de  instalação Sem escolher instalação (Insucesso)
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto o botão "Agendar cálculo"
	#	Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Instalação"

Cenário: Testar o comportamento do filtro tipo de  instalação Escolhendo algumas usinas (Sucesso)
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
	E eu seleciono o item "CAMPOS" na lista "Usinas"
	E eu aperto o botão "Agendar cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 8 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro tipo de  instalação Escolhendo algumas interligações internacionais  (Sucesso)
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Interligações Internacionais"
	E eu seleciono o item "CI LIVRAMENTO 2" na lista "Interligações internacionais"
	E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações internacionais"
	E eu aperto o botão "Agendar cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 7 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.URUGUAIANA"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro tipo de  instalação Escolhendo todas as usinas  (Sucesso)
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Usinas"
	E eu seleciono todos os itens da lista "Usinas"
	E eu aperto o botão "Agendar cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
	#	E eu deveria ver 11 itens no grid "Agendamentos pré-existentes"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
	#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
	#	E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"
	#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: UT MARIO LAGO"
	#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")

Cenário: Testar o comportamento do filtro tipo de  instalação Escolhendo todas as interligações internacionais  (Sucesso)
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Solicitar cálculo de taxa"
	Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
	E eu informo o valor "Abril 2010" para o campo "Mês final"
	E eu aperto a guia "Interligações Internacionais"
	E eu seleciono todos os itens da lista "Interligações Internacionais"
	E eu aperto o botão "Agendar cálculo"
	Então eu deveria ver o modal "Agendamento de cálculo"
#	E eu deveria ver 13 itens no grid "Agendamentos pré-existentes"
#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 1"
#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 2"
#	E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
#	E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.URUGUAIANA"
#	E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI ACARAY"
#	(realizar a consulta na guia "Cálculo Taxas (Agend existente)")


