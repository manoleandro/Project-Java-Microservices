# language: pt

Funcionalidade: Exportar para Excel
	
Cenário: Exportar para Excel Quando não houver informações de taxas exibidas em tela
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "9999" no campo "TEIF" na guia "Usinas"
	E eu aperto o botão "Exportar Excel"
	Então eu deveria ver a mensagem de erro de código "RS_MENS_015"
	#	Mensagem: Não existem dados para os filtros selecionados.

Cenário: Exportar para Excel Quando não houver informações de taxas exibidas em tela
	Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "9999" no campo "TEIF" na guia "Interligações Internacionais"
	E eu aperto o botão "Exportar Excel"
	Então eu deveria ver a mensagem de erro de código "RS_MENS_015"

Cenário: Exportar para Excel Quando houver informações de taxas exibidas em tela com usuário "cnos" e perfil "CNOS"
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Usinas"
	E eu aperto o botão "Exportar Excel"
	Então eu deveria poder baixar um arquivo Excel "Taxas de Referência"

Cenário: Exportar para Excel Quando houver informações de taxas exibidas em tela com usuário "cosr-ne1" e perfil "COSR-NE"
	Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Usinas"
	E eu aperto o botão "Exportar Excel"
	Então eu deveria poder baixar um arquivo Excel "Taxas de Referência"

Cenário: Exportar para Excel Quando houver informações de taxas filtradas em tela com usuário "cnos" e perfil "CNOS"
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "UT" no campo "Instalação" na guia "Usinas"
	E eu aperto o botão "Exportar Excel"
	Então eu deveria poder baixar um arquivo Excel "Taxas de Referência"

Cenário: Exportar para Excel Quando houver informações de taxas filtradas em tela com usuário "cosr-se1" e perfil "COSR-SE"
	Dado que eu esteja autenticado com usuário "cosr-se1" e perfil "COSR-SE" 
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "UT" no campo "Instalação" na guia "Usinas"
	E eu aperto o botão "Exportar Excel"
	Então eu deveria poder baixar um arquivo Excel "Taxas de Referência"
 