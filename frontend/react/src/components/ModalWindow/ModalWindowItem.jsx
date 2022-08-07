import { ModalWindow } from "./ModalWindow";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { ModalHeader } from "./ModalHeader";

export const ModalWindowItem = ({active, setActive, id,name, 
    description,price,duration,createdDate,lastModifyedDate,tags, onClose}) => {
    return (
        <ModalWindow active={active} setActive={setActive}>
        <Modal.Dialog>

        <ModalHeader title={"Item information"}/>
    
          <Modal.Body>
            <p>Certificate id : {id}</p>
            <p>Certificate name: {name}</p>
            <p>Certificate description: {description}</p>
            <p>Certificate price: {price}</p>
            <p>Ceritificate duration: {duration}</p>
            <p>Certificate creation date: {createdDate}</p>
            <p>Certificate last modification date: {lastModifyedDate}</p>
            <p>Certificate tags: {tags}</p>
          </Modal.Body>

          <Modal.Footer>
            <Button variant="secondary" onClick={() => onClose(false)}>Close</Button>
          </Modal.Footer>
        </Modal.Dialog>
        </ModalWindow>
    );
};