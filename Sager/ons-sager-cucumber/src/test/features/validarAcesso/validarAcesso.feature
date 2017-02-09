# language: pt

Funcionalidade: Validar acesso por usuario e perfil


##-------------------------------------------------------------------- OK FUNCIONOU -----------------------------------------------------------


##@CT01001

#Esquema do Cenario: Verificar o acesso a funcionalidade Consultar Taxas para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Funcionalidade     | Menu    |
#| "cnos" | "CNOS" | "cnos" | "Consultar Taxas"  | "SAGER" |



##Mensagem(s): N/A


##--------------------------------------------------------------------- NÃO DEU CERTO ---------------------------------------------------


###@CT01002
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade Consultar Taxas para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login       | Perfil     | Senha      | Funcionalidade     | Menu    |
#| "cosr-nco1" | "cosr-nco" | "cosr-nco1" | "Consultar Taxas"  | "SAGER" |


##Mensagem(s): N/A


#------------------------------------------------- OK DEU CERTO ------------------------------------------------------------

##@CT02001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade Solicitar Cálculo de Taxas para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#Exemplos: 
#| Login   | Perfil | Senha  | Funcionalidade               | Menu    |
#|  "cnos" | "CNOS" | "cnos" | "Solicitar Cálculo de Taxas" | "SAGER" |
#

##Mensagem(s): N/A


###---------------------------------------------------- OK DEU CERTO --------------------------------------------------------------------
#
#
###@CT03001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade Consultar Disponibilidade para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Funcionalidade              | Menu    |
#| "cnos" | "CNOS" | "cnos" | "Consultar Disponibilidade" | "SAGER" |
#

###Mensagem(s): N/A
#
#
###-------------------------------------------------- NAO DEU CERTO -----------------------------------------------------------------------
#
#
###@CT03002
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade Consultar Disponibilidade para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#Exemplos: 
#| Login       | Perfil     | Senha       | Funcionalidade              | Menu    |
#| "cosr-nco1" | "cosr-nco" | "cosr-nco1" | "Consultar Disponibilidade" | "SAGER" |
#

###Mensagem(s): N/A
#
#
###------------------------------------------------- OK DEU CERTO ---------------------------------------------------------------
#
#
###@CT04001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade Manter Agendamento para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#Exemplos: 
#| Login  | Perfil | Senha  | Funcionalidade       | Menu    |
#| "cnos" | "CNOS" | "cnos" | "Manter Agendamento" | "SAGER" |
#

###Mensagem(s): N/A
#
#
###------------------------------------------------------ NAO DEU CERTO (NAO TEM ACESSO NA PAGINA) -------------------------------------------------------------
#
#
###@CT04002
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade Manter Agendamento para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login      | Perfil    | Senha      | Funcionalidade       | Menu    |
#| "cosr-se1" | "COSR-SE" | "cosr-se1" | "Manter Agendamento" | "SAGER" |


###Mensagem(s): N/A
#
#
###-------------------------------------------- NAO DEU CERTO (ABA NOTIFICACOES NAO ESTA NO LUGAR "CERTO") ---------------------------------------------------------------
#
#
###@CT05001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade Notificações para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login    | Perfil  | Senha    | Funcionalidade  | Menu           |
#|  "cnos"  | "CNOS"  |  "cnos"  | "Notificações"  | "Notificações" |


###Mensagem(s): N/A
#
#
###-------------------------------------------- NAO DEU CERTO (ABA NOTIFICACOES NAO ESTA NO LUGAR "CERTO") ------------------------------------------------
#
#
###@CT05002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Notificações" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#| Login    | Perfil | Senha    | Funcionalidade  | Menu           |
#| "cos-sp" | "COS"  | "cos-sp" | "Notificações"  | "Notificações" |

#Exemplos: 
#Então eu deveria ver a funcionalidade "Notificações" disponível
#
#
###Mensagem(s): N/A
#
#
###---------------------------------------------- NAO DEU CERTO (ABA NOTIFICACOES NAO ESTA NO LUGAR "CERTO") ----------------------------------------------------------------------------------------
#
#
###@CT05003

#Esquema do Cenario: Verificar o acesso a funcionalidade "Notificações" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login    | Perfil    | Senha    | Funcionalidade  | Menu           |
#| "chesf"  | "Agente " | "chesf"  | "Notificações"  | "Notificações" |


###Mensagem(s): N/A
#
#
###--------------------------------------------------- OK DEU CERTO -----------------------------------------------------------------
#
#
###@CT06001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade Consultar Eventos para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Funcionalidade      | Menu    |
#| "cnos" | "CNOS" | "cnos" | "Consultar Eventos" | "SAGER" |
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------  NAO DEU CERTO (LOGIN NÃO AUTORIZADO) ---------------------------------------------------------
#
#
###@CT06002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade Consultar Eventos para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login      | Perfil     | Senha       | Funcionalidade      | Menu    |
#| "cosr-nco" | "cosr-nco" | "cosr-nco"  | "Consultar Eventos" | "SAGER" |


