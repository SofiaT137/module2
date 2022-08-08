import {
    Pagination as MUPagination
  } from "@mui/material";
import { useContext } from "react";
import { Context } from "./../Context";

export const Pagination = () => {

const {editPage, page, pageQty} = useContext(Context);

  return (
    <>
      {!!pageQty && (
        <MUPagination
          sx={{
            display: "flex",
            justifyContent: "center",
            marginBottom: "20px",
          }}
          count={pageQty}
          page={page}
          showFirstButton
          showLastButton
          variant="outlined"
          shape="rounded"
          onChange={(_, num) => editPage(num)}
        />
      )}
    </>
  );
};
