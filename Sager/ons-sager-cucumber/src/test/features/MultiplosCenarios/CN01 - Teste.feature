# language: pt

Funcionalidade: Acessar a funcionalidade com um usuário que tenha o perfil CNOS

##@CT01001

Esquema do Cenario: Pesquisar taxas informando a data de início maior que janeiro de 2001

###Pesquisa das Taxas
Dado que eu esteja autenticado com usuário <Login> e perfil <Perfil>
E eu esteja na página "Consultar taxas"
Quando eu informo no campo "Mês inicial" o valor <MesInicial>
E eu informo no campo "Mês final" o valor <MesFinal>
E eu seleciono no campo "tipo de taxa" com o valor <TipoTaxa>
E eu seleciono a instalação <Instalacao> na guia <TipoInstalacao>
E eu aperto o botão <Botao>
Então eu deveria ver no grid <Instalacao> o <Resultado>:
|        Mês          |     TEIPacumulada       |
|   "01/2008 (V1)"    |       "0,00728763"      |
|   "02/2008 (V1)"    |       "0,00731744"      |
|   "03/2008 (V1)"    |       "0,00947601"      |
|   "04/2008 (V1)"    |       "0,00963115"      |

Exemplos: 
|    Login     |    Perfil     | MesInicial |  MesFinal |       TipoTaxa   |  Instalação  | TipoInstalacao |     Botao     |
|      "cnos"    |   "CNOS"    |  "01/2008" | "04/2008" | "TEIP acumulada" |  "CAMPOS"    |     "Usinas"   |   "Pesquisar" |