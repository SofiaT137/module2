import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import { useState } from "react";
import { useContext } from "react";
import { Context } from "./../Context";

import {
    Box,
    Alert,
    Collapse,
  } from "@mui/material";

export const ErrorWindow = () => {

    const [open, setOpen] = useState(true);

    const {error, editError} = useContext(Context);
    
    return (
        <Box sx={{ width: "100%" }}>
        <Collapse in={open}>
          <Alert
            severity="error"
            action={
              <IconButton
                aria-label="close"
                color="inherit"
                size="small"
                onClick={() => {
                  setOpen(false)
                  editError("")
                }}
              >
                <CloseIcon fontSize="inherit" />
              </IconButton>
            }
            sx={{ m: 1, width: "100%" }}
          >
            {error.response.data.exceptionMessage}
          </Alert>
        </Collapse>
      </Box>
    ) 
}