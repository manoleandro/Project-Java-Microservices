## language: pt
#Funcionalidade: Solicitar cálculo informando período para filtragem
#@CT2.1
#Cenário: Solicitar cálculo informando período para filtragemMês inicial Válido e Mês final Válido
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens da lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
##Funcionalidade: Solicitar cálculo informando período para filtragem
#@CT2.2.1
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Inicial válido e Mês Final inválido 
##Não implementado
##
#@CT2.2.2
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo: Mês Inicial inválido e Mês Final válido 
##Não implementado
##
#@CT2.2.3
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Inicial sem preenchimento e Mês Final inválido
##Não implementado
##
#@CT2.2.4
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Inicial inválido e Mês Final sem preenchimento
##Não implementado
##
#@CT2.2.5
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Inicial maior que Mês Final
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#E eu informo o valor "Abril 2010" para o campo "Mês inicial"
#E eu informo o valor "Março 2010" para o campo "Mês final"
#E eu seleciono todos os itens da lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_004"
##
#@CT2.2.6
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Inicial maior ou igual a Data Corrente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2030" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens da lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Mês inicial"
#E eu deveria ver a mensagem de erro de código "RS_MENS_004"
#@CT2.2.7
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Final maior ou igual a Data Corrente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Março 2030" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens da lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_026" e crítica "Mês final"
#@CT2.2.8
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Inicial menor que 01/01/2001
#Dado que eu esteja na página "Autenticação"
#Quando eu me autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2000" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu seleciono todos os itens da lista "Usinas"
#E eu aperto a guia "Usinas"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver a mensagem de erro "RS_MENS_022" e crítica "Mês inicial"
#@CT2.2.9
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo: Mês Final menor que 01/01/2001
##Não implementado
##
#@CT2.2.10
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Inicial sem preenchimento
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês final"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver a mensagem de erro "RS_MENS_002" e crítica "Mês inicial"
#@CT2.2.11
#Cenário: Verificar a adequação das mensagens de insucesso e o comportamento do sistema para as situações abaixo:Mês Final sem prenchimento
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Abril 2010" para o campo "Mês inicial"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Mês final"
##Funcionalidade: Solicitar cálculo informando filtro por tipo de instalação
#@CT2.3.1
#Cenário: Testar o comportamento do filtro tipo de  instalaçãoSem escolher instalação (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Instalação"
#@CT2.3.2
#Cenário: Testar o comportamento do filtro tipo de  instalaçãoEscolhendo algumas usinas e interligações internacionais (Insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#Então eu deveria ver a guia "Interligações Internacionais" desabilitada
#@CT2.3.3
#Cenário: Testar o comportamento do filtro tipo de  instalaçãoEscolhendo algumas usinas (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 8 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.3.4
#Cenário: Testar o comportamento do filtro tipo de  instalaçãoEscolhendo algumas interligações internacionais  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Interligações internacionais"
#E eu seleciono o item "CI LIVRAMENTO 2" na lista "Interligações internacionais"
#E eu seleciono o item "CI CV.URUGUAIANA" na lista "Interligações internacionais"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 7 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.URUGUAIANA"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.3.5
#Cenário: Testar o comportamento do filtro tipo de  instalaçãoEscolhendo todas as usinas  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono todos os itens da lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 11 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
#E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: UT MARIO LAGO"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.3.6
#Cenário: Testar o comportamento do filtro tipo de  instalaçãoEscolhendo todas as interligações internacionais  (Sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Interligações internacionais"
#E eu seleciono todos os itens da lista "Interligações internacionais"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 13 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 1"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 2"
#E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.URUGUAIANA"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI ACARAY"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
##Funcionalidade: Solicitar cálculo filtrando pelo nome da instalação
#@CT2.4.1
#Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasUsar um nome de instalação usina existente 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu informo "SOBRA" para o campo "Nome" na lista "Usinas"
##Eu seleciono o item "U.SOBRADINHO"
#E eu aperto o botão "Agendar Cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
#E todo item no grid "Agendamentos pré-existentes" deveria ter o valor "Instalação: U.SOBRADINHO"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.4.2
#Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasUsar um nome de instalação usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu aperto a guia 'Usinas"
#E eu informo "ZZZ" para o campo "Nome" na lista "Usinas"
#Então eu deveria ver 0 itens na guia "Usinas"
#@CT2.4.3
#Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasUsar um nome de instalação Interligação internacional existente 
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Interligações internacionais"
#E eu informo "GARABI" para o campo "Nome" na lista "Interligações internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações internacionais"
#E eu seleciono o item "CI CV.GARABI 2" na lista "Interligações internacionais"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 1"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 2"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.4.4
#Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasUsar um nome de instalação  Interligação internacional  inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu aperto a guia "Interligações internacionais"
#E eu informo "ZZZ" para o campo "Nome" na lista "Interligações internacionais"
#Então eu deveria ver 0 itens na guia "Interligações internacionais"
#@CT2.4.5
#Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasEscolher mais de um nome de instalação existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Interligações internacionais"
#E eu informo "CI CV.GARABI 1" para o campo "Nome" na lista "Interligações internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações internacionais"
#E eu informo "CI ACARAY" para o campo "Nome" na lista "Interligações internacionais"
#E eu seleciono o item "CI ACARAY" na lista "Interligações internacionais"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 1"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI ACARAY"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.4.6
#Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasEscolher um nome de instalação usina existente e um nome instalação usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu aperto a guia "Usinas"
#E eu informo "U.SOBRADINHO" para o campo "Nome" na lista "Usinas"
##Eu seleciono o item "U.SOBRADINHO"
#E eu informo "ZZZ" para o campo "Nome" na lista "Usinas"
#Então eu deveria ver 0 itens na lista "Usinas"
#E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: U.SOBRADINHO"
#@CT2.4.7
#Cenário: Testar o comportamento do filtro nome da instalação para a seleção das mesmasEscolher um nome de instalação interligação Internacional existente e um nome instalação interligação Internacional inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu aperto a guia "Interligações internacionais"
#E eu informo "CI CV.GARABI 1" para o campo "Nome" na lista "Interligações internacionais"
#E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações internacionais"
#E eu informo "ZZZ" para o campo "Nome" na lista "Interligações internacionais"
#Então eu deveria ver 0 itens na lista "Interligações Internacionais"
#E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: CI CV.GARABI 1"
##Funcionalidade: Solicitar cálculo informando filtro por tipo
#@CT2.5.1
#Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas:Não escolher o tipo de usina
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu seleciono todos os itens da lista "Usinas" 
#E eu aperto o botão "Agendar Cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 11 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
#E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: UT MARIO LAGO"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.5.2
#Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas:Escolher Hidrelétricas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu informo "UHE" para o campo "Tipo" na lista "Usinas"
##Eu seleciono todos os itens da lista "Usinas" 
#E eu aperto o botão "Agendar Cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 5 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
#E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"
#@CT2.5.3
#Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas:Escolher Termelétricas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu informo "UTE" para o campo "Tipo" na lista "Usinas"
##Eu seleciono todos os itens da lista "Usinas" 
#E eu aperto o botão "Agendar Cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 6 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: UT MARIO LAGO"
#@CT2.5.4
#Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas:Escolher tipo inexistente de usina
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu aperto a guia "Usinas"
#E eu informo "ZZZ" para o campo "Tipo" na lista "Usinas"
#Então eu deveria ver 0 itens na guia "Usinas"
#@CT2.5.5
#Cenário: Testar o comportamento do filtro de tipo de usina para a seleção de usinas:Escolher mais de um tipo de usina
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu informo "UHE" para o campo "Tipo" na lista "Usinas"
##Eu seleciono o item "U.SOBRADINHO" da lista "Usinas"
#E eu informo "UTE" para o campo "Tipo" na lista "Usinas"
##Eu seleciono o item "CAMPOS" da lista "Usinas" 
#E eu aperto o botão "Agendar Cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 8 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
##(realizar a consulta na guia "Agendamentos (Calc Taxas)")
##Funcionalidade: Solicitar cálculo filtrando pelo nome do agente
#@CT2.6.1
#Cenário: Testar o comportamento do filtro de Agente para a seleção da instalaçãoUsar um nome de agente de usina existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu informo "FURNAS" no campo "Agente" na lista "Usinas"
#E eu seleciono todos os itens da lista "Usinas"
#E eu aperto o botão "Agendar Cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
#@CT2.6.2
#Cenário: Testar o comportamento do filtro de Agente para a seleção da instalação Usar um nome de agente de usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu aperto a guia "Usinas"
#E eu informo "ZZZ" para o campo "Agente" na lista "Usinas"
#Então eu deveria ver 0 itens na guia "Usinas"
#@CT2.6.3
#Cenário: Testar o comportamento do filtro de Agente para a seleção da instalaçãoUsar um nome de agente de Interligação internacional existente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Interligações internacionais"
#E eu informo "ELETROSUL" para o campo "Agente" na lista "Interligações internacionais"
##Eu seleciono todos os itens da lista "Interligações Internacionais"
#E eu aperto o botão "Agendar Cálculo" 
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 7 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.URUGUAIANA"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.6.4
#Cenário: Testar o comportamento do filtro de Agente para a seleção da instalaçãoUsar um nome de agente de  Interligação internacional  inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu aperto a guia "Interligações internacionais"
#E eu informo "ZZZ" para o campo "Agente" na lista "Interligações internacionais"
#Então eu deveria ver 0 itens na guia "Interligações internacionais"
#@CT2.6.5.1
#Cenário: Testar o comportamento do filtro de Agente para a seleção da instalaçãoEscolher mais de um agente por nome e por instalação
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu informo "CHESF" para o campo "Agente" na lista "Usinas"
##Eu seleciono o item "U.SOBRADINHO" da lista "Usinas"
#E eu informo "COPEL-GT" para o campo "Agente" na lista "Usinas"
##Eu seleciono o item "BENTO MUNHOZ" da lista "Usinas"
#E eu aperto o botão "Agendar Cálculo" 
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 5 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.6.5.2
#Cenário: Testar o comportamento do filtro de Agente para a seleção da instalação Escolher mais de um agente por nome e por instalação
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Interligações internacionais"
#E eu informo "ELETROSUL" para o campo "Agente" na lista "Interligações internacionais"
##Eu seleciono o item "CI LIVRAMENTO 2" da lista "Interligações Internacionais"
#E eu informo "CIEN" para o campo "Agente" na lista "Interligações internacionais"
##Eu seleciono o item "CI CV.GARABI 2" da lista "Interligações Internacionais"
#E eu aperto o botão "Agendar Cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 5 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 2"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.6.6
#Cenário: Testar o comportamento do filtro de Agente para a seleção da instalaçãoEscolher agente por nome de agente e instalação usina existente e um agente por nome e instalação usina inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu aperto a guia "Usinas"
#E eu informo "CHESF" para o campo "Agente" na lista "Usinas"
##Eu seleciono o item "U.SOBRADINHO" da lista "Usinas"
#E eu informo "ZZZ" para o campo "Agente" na lista "Usinas"
#Então eu deveria ver 0 itens na lista "Usinas"
#E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: U.SOBRADINHO"
#@CT2.6.7
#Cenário: Testar o comportamento do filtro de Agente para a seleção da instalaçãoEscolher um agente por nome e instalação interligação Internacional existente e um um agente por nome e instalação interligação Internacional inexistente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu aperto a guia "Interligações Internacionais"
#E eu informo "ELETROSUL" para o campo "Agente" na lista "Interligações Internacionais"
##Eu seleciono o item "CI LIVRAMENTO 2" da lista "Interligações Internacionais"
#E eu informo "ZZZ" para o campo "Agente" na lista "Interligações Internacionais"
#Então eu deveria ver 0 itens na lista "Interligações Internacionais"
#E eu deveria ver 1 itens selecionados na lista "Usinas" com valor "Nome: CI LIVRAMENTO 2"
##Funcionalidade: Solicitar cálculo escolhendo instalações
#@CT2.7.1
#Cenário: Testar as seguintes condições:Não escolher Usinas ou Interligações Internacionais (insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto o botão "Agendar Cálculo" 
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Instalação"
#@CT2.7.2
#Cenário: Testar as seguintes condições:Escolher apenas uma Usina
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
##Eu seleciono o item "U.SOBRADINHO" da lista "Usinas"
#E eu aperto o botão "Agendar Cálculo" 
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.7.3
#Cenário: Testar as seguintes condições:Escolher apenas uma Interligação Internacional
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Interligações internacionais"
##Eu seleciono o item "CI LIVRAMENTO 2" da lista "Interligações Internacionais"
#E eu aperto o botão "Agendar Cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 3 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.7.4
#Cenário: Testar as seguintes condições:Escolher uma Usina e uma Interligação Internacional (insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
##Eu seleciono o item "U.SOBRADINHO" da lista "Usinas"
#Então eu deveria ver a guia "Interligações Internacionais" desabilitada
#@CT2.7.5
#Cenário: Testar as seguintes condições:Escolher várias Usinas e várias Interligações Interncaionais (insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
##Eu seleciono o item "U.SOBRADINHO" da lista "Usinas"
##Eu seleciono o item "CAMPOS" da lista "Usinas"
#Então eu deveria ver a guia "Interligações Internacionais" desabilitada
#@CT2.7.6
#Cenário: Testar as seguintes condições:Escolher todas as Usinas
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
##Eu seleciono todos os itens da lista "Usinas"
#E eu aperto o botão "Agendar Cálculo" 
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 11 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: U.SOBRADINHO"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CAMPOS"
#E eu deveria ver 1 itens no grid "Agendamentos pré-existentes" com valor "Instalação: BENTO MUNHOZ"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: UT MARIO LAGO"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.7.7
#Cenário: Testar as seguintes condições: Escolher todas as Interligações Internacionais
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando  eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Interligações Internacionais"
##Eu seleciono todos os itens da lista "Interligações Internacionais"
#E eu aperto o botão "Agendar Cálculo" 
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver 13 itens no grid "Agendamentos pré-existentes"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 1"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.GARABI 2"
#E eu deveria ver 3 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI LIVRAMENTO 2"
#E eu deveria ver 4 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI CV.URUGUAIANA"
#E eu deveria ver 2 itens no grid "Agendamentos pré-existentes" com valor "Instalação: CI ACARAY"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT2.7.8
#Cenário: Testar as seguintes condições:Escolher todas as Usinas e todas as Interligações Internacionais (insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
##Eu seleciono todos os itens da lista "Usinas"
#Então eu deveria ver a guia "Interligações Internacionais" desabilitada
