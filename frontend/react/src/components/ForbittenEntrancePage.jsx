import { Button } from "./../components/Buttons/Button";
import { useNavigate } from "react-router-dom";

export const ForbittenEntrancePage = () => {

    const navigate = useNavigate();

    const handleExit = () => {
        navigate("/login");
    }

    return (
        <div className="noAccess">
        <h1> You have no access!!!</h1>
        <Button title="Exit" className="btn-primary" onClick={handleExit} />
        </div>
      );
}