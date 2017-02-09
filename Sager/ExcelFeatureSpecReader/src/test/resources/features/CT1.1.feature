# language: pt

Funcionalidade: Realizar Agendamento de Cálculo (Sucesso)

@CT1.1.1
Cenário: Verificar autorização de acesso a funcionalidade (Sucesso)

Dado que eu esteja na página ""Autenticação""
Quando eu me autenticado com usuário ""cnos"" e perfil ""CNOS"""
Então a funcionalidade "Solicitar cálculo de taxa" deveria estar disponível

@CT1.2.1
Cenário: Verificar autorização de acesso a funcionalidade (Insucesso)

Dado que eu esteja na página ""Autenticação""
Quando eu me autenticado com usuário ""cosr-ne1"" e perfil ""COSR-NE"""
Então eu não deveria ver a funcionalidade "Solicitar cálculo de taxa"

@CT1.2.2
Cenário: Verificar autorização de acesso a funcionalidade (Insucesso)

Dado que eu esteja na página ""Autenticação""
Quando eu me autenticado com usuário ""chesf"" e perfil ""Agente"""
Então eu não deveria ver a funcionalidade "Solicitar cálculo de taxa"
