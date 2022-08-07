import * as S from './styled';
import TableBody from '@mui/material/TableBody';
import { TableItem } from './TableItem';


export const TableBodyComponent = ({certificates}) => {
    return (
    <TableBody>
        {certificates.map((certificate) => (
          <S.StyledTableRow key={certificate.giftCertificateName}>
            <TableItem certificate={certificate}/>
          </S.StyledTableRow>
        ))}
      </TableBody>)
}