###Mensagem(s): N/A
#
#
###------------------------------------------------- OK DEU CERTO ---------------------------------------------------------------
#
#
###@CT07001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade Consultar Taxas de Referência para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login   | Perfil | Senha   | Funcionalidade                  | Menu    |
#|  "cnos" | "CNOS" |  "cnos" | "Consultar Taxas de Referência" | "SAGER" |


###Mensagem(s): N/A
#
#
###------------------------------------------ NÃO DEU CERTO (LOGIN NÃO AUTORIZADO)---------------------------------------------------------------------
#
#
###@CT07002

#Esquema do Cenario: Verificar o acesso a funcionalidade Consultar Taxas de Referência para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login       | Perfil     | Senha       | Funcionalidade                  | Menu    |
#| "cosr-nco2" | "cosr-nco" | "cosr-nco2" | "Consultar Taxas de Referência" | "SAGER" |


###Mensagem(s): N/A
#
#
###------------------------------------------ NÃO DEU CERTO (NO PROJETO ESTÁ "Gerar relatórios") ----------------------------------------------------------
#
#
###@CT08001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade Gerar Relatórios para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Funcionalidade     | Menu    |
#| "cnos" | "CNOS" | "cnos" | "Gerar Relatórios" | "SAGER" |
#

###Mensagem(s): N/A
#
#
###------------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) -------------------------------------------------------------------------
#
#
###@CT09001

#Esquema do Cenario: Verificar o acesso a funcionalidade Tarefas para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Funcionalidade | Menu      |
#| "cnos" | "CNOS" | "cnos" | "Tarefas"      | "Tarefas" |


###Mensagem(s): N/A
#
#
###------------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) -----------------------------------------
#
#
###@CT09002

#Esquema do Cenario: Verificar o acesso a funcionalidade Tarefas para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login      | Perfil     | Senha      | Funcionalidade | Menu      |
#| "cosr-nco" | "cosr-nco" | "cosr-nco" | "Tarefas"      | "Tarefas" |


###Mensagem(s): N/A
#
#
###------------------------------------------- NÃO DEU CERTO (FALHA NO LOGIN) -----------------------------------------------
#
#
###@CT10001

#Esquema do Cenario: Verificar o acesso a funcionalidade Retificar Eventos para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login   | Perfil   | Senha   | Funcionalidade       | Menu    |
#| "copel" | "Agente" | "copel" | "Retificar Eventos"  | "SAGER" |


###Mensagem(s): N/A
#
#
###--------------------------------------- NÃO DEU CERTO (AINDA NÃO IMPLEMENTADO NO MENU) -------------------------------------------------------
#
#
###@CT11001

#Esquema do Cenario: Verificar o acesso a funcionalidade Consultar Eventos Consolidados para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Funcionalidade                   | Menu    |
#| "cnos" | "CNOS" | "cnos" | "Consultar Eventos Consolidados" | "SAGER" |


###Mensagem(s): N/A
#
#
###----------------------------------------- NÃO DEU CERTO (AINDA NÃO IMPLEMENTADO NO MENU) --------------------------------------------------------
#
#
###@CT11002

#Esquema do Cenario: Verificar o acesso a funcionalidade Consultar Eventos Consolidados para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login    | Perfil | Senha    | Funcionalidade                   | Menu    |
#| "cos-sp" | "COS"  | "cos-sp" | "Consultar Eventos Consolidados" | "SAGER" |

###Mensagem(s): N/A
#
#
###------------------------------------- NÃO DEU CERTO (AINDA NÃO IMPLEMENTADO NO MENU) -------------------------------------------------------------
#
#
###@CT11003

#Esquema do Cenario: Verificar o acesso a funcionalidade Consultar Eventos Consolidados para um usuário que tenha o perfil Agente
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login   | Perfil   | Senha   | Funcionalidade                   | Menu    |
#| "chesf" | "Agente" | "chesf" | "Consultar Eventos Consolidados" | "SAGER" |

###Mensagem(s): N/A
#
#
###----------------------------------- NÃO DEU CERTO (AINDA NÃO IMPLEMENTADO NO MENU) ------------------------------------------------------------------
#
#
###@CT12001

#Esquema do Cenario: Verificar o acesso a funcionalidade Manter Cenário para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login  | Perfil  | Senha  | Funcionalidade   | Menu    |
#| "cnos" | "CNOS"  | "cnos" | "Manter Cenário" | "SAGER" |


###Mensagem(s): N/A
#
#
###--------------------------------------- OK DEU CERTO ------------------------------------------------------------------
#
#
###@CT13001

#Esquema do Cenario: Verificar o acesso a funcionalidade Manter Parametrização para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#Exemplos: 
#| Login   | Perfil | Senha  | Funcionalidade          | Menu    |
#|  "cnos" | "CNOS" | "cnos" | "Manter Parametrização" | "SAGER" |


###Mensagem(s): N/A
#
#
###---------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) --------------------------------------------------------
#
#
###@CT14001

