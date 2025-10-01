# 🎬 ClimaFlim  

**ClimaFlim** é um aplicativo Android criado para auxiliar o usuário a escolher um filme ou série para assistir usando o clima de algum estado ou país no momento.  
As regras do gênero que irá ser listado você pode conferir na documentação da [API ClimaApi](https://github.com/wpmello/climafilm-api).  

---

## 🛠️ Tecnologias utilizadas
<p align="left">
  <img src="https://img.shields.io/badge/Java-17-blue?logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Kotlin-1.9-purple?logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Android-Compose-brightgreen?logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Gradle-8.0-black?logo=gradle&logoColor=white" />
</p> 

---

## ⚙️ Funcionalidades  

- [x] Listagem e atualização de filmes em tempo real  
- [x] Tela de pesquisa para encontrar um estado/país e receber filmes com o gênero baseado no clima do local pesquisado  
- [x] Tela de detalhes do filme  
- [x] Tela de configuração (idioma, tema do aplicativo, formato de temperatura °C/°F, etc.)  
- [ ] Tela de IA para auxílio na escolha do filme/série (em desenvolvimento)  
- [ ] Tela de login (em desenvolvimento)  
- [ ] Sistema de assinatura mensal (em desenvolvimento)  

---

## 📸 Screenshots / GIFs  

<table>
  <tr>
    <td><img src="tela-inicial.jpeg" width="250"/></td>
    <td><img src="tela-clima.jpeg" width="250"/></td>
    <td><img src="detalhe-filme.jpeg" width="250"/></td>
    <td><img src="tela-configuração.jpeg" width="250"/></td>
  </tr>
  <tr>
    <td><p align="center">Tela Inicial</p></td>
    <td><p align="center">Tela de Clima</p></td>
    <td><p align="center">Detalhes do filme</p></td>
    <td><p align="center">Tela de Configuração</p></td>
  </tr>
</table>

---

## 📲 Download para testar o app
Baixe a versão mais recente do ClimaFlim e instale no seu dispositivo Android:

[📥 Baixar última versão](https://github.com/wpmello/climafilm-app/releases/tag/v1.0.0)

---

## 📦 Como rodar o projeto localmente

Se você quiser clonar e rodar o projeto na sua máquina, siga os passos abaixo:

### 🔹 Pré-requisitos
- [Android Studio](https://developer.android.com/studio) (última versão recomendada).
- [Java JDK 17+](https://adoptium.net/).
- [Gradle](https://gradle.org/install/) (opcional, já vem no Android Studio).
- Git instalado na máquina.

### 🔹 Passo a passo

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repo.git
   
2. Vá em File > Open... e selecione a pasta do projeto.

3. Aguarde o Android Studio sincronizar o Gradle e baixar as dependências.

4. Conecte um dispositivo Android ou inicie um Emulador no Android Studio.

5. Clique em ▶️ Run para instalar e executar o app.

### 🔹 Observações

- Recomendo usar o emulador do Android Studio com Android 10+.
- O Gradle e o SDK serão baixados automaticamente, mas pode levar alguns minutos na primeira vez.
