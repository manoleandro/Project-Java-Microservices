## language: pt
#Funcionalidade: Inclusão de Data/Hora Agendamento
#@CT3.1.1
#Cenário: Inclusão de Data/Hora AgendamentoData e Hora de agendamento preenchida automaticamente
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa" 
#Quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#Então eu deveria ver o modal "Agendamento de cálculo"
#E eu deveria ver o valor "DATA_CORRENTE" para o campo "Data agendamento"
#E eu deveria ver o valor "HORA_CORRENTE" para o campo "Hora agendamento"
#E eu deveria ver 4 itens na lista "Agendamentos Pré-existentes"
#E eu deveria ver 1 itens na lista "Agendamentos Pré-existentes" com valor "Protocolo: 001-2014; Instalação: U.SOBRADINHO; Data Hora: 01/04/2014 09:56; Solicitante: cnos; Mês Inicial de cálculo: 01/2012; Mês final de cálculo: 05/2012"
#E eu deveria ver 1 itens na lista "Agendamentos Pré-existentes" com valor "Protocolo: 001-2015; Instalação: U.SOBRADINHO; Data Hora: 01/05/2015 09:56; Solicitante: cnos; Mês Inicial de cálculo: 06/2013; Mês final de cálculo: 12/2013"
#E eu deveria ver 1 itens na lista "Agendamentos Pré-existentes" com valor "Protocolo: 001-2016; Instalação: U.SOBRADINHO; Data Hora: 03/06/2016 14:30; Solicitante: cosr-ne1; Mês Inicial de cálculo: 10/2015; Mês final de cálculo: 12/2015"
#E eu deveria ver 1 itens na lista "Agendamentos Pré-existentes" com valor "Protocolo: 001-2017; Instalação: U.SOBRADINHO; Data Hora: 01/10/2017 10:00; Solicitante: cnos; Mês Inicial de cálculo: 03/2016; Mês final de cálculo: 10/2016"
##(realizar a consulta na guia "Cálculo Taxas (Agend existente)")
#@CT3.1.2
#Cenário: Inclusão de Data/Hora AgendamentoData e hora de agendamento no passado (insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#E eu informo "01/03/2010" para o campo "Data agendamento"
#E eu informo "10:00" para o campo "Hora agendamento"
#E eu aperto o botão "Agendar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_025"
#@CT3.1.3
#Cenário: Inclusão de Data/Hora AgendamentoData e hora de agendamento maior ou igual a data corrente (sucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item  "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#E eu informo "01/03/2020" para o campo "Data agendamento"
#E eu informo "10:00" para o campo "Hora agendamento"
#E eu aperto o botão "Agendar"
#Então eu vejo a mensagem de sucesso de código "RS_MENS_001"
#@CT3.1.4
#Cenário: Inclusão de Data/Hora AgendamentoData e hora de agendamento sem preenchimento (insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#E eu informo o valor "" para o campo "Data de agendamento"
#E eu informo o valor "" para o campo ""Hora de agendamento"
#E eu aperto o botão "Agendar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Data de agendamento"
#E eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Hora de agendamento"
#@CT3.1.5
#Cenário: 5) Data e hora inválida (insucesso)
##Teste não reproduzível (hora inválida), pois vamos utilizar um componente pronto para escolher a data em um calendário
##
#@CT3.1.6
#Cenário: 6) Data e hora válida (sucesso) 
##Já Testado acima
##Já Testado acima
#@CT3.1.7
#Cenário: 7) Data Válida e Hora inválida (insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#E eu informo "01/03/2020" para o campo "Data agendamento"
#E eu informo o valor "ZZZ" para o campo ""Hora de agendamento"
#E eu aperto o botão "Agendar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_017"
#@CT3.1.8
#Cenário: 8) Data Inválida e Hora válida (insucesso)
##Teste não reproduzível (hora inválida), pois vamos utilizar um componente pronto para escolher a data em um calendário
##
#@CT3.1.9
#Cenário: Inclusão de Data/Hora AgendamentoPreenchimento apenas da Data (insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#E eu informo "01/03/2020" para o campo "Data agendamento"
#E eu informo o valor "" para o campo ""Hora de agendamento"
#E eu aperto o botão "Agendar"
#Então eu deveria ver a mensagem de erro de código "RS_MENS_002" e crítica "Hora de agendamento"
#@CT3.1.10
#Cenário: Inclusão de Data/Hora AgendamentoPreenchimento apenas da Hora (insucesso)
#Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
#E eu esteja na página "Solicitar cálculo de taxa"
##quando eu informo o valor "Março 2010" para o campo "Mês inicial"
#E eu informo o valor "Abril 2010" para o campo "Mês final"
#E eu aperto a guia "Usinas"
#E eu seleciono o item "CAMPOS" na lista "Usinas"
#E eu aperto o botão "Agendar cálculo"
#E eu informo o valor "" para o campo "Data de agendamento"
#E eu informo "10:00" para o campo "Hora agendamento"
#E eu aperto o botão "Agendar"
#Então eu deveria ver a mensagem de erro "RS_MENS_002" e crítica "Data de agendamento"
