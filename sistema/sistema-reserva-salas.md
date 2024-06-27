# Sistema de Reserva de Salas
## 🌐 Apresentação


## 📋 Requisitos
### Requisitos Funcionais (RF)

| ID     | Descrição                                     |
|--------|-----------------------------------------------|
| RF001  | O sistema deve disponibilizar uma área (página) na qual os usuários possam se cadastrar. |
| RF002  | O sistema deve permitir o cadastro de usuários, onde devem informar o nome, e-mail, matrícula, login e senha. |
| RF003  | O sistema deve permitir que os usuários efetuem login, para terem acesso às operações disponibilizadas. |
| RF004  | O sistema deve permitir que os usuários efetuem a recuperação da senha, inserindo o e-mail associado no cadastrado do sistema. |
| RF005  | O sistema deve permitir que os usuários cadastrados alterem seus dados. |
| RF006  | O sistema deve permitir que um usuário logado consiga cadastrar uma reserva, informando horário de início e fim, data, finalidade e espaço (sala). |
| RF007  | O sistema deve permitir que um usuário logado exclua sua própria reserva, desde que não esteja efetivada ou em curso. |
| RF008  | O sistema deve permitir que um usuário visualize os detalhes da reserva feita por ele. |
| RF009  | O sistema deve informar via e-mail a confirmação da reserva para o usuário. |
| RF010  | O sistema deve permitir que um usuário visualize todas as suas reservas. |
| RF011  | O sistema deve permitir que usuários visualizem as reservas feitas em uma data específica. |
| RF012  | O sistema deve permitir que usuários visualizem as reservas de uma sala específica por meio de um calendário. |
| RF013  | O sistema deve permitir que um usuário administrador gerencie usuários cadastrados (listar, editar e excluir). |
| RF014  | O sistema deve permitir que um usuário administrador gerencie salas e blocos (cadastrar, listar, editar e excluir). |
| RF015  | O sistema deve permitir que um usuário administrador realize reservas em lote, informando data de início e fim. |
| RF016  | O sistema deve permitir que um usuário administrador exclua reservas em lote. |
| RF017  | O sistema deve permitir que um usuário administrador liste todas as reservas cadastradas. |
| RF018  | O sistema deve permitir que um usuário administrador liste todas as reservas em lotes cadastradas por ele. |

### Requisitos Não-Funcionais (RNF)

| ID     | Descrição                                     |
|--------|-----------------------------------------------|
| RNF001 | O sistema deve funcionar interna e externamente à rede da UFERSA. |
| RNF002 | O sistema deve ser desenvolvido em sistema web. |
| RNF003 | O sistema deve apresentar uma interface simples e intuitiva para o usuário. |
| RNF004 | Para realizar o cadastro no sistema, é necessário que o usuário seja classificado como técnico-administrativo ou docente. |
| RNF005 | O sistema deve criptografar a senha dos usuários para armazená-la em seu banco de dados. |
| RNF006 | O sistema deve retornar um alerta/mensagem afirmando sucesso ou fracasso ao finalizar as operações. |

## 💻 Telas do Sistema
**⚠️ Observações**<br>
- Tamanho Desktop: 1920x1080 pixels
- Tamanho Tablet: 768x1280 pixels
- Tamanho Mobile: 375x780 pixels

Extensão utilizada para captura das imagens: [DevTools Chrome](https://developer.chrome.com/docs/devtools?hl=pt-br)

### Tela de Cadastro de Usuário
> A tela de cadastro de conta do usuário realiza o registro do usuário no sistema. Seu objetivo principal é que novos usuários se registrem no sistema, fornecendo as informações necessárias para criar uma conta e assim ter acesso as funcionalidades do sistema.

**Pré-Condições**
- O sistema está disponível e acessível. 

**Passos**
- O usuário acessa a tela de cadastro de conta, através do link vinculado na tela de login; 
- Na tela de cadastro, o usuário é solicitado a fornecer as seguinte informações:
	- Nome;
	- Email;
	- Matricula;
	- Login;
	- Senha. 
- Após inserir as informações válidas, o usuário clica no botão "Cadastrar".

**Imagens**
<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Cadastro Desktop](/imgs/tela-cadastro-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Cadastro Tablet](/imgs/tela-cadastro-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Cadastro Mobile](/imgs/tela-cadastro-mobile.png)
</div>
</details>

### Tela de Login
> A tela de login é a primeira interface onde os usuários interagem ao acessar o sistema. Seu objetivo principal é de autenticação, garantindo que apenas pessoas autorizadas tenham acesso às funcionalidades dentro do sistema.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário ter cadastro no sistema e suas credenciais estarem corretas.

**Passos**
- O usuário acessa a tela de login do sistema; 
- Insere o "Login" e "Senha" nos campos correspondentes; 
- Clica no botão "Entrar".

