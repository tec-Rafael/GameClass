"use strict";

async function carregarAlunos() {
    try {
        const alunos = await apiFetch("/api/alunos");
        const tbody = byId("tabelaAlunos");
        tbody.innerHTML = "";

        if (!alunos || alunos.length === 0) {
            tbody.innerHTML = `<tr><td colspan="7" class="text-center text-secondary">Nenhum aluno encontrado.</td></tr>`;
            return;
        }

        alunos.forEach(aluno => {
            tbody.innerHTML += `
                <tr>
                    <td>${aluno.id}</td>
                    <td>${aluno.nome || ""}</td>
                    <td>${aluno.email || ""}</td>
                    <td>${aluno.ra || ""}</td>
                    <td><span class="badge text-bg-info">${aluno.nivelConta || ""}</span></td>
                    <td>${aluno.cursosAprovados ?? 0}</td>
                    <td class="text-end">
                        <a class="btn btn-sm btn-warning" href="aluno-form.html?id=${aluno.id}">Editar</a>
                        <button class="btn btn-sm btn-danger" onclick="deletarAluno(${aluno.id})">Deletar</button>
                    </td>
                </tr>`;
        });
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function salvarAluno(event) {
    event.preventDefault();

    const id = byId("id").value;
    const aluno = {
        nome: byId("nome").value.trim(),
        email: byId("email").value.trim(),
        ra: byId("ra").value.trim(),
        senha: byId("senha").value.trim()
    };

    if (!aluno.nome) return alert("Nome é obrigatório!");
    if (!aluno.email) return alert("Email é obrigatório!");
    if (!aluno.ra) return alert("RA é obrigatório!");
    if (!aluno.senha) return alert("Senha é obrigatória!");

    try {
        if (id && id !== "0") {
            await apiFetch(`/api/alunos/${id}`, {
                method: "PUT",
                body: JSON.stringify(aluno)
            });
            alert("Aluno alterado com sucesso!");
        } else {
            await apiFetch("/api/alunos", {
                method: "POST",
                body: JSON.stringify(aluno)
            });
            limparFormulario("form");
            byId("id").value = "0";
            alert("Aluno criado com sucesso!");
        }
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function carregarAlunoForm() {
    const id = getParam("id");
    if (!id) return;

    byId("tituloPagina").innerText = "Editar Aluno";
    byId("textoBotao").innerText = "Salvar alterações";

    try {
        const aluno = await apiFetch(`/api/alunos/${id}`);
        byId("id").value = aluno.id || 0;
        byId("nome").value = aluno.nome || "";
        byId("email").value = aluno.email || "";
        byId("ra").value = aluno.ra || "";
        byId("senha").value = "";
        byId("senha").placeholder = "Digite uma nova senha";
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function deletarAluno(id) {
    if (!confirmarDelecao("este aluno")) return;

    try {
        await apiFetch(`/api/alunos/${id}`, { method: "DELETE" });
        alert("Aluno deletado com sucesso!");
        carregarAlunos();
    } catch (erro) {
        mostrarErro(erro);
    }
}
