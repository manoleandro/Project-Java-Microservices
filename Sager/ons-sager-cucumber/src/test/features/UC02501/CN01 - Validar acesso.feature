# language: pt
Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil CNOS
@CT1.1
Cenário: 
Dado que eu esteja na página "Autenticação"
Quando eu me autentico com usuário "cnos" e perfil "CNOS"
Então a funcionalidade "Consultar taxas de referência" deveria estar disponível
#Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil COSR
@CT1.2
Cenário: 
Dado que eu esteja na página "Autenticação"
Quando eu me autentico com usuário "cosr-ne1" e perfil "COSR-NE"
Então a funcionalidade "Consultar taxas de referência" deveria estar disponível
#Funcionalidade: Acessar a funcionalidade com um usuário que tenha perfil diferente de CNOS e COSR
@CT1.3.1
Cenário: 
Dado que eu esteja na página "Autenticação"
Quando eu me autentico com usuário "chesf" e perfil "Agente-NE"
Então eu não deveria ver a funcionalidade "Consultar Taxas de referência"