**Imagens**
<details>
<summary>Mostrar Tela Desktop</summary>

![Tela Login Desktop](/imgs/tela-login-desktop.png)
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Login Tablet](/imgs/tela-login-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Login Mobile](/imgs/tela-login-mobile.png)
</div>
</details>

### Tela de Recuperar Senha
> A tela de recuperar senha realiza a recuperação de senha do usuário no sistema. Seu objetivo principal é de recuperação ao acesso no sistema, na qual permite que os usuários redefinam suas senhas caso as tenham esquecido, assim, é enviado ao seu e-mail de cadastro uma nova senha.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário ter registro no sistema.

**Passos**
- O usuário acessa a tela de recuperação de senha, através do link vinculado na tela de login; 
- Na tela de recuperação de senha, o usuário é solicitado a fornecer o endereço de e-mail associado à sua conta; 
- O usuário insere o "E-mail" no campo correspondente; 
- Clica no botão "Recuperar".

**Imagens**
<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Recuperacao Desktop](/imgs/tela-recuperacao-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Recuperacao Tablet](/imgs/tela-recuperacao-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Recuperacao Mobile](/imgs/tela-recuperacao-mobile.png)
</div>
</details>

### Tela Ativação de Conta
> A tela de ativação de conta é disponível após inserir as credenciais de login pela primeira vez para fazer acesso ao sistema, nela é necessário fornecer o código de ativação de conta. Seu objetivo principal é de autenticação e confirmação de cadastro garantindo a ativação da conta registrada no sistema.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário ter registro no sistema e suas credenciais estarem corretas.
- Ser o seu primeiro acesso.

**Passos**
- O usuário acessa a tela de login do sistema; 
- Insere o "Login" e "Senha" nos campos correspondentes pela primeira vez após o cadastro; 
- Clica no botão "Entrar";
- Insere o código de ativação enviado pelo e-mail.

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Ativar Conta Desktop](/imgs/tela-ativarConta-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Ativar Conta Tablet](/imgs/tela-ativarConta-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Ativar Conta Mobile](/imgs/tela-ativarConta-mobile.png)
</div>
</details>

### Tela de Consultar Reserva por Data
> A tela de consulta de reserva por data realiza a listagem das reservas por data no sistema. Seu objetivo principal é de visualização das reservas filtrada pela data informada.

**Pré-Condições**
- O sistema está disponível e acessível. 

**Passos**
- O usuário acessa a consultar reserva por data, através do link vinculado na tela de login; 
- O usuário seleciona a data específica para a qual deseja consultar as reservas;
- O sistema realiza a listagem das reservas da data informada.

**Imagens**
<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Consulta Desktop](/imgs/tela-consulta-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Consulta Tablet](/imgs/tela-consulta-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Consulta Mobile](/imgs/tela-consulta-mobile.png)
</div>
</details>

### Tela de Visualizar Calendário por Sala
> A tela de visualizar calendário realiza a listagem das reservas por sala diante de um calendário no sistema. Seu objetivo principal é de visualização dessas reservas em uma interface de calendário.

**Pré-Condições**
- O sistema está disponível e acessível. 

**Passos**
- O usuário acessa a visualização de calendário por sala, através do link vinculado na tela de login; 
- O usuário seleciona a sala específica para a qual deseja visualizar as reservas;
- O sistema exibe um calendário com as datas e as reservas agendadas para a sala selecionada.

**Imagens**
<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Calendário Desktop](/imgs/tela-calendario-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Calendário Tablet](/imgs/tela-calendario-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Calendário Mobile](/imgs/tela-calendario-mobile.png)
</div>
</details>

### Tela Home
> A tela home é a primeira página que o usuário visualiza após o acesso ao sistema. Seu objetivo principal é fornecer uma visão geral das funcionalidades do sistema, bem como os recursos disponíveis.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário está autenticado no sistema.

**Elementos da tela (Usuário Padrão)**
- Menu de navegação a esquerda fixo, com as opções:
- Home
- Reservas
	- Nova Reserva
	- Minhas Reservas
	- Consultas
- Meu Perfil
- Sair

**Elementos da tela (Usuário Administrador)**
- Menu de navegação a esquerda fixo, com as opções:
- Home
- Reservas
  - Nova Reserva
  - Reserva em Lote
  - Minhas Reservas
  - Consultas
  - Todas as Reservas
  - Listar Lotes
- Salas
  - Nova Sala
  - Listar Salas
- Usuários
- Meu Perfil
- Sair

**Imagens**</br>
:warning: A disponibilidade dessas imagens será restrita ao usuário administrador, uma vez que o usuário padrão possui especificações similares, mas com algumas funções ausentes.

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Home Desktop](/imgs/tela-home-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Home Tablet](/imgs/tela-home-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Home Mobile](/imgs/tela-home-mobile.png)
</div>
</details>