#Esquema do Cenario: Verificar o acesso a operação Consultar da funcionalidade Tarefas para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Menu      | Operação    |
#| "cnos" | "CNOS" | "cnos" | "Tarefas" | "Consultar" |


###Mensagem(s): N/A
#
#
###---------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) ------------------------------------
#
#
###@CT14002

#Esquema do Cenario: Verificar o acesso a operação "Consultar" da funcionalidade "Tarefas" para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login      | Perfil     | Senha      | Menu     | Operação    |
#| "cosr-nco" | "cosr-nco" | "cosr-nco" |"Tarefas" | "Consultar" |


###Mensagem(s): N/A
#
#
###------------------------------------------------ NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO)  -------------------------------------------
#
#
###@CT15001

#Esquema do Cenario: Verificar o acesso a operação Incluir da funcionalidade Tarefas para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login   | Perfil | Senha  | Menu       | Operação  |
#|  "cnos" | "CNOS" | "cnos" | "Tarefas"  | "Incluir" |


###Mensagem(s): N/A
#
#
###----------------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO)  ----------------------------------------
#
#
###@CT15002

#Esquema do Cenario: Verificar o acesso a operação Incluir da funcionalidade Tarefas para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login       | Perfil     | Senha       | Menu      | Operação  |
#| "cosr-nco1" | "cosr-nco" | "cosr-nco1" | "Tarefas" | "Incluir" |


###Mensagem(s): N/A
#
#
###-------------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) ---------------------------------------------------------
#
#
###@CT16001
#
#Esquema do Cenario: Verificar o acesso a operação Alterar da funcionalidade Tarefas para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login      | Perfil     | Senha      | Menu      | Operação  |
#|"cosr-nco2" | "cosr-nco" |"cosr-nco2" | "Tarefas" | "Alterar"   |
#
#
###Mensagem(s): N/A
#
#
###--------------------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) -------------------------------------------------------------------------------------
#
#
###@CT17001
#
#Esquema do Cenario: Verificar o acesso a operação "Atender" da funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Menu      | Operação  |
#| "cnos" | "CNOS" | "cnos" | "Tarefas" | "Atender" |
#
#
###Mensagem(s): N/A
#
#
###------------------------------------------------ NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) -------------------------------------------------
#
#
###@CT17002
#
#Esquema do Cenario: Verificar o acesso a operação Atender da funcionalidade Tarefas para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login      | Perfil    | Senha     | Menu      | Operação  |
#| "COSR-NE"1 | "COSR-NE" | "COSR-NE" | "Tarefas" | "Atender" |
#
#
###Mensagem(s): N/A
#
#
###--------------------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) ----------------------------------------------
#
#
###@CT18001
#
#Esquema do Cenario: Verificar o acesso a operação Cancelar da funcionalidade Tarefas para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Menu      | Operação   |
#| "cnos" | "CNOS" | "cnos" | "Tarefas" | "Cancelar" |
#
#
###Mensagem(s): N/A
#
#
###--------------------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) -------------------------------------------------
#
#
###@CT18002
#
#Esquema do Cenario: Verificar o acesso a operação Cancelar da funcionalidade Tarefas para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login       | Perfil    | Senha       | Menu      | Operação   |
#| "cosr-ne2" | "COSR-NE" | "cosr-ne2" | "Tarefas" | "Cancelar" |
#
#
###Mensagem(s): N/A
#
#
###------------------------------------------------ NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) ------------------------------------------------------
#
#
###@CT19001
#
#Esquema do Cenario: Verificar o acesso a operação Agendar da funcionalidade Tarefas para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Menu      | Operação  |
#| "cnos" | "CNOS" | "cnos" | "Tarefas" | "Agendar" |
#
#
###Mensagem(s): N/A
#
#
###------------------------------------------------ NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) ------------------------------------------------
#
#
###@CT19002
#
#Esquema do Cenario: Verificar o acesso a operação Agendar da funcionalidade Tarefas para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login     | Perfil   | Senha     | Menu      | Operação  |
#| "cosr-s1" | "COSR-S" | "cosr-s1" | "Tarefas" | "Agendar" |
#
#
###Mensagem(s): N/A
#
#
###---------------------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) -----------------------------------------------
#
#
###@CT20001
#
#Esquema do Cenario: Verificar o acesso a operação Visualizar Eventos da funcionalidade Tarefas para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login  | Perfil | Senha  | Menu      | Operação             |
#| "cnos" | "CNOS" | "cnos" | "Tarefas" | "Visualizar Eventos" |
#
#
###Mensagem(s): N/A
#
#
###------------------------------------------------ NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) --------------------------------------------------------------
#
#
###@CT20002
#
#Esquema do Cenario: Verificar o acesso a operação Visualizar Eventos da funcionalidade Tarefas para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login      | Perfil    | Senha      | Menu       | Operação             |
#| "cosr-se1" | "COSR-SE" | "cosr-se1" | "Tarefas"  | "Visualizar Eventos" |

