import { TextField } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import { useState } from "react";
import { useContext } from "react";
import { Context } from "./../Context";
import { getQuery } from "../../utils";

export const SearchBox = () => {
  const [title, setTitle] = useState("");

  const { editPage, editQuery } = useContext(Context);

  const onSearchChange = (event) => {
    setTitle(event.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (title) {
      editPage(1);
      editQuery(getQuery(title, "Name"));
    }
  };

  return (
    <form noValidate autoComplete="off" onSubmit={handleSubmit}>
      <TextField
        sx={{
          m: 1,
          backgroundColor: "white",
          borderRadius: "5px",
          border: "none",
        }}
        fullWidth
        label="Find a certificate"
        value={title}
        onChange={onSearchChange}
        InputProps={{
          endAdornment: (
            <button
              type="submit"
              style={{ border: "none", backgroundColor: "white" }}
            >
              <SearchIcon />
            </button>
          ),
        }}
      />
    </form>
  );
};