### Tela de Meu Perfil 
> A tela de alteração de dados realiza a mudança de informações (nome e/ou senha) no sistema. Seu objetivo principal é que os usuários possam realizar mudanças, caso haja necessidade, das informações que desejam.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário ter registro no sistema.

**Passos**
- O usuário acessa a opção de "Meu Perfil", através do menu lateral na tela home; 
- Na tela de edição, o sistema mostra as informações já registradas com possibilidade de mudanças nas seguinte informações:
	- Nome;
	- Senha. 
- Após inserir as informações válidas, o usuário clica no botão "Atualizar".

**Imagens**
<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Meu Perfil Desktop](/imgs/tela-meuPerfil-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Meu Perfil Tablet](/imgs/tela-meuPerfil-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Meu Perfil Mobile](/imgs/tela-meuPerfil-mobile.png)
</div>
</details>

### Tela de Nova Reserva
> A tela de nova reserva realiza o registro da reserva da sala no sistema. Seu objetivo principal é que os usuários consigam registrar no sistema uma reserva de ambiente para usufruir dos recursos liberados pela universidade.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário está autenticado no sistema.

**Passos**
- O usuário acessa a opção de nova reserva, através do menu lateral na tela home; 
- Na tela de cadastro da reserva, o usuário é solicitado a fornecer as seguinte informações:
	- Data;
	- Horário Inicial;
	- Horário Final;
	- Finalidade. 
- Após inserir as informações válidas, o usuário clica no botão "Buscar";
- O sistema retorna as salas disponíveis para aquele dia e horário informado;
- O usuário clica no botão "Reservar"

**Imagens**

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Nova Reserva Desktop](/imgs/tela-novaReserva-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Nova Reserva Tablet](/imgs/tela-novaReserva-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Nova Reserva Mobile](/imgs/tela-novaReserva-mobile.png)
</div>
</details>

### Tela Minhas Reservas
> A tela de Minhas Reservas ou Listar Reservas irá mostrar as reservas realizadas pelo usuário logado. Seu objetivo principal é de listagem para a visualização das reservas cadastradas.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário ter registro no sistema e suas credenciais estarem corretas.

**Passos**
- O usuário acessa a opção de "Reserva", através do menu lateral na tela home. 
- O usuário acessa a opção "Minhas Reservas", após a abertura do menu expansivo.

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Minhas Reservas Desktop](/imgs/tela-minhaReserva-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Minhas Reservas Tablet](/imgs/tela-minhaReserva-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Minhas Reservas Mobile](/imgs/tela-minhaReserva-mobile.png)
</div>
</details>

### Tela Reserva em Lote (Usuário Administrador)
> A tela de reserva em lote realiza o registro de várias reservas no sistema. Seu objetivo principal é que os usuários do tipo administrador consigam registrar no sistema um lote de reserva de uma única só vez.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário está autenticado no sistema e ser do tipo administrador.

**Passos**
- O usuário administrador acessa a opção de "Reserva", através do menu lateral na tela home. 
- O usuário administrador acessa a opção "Reserva em Lote", após a abertura do menu expansivo.
- Na tela de cadastro da reserva em lote, o usuário administrador é solicitado a fornecer as seguinte informações:
	- Data Inicial;
	- Data Final;
	- Horário Inicial;
	- Horário Final;
	- Finalidade (Nome da Disciplina - Nome e Sobrenome do professor). 
- Após inserir as informações válidas, o usuário administrador clica no botão "Buscar".
- O sistema retorna as salas disponíveis para as informações especificadas;
- O usuário administrador clica no botão "Reservar".

**Imagens** </br>
:warning: A disponibilidade dessas imagens será restrita ao usuário administrador, uma vez que o usuário padrão possui especificações similares, mas com algumas funções ausentes.

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Reserva em Lote Desktop](/imgs/tela-reservaLote-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Reserva em Lote Tablet](/imgs/tela-reservaLote-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Reserva em Lote Mobile](/imgs/tela-reservaLote-mobile.png)
</div>
</details>

### Tela Todas as Reservas (Usuário Administrador)
> A tela de Todas as Reservas é disponível apenas para os usuários administradores e faz a listagem geral das reservas cadastradas no sistema (exceto as reservas em lote). Seu objetivo principal é de listagem das informações das reservas cadastradas.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário está autenticado no sistema e ser do tipo administrador.

**Passos**
- O usuário acessa a opção de "Reserva", através do menu lateral na tela home. 
- O usuário acessa a opção "Todas as Reservas", após a abertura do menu expansivo.

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Todas as Reservas Desktop](/imgs/tela-todasReservas-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Todas as Reservas Tablet](/imgs/tela-todasReservas-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Todas as Reservas Mobile](/imgs/tela-todasReservas-mobile.png)
</div>
</details>

