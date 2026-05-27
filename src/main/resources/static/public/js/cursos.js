"use strict";

async function carregarCursos() {
    try {
        const cursos = await apiFetch("/api/cursos");
        const tbody = byId("tabelaCursos");
        tbody.innerHTML = "";

        if (!cursos || cursos.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" class="text-center text-secondary">Nenhum curso encontrado.</td></tr>`;
            return;
        }

        cursos.forEach(curso => {
            tbody.innerHTML += `
                <tr>
                    <td>${curso.id}</td>
                    <td>${curso.titulo || ""}</td>
                    <td>${curso.nomeProfessor || ""}</td>
                    <td>${curso.professorId || ""}</td>
                    <td>${curso.alunos ? curso.alunos.length : 0}</td>
                    <td class="text-end">
                        <a class="btn btn-sm btn-info" href="curso-detalhe.html?id=${curso.id}">Abrir</a>
                        <a class="btn btn-sm btn-warning" href="curso-form.html?id=${curso.id}">Editar</a>
                        <button class="btn btn-sm btn-danger" onclick="deletarCurso(${curso.id})">Deletar</button>
                    </td>
                </tr>`;
        });
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function salvarCurso(event) {
    event.preventDefault();

    const id = byId("id").value;
    const curso = {
        titulo: byId("titulo").value.trim(),
        descricao: byId("descricao").value.trim(),
        professorId: Number(byId("professorId").value)
    };

    if (!curso.titulo) return alert("Título é obrigatório!");
    if (!curso.professorId) return alert("ID do professor é obrigatório!");

    try {
        if (id && id !== "0") {
            await apiFetch(`/api/cursos/${id}`, {
                method: "PUT",
                body: JSON.stringify(curso)
            });
            alert("Curso alterado com sucesso!");
        } else {
            await apiFetch("/api/cursos", {
                method: "POST",
                body: JSON.stringify(curso)
            });
            limparFormulario("form");
            byId("id").value = "0";
            alert("Curso criado com sucesso!");
        }
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function carregarCursoForm() {
    const id = getParam("id");
    if (!id) return;

    byId("tituloPagina").innerText = "Editar Curso";
    byId("textoBotao").innerText = "Salvar alterações";

    try {
        const curso = await apiFetch(`/api/cursos/${id}`);
        byId("id").value = curso.id || 0;
        byId("titulo").value = curso.titulo || "";
        byId("descricao").value = curso.descricao || "";
        byId("professorId").value = curso.professorId || "";
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function deletarCurso(id) {
    if (!confirmarDelecao("este curso")) return;

    try {
        await apiFetch(`/api/cursos/${id}`, { method: "DELETE" });
        alert("Curso deletado com sucesso!");
        carregarCursos();
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function carregarCursoDetalhe() {
    const id = getParam("id");
    if (!id) {
        alert("ID do curso não informado!");
        location.href = "cursos.html";
        return;
    }

    byId("cursoId").value = id;

    try {
        const curso = await apiFetch(`/api/cursos/${id}`);
        byId("tituloCurso").innerText = curso.titulo || "Curso";
        byId("descricaoCurso").innerText = curso.descricao || "Sem descrição.";
        byId("professorCurso").innerText = `${curso.nomeProfessor || "Professor não informado"} (ID ${curso.professorId || "-"})`;

        const tbody = byId("tabelaAlunosCurso");
        tbody.innerHTML = "";

        if (!curso.alunos || curso.alunos.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" class="text-center text-secondary">Nenhum aluno matriculado.</td></tr>`;
            return;
        }

        curso.alunos.forEach(aluno => {
            tbody.innerHTML += `
                <tr>
                    <td>${aluno.id}</td>
                    <td>${aluno.nome || ""}</td>
                    <td>${aluno.ra || ""}</td>
                    <td>${aluno.nota ?? "Sem nota"}</td>
                    <td>${aluno.situacao || ""}</td>
                    <td class="text-end">
                        <button class="btn btn-sm btn-success" onclick="prepararNota(${curso.id}, ${aluno.id})">Nota</button>
                        <button class="btn btn-sm btn-danger" onclick="removerAlunoCurso(${curso.id}, ${aluno.id})">Remover</button>
                    </td>
                </tr>`;
        });
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function matricularAluno(event) {
    event.preventDefault();

    const cursoId = byId("cursoId").value;
    const alunoId = byId("alunoId").value;

    if (!alunoId) return alert("Informe o ID do aluno!");

    try {
        await apiFetch(`/api/cursos/${cursoId}/alunos/${alunoId}`, { method: "POST" });
        alert("Aluno matriculado com sucesso!");
        byId("alunoId").value = "";
        carregarCursoDetalhe();
    } catch (erro) {
        mostrarErro(erro);
    }
}

function prepararNota(cursoId, alunoId) {
    byId("notaCursoId").value = cursoId;
    byId("notaAlunoId").value = alunoId;
    byId("nota").focus();
}

async function aplicarNota(event) {
    event.preventDefault();

    const cursoId = byId("notaCursoId").value;
    const alunoId = byId("notaAlunoId").value;
    const professorId = Number(byId("notaProfessorId").value);
    const nota = Number(byId("nota").value);

    if (!cursoId || !alunoId) return alert("Clique em 'Nota' em algum aluno primeiro!");
    if (!professorId) return alert("Informe o ID do professor!");
    if (isNaN(nota) || nota < 0 || nota > 10) return alert("Nota deve estar entre 0 e 10!");

    try {
        await apiFetch(`/api/cursos/${cursoId}/alunos/${alunoId}/nota`, {
            method: "PATCH",
            body: JSON.stringify({ professorId, nota })
        });
        alert("Nota aplicada com sucesso!");
        byId("formNota").reset();
        byId("notaCursoId").value = "";
        byId("notaAlunoId").value = "";
        carregarCursoDetalhe();
    } catch (erro) {
        mostrarErro(erro);
    }
}

async function removerAlunoCurso(cursoId, alunoId) {
    if (!confirm("Deseja remover este aluno do curso?")) return;

    try {
        await apiFetch(`/api/cursos/${cursoId}/alunos/${alunoId}`, { method: "DELETE" });
        alert("Aluno removido do curso!");
        carregarCursoDetalhe();
    } catch (erro) {
        mostrarErro(erro);
    }
}
