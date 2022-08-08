import { ModalWindow } from "./ModalWindow";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { ModalHeader } from "./ModalHeader";

export const ModalWindowDelete = ({active, setActive,certificateId,onDialog}) => {
    return (

        <ModalWindow active={active} setActive={setActive}>
          
        <Modal.Dialog>

          <ModalHeader title={"Delete confirmation"}/>
    
          <Modal.Body>
            <p>Are you sure that you want to delete certificate with id={certificateId}?</p>
          </Modal.Body>

          <Modal.Footer>
            <Button variant="primary" onClick={() => onDialog(true)}>Yes</Button>
            <Button variant="secondary" onClick={() => onDialog(false)}>Cancel</Button>
          </Modal.Footer>
        </Modal.Dialog>
        </ModalWindow>
    );
};
    
