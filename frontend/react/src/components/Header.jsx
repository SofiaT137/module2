import { useNavigate } from "react-router-dom";
import Container from "react-bootstrap/Container";
import { useState } from "react";
import { ModalWindowCreate } from "././ModalWindow/ModalWindowCreate";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { useContext } from "react";
import { Context } from "./Context";
import { Button } from "./../components/Buttons/Button";

export const Header = () => {
  const { editForced, forced, editPageSize } = useContext(Context);

  const [modalActive, setModalActive] = useState(false);
  const navigate = useNavigate();

  const change = (eventkey) => {
    editPageSize(eventkey);
    editForced(!forced);
  };

  const closeWindow = () => {
    editForced(!forced);
    setModalActive(false);
  };

  let login = localStorage.getItem("login");

  const logout = () => {
    localStorage.removeItem("login");
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container>
        <Navbar.Brand>Admin UI</Navbar.Brand>
        <Nav className="me-auto">
          {" "}
          {login ? (
            <>
              <Button
                className="btn-primary"
                title="Add new"
                onClick={() => setModalActive(true)}
              />
            </>
          ) : (
            <></>
          )}
          {login ? (
            <>
              <NavDropdown title={"Items on page"} onSelect={change}>
                <NavDropdown.Item eventKey="5">5</NavDropdown.Item>
                <NavDropdown.Item eventKey="10">10</NavDropdown.Item>
                <NavDropdown.Item eventKey="20">20</NavDropdown.Item>
              </NavDropdown>
            </>
          ) : (
            <></>
          )}
        </Nav>
        <Nav className="ms-auto">
          {login ? (
            <>
              <NavDropdown title={login}>
                <NavDropdown.Item onClick={logout}>Log out</NavDropdown.Item>
              </NavDropdown>
            </>
          ) : (
            <></>
          )}
        </Nav>
      </Container>
      <ModalWindowCreate
        active={modalActive}
        setActive={setModalActive}
        onClose={closeWindow}
      />
    </Navbar>
  );
};
