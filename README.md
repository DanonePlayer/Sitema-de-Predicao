# hands-on-t5-edge-neo
Repositório do time Edge Neo (turma 5) para o projeto do Hands On

<details>

<summary> <h2>Objetivos das Sprints</h2> </summary>

**1. Sprint:**
- Criar o prototipo. [✓]
- Documentos de Requisitos. [✓]
- Banco de Dados da NTI. [✓]
- Criação de Fluxo. [✓]
- Entendimento das personas (Aluno, Coordenador e Administrador). [✓]
- Leitura do TCC. [✓]

**2. Sprint:**

- Modelagem do Banco de Dados.
- Criação do ambiente no vscode (Back-end e Front-end).
- Criação das páginas (html, css).
- Estudo do WEKA.

</details>

<details>

<summary>
    <h2>Commit Semântico</h2>
</summary>

**Lembrar: git commit -m "feat: ....... #numero da issue"**

- feat: Tratam adições de novas funcionalidades ou de quaisquer outras novas implantações ao código.
- fix: Essencialmente definem o tratamento de correções de bugs.
- refactor: Tipo utilizado em quaisquer mudanças que sejam executados no código, porém não alterem a funcionalidade final da tarefa impactada.
- test: Adicionando testes ausentes ou corrigindo testes existentes nos processos de testes automatizados (TDD).
- docs: referem-se a inclusão ou alteração somente de arquivos de documentação.
- build: Alterações que afetam o sistema de construção ou dependências externas (escopos de exemplo: gulp, broccoli, npm).

</details>

<details>
<summary>
    <h2>Como inciar a aplicação</h2>
</summary>

<h3>Back-end</h3>

A aplicação back-end pode ser iniciada pelo Spring Boot Dashboard ou com o Maven.

1. No Spring Boot Dashboard, clicar em "Run" na aplicação "sgcmapi".

2. Se optar pelo Maven, no prompt de comandos, a partir do diretório `./sgcmapi`:

    a. Para iniciar a aplicação com o Maven:

    ```console
    mvn spring-boot:run
    ```

    Ou

    b. Para compilar o pacote e depois executar o JAR gerado:

    ```console
    mvn package
    java -jar target\sgcmapi.jar
    ```

A aplicação vai iniciar no endereço <https://localhost:9000/>, com acesso local a base de dados MySQL, por meio da porta padrão 3306, além de usuário e senha "root".

<h3>Front-end</h3>

As dependências do projeto não são compartilhadas no repositório. Para instalar as dependências, a partir do diretório `./sgcmapp`, no prompt de comandos, digite:

```console
npm install
```

Para iniciar a aplicação, a partir do diretório `./sgcmapp`, execute o comando:

```console
ng serve
```

A aplicação vai iniciar no endereço <http://localhost:4200>.

<h3>Banco de Dados</h3>

- Verificar se o MySQL está funcionando:
- Para tentar conectar no MySQL, no prompt de comandos digite:

    ```console
    mysql -u root -p
    ```

- Tentar acessar com senha em branco ou senha igual ao nome de usuário (root).
- Tutorial para resetar a senha de root, caso necessário: <https://dev.mysql.com/doc/mysql-windows-excerpt/8.0/en/resetting-permissions-windows.html>

- Se necessário, realizar a instalação:
- [Link para download](https://dev.mysql.com/downloads/file/?id=516927)
- [Tutorial de instalação](https://github.com/webacademyufac/tutoriais/blob/main/mysql/mysql.md)

</details>

<details>

<summary>
    <h2>Ferramentas</h2>
</summary>

<h3>JDK 17</h3>

- Para verificar se o JDK está corretamente instalado e configurado, digite no prompt de comandos:

```console
javac -version
```

- Se necessário, realizar a instalação e configuração:
- [Link para download](https://download.oracle.com/java/17/archive/jdk-17.0.10_windows-x64_bin.msi)
- Criar a variável de ambiente JAVA_HOME configurada para o diretório de instalação do JDK. Exemplo: “C:\Program Files\Java\jdk-17”.
- Adicionar “%JAVA_HOME%\bin” na variável de ambiente PATH.
- [Tutorial de configuração](https://mkyong.com/java/how-to-set-java_home-on-windows-10/)

<h3>Maven</h3>

- Para verificar se o Maven está corretamente instalado e configurado, digite no prompt de comandos:

```console
mvn -version
```

- Se necessário, realizar a instalação e configuração:
- [Link para download](https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.zip)
- Adicionar o diretório de instalação do Maven na variável de ambiente PATH. Exemplo: “C:\apache-maven\bin”.
- [Tutorial de instalação](https://mkyong.com/maven/how-to-install-maven-in-windows/)

<h3>Node.js (e npm)</h3>
  - Versão 20 (LTS).
  - Para verificar a versão do Node.js, no prompt de comandos digite:

    ```console
    node --version
    ```

  - [Link para download](https://nodejs.org/dist/v20.14.0/node-v20.14.0-x64.msi)

<h3>Angular CLI</h3>
- Versão 17.
- Para verificar a versão do Angular CLI, no prompt de comandos digite:

```console
ng version
```

- Para instalar o Angular CLI, no prompt de comandos digite:

```console
npm i -g @angular/cli@17.3.8
```

- [Tutorial de instalação](https://v17.angular.io/guide/setup-local)

</details>


