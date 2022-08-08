import { useState } from "react";
import { Button } from "./Button";
import { ModalWindowItem } from "../ModalWindow/ModalWindowItem";
import {convertDate, getTagsNames} from "../../utils"

export const ViewButton = ({id, name, description, price, duration, createDate, lastUpdateDate, tags}) => {

    const [modalView, setModalView] = useState(false);

    const handleView = () => {
        setModalView(true);
    }

    const closeWindow = () => {
        setModalView(false);
      }

    return (<>
    <Button title="View" className="btn-primary" onClick={handleView} />
    <ModalWindowItem
        active={modalView}
        setActive={setModalView}
        id={id}
        name={name}
        description={description}
        price={price}
        duration={duration}
        createdDate={convertDate(createDate)}
        lastModifyedDate={convertDate(lastUpdateDate)}
        tags={getTagsNames(tags)}
        onClose={closeWindow}
      />
    </>)
}