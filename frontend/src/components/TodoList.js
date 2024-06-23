import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimesCircle } from "@fortawesome/free-solid-svg-icons"; 
import './TodoList.css'; 

export const TodoList = ({ tarefas, setTarefas }) => {
  const handleExcluirTarefa = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/task/${id}`, {
        method: "DELETE",
      });
      if (response.ok) {
        console.log("Tarefa deletada");
        setTarefas(tarefas.filter((tarefa) => tarefa.id !== id));
      } else {
        console.error("Erro ao deletar a tarefa:", response.statusText);
      }
    } catch (error) {
      console.error("Erro ao deletar a tarefa:", error);
    }
  };

  return (
    <div className="tarefa-list-card">
      <h2>Suas Tarefas</h2>
      <ul>
        {tarefas.map((tarefa) => (
          <li key={tarefa.id} className={`tarefa-item ${tarefa.completed ? 'completed' : ''}`}>
            <span>{tarefa.description}</span>
            <button onClick={() => handleExcluirTarefa(tarefa.id)}>
              <FontAwesomeIcon icon={faTimesCircle} />
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};
