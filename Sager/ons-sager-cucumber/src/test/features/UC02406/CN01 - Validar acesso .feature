# language: pt
Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil CNOS
@CT1.1
Cenário: 
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "cnos" e perfil "CNOS"
Então o botão "Notificações" deveria estar habilitado
#Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil COSR
@CT1.2
Cenário: 
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "cosr-ne1" e perfil "COSR-NE"
Então o botão "Notificações" deveria estar habilitado
#Funcionalidade: Acessar a funcionalidade com um usuário que tenha perfil diferente de CNOS e COSR
@CT1.3
Cenário: 
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "chesf" e perfil "Agente"
Então o botão "Notificações" deveria estar habilitado
