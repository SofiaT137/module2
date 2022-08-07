import { useContext } from "react";
import { Container, Stack } from "@mui/material";
import TableCustom from "./Table/Table";
import { SearchBox } from "./SearchBox/SearchBox";
import { Context } from "./Context";
import { ErrorWindow } from "./ErrorWindow/ErrorWindow";
import { Pagination } from "./Pagination/Pagination";

export const MainPage = () => {
  const { certificates, error } = useContext(Context);

  return (
    <Container
      sx={{ marginTop: 2, minHeight: "calc(100vh - 123px)" }}
      maxWidth="md"
    >
      {error ? (
        <>
          <ErrorWindow />
        </>
      ) : (
        <></>
      )}
      <SearchBox />
      <Stack spacing={3}>
        <TableCustom certificates={certificates} />
        <Pagination />
      </Stack>
    </Container>
  );
};