### Tela Listar Lotes (Usuário Administrador)
> A tela de Listar Lotes é disponível apenas para os usuários administradores e irá mostrar as reservas em lote realizadas cadastradas vinculada ao usuário logado. Seu objetivo principal é de listagem das informações das reservas em lote cadastradas. 

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário está autenticado no sistema e ser do tipo administrador.

**Passos**
- O usuário acessa a opção de "Reserva", através do menu lateral na tela home. 
- O usuário acessa a opção "Listar Lotes", após a abertura do menu expansivo.

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Listar Lotes Desktop](/imgs/tela-listarLotes-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Listar Lotes Tablet](/imgs/tela-listarLotes-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Listar Lotes Mobile](/imgs/tela-listarLotes-mobile.png)
</div>
</details>

### Tela Listar Usuários (Usuário Administrador)
> A tela de Listar Usuários é disponível apenas para os usuários administradores e irá mostrar todos os usuários cadastrados no sistema. Seu objetivo principal é de listagem das informações dos usuários permitindo ao administrador fazer edições e/ou exclusão. 

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário está autenticado no sistema e ser do tipo administrador.

**Passos**
- O usuário acessa a opção de "Usuários", através do menu lateral na tela home. 

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Listar Usuarios Desktop](/imgs/tela-listarUsuarios-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Listar Usuarios Tablet](/imgs/tela-listarUsuarios-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Listar Usuarios Mobile](/imgs/tela-listarUsuarios-mobile.png)
</div>
</details>

### Tela Nova Sala (Usuário Administrador)
> A tela de Nova Sala realiza o registro de um espaço físico no sistema. Seu objetivo principal é que os usuários administradores consigam registrar no sistema um ambiente para sincronizar com a realidade dos ambiente existentes da universidade.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário está autenticado no sistema e ser do tipo administrador.

**Passos**
- O usuário acessa a opção de "Sala", através do menu lateral na tela home.
- O usuário acessa a opção "Nova Sala", após a abertura do menu expansivo. 
- Na tela de cadastro de sala, o usuário administrador é solicitado a fornecer as seguinte informações:
	- Nome;
	- Capacidade;
	- Informações;
	- Bloco;
	- Status;
	- Tipo.
- Após inserir as informações válidas, o usuário administrador clica no botão "Cadastrar".

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Nova Sala Desktop](/imgs/tela-novaSala-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Nova Sala Tablet](/imgs/tela-novaSala-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Nova Sala Mobile](/imgs/tela-novaSala-mobile.png)
</div>
</details>

### Tela Editar Sala (Usuário Administrador)
> A tela de Edição de Sala é semelhante a tela de cadastro, porém com informações extras. Seu objetivo principal é que os usuários administradores consigam editar as informações inconsistentes dos ambientes registrados no sistema.

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário está autenticado no sistema e ser do tipo administrador.

**Passos**
- O usuário acessa a opção de "Sala", através do menu lateral na tela home;
- O usuário acessa a opção "Listar Sala", após a abertura do menu expansivo;
- O usuário aperta no icone de lápis da sala que deseja editar;
- Na tela de edição de sala, o usuário administrador pode fornecer as seguinte informações:
	- Nome (obrigatório);
	- Capacidade (obrigatório);
	- Informações;
	- Status;
	- Bloco (obrigatório);
	- Tipo.
- Após inserir as informações válidas, o usuário administrador clica no botão "Atualizar".
- 
<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Editar Sala Desktop](/imgs/tela-editarSala-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Editar Sala Tablet](/imgs/tela-editarSala-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Editar Sala Mobile](/imgs/tela-editarSala-mobile.png)
</div>
</details>

### Tela Listar Salas (Usuário Administrador)
> A tela de Listar Salas é disponível apenas para os usuários administradores e irá mostrar todos os espaços físicos cadastrados no sistema. Seu objetivo principal é de listagem das informações dos espaços permitindo ao administrador fazer edições e/ou exclusão. 

**Pré-Condições**
- O sistema está disponível e acessível. 
- O usuário está autenticado no sistema e ser do tipo administrador.

**Passos**
- O usuário acessa a opção de "Sala", através do menu lateral na tela home;
- O usuário acessa a opção "Listar Sala", após a abertura do menu expansivo;

<details>
<summary>Tela Desktop</summary>

<div style="text-align:center;">

![Tela Listar Sala Desktop](/imgs/tela-listarSala-desktop.png)
</div>
</details>

<details>
<summary>Tela Tablet</summary>

<div style="text-align:center;">

![Tela Listar Sala Tablet](/imgs/tela-listarSala-tablet.png)
</div>
</details>

<details>
<summary>Tela Mobile</summary>

<div style="text-align:center;">

![Tela Listar Sala Mobile](/imgs/tela-listarSala-mobile.png)
</div>
</details>

---
[🔙 Voltar](../)