import { TableBodyComponent } from "./TableBody";
import { TableHeader } from "./TableHeader";
import Table from '@mui/material/Table';
import Paper from '@mui/material/Paper';
import TableContainer from '@mui/material/TableContainer';

const TableCustom = (props) => {
const {certificates} = props;
  return (
    <TableContainer sx={{ m: 1 }} component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="customized table">
        <TableHeader/>
        <TableBodyComponent certificates={certificates} />  
        </Table>
    </TableContainer>
  );
};

export default TableCustom;
