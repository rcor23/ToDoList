import React, { useState } from "react";
import FormNormal from "./forms/formNormal";
import FormData from "./forms/formData";
import FormPrazo from "./forms/formPrazo";
import './TodoForm.css';


function TodoForm({ addTarefa }) {

  const [formType, setFormType] = useState("normal");

  const renderForm = () => {
    switch (formType) {
      case "normal":
        return <div><FormNormal addTarefa={addTarefa}/></div>;
      case "prazo":
        return <div><FormPrazo addTarefa={addTarefa}/></div>;
      case "data":
        return <div><FormData addTarefa={addTarefa}/></div>;
      default:
        return null;
    }
  };

  return (
    <div className="tarefa-form-card">
      <h2>Criar Tarefa</h2>

      <div>
        <button className="button-type-form" onClick={() => setFormType("normal")}>Normal</button>
        <button className="button-type-form" onClick={() => setFormType("prazo")}>Prazo</button>
        <button className="button-type-form" onClick={() => setFormType("data")}>Data</button>
      </div>

      {renderForm()}

    </div>
  );

};

export default TodoForm;
