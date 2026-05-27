"use strict";

async function carregarProfessores() {
    try {
        const professores = await apiFetch("/api/professores");
        const tbody = byId("tabelaProfessores");
        tbody.innerHTML = "";

        if (!professores || professores.length === 0) {
            tbody.innerHTML = `<tr><td colspan="5" class="text-center text-secondary">Nenhum professor encontrado.</td></tr>`;
            return;
        }

        professores.forEach(professor => {
            tbody.innerHTML += `
                <tr>
                    <td>${professor.id}</td>
                    <td>${professor.nome || ""}</td>
                    <td>${professor.email || ""}</td>
                    <td>${professor.nomeCurso || "Sem curso"}</td>
                    <td class="text-end">
                        <a class="btn btn-sm btn-warning" href="professor-form.html?id=${professor.id}">Editar</a>
                        <button class="btn btn-sm btn-danger" onclick="deletarProfessor(${professor.id})">Deletar</button>
                    </td>
                </tr>`;
        });
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function salvarProfessor(event) {
    event.preventDefault();

    const id = byId("id").value;
    const professor = {
        nome: byId("nome").value.trim(),
        email: byId("email").value.trim(),
        senha: byId("senha").value.trim()
    };

    if (!professor.nome) return alert("Nome é obrigatório!");
    if (!professor.email) return alert("Email é obrigatório!");
    if (!professor.senha) return alert("Senha é obrigatória!");

    try {
        if (id && id !== "0") {
            await apiFetch(`/api/professores/${id}`, {
                method: "PUT",
                body: JSON.stringify(professor)
            });
            alert("Professor alterado com sucesso!");
        } else {
            await apiFetch("/api/professores", {
                method: "POST",
                body: JSON.stringify(professor)
            });
            limparFormulario("form");
            byId("id").value = "0";
            alert("Professor criado com sucesso!");
        }
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function carregarProfessorForm() {
    const id = getParam("id");
    if (!id) return;

    byId("tituloPagina").innerText = "Editar Professor";
    byId("textoBotao").innerText = "Salvar alterações";

    try {
        const professor = await apiFetch(`/api/professores/${id}`);
        byId("id").value = professor.id || 0;
        byId("nome").value = professor.nome || "";
        byId("email").value = professor.email || "";
        byId("senha").value = "";
        byId("senha").placeholder = "Digite uma nova senha";
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function deletarProfessor(id) {
    if (!confirmarDelecao("este professor")) return;

    try {
        await apiFetch(`/api/professores/${id}`, { method: "DELETE" });
        alert("Professor deletado com sucesso!");
        carregarProfessores();
    } catch (erro) {
        mostrarErro(erro);
    }
}
