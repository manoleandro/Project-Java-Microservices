# language: pt
Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil CNOS
@CT1.1.1
Cenário: Testar o comportamento do sistema quando utilizado o usuário CNOS (Sucesso).
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "cnos" e perfil "CNOS"
Então a funcionalidade "Consultar taxas" deveria estar disponível
#Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil COSR
@CT1.1.2
Cenário: Testar comportamento do sistema quando utilizado o usuário COSR (Sucesso).
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "cosr-nco1" e perfil "COSR-NCO"
Então a funcionalidade "Consultar taxas" deveria estar disponível
#Funcionalidade: Acessar a funcionalidade com um usuário que tenha perfil diferente de CNOS e COSR
@CT1.2.1
Cenário: Testar comportamento do sistema quando utilizado o usuário Agente (Insucesso).
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "chesf" e perfil "Agente"
Então eu não deveria ver a funcionalidade "Consultar taxas"
