import React, { useState } from "react";

function FormData({ addTarefa }) {
  const [descricao, setDescricao] = useState("");
  const [data, setData] = useState("");
  const [prioridade, setPrioridade] = useState("AUSENTE");

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (descricao && data && prioridade) {
      const novaTarefa = {
        description: descricao,
        priority: prioridade,
        plannedDate: data,
      };

      const response = await fetch("http://localhost:8080/api/task/create/data", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(novaTarefa),
      });

      if (response.ok) {
        setDescricao("");
        setData("");
        setPrioridade("AUSENTE");
        const data = await response.json();
        addTarefa(data);

      } else {
        console.error("Erro ao criar a Tarefa:", response.statusText);
      }
    } else {
      alert("Todos os campos devem ser preenchidos");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="input-wrapper">
        <input
          type="text"
          placeholder="Descrição"
          value={descricao}
          onChange={(e) => setDescricao(e.target.value)}
          required
        />
      </div>
      <div className="input-wrapper">
        <input
          type="date"
          value={data}
          onChange={(e) => setData(e.target.value)}
          required
        />
      </div>
      <div className="input-wrapper">
        <select
          value={prioridade}
          onChange={(e) => setPrioridade(e.target.value)}
          required
        >
          <option value="ALTA">Alta</option>
          <option value="MEDIA">Média</option>
          <option value="BAIXA">Baixa</option>
          <option value="AUSENTE">Ausente</option>
        </select>
      </div>
      <button className="button-criar-tarefa" type="submit">
        Criar
      </button>
    </form>
  );
}

export default FormData;
