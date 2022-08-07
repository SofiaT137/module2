import { DeleteButton } from "./../Buttons/DeleteButton";
import { EditBotton } from "./../Buttons/EditButton";
import { ViewButton } from "./../Buttons/ViewButton";

export const TableButtons = ({ certificate }) => {
  return (
    <div className="btn-group" role="group" aria-label="Basic example">
      <ViewButton
        id={certificate.id}
        name={certificate.giftCertificateName}
        description={certificate.description}
        price={certificate.price}
        duration = {certificate.duration}
        createDate={certificate.createDate}
        lastUpdateDate={certificate.lastUpdateDate}
        tags={certificate.tags}
      />
      <EditBotton id={certificate.id}
        name={certificate.giftCertificateName}
        description={certificate.description}
        price={certificate.price}
        duration = {certificate.duration} 
        tags={certificate.tags}/> 
      <DeleteButton id={certificate.id} />
    </div>
  );
};
