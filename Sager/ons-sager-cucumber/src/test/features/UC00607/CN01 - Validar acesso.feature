## language: pt
#
Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil CNOS
##@CT1.1
Cenário: Dado que eu esteja na tela "Autenticação"
Quando eu me autenticado com usuário "cnos" e perfil "CNOS"
Então a funcionalidade "Consultar Parametrização" deveria estar disponível
#
##Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil COSR
##@CT1.2
Cenário: Dado que eu esteja na tela "Autenticação"
#Quando eu me autenticado com usuário "cosr-ne1" e perfil "COSR-NE"
#Então eu não deveria ver a funcionalidade "Consultar Parametrização"
#
###Funcionalidade: Acessar a funcionalidade com um usuário que tenha perfil diferente de CNOS e COSR
##@CT1.3
Cenário: Dado que eu esteja na tela "Autenticação"
#Quando eu me autenticado com usuário "chesf" e perfil "Agente"
#Então eu não deveria ver a funcionalidade "Consultar Parametrização"