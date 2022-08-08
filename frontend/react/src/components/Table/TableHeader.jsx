import * as S from './styled';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';

export const TableHeader = () => {
    return (<TableHead>
        <TableRow>
          <S.StyledTableCell>Title</S.StyledTableCell>
          <S.StyledTableCell align="center">Create date</S.StyledTableCell>
          <S.StyledTableCell align="center">Tags</S.StyledTableCell>
          <S.StyledTableCell align="center">Duration</S.StyledTableCell>
          <S.StyledTableCell align="center">Price</S.StyledTableCell>
          <S.StyledTableCell align="center">Actions</S.StyledTableCell>
        </TableRow>
      </TableHead>)
}