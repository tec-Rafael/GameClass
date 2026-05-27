"use strict";

async function carregarMatriculas() {
    const cursoId = byId("cursoIdFiltro").value.trim();
    const url = cursoId ? `/api/matriculas/curso/${cursoId}` : "/api/matriculas";

    try {
        const matriculas = await apiFetch(url);
        const tbody = byId("tabelaMatriculas");
        tbody.innerHTML = "";

        if (!matriculas || matriculas.length === 0) {
            tbody.innerHTML = `<tr><td colspan="8" class="text-center text-secondary">Nenhuma matrícula encontrada.</td></tr>`;
            return;
        }

        matriculas.forEach(m => {
            tbody.innerHTML += `
                <tr>
                    <td>${m.id}</td>
                    <td>${m.alunoId}</td>
                    <td>${m.nomeAluno || ""}</td>
                    <td>${m.raAluno || ""}</td>
                    <td>${m.cursoId}</td>
                    <td>${m.tituloCurso || ""}</td>
                    <td>${m.nota ?? "Sem nota"}</td>
                    <td>${m.situacao || ""}</td>
                </tr>`;
        });
    } catch (erro) {
        mostrarErro(erro);
    }
}
