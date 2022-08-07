import Modal from "react-bootstrap/Modal";

export const ModalHeader = (props) => {
  const { title } = props;
  return (
    <Modal.Header>
      <Modal.Title>{title}</Modal.Title>
    </Modal.Header>
  );
}
