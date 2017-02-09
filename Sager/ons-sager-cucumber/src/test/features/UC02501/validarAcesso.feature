# language: pt

#Funcionalidade: Validar acesso
 	
#Cenário: Acessar a funcionalidade com um usuário que tenha o perfil CNOS
#	Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#	Então a funcionalidade "Consultar taxas de referência" deveria estar disponível
	
#Cenário: Acessar a funcionalidade com um usuário que tenha o perfil COSR
#	Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
#	Então a funcionalidade "Consultar taxas de referência" deveria estar disponível
#	
#Cenário: Acessar a funcionalidade com um usuário que tenha perfil diferente de CNOS e COSR
#	Dado que eu esteja autenticado com usuário "chesf" e perfil "Agente-NE"
#	Então eu não deveria ver a funcionalidade "Consultar taxas de referência"