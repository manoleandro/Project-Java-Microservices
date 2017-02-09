# language: pt
Funcionalidade: Cálculo das Taxas
@CT1.1.1.1.1
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam em operação comercial (notificação)
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
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação: O Cálculo das taxas foi finalizado com sucesso"
@CT1.1.1.1.2
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam em operação comercial (consulta do Log de Processamento)
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
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  ""Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE"
@CT1.1.1.1.3
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam em operação comercial (consulta Taxas calculadas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "11/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "U.SOBRADINHO"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:09/2010; TEIP:0,05992407; TEIP Mensal: 0,222153; TEIF:0,079963557; TEIF Mensal:0"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:10/2010; TEIP:0,064807139; TEIP Mensal: 0,299873; TEIF:0,080201343; TEIF Mensal:0,018605661"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:11/2010; TEIP:0,068393968; TEIP Mensal:0,215209; TEIF:0,08025346; TEIF Mensal:0"
#
@CT1.1.1.1.4
Cenário: Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que todas as unidades geradoras estejam em operação comercial (notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação: U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação: O Cálculo ds taxas foi finalizado com sucesso"
@CT1.1.1.1.5
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que todas as unidades geradoras estejam em operação comercial (consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação: U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:002-2017; Instalação:U.SOBRADINHO; Solicitante:cnos; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE"
@CT1.1.1.1.6
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam em operação comercial (consulta Taxas calculadas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:002-2017; Instalação: U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "11/2014" para o campo "Mês inicial"
E eu informo o valor "01/2015" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "U.SOBRADINHO"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:11/2014; TEIP:0,075776964; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:12/2014; TEIP:0,075775884; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:01/2015; TEIP:0,075792797; TEIP Mensal:0; TEIF:0; TEIF Mensal:0"
#
@CT1.1.1.1.7
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Usina em que todas as unidades geradoras estejam em operação comercial (notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:003-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação: O Cálculo ds taxas foi finalizado com sucesso"
@CT1.1.1.1.8
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Usina em que todas as unidades geradoras estejam em operação comercial (consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:003-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:003-2017; Instalação:U.SOBRADINHO; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE"
@CT1.1.1.1.9
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Usina em que todas as unidades geradoras estejam em operação comercial (consulta Taxas calculadas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:003-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2014" para o campo "Mês inicial"
E eu informo o valor "11/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "U.SOBRADINHO"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:09/2014; TEIP:0,075718284; TEIP Mensal: 0,000312; TEIF:0,03526248; TEIF Mensal:0,008964729"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:10/2014; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,075776964"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:11/2014; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,075776964"
#
@CT1.1.1.2.1
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam desativadas. (Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "TERMONORTE I" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:004-2017; Instalação:TERMONORTE I; Data/Hora:01/10/2017 11:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.1.2.2
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam desativadas. (consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "TERMONORTE I" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:004-2017; Instalação:TERMONORTE I; Data/Hora:01/10/2017 11:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:004-2017; Instalação:TERMONORTE I; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE"
@CT1.1.1.2.3
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam desativadas. (consulta Taxas calculadas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "TERMONORTE I" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:004-2017; Instalação:TERMONORTE I; Data/Hora:01/10/2017 11:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "11/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "TERMONORTE I" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "TERMONORTE I"
E eu deveria ver um item no grid "TERMONORTE I" com o valor "Mês referência:09/2010; TEIP:0,04177361; TEIP Mensal: 0,042309; TEIF:0,04554911; TEIF Mensal:0,03284061"
E eu deveria ver um item no grid "TERMONORTE I" com o valor "Mês referência:10/2010; TEIP:0,040975459; TEIP Mensal: 0,002111; TEIF:0,04506113; TEIF Mensal:0,020720851"
E eu deveria ver um item no grid "TERMONORTE I" com o valor "Mês referência:11/2010; TEIP:0,04015052; TEIP Mensal: 0,000503; TEIF:0,044505779; TEIF Mensal:0"
#
@CT1.1.1.2.4
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam desativadas. (Notificação)
#Não aplicável. Única Usina com Unidades Geradoras desativadas (TERMONORTE I) não tem movimento na massa de dados (Taxas - Usinas) após 06/2013
#
@CT1.1.1.2.5
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam desativadas. (consulta do Log de Processamento)
#Não aplicável. Única Usina com Unidades Geradoras desativadas (TERMONORTE I) não tem movimento na massa de dados (Taxas - Usinas) após 06/2013
#
@CT1.1.1.2.6
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam desativadas. (consulta Taxas calculadas)
#Não aplicável. Única Usina com Unidades Geradoras desativadas (TERMONORTE I) não tem movimento na massa de dados (Taxas - Usinas) após 06/2013
#
@CT1.1.1.2.7
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Usina em que todas as unidades geradoras estejam desativadas. (Notificação)
#Não aplicável. Única Usina com Unidades Geradoras desativadas (TERMONORTE I) não tem movimento na massa de dados (Taxas - Usinas) após 06/2013
#
@CT1.1.1.2.8
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Usina em que todas as unidades geradoras estejam desativadas.  (consulta do Log de Processamento)
#Não aplicável. Única Usina com Unidades Geradoras desativadas (TERMONORTE I) não tem movimento na massa de dados (Taxas - Usinas) após 06/2013
#
@CT1.1.1.2.9
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Usina em que todas as unidades geradoras estejam desativadas.  (consulta Taxas calculadas)
#Não aplicável. Única Usina com Unidades Geradoras desativadas (TERMONORTE I) não tem movimento na massa de dados (Taxas - Usinas) após 06/2013
#
@CT1.1.1.3.1
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda. (Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:005-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado sem sucesso" 
#
@CT1.1.1.3.2
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda. (consulta do log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:005-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o valor "NOK" no item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:005-2017; Instalação:UHE JIRAU; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Finalizado; Resultado: NOK"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver "Não há dados no período informado para a instalação pois as Unidades Geradoras não estavam em operação comercial.  Instalação: UHE JIRAU"
E eu deveria ver um link para a tela "Retificar Eventos"
@CT1.1.1.3.3
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda. (consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:005-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 12:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "11/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT1.1.1.3.4
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda. (Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE BALBINA" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:020-2017; Instalação:UHE BALBINA; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 12/2015; Mes Final: 12/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.1.3.5
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda. (consulta do log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE BALBINA" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:020-2017; Instalação:UHE BALBINA; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 12/2015; Mes Final: 12/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o valor "NOK" no item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:020-2017; Instalação:UHE BALBINA; Solicitante:cnos; Mes Inicial: 12/2015; Mes Final: 12/2015; Status: Finalizado; Resultado: OK"
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:020-2017; Instalação:UHE BALBINA; Solicitante:cnos; Mes Inicial: 12/2015; Mes Final: 12/2014; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE"
@CT1.1.1.3.6
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda. (consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE BALBINA" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:020-2017; Instalação:UHE BALBINA; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 12/2015; Mes Final: 12/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "12/2015" para o campo "Mês inicial"
E eu informo o valor "12/2015" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE BALBINA" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 itens no grid "UHE BALBINA"
E eu deveria ver um item no grid "UHE BALBINA" com o valor "Mês referência:12/2015; TEIP:0,0533304662; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
@CT1.1.1.3.7
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda. (Notificação)
#Não aplicável. Não existe Usina na massa de dados com todas Unidades Geradoras ainda não implantadas após 10/2014
#
@CT1.1.1.3.8
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda. (consulta do log de Processamento)
#Não aplicável. Não existe Usina na massa de dados com todas Unidades Geradoras ainda não implantadas após 10/2014
#
@CT1.1.1.3.9
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda. (consulta Cálculo de Taxas)
#Não aplicável. Não existe Usina na massa de dados com todas Unidades Geradoras ainda não implantadas após 10/2014
#
@CT1.1.1.4.1
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam suspensas.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:006-2017; Instalação:CAMPOS; Data/Hora:01/10/2017 10:00; Solicitante:cosr-se2; Mes Inicial: 09/2013; Mes Final: 11/2013; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado sem sucesso" 
#
@CT1.1.1.4.2
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam suspensas.(Consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:006-2017; Instalação:CAMPOS; Data/Hora:01/10/2017 10:00; Solicitante:cosr-se2; Mes Inicial: 09/2013; Mes Final: 11/2013; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o valor "NOK" no item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:006-2017"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver "Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação: CAMPOS Dt Suspensão: 14/12/2012 a 01/04/2015"
E eu deveria ver um link para a tela "Retificar Eventos"
@CT1.1.1.4.3
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam suspensas.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:006-2017; Instalação:CAMPOS; Data/Hora:01/10/2017 10:00; Solicitante:cosr-se2; Mes Inicial: 09/2013; Mes Final: 11/2013; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2013" para o campo "Mês inicial"
E eu informo o valor "11/2013" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT1.1.1.4.4
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam suspensas.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:007-2017; Instalação:CAMPOS; Data/Hora:01/10/2017 10:00; Solicitante:cosr-se2; Mes Inicial: 01/2015; Mes Final: 03/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado sem sucesso" 
#
@CT1.1.1.4.5
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam suspensas.(Consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:007-2017; Instalação:CAMPOS; Data/Hora:01/10/2017 10:00; Solicitante:cosr-se2; Mes Inicial: 01/2015; Mes Final: 03/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o valor "NOK" no item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:007-2017"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver "Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação: CAMPOS Dt Suspensão: 14/12/2012 a 01/04/2015"
E eu deveria ver um link para a tela "Retificar Eventos"
@CT1.1.1.4.6
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que todas as unidades geradoras estejam suspensas.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:007-2017; Instalação:CAMPOS; Data/Hora:01/10/2017 10:00; Solicitante:cosr-se2; Mes Inicial: 01/2015; Mes Final: 03/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "01/2015" para o campo "Mês inicial"
E eu informo o valor "Março 2015" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT1.1.1.4.7
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que todas as unidades geradoras estejam suspensas.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:008-2017; Instalação:CAMPOS; Data/Hora:01/10/2017 10:00; Solicitante:cosr-se2; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado sem sucesso" 
#
@CT1.1.1.4.8
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que todas as unidades geradoras estejam suspensas.(Consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:008-2017; Instalação:CAMPOS; Data/Hora:01/10/2017 10:00; Solicitante:cosr-se2; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o valor "NOK" no item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:008-2017"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver "Não há dados no período informado para as seguintes instalações pois as mesmas estavam suspensas.  Instalação: CAMPOS Dt Suspensão: 14/12/2012 a 01/04/2015"
E eu deveria ver um link para a tela "Retificar Eventos"
@CT1.1.1.4.9
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que todas as unidades geradoras estejam suspensas.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:008-2017; Instalação:CAMPOS; Data/Hora:01/10/2017 10:00; Solicitante:cosr-se2; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "01/2015" para o campo "Mês inicial"
E eu informo o valor "Março 2015" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "CAMPOS" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT1.1.1.5.1
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em operação comercial. (Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:009-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.1.5.2
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em operação comercial. (Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:009-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:009-2017; Instalação:CI CV.GARABI 1; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.1.5.3
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em operação comercial. (Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:009-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "11/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "CI CV.GARABI 1" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "CI CV.GARABI 1"
E eu deveria ver um item no grid "CI CV.GARABI 1" com o valor "Mês referência:09/2010; TEIP:0,29183; TEIP Mensal: 0; TEIF:0,186997101; TEIF Mensal:0"
E eu deveria ver um item no grid "CI CV.GARABI 1" com o valor "Mês referência:10/2010; TEIP:0,291615; TEIP Mensal: 0; TEIF:0,170330405; TEIF Mensal:0"
E eu deveria ver um item no grid "CI CV.GARABI 1" com o valor "Mês referência:11/2010; TEIP:0,291615; TEIP Mensal: 0,0714951754; TEIF:0,1700252; TEIF Mensal:0"
@CT1.1.1.5.4
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em operação comercial. (Notificação)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.5.5
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em operação comercial. (Consulta Log de Processamento)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.5.6
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em operação comercial. (Consulta Cálculo de Taxas)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.5.7
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Interligação Internacional em operação comercial. (Notificação)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.5.8
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Interligação Internacional em operação comercial. (Consulta Log de Processamento)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.5.9
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Interligação Internacional em operação comercial. (Consulta Cálculo de Taxas)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.6.1
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional desativada. (Notificação)
#Não aplicável. Não existe Interligação Internacional desativada na massa de dados 
#
@CT1.1.1.6.2
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional desativada. (Consulta Log de Processamento)
#Não aplicável. Não existe Interligação Internacional desativada na massa de dados 
#
@CT1.1.1.6.3
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional desativada. (Consulta Cálculo das Taxas)
#Não aplicável. Não existe Interligação Internacional desativada na massa de dados 
#
@CT1.1.1.6.4
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Interligação Internacional desativada. (Notificação)
#Não aplicável. Não existe Interligação Internacional desativada na massa de dados 
#
@CT1.1.1.6.5
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Interligação Internacional desativada. (Consulta Log de Processamento)
#Não aplicável. Não existe Interligação Internacional desativada na massa de dados 
#
@CT1.1.1.6.6
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Interligação Internacional desativada. (Consulta Cálculo das Taxas)
#Não aplicável. Não existe Interligação Internacional desativada na massa de dados 
#
@CT1.1.1.6.7
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 para Interligação Internacional desativada. (Notificação)
#Não aplicável. Não existe Interligação Internacional desativada na massa de dados 
#
@CT1.1.1.6.8
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 para Interligação Internacional desativada. (Consulta Log de Processamento)
#Não aplicável. Não existe Interligação Internacional desativada na massa de dados 
#
@CT1.1.1.6.9
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 para Interligação Internacional desativada. (Consulta Cálculo das Taxas)
#Não aplicável. Não existe Interligação Internacional desativada na massa de dados 
#
@CT1.1.1.7.1
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:010-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 02/2010; Mes Final: 04/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado sem sucesso" 
#
@CT1.1.1.7.2
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:010-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 02/2010; Mes Final: 04/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o valor "NOK" no item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:010-2017"
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver "Não há dados no período informado para a instalação pois a mesma não estava ativada.  Instalação: CI CV.GARABI 1 Ativação: 21/06/2010"
E eu deveria ver um link para a tela "Retificar Eventos"
@CT1.1.1.7.3
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda.(Consulta Cálculo das Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:010-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 02/2010; Mes Final: 04/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "02/2010" para o campo "Mês inicial"
E eu informo o valor "04/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "CI CV.GARABI 1" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT1.1.1.7.4
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda.(Notificação)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.7.5
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda.(Consulta Log de Processamento)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.7.6
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda.(Consulta Cálculo das Taxas)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.7.7
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda.(Notificação)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.7.8
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda.(Consulta Log de Processamento)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.7.9
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda.(Consulta Cálculo das Taxas)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.8.1
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional suspensa. (Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI LIVRAMENTO 2" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:011-2017; Instalação:CI LIVRAMENTO 2; Data/Hora:01/10/2017 10:00; Solicitante:cosr-s1; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado sem sucesso" 
#
@CT1.1.1.8.2
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional suspensa. (Consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI LIVRAMENTO 2" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:011-2017; Instalação:CI LIVRAMENTO 2; Data/Hora:01/10/2017 10:00; Solicitante:cosr-s1; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o modal "LOG DE AVISOS E ERROS"
E eu deveria ver "Não há dados no período informado para a instalação pois a mesma não estava ativada.  Instalação: CI LIVRAMENTO 2 Suspenso de: 01/02/2001 a 19/09/2012"
E eu deveria ver um link para a tela "Retificar Eventos"
@CT1.1.1.8.3
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional suspensa. (Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI LIVRAMENTO 2" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:011-2017; Instalação:CI LIVRAMENTO 2; Data/Hora:01/10/2017 10:00; Solicitante:cosr-s1; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "Agosto 2014" para o campo "Mês inicial"
E eu informo o valor "11/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "XXXX" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu deveria ver a mensagem de erro de código "RS_MENS_015"
@CT1.1.1.8.4
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional suspensa. (Notificação)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.8.5
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional suspensa. (Consulta do Log de Processamento)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.8.6
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional suspensa. (Consulta Cálculo de Taxas)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.8.7
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Interligação Internacional suspensa. (Notificação)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.8.8
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Interligação Internacional suspensa. (Consulta do Log de Processamento)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.1.8.9
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Interligação Internacional suspensa. (Consulta Cálculo de Taxas)
#Não aplicável. Não existe Interligação Internacional na massa de dados com movimento de calculo após 03/2012
#
@CT1.1.2.1.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.1.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:012-2017; Instalação: UHE JIRAU; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.1.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "02/2014" para o campo "Mês inicial"
E eu informo o valor "04/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:02/2014; TEIP:0,000563833; TEIP Mensal: 0,003026; TEIF:0,01018391; TEIF Mensal:0,001835654"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:03/2014; TEIP:0,00065989; TEIP Mensal: 0,005763; TEIF:0,01012011; TEIF Mensal:0,001172213"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:04/2014; TEIP:0,000866969; TEIP Mensal: 0,012425; TEIF:0,010284131; TEIF Mensal:0,01484099"
@CT1.1.2.1.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:013-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.1.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:013-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:013-2017; Instalação:UHE JIRAU; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.1.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:013-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "11/2014" para o campo "Mês inicial"
E eu informo o valor "01/2015" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:11/2014; TEIP:0,003944481; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:12/2014; TEIP:0,003590271; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:01/2015; TEIP:0,003796266; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
@CT1.1.2.1.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:014-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.1.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:014-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:014-2017; Instalação:UHE JIRAU; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.1.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:014-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2014" para o campo "Mês inicial"
E eu informo o valor "11/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:09/2014; TEIP:0,006575959; TEIP Mensal: 0,081957; TEIF:0,012417; TEIF Mensal:0,02087431"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:10/2014; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,004426378"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:11/2015; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,003944481"
@CT1.1.2.2.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Instalação Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.2.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Instalação Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:012-2017; Instalação: UHE JIRAU; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.2.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Instalação Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "02/2014" para o campo "Mês inicial"
E eu informo o valor "04/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:02/2014; TEIP:0,000563833; TEIP Mensal: 0,003026; TEIF:0,01018391; TEIF Mensal:0,001835654"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:03/2014; TEIP:0,00065989; TEIP Mensal: 0,005763; TEIF:0,01012011; TEIF Mensal:0,001172213"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:04/2014; TEIP:0,000866969; TEIP Mensal: 0,012425; TEIF:0,010284131; TEIF Mensal:0,01484099"
@CT1.1.2.2.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Instalação Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:013-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.2.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Instalação Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:013-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:013-2017; Instalação:UHE JIRAU; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.2.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Instalação Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:013-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Pesquisar"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "11/2014" para o campo "Mês inicial"
E eu informo o valor "01/2015" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:11/2014; TEIP:0,003944481; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:12/2014; TEIP:0,003590271; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:01/2015; TEIP:0,003796266; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
@CT1.1.2.2.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Instalação Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:014-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.2.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Instalação Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:014-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:014-2017; Instalação:UHE JIRAU; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.2.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Instalação Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:014-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Pesquisar"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2014" para o campo "Mês inicial"
E eu informo o valor "11/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:09/2014; TEIP:0,006575959; TEIP Mensal: 0,081957; TEIF:0,012417; TEIF Mensal:0,02087431"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:10/2014; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,004426378"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:11/2015; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,003944481"
@CT1.1.2.3.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período. (Notificação)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada durante todo o período
#
@CT1.1.2.3.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período. (Consulta Log de Processamento)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada durante todo o período
#
@CT1.1.2.3.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período. (Consulta Cálculo de Taxas)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada durante todo o período
#
@CT1.1.2.3.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período. (Notificação)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada durante todo o período
#
@CT1.1.2.3.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período. (Consulta Log de Processamento)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada durante todo o período
#
@CT1.1.2.3.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período. (Consulta Cálculo de Taxas)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada durante todo o período
#
@CT1.1.2.3.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e posterior a 01/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período. (Notificação)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada durante todo o período
#
@CT1.1.2.3.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e posterior a 01/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período. (Consulta Log de Processamento)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada durante todo o período
#
@CT1.1.2.3.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e posterior a 01/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período. (Consulta Cálculo de Taxas)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada durante todo o período
#
@CT1.1.2.4.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período.(Notificação)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada e parte do período
#
@CT1.1.2.4.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período.(Consultar Log de Processamento)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada e parte do período
#
@CT1.1.2.4.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período.(Consultar Cálculo de Taxas)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada e parte do período
#
@CT1.1.2.4.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período.(Notificação)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada e parte do período
#
@CT1.1.2.4.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período.(Consultar Log de Processamento)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada e parte do período
#
@CT1.1.2.4.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período.(Consultar Cálculo de Taxas)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada e parte do período
#
@CT1.1.2.4.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período.(Notificação)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada e parte do período
#
@CT1.1.2.4.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período.(Consultar Log de Processamento)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada e parte do período
#
@CT1.1.2.4.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período.(Consultar Cálculo de Taxas)
#Não aplicável. Não existe Usina na massa de dados com Unidade geradora em operação e outra desativada e parte do período
#
@CT1.1.2.5.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT MARIO LAGO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:015-2017; Instalação:UT MARIO LAGO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 06/2002; Mes Final: 08/2002; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.5.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período.(Consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT MARIO LAGO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:015-2017; Instalação:UT MARIO LAGO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 06/2002; Mes Final: 08/2002; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:015-2017; Instalação:UT MARIO LAGO; Solicitante:cnos; Mes Inicial: 06/2002; Mes Final: 08/2002; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.5.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT MARIO LAGO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:015-2017; Instalação:UT MARIO LAGO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 06/2002; Mes Final: 08/2002; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "Junho 2002" para o campo "Mês inicial"
E eu informo o valor "Agosto 2002" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UT MARIO LAGO"
E eu deveria ver um item no grid "UT MARIO LAGO" com o valor "Mês referência:06/2002; TEIP:0,041362859; TEIP Mensal: 0,033405; TEIF:0,05299082; TEIF Mensal:0,118107602"
E eu deveria ver um item no grid "UT MARIO LAGO" com o valor "Mês referência:07/2002; TEIP:0,04102952; TEIP Mensal: 0; TEIF:0,054508399; TEIF Mensal:0,1260546"
E eu deveria ver um item no grid "UT MARIO LAGO" com o valor "Mês referência:08/2002; TEIP:0,04112776; TEIP Mensal: 0,025894; TEIF:0,056504931; TEIF Mensal:0,154791906"
@CT1.1.2.5.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:016-2017; InstalaçãoUHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.5.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período.(Consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "UHE JIRAU" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:016-2017; InstalaçãoUHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:016-2017; Instalação:UHE JIRAU; Solicitante:cnos; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.5.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:016-2017; InstalaçãoUHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "11/2014" para o campo "Mês inicial"
E eu informo o valor "01/2015" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:11/2014; TEIP:0,003944481; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:12/2014; TEIP:0,003590271; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:01/2015; TEIP:0,003796266; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
#
@CT1.1.2.5.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:017-2017; InstalaçãoUHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.5.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período.(Consulta do Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:017-2017; InstalaçãoUHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:017-2017; Instalação:UHE JIRAU; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 09/2014; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.5.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e data posterior a 01/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:017-2017; InstalaçãoUHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2014" para o campo "Mês inicial"
E eu informo o valor "11/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:09/2014; TEIP:0,006575959; TEIP Mensal: 0,081957; TEIF:0,012417; TEIF Mensal:0,02087431"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:10/2014; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,004426378"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:11/2014; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,003944481"
#
@CT1.1.2.6.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra não tenha entrado ainda em operação comercial.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.6.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra não tenha entrado ainda em operação comercial.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:012-2017; Instalação: UHE JIRAU; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.6.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra não tenha entrado ainda em operação comercial.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "02/2014" para o campo "Mês inicial"
E eu informo o valor "04/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:02/2014; TEIP:0,000563833; TEIP Mensal: 0,003026; TEIF:0,01018391; TEIF Mensal:0,001835654"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:03/2014; TEIP:0,00065989; TEIP Mensal: 0,005763; TEIF:0,01012011; TEIF Mensal:0,001172213"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:04/2014; TEIP:0,000866969; TEIP Mensal: 0,012425; TEIF:0,010284131; TEIF Mensal:0,01484099"
@CT1.1.2.6.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra não tenha entrado ainda em operação comercial.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:013-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.6.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra não tenha entrado ainda em operação comercial.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:013-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:013-2017; Instalação:UHE JIRAU; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.6.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra não tenha entrado ainda em operação comercial.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:013-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 11/2014; Mes Final: 01/2015; Status: Agendado"E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "11/2014" para o campo "Mês inicial"
E eu informo o valor "01/2015" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:11/2014; TEIP:0,003944481; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:12/2014; TEIP:0,003590271; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:01/2015; TEIP:0,003796266; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0"
@CT1.1.2.6.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra não tenha entrado ainda em operação comercial.(Notificação)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:014-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu aperto o sinal de Notificação
E eu aperto a guia "Novas"
E eu seleciono o item do grid "Notificações" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu aperto o botão "Marcar como lida"
Então eu não deveria ver o item no grid "Notificações Não lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o item no grid "Notificações lidas" com "Data/Hora" com o valor "DATA/HORA CORRENTE"
E eu deveria ver o valor "Notificação:O Cálculo das taxas foi finalizado com sucesso" 
#
@CT1.1.2.6.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra não tenha entrado ainda em operação comercial.(Consulta Log de Processamento)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:014-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
Então eu deveria ver o item no grid "Agendamentos de Cálculo de Taxas" com o valor  "Protocolo:014-2017; Instalação:UHE JIRAU; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Finalizado; Resultado OK"
E eu deveria ver "Data/Hora" com o valor "DATA/HORA CORRENTE" 
@CT1.1.2.6.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra não tenha entrado ainda em operação comercial.(Consulta Cálculo de Taxas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:014-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 10:00; Solicitante:cosr-nco1; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2014" para o campo "Mês inicial"
E eu informo o valor "11/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu seleciono o tipo de taxa "TEIF"
E eu seleciono o tipo de taxa "TEIF mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:09/2014; TEIP:0,006575959; TEIP Mensal: 0,081957; TEIF:0,012417; TEIF Mensal:0,02087431"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:10/2014; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,004426378"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:11/2015; TEIP:0; TEIP Mensal: 0; TEIF:0; TEIF Mensal:0,003944481"
@CT1.1.2.7.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa durante todo o período.(Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa ao mesmo tempo
#
@CT1.1.2.7.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa ao mesmo tempo
#
@CT1.1.2.7.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Cálculo de Taxas)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa ao mesmo tempo
#
@CT1.1.2.7.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa durante todo o período.(Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa ao mesmo tempo
#
@CT1.1.2.7.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa ao mesmo tempo
#
@CT1.1.2.7.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Cálculo de Taxas)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa ao mesmo tempo
#
@CT1.1.2.7.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa durante todo o período.(Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa ao mesmo tempo
#
@CT1.1.2.7.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa ao mesmo tempo
#
@CT1.1.2.7.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 01/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa durante todo o período.(Consulta Cálculo de Taxas)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa ao mesmo tempo
#
@CT1.1.2.8.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa em parte do período  (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa em parte do periodo ao mesmo tempo
#
@CT1.1.2.8.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa em parte do período (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa em parte do periodo ao mesmo tempo
#
@CT1.1.2.8.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa em parte do período (Consulta Cálculo de Taxas)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa em parte do periodo ao mesmo tempo
#
@CT1.1.2.8.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa em parte do período  (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa em parte do periodo ao mesmo tempo
#
@CT1.1.2.8.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa em parte do período (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa em parte do periodo ao mesmo tempo
#
@CT1.1.2.8.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa em parte do período (Consulta Cálculo de Taxas)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa em parte do periodo ao mesmo tempo
#
@CT1.1.2.8.7
Cenário: 02) Calcular Taxas para período que compreenda  data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa em parte do período  (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa em parte do periodo ao mesmo tempo
#
@CT1.1.2.8.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa em parte do período (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa em parte do periodo ao mesmo tempo
#
@CT1.1.2.8.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e data posterior a 1/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja suspensa em parte do período (Consulta Cálculo de Taxas)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e suspensa em parte do periodo ao mesmo tempo
#
@CT1.1.2.9.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma Interligação internacional esteja em operação comercial e outra esteja desativada durante todo o período (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante todo o periodo ao mesmo tempo
#
@CT1.1.2.9.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma Interligação internacional esteja em operação comercial e outra esteja desativada durante todo o período (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante todo o periodo ao mesmo tempo
#
@CT1.1.2.9.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma Interligação internacional esteja em operação comercial e outra esteja desativada durante todo o período (Consulta Cálculo de Taxa)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante todo o periodo ao mesmo tempo
#
@CT1.1.2.9.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma Interligação internacional esteja em operação comercial e outra esteja desativada durante todo o período (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante todo o periodo ao mesmo tempo
#
@CT1.1.2.9.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma Interligação internacional esteja em operação comercial e outra esteja desativada durante todo o período (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante todo o periodo ao mesmo tempo
#
@CT1.1.2.9.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma Interligação internacional esteja em operação comercial e outra esteja desativada durante todo o período (Consulta Cálculo de Taxa)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante todo o periodo ao mesmo tempo
#
@CT1.1.2.9.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma Interligação internacional esteja em operação comercial e outra esteja desativada durante todo o período (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante todo o periodo ao mesmo tempo
#
@CT1.1.2.9.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma Interligação internacional esteja em operação comercial e outra esteja desativada durante todo o período (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante todo o periodo ao mesmo tempo
#
@CT1.1.2.9.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma Interligação internacional esteja em operação comercial e outra esteja desativada durante todo o período (Consulta Cálculo de Taxa)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante todo o periodo ao mesmo tempo
#
@CT1.1.2.10.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja desativada em parte do período (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante parte do periodo ao mesmo tempo
#
@CT1.1.2.10.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja desativada em parte do período (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante parte do periodo ao mesmo tempo
#
@CT1.1.2.10.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja desativada em parte do período (Consulta Cálculo de Taxa)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante parte do periodo ao mesmo tempo
#
@CT1.1.2.10.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja desativada em parte do período (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante parte do periodo ao mesmo tempo
#
@CT1.1.2.10.5
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja desativada em parte do período (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante parte do periodo ao mesmo tempo
#
@CT1.1.2.10.6
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja desativada em parte do período (Consulta Cálculo de Taxa)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante parte do periodo ao mesmo tempo
#
@CT1.1.2.10.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja desativada em parte do período (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante parte do periodo ao mesmo tempo
#
@CT1.1.2.10.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja desativada em parte do período (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante parte do periodo ao mesmo tempo
#
@CT1.1.2.10.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra esteja desativada em parte do período (Consulta Cálculo de Taxa)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e desativada durante parte do periodo ao mesmo tempo
#
@CT1.1.2.11.1
Cenário: 02) Calcular Taxas para período que compreenda data anterior para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra não tenha entrado ainda em operação comercial. (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e não entrado em operação ao mesmo tempo
#
@CT1.1.2.11.2
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra não tenha entrado ainda em operação comercial. (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e não entrado em operação ao mesmo tempo
#
@CT1.1.2.11.3
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra não tenha entrado ainda em operação comercial. (Consulta Cálculo de Taxa)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e não entrado em operação ao mesmo tempo
#
@CT1.1.2.11.4
Cenário: 02) Calcular Taxas para período que compreenda data posterior a 01/10/2014 para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra não tenha entrado ainda em operação comercial. (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e não entrado em operação ao mesmo tempo
#
@CT1.1.2.11.5
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra não tenha entrado ainda em operação comercial. (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e não entrado em operação ao mesmo tempo
#
@CT1.1.2.11.6
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra não tenha entrado ainda em operação comercial. (Consulta Cálculo de Taxa)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e não entrado em operação ao mesmo tempo
#
@CT1.1.2.11.7
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra não tenha entrado ainda em operação comercial. (Notificação)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e não entrado em operação ao mesmo tempo
#
@CT1.1.2.11.8
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra não tenha entrado ainda em operação comercial. (Consulta Log de Processamento)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e não entrado em operação ao mesmo tempo
#
@CT1.1.2.11.9
Cenário: 02) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente para Interligação Internacional em que ao menos uma interligação internacional esteja em operação comercial e outra não tenha entrado ainda em operação comercial. (Consulta Cálculo de Taxa)
#Não aplicável. Uma Interligação Internacional não possui unidades e não pode estar em operação e não entrado em operação ao mesmo tempo
#
#Funcionalidade: Calcular Taxas para todos os cenários vigentes e diferentes insumos
@CT1.2.1.1
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Taxas Ajustadas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "MARIO LAGO" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UT MARIO LAGO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:015-2017; Instalação:UT MARIO LAGO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 06/2002; Mes Final: 08/2002; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "Junho 2010" para o campo "Mês inicial"
E eu informo o valor "Agosto 2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP_fc"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu seleciono o tipo de taxa "TEIFa"
E eu seleciono o tipo de taxa "TEIF_fc"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT MARIO LAGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "MARIO LAGO"
E eu deveria ver um item no grid "MARIO LAGO" com o valor "Mês referência:Jun/2010; TEIP:0,012764; TEIP_fc: 0; TEIP_oper:0,01276366; TEIFa:0,000897; TEIF_fc:0; TEIF_oper:0,000896671"
E eu deveria ver um item no grid "MARIO LAGO" com o valor "Mês referência:Jul/2010; TEIP:0,023459; TEIP_fc: 0; TEIP_oper:0,023458671; TEIFa:0; TEIF_fc:0; TEIF_oper:0"
E eu deveria ver um item no grid "MARIO LAGO" com o valor "Mês referência:Ago/2010; TEIP:0,000196; TEIP_fc: 0; TEIP_oper:0,000195598; TEIFa:0,010755; TEIF_fc:0; TEIF_oper:0,01075462"
@CT1.2.1.2
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Taxas Ajustadas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "FORTA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:021-2017; Instalação:UT. FORTALEZA; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 01/2015; Mes Final: 01/2015; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "01/2015" para o campo "Mês inicial"
E eu informo o valor "01/2015" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP_fc"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu seleciono o tipo de taxa "TEIFa"
E eu seleciono o tipo de taxa "TEIF_fc"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 itens no grid "UT. FORTALEZA"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:01/2015; TEIP:0,0451605842; TEIP_fc: 0; TEIP_oper:0,0400056913; TEIFa:0; TEIF_fc:0; TEIF_oper:0,0175974853"
@CT1.2.1.3
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Taxas Ajustadas)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "FORTA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:022-2017; Instalação:UT. FORTALEZA; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2014" para o campo "Mês inicial"
E eu informo o valor "11/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP_fc"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu seleciono o tipo de taxa "TEIFa"
E eu seleciono o tipo de taxa "TEIF_fc"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UT. FORTALEZA"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:09/2014; TEIP:0,0411402211; TEIP_fc: 0; TEIP_oper:0,0416488126; TEIFa:0; TEIF_fc:0; TEIF_oper:0,0174945705"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:10/2014; TEIP:0,0476218276; TEIP_fc: 0; TEIP_oper:0,0411835201; TEIFa:0; TEIF_fc:0; TEIF_oper:0,0172496065"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:11/2014; TEIP:0,0468895733; TEIP_fc: 0; TEIP_oper:0,04046176; TEIFa:0; TEIF_fc:0; TEIF_oper:0,0170839466"
@CT1.2.1.4
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Parâmetros)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "SOBRA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "11/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP_fc"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu seleciono o tipo de taxa "TEIFa"
E eu seleciono o tipo de taxa "TEIF_fc"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "U.SOBRADINHO"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:Set/2010; TEIP:0,222153; TEIP_fc: 0; TEIP_oper:0,222152799; TEIFa:0; TEIF_fc:0; TEIF_oper:0"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:Out/2010; TEIP:0,299873; TEIP_fc: 0; TEIP_oper:0,299872994; TEIFa:0,018511; TEIF_fc:0; TEIF_oper:0,018511049"
E eu deveria ver um item no grid "U.SOBRADINHO" com o valor "Mês referência:Nov/2010; TEIP:0,215209; TEIP_fc: 0; TEIP_oper:0,215209305; TEIFa:0,003128; TEIF_fc:0; TEIF_oper:0,003127689"
@CT1.2.1.5
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Parâmetros)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "FORTA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:023-2017; Instalação:UT. FORTALEZA; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "11/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP_fc"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu seleciono o tipo de taxa "TEIFa"
E eu seleciono o tipo de taxa "TEIF_fc"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UT. FORTALEZA"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:09/2010; TEIP:0,185642093; TEIP_fc: 0; TEIP_oper:0,0315772817; TEIFa:0; TEIF_fc:0; TEIF_oper:0,043527212"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:10/2010; TEIP:0,180144101; TEIP_fc: 0; TEIP_oper:0,0290994141; TEIFa:0; TEIF_fc:0; TEIF_oper:0,039607726"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:11/2010; TEIP:0,180144101; TEIP_fc: 0; TEIP_oper:0,0291045532; TEIFa:0; TEIF_fc:0; TEIF_oper:0,036436446"
@CT1.2.1.6
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Parâmetros)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:024-2017; Instalação:UT. FORTALEZA; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 11/2014; Mes Final: 12/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "11/2014" para o campo "Mês inicial"
E eu informo o valor "12/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP_fc"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu seleciono o tipo de taxa "TEIFa"
E eu seleciono o tipo de taxa "TEIF_fc"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens no grid "UT. FORTALEZA"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:11/2014; TEIP:0,0468895733; TEIP_fc: 0; TEIP_oper:0,04046176; TEIFa:0; TEIF_fc:0; TEIF_oper:0,0170839466"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:12/2014; TEIP:0,0458731912; TEIP_fc: 0; TEIP_oper:0,0400056913; TEIFa:0; TEIF_fc:0; TEIF_oper:0,0175974853"
@CT1.2.1.7
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Taxas Mensais)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:012-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 02/2014; Mes Final: 04/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "02/2014" para o campo "Mês inicial"
E eu informo o valor "04/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP_fc"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu seleciono o tipo de taxa "TEIFa"
E eu seleciono o tipo de taxa "TEIF_fc"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:02/2014; TEIP:0,00056383328; TEIP_oper:0,000141782002; TEIFa:0,00599577604; TEIF_oper:0,00519181695"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:Março/2014; TEIP:0,000659889774; TEIP_oper:0,000182748539; TEIFa:0,0059307809; TEIF_oper:0,00506601669"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:04/2014; TEIP:0,000866969291; TEIP_oper:0,000314563076; TEIFa:0,00606241683; TEIF_oper:0,00509879319"
@CT1.2.1.8
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Taxas Mensais)
#Este caso de teste não faz sentido para o Negócio e, portanto, não será implementado
#
@CT1.2.1.9
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Taxas Mensais)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:017-2017; Instalação:UHE JIRAU; Data/Hora:01/10/2017 16:00; Solicitante:cnos; Mes Inicial: 09/2014; Mes Final: 11/2014; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "09/2014" para o campo "Mês inicial"
E eu informo o valor "11/2014" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP_fc"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu seleciono o tipo de taxa "TEIFa"
E eu seleciono o tipo de taxa "TEIF_fc"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "UHE JIRAU" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 3 itens no grid "UHE JIRAU"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:09/2014; TEIP:0,0065759588; TEIP_oper:0,00527710654; TEIFa:0,00683222106; TEIF_oper:0,00563729461"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:10/2014; TEIP:0,00442637783; TEIP_oper:0,00442637783; TEIFa:0,00626587542; TEIF_oper:0,00626835972"
E eu deveria ver um item no grid "UHE JIRAU" com o valor "Mês referência:11/2014; TEIP:0,00394448079; TEIP_oper:0,00394448079; TEIFa:0,00618612953; TEIF_oper:0,00619304413"
@CT1.2.1.10
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Taxas de Referência)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu informo o valor "FORTA" para o campo "Nome" da guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:025-2017; Instalação:UT. FORTALEZA; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 12/2003; Mes Final: 12/2003; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "12/2003" para o campo "Mês inicial"
E eu informo o valor "12/2003" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu seleciono o tipo de taxa "TEIP_fc"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu seleciono o tipo de taxa "TEIFa"
E eu seleciono o tipo de taxa "TEIF_fc"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "UT. FORTALEZA" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 itens no grid "UT. FORTALEZA"
E eu deveria ver um item no grid "UT. FORTALEZA" com o valor "Mês referência:12/2013; TEIP:0,0187816694; TEIP_fc: 0; TEIP_oper:0,0187816694; TEIFa:0,0190766696; TEIF_fc:0; TEIF_oper:0,0190766696"
#
@CT1.2.1.11
Cenário: 01) Calcular Taxas para período que compreenda data posterior a 01/10/2014 utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Taxas de Referência)
#UHE BALBINA tem início de operação  em 01/05/2015, mês para o qual temos uma taxa de referência, mas em 2015 temos taxas calculadas apenas em 12/2015
#
@CT1.2.1.12
Cenário: 01) Calcular Taxas para período que compreenda data anterior a 1/10/2014 e posterior a 01/10/2014 simultaneamente utilizando diferentes insumos para o cálculo da Taxa Acumulada de um determinado mês (Taxas de Referência)
#UHE BALBINA tem início de operação  em 01/05/2015, mês para o qual temos uma taxa de referência, mas em 2015 temos taxas calculadas apenas em 12/2015
#
#Funcionalidade: Calcular disponibilidade  para todos os cenários vigentes
@CT1.3.1.1
Cenário: 01) Calcular a Disponibilidade para um período para Usina em que todas as unidades geradoras estejam em operação comercial
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.1.2
Cenário: 01) Calcular a Disponibilidade para um período para Usina em que todas as unidades geradoras estejam desativadas
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.1.3
Cenário: 01) Calcular a Disponibilidade para um período para Usina em que todas as unidades geradoras não tenham entrado em operação comercial ainda
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.1.4
Cenário: 01) Calcular a Disponibilidade para um período para Usina em que todas as unidades geradoras estejam suspensas
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.1.5
Cenário: 01) Calcular a Disponibilidade para um período para Interligação Internacional em operação comercial
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.1.6
Cenário: 01) Calcular a Disponibilidade para um período para Interligação Internacional desativada
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.1.7
Cenário: Calcular a Disponibilidade para um período para Instalação Interligação Internacional que não tenha entrado em operação comercial ainda
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.1.8
Cenário: 01) Calcular a Disponibilidade para um período para Interligação Internacional suspensa.
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.1
Cenário: 02) Calcular a Disponibilidade para um período para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa durante todo o período
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.2
Cenário: 02) Calcular a Disponibilidade para um período para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja suspensa em parte do período
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.3
Cenário: 02) Calcular a Disponibilidade para um período para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada durante todo o período.
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.4
Cenário: 02) Calcular a Disponibilidade para um período para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja desativada em parte do período
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.5
Cenário: 02) Calcular a Disponibilidade para um período para Usina em que ao menos uma unidade geradora esteja em operação comercial e outra esteja entrando em operação comercial em parte do período
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.6
Cenário: 02) Calcular a Disponibilidade para um período para Usina em que ao menos uma unidade geradora que esteja em operação comercial e outra que ainda não tenha entrado ainda em operação comercial
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.7
Cenário: 02) Calcular a Disponibilidade para um período para Interligação Internacional que esteja em operação comercial
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.8
Cenário: 02) Calcular a Disponibilidade para um período para Interligação Internacional que esteja suspensa durante todo o período
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.9
Cenário: 02) Calcular a Disponibilidade para um período para Interligação Internacional que esteja suspensa em parte do período
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.10
Cenário: 02) Calcular a Disponibilidade para um período para Interligação Internacional que esteja desativada durante todo o período.
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.11
Cenário: 02) Calcular a Disponibilidade para um período para Interligação Internacional que esteja desativada em parte do período
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.12
Cenário: 02) Calcular a Disponibilidade para um período para Interligação Internacional que esteja em operação comercial em parte do período
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
@CT1.3.2.13
Cenário: 02) Calcular a Disponibilidade para um período para Interligação Internacional que ainda não tenha entrado ainda em operação comercial
#Teste implementado nos roteiros de teste UC02403_Consultar_Disponibilidades
#
#Funcionalidade: Calcular Índice de Indisponibilidade  para todos os cenários vigentes
@CT1.4.1.1
Cenário: Calcular Indice de Indisponibilidade para um período para Usina com renovação de outorga e que possuam taxas calculadas[ONS: Possui o histórico de 60 meses das taxas TEIFa e TEIP calculada para o Índice)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "USINA XINGO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:026-2017; Instalação:USINA XINGO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 12/2003; Mes Final: 12/2003; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "12/2003" para o campo "Mês inicial"
E eu informo o valor "12/2003" para o campo "Mês final"
E eu seleciono o tipo de taxa "Índice de Indisponibilidade"
E eu aperto a guia "Usinas"
E eu seleciono o item "USINA XINGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 itens na lista "USINA XINGO"
E eu deveria ver um item no grid "USINA XINGO" com o valor "Mês referência:12/2013; Indisponibilidade: 0,11266"
#
@CT1.4.1.2
Cenário: Calcular Indice de Indisponibilidade para um período para Interligação Internacional com renovação de outorga e que possuam taxas calculadas
#Teste não reproduzível por falta de dados
#
@CT1.4.2.1
Cenário: Calcular Indice de Indisponibilidade para um período para Instalação Usina com renovação de outorga  e  que não possuam taxas calculadas[ONS: Não possua o histórico de 60 meses das taxas TEIFa e TEIP calculada para o Índice, logo terá que utilizar as taxas de referência para calcular o Índice)
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "USINA XINGO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:026-2017; Instalação:USINA XINGO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 12/2003; Mes Final: 12/2003; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "12/2003" para o campo "Mês inicial"
E eu informo o valor "12/2003" para o campo "Mês final"
E eu seleciono o tipo de taxa "Índice de Indisponibilidade"
E eu aperto a guia "Usinas"
E eu seleciono o item "USINA XINGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 1 itens no grid "USINA XINGO"
E eu deveria ver um item no grid "USINA XINGO" com o valor "Mês referência:12/2013; Indisponibilidade: 0,11266"
#
@CT1.4.2.2
Cenário: Calcular Indice de Indisponibilidade para um período para Interligação Internacional com renovação de outorga  e  que não possuam taxas calculadas
#Teste não reproduzível por falta de dados
#
#Funcionalidade: Gerar IndAcum
@CT1.5.1
Cenário: Gerar arquivo IndAcum, nos formatos .DAT e XML, automaticamente para um período para usina hidrelétrica em que todas as unidades geradoras estejam em operação comercial
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.5.2
Cenário: Gerar arquivo IndAcum, nos formatos .DAT e XML, automaticamente para um período para usina hidrelétrica em que todas as unidades geradoras estejam desativadas
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.5.3
Cenário: Gerar arquivo IndAcum, nos formatos .DAT e XML, automaticamente para um período para usina hidrelétrica em que todas as unidades geradoras não tenham entrado em operação comercial ainda
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.5.4
Cenário: Gerar arquivo IndAcum, nos formatos .DAT e XML, automaticamente para um período para usina hidrelétrica em que todas as unidades geradoras estejam suspensas
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
#Funcionalidade: Gerar DispVer
@CT1.6.1.1
Cenário: Gerar arquivo DispVer, nos formatos .DAT e XML, automaticamente para um período para usina térmica em que todas as unidades geradoras estejam em operação comercial
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.6.1.2
Cenário: Gerar arquivo DispVer, nos formatos .DAT e XML, automaticamente para um período para usina térmica em que todas as unidades geradoras estejam desativadas
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.6.1.3
Cenário: Gerar arquivo DispVer, nos formatos .DAT e XML, automaticamente para um período para usina térmica em que todas as unidades geradoras não tenham entrado em operação comercial ainda
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.6.1.4
Cenário: Gerar arquivo DispVer, nos formatos .DAT e XML, automaticamente para um período para usina térmica em que todas as unidades geradoras estejam suspensas
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.6.2.1
Cenário: Gerar arquivo DispVer, nos formatos .DAT e XML, automaticamente para um período para interligação internacional que esteja em operação comercial
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.6.2.2
Cenário: Gerar arquivo DispVer, nos formatos .DAT e XML, automaticamente para um período para interligação internacional que esteja desativada
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.6.2.3
Cenário: Gerar arquivo DispVer, nos formatos .DAT e XML, automaticamente para um período para interligação internacional que não tenha entrado em operação comercial ainda
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.6.2.4
Cenário: Gerar arquivo DispVer, nos formatos .DAT e XML, automaticamente para um período para interligação internacional que esteja suspensa.
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
#Funcionalidade: Gerar TipSinc
@CT1.7.1
Cenário: Gerar arquivo TipSinc, nos formatos .DAT e XML, automaticamente para um período para usina hidrelétrica, que esteja cadastrada para receber encargo de serviço de sistema por compensador síncrono em que todas as unidades geradoras estejam em operação comercial
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.7.2
Cenário: Gerar arquivo TipSinc, nos formatos .DAT e XML, automaticamente para um período para usina hidrelétrica, que esteja cadastrada para receber encargo de serviço de sistema por compensador síncrono em que todas as unidades geradoras estejam desativadas
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.7.3
Cenário: Gerar arquivo TipSinc, nos formatos .DAT e XML, automaticamente para um período para usina hidrelétrica, que esteja cadastrada para receber encargo de serviço de sistema por compensador síncrono em que todas as unidades geradoras não tenham entrado em operação comercial ainda
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
@CT1.7.4
Cenário: Gerar arquivo TipSinc, nos formatos .DAT e XML, automaticamente para um período para usina hidrelétrica, que esteja cadastrada para receber encargo de serviço de sistema por compensador síncrono em que todas as unidades geradoras estejam suspensas
#Teste implementado nos roteiros de teste UC2502 - Gerar Arquivos
#
#Funcionalidade: Gerar notificação do Cálculo  para todos os cenários vigentes
@CT1.8.1
Cenário: Cálculo finalizado com sucesso e notificação de sucesso (Sucesso)
#Teste implementado nos roteiros de teste UC2405_Manter Agendamento
#
@CT1.8.2
Cenário: Cálculo finalizado com inconsistências e notificação de inconsistências (Sucesso)
#Teste implementado nos roteiros de teste UC2405_Manter Agendamento
#
#Funcionalidade: Gerar Versionamento de Cálculo  para todos os cenários vigentes
@CT1.9.1.1
Cenário: Versionamento de parâmetros para Intalação Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
E eu aperto a taxa "TEIP mensal", versão "v2", do mês de referência "09/2010" da instalação "U.SOBRADINHO"
Então eu deveria ver o modal "Memória de Cálculo"
E eu deveria ver 6 itens no grid "Memória de cálculo"
E eu deveria ver um item no grid "Memória de cálculo" com os valores "Equipamento: BAUSB_13P8_UG1 (v2); HEDP: 0,000; HP: 720,000"
E eu deveria ver um item no grid "Memória de cálculo" com os valores "Equipamento: BAUSB_13P8_UG2 (v2); HEDP: 0,000; HP: 720,000"
E eu deveria ver um item no grid "Memória de cálculo" com os valores "Equipamento: BAUSB_13P8_UG3 (v2); HEDP: 0,000; HP: 720,000"
E eu deveria ver um item no grid "Memória de cálculo" com os valores "Equipamento: BAUSB_13P8_UG4 (v2); HEDP: 0,000; HP: 720,000"
E eu deveria ver um item no grid "Memória de cálculo" com os valores "Equipamento: BAUSB_13P8_UG5 (v2); HEDP: 0,000; HP: 720,000"
E eu deveria ver um item no grid "Memória de cálculo" com os valores "Equipamento: BAUSB_13P8_UG6 (v2); HEDP: 0,000; HP: 720,000"
@CT1.9.1.2
Cenário: Versionamento de parâmetros para Interligação Internacional
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:009-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu aperto a taxa "TEIP mensal", versão "v2", do mês de referência "09/2010" da instalação "CI CV.GARABI 1"
Então eu deveria ver o modal "Memória de Cálculo"
E eu deveria ver 1 itens no grid "Memória de cálculo"
E eu deveria ver um item no grid "Memória de cálculo" com os valores "Equipamento: CI CV.GARABI 1 (v2); HEDP: 0,000; HP: 720,000"
@CT1.9.2.1
Cenário: Versionamento de TEIP mensal para Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "U.SOBRADINHO"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v2); TEIP mensal: 0,222153"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v1); TEIP mensal: 0,222153"
@CT1.9.2.2
Cenário: Versionamento de TEIP mensal para Interligação Internacional
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:009-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP mensal"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "CI CV.GARABI 1"
E eu deveria ver um item na lista "CI CV.GARABI 1" com o valor "Mês Referência: 09/2010 (v2); TEIP mensal: 0,000"
E eu deveria ver um item na lista "CI CV.GARABI 1" com o valor "Mês Referência: 09/2010 (v1); TEIP mensal: 0,000"
@CT1.9.3.1
Cenário: Versionamento de TEIFa mensal para Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIFa mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "U.SOBRADINHO"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v2); TEIFa mensal: 0,000"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v1); TEIFa mensal: 0,000"
@CT1.9.3.2
Cenário: Versionamento de TEIFa mensal para Interligação Internacional
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:009-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIFa mensal"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "CI CV.GARABI 1"
E eu deveria ver um item na lista "CI CV.GARABI 1" com o valor "Mês Referência: 09/2010 (v2); TEIFa mensal: 0,000"
E eu deveria ver um item na lista "CI CV.GARABI 1" com o valor "Mês Referência: 09/2010 (v1); TEIFa mensal: 0,000"
@CT1.9.4.1
Cenário: Versionamento de TEIP_fc mensal para Usina
#Teste não executado por falta de dados no SAMUG
#
@CT1.9.4.2
Cenário: Versionamento de TEIP_fc mensal para Interligação Internacional
#Teste não executado por falta de dados no SAMUG
#
@CT1.9.5.1
Cenário: Versionamento de TEIF_fc mensal para Usina
#Taxa não encontrada no SAMUG (encontramos a TEIFa_fc mas não a TEIF_fc)
#
@CT1.9.5.2
Cenário: Versionamento de TEIF_fc mensal para Interligação Internacional
#Taxa não encontrada no SAMUG (encontramos a TEIFa_fc mas não a TEIF_fc)
#
@CT1.9.6.1
Cenário: Versionamento de TEIP_oper mensal para Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP_oper mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "U.SOBRADINHO"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v2); TEIP_oper mensal: 0,222152799"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v1); TEIP_oper mensal: 0,222152799"
@CT1.9.6.2
Cenário: Versionamento de TEIP_oper mensal para Interligação Internacional
#Taxa não encontrada no SAMUG para interligação internacional
#
@CT1.9.7.1
Cenário: Versionamento de TEIF_oper mensal para Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIF_oper mensal"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "U.SOBRADINHO"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v2); TEIF_oper mensal: 0,000"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v1); TEIF_oper mensal: 0,000"
@CT1.9.7.2
Cenário: Versionamento de TEIF_oper mensal para Interligação Internacional
#Taxa não encontrada no SAMUG para interligação internacional
#
@CT1.9.8.1
Cenário: Versionamento de TEIP para Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "U.SOBRADINHO"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v2); TEIP: 0,0599240698"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v1); TEIP: 0,0599240698"
@CT1.9.8.2
Cenário: Versionamento de TEIP para Interligação Internacional
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:009-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "CI CV.GARABI 1"
E eu deveria ver um item na lista "CI CV.GARABI 1" com o valor "Mês Referência: 09/2010 (v2); TEIP: 0,29183"
E eu deveria ver um item na lista "CI CV.GARABI 1" com o valor "Mês Referência: 09/2010 (v1); TEIP: 0,29183"
@CT1.9.9.1
Cenário: Versionamento de TEIFa para Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIFa"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "U.SOBRADINHO"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v2); TEIFa: 0,0655892566"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v1); TEIFa: 0,0655892566"
@CT1.9.9.2
Cenário: Versionamento de TEIFa para Interligação Internacional
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista de "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:009-2017; Instalação:CI CV.GARABI 1; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIFa"
E eu aperto a guia "Interligações Internacionais"
E eu seleciono o item "CI CV.GARABI 1" na lista "Interligações Internacionais"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "CI CV.GARABI 1"
E eu deveria ver um item na lista "CI CV.GARABI 1" com o valor "Mês Referência: 09/2010 (v2); TEIFa: 0,000033"
E eu deveria ver um item na lista "CI CV.GARABI 1" com o valor "Mês Referência: 09/2010 (v1); TEIFa: 0,000033"
@CT1.9.10.1
Cenário: Versionamento de TEIP_fc para Usina
#Teste não executado por falta de dados no SAMUG
#
@CT1.9.10.2
Cenário: Versionamento de TEIP_fc para Interligação Internacional
#Teste não executado por falta de dados no SAMUG
#
@CT1.9.11.1
Cenário: Versionamento de  TEIF_fc para Usina
#Taxa não encontrada no SAMUG (encontramos a TEIFa_fc mas não a TEIF_fc)
#
@CT1.9.11.2
Cenário: Versionamento de  TEIF_fc para Interligação Internacional
#Taxa não encontrada no SAMUG (encontramos a TEIFa_fc mas não a TEIF_fc)
#
@CT1.9.12.1
Cenário: Versionamento de  TEIP_oper para Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIP_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "U.SOBRADINHO"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v2); TEIP_oper: 0,0601736829"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v1); TEIP_oper: 0,0601736829"
@CT1.9.12.2
Cenário: Versionamento de  TEIP_oper para Interligação Internacional
#Taxa não encontrada no SAMUG para interligação internacional
#
@CT1.9.13.1
Cenário: Versionamento de TEIF_oper para Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:001-2017; Instalação:U.SOBRADINHO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 09/2010; Mes Final: 11/2010; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu informo o valor "09/2010" para o campo "Mês inicial"
E eu informo o valor "09/2010" para o campo "Mês final"
E eu seleciono o tipo de taxa "TEIF_oper"
E eu aperto a guia "Usinas"
E eu seleciono o item "U.SOBRADINHO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "U.SOBRADINHO"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v2); TEIF_oper: 0,345407099"
E eu deveria ver um item na lista "U.SOBRADINHO" com o valor "Mês Referência: 09/2010 (v1); TEIF_oper: 0,345407099"
@CT1.9.13.2
Cenário: Versionamento de TEIF_oper para Interligação Internacional 
#Taxa não encontrada no SAMUG para interligação internacional
#
@CT1.9.14.1
Cenário: Versionamento de Índice de Indisponibilidade para Usina
Dado que eu esteja autenticado com usuário "cnos" e perfil "CNOS" 
E eu esteja na página "Consultar agendamentos" 
Quando eu seleciono "Cálculo de Taxas" para o campo "Origem de agendamento"
E eu informo o valor "01/10/2017" para o campo "Data Inicial"
E eu informo o valor "01/10/2017" para o campo "Data Final"
E eu aperto a guia "Usinas"
E eu seleciono o item "USINA XINGO" na lista de "Usinas"
E eu aperto o botão "Pesquisar"
E eu seleciono o item do grid "Agendamentos de Cálculo de Taxas" com o valor "Protocolo:026-2017; Instalação:USINA XINGO; Data/Hora:01/10/2017 10:00; Solicitante:cnos; Mes Inicial: 12/2003; Mes Final: 12/2003; Status: Agendado"
E eu aperto o botão "Executar Agora"
E eu espero 10 minutos ou até receber um Aviso de Nova Notificação 
E eu seleciono o item de menu "Consultar Taxas"
E eu informo o valor "12/2003" para o campo "Mês inicial"
E eu informo o valor "12/2003" para o campo "Mês final"
E eu seleciono o tipo de taxa "Índice de Indisponibilidade"
E eu aperto a guia "Usinas"
E eu seleciono o item "USINA XINGO" na lista "Usinas"
E eu aperto o botão "Pesquisar"
Então eu deveria ver 2 itens na lista "USINA XINGO"
E eu deveria ver um item no grid "USINA XINGO" com o valor "Mês referência:12/2013 (v2); Indisponibilidade: 0,11266"
E eu deveria ver um item no grid "USINA XINGO" com o valor "Mês referência:12/2013 (v1); Indisponibilidade: 0,11266"
#
@CT1.9.14.2
Cenário: Versionamento de Índice de Indisponibilidade para Interligação Internacional
#Teste não reproduzível por falta de dados
