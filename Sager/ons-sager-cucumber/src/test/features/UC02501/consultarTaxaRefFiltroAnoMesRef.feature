# language: pt

Funcionalidade: Consultar Taxa de Referência filtrando pelo Ano/Mês de referência

Cenário: Testar o comportamento do filtro Ano/Mês de Referência para a seleção das Taxas de Referência: Pesquisar um Ano/Mês de Referência existente com usuário "cnos" e perfil "CNOS"  	
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "2014-06" no campo "Mês de Referência" na guia "Usinas"
	Então eu deveria ver 1 itens na lista "Taxas de Referência - Usinas"
	E eu deveria ver um item na lista "Taxas de Referência - Usinas" com os valores Instalação: "CANDIOTA III"; Mês Ref: "2014-06"; TEIF Ref: "0,16438"; IP Ref: "0,10274"

Cenário: Testar o comportamento do filtro Ano/Mês de Referência para a seleção das Taxas de Referência: Pesquisar um Ano/Mês de Referência existente com usuário "cosr-s1" e perfil "COSR-S" 
	Dado que eu esteja autenticado com usuário "cosr-S1" e perfil "COSR-S" 
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "2001-01" no campo "Mês de Referência" na guia "Interligações Internacionais"
	Então eu deveria ver 2 itens na lista "Taxas de Referência - Interligações Internacionais"
	E eu deveria ver um item na lista "Taxas de Referência - Interligações Internacionais" com os valores Instalação: "CI CV.GARABI 1"; Mês Ref: "2001-01"; TEIF Ref: "0"; IP Ref: "0,03"
	E eu deveria ver um item na lista "Taxas de Referência - Interligações Internacionais" com os valores Instalação: "CI CV.GARABI 2"; Mês Ref: "2001-01"; TEIF Ref: "0"; IP Ref: "0.0274"

Cenário: Testar o comportamento do filtro Ano/Mês de Referência para a seleção das Taxas de Referência: Pesquisar um Ano/Mês de Referência inexistente com usuário "cnos" e perfil "CNOS" 
	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Usinas"
	E eu informo o valor "1973-11" no campo "Mês de Referência" na guia "Usinas"
	Então eu deveria ver 0 itens na lista "Taxas de Referência - Usinas"

Cenário: Testar o comportamento do filtro Ano/Mês de Referência para a seleção das Taxas de Referência: Pesquisar um Ano/Mês de Referência inexistente com usuário "cosr-s1" e perfil "COSR-S" 
	Dado que eu esteja autenticado com usuário "cosr-s1" e perfil "COSR-S" 
	E eu esteja na página "Consultar taxas de referência"
	Quando eu aperto a guia "Interligações Internacionais"
	E eu informo o valor "2016-01" no campo "Mês de Referência" na guia "Interligações Internacionais"
	Então eu deveria ver 0 itens na lista "Taxas de Referência - Interligações Internacionais"
