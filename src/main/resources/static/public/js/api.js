"use strict";

const ApiConfig = {
    baseUrl: localStorage.getItem("apiBaseUrl") || "",
    username: localStorage.getItem("apiUsername") || "admin",
    password: localStorage.getItem("apiPassword") || "123456"
};

function setApiConfig(baseUrl, username, password) {
    localStorage.setItem("apiBaseUrl", baseUrl || "");
    localStorage.setItem("apiUsername", username || "admin");
    localStorage.setItem("apiPassword", password || "123456");
    alert("Configurações salvas!");
    location.reload();
}

function authHeader() {
    return "Basic " + btoa(ApiConfig.username + ":" + ApiConfig.password);
}

function getParam(name) {
    return new URLSearchParams(window.location.search).get(name);
}

function byId(id) {
    return document.getElementById(id);
}

function limparFormulario(formId) {
    const form = byId(formId);
    if (form) form.reset();
}

async function tratarResposta(response) {
    if (response.status === 204) {
        return null;
    }

    const contentType = response.headers.get("content-type") || "";
    const isJson = contentType.includes("application/json");
    const data = isJson ? await response.json() : await response.text();

    if (!response.ok) {
        let mensagem = "Erro na requisição. Status: " + response.status;

        if (data) {
            if (typeof data === "string") {
                mensagem = data;
            } else if (data.mensagem) {
                mensagem = data.mensagem;
            } else if (data.message) {
                mensagem = data.message;
            } else if (data.error) {
                mensagem = data.error;
            }
        }

        throw new Error(mensagem);
    }

    return data;
}

async function apiFetch(path, options = {}) {
    const headers = {
        "Content-Type": "application/json",
        "Authorization": authHeader(),
        ...(options.headers || {})
    };

    try {
        const response = await fetch(ApiConfig.baseUrl + path, {
            ...options,
            headers
        });

        return await tratarResposta(response);
    } catch (erro) {
        if (erro.message && erro.message.includes("Failed to fetch")) {
            throw new Error("Não foi possível conectar na API. Confira se o Spring Boot está rodando e se você está acessando o front por http://localhost:8082/index.html.");
        }
        throw erro;
    }
}

function mostrarErro(erro) {
    alert(erro.message || "Ocorreu um erro inesperado.");
}

function confirmarDelecao(nome) {
    return confirm("Tem certeza que deseja deletar " + nome + "?");
}

function montarNavbar(active) {
    return `
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid px-lg-5">
            <a class="navbar-brand fw-bold" href="index.html">Game Class</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Abrir menu">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto gap-lg-3">
                    <li class="nav-item"><a class="nav-link ${active === "home" ? "active" : ""}" href="index.html">Home</a></li>
                    <li class="nav-item"><a class="nav-link ${active === "alunos" ? "active" : ""}" href="alunos.html">Alunos</a></li>
                    <li class="nav-item"><a class="nav-link ${active === "professores" ? "active" : ""}" href="professores.html">Professores</a></li>
                    <li class="nav-item"><a class="nav-link ${active === "cursos" ? "active" : ""}" href="cursos.html">Cursos</a></li>
                    <li class="nav-item"><a class="nav-link ${active === "matriculas" ? "active" : ""}" href="matriculas.html">Matrículas</a></li>
                    <li class="nav-item"><a class="nav-link ${active === "perfil" ? "active" : ""}" href="perfil.html">Perfil</a></li>
                </ul>
            </div>
        </div>
    </nav>`;
}

function montarFooter() {
    return `
    <footer class="py-4 text-center text-light bg-dark mt-auto">
        <p class="mb-0">Game Class - Plataforma de cursos online</p>
    </footer>`;
}

function initLayout(active) {
    const header = byId("header");
    const footer = byId("footer");
    if (header) header.innerHTML = montarNavbar(active);
    if (footer) footer.innerHTML = montarFooter();
}

function abrirConfigApi() {
    const baseUrl = prompt("URL da API (deixe vazio para usar a mesma origem do Spring Boot):", ApiConfig.baseUrl);
    if (baseUrl === null) return;

    const username = prompt("Usuário da API:", ApiConfig.username);
    if (username === null) return;

    const password = prompt("Senha da API:", ApiConfig.password);
    if (password === null) return;

    setApiConfig(baseUrl, username, password);
}
