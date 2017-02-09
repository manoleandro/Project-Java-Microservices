# language: pt
Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil CNOS
@CT1.1
Cenário: Acessar a funcionalidade com um usuário que tenha o perfil CNOS
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "cnos" e perfil "CNOS"
E eu aperto o item de menu "Consultar eventos"
Então eu deveria ver a página "Consultar eventos"
E eu deveria ver 15 itens na guia "Usinas"
E eu deveria ver 5 itens na guia "Interligações Internacionais"
#(consultar as guias Usinas e Interligações Internacionais na planilha Massa de dados para testes")
#Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil COSR
@CT1.2.1
Cenário: Acessar a funcionalidade com um usuário que tenha o perfil COSR: NE
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "cosr-ne1" e perfil "COSR-NE"
E eu aperto o item de menu "Consultar eventos"
Então eu deveria ver a página "Consultar eventos"
E eu deveria ver 5 itens na guia "Usinas"
E todo item na lista "Usinas" deveria ter o atributo "COSR: NE"
E eu deveria ver a guia "Interligações Internacionais" desabilitada
#(consultar as guias Usinas na planilha Massa de dados para testes")
@CT1.2.2
Cenário: Acessar a funcionalidade com um usuário que tenha o perfil COSR: S
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "cosr-s1" e perfil "COSR-S"
E eu aperto o item de menu "Consultar eventos"
Então eu deveria ver a página "Consultar eventos"
E eu deveria ver 4 itens na guia "Usinas"
E todo item na lista "Usinas" deveria ter o atributo "COSR: S"
E eu deveria ver 5 itens na guia "Interligações Internacionais"
#(consultar as guias Usinas e Interligações Internacionais na planilha Massa de dados para testes")
#Funcionalidade: Acessar a funcionalidade com um usuárioa que tenha perfil diferente de CNOS e COSR
@CT1.3
Cenário: Acessar a funcionalidade com um usuário que tenha o perfil agente
Dado que eu esteja na tela "Autenticação"
Quando eu me autentico com usuário "chesf" e perfil "Agente"
E eu aperto o item de menu "Consultar eventos"
#Eu não deveria ver o item de menu "Consultar eventos"
