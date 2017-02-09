# language: pt
Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil CNOS
@CT1.1
Cenário: Verificar autorização de acesso a funcionalidade (Sucesso)
Dado que eu esteja na página "Autenticação"
Quando eu me autentico com usuário "cnos" e perfil "CNOS"
Então a funcionalidade "Consultar agendamentos" deveria estar disponível
#Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil COSR
@CT1.2
Cenário: Verificar autorização de acesso a funcionalidade (Sucesso)
Dado que eu esteja na página "Autenticação"
Quando eu me autentico com usuário "cosr-ne1" e perfil "COSR-NE" 
Então a funcionalidade "Consultar agendamentos" deveria estar disponível
#Funcionalidade: Acessar a funcionalidade com um usuário que tenha perfil diferente de CNOS e COSR
@CT1.3
Cenário: Verificar autorização de acesso a funcionalidade (Insucesso)
Dado que eu esteja na página "Autenticação"
Quando eu me autentico com usuário "chesf" e perfil "Agente"
Então a funcionalidade "Consultar agendamentos" deveria estar indisponível
