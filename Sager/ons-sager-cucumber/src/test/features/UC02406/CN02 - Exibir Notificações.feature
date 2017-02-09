# language: pt
Funcionalidade:  Exibir Notificações
@CT2.1.1
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações1) Cálculo finalizado com sucesso (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "U.SOBRADINHO " na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "O Cálculo das taxas foi finalizado com sucesso"
@CT2.1.2
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações2) Cálculo finalizado com erros (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-nco1" e perfil "COSR-NCO" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "JIRAU" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "O Cálculo das taxas foi finalizado sem sucesso" 
#
@CT2.1.3
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações3) Não há notificação da execução de cálculo a ser exibida (Insucesso)
#Não aplicável
#
@CT2.1.4
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações4) Acessar resultado do cálculo através da notificação (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-nco1" e perfil "COSR-NCO" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "JIRAU" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
E eu aperto o link no item do grid "Notificações" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
Então eu deveria ver a página "Consultar agendamentos"
E eu deveria ver um item no grid Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017; Instalação:UHE JIRAU; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Finalizado; Resultado: NOK"
@CT2.1.5
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações5) Cálculo Cancelado (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-nco1" e perfil "COSR-NCO" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "JIRAU" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Cancelar"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Cálculo cancelado"
@CT2.1.6
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações1) Cálculo finalizado com sucesso (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "U.SOBRADINHO " na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o itens no grid "Notificações - Novas" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
E eu deveria ver o itens no grid "Notificações - Lidas" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
E eu deveria ver o valor "O Cálculo das taxas foi finalizado com sucesso"
@CT2.1.7
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações2) Cálculo finalizado com erros (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "JIRAU" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver o valor "O Cálculo das taxas foi finalizado com sucesso"
@CT2.1.8
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações3) Não há notificação da execução de cálculo a ser exibida (Insucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.1.9
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações4) Acessar resultado do cálculo através da notificação (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
E eu aperto o link no item do grid "Notificações" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
Então eu deveria ver a página "Consultar agendamentos"
E eu deveria ver um item no grid Agendamentos de Cálculo de Taxas" com valor "Protocolo:002-2017; Instalação:UHE JIRAU; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Finalizado; Resultado: NOK"
@CT2.1.10
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações5) Cálculo Cancelado (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Cancelar"
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Cálculo cancelado"
@CT2.1.11
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações:1) Cálculo finalizado com sucesso (Insucesso)
Dado que eu esteja autenticado com usuário "chesf" e perfil "Agente" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Logout"
E eu me autentico com usuário "chesf" e perfil "Agente"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
E eu deveria ver a mensagem de código "RS_MENS_028"
@CT2.1.12
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações:2) Cálculo finalizado com erros (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Logout"
#eu me autentico com usuário "chesf" e perfil "Agente"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
E eu deveria ver a mensagem de código "RS_MENS_028"
@CT2.1.13
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações:3) Não há notificação da execução de cálculo a ser exibida (Sucesso)
Dado que eu esteja autenticado com usuário "chesf" e perfil "Agente"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
E eu deveria ver a mensagem de código "RS_MENS_028"
@CT2.1.14
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações:4) Acessar resultado do cálculo através da notificação (Insucesso)
#Não aplicável: o teste anterior já comprovou que não há geração de notificação, portanto não há como acessar o resultado a partir dela
#
@CT2.1.15
Cenário: Após a execução dos calculos verificar se foi gerado as seguintes notificações:5) Cálculo Cancelado (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Tarefas"
E existe uma tarefa "Cálculo das Taxas" com valor "CO Solicitante: NE; CO Atendente: NE; Status: Agendado"
E eu seleciono o elemento "Cálculo das Taxas" na lista "Tarefas"
E eu aperto o botão "Cancelar"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Logout"
E eu me autentico com usuário "chesf" e perfil "Agente"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
E eu deveria ver a mensagem de código "RS_MENS_028"
@CT2.2.1
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:1) Eventos Retificados (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2017" para o campo "Data Inicial"
E eu informo o valor "01/05/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Retificações" com o valor "Número da tarefa: CNOS T0005/2017; Data/Hora:01/05/2017 10:00; Solicitante:cosr-ne2; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Logout"
E eu me autentico com o usuário "chesf" e perfil "Agente"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria um item no grid "Notificações - Novas" com o valor "Transferência realizada com sucesso"
@CT2.2.2
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:2) Não há notificação de Eventos Retificados a ser exibida (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2017" para o campo "Data Inicial"
E eu informo o valor "01/05/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Retificações" com o valor "Número da tarefa: CNOS T0005/2017; Data/Hora:01/05/2017 10:00; Solicitante:cosr-ne2; Status: Agendado"
E eu aperto o botão "Executar Agora"
Então eu não deveria ver a mensagem de código "RS_MENS_028"
@CT2.2.3
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:3) Acessar Evento Retificado através da notificação (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2017" para o campo "Data Inicial"
E eu informo o valor "01/05/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Retificações" com o valor "Número da tarefa: CNOS T0005/2017; Data/Hora:01/05/2017 10:00; Solicitante:cosr-ne2; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação
E eu aperto o botão "Logout"
E eu me autentico com o usuário "chesf" e perfil "Agente"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
E eu aperto o link do 1 o item da lista "Notificações - Novas"
Então eu deveria ver a página "Consultar Eventos"
@CT2.2.4
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:4) Não acessar Evento Retificado através da notificação  (Insucesso) 
#Não aplicável: equivalente ao teste anterior
#
@CT2.2.5.1
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:1) Eventos Retificados (Insucesso?)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2017" para o campo "Data Inicial"
E eu informo o valor "01/05/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Retificações" com o valor "Número da tarefa: CNOS T0005/2017; Data/Hora:01/05/2017 10:00; Solicitante:cosr-ne2; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria um item no grid "Notificações - Novas" com o valor "Transferência realizada com sucesso"
@CT2.2.5.2
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:2) Não há notificação de Eventos Retificados a ser exibida (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2017" para o campo "Data Inicial"
E eu informo o valor "01/05/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Retificações" com o valor "Número da tarefa: CNOS T0005/2017; Data/Hora:01/05/2017 10:00; Solicitante:cosr-ne2; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu não deveria ver a mensagem de código "RS_MENS_028"
@CT2.2.5.3
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:3) Acessar Evento Retificado através da notificação (Insucesso?)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2017" para o campo "Data Inicial"
E eu informo o valor "01/05/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Retificações" com o valor "Número da tarefa: CNOS T0005/2017; Data/Hora:01/05/2017 10:00; Solicitante:cosr-ne2; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
E eu aperto o link do 1 o item da lista "Notificações - Novas"
Então eu deveria ver a página "Consultar Eventos"
@CT2.2.6.1
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:1) Eventos Retificados (Insucesso?)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2017" para o campo "Data Inicial"
E eu informo o valor "01/05/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Retificações" com o valor "Número da tarefa: CNOS T0005/2017; Data/Hora:01/05/2017 10:00; Solicitante:cosr-ne2; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Logout"
E eu me autentico com o usuário "cosr-ne2" e perfil "COSR-NE"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria um item no grid "Notificações - Novas" com o valor "Transferência realizada com sucesso"
@CT2.2.6.2
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:2) Não há notificação de Eventos Retificados a ser exibida (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2017" para o campo "Data Inicial"
E eu informo o valor "01/05/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Retificações" com o valor "Número da tarefa: CNOS T0005/2017; Data/Hora:01/05/2017 10:00; Solicitante:cosr-ne2; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu não deveria ver a mensagem de código "RS_MENS_028"
@CT2.2.6.3
Cenário: Após a transferência dos eventos para a base histórica verificar se foi gerado as seguintes notificações:3) Acessar Evento Retificado através da notificação (Insucesso?)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Retificação" para o campo "Origem de agendamento"
E eu informo o valor "01/05/2017" para o campo "Data Inicial"
E eu informo o valor "01/05/2017" para o campo "Data Final"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Retificações" com o valor "Número da tarefa: CNOS T0005/2017; Data/Hora:01/05/2017 10:00; Solicitante:cosr-ne2; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Logout"
E eu me autentico com o usuário "cosr-ne2" e perfil "COSR-NE"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
E eu aperto o link do 1 o item da lista "Notificações - Novas"
Então eu deveria ver a página "Consultar Eventos"
@CT2.3.1
Cenário: 1) Tarefa incluída notificada (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
Quando eu aperto o botão "Incluir"
E eu informo o valor "COSR-N 00001/2016" para o campo "Número Tarefa"
E eu informo o valor "01/04/2017" para o campo "Prazo Atendimento"
E eu aperto o botão "Salvar"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Tarefa inserida com sucesso"
@CT2.3.2
Cenário: 2) Tarefa incluída não notificada (Insucesso)
#Não aplicável: sem tarefa não há notificação
#
@CT2.3.3
Cenário: 3) Tarefa não incluída notificada (Insucesso)
#Não aplicável: sem tarefa não há notificação
#
@CT2.3.4
Cenário: 4) Tarefa não incluída não notificada (Sucesso)
#Não aplicável: sem tarefa não há notificação. Neste caso, o estado das notificações é o estado inicial do SAGER
#
@CT2.3.5
Cenário: 5) Acessar Tarefa incluída através de notificação de inclusão (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
Quando eu aperto o botão "Incluir"
E eu informo o valor "COSR-N 00001/2016" para o campo "Número Tarefa"
E eu informo o valor "01/04/2017" para o campo "Prazo Atendimento"
E eu aperto o botão "Salvar"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
E eu aperto o link do 1º item do grid "Notificações - Novas"
Então eu deveria ver a página "Tarefas"
E eu deveria ver um item no grid "Tarefas" com o valor "Número Tarefa: COSR-N 00001/2016; Prazo Atendimento: 01/04/2017"
@CT2.3.6
Cenário: 6) Não acessar Tarefa incluída através de notificação de inclusão (Insucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.3.7
Cenário: 7) Acessar Tarefa que não foi incluída através de notificação de inclusão (Insucesso)
#Não aplicável: sem notificação, não há como acessar algum link de notificação para tarefa
#
@CT2.3.8
Cenário: 8) Não acessar Tarefa que não foi incluída através de notificação de inclusão (Sucesso)
#Não aplicável: sem notificação, não há como acessar algum link de notificação para tarefa
#
@CT2.4.1
Cenário: 1) Tarefa atendida e notificada (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
E eu selecione o item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Solicitado; Número da tarefa: COSR-NE 00001/2017; solicitante: cosr-ne2"
E eu aperto o botão "Atender"
E eu espero 5 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Atendimento da tarefa"
@CT2.4.2
Cenário: 2) Tarefa atendida e não notificada (Insucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.4.4
Cenário: 4) Tarefa não atendida não notificada (Sucesso)
#Não aplicável:Uma tarefa não atendida não gera notificação. Neste caso, a lista de notifcações novas é definida no estado inicial do SAGER.
#
@CT2.4.5
Cenário: 5) Acessar Tarefa atendida através de notificação de atendimento (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
E eu selecione o item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Solicitado; Número da tarefa: COSR-NE 00001/2017; solicitante: cosr-ne2"
E eu aperto o botão "Atender"
E eu espero 5 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
E eu aperto o link no item do grid "Notificações - Novas" com o valor "Atendimento da Tarefa"
Então eu deveria ver a página "Tarefas"
E eu deveria ver um item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Concluído; Número da tarefa: COSR-NE 00001/2017; Solicitante: cosr-ne2"
@CT2.4.6
Cenário: 6) Não acessar Tarefa atendida através de notificação de atendemento (Insucesso)
#Não aplicável
#
@CT2.5.1
Cenário: 1) Tarefa agendada notificada (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
E eu selecione o item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Solicitado; Número da tarefa: COSR-NE 00001/2017; solicitante: cosr-ne2"
E eu aperto o botão "Agendar"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Agendamento da tarefa"
@CT2.5.2
Cenário: 2) Tarefa agendada e não notificada (Insucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.5.3
Cenário: 3) Tarefa não agendada e não notificada (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
E eu selecione o item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Solicitado; Número da tarefa: COSR-NE 00001/2017; solicitante: cosr-ne2"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
E eu deveria ver a mensagem de código "RS_MENS_028"
@CT2.5.4
Cenário: 4) Acessar Tarefa agendada através de notificação de agendamento (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
E eu selecione o item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Solicitado; Número da tarefa: COSR-NE 00001/2017; solicitante: cosr-ne2"
E eu aperto o botão "Agendar"
E eu aperto o botão "Notificações"
E eu aperto a guia "Novas"
E eu aperto o link do 1º item do grid "Notificações - Novas" com o valor "Agendamento da tarefa"
Então eu deveria ver a página "Tarefas"
E eu deveria ver 1 item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Agendado; Número da tarefa: COSR-NE 00001/2017; Solicitante: cosr-ne2"
@CT2.5.5
Cenário: 5) Não acessar Tarefa agendada através de notificação de agendamento (Insucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.6.1
Cenário: 1) Tarefa concluída e notificada (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "U.SOBRADINHO " na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Tarefa Concluída"
@CT2.6.2
Cenário: 2) Tarefa concluída e não notificada (Insucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.6.3
Cenário: 3) Tarefa não concluída e notificada (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO " na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
@CT2.6.5
Cenário: 5) Acessar Tarefa concluída através de notificação de conclusão (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO " na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu aperto o link no item com o valor "Tarefa concluída"
Então eu deveria ver a tela "Tarefas"
E eu deveria ver um item no grid "Tarefas" com o valor "Solicitante: cnos; Status: Concluída""
@CT2.6.6
Cenário: 6) Não acessar Tarefa concluída através de notificação de conclusão (Insucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.6.7
Cenário: 7) Acessar Tarefa que não foi concluída através de notificação de conclusão (Insucesso)
#Não aplicável: não há notificação antes da conclusão da tarefa, portanto não há como acessar a tarefa através da notificação gerada, antes da sua conclusão.
#
@CT2.6.8
Cenário: 8) Não acessar Tarefa não foi concluída através de notificação de conclusão (Sucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.7.1
Cenário: 1) Tarefa cancelada e notificada (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
E eu selecione o item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Solicitado; Número da tarefa: COSR-NE 00001/2017; solicitante: cosr-ne2"
E eu aperto o botão "Cancelar"
E eu esteja na página "Notificações"
Então eu deveria ver um item no grid "Notificações - Novas" com "Data/Hora" com o valor "Tarefa Cancelada"
@CT2.7.2
Cenário: 2) Tarefa cancelada e não notificada (Insucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.7.3
Cenário: 3) Tarefa não cancelada e notificada (Insucesso)
#Não aplicável: Não cancelar uma tarefa não iniciada não gera notificação
#
@CT2.7.4
Cenário: 4) Acessar Tarefa cancelada através de notificação de cancelamento (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
E eu selecione o item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Solicitado; Número da tarefa: COSR-NE 00001/2017; solicitante: cosr-ne2"
E eu aperto o botão "Cancelar"
E eu esteja na página "Notificações"
E eu seleciono o itens no grid "Notificações - Novas" com o valor "Tarefa Cancelada"
Então eu deveria ver a tela "Tarefas"
E eu deveria ver um item no grid "Tarefas" com o valor "Número da Tarefa: COSR-NE 00001/2017; Solicitante: cosr-ne; Status: Cancelada""
@CT2.7.5
Cenário: 5) Não acessar Tarefa cancelada através de notificação de cancelamento (Insucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.7.6
Cenário: 6) Acessar Tarefa que não foi cancelada através de notificação de cancelamento (Insucesso)
#Não aplicável: Não cancelar uma tarefa não iniciada não gera notificação
#
@CT2.7.7
Cenário: 7) Não acessar Tarefa que não foi cancelada através de notificação de cancelamento (Sucesso)
#Não aplicável: equivalente ao teste anterior
#
@CT2.8.1
Cenário: 1) Marcar uma notificação como lida (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "U.SOBRADINHO " na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver um item no grid "Notificações - Novas" com o valor "O Cálculo das taxas foi finalizado com sucesso"
@CT2.8.2
Cenário: 2) Marcar todas as notificações como lidas (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "U.SOBRADINHO " na lista de "Usinas"
E eu informo o valor "JIRAU" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono 2 itens do grid "Notificações" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu deveria ver 2 itens no grid "Notificações - Lidas" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
@CT2.9.1
Cenário: 1) Centro de Operação Solicitante visualiza notificação de tarefa (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o valor "U.SOBRADINHO" na Lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações - Novas" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Tarefa Agendada"
@CT2.9.2
Cenário: 2) Centro de Operação Solicitante não visualiza notificação de tarefa (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o valor "U.SOBRADINHO" na Lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu aperto o botão "Logout"
E eu me autentico com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
#
@CT2.9.3
Cenário: 3) Centro de Operação Atendente visualiza notificação de tarefa (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o valor "U.SOBRADINHO" na Lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu aperto o botão "Logout"
E eu me autentico com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações - Novas" com "Data/Hora" com o valor "DATA_HORA_CORRENTE"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Tarefa Agendada"
@CT2.9.4
Cenário: 4) Centro de Operação Atendente não visualiza notificação de tarefa (Insucesso)
#Não aplicável
#
@CT2.9.5
Cenário: 5) Centro de Operação que não é Solicitante e nem Atendente não visualiza notificação de tarefa (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o valor "U.SOBRADINHO" na Lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu aperto o botão "Logout"
E eu me autentico com usuário "cosr-se1" e perfil "COSR-SE" 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
E eu deveria ver a mensagem de código "RS_MENS_028"
@CT2.9.6
Cenário: 6) Centro de Operação que não é Solicitante e nem atendente visualiza notificação de tarefa (Insucesso)
#Não aplicável
#
@CT2.10.1
Cenário: 1) Perfil COSR visualiza agendamentos realizados pelo SAGER da sua área (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/02/2017" para o campo "Data Inicial"
E eu informo o valor "01/02/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o valor "U.SOBRADINHO" na Lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:003-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:SAGER; Mes Inicial: 01/2017; Mes Final: 01/2017; Status: Agendado"
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu aperto o botão "Logout"
E eu me autentico com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Tarefa Agendada"
@CT2.10.2
Cenário: 2) Perfil COSR visualiza agendamentos realizados pelo CNOS da sua área (Sucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o valor "U.SOBRADINHO" na Lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu aperto o botão "Logout"
E eu me autentico com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Tarefa Agendada"
@CT2.10.3.1
Cenário: 3) Perfil COSR visualiza agendamentos realizados pelo SAGER fora da sua área (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/02/2017" para o campo "Data Inicial"
E eu informo o valor "01/02/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o valor "U.SOBRADINHO" na Lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:003-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:SAGER; Mes Inicial: 01/2017; Mes Final: 01/2017; Status: Agendado"
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu aperto o botão "Logout"
E eu me autentico com usuário "cosr-se1" e perfil "COSR-SE" 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
E eu deveria ver a mensagem de código "RS_MENS_028"
@CT2.10.3.2
Cenário: 3) Perfil COSR visualiza agendamentos realizados pelo CNOS fora da sua área (Insucesso)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
E eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o valor "U.SOBRADINHO" na Lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Reagendar"
E eu informo o valor "02/10/2017 10:00" para o campo "Nova Data/Hora"
E eu aperto o botão "Salvar"
E eu aperto o botão "Logout"
E eu me autentico com usuário "cosr-se1" e perfil "COSR-SE" 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
E eu deveria ver a mensagem de código "RS_MENS_028"
@CT2.10.4
Cenário: 4) Agente Geração visualiza retificações de eventos das suas instalações (Sucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
E eu selecione o item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Solicitado; Número da tarefa: COSR-NE 00001/2017; solicitante: cosr-ne2"
E eu aperto o botão "Atender"
E eu aperto o botão "Logout"
E eu me autentico com o usuário "chesf" e perfil "Agente" 
E eu esteja na página "Notificações"
Então eu deveria ver um item no grid "Notificações - Novas" com o valor "Tarefa Atendida"
@CT2.10.5
Cenário: 5) Agente Geração visualiza retificações de eventos de instalações que não são suas (Insucesso)
Dado que eu esteja autenticado com usuário "cosr-ne1" e perfil "COSR-NE" 
E eu esteja na página "Tarefas"
E eu selecione o item no grid "Tarefas" com o valor "Tarefa: Retificar Eventos; Status: Solicitado; Número da tarefa: COSR-NE 00001/2017; solicitante: cosr-ne2"
E eu aperto o botão "Atender"
E eu aperto o botão "Logout"
E eu me autentico com o usuário "furnas" e perfil "Agente" 
E eu esteja na página "Notificações"
Então eu deveria ver 0 itens no grid "Notificações - Novas"
E eu deveria ver a mensagem de código "RS_MENS_028"
