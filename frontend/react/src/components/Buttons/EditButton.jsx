import { Button } from "./Button";
import { useState } from "react";
import { ModalWindowEdit } from "./../ModalWindow/ModalWindowEdit";
import { useContext } from "react";
import {Context} from "./../Context";

export const EditBotton = ({
  id,
  name,
  description,
  price,
  duration,
  tags,
}) => {
  const [modalActive, setModalActive] = useState(false);
  
  const {editForced, forced} = useContext(Context);

  const handleEdit = () => {
    setModalActive(true);
  };

  const closeWindow = () => {
    editForced(!forced);
    setModalActive(false);
  };

  return (
    <>
      <Button title="Edit" className="btn-info" onClick={handleEdit} />
      <ModalWindowEdit
        active={modalActive}
        setActive={setModalActive}
        id={id}
        name={name}
        gsDescription={description}
        gsPrice={price}
        gsDuration={duration}
        tags={tags}
        onClose={closeWindow}
      />
    </>    
  );
};
