# Livraria Virtual

O app consome a Open Library API para listar livros e utiliza o Firebase Authentication para o sistema de login e cadastro.

## Funcionalidades
- **Autenticação:** Cadastro e login reais integrados ao Firebase (E-mail e Senha).
- **Busca de Livros:** Pesquisa em tempo real de livros com exibição de capa, autores, nota e ano de publicação.

## Tecnologias e Arquitetura
O projeto segue a arquitetura MVVM (Model-View-ViewModel) com o padrão de Repository para separação de responsabilidades.
- **Kotlin** + **Coroutines** + **StateFlow**
- **Jetpack Compose** + **Navigation Compose**
- **Retrofit** & **Gson** (Consumo da API Rest)
- **Coil** (Carregamento assíncrono de imagens)
- **Firebase Authentication**

## Como rodar o projeto
1. Abra o projeto no **Android Studio**.
2. Certifique-se de ter um arquivo `google-services.json` válido na pasta `app/` vinculado ao seu projeto Firebase (com Email/Password ativado).
3. Clique em **Sync Project with Gradle Files** para baixar as dependências (`Retrofit`, `Coil`, `Firebase`, etc).
4. Clique em **Run (Shift + F10)** para instalar o app no emulador ou dispositivo físico.

---
*Desenvolvido com foco em código limpo, design reativo e usabilidade.*