#
###Mensagem(s): N/A
#
#
###------------------------------------------- NAO DEU CERTO (USUARIO NÃO AUTORIZADO) -----------------------------------------------------------------------
#
#
###@CT21001

#Esquema do Cenario: Verificar o acesso a operação Importar da funcionalidade Retificar Eventos para um usuário que tenha o perfil COSR
#
####Acessar operação Importar da funcionalidade Retificação de Eventos
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#E eu seleciono a funcionalidade <Funcionalidade>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login      | Perfil    | Senha      | Menu    | Funcionalidade      | Operação   |
#| "cosr-ne2" | "COSR-NE" | "cosr-ne2" | "SAGER" | "Retificar Eventos" | "Importar" |


###Mensagem(s): N/A
#
#
###--------------------------------------------- NAO DEU CERTO (USUARIO NÃO AUTORIZADO) --------------------------------------------------------------
#
#
###@CT22001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade Retificar Eventos para um usuário que tenha o perfil COSR
#
####Acessar operação Importar da funcionalidade Retificação de Eventos
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#E eu seleciono a funcionalidade <Funcionalidade>
#Então eu deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login      | Perfil    | Senha      | Funcionalidade      | Menu    |
#| "cosr-ne2" | "COSR-NE" | "cosr-ne2" | "Retificar Eventos" | "SAGER" |
#
#
###Mensagem(s): N/A
#
#
###-------------------------------------------- NAO DEU CERTO (A FUNCIONALIDADE "Tarefas" NAO ESTA NO LUGAR CERTO) -----------------------------------------------------------------
#
#
###@CT23001
#
#Esquema do Cenario: Verificar o acesso a operação Alterar da funcionalidade Tarefas para um usuário que tenha o perfil CNOS
#
####Acessar operação "Alterar" da funcionalidade pela opção"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu não deveria ver a operação <Operação> disponível:
#
#Exemplos: 
#| Login   | Perfil | Senha  | Menu      | Operação  |
#|  "cnos" | "CNOS" | "cnos" | "Tarefas" | "Alterar" |
#
#
###Mensagem(s): N/A
#
#
###-----------------------------------------------NAO DEU CERTO (USUARIO NÃO AUTORIZADO) ------------------------------------------------------------------
#
#
###@CT024001

#Esquema do Cenario: Verificar o acesso  a funcionalidade Consultar Taxas para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
####--É possível descrever assim na negativa "...eu não deveria..."??
#
#Exemplos: 
#| Login    | Perfil   | Senha    | Funcionalidade    | Menu    |
#| "furnas" | "Agente" | "furnas" | "Consultar Taxas" | "SAGER" |


###Mensagem(s): N/A
#
#
###----------------------------------------  NAO DEU CERTO (USUARIO NÃO AUTORIZADO) ----------------------------------------------------------------------
#
#
###@CT25001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade Solicitar Cálculo de Taxas para um usuário que tenha o perfil diferente de CNOS perfil COSR-NE
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#
#Exemplos: 
#| Login      | Perfil    | Senha      | Funcionalidade                | Menu    |
#| "cosr-ne2" | "COSR-NE" | "cosr-ne2" | "Solicitar Cálculo de Taxas " | "SAGER" |
#
#
###Mensagem(s): N/A
#
#
###--------------------------------------  NAO DEU CERTO (USUARIO NÃO AUTORIZADO) -------------------------------------------------------------------------
#
#
###@CT25002

#Esquema do Cenario: Verificar o acesso  a funcionalidade Solicitar Cálculo de Taxas para um usuário que tenha o perfil diferente de CNOS perfil Agente
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
###--É possível descrever assim na negativa "...eu não deveria..."??
#
#Exemplos: 
#| Login   | Perfil   | Senha   | Funcionalidade              | Menu    |
#| "copel" | "Agente" | "copel" | "Solicitar Cálculo de Taxa" | "SAGER" |

###Mensagem(s): N/A
#
#
###-----------------------------------  NAO DEU CERTO (USUARIO NÃO AUTORIZADO) -----------------------------------------------------------------------
#
#
###@CT26001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade Consultar Disponibilidade para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
####--É possível descrever assim na negativa "...eu não deveria..."??
#
#Exemplos: 
#| Login    | Perfil   | Senha    | Funcionalidade              | Menu    |
#| "furnas" | "Agente" | "furnas" | "Consultar Disponibilidade" | "SAGER" |
#
#
###Mensagem(s): N/A
#
#
###------------------------------- NAO DEU CERTO (USUARIO TEM A PERMISSÃO DE VER O ITEM NO MENU) ---------------------------------------------------------------------------
#
#
###@CT27001

#Esquema do Cenario: Verificar o acesso a funcionalidade Manter Agendamento para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
####--É possível descrever assim na negativa "...eu não deveria..."??
#
#Exemplos: 
#| Login   | Perfil   | Senha   | Funcionalidade       | Menu    |
#| "chesf" | "Agente" | "chesf" | "Manter Agendamento" | "SAGER" |


