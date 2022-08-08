import { ModalWindow } from "./ModalWindow";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import { TagsInput } from "react-tag-input-component";
import { useState } from "react";
import { useForm } from "react-hook-form";
import axios from "axios";
import { useContext } from "react";
import {Context} from "./../Context";
import { ModalHeader } from "./ModalHeader";

export const ModalWindowEdit = ({ active, setActive, id, name, gsDescription,gsPrice,gsDuration,tags,onClose}) => {

  const BASE_URL = "http://localhost:8085/module2/gift_certificates/";
  const [selected, setSelected] = useState([]);
  const [tagLengthError, setTagLengthError] = useState(false);
  const [tagIncorectName, setTagIncorectName] = useState('');
  const [tagExceptionMessage, setTagExceptionMessage] = useState('');

  const {editError} = useContext(Context);  

  const {
    register,
    formState: { errors, isValid },
    handleSubmit,
    getValues,
    reset
  } = useForm({
    mode: "all",
  });

  const onSubmit = () => {
    sentUpdateItemRequest();
    clearFormFunction();
  };


  const onBlur = () => {
    setTagLengthError(false);
  }

  const clearFormFunction = () => {
    reset();
    while(selected.length > 0) {
      selected.pop();
    }
    onClose(false);   
  }

  const beforeAddTagValidation = (tags) => {

    if(!(tags[tags.length-1] === undefined)){
      if(tags[tags.length-1].length < 3){
        setTagIncorectName(tags[tags.length-1]);
        setTagExceptionMessage(' Tag name length must be greater than 3');
        setTagLengthError(true);
        tags.pop();
      }else if(tags[tags.length-1].length > 15){
        setTagIncorectName(tags[tags.length-1]);
        setTagExceptionMessage(' Tag name length must be mess than 15');
        setTagLengthError(true);
        tags.pop();
      }
    }    
    return tags;
  }
  const sentUpdateItemRequest = () => {
    const giftCertificateName = getValues("giftCertificateName");
    const description = getValues("description");
    const price = getValues("price");
    const duration = getValues("duration"); 
    const tags = selected;

    const headers = { 
      'Content-Type': 'application/json',
      'Authorization': 'Bearer_' + localStorage.getItem('token'),
    };

    axios.patch(BASE_URL+id, {
      giftCertificateName: giftCertificateName === '' ? name : giftCertificateName,
      description: description === '' ? gsDescription : description,
      price: price === '' ? gsPrice : price,
      duration: duration === '' ? gsDuration : duration,
      tags}, {headers})
      .then(response  => {
         if(response.status === 201){
          console.log('updated');
        }
      })
      .catch(error  => {
        editError(error)
      });
  };

 
  return (
    <ModalWindow active={active} setActive={setActive}>
      <Modal.Dialog>

      <ModalHeader title={'Edit certificate with id = '+ id }/>

        <Modal.Body>
          <form onSubmit={handleSubmit(onSubmit)} noValidate>
            <div className="form-group">
              <label htmlFor="gsName">Name</label>
              <input
                type="text"
                className="form-control"
                id="gsName"
                {...register("giftCertificateName", {
                  minLength: {
                    value: 6,
                    message: "Minimum name length is 6",
                  },
                  maxLength: {
                    value: 30,
                    message: "Maximum name length is 30",
                  },
                })}
              />
            </div>
            <div className="errorMSG">
              {errors?.giftCertificateName && (
                <p>{errors?.giftCertificateName?.message || "Error!"}</p>
              )}
            </div>
            <div className="form-group">
              <label htmlFor="gsDescription">Description</label>
              <textarea
                className="form-control"
                id="gsDescription"
                rows="2"
                {...register("description", {
                  minLength: {
                    value: 12,
                    message: "Minimum description length is 12",
                  },
                  maxLength: {
                    value: 1000,
                    message: "Maximum description length is 1000",
                  },
                })}
              ></textarea>
            </div>
              <div className="errorMSG">
              {errors?.description && (
                <p>{errors?.description?.message || "Error!"}</p>
              )}
            </div>
            <div className="form-group">
              <label htmlFor="gsPrice">Price</label>
              <input
                type="number"
                className="form-control"
                id="gsPrice"
                step=".01"
                {...register("price", {
                  min: {
                    value: 1.0,
                    message: "Minimum price is 1.0",
                  },
                })}
              />
            </div>
            <div className="errorMSG">
              {errors?.price && (
                <p>{errors?.price?.message || "Error!"}</p>
              )}
              </div>
            <div className="form-group">
              <label htmlFor="gsDuration">Duration</label>
              <input
                type="number"
                className="form-control"
                id="gsDuration"
                {...register("duration", {
                  min: {
                    value: 1,
                    message: "Minimum duration is 1",
                  },
                })}
              />
            </div>
            <div className="errorMSG">
              {errors?.duration && (
                <p>{errors?.duration?.message || "Error!"}</p>
              )}
              </div>
            <div>
              <label>Tags</label>
              <TagsInput
                value={selected}
                beforeAddValidate={beforeAddTagValidation(selected)}
                onBlur={onBlur}
                onChange={setSelected}
                placeHolder="enter certificate tags"
              />
            </div>
            {tagLengthError && <div className="errorMSG">Tag "{tagIncorectName}" is not correct.{tagExceptionMessage}</div>}
            <div className="buttonGroup">
              <Button variant="primary" type="submit" disabled={!isValid}>
                Edit
              </Button>
              <Button variant="secondary" onClick={() => clearFormFunction()}>
                Cancel
              </Button>
            </div>
          </form>
        </Modal.Body>
      </Modal.Dialog>
    </ModalWindow>
  );
};
