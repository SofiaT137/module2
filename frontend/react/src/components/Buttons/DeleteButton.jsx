import { useState, useContext } from "react";
import { Button } from "./Button";
import axios from "axios";
import { ModalWindowDelete } from "./../ModalWindow/ModalWindowDelete";
import {Context} from "./../Context";

export const DeleteButton = ({id}) => {

    const {editForced, forced, editError} = useContext(Context);
    const [modalDelete, setModalDelete] = useState(false);
    const BASE_URL = "http://localhost:8085/module2/gift_certificates/";

    const areUSureDelete = (choose) => {
        const headers = {
          Authorization: "Bearer_" + localStorage.getItem("token"),
        };
        if (choose) {
          axios
            .delete(BASE_URL + id, { headers })
            .then((response) => console.log(response.status))
            .catch((error) => {
              editError(error)
            });
          setModalDelete(false);
          editForced(!forced);
        } else {
          setModalDelete(false);
        }
      };

    const handleDelete = () => {
        setModalDelete(true);
    }

    return (<>
    <Button title="Delete" className="btn-light" onClick={handleDelete} />
    <ModalWindowDelete
        active={modalDelete}
        setActive={setModalDelete}
        certificateId={id}
        onDialog={areUSureDelete}
      />
    </>)
}