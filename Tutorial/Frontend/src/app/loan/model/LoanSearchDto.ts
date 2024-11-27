import { Pageable } from '../../core/model/page/Pageable';
export interface LoanSearchDto {
    gameId?: number;
    clientId?: number;
    searchDate?: string;
    pageable: {
        pageNumber: number;
        pageSize: number;
    };
}