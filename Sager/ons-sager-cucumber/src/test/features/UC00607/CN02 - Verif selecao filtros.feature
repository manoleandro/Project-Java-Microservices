## language: pt

Funcionalidade: Consultar disponibilidades informando período para filtragem

#@CT3.1.1  -------------------------------------- OK
#Cenário: Incluir dia útil e hora válidos (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Incluir" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "14" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu informo o valor "12" para o campo "Hora" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de sucesso de código "RS_MENS_001"
#Mensagem: Operação realizada com sucesso

#@CT3.1.2 -------------------------------------- OK
#Cenário: Incluir dia útil inválido no mês 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Incluir" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "34" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu informo o valor "12" para o campo "Hora" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_104"
#Mensagem: Dia inválido
#OK, sugerimos considera o valor 25 para o campo dia útil.
#OBS Jerome: Mantivemos o valor 34 para caracterizar um dia útil inválido

##@CT3.1.3 -------------------------------------- OK
#Cenário: Incluir hora inválida (Insucesso) 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Incluir" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "14" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu informo o valor "32:00" para o campo "Hora" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"

##@CT3.1.4 ------------------------------------ BUUUUUUUUUUUUUG!!!!!!!!
# ESTA RETORNANDO Dia inválido.RS-MENS_105 - Hora inválida.RS.MENS_085
#Cenário: Incluir uma data e hora de agendamento já existente (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Incluir" do 2 o item do grid "Parametrizações"
#E eu informo o valor "14" para o campo "Dia útil" do 2 o item do grid "Parametrizações"
#E eu informo o valor "12" para o campo "Hora" do 2 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_085"
##Mensagem: Registro duplicado
##Onde está a informação da 1a linha? Incluir na planilha de Massa de Dados ou duplicar as entradas desse teste
##Jérôme: Planilha "SAGER - Massa de dados para testes" / Guias "Agendamentos (Parâmetros)"
#
##@CT3.1.5 -------------------------------------- OK
#Cenário: Incluir apenas um dia útil válido sem incluir hora (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Incluir" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "14" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Hora"
##Mensagem: Informações obrigatórias não informadas: Hora
#
##@CT3.1.6 -------------------------------------- OK
#Cenário: Incluir apenas uma hora válida sem incluir dia útil (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Incluir" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "12" para o campo "Hora" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Dia útil"
##Mensagem: Informações obrigatórias não informadas: Dia útil
#
##@CT3.1.7.1 ----------------------------  VERIFIQUEI COM O TSURU, BRUNA FICOU DE AJUSTAR PARA ACEITAR O 00 
#Cenário: Incluir dia útil e hora zerados com parametrização inexistente (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Incluir" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "00" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu informo o valor "00" para o campo "Hora" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de sucesso de código "RS_MENS_001"
#E eu deveria ver no 1o item do grid o campo Dia útil valor "14" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
##Mensagem: Operação realizada com sucesso
##Nota para o programador: desconsiderar os dados da guia "Agendamentos (Parâmetros)"
#
##@CT3.1.7.2 ----------------------------  VERIFIQUEI COM O TSURU, BRUNA FICOU DE AJUSTAR PARA ACEITAR O 00
#Cenário: Incluir dia útil e hora zerados com parametrização existente (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Incluir" na coluna "Ações" do 1o item do grid "Parametrizações"
#E eu informo o valor "00" para o campo "Dia útil" do 3 o item do grid "Parametrizações"
#E eu informo o valor "00" para o campo "Hora" do 3 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de sucesso de código "RS_MENS_001"
##Mensagem: Operação realizada com sucesso
##Os agendamentos precisam ser diferentes. Nesse exemplo, você está duplicando o agendamento.
#
##@CT3.2.1 ----------------------------  VERIFIQUEI COM O TSURU, BRUNA FICOU DE AJUSTAR PARA ACEITAR O :30
#Cenário: Alterar uma data e hora parametrizada para uma data e hora  válida (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Alterar" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "16" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu informo o valor "14:30" para o campo "Hora" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de sucesso de código "RS_MENS_001"
##Mensagem: Operação realizada com sucesso
##Onde está a informação da 1a linha? Incluir na planilha de Massa de Dados
##Jérôme: Planilha "SAGER - Massa de dados para testes" / Guias "Agendamentos (Parâmetros)"
#
##@CT3.2.2 ----------------------------  O COMPORTAMENTO DE HOJE EM DIA NÃO ESTA IGUAL AO TESTE, DEPOIS QUE VALIDAR TODOS VOLTAR E TENTAR ARRUMAR
#Cenário: Alterar uma data e hora parametrizada para uma data inválida (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Alterar" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "25" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_104"
#E eu deveria ver o valor "14" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu deveria ver o valor "12" para o campo "Hora" do 1 o item do grid "Parametrizações"
##Mensagem: Dia inválido
##Onde está a informação da 1a linha? Incluir na planilha de Massa de Dados
##Jérôme: Planilha "SAGER - Massa de dados para testes" / Guias "Agendamentos (Parâmetros)"
##OK, sugerimos considera o valor 25 para o campo dia útil.  
##OBS Jerome: Mantivemos o valor 36 para caracterizar um dia útil inválido
##O valor anterior será mantido? 
#
##@CT3.2.3 ----------------------------  VERIFIQUEI COM O TSURU, BRUNA FICOU DE AJUSTAR PARA ACEITAR O :30
#Cenário: Alterar uma data e hora parametrizada para uma hora inválida (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Alterar" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "28:30" para o campo "Hora" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_105"
#E eu deveria ver o valor "14" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu deveria ver o valor "12" para o campo "Hora" do 1 o item do grid "Parametrizações"
##Mensagem: Hora inválida
##Onde está a informação da 1a linha? Incluir na planilha de Massa de Dados
##Jérôme: Planilha "SAGER - Massa de dados para testes" / Guias "Agendamentos (Parâmetros)"
#
##@CT3.2.4 -------------------------------------- OK
#Cenário: Alterar uma data e hora parametrizada para uma data e hora já existente na parametrização (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Alterar" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu informo o valor "18" para o campo "Dia útil" do 1 o item do grid "Parametrizações"
#E eu informo o valor "11" para o campo "Hora" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Salvar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_085"
##Mensagem: Registro duplicado
##Onde está a informação da 1a linha? Incluir na planilha de Massa de Dados
##Jérôme: Planilha "SAGER - Massa de dados para testes" / Guias "Agendamentos (Parâmetros)"
#
##@CT3.3.1 ------------------------------------ BUUUUUUUUUUUUUG!!!!!!!!
#Cenário: Excluir uma parâmetrização de agendamento para Tarefas de Retificação (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Excluir" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Sim"
#Então eu deveria ver a mensagem de sucesso de código "RS_MENS_001"
##Mensagem: Operação realizada com sucesso
##Onde está a informação da 1a linha? Incluir na planilha de Massa de Dados
##Jérôme: Planilha "SAGER - Massa de dados para testes" / Guias "Agendamentos (Parâmetros)"
# 
#
##@CT3.3.2 -------------------------------------- OK
#Cenário: Excluir a única ou todas as parâmetrização de agendamento para Tarefas de Retificação (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS"
#E eu esteja na tela "Consultar Parametrização"
#Quando eu aperto o botão "Excluir" na coluna "Ações" do 1 o item do grid "Parametrizações"
#E eu aperto o botão "Sim"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_103"
##Mensagem: Tem que existir pelo menos um agendamento
##Onde está a informação da 1a linha? Incluir na planilha de Massa de Dados
##Jérôme: Planilha "SAGER - Massa de dados para testes" / Guias "Agendamentos (Parâmetros)"