###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT28001
#
#Esquema do Cenario: Verificar o acesso  funcionalidade "Consultar" Eventos" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
####--É possível descrever assim na negativa "...eu não deveria..."??
#
#Exemplos: 
#| Login   | Perfil   | Senha   | Funcionalidade      | Menu    |
#| "copel" | "Agente" | "copel" | "Consultar Eventos" | "SAGER" |
#
#
###Mensagem(s): N/A
#
#
###------------------------------------- HUGO ----------------------------------------------------------------------------------
#
#
###@CT29001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Taxas de Referência" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#| "furnas"    | "Agente " | "furnas"    | "Consultar" Taxas de Referência |   "SAGER"   |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Consultar" Taxas de Referência"
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------  NAO DEU CERTO (USUARIO NÃO AUTORIZADO)  ---------------------------------------------------------
#
#
###@CT30001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade Gerar Relatórios para um usuário que tenha o perfil diferente de CNOS perfil COSR-NE
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#
#Exemplos: 
#| Login      | Perfil    | Senha      | Funcionalidade     | Menu    |
#| "cosr-ne2" | "COSR-NE" | "cosr-ne2" | "Gerar Relatórios" | "SAGER" |
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT30002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade Gerar Relatórios para um usuário que tenha o perfil diferente de CNOS perfil Agente
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#
#Exemplos: 
#| Login    | Perfil    | Senha   | Funcionalidade     | Menu    |
#|  "copel" | "Agente " | "copel" | "Gerar Relatórios" | "SAGER" |
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT31001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Tarefas" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#|  "copel"  | "Agente " |  "copel"  |"Tarefas"                       |"Tarefas"      |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Tarefas"
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT32001
#
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Retificar Eventos" para um usuário que tenha o perfil diferente de COSR
#
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#|  "cnos"  | "CNOS"  |  "cnos"  | "Retificar Eventos"           |   "SAGER"   |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Retificar Eventos"
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
#
###@CT34001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Cenário" para um usuário que tenha o perfil diferente de CNOS; perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| Login     | Perfil   | Senha      | Funcionalidade                | Menu         |
#|  "cnos"  | "cosr-nco" |  "cosr-nco1" | "Manter Cenário"                |   "SAGER"   |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Manter Cenário"
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT34002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Cenário" para um usuário que tenha o perfil diferente de CNOS; perfil Agente
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#|  "copel"  | "Agente " |  "copel"  | "Manter Cenário"                |   "SAGER"   |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Manter Cenário"
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT35001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Parametrização" para um usuário que tenha o perfil diferente de CNOS; perfil COSR
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#|  "cnos"  | "CNOS"  |  "cnos"  | "Manter Parametrização"      |   "SAGER"   |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Manter Parametrização"
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT35002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Parametrização" para um usuário que tenha o perfil diferente de CNOS; perfil Agente
#
####Validar Acesso as funcionalidades pelo menu
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#|  "copel"  | "Agente " |  "copel"  | "Manter Parametrização"      |   "SAGER"   |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Manter Parametrização"
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT36001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade perla URL "Consultar" Taxas" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login | Perfil | Senha | Funcionalidade                | Menu   |
#|  /consulta-taxa   | CNOS  | cnos   | cnos  | "Consultar" Taxas"             | "SAGER"  |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Taxas" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT36002
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade pela URL "Consultar" Taxas" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu   |
#|  /consulta-taxa   | "cosr-nco1" | "cosr-nco" | "cosr-nco1" | "Consultar" Taxas"             | "SAGER"  |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Taxas" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT37001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade "Solicitar Cálculo de Taxas" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login     | Perfil   | Senha | Funcionalidade                | Menu  |
#|  /calculo-taxa    |  "cnos"  | "CNOS"  | cnos  | "Solicitar Cálculo de Taxas "   | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Solicitar Cálculo de Taxas" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT38001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade "Consultar" Disponibilidade" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|     "/disp"         |  "cnos"  | "CNOS"  |  "cnos"  | "Consultar" Disponibilidade"     | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Disponibilidade" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT38002
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade "Consultar" Disponibilidade" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|     "/disp"         | "cosr-nco1" | "cosr-nco" | "cosr-nco1" | "Consultar" Disponibilidade"     | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Disponibilidade" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT39001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade "Manter Agendamento" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade    | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#| /manter-agendamento  |  "cnos"  | "CNOS"  |  "cnos"  | "Manter Agendamento"            | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Manter Agendamento" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT39002
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade "Manter Agendamento" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade    | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#| /manter-agendamento  | "cosr-se1"  | "COSR-SE"  | "cosr-se1"  | "Manter Agendamento"            | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Manter Agendamento" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT40001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Notificações" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#|      /            |  "cnos"  | "CNOS"  |  "cnos"  | "Notificações"                  | "Notificações" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Notificações" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT40002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Notificações" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#|      /            | "cos-sp"    | COS      | "cos-sp"    | "Notificações"                  | "Notificações" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Notificações" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT40003
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Notificações" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#|      /            | "chesf"     | "Agente " | "chesf"     | "Notificações"                  | "Notificações" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Notificações" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT41001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Eventos" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|     "/evento"      |  "cnos"  | "CNOS"  |  "cnos"  | "Consultar" Eventos"             | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Eventos" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT41002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Eventos" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|     "/evento"      | "cosr-nco"  | "cosr-nco" | "cosr-nco"  | "Consultar" Eventos"             | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Eventos" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT42001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Taxas de Referência" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#| /consulta-taxa-ref |  "cnos"  | "CNOS"  |  "cnos"  | "Consultar" Taxas de Referência | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Taxas de Referência" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT42002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Taxas de Referência" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#| /consulta-taxa-ref |"cosr-nco2" | "cosr-nco" |"cosr-nco2" | "Consultar" Taxas de Referência | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Taxas de Referência" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT43001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Gerar Relatórios" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  /gerar-relatorio  |  "cnos"  | "CNOS"  |  "cnos"  | Gerar Relatórios              | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Gerar Relatórios" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT44001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu    |
#|   "/manter-tarefa"   |  "cnos"  | "CNOS"  |  "cnos"  |"Tarefas"                       |"Tarefas" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Tarefas" disponível
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT42002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Taxas de Referência" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#| /consulta-taxa-ref |"cosr-nco2" | "cosr-nco" |"cosr-nco2" | "Consultar" Taxas de Referência | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Taxas de Referência" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT43001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Gerar Relatórios" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  /gerar-relatorio  |  "cnos"  | "CNOS"  |  "cnos"  | Gerar Relatórios              | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Gerar Relatórios" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT44001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu    |
#|   "/manter-tarefa"   |  "cnos"  | "CNOS"  |  "cnos"  |"Tarefas"                       |"Tarefas" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Tarefas" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT44002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Tarefas" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu |
#|   "/manter-tarefa"   | "cosr-nco"  | "cosr-nco" | "cosr-nco"  |"Tarefas"                       |"Tarefas" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Tarefas" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT45001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Retificar Eventos" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  /retificar-evento |"cosr-nco2" | "cosr-nco" |"cosr-nco2" | "Retificar Eventos"           | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Retificar Eventos" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT46001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Retificar Eventos" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| Login     | Perfil   | Senha     | Funcionalidade                | Menu         |
#| "cosr-ne2"  | "COSR-NE"  | "cosr-ne2"  | "Retificar Eventos"           |   "SAGER"   |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Retificar Eventos"
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT47001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Eventos" Consolidados" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|   URLFuncionalidade   | Login     | Perfil   | Senha  | Funcionalidade                | Menu  |
#| /eventos-consolidados |  "cnos"  | "CNOS"  | cnos   | "Consultar" Eventos" Consolidados" | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Eventos" Consolidados" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT47002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Eventos" Consolidados" para um usuário que tenha o perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|   URLFuncionalidade   | Login     | Perfil   | Senha   | Funcionalidade                | Menu  |
#| /eventos-consolidados | "cos-sp"    | COS      | "cos-sp"  | "Consultar" Eventos" Consolidados" | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Eventos" Consolidados" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT47003
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Eventos" Consolidados" para um usuário que tenha o perfil Agente
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|   URLFuncionalidade   | Login     | Perfil   | Senha  | Funcionalidade                | Menu  |
#| /eventos-consolidados | "chesf"     | "Agente " | "chesf"  | "Consultar" Eventos" Consolidados" | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Consultar" Eventos" Consolidados" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT48001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Cenário" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#|  URLFuncionalidade  | Login  | Perfil   | Senha  | Funcionalidade                | Menu  |
#| "/manter-cenario"   | cnos   | "CNOS"  | cnos   | "Manter Cenário"                | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Manter Cenário" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT49001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Parametrização" para um usuário que tenha o perfil CNOS
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja com o "browser" aberto
#Quando eu informo a URL <URLFuncionalidade>
#E eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu deveria abrir a página da funcionalidade <Funcionalidade>.
#| URLFuncionalidade | Login  | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  "/manter-param" | cnos   | "CNOS"  |  "cnos"  | "Manter Parametrização"      | "SAGER" |
#
#Exemplos: 
#Então eu deveria ver a funcionalidade "Manter Parametrização" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT50001
#
#Esquema do Cenario: Verificar o acesso a operação "Consultar" da funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   |  "cnos"  | "CNOS"  |  "cnos"  |"Tarefas"      | "Consultar" |
#
#Exemplos: 
#Então eu deveria ver a operação "Consultar" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT50002
#
#Esquema do Cenario: Verificar o acesso a operação "Consultar" da funcionalidade "Tarefas" para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   | "cosr-nco"  | "cosr-nco" | "cosr-nco"  |"Tarefas"      | "Consultar" |
#
#Exemplos: 
#Então eu deveria ver a operação "Consultar" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT51001
#
#Esquema do Cenario: Verificar o acesso a operação "Incluir" da funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   |  "cnos"  | "CNOS"  |  "cnos"  |"Tarefas"      | "Incluir"   |
#
#Exemplos: 
#Então eu deveria ver a operação "Incluir" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT51002
#
#Esquema do Cenario: Verificar o acesso a operação "Incluir" da funcionalidade "Tarefas" para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   | "cosr-nco1" | "cosr-nco" | "cosr-nco1" |"Tarefas"      | "Incluir"   |
#
#Exemplos: 
#Então eu deveria ver a operação "Incluir" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT52001
#
#Esquema do Cenario: Verificar o acesso a operação "Alterar" da funcionalidade "Tarefas" para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   |"cosr-nco2" | "cosr-nco" |"cosr-nco2" |"Tarefas"      | "Alterar"   |
#
#Exemplos: 
#Então eu deveria ver a operação "Alterar" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT53001
#
#Esquema do Cenario: Verificar o acesso a operação "Atender" da funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   |  "cnos"  | "CNOS"  |  "cnos"  |"Tarefas"      |"Atender"   |
#
#Exemplos: 
#Então eu deveria ver a operação "Atender" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT53002
#
#Esquema do Cenario: Verificar o acesso a operação "Atender" da funcionalidade "Tarefas" para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   | "COSR-NE"1  | "COSR-NE"  | "COSR-NE"1  |"Tarefas"      |"Atender"   |
#
#Exemplos: 
#Então eu deveria ver a operação "Atender" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT54001
#
#Esquema do Cenario: Verificar o acesso a operação "Cancelar" da funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   |  "cnos"  | "CNOS"  |  "cnos"  |"Tarefas"      | Cancelar  |
#
#Exemplos: 
#Então eu deveria ver a operação "Cancelar" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT54002
#
#Esquema do Cenario: Verificar o acesso a operação "Cancelar" da funcionalidade "Tarefas" para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   | "cosr-ne2"  | "COSR-NE"  | "cosr-ne2"  |"Tarefas"      | Cancelar  |
#
#Exemplos: 
#Então eu deveria ver a operação "Cancelar" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT55001
#
#Esquema do Cenario: Verificar o acesso a operação "Agendar" da funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   |  "cnos"  | "CNOS"  |  "cnos"  |"Tarefas"      | Agendar   |
#
#Exemplos: 
#Então eu deveria ver a operação "Agendar" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT55002
#
#Esquema do Cenario: Verificar o acesso a operação "Agendar" da funcionalidade "Tarefas" para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   | cosr-s1   | COSR-S   | cosr-s1   |"Tarefas"      | Agendar   |
#
#Exemplos: 
#Então eu deveria ver a operação "Agendar" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT56001
#
#Esquema do Cenario: Verificar o acesso a operação "Visualizar Eventos" da funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação           |
#|   "/manter-tarefa"   |  "cnos"  | "CNOS"  |  "cnos"  |"Tarefas"      | "Visualizar Eventos" |
#
#Exemplos: 
#Então eu deveria ver a operação "Visualizar Eventos" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT56002
#
#Esquema do Cenario: Verificar o acesso a operação "Visualizar Eventos" da funcionalidade "Tarefas" para um usuário que tenha o perfil COSR
#
####Acessar operações da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação           |
#|   "/manter-tarefa"   | "cosr-se1"  | "COSR-SE"  | "cosr-se1"  |"Tarefas"      | "Visualizar Eventos" |
#
#Exemplos: 
#Então eu deveria ver a operação "Visualizar Eventos" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT57001
#
#Esquema do Cenario: Verificar o acesso a operação "Importar Planilha" da funcionalidade "Retificar Eventos" para um usuário que tenha o perfil COSR
#
####Acessar operação Importar da funcionalidade Retificação de Eventos
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#E eu seleciono o menu <Menu>
#E eu seleciono a funcionalidade <Funcionalidade>
#Então eu deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu   | Funcionalidade    | Operação  |
#|"/retificar-eventos" | "cosr-ne2"  | "COSR-NE"  | "cosr-ne2"  | "SAGER"  | Retificar Eventos | Importar Planilha |
#
#Exemplos: 
#Então eu deveria ver a operação "Importar" disponível
#
#
###Mensagem(s): N/A
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT58001
#
#Esquema do Cenario: Verificar o acesso a operação "Alterar" da funcionalidade "Tarefas" para um usuário que tenha o perfil CNOS
#
####Acessar operação "Alterar" da funcionalidade pela URL"Tarefas"
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a operação <Operação> disponível:
#|  URLFuncionalidade | Login     | Perfil   | Senha     | Menu         | Operação  |
#|   "/manter-tarefa"   |  "cnos"  | "CNOS"  |  "cnos"  |"Tarefas"      | "Alterar"   |
#
#Exemplos: 
#Então eu não deveria ver a operação "Alterar" disponível
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT59001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade "Consultar" Taxas" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  /consulta-taxa   | "furnas"    | "Agente " | "furnas"    | "Consultar" Taxas"             | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Consultar" Taxas"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT60001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade "Solicitar Cálculo de Taxas" para um usuário que tenha o perfil diferente de CNOS; perfil "COSR-NE"
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  /calculo-taxa    | "cosr-ne2"  | "COSR-NE"  | "cosr-ne2"  | "Solicitar Cálculo de Taxas "   | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Solicitar Cálculo de Taxas"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT60002
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade "Solicitar Cálculo de Taxas" para um usuário que tenha o perfil diferente de CNOS; perfil Agente
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  /calculo-taxa    |  "copel"  | "Agente " |  "copel"  | "Solicitar Cálculo de Taxas "   | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Solicitar Cálculo de Taxas"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT61001
#
#Esquema do Cenario: Verificar o acesso  a funcionalidade "Consultar" Disponibilidade" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|     "/disp"         | "furnas"    | "Agente " | "furnas"    | "Consultar" Disponibilidade"     | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Consultar" Disponibilidade"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT62001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Agendamento" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade   | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#| /manter-agendamento | "chesf"     | "Agente " | "chesf"     | "Manter Agendamento"            | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Manter Agendamento"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT63001
#
#Esquema do Cenario: Verificar o acesso  funcionalidade "Consultar" Eventos" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|     "/evento"      |  "copel"  | "Agente " |  "copel"  | "Consultar" Eventos"             | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Consultar" Eventos"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT64001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Taxas de Referência" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade  | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#| /consulta-taxa-ref | "furnas"    | "Agente " | "furnas"    | "Consultar" Taxas de Referência | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Consultar" Taxas de Referência"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT65001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Gerar Relatórios" para um usuário que tenha o perfil diferente de CNOS; perfil "COSR-NE"
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade  | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  /gerar-relatorio  | "cosr-ne2"  | "COSR-NE"  | "cosr-ne2"  | Gerar Relatórios              | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Gerar Relatórios"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT65002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Gerar Relatórios" para um usuário que tenha o perfil diferente de CNOS; perfil Agente
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade  | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  /gerar-relatorio  |  "copel"  | "Agente " |  "copel"  | Gerar Relatórios              | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Gerar Relatórios"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT66001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Tarefas" para um usuário que tenha o perfil diferente de CNOS e COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade  | Login     | Perfil   | Senha     | Funcionalidade                | Menu    |
#|   "/manter-tarefa"   |  "copel"  | "Agente " |  "copel"  |"Tarefas"                       |"Tarefas" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Tarefas"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT67001
#
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Retificar Eventos" para um usuário que tenha o perfil diferente de COSR
#
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade  | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|"/retificar-eventos" |  "cnos"  | "CNOS"  |  "cnos"  | "Retificar Eventos"           | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Retificar Eventos"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT68001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Consultar" Eventos" Consolidados" para um usuário que tenha  perfil diferente de CNOS, COSR e Agente
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#---Existe algum outro perfil além destes 3 para criar os parâmetros deste teste???
#
#Exemplos: 
#---Existe algum outro perfil além destes 3 para criar os parâmetros deste teste???
#
#
###Mensagem(s): ---Existe algum outro perfil além destes 3 para criar os parâmetros deste teste???
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT69001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Cenário" para um usuário que tenha o perfil diferente de CNOS; perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|"/manter-cenario"  | "cosr-ne2"  | "COSR-NE"  | "cosr-ne2"    | "Manter Cenário"                | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Manter Cenário"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT69002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Cenário" para um usuário que tenha o perfil diferente de CNOS; perfil Agente
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|"/manter-cenario"  |  "copel"  | "Agente " |  "copel"  | "Manter Cenário"                | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Manter Cenário"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT70001
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Parametrização" para um usuário que tenha o perfil diferente de CNOS; perfil COSR
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  "/manter-param" | "cosr-ne2"  | "COSR-NE"  | "cosr-ne2"     | "Manter Parametrização"      | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Manter Parametrização"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
#
###@CT70002
#
#Esquema do Cenario: Verificar o acesso a funcionalidade "Manter Parametrização" para um usuário que tenha o perfil diferente de CNOS; perfil Agente
#
####Validar Acesso as funcionalidades pela URL
#Dado que eu esteja na página "Autenticação"
#Quando eu informo no campo "Login" o valor <Login>
#E eu informo no campo "Senha" o valor <Senha>
#E eu aperto o botão "Login"
#Então eu não deveria ver a funcionalidade <Funcionalidade> disponível no menu <Menu> do sistema:
#
#
#--É possível descrever assim na negativa "...eu não deveria..."??
#| URLFuncionalidade | Login     | Perfil   | Senha     | Funcionalidade                | Menu  |
#|  "/manter-param" |  "copel"  | "Agente " |  "copel"  | "Manter Parametrização"      | "SAGER" |
#
#Exemplos: 
#Então eu não deveria ver a funcionalidade "Manter Parametrização"
#
#
###Mensagem(s): Você não tem autorização para acessar esta página
#
#
###----------------------------------------------------------------------------------------------------------------------------------------
#